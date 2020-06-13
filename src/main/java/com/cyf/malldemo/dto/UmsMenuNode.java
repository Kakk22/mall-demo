package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.UmsMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**后台菜单节点封装
 * @author by cyf
 * @date 2020/6/13.
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    private List<UmsMenuNode> children ;
}
