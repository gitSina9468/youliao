package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.util.Date;

/**
* 定时任务执行日志
* @author  gitsina
* @date   2020-06-25 02:50:38.505
**/
@ApiModel
@Getter
@Setter
@ToString
public class ScheduleJobLogQueryVO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
	private Integer id;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
	private String jobName;

    /**
     * 开始执行时间
     */
    @ApiModelProperty("开始执行时间")
	private Date startTime;

    /**
     * 执行结束时间
     */
    @ApiModelProperty("执行结束时间")
	private Date endTime;

    /**
     * 花费时间
     */
    @ApiModelProperty("花费时间")
	private Integer costSeconds;

    /**
     * 操作结果 失败 成功
     */
    @ApiModelProperty("操作结果 失败 成功")
	private String result;

    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
	private String exception;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
	private String createBy;

    /**
     * 当前页
     */
	@ApiModelProperty("当前页")
    @Min(value = 1,message="当前页不小于1")
    private Integer pageNum;
    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    @Min(value = 1,message="每页条数不能小于1")
    private Integer pageSize;
}
