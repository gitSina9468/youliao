package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* describe: 业务日志表
* @author : songqiang
* @date: 2019-01-15 07:11:15.561
**/
@Getter
@Setter
@ToString
public class BusinessLogDO {
    /**
     * 主键id
     */
    private String id;
    /**
     * 菜单
     */
    private String menu;
    /**
     * 按钮
     */
    private String button;
    /**
     * 目标类
     */
    private String targetName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 操作ip
     */
    private String host;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 返回参数
     */
    private String responseParams;
    /**
     * 操作结果 失败 成功
     */
    private String result;
    /**
     * 请求时间
     */
    private Date requestTime;
    /**
     * 相应时间
     */
    private Date responseTime;
    /**
     * 异常描述
     */
    private String exception;
    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作人
     */
    private String operationUser;
    /**
     * 操作类型 
     */
    private String operationType;
    /**
     * 操作时间
     */
    private Date createTime;
}