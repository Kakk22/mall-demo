package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.UmsRoleDao;
import com.cyf.malldemo.mbg.mapper.UmsMenuMapper;
import com.cyf.malldemo.mbg.mapper.UmsRoleMapper;
import com.cyf.malldemo.mbg.mapper.UmsRoleMenuRelationMapper;
import com.cyf.malldemo.mbg.mapper.UmsRoleResourceRelationMapper;
import com.cyf.malldemo.mbg.model.*;
import com.cyf.malldemo.service.UmsRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/19.
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;
    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuMapper umsMenuMapper;
    @Autowired
    private UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;


    @Override
    public List<UmsRole> listAllRole() {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        List<UmsRole> umsRoles = umsRoleMapper.selectByExample(umsRoleExample);
        return umsRoles;
    }

    @Override
    public List<UmsRole> listRole(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return umsRoleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public int createRole(UmsRole umsRole) {
        umsRole.setAdminCount(0);
        umsRole.setCreateTime(new Date());
        umsRole.setSort(0);
        return umsRoleMapper.insert(umsRole);
    }

    @Override
    public int deleteRole(long id) {
        return umsRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(long id, UmsRole umsRole) {
        umsRole.setId(id);
        return umsRoleMapper.updateByPrimaryKeySelective(umsRole);
    }

    /**
     * 更新角色资源
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //删除原来所有的资源
        UmsRoleResourceRelationExample example = new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleResourceRelationMapper.deleteByExample(example);
        //把新的资源加入
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation umsRoleResourceRelation = new UmsRoleResourceRelation();
            umsRoleResourceRelation.setRoleId(roleId);
            umsRoleResourceRelation.setResourceId(resourceId);
            umsRoleResourceRelationMapper.insert(umsRoleResourceRelation);
        }
        return resourceIds.size();
    }

    /**
     * 根据角色id获取拥有的菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        List<UmsMenu> menuListByRoleId = umsRoleDao.getMenuListByRoleId(roleId);
        return menuListByRoleId;
    }

    /**
     * 给角色分配菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //删除原来的菜单
        UmsRoleMenuRelationExample example = new UmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleMenuRelationMapper.deleteByExample(example);
        //添加新的菜单
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation umsRoleMenuRelation = new UmsRoleMenuRelation();
            umsRoleMenuRelation.setRoleId(roleId);
            umsRoleMenuRelation.setMenuId(menuId);
            umsRoleMenuRelationMapper.insert(umsRoleMenuRelation);
        }
        return menuIds.size();
    }

    /**
     * 获取角色的资源
     *
     * @param roleId
     * @return
     */
    @Override
    public List<UmsResource> listResource(Long roleId) {
        List<UmsResource> resourceList = umsRoleDao.getResourceListByRoleId(roleId);
        return resourceList;
    }
}
