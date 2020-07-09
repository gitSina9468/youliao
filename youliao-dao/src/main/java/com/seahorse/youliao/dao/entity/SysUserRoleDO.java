package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* describe: 用户和角色关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.660
**/
@Getter
@Setter
@ToString
public class SysUserRoleDO {
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 用户ID
     */
    private Integer userId;
}