package com.cyf.malldemo.enums;

import lombok.Getter;

/**
 * 商品搜索排序枚举
 *
 * @author by cyf
 * @date 2020/11/14.
 */
@Getter
public enum SortEnum {
    /**
     * 相关度
     */
    RELATIVITY(0),
    /**
     * 新品
     */
    NEW(1),
    /**
     * 按销量
     */
    SALES(2),
    /**
     * 价格从低到高
     */
    LOW_PRICE(3),
    /**
     * 价格从高到低
     */
    HIGH_PRICE(4);


    private int sort;

    SortEnum(Integer sort) {
        this.sort = sort;
    }
}
