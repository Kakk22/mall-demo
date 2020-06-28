package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.CartProduct;
import com.cyf.malldemo.mbg.model.OmsCartItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/25.
 */
public interface OmsCartItemService {
    /**
     * 添加购物车
     *
     * @param omsCartItem
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int add(OmsCartItem omsCartItem);

    /**
     * 批量删除购物车中的商品
     *
     * @param memberId memberId
     * @param ids      cartItem id
     * @return 操作了多少条数据
     */
    int delete(Long memberId, List<Long> ids);

    /**
     * 清空购物车
     *
     * @param memberId 会员id
     * @return 操作了多少条数据
     */
    int clear(Long memberId);

    List<OmsCartItem> list(Long memberId);

    int updateQuantity(Long memberId, Long id, Integer quantity);

    CartProduct getProduct(Long productId);

    @Transactional(rollbackFor = Exception.class)
    int updateArr(OmsCartItem omsCartItem);
}
