package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* describe: 部门表
* @author : songqiang
* @date: 2020-01-17 09:23:44.242
**/
@Getter
@Setter
@ToString
public class SysDeptDO {
    /**
     * 部门id
     */
    private Integer id;
    /**
     * 父部门id
     */
    private Integer pid;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 部门状态:1正常,0停用
     */
    private Boolean enabled;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
}