package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* 定时任务执行日志
* @author  gitsina
* @date 2020-06-25 02:49:51.396
**/
@Getter
@Setter
@ToString
public class ScheduleJobLogDO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 开始执行时间
     */
    private Date startTime;
    /**
     * 执行结束时间
     */
    private Date endTime;
    /**
     * 花费时间
     */
    private Integer costSeconds;
    /**
     * 操作结果 失败 成功
     */
    private String result;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
}
