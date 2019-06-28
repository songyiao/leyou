package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("INSERT INTO tb_category_brand(category_id,brand_id) VALUES(#{cid},#{id})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("id")Long id);

    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    void deleteByBid(Long bid);

    @Select("SELECT * FROM tb_brand WHERE id IN (SELECT brand_id FROM tb_category_brand WHERE category_id = #{cid})")
    List<Brand> queryBrandByCid(Long cid);
}
