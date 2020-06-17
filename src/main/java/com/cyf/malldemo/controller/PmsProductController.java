package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.PmsProductParam;
import com.cyf.malldemo.dto.PmsProductQueryParam;
import com.cyf.malldemo.mbg.model.PmsProduct;
import com.cyf.malldemo.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
@Api(tags = "PmsProductController", description = "商品管理")
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService pmsProductService;

    @ApiOperation("新增商品")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductParam param){
        int count = pmsProductService.create(param);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("创建失败");
    }

    @ApiOperation("查询商品")
    @GetMapping("/list")
    public CommonResult list(PmsProductQueryParam queryParam,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        List<PmsProduct> list = pmsProductService.list(queryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }


    @ApiOperation("批量删除状态")
    @PostMapping("/update/deleteStatus")
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("deleteStatus") Integer deleteStatus){
        int count = pmsProductService.updateDeleteStatus(ids,deleteStatus);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation("更新新品状态")
    @PostMapping("/update/newStatus")
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus){
        int count = pmsProductService.updateNewStatus(ids, newStatus);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }


    @ApiOperation("更新审核状态")
    @PostMapping("/update/verifyStatus")
    public CommonResult updateverifyStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("verifyStatus") Integer nverifyStatus,
                                           @RequestParam("detail") String detail){
        int count = pmsProductService.updateVerifyStatus(ids, nverifyStatus, detail);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }
}
