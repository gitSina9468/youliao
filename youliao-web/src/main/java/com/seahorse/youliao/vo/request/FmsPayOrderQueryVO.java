package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@ApiModel
@Getter
@Setter
@ToString
public class FmsPayOrderQueryVO {

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
	private String orderNo;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
	private String productName;

    /**
     * 商户订单号
     */
    @ApiModelProperty("商户订单号")
	private String outTradeNo;

    /**
     * 缴费金额最小值
     */
    @ApiModelProperty("缴费金额最小值")
	private BigDecimal min;

    /**
     * 缴费金额最大值
     */
    @ApiModelProperty("缴费金额最大值")
	private BigDecimal max;

    /**
     * 缴费方式 1 微信 2 支付宝
     */
    @ApiModelProperty("缴费方式 1 微信 2 支付宝")
	private Integer payType;

    /**
     * 支付状态  0 下单 1 支付中 2 支付失败  3 支付成功 
     */
    @ApiModelProperty("支付状态  0 下单 1 支付中 2 支付失败  3 支付成功 ")
	private Integer payStatus;

    /**
     * 退款状态 false 未退款 true 已退款
     */
    @ApiModelProperty("退款状态 false 未退款 true 已退款")
    private Boolean orderRefundStatus;

    /**
     * 当前页
     */
	@ApiModelProperty("当前页")
    @Min(value = 1,message="当前页不小于1")
    private Integer pageNum;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    @Min(value = 1,message="每页条数不能小于1")
    private Integer pageSize;
}
