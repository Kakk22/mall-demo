package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.PmsProductAttributeParam;
import com.cyf.malldemo.mbg.model.PmsProductAttribute;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
public interface PmsProductAttributeService {
    /**
     * 分页查找 商品属性列表或参数列表
     * @param cid 商品分类id
     * @param type 0 为属性 1为参数
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<PmsProductAttribute>  getList(Long cid , Integer type,Integer pageSize,Integer pageNum);

    int create(PmsProductAttributeParam param);

    int delete(List<Long> ids);
}
