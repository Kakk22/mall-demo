package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.OmsOrderDao;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.mapper.OmsOrderMapper;
import com.cyf.malldemo.mbg.model.OmsOrder;
import com.cyf.malldemo.mbg.model.OmsOrderExample;
import com.cyf.malldemo.service.OmsOrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<OmsOrder> getList(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return omsOrderDao.getList(queryParam);
    }
}
