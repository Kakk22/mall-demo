package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.dto.PmsProductCategoryWithChildren;
import com.cyf.malldemo.mbg.model.PmsProductCategory;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/14.
 */
public interface PmsProductCategoryService {

    int create(PmsProductCategoryParam pmsProductCategoryParam);

    List<PmsProductCategoryWithChildren> listWithChildren();

    /**
     * 通过父id分页查询
     * @param parentId
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<PmsProductCategory> list(Long parentId,Integer pageSize,Integer pageNum);

    int delete(Long id);

    PmsProductCategory getItem(Long id);

    int update(Long id,PmsProductCategoryParam param);
}
