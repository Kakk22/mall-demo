package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.SmsCouponParam;
import com.cyf.malldemo.mbg.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
public interface SmsCouponService {

    /**
     * @param name
     * @param type
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

    @Transactional(rollbackFor = Exception.class)
    int create(SmsCouponParam param);
}
