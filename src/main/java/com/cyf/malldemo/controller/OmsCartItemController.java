package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.CartProduct;
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

    @ApiOperation(value = "获取会员的购物车列表")
    @GetMapping("/list")
    public CommonResult list() {
        List<OmsCartItem> list = omsCartItemService.list(umsMemberService.getCurrentMember().getId());
        return CommonResult.success(list);
    }

    @ApiOperation(value = "更新购物车商品数量")
    @PostMapping("/updateQuantity")
    public CommonResult updateQuantity(@RequestParam Long id,@RequestParam Integer quantity) {
        int count = omsCartItemService.updateQuantity(umsMemberService.getCurrentMember().getId(),id,quantity);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation(value = "获取购物车中某个商品的规格，用于重选")
    @GetMapping("/getProduct/{productId}")
    public CommonResult getProduct(@PathVariable Long productId) {
        CartProduct product = omsCartItemService.getProduct(productId);
        return CommonResult.success(product);
    }

    @ApiOperation(value = "修改商品的规格")
    @PostMapping("/updateArr")
    public CommonResult updateArr(@RequestBody OmsCartItem omsCartItem) {
        int count = omsCartItemService.updateArr(omsCartItem);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }
}
