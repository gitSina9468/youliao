package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* describe: 系统访问记录
* @author : songqiang
* @date: 2020-01-17 09:23:45.040
**/
@Getter
@Setter
@ToString
public class SysLoginLogDO {
    /**
     * 访问ID
     */
    private Integer id;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 登录IP地址
     */
    private String ip;
    /**
     * 登录地
     */
    private String loginLocation;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录状态 0成功 1失败
     */
    private Integer status;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 访问时间
     */
    private Date loginTime;
}