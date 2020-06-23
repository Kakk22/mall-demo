package com.cyf.malldemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**订单发货参数
 * @author by cyf
 * @date 2020/6/23.
 */
@Getter
@Setter
public class OmsOrderDeliveryParam {
    @ApiModelProperty("订单id")
    private Long orderId;
    @ApiModelProperty("物流编号")
    private String deliverySn;
    @ApiModelProperty("物流公司")
    private String deliveryCompany;
}
