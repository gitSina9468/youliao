package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* describe: 角色和菜单关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.934
**/
@Getter
@Setter
@ToString
public class SysRoleMenuDO {
    /**
     * 菜单ID
     */
    private Integer menuId;
    /**
     * 角色ID
     */
    private Integer roleId;
}