package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信查询结果参数封装
 * @author: Mr.Song
 * @create: 2020-03-07 20:25
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatQueryResultEntity {

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
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    public String nonceStr;

    /**
     * 签名
     */
    @XStreamAlias("sign")
    public String sign;

    /**
     * 业务结果
     */
    @XStreamAlias("result_code")
    public String resultCode;

    /**
     * 商户订单号
     */
    @XStreamAlias("prepay_id")
    public String prepayId;

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    public String tradeType;

    /**
     * 交易类型
     */
    @XStreamAlias("code_url")
    public String codeUrl;

    public String out_trade_no;
    public String trade_state;
    public String trade_state_desc;
    public String err_code;
    public String err_code_des;
    public String device_info;
    public String openid;
    public String is_subscribe;
    public String trade_type;
    public String bank_type;
    public String total_fee;
    public String settlement_total_fee;
    public String fee_type;
    public String cash_fee;
    public String cash_fee_type;
    public String coupon_fee;
    public String coupon_count;
    public String coupon_id_0;
    public String coupon_id_1;
    public String coupon_type_$0;
    public String coupon_type_$1;
    public String coupon_type_$2;
    public String coupon_type_0;
    public String coupon_type_1;
    public String coupon_type_2;
    public String coupon_fee_$0;
    public String coupon_fee_$1;
    public String coupon_fee_$2;
    public String coupon_fee_0;
    public String coupon_fee_1;
    public String coupon_fee_2;
    public String transaction_id;
    public String attach;
    public String time_end;


}
