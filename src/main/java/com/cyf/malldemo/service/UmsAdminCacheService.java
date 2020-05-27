package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.UmsAdmin;
import com.cyf.malldemo.mbg.model.UmsResource;

import java.util.List;


/**后台用户缓存
 * @author by cyf
 * @date 2020/5/25.
 */
public interface UmsAdminCacheService {
    UmsAdmin getAdmin(String username);

    void setAdmin(UmsAdmin umsAdmin);

    List<UmsResource> getResourceList(Long adminId);

    void setResourceList(Long adminId, List<UmsResource> resourceList);

    /**
     * 当角色资源信息改变时，删除缓存
     * @param roleId
     */
    void deleteResourceListByRole(Long roleId);

    /**
     * 删除后台用户缓存
     * @param adminId
     */
    void delAdmin(Long adminId);


}
