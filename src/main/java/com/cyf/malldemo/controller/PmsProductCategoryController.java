package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by cyf
 * @date 2020/6/14.
 */
@Api(tags = "PmsProductCategoryController",description = "商品分类管理")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation("创建商品分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductCategoryParam param){
        int count = pmsProductCategoryService.create(param);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed("创建失败");
    }


}
