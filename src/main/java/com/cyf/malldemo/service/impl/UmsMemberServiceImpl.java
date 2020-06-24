package com.cyf.malldemo.service.impl;


import com.cyf.malldemo.service.UmsMemberCacheService;
import com.cyf.malldemo.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 会员管理实现类
 *
 * @author by cyf
 * @date 2020/5/16.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberCacheService umsMemberCacheService;

    /**
     * 获取验证码，存到redis
     *
     * @param telephone
     * @return
     */
    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        umsMemberCacheService.setAuthCode(telephone,sb.toString());
        return sb.toString();
    }



    /**
     * 校验验证码
     *
     * @param telephone
     * @param code
     * @return
    public CommonResult verifyAuthCode(String telephone, String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.failed("请输入验证码");
        }
        String result = (String)redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean equals = result.equals(code);
        if (equals) {
            return CommonResult.success("验证成功");
        } else {
            return CommonResult.failed("验证码错误");
        }
    }*/
}
