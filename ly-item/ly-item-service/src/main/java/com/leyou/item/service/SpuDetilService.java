package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpuDetilService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }
}
