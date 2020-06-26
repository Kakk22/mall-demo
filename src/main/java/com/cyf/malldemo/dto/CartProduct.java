package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.PmsProduct;
import com.cyf.malldemo.mbg.model.PmsProductAttribute;
import com.cyf.malldemo.mbg.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**购物车中选择规格的商品信息
 * @author by cyf
 * @date 2020/6/26.
 */
@Getter
@Setter
public class CartProduct extends PmsProduct {
    List<PmsProductAttribute> productAttributeList;
    List<PmsSkuStock>  skuStockList;
}
