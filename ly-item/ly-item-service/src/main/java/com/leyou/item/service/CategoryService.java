package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryListByParent(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",pid);
        return categoryMapper.selectByExample(example);
    }

    public List<Category> queryByBrandBid(Long bid) {
        return categoryMapper.queryByBrandBid(bid);
    }
}