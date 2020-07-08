package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.SmsCouponProductCategoryRelationDao;
import com.cyf.malldemo.dao.SmsCouponProductRelationDao;
import com.cyf.malldemo.dto.SmsCouponParam;
import com.cyf.malldemo.mbg.mapper.SmsCouponMapper;
import com.cyf.malldemo.mbg.model.SmsCoupon;
import com.cyf.malldemo.mbg.model.SmsCouponExample;
import com.cyf.malldemo.mbg.model.SmsCouponProductCategoryRelation;
import com.cyf.malldemo.mbg.model.SmsCouponProductRelation;
import com.cyf.malldemo.service.SmsCouponService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
@Service
public class SmsCouponServiceImpl implements SmsCouponService {
    @Autowired
    private SmsCouponMapper smsCouponMapper;
    @Autowired
    private SmsCouponProductCategoryRelationDao productCategoryRelationDao;
    @Autowired
    private SmsCouponProductRelationDao productRelationDao;

    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        List<SmsCoupon> list = smsCouponMapper.selectByExample(example);
        return list;
    }

    @Override
    public int create(SmsCouponParam param) {
        param.setCount(param.getPublishCount());
        param.setUseCount(0);
        param.setReceiveCount(0);
        smsCouponMapper.insert(param);
        if (param.getUseType() == 1) {
            //指定分类
            List<SmsCouponProductCategoryRelation> productCategoryRelationList = param.getProductCategoryRelationList();
            for (SmsCouponProductCategoryRelation categoryRelation : productCategoryRelationList) {
                categoryRelation.setCouponId(param.getId());
            }
            productCategoryRelationDao.insertList(productCategoryRelationList);
        }
        if (param.getUseType() == 2){
            //指定产品
            List<SmsCouponProductRelation> productRelationList = param.getProductRelationList();
            for (SmsCouponProductRelation productRelation : productRelationList) {
                productRelation.setCouponId(param.getId());
            }
            productRelationDao.insertList(productRelationList);
        }
        return 1;
    }
}
