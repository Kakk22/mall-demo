package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.OmsCartItem;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by cyf
 * @date 2020/6/25.
 */
public interface OmsCartItemService {
    @Transactional
    int add(OmsCartItem omsCartItem);
}
