package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.MoneyInfoParam;
import com.cyf.malldemo.dto.OmsOrderDeliveryParam;
import com.cyf.malldemo.dto.OmsOrderDetail;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/22.
 */
public interface OmsOrderService {

    List<OmsOrder> getList(OmsOrderQueryParam queryParam,Integer pageSize, Integer pageNum);

    int delete(List<Long> ids);
    @Transactional
    int close(List<Long> ids, String note);
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> params);

    OmsOrderDetail detail(Long id);
    @Transactional
    int UpdateMoneyInfo(MoneyInfoParam param);
}
