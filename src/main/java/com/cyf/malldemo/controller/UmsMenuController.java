package com.cyf.malldemo.controller;


import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.UmsMenuNode;
import com.cyf.malldemo.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/13.
 */

@Api(tags = "UmsMenuController",description = "菜单模块")
@RequestMapping("/menu")
@RestController
public class UmsMenuController {
    @Autowired
    private UmsMenuService umsMenuService;

    @ApiOperation(value = "树状返回菜单列表")
    @GetMapping("/treeList")
    public CommonResult treeList(){
        List<UmsMenuNode> umsMenuNodes = umsMenuService.treeList();
        return CommonResult.success(umsMenuNodes);
    }
}
