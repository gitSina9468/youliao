package com.seahorse.youliao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.enums
 * @ClassName: PayTypeEnum
 * @Description: 支付类型枚举
 * @author:songqiang
 * @Date:2020-03-18 10:46
 **/
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    WECHAT(1,"微信"),

    ALIPAY(2,"支付宝");

    /**
     * 值
     */
    private Integer value;

    /**
     * 名称
     */
    private String name;
}
