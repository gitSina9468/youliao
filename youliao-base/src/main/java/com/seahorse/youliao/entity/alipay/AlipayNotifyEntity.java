package com.seahorse.youliao.entity.alipay;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 支付宝回调参数
 * @author: Mr.Song
 * @create: 2020-03-07 19:30
 **/
@Getter
@Setter
public class AlipayNotifyEntity implements Serializable {

    /**
     * 该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss
     */
    private String gmtCreate;
    /**
     * 字符集
     */
    private String charset;
    /**
     * 卖家支付宝账号
     */
    private String sellerEmail;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等
     */
    private String subject;
    /**
     * 签名
     */
    private String sign;
    /**
     * 该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来
     */
    private String body;
    /**
     * 买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
     */
    private String buyerId;
    /**
     * 发票金额
     */
    private String invoiceAmount;
    /**
     * 通知id
     */
    private String notifyId;
    /**
     * 支付成功的各个渠道金额信息,array
     */
    private String fundBillList;
    /**
     * 通知类型
     */
    private String notifyType;
    /**
     * 交易目前所处的状态，见交易状态说明
     */
    private String tradeStatus;
    /**
     * 商家在交易中实际收到的款项
     */
    private String receiptAmount;
    /**
     * 用户在交易中支付的金额
     */
    private String buyerPayAmount;
    /**
     * appId
     */
    private String appId;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 卖家支付宝用户号
     */
    private String sellerId;
    /**
     * 该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss
     */
    private String gmtPayment;

    /**
     * 异步时间
     */
    private String notifyTime;
    /**
     * 版本
     */
    private String version;
    /**
     * 原支付请求的商户订单号
     */
    private String outTradeNo;
    /**
     * 本次交易支付的订单金额
     */
    private String totalAmount;
    /**
     * 支付宝交易凭证号
     */
    private String tradeNo;
    /**
     * 认证appId
     */
    private String authAppId;
    /**
     * 买家支付宝账号
     */
    private String buyerLogonId;
    /**
     * 点数
     */
    private String pointAmount;

    //--------------------------------------------->
    //以下参数可能是退款属性，待验证

    /**
     * 商户业务ID，主要是退款通知中返回退款申请的流水号
     */
    private String outBizNo;
    /**
     * 退款通知中，返回总退款金额，单位为元，支持两位小数
     */
    private BigDecimal refundFee;
    /**
     * 该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S
     */
    private Date gmtRefund;
    /**
     * 该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss
     */
    private Date gmtClose;
    /**
     * 公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。
     */
    private String passbackParams;

    //<----------------------------------------------
}
