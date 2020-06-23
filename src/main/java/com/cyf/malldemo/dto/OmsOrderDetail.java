package com.cyf.malldemo.dto;

import com.cyf.malldemo.mbg.model.OmsOrder;
import com.cyf.malldemo.mbg.model.OmsOrderItem;
import com.cyf.malldemo.mbg.model.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**订单具体信息
 * @author by cyf
 * @date 2020/6/23.
 */
@Getter
@Setter
@ToString
public class OmsOrderDetail extends OmsOrder {
    private List<OmsOrderItem> omsOrderItems;
    private List<OmsOrderOperateHistory> omsOrderOperateHistories;
}
