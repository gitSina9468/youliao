package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信查询参数封装
 * @author: Mr.Song
 * @create: 2020-03-07 19:54
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatQueryEntity {


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


}
