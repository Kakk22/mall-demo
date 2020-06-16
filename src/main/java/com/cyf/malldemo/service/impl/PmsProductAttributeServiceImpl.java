package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dto.PmsProductAttributeParam;
import com.cyf.malldemo.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.cyf.malldemo.mbg.mapper.PmsProductAttributeMapper;
import com.cyf.malldemo.mbg.model.PmsProductAttribute;
import com.cyf.malldemo.mbg.model.PmsProductAttributeCategory;
import com.cyf.malldemo.mbg.model.PmsProductAttributeExample;
import com.cyf.malldemo.service.PmsProductAttributeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria()
                .andProductAttributeCategoryIdEqualTo(cid)
                .andTypeEqualTo(type);
        return pmsProductAttributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam param) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(param, pmsProductAttribute);
        int count = pmsProductAttributeMapper.insertSelective(pmsProductAttribute);
        //添加完后 商品的分类对应的要添加
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        //0为属性 1为参数
        if (pmsProductAttribute.getType() == 0) {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        } else if (pmsProductAttribute.getType() == 1) {
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        }
        pmsProductAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        //先查看是属性还是参数
        PmsProductAttribute pmsProductAttribute = pmsProductAttributeMapper.selectByPrimaryKey(ids.get(0));
        Integer type = pmsProductAttribute.getType();
        //删除的分类
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = pmsProductAttributeMapper.deleteByExample(example);
        //删除完要删除数量
        if (type == 0){
            if (pmsProductAttributeCategory.getAttributeCount() > count){
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() - count);
            }else {
                pmsProductAttributeCategory.setAttributeCount(0);
            }

        }
        if (type == 1){
            if (pmsProductAttributeCategory.getParamCount() > count){
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() - count);
            }else {
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        pmsProductAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }
}
