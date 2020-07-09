package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;
/**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysRoleUpdateVO {
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
	private Integer id;
    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
	private String roleCode;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
	private String roleName;
    /**
     * 角色权限字符串
     */
    @ApiModelProperty("角色权限字符串")
	private String permission;
    /**
     * 角色级别
     */
    @ApiModelProperty("角色级别")
	private Integer level;
    /**
     * 数据权限
     */
    @ApiModelProperty("数据权限")
	private String dataScope;
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
