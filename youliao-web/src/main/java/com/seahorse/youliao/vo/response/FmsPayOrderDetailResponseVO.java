package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@ApiModel
@Getter
@Setter
@ToString
public class FmsPayOrderDetailResponseVO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

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
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;

    /**
     * 商户订单号
     */
    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    /**
     * 流水号-支付订单号
     */
    @ApiModelProperty("流水号-支付订单号")
    private String transactionId;

    /**
     * 随机字符串
     */
    @ApiModelProperty("随机字符串")
    private String nonceStr;

    /**
     * 缴费人
     */
    @ApiModelProperty("缴费人")
    private String payUser;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 支付二维码路径
     */
    @ApiModelProperty("支付二维码路径")
    private String qrCodeUrl;

    /**
     * 缴费金额
     */
    @ApiModelProperty("缴费金额")
    private BigDecimal payFee;

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
     * 退费金额
     */
    @ApiModelProperty("退费金额")
    private BigDecimal refundFee;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 退费明细
     */
    private List<FmsRefundOrderResponseVO> list;
}