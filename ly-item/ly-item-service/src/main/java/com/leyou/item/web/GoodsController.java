package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.SkuService;
import com.leyou.item.service.SpuDetilService;
import com.leyou.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SpuDetilService spuDatilService;
    @Autowired
    private SkuService skuService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(spuService.querySpuList(page,rows,saleable,key));
    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){

        try {
            spuService.saveGoods(spu);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        try {
            spuService.updateGoods(spu);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return ResponseEntity.ok(spuDatilService.querySpuDetailBySpuId(spuId));
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(skuService.querySkuListBySpuId(spuId));
    }
}
