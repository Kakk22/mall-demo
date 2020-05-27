package com.cyf.malldemo.service.impl;


import com.cyf.malldemo.annotation.CacheException;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.service.RedisService;
import com.cyf.malldemo.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private long AUTH_CODE_EXPIRE_SECONDS;

    /**
     * 获取验证码，存到redis
     *
     * @param telephone
     * @return
     */
    @CacheException
    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        //设置失效时间
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(sb.toString(), "获得验证码成功");
    }

    /**
     * 校验验证码
     *
     * @param telephone
     * @param code
     * @return
     */
    @CacheException
    @Override
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
    }
}
