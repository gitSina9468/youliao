package com.seahorse.youliao.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 微信异步回调结果参数封装
 * @author: Mr.Song
 * @create: 2020-03-07 20:18
 **/
@Setter
@Getter
@ToString
@XStreamAlias("xml")
public class WeChatResultEntity {

    /**
     * SUCCESS/FAIL
     * SUCCESS表示商户接收通知成功并校验成功
     */
    @XStreamAlias("return_code")
    private String returnCode = "SUCCESS";

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    private String returnMsg = "OK";
}
