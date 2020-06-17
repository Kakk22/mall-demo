package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.PmsProductParam;
import com.cyf.malldemo.service.PmsProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductParam param){
        int count = pmsProductService.create(param);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("创建失败");
    }
}
