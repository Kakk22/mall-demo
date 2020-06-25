package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.mbg.model.OmsCartItem;
import com.cyf.malldemo.service.OmsCartItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by cyf
 * @date 2020/6/25.
 */
@Api(tags = "OmsCartItemController",description = "购物车管理")
@RestController
@RequestMapping("/cart")
public class OmsCartItemController {
    @Autowired
    private OmsCartItemService omsCartItemService;

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("/add")
    public CommonResult add(@RequestBody OmsCartItem omsCartItem){
        int count = omsCartItemService.add(omsCartItem);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("添加失败");
    }
}
