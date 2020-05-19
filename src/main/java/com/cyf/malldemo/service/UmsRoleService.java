package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.UmsRole;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/19.
 */
public interface UmsRoleService {

    List<UmsRole>  listAllRole();


    List<UmsRole> listRole(Integer pageNum,Integer pageSize);
}
