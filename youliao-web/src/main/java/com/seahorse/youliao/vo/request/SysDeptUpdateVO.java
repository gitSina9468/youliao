package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
/**
* describe: 部门表
* @author : songqiang
* @date: 2020-01-17 09:23:44.242
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysDeptUpdateVO {
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
	private Integer id;
    /**
     * 父部门id
     */
    @ApiModelProperty("父部门id")
	private Integer pid;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称不能为空")
	private String deptName;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
	private Integer sort;
    /**
     * 部门状态:1正常,0停用
     */
    @ApiModelProperty("部门状态:1正常,0停用")
	private Boolean enabled;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
	private String createBy;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
