package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.PmsProductAttributeCategoryItem;
import com.cyf.malldemo.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/16.
 */
@Api(tags = "PmsProductAttributeCategoryController",description = "商品属性分类管理")
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    private  PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @ApiOperation("获得所有商品属性分类及其下属性")
    @GetMapping("/listWithArr")
    public CommonResult listWithArr() {
        List<PmsProductAttributeCategoryItem> listWithAtt = pmsProductAttributeCategoryService.getListWithAtt();
        return CommonResult.success(listWithAtt);
    }
}
