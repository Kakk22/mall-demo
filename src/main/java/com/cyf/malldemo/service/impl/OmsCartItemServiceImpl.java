package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.CartProductDao;
import com.cyf.malldemo.dto.CartProduct;
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
    @Autowired
    private CartProductDao cartProductDao;

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
        if (existCartItem == null) {
            //为空
            omsCartItem.setCreateDate(new Date());
            count = omsCartItemMapper.insert(omsCartItem);
        } else {
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
        if (cartItem.getProductSkuId() != null) {
            omsCartItemExample.createCriteria().andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> itemList = omsCartItemMapper.selectByExample(omsCartItemExample);
        if (!CollectionUtils.isEmpty(itemList)) {
            return itemList.get(0);
        }
        return null;
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andIdIn(ids);
        int count = omsCartItemMapper.updateByExampleSelective(omsCartItem, example);
        return count;
    }

    @Override
    public int clear(Long memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return omsCartItemMapper.updateByExampleSelective(omsCartItem, example);
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<OmsCartItem> itemList = omsCartItemMapper.selectByExample(example);
        return itemList;
    }

    @Override
    public int updateQuantity(Long memberId, Long id, Integer quantity) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
                                .andIdEqualTo(id)
                                .andDeleteStatusEqualTo(0);
        return omsCartItemMapper.updateByExampleSelective(omsCartItem, example);
    }

    @Override
    public CartProduct getProduct(Long productId) {
        return cartProductDao.getProduct(productId);
    }

    @Override
    public int updateArr(OmsCartItem omsCartItem) {
        //更新商品规格信息，删除原来重新添加
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(omsCartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        omsCartItemMapper.updateByPrimaryKeySelective(updateCart);
        //重新添加
        omsCartItem.setId(null);
        int count = add(omsCartItem);
        return count;
    }
}
