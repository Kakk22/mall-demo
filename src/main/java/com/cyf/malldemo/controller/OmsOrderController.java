package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.model.OmsOrder;
import com.cyf.malldemo.service.OmsOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/22.
 */
@RestController
@Api(tags = "OmsOrderController",description = "订单管理")
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;

    @GetMapping("getList")
    public CommonResult getList(OmsOrderQueryParam param,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        List<OmsOrder> list = omsOrderService.getList(param, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }
}
