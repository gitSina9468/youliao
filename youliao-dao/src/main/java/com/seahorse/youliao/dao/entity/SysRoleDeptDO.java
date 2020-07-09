package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* describe: 角色和部门关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.758
**/
@Getter
@Setter
@ToString
public class SysRoleDeptDO {
    /**
     * 岗位ID
     */
    private Integer deptId;
    /**
     * 角色ID
     */
    private Integer roleId;
}