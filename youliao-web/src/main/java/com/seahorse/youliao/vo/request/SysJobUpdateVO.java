package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
/**
* describe: 岗位信息表
* @author : songqiang
* @date: 2020-01-17 09:23:44.813
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysJobUpdateVO {
    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位ID")
	private Integer id;
    /**
     * 岗位编码
     */
    @ApiModelProperty("岗位编码")
    @NotBlank(message = "岗位编码不能为空")
	private String code;
    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    @NotBlank(message = "岗位名称不能为空")
	private String job;
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
	private Integer deptId;
    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty("状态（0正常 1停用）")
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
