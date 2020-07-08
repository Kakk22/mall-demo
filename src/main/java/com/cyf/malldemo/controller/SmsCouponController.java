package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.SmsCouponParam;
import com.cyf.malldemo.mbg.model.SmsCoupon;
import com.cyf.malldemo.service.SmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/7/2.
 */
@Api(tags = "SmsCouponController", description = "优惠券管理")
@RestController
@RequestMapping("/sms")
public class SmsCouponController {
    @Autowired
    private SmsCouponService smsCouponService;

    @ApiOperation(value = "根据优惠券名称和类别分页获取优惠券")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "name") String name,
                             @RequestParam(value = "type") Integer type,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsCoupon> list = smsCouponService.list(name, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation(value = "创建优惠券")
    @PostMapping("/create")
    public CommonResult create(@RequestBody SmsCouponParam param) {
        int count = smsCouponService.create(param);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("添加失败");
    }
}
