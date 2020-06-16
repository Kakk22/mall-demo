package com.cyf.malldemo.service;



import com.cyf.malldemo.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
public interface PmsProductAttributeCategoryService {

    List<PmsProductAttributeCategoryItem> getListWithAtt();
}
