package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
* describe: 业务日志表
* @author : songqiang
* @date: 2020-01-16 13:11:15.561
**/
@ApiModel
@Getter
@Setter
@ToString
public class BusinessLogQueryVO {

    /**
     * 菜单
     */
    @ApiModelProperty("菜单")
	private String menu;

    /**
     * 按钮
     */
    @ApiModelProperty("按钮")
	private String button;

    /**
     * 目标类
     */
    @ApiModelProperty("目标类")
	private String targetName;

    /**
     * 方法名称
     */
    @ApiModelProperty("方法名称")
	private String methodName;

    /**
     * 操作ip
     */
    @ApiModelProperty("操作ip")
	private String host;


    /**
     * 操作结果 失败 成功
     */
    @ApiModelProperty("操作结果 失败 成功")
	private String result;


    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
	private String operationUser;

    /**
     * 操作类型 
     */
    @ApiModelProperty("操作类型 ")
	private String operationType;

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
