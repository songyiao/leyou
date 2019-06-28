package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spec_param")
@Data
public class SpecParam {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;  //是否为数字类型参数
    private String unit;       //数字类型参数的单位，非数字类型参数可以为空
    private Boolean generic;    //是否是sku通用属性，true或false
    private Boolean searching;  //是否属于搜索过滤
    private String segments;    //数值类型的参数，如果需要搜索过滤，设置的范围
}
