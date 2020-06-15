package com.cyf.malldemo.dao;

import com.cyf.malldemo.dto.PmsProductCategoryWithChildren;

import java.util.List;

/**自定义 分类查询Dao
 * @author by cyf
 * @date 2020/6/15.
 */
public interface PmsProductCategoryDao {

    List<PmsProductCategoryWithChildren>  listWithChildren();
}
