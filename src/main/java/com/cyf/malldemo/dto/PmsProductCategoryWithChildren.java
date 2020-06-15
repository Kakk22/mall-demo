package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.PmsProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/15.
 */
@Getter
@Setter
public class PmsProductCategoryWithChildren extends PmsProductCategory {
    private List<PmsProductCategory> children;
}
