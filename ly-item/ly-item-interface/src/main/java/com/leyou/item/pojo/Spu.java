package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_spu")
@Data
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private String title;// 标题
    private Long cid3;// 3级类目
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间
    @JsonIgnore  //该属性不转json
    private Date lastUpdateTime;// 最后修改时间
	// 省略getter和setter
    @Transient   //该属性不与数据库字段对应
    private String cname;// 三级分类名称
    @Transient
    private String bname;// 品牌名称
}