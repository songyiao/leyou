package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Brand.class);
        if(StringUtils.isNoneBlank(sortBy)){
            example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
        }
        if (StringUtils.isNoneBlank(key)){
            example.createCriteria().andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        List<Brand> brandList = brandMapper.selectByExample(example);
        PageInfo<Brand> info = new PageInfo<>(brandList);
        return new PageResult<Brand>(info.getTotal(),brandList);
    }

    public void saveBrand(Brand brand, List<Long> cids) {
        int count = brandMapper.insert(brand);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_ADD_FAIL);
        }
        for (Long cid : cids) {
            int i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(i != 1){
                throw new LyException(ExceptionEnum.BRAND_ADD_FAIL);
            }
        }
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        int count = brandMapper.updateByPrimaryKey(brand);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_UPDATE_FAIL);
        }
        brandMapper.deleteByBid(brand.getId());
        for (Long cid : cids) {
            int i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(i != 1){
                throw new LyException(ExceptionEnum.BRAND_UPDATE_FAIL);
            }
        }
    }

    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUD);
        }

        return list;
    }

    public Brand queryBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
