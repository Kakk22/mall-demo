package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.mbg.model.PmsProductAttribute;
import com.cyf.malldemo.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
@Api(tags = "PmsProductAttributeController",description = "商品属性管理")
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;

    @ApiOperation("根据分类查询属性列表或参数列表")
    @GetMapping("/list/{cid}")
    public CommonResult list(@PathVariable Long cid,
                             @RequestParam (value = "type")Integer type,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                             @RequestParam (value = "pageNum",defaultValue = "1")Integer pageNum) {
        List<PmsProductAttribute> list = pmsProductAttributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }
}
