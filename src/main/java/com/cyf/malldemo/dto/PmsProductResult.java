package com.cyf.malldemo.dto;

import lombok.Getter;
import lombok.Setter;

/** 用于返回编辑商品所需要的信息
 * @author by cyf
 * @date 2020/6/19.
 */
@Getter
@Setter
public class PmsProductResult extends  PmsProductParam {
    //商品所选分类的父id
    private Long cateParentId;
}
