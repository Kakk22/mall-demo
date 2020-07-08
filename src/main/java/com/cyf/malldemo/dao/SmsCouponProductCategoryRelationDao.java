package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.SmsCouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
public interface SmsCouponProductCategoryRelationDao {

    int insertList(@Param("list") List<SmsCouponProductCategoryRelation> list);
}
