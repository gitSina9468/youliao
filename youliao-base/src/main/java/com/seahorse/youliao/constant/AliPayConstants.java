package com.seahorse.youliao.constant;

/**
 * @description: 支付宝支付常量
 * @author: Mr.Song
 * @create: 2020-03-07 19:58
 **/
public class AliPayConstants {

    private AliPayConstants() {
    }

    //-----------二维码支付回调状态------------------->
    /**
     * 扫码等待支付
     */
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";

    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 扫码支付成功
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    /**
     * 交易结束，不可退款
     */
    public static final String TRADE_FINISHED = "TRADE_FINISHED";

    //<-----------二维码支付回调状态-------------------


    //-----------------二维码下单状态------------------>
    /**
     * 成功
     */
    public static final String SUCCESS = "10000";
    /**
     * 用户支付中
     */
    public static final String PAYING = "10003";
    /**
     * 失败
     */
    public static final String FAILED = "40004";
    /**
     * 系统异常
     */
    public static final String ERROR = "20000";

    //<---------------二维码下单状态---------------------
}
