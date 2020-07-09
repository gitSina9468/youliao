package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.math.BigDecimal;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@Getter
@Setter
@ToString
public class FmsRefundOrderDO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 支付id
     */
    private Long payId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 退款金额
     */
    private BigDecimal refundFee;
    /**
     * 退款状态 0 失败  1 成功
     */
    private Boolean refundStatus;
    /**
     * 退款人
     */
    private String refundUser;
    /**
     * 退款事由
     */
    private String refundReason;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
}