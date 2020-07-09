package com.seahorse.youliao.constant;

/**
 * @description: 微信支付接口url地址
 * @author: Mr.Song
 * @create: 2020-03-05 21:34
 **/
public class WeChatPayApiURL {

    /**
     * 微信支付统一接口(POST)
     */
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信退款接口(POST)
     */
    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 订单查询接口(POST)
     */
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     *  关闭订单接口(POST)
     */
    public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    /**
     * 退款查询接口(POST)
     */
    public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 对账单接口(POST)
     */
    public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    /**
     * 短链接转换接口(POST)
     */
    public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    /**
     * 接口调用上报接口(POST)
     */
    public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
}
