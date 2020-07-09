package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
@Getter
@Setter
@ToString
public class SysUserDO {
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 性别
     */
    private String sex;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态:1正常,0禁用
     */
    private Boolean enabled;
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 岗位id
     */
    private Integer jobId;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后登录ip
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginTime;
    /**
     * 备注
     */
    private String remark;
}