package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.PmsProductAttribute;
import com.cyf.malldemo.mbg.model.PmsProductAttributeCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
@Getter
@Setter
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    private List<PmsProductAttribute> attributes;
}
