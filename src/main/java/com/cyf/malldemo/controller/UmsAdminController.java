package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.UmsAdminLoginParam;
import com.cyf.malldemo.dto.UpdateAdminPasswordParam;
import com.cyf.malldemo.mbg.model.UmsAdmin;
import com.cyf.malldemo.mbg.model.UmsAdminLoginLog;
import com.cyf.malldemo.mbg.model.UmsPermission;
import com.cyf.malldemo.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by cyf
 * @date 2020/5/18.
 */
@Api(tags = "UmsAdminController", description = "后台用户管理")
@Controller
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdmin, BindingResult result) {
        UmsAdmin register = umsAdminService.register(umsAdmin);
        if (register == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(register);
    }

    @ApiOperation("登录返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        System.out.println(umsAdminLoginParam.getUsername()+" "+umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>(8);
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有的权限,包括+—权限")
    @RequestMapping(value = "/permission/{adminId}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsPermission>> getPermission(@PathVariable long adminId){
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }

    @ApiOperation("修改用户密码")
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@RequestBody UpdateAdminPasswordParam param){
        int status = umsAdminService.updatePassword(param);
        int emptyParam = -1;
        int emptyUser = -2;
        int failPassword = -3;
        if (status > 0){
            return CommonResult.success("修改成功");
        }else if (status == emptyParam){
            return CommonResult.failed("参数不能为空");
        }else if (status == emptyUser){
            return CommonResult.failed("用户不存在");
        }else if (status == failPassword){
            return CommonResult.failed("旧密码错误");
        }else {
            return CommonResult.failed();
        }
    }


}
