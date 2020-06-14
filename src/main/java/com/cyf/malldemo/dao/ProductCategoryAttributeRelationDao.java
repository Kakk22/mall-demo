package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**自定义商品分类和属性关系Dao
 * @author by cyf
 * @date 2020/6/14.
 */
public interface ProductCategoryAttributeRelationDao {

    /**
     * 批量创建分类和属性关系
     * @param pmsProductCategoryAttributeRelations
     * @return
     */
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelations);
}
