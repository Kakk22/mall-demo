package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.PmsProductAttributeCategoryDao;
import com.cyf.malldemo.dto.PmsProductAttributeCategoryItem;
import com.cyf.malldemo.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
    @Autowired
    private PmsProductAttributeCategoryDao pmsproductAttributeCategoryDao;

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAtt() {
        return pmsproductAttributeCategoryDao.getListWithAtt();
    }
}
