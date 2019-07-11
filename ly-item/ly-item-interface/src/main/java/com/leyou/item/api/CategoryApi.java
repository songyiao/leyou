package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("names")
    List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("list")
    List<Category> list(@RequestParam(value = "pid",defaultValue = "0") Long pid);

    @GetMapping("bid/{bid}")
    List<Category> queryByBrandBid(@PathVariable("bid")Long bid);
}
