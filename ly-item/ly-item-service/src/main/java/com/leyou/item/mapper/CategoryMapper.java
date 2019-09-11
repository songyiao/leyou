package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category>,IdListMapper<Category,Long> {
    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> queryByBrandBid(Long bid);

    @Select("SELECT f_cid_cname(#{cid1},#{cid2},#{cid3})")
    String queryCnameByCids(@Param("cid1") Long cid1, @Param("cid2")Long cid2, @Param("cid3")Long cid3);
}
