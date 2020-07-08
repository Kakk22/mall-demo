package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
public interface SmsCouponProductRelationDao {

    int insertList(@Param("list") List<SmsCouponProductRelation> list);
}
