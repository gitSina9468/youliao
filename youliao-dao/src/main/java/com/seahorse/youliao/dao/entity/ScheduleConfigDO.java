package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* 定时任务表配置
* @author  gitsina
* @date 2020-06-25 02:38:07.153
**/
@Getter
@Setter
@ToString
public class ScheduleConfigDO {
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 定时任务名称
     */
    private String jobName;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法
     */
    private String method;
    /**
     * cron 表达式
     */
    private String cron;
    /**
     * 状态:1正常,0停用
     */
    private Boolean enabled;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateBy;
}
