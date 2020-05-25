package com.cyf.malldemo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.cyf.malldemo.common.utils.JWTtokenUtil;
import com.cyf.malldemo.dao.UmsAdminRoleRelationDao;
import com.cyf.malldemo.dto.AdminUserDetails;
import com.cyf.malldemo.mbg.mapper.UmsAdminMapper;
import com.cyf.malldemo.mbg.model.*;
import com.cyf.malldemo.service.UmsAdminCacheService;
import com.cyf.malldemo.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/17.
 */

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private JWTtokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //查缓存
        UmsAdmin admin = umsAdminCacheService.getAdmin(username);
        if (admin!=null)
            return admin;
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            UmsAdmin umsAdmin = adminList.get(0);
            umsAdminCacheService.setAdmin(umsAdmin);
            return umsAdmin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }

    /**
     * 获取用户资源列表
     * @param adminId
     * @return
     */
    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //查缓存
        List<UmsResource> resourceList = umsAdminCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)){
            return resourceList;
        }
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)){
            umsAdminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            return new AdminUserDetails(admin,getResourceList(admin.getId()));
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
