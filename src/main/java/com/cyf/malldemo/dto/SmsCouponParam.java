package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.SmsCoupon;
import com.cyf.malldemo.mbg.model.SmsCouponProductCategoryRelation;
import com.cyf.malldemo.mbg.model.SmsCouponProductRelation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
@Getter
@Setter
public class SmsCouponParam extends SmsCoupon {
    private List<SmsCouponProductRelation> productRelationList;
    private List<SmsCouponProductCategoryRelation>  productCategoryRelationList;
}
