package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> querySpuList(Integer page, Integer rows, Boolean saleable, String key) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        example.setOrderByClause("last_update_time DESC");
        List<Spu> spuList = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spuList)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        for (Spu spu : spuList) {
            String cname = categoryMapper.queryCnameByCids(spu.getCid1(), spu.getCid2(), spu.getCid3());
            spu.setCname(cname);
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());
        }
        PageInfo info = new PageInfo<>(spuList);
        return new PageResult<Spu>(info.getTotal(),spuList);
    }

    @Transactional
    public void saveGoods(Spu spu) {
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int count = spuMapper.insert(spu);
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_ADD_ERROR);
        }
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(spuDetail);
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_ADD_ERROR);
        }
        saveSkuAndStock(spu);
    }

    @Transactional
    public void updateGoods(Spu spu) {
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skus = skuMapper.select(sku);
        if(!CollectionUtils.isEmpty(skus)){
            skuMapper.delete(sku);
            List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setCreateTime(null);
        spu.setLastUpdateTime(new Date());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        saveSkuAndStock(spu);
    }

    public void saveSkuAndStock(Spu spu){
        List<Sku> skus = spu.getSkus();
        List<Stock> stockList= new ArrayList<>();
        for (Sku sku : skus) {
            if (!sku.getEnable()) {
                continue;
            }
            // 保存sku
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            int count = skuMapper.insert(sku);
            if(count != 1){
                throw new LyException(ExceptionEnum.GOODS_ADD_ERROR);
            }

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        int count = stockMapper.insertList(stockList);
        if(count != stockList.size()){
            throw new LyException(ExceptionEnum.GOODS_ADD_ERROR);
        }
    }
}
