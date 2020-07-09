package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信支付配置参数封装
 * @author: Mr.Song
 * @create: 2020-03-07 19:55
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatPayConfigEntity {


    /**
     * 微信支付分配的公众账号ID（企业号corpid即为此appId）
     */
    @XStreamAlias("appid")
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串，长度要求在32位以内。推荐随机数生成算法
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 通过签名算法计算得出的签名值，详见签名生成算法
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     */
    @XStreamAlias("sign_type")
    private String signType = "MD5";

    /**
     * 商品ID
     */
    @XStreamAlias("product_id")
    private String productId;

    /**
     * 商品简单描述，该字段请按照规范传递，具体请见参数规定
     */
    @XStreamAlias("body")
    private String body;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 订单总金额，单位为分，详见支付金额
     */
    @XStreamAlias("total_fee")
    private int totalFee;

    /**
     * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
     */
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    @XStreamAlias("notify_url")
    private String notifyUrl = "/pay/webChatPay/payAction.html";

    /**
     * JSAPI 公众号支付
     * NATIVE 扫码支付
     * APP APP支付
     */
    @XStreamAlias("trade_type")
    private String tradeType = "NATIVE";

    /**
     * 订单生成时间
     */
    @XStreamAlias("time_start")
    private String timeStart;

    /**
     * 订单失效时间
     * 最短失效时间间隔大于1分钟
     */
    @XStreamAlias("time_expire")
    private String timeExpire;


}
