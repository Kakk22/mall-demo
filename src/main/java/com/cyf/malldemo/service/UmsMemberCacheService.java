package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.UmsMember;

/**会员缓存
 * @author by cyf
 * @date 2020/6/24.
 */
public interface UmsMemberCacheService {
    /**
     * 设置用户缓存
     * @param member
     */
    void setMember(UmsMember member);

    /**
     * 获取用户缓存
     * @param username
     * @return
     */
    UmsMember getMember(String username);

    /**
     * 删除用户缓存
     * @param id
     */
    void delMember(Long id);

    /**
     * 设置验证码
     * @param telephone
     * @param authCode
     */
    void setAuthCode(String telephone,String authCode);

    /**
     * 获取验证码
     * @param telephone
     * @return
     */
    String getAuthCode(String telephone);

}
