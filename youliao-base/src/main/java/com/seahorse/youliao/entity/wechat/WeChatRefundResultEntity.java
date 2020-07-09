package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信退款结果封装
 * @author: Mr.Song
 * @create: 2020-03-07 20:20
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatRefundResultEntity {

    /**
     * SUCCESS/FAIL
     */
    @XStreamAlias("result_code")
    private String resultCode;

    /**
     * 返回状态码
     */
    @XStreamAlias("return_code")
    public String returnCode;

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    public String returnMsg;

    /**
     * 公众账号ID
     */
    @XStreamAlias("appid")
    public String appId;

    /**
     * 商户号
     */
    @XStreamAlias("mch_id")
    public String mchId;

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    public String outTradeNo;
    /**
     * 微信订单号
     */
    @XStreamAlias("transaction_id")
    public String transactionId;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    public String nonceStr;

    /**
     * 随机字符串
     */
    public String sign;

    /**
     * 商户退款单号
     */
    @XStreamAlias("out_refund_no")
    public String outRefundNo;

    /**
     * 微信退款id
     */
    @XStreamAlias("refund_id")
    public String refundId;

    /**
     * 微信退款id
     */
    @XStreamAlias("refund_fee")
    public int refundFee;
    /**
     * 标价金额
     */
    @XStreamAlias("total_fee")
    public int totalFee;

    /**
     * 现金支付金额
     */
    @XStreamAlias("cash_fee")
    public int cashFee;

    /**
     * 应结退款金额
     */
    public Integer settlement_refund_fee;

    /**
     * 应结订单金额
     */
    public Integer settlement_total_fee;

    /**
     * 标价币种
     */
    public String fee_type;

    /**
     * 现金支付币种
     */
    public String cash_fee_type;

    /**
     * 现金退款金额
     */
    public Integer cash_refund_fee;

    /**
     * 代金券类型
     */
    public String coupon_type_$n;

    /**
     * 代金券退款总金额
     */
    public Integer coupon_refund_fee;

    /**
     * 单个代金券退款金额
     */
    public Integer coupon_refund_fee_$n;

    /**
     * 退款代金券使用数量
     */
    public Integer coupon_refund_count;

    /**
     * 退款代金券ID
     */
    public Integer coupon_refund_id_$n;


}
