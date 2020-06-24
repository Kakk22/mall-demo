package com.cyf.malldemo.service;

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
    String generateAuthCode(String telephone);

    /**
     * 会员注册
     * @param username 用户名
     * @param password 密码
     * @param telephone 手机号码
     * @param authCode 验证码
     */
    void register(String username,String password,String telephone,String authCode);



}
