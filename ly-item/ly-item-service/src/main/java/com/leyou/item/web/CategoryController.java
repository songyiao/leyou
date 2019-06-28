package com.leyou.item.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> list(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        List<Category> categoryList = categoryService.queryListByParent(pid);
        if(categoryList == null || categoryList.size() < 1 ){
            throw  new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandBid(@PathVariable("bid")Long bid){
        List<Category> categoryList = categoryService.queryByBrandBid(bid);
        if(CollectionUtils.isEmpty(categoryList)){
            throw  new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok(categoryList);
    }
}
