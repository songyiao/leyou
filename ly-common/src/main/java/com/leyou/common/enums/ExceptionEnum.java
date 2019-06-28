package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {

    BRAND_NOT_FOUD(404,"未查到品牌信息!"),
    CATEGORY_NOT_FOUND(404,"分类内容未找到!"),
    BRAND_ADD_FAIL(500,"品牌新增失败!"),
    INVALID_FILE_TYPE(400,"无效的文件类型!"),
    BRAND_UPDATE_FAIL(500,"品牌修改失败!"),
    GROUP_NOT_FOUND(404,"分组信息未查到!"),
    PARAMS_NOT_FOUND(404,"规格参数未查到!"),
    GOODS_NOT_FOUND(404,"商品未找到!")
    ;
    private int code;
    private String msg;
}
