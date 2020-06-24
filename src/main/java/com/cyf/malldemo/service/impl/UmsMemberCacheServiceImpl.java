package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.annotation.CacheException;
import com.cyf.malldemo.mbg.mapper.UmsMemberMapper;
import com.cyf.malldemo.mbg.model.UmsMember;
import com.cyf.malldemo.service.RedisService;
import com.cyf.malldemo.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by cyf
 * @date 2020/6/24.
 */
@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.authCode}")
    private String REDIS_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private Long  REDIS_EXPIRE_AUTH_CODE;


    @Override
    public void setMember(UmsMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
        redisService.set(key,member,REDIS_EXPIRE);
    }

    @Override
    public UmsMember getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        return (UmsMember) redisService.get(key);
    }

    @Override
    public void delMember(Long id) {
        UmsMember umsMember = umsMemberMapper.selectByPrimaryKey(id);
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + umsMember.getUsername();
        redisService.del(key);
    }

    @CacheException
    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_AUTH_CODE + ":" + telephone;
        redisService.set(key,authCode,REDIS_EXPIRE_AUTH_CODE);
    }
    @CacheException
    @Override
    public String getAuthCode(String telephone) {
        String key = REDIS_DATABASE + ":" + REDIS_AUTH_CODE + ":" + telephone;
        return (String) redisService.get(key);
    }
}
