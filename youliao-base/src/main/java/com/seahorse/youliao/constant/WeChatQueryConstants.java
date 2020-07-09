package com.seahorse.youliao.constant;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.constant
 * @ClassName: WeChatQueryConstants
 * @Description: 微信支付查询结果
 * 详细描述 见官网 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_2
 * @author:songqiang
 * @Date:2020-03-24 10:30
 **/
public class WeChatQueryConstants {


    /**
     * 支付成功
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 转入退款
     */
    public static final String REFUND = "REFUND";

    /**
     * 未支付
     */
    public static final String NOTPAY = "NOTPAY";

    /**
     * 已关闭
     */
    public static final String CLOSED = "CLOSED";

    /**
     * 已撤销（付款码支付）
     */
    public static final String REVOKED = "REVOKED";

    /**
     * 用户支付中（付款码支付）
     */
    public static final String USERPAYING = "USERPAYING";

    /**
     * 支付失败(其他原因，如银行返回失败)
     */
    public static final String PAYERROR = "PAYERROR";

}
