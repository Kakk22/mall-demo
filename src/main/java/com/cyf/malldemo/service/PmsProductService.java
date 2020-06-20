package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.PmsProductParam;
import com.cyf.malldemo.dto.PmsProductQueryParam;
import com.cyf.malldemo.dto.PmsProductResult;
import com.cyf.malldemo.mbg.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
public interface PmsProductService {
    /**
     * 事务隔离级别 和传播级别
     *
     * @param param
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    int create(PmsProductParam param);


    List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, Integer pageSize, Integer pageNum);

    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    PmsProductResult getUpdateInfo(Long id);
}
