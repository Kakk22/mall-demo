package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.OmsOrderDao;
import com.cyf.malldemo.dao.OmsOrderOperateHistoryDao;
import com.cyf.malldemo.dto.OmsOrderDeliveryParam;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.mapper.OmsOrderMapper;
import com.cyf.malldemo.mbg.model.OmsOrder;
import com.cyf.malldemo.mbg.model.OmsOrderExample;
import com.cyf.malldemo.mbg.model.OmsOrderOperateHistory;
import com.cyf.malldemo.service.OmsOrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author by cyf
 * @date 2020/6/22.
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {
    @Autowired
    private OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsOrderDao omsOrderDao;
    @Autowired
    private OmsOrderOperateHistoryDao omsOrderOperateHistoryDao;

    @Override
    public List<OmsOrder> getList(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return omsOrderDao.getList(queryParam);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setDeleteStatus(1);
        OmsOrderExample omsOrderExample = new OmsOrderExample();
        omsOrderExample.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return omsOrderMapper.updateByExampleSelective(omsOrder,omsOrderExample);
    }

    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setStatus(4);
        OmsOrderExample omsOrderExample = new OmsOrderExample();
        omsOrderExample.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = omsOrderMapper.updateByExampleSelective(omsOrder,omsOrderExample);
        //添加操作记录
        List<OmsOrderOperateHistory> operateHistoryList  = ids.stream().map(orderId -> {
            OmsOrderOperateHistory omsOrderOperateHistory = new OmsOrderOperateHistory();
            omsOrderOperateHistory.setOrderId(orderId);
            omsOrderOperateHistory.setCreateTime(new Date());
            omsOrderOperateHistory.setNote("订单关闭"+note);
            omsOrderOperateHistory.setOperateMan("后台管理员");
            omsOrderOperateHistory.setOrderStatus(4);
            return omsOrderOperateHistory;
        }).collect(Collectors.toList());
        omsOrderOperateHistoryDao.insertList(operateHistoryList);
        return count;
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> params) {
        //批量发货
        int count = omsOrderDao.delivery(params);
        //操作记录
        List<OmsOrderOperateHistory> historyList = params.stream().map(deliveryParam -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderStatus(2);
            history.setOperateMan("系统管理员");
            history.setCreateTime(new Date());
            history.setNote("完成发货");
            history.setOrderId(deliveryParam.getOrderId());
            return history;
        }).collect(Collectors.toList());
        omsOrderOperateHistoryDao.insertList(historyList);
        return count;
    }
}
