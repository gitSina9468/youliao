package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信退款参数封装
 * @author: Mr.Song
 * @create: 2020-03-07 20:21
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatRefundEntity {

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
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    public String nonceStr;

    /**
     * 随机字符串
     */
    public String sign;

    /**
     * 签名类型
     */
    @XStreamAlias("sign_type")
    public String signType = "MD5";

    /**
     * 商户退款单号
     */
    @XStreamAlias("out_refund_no")
    public String outRefundNo;

    /**
     * 订单金额
     */
    @XStreamAlias("total_fee")
    public Integer totalFee;

    /**
     *退款金额
     */
    @XStreamAlias("refund_fee")
    public Integer refundFee;

    /**
     *退款货币种类
     */
    public String refund_fee_type;

    /**
     * 退款原因
     */
    public String refund_desc;

    /**
     * 退款资源来源
     */
    public String refund_account;

    /**
     * 退款回调地址
     */
    public String notify_url;
}
