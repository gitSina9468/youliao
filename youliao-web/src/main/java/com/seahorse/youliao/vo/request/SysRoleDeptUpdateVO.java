package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* describe: 角色和部门关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.758
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysRoleDeptUpdateVO {
    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位ID")
	private Integer deptId;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
	private Integer roleId;

}
