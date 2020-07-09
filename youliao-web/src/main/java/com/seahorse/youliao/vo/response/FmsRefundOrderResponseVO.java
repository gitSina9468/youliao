package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.math.BigDecimal;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@ApiModel
@Getter
@Setter
@ToString
public class FmsRefundOrderResponseVO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 支付id
     */
    @ApiModelProperty("支付id")
    private Long payId;

    /**
     * 商户退款单号
     */
    @ApiModelProperty("商户退款单号")
    private String outRefundNo;

    /**
     * 退款金额
     */
    @ApiModelProperty("退款金额")
    private BigDecimal refundFee;

    /**
     * 退款状态 0 失败  1 成功
     */
    @ApiModelProperty("退款状态 0 失败  1 成功")
    private Boolean refundStatus;

    /**
     * 退款人
     */
    @ApiModelProperty("退款人")
    private String refundUser;

    /**
     * 退款事由
     */
    @ApiModelProperty("退款事由")
    private String refundReason;

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

}