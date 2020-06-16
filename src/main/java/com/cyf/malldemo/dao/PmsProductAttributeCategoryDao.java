package com.cyf.malldemo.dao;

import com.cyf.malldemo.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**自定义商品属性分类Dao
 * @author by cyf
 * @date 2020/6/16.
 */
public interface PmsProductAttributeCategoryDao {
    /**
     * 查询商品分类及其属性
     * @return
     */
    List<PmsProductAttributeCategoryItem>  getListWithAtt();
}
