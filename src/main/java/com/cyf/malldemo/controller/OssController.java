package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.OssCallbackResult;
import com.cyf.malldemo.dto.OssPolicyResult;
import com.cyf.malldemo.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**oss 相关操作接口
 * @author by cyf
 * @date 2020/8/22.
 */
@Api(tags = "OssController",description = "Oss管理")
@RestController
@RequestMapping(value = "/aliyun/oss")
public class OssController {
    @Autowired
    private OssService ossService;


    @ApiOperation("oss上传签名生成")
    @GetMapping(value = "/policy")
    public CommonResult policy(){
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @PostMapping(value = "callback")
    public CommonResult callback(HttpServletRequest request){
        System.out.println("callback");
        OssCallbackResult callback = ossService.callback(request);
        return CommonResult.success(callback);
    }
}
