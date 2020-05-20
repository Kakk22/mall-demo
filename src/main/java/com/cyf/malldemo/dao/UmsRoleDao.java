package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.UmsMenu;
import com.cyf.malldemo.mbg.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**自定义后台角色Dao
 * @author by cyf
 * @date 2020/5/20.
 */
public interface UmsRoleDao {

    /**
     * 根据角色id查询对应的资源
     * @param RoleId
     * @return
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long RoleId);

    /**
     * 根据角色id查询菜单
     * @param roleId
     * @return
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
}
