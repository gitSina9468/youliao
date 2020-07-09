package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
* describe: 订单退款
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@ApiModel()
@Getter
@Setter
@ToString
public class FmsPayOrderRefundVO {

    /**
     * 订单id
     */
    @ApiModelProperty("订单主键id")
    @NotNull(message = "订单主键id不能为空")
	private Long payId;

    /**
     * 退款金额
     */
    @ApiModelProperty("退款金额")
    @NotNull(message = "退款金额不能为空")
    @DecimalMin(value = "0.01",message = "退款金额最小0.01元")
	private BigDecimal refundFee;

    /**
     * 退款原因
     */
    @ApiModelProperty("退款原因")
    @NotBlank(message = "退款原因")
    private String refundReason;

}
