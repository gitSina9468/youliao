package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
* describe: 支付订单统计
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@ApiModel
@Getter
@Setter
@ToString
public class FmsPayOrderCountResponseVO {

    /**
     * 支付总金额
     */
    @ApiModelProperty("支付总金额")
    private BigDecimal payTotalFee;

    /**
     * 退款总金额
     */
    @ApiModelProperty("退款总金额")
    private BigDecimal refundTotalFee;

    /**
     * 支付笔数
     */
    @ApiModelProperty("支付笔数")
    private Integer size;

}