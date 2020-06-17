package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.PmsProductParam;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
public interface PmsProductService {
    /**
     * 事务隔离级别 和传播级别
     * @param param
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam param);
}
