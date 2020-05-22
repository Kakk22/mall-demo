package com.cyf.malldemo.service;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**前台订单管理
 * @author by cyf
 * @date 2020/5/22.
 */
public interface OmsPortalOrderService {
    /**
     * 根据提交信息生成订单
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);
}
