package com.cyf.malldemo.controller;


import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.mbg.model.UmsRole;
import com.cyf.malldemo.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/19.
 */
@Api(tags = "UmsRoleController", description = "用户角色")
@RestController
@RequestMapping("/role")
public class UmsRoleController {
    @Autowired
    private UmsRoleService umsRoleService;


    @ApiOperation("获取所有Role")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> umsRoles = umsRoleService.listAllRole();
        return CommonResult.success(umsRoles);
    }

    @ApiOperation("分页获取role")
    @RequestMapping(value = "/listRole",method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsRole>> listRole(@RequestParam(value = "pageNum", defaultValue = "1")
                                                      @ApiParam("页码") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "2")
                                                      @ApiParam("每页数量") Integer pageSize) {
        List<UmsRole> umsRoles = umsRoleService.listRole(pageNum, pageSize);
        CommonPage<UmsRole> umsRoleCommonPage = CommonPage.restPage(umsRoles);
        return CommonResult.success(umsRoleCommonPage);
    }
}
