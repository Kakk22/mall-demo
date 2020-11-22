package com.cyf.malldemo.domain;

import lombok.Data;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/11/17.
 */
@Data
public class EsProductRelatedInfo {
    private List<String> brandNames;
    private List<String> productCategoryNames;
    private List<ProductAttr> productAttrs;

    @Data
    public static class ProductAttr {
        private Long attrId;
        private String attrName;
        private List<String> attrValues;
    }
}

