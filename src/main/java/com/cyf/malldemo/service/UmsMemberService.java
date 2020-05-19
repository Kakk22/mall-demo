package com.cyf.malldemo.service;

import com.cyf.malldemo.common.CommonResult;

/**
 * @author by cyf
 * @date 2020/5/16.
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     * @param telephone
     * @return
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 校验验证码
     * @param telePhone
     * @param code
     * @return
     */
    CommonResult verifyAuthCode(String telePhone,String code);



}
