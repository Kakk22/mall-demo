package com.cyf.malldemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cyf.malldemo.dto.UmsMenuNode;
import com.cyf.malldemo.mbg.mapper.UmsMenuMapper;
import com.cyf.malldemo.mbg.model.UmsMenu;
import com.cyf.malldemo.mbg.model.UmsMenuExample;
import com.cyf.malldemo.service.UmsMenuService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by cyf
 * @date 2020/6/13.
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuMapper umsMenuMapper;

    /**
     * 以树形结构返回
     *
     * @return
     */
    @Override
    public List<UmsMenuNode> treeList() {
        //所有的菜单
        List<UmsMenu> menuList = umsMenuMapper.selectByExample(new UmsMenuExample());
        //将菜单组装成节点
        List<UmsMenuNode> nodes = menuList.stream()
                //找出所有父节点
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
        return nodes;
    }

    /**
     * 将UmsMenu 转化为UmsMenuNode 并设置children属性
     *
     * @param menu
     * @param menuList
     * @return
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtil.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsMenuExample example = new UmsMenuExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<UmsMenu> umsMenus = umsMenuMapper.selectByExample(example);
        return umsMenus;
    }
}
