package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.mbg.model.OmsCartItem;
import com.cyf.malldemo.service.OmsCartItemService;
import com.cyf.malldemo.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/25.
 */
@Api(tags = "OmsCartItemController", description = "购物车管理")
@RestController
@RequestMapping("/cart")
public class OmsCartItemController {
    @Autowired
    private OmsCartItemService omsCartItemService;
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("/add")
    public CommonResult add(@RequestBody OmsCartItem omsCartItem) {
        int count = omsCartItemService.add(omsCartItem);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("添加失败");
    }

    @ApiOperation(value = "清空购物车")
    @PostMapping("/clear")
    public CommonResult clear() {
        int count = omsCartItemService.clear(umsMemberService.getCurrentMember().getId());
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation(value = "删除购物车某个商品")
    @PostMapping("/delete")
    public CommonResult add(@RequestParam List<Long> ids) {
        int count = omsCartItemService.delete(umsMemberService.getCurrentMember().getId(),ids);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("删除失败");
    }
}
