package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.model.OmsOrder;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/22.
 */
public interface OmsOrderService {

    List<OmsOrder> getList(OmsOrderQueryParam queryParam,Integer pageSize, Integer pageNum);
}
