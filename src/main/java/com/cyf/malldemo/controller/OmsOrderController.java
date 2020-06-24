package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.MoneyInfoParam;
import com.cyf.malldemo.dto.OmsOrderDeliveryParam;
import com.cyf.malldemo.dto.OmsOrderDetail;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.model.OmsOrder;
import com.cyf.malldemo.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("分页获取所有订单")
    @GetMapping("getList")
    public CommonResult getList(OmsOrderQueryParam param,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        List<OmsOrder> list = omsOrderService.getList(param, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("批量删除订单")
    @PostMapping("delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids){
        int  count = omsOrderService.delete(ids);
        return count > 0? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation("批量关闭订单")
    @PostMapping("update/close")
    public CommonResult close(@RequestParam("ids") List<Long> ids,
                              @RequestParam("note") String note){
        int  count = omsOrderService.close(ids,note);
        return count > 0? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation("批量发货")
    @PostMapping("update/delivery")
    public CommonResult delivery(@RequestBody List<OmsOrderDeliveryParam> params){
        int  count = omsOrderService.delivery(params);
        return count > 0? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation("查询订单详细：订单信息，商品信息，操作记录")
    @PostMapping("{id}")
    public CommonResult detail(@PathVariable Long id){
        OmsOrderDetail omsOrderDetail = omsOrderService.detail(id);
        return CommonResult.success(omsOrderDetail);
    }

    @ApiOperation("修改订单价格")
    @PostMapping("update/moneyInfo")
    public CommonResult UpdateMoneyInfo(@RequestBody MoneyInfoParam param){
        int count = omsOrderService.UpdateMoneyInfo(param);
        return count > 0? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

}
