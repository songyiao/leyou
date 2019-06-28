package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name="tb_category")
@Data
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long parentId;
	private Boolean isParent;
	private Integer sort;
	// getter和setter略
    // 注意isParent的get和set方法
}