package com.cyf.malldemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cyf.malldemo.dao.ProductCategoryAttributeRelationDao;
import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.mbg.mapper.PmsProductCategoryMapper;
import com.cyf.malldemo.mbg.model.PmsProductCategory;
import com.cyf.malldemo.mbg.model.PmsProductCategoryAttributeRelation;
import com.cyf.malldemo.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/14.
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;
    @Autowired
    private ProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;

    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        //新建分类，产品数量为0
        pmsProductCategory.setProductCount(0);
        BeanUtil.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        //更新等级
        updateLevel(pmsProductCategory);
        //分类的属性
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            // TODO: 2020/6/14 分类属性的插入
            insertRelationList(pmsProductCategory.getId(),productAttributeIdList);
        }
        return pmsProductCategoryMapper.insert(pmsProductCategory);
    }


    private void insertRelationList(Long id, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>(10);
        for (Long longId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductCategoryId(id);
            relation.setProductAttributeId(longId);
            relationList.add(relation);
        }
        productCategoryAttributeRelationDao.insertList(relationList);
    }

    /**
     * 更新分类等级
     *
     * @param pmsProductCategory
     */
    private void updateLevel(PmsProductCategory pmsProductCategory) {
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            PmsProductCategory parent = pmsProductCategoryMapper.selectByPrimaryKey(pmsProductCategory.getParentId());
            if (parent != null) {
                pmsProductCategory.setLevel(parent.getLevel() + 1);
            } else {
                pmsProductCategory.setLevel(0);
            }
        }
    }
}
