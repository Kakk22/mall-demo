package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.mbg.mapper.PmsProductAttributeMapper;
import com.cyf.malldemo.mbg.model.PmsProductAttribute;
import com.cyf.malldemo.mbg.model.PmsProductAttributeExample;
import com.cyf.malldemo.service.PmsProductAttributeService;
import com.github.pagehelper.PageHelper;
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

    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria()
                .andProductAttributeCategoryIdEqualTo(cid)
                .andTypeEqualTo(type);
        return  pmsProductAttributeMapper.selectByExample(example);
    }
}
