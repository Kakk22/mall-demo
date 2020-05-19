package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.UmsAdmin;
import com.cyf.malldemo.mbg.model.UmsPermission;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**后台管理员
 * @author by cyf
 * @date 2020/5/17.
 */
public interface UmsAdminService {

    UmsAdmin getAdminByUsername(String name);

    /**
     * 注册
     * @param umsAdmin
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdmin);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(Long adminId);


}
