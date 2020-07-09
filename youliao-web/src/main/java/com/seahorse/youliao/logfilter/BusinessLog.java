package com.seahorse.youliao.logfilter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.logfilter
 * @ClassName: BusinessLog
 * @Description: 业务日志
 * @author:songqiang
 * @Date:2020-01-15 18:53
 **/
@Setter
@Getter
@ToString
public class BusinessLog implements Serializable {


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
     * 请求地址
     */
    private String host;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应参数
     */
    private String responseParams;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 描述信息
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
     * 生成时间
     */
    private Date createTime;
}
