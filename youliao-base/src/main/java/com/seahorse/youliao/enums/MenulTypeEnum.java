package com.seahorse.youliao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 菜单枚举类型
 * @author: Mr.Song
 * @create: 2020-02-22 11:38
 **/
@Getter
@AllArgsConstructor
public enum MenulTypeEnum {

    ROOT_MENU(1,"一级菜单"),

    SUB_MENU(2,"子菜单"),

    BUTTON(3,"按钮权限");

    /**
     * 值
     */
    private Integer value;

    /**
     * 名称
     */
    private String name;


}
