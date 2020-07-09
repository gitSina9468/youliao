package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* describe: 岗位信息表
* @author : songqiang
* @date: 2020-01-17 09:23:44.813
**/
@Getter
@Setter
@ToString
public class SysJobDO {
    /**
     * 岗位ID
     */
    private Integer id;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位名称
     */
    private String job;
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 状态（0正常 1停用）
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