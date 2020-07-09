package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* describe: 角色和菜单关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.934
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysRoleMenuUpdateVO {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
	private Integer menuId;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
	private Integer roleId;

}
