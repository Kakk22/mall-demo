package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.UmsMenuNode;
import com.cyf.malldemo.mbg.model.UmsMenu;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/13.
 */
public interface UmsMenuService {
    /**
     * 树状返回菜单信息
     *
     * @return node
     */
    List<UmsMenuNode> treeList();

    /**分页查询子菜单
     * @return 子菜单
     */
    List<UmsMenu> list(Long parentId,Integer pageSize,Integer pageNum);
}
