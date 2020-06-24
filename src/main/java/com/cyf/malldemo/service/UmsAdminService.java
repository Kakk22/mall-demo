package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.UpdateAdminPasswordParam;
import com.cyf.malldemo.mbg.model.UmsAdmin;
import com.cyf.malldemo.mbg.model.UmsPermission;
import com.cyf.malldemo.mbg.model.UmsResource;
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

    /**
     * 获得用户资源
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 获得用户信息
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);

    UmsAdmin getItem(Long adminId);

    /**
     * 修改密码
     * @param updateAdminPasswordParam 传入密码参数
     * @return
     */
    int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam);


}
