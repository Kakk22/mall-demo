package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by cyf
 * @date 2020/5/16.
 */

@Api(tags = "UmsMemberController",description = "会员登录注册")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {

    @Autowired
    private UmsMemberService umsMemberService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode",method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String telephone){
        String authCode = umsMemberService.generateAuthCode(telephone);
        return CommonResult.success(authCode);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode){
        umsMemberService.register(username, password, telephone, authCode);
        return CommonResult.success("注册成功");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password){
        String token = umsMemberService.login(username, password);
        if (token == null){
            return CommonResult.failed("用户名或密码错误");
        }
        Map<String,String> map = new HashMap<>(2);
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }


    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public CommonResult register(@RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode){
        umsMemberService.updatePassword(telephone,password,authCode);
        return CommonResult.success("改修成功");
    }

}
