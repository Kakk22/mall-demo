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


import java.util.Date;
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

    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return umsMenuMapper.insert(umsMenu);
    }

    /**
     * 修改等级
     * @param umsMenu
     */
    private void updateLevel(UmsMenu umsMenu) {
        //没有父菜单则设为0
        if (umsMenu.getParentId() == 0){
            umsMenu.setLevel(0);
        }else {
            //根据父菜单设计等级
            UmsMenu parentMenu = umsMenuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu != null){
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            }else {
                umsMenu.setLevel(0);
            }
        }
    }

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

    @Override
    public int delete(Long id) {
        return umsMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return umsMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        //方法将不为空的字段更新到数据库
        return umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
    }
}
