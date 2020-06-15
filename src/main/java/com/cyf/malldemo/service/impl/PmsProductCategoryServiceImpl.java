package com.cyf.malldemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cyf.malldemo.dao.PmsProductCategoryDao;
import com.cyf.malldemo.dao.ProductCategoryAttributeRelationDao;
import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.dto.PmsProductCategoryWithChildren;
import com.cyf.malldemo.mbg.mapper.PmsProductCategoryMapper;
import com.cyf.malldemo.mbg.model.PmsProductCategory;
import com.cyf.malldemo.mbg.model.PmsProductCategoryAttributeRelation;
import com.cyf.malldemo.mbg.model.PmsProductCategoryExample;
import com.cyf.malldemo.service.PmsProductCategoryService;
import com.github.pagehelper.PageHelper;
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
    @Autowired
    private PmsProductCategoryDao pmsProductCategoryDao;

    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        //新建分类，产品数量为0
        pmsProductCategory.setProductCount(0);
        BeanUtil.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        //更新等级
        updateLevel(pmsProductCategory);
        //分类的属性
        int count =pmsProductCategoryMapper.insert(pmsProductCategory);
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            // TODO: 2020/6/14 分类属性的插入
            insertRelationList(pmsProductCategory.getId(),productAttributeIdList);
        }
        return count;
    }

    /**
     * sql 语句查询 PmsProductCategoryWithChildren 把子分类放入List集合中
     * @return
     */
    @Override
    public List<PmsProductCategoryWithChildren> listWithChildren() {
        return pmsProductCategoryDao.listWithChildren();
    }

    @Override
    public List<PmsProductCategory> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return pmsProductCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return pmsProductCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    //采用语法把查出来的子对象放入List集合 尝试使用sql语句
   /* @Override
    public List<PmsProductCategoryWithChildren> listWithChildren() {
        List<PmsProductCategory> list = pmsProductCategoryMapper.selectByExample(new PmsProductCategoryExample());
        List<PmsProductCategoryWithChildren> withChildrenList = list.stream()
                //equals 要按照类型Long 不能填int0 不然返回false
                .filter(pmsProductCategory -> pmsProductCategory.getParentId().equals(0L))
                .map(pmsProductCategory -> covertPmsProductCategory(pmsProductCategory, list))
                .collect(Collectors.toList());
        return withChildrenList;
    }

    *//**
     * 将所有的PmsProductCategory 转化为 PmsProductCategoryWithChildren
     * @param pmsProductCategory 传入的父菜单
     * @param list
     * @return
     *//*
    private PmsProductCategoryWithChildren covertPmsProductCategory(PmsProductCategory pmsProductCategory, List<PmsProductCategory> list) {
        PmsProductCategoryWithChildren withChildren = new PmsProductCategoryWithChildren();
        BeanUtil.copyProperties(pmsProductCategory,withChildren);
        List<PmsProductCategoryWithChildren> children = list.stream()
                .filter(subPmsProductCategory -> pmsProductCategory.getId().equals(subPmsProductCategory.getParentId()))
                .map(subPmsProductCategory -> covertPmsProductCategory(subPmsProductCategory, list))
                .collect(Collectors.toList());
        withChildren.setChildren(children);
        return withChildren;
    }*/

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
