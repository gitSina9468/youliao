package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* describe: 用户和角色关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.660
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysUserRoleUpdateVO {
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
	private Integer roleId;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
	private Integer userId;

}
