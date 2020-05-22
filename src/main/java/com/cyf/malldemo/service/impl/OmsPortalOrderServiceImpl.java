package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.component.CancelOrderSender;
import com.cyf.malldemo.dto.OrderParam;
import com.cyf.malldemo.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台订单管理
 *
 * @author by cyf
 * @date 2020/5/22.
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {

        // TODO: 2020/5/22 执行一系列下单操作
        LOGGER.info("process generateOrder");
        //下单完成后，开启一个延迟消息，当用户没有付款操过一定时间时取消订单(orderId在下单后生成)
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null,"下单成功");
    }


    @Override
    public void cancelOrder(Long orderId) {

    }

    //设置订单取消时间
    private void sendDelayMessageCancelOrder(long orderId) {
        //设置超时时间,测试设置30秒
        Long delayTimes = 30 * 1000L;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId,delayTimes);

    }
}
