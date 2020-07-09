package com.seahorse.youliao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.enums
 * @ClassName: PayStatusEnum
 * @Description: 支付状态枚举
 * @author:songqiang
 * @Date:2020-03-18 10:43
 **/
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    UNIFIED_ORDER(0,"下单"),

    PAYING(1,"支付中"),

    FAILURE(2,"支付失败"),

    SUCCESS(3,"支付成功");

    /**
     * 值
     */
    private Integer value;

    /**
     * 名称
     */
    private String name;

    /**
     * 根据value 查询
     * @param value
     * @return
     */
    public static String getNameByValue(Integer value) {

        PayStatusEnum[] payStatusEnums = PayStatusEnum.values();
        for (PayStatusEnum statusEnum : payStatusEnums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum.getName();
            }
        }
        return null;
    }


}
