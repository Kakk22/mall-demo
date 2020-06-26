package com.cyf.malldemo.dao;

import com.cyf.malldemo.dto.CartProduct;
import org.apache.ibatis.annotations.Param;

/**
 * @author by cyf
 * @date 2020/6/26.
 */
public interface CartProductDao {
    CartProduct getProduct(@Param("id") Long productId);
}
