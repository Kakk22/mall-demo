package com.cyf.malldemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**修改订单价格参数
 * @author by cyf
 * @date 2020/6/24.
 */
@Getter
@Setter
public class MoneyInfoParam {
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount;
    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountAmount;
}
