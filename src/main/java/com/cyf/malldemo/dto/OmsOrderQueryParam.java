package com.cyf.malldemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**订单查询参数封装
 * @author by cyf
 * @date 2020/6/22.
 */
@Getter
@Setter
public class OmsOrderQueryParam {
    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("收货人姓名或者号码")
    private String receiverKeyword;
    @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订")
    private Integer status;
    @ApiModelProperty("订单类型：0->正常订单，1->秒杀订单")
    private Integer orderType;
    @ApiModelProperty("订单来源:0->PC ,1->APP")
    private Integer sourceType;
    @ApiModelProperty("订单创建时间")
    private String createTime;


}
