package com.cyf.malldemo.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.cyf.malldemo.common.exception.Asserts;
import com.cyf.malldemo.common.utils.JWTtokenUtil;
import com.cyf.malldemo.dto.UmsMemberDetails;
import com.cyf.malldemo.mbg.mapper.UmsMemberLevelMapper;
import com.cyf.malldemo.mbg.mapper.UmsMemberMapper;
import com.cyf.malldemo.mbg.model.UmsMember;
import com.cyf.malldemo.mbg.model.UmsMemberExample;
import com.cyf.malldemo.mbg.model.UmsMemberLevel;
import com.cyf.malldemo.mbg.model.UmsMemberLevelExample;
import com.cyf.malldemo.service.UmsMemberCacheService;
import com.cyf.malldemo.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 会员管理实现类
 *
 * @author by cyf
 * @date 2020/5/16.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberService.class);
    @Autowired
    private UmsMemberCacheService umsMemberCacheService;
    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;
    @Autowired
    private JWTtokenUtil jwTtokenUtil;

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

    @Override
    public void register(String username, String password, String telephone, String authCode) {
        //验证验证码是否正确
        if (!verifyAuthCode(telephone,authCode)){
            Asserts.fail("验证码错误");
        }
        //查询是否已经注册,用户名相同或者是手机号码相同
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or(example.createCriteria().andPhoneEqualTo(telephone));
        List<UmsMember> umsMemberList = umsMemberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMemberList)){
            Asserts.fail("用户已存在");
        }
        //不存在，新增
        UmsMember umsMember = new UmsMember();
        umsMember.setCreateTime(new Date());
        umsMember.setUsername(username);
        //加密
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setPhone(telephone);
        umsMember.setStatus(1);
        //设置默认会员等级
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = umsMemberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)){
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        umsMemberMapper.insert(umsMember);
        umsMember.setPassword(null);
    }

    /**
     * 校验验证码
     *
     * @param telephone
     * @param code
     * @return
     */
    private Boolean verifyAuthCode(String telephone, String code) {
        if (StringUtils.isEmpty(code)) {
            Asserts.fail("请输入验证码");
        }
        String authCode = umsMemberCacheService.getAuthCode(telephone);
        return authCode.equals(code);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember umsMember = getByUsername(username);
        if (umsMember == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new UmsMemberDetails(umsMember);
    }

    private UmsMember getByUsername(String username) {
        UmsMember member = umsMemberCacheService.getMember(username);
        if (member != null){
            return member;
        }
        UmsMemberExample umsMemberExample = new UmsMemberExample();
        umsMemberExample.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> umsMemberList = umsMemberMapper.selectByExample(umsMemberExample);
        if (!CollectionUtils.isEmpty(umsMemberList)){
            umsMemberCacheService.setMember(umsMemberList.get(0));
            return umsMemberList.get(0);
        }
        return null;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码错误");
            }
            //身份认证
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //放入context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwTtokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常 : {}",e.getMessage());
        }
        return token;
    }

    /**
     * 获取当前登录会员
     * @return
     */
    @Override
    public UmsMember getCurrentMember() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UmsMemberDetails umsMemberDetails = (UmsMemberDetails)authentication.getPrincipal();
        return umsMemberDetails.getUmsMember();
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample memberExample = new UmsMemberExample();
        memberExample.createCriteria().andPhoneEqualTo(telephone);
        List<UmsMember> umsMemberList = umsMemberMapper.selectByExample(memberExample);
        if (CollectionUtils.isEmpty(umsMemberList)){
            Asserts.fail("用户不存在");
        }
        if (!verifyAuthCode(telephone,authCode)){
            Asserts.fail("验证码错误");
        }
        UmsMember member = umsMemberList.get(0);
        member.setPassword(passwordEncoder.encode(password));
        umsMemberMapper.updateByPrimaryKeySelective(member);
        //删除缓存
        umsMemberCacheService.delMember(member.getId());
    }
}
