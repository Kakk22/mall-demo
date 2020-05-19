package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.mbg.mapper.UmsRoleMapper;
import com.cyf.malldemo.mbg.model.UmsRole;
import com.cyf.malldemo.mbg.model.UmsRoleExample;
import com.cyf.malldemo.service.UmsRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/19.
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Override
    public List<UmsRole> listAllRole() {
        UmsRoleExample umsRoleExample = new UmsRoleExample();
        List<UmsRole> umsRoles = umsRoleMapper.selectByExample(umsRoleExample);
        return umsRoles;
    }

    @Override
    public List<UmsRole> listRole(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return umsRoleMapper.selectByExample(new UmsRoleExample());
    }
}
