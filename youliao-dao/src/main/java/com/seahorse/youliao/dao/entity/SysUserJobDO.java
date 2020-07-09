package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* describe: 用户与岗位关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.367
**/
@Getter
@Setter
@ToString
public class SysUserJobDO {
    /**
     * 岗位ID
     */
    private Integer jobId;
    /**
     * 用户ID
     */
    private Integer userId;
}