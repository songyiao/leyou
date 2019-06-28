package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkuService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public List<Sku> querySkuListBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        List<Long> skuIds = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(skuIds);
        if(CollectionUtils.isEmpty(stockList)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        Map<Long, Long> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return skus;
    }
}
