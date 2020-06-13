package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.UmsMenuNode;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/13.
 */
public interface UmsMenuService {

    List<UmsMenuNode> treeList();
}
