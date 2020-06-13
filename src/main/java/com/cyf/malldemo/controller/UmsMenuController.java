package com.cyf.malldemo.controller;


import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.UmsMenuNode;
import com.cyf.malldemo.mbg.model.UmsMenu;
import com.cyf.malldemo.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "返回子菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult list(@PathVariable Long parentId,
                             @ApiParam(value = "分页每页大小")
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                             @ApiParam(value = "分页页码",defaultValue = "1")
                             @RequestParam(value = "pageNum") Integer pageNum){
        List<UmsMenu> list = umsMenuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

}
