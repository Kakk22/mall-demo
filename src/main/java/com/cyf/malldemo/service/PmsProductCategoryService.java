package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.dto.PmsProductCategoryWithChildren;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/14.
 */
public interface PmsProductCategoryService {

    int create(PmsProductCategoryParam pmsProductCategoryParam);

    List<PmsProductCategoryWithChildren> listWithChildren();
}
