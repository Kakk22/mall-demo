package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.mbg.mapper.OmsCartItemMapper;
import com.cyf.malldemo.mbg.model.OmsCartItem;
import com.cyf.malldemo.mbg.model.OmsCartItemExample;
import com.cyf.malldemo.mbg.model.UmsMember;
import com.cyf.malldemo.service.OmsCartItemService;
import com.cyf.malldemo.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/25.
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
    @Autowired
    private UmsMemberService umsMemberService;
    @Autowired
    private OmsCartItemMapper omsCartItemMapper;
    @Override
    public int add(OmsCartItem omsCartItem) {
        int count;
        //获取当前会员
        UmsMember currentMember = umsMemberService.getCurrentMember();
        omsCartItem.setMemberId(currentMember.getId());
        omsCartItem.setMemberNickname(currentMember.getNickname());
        omsCartItem.setDeleteStatus(0);
        //根据会员id 商品名称 ，skuId查询是否有相同的商品
        OmsCartItem existCartItem = getCartItem(omsCartItem);
        if (existCartItem == null){
            //为空
            omsCartItem.setCreateDate(new Date());
            count = omsCartItemMapper.insert(omsCartItem);
        }else {
            //不为空
            existCartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + omsCartItem.getQuantity());
            count = omsCartItemMapper.updateByPrimaryKeySelective(existCartItem);
        }
        return count;
    }

    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample omsCartItemExample = new OmsCartItemExample();
        omsCartItemExample.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                                           .andProductIdEqualTo(cartItem.getProductId());
        if (cartItem.getProductSkuId()!=null){
            omsCartItemExample.createCriteria().andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> itemList = omsCartItemMapper.selectByExample(omsCartItemExample);
        if (!CollectionUtils.isEmpty(itemList)){
            return itemList.get(0);
        }
        return null;
    }
}
