package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;

/**
* describe: 角色和部门关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.758
**/
@ApiModel
@Getter
@Setter
@ToString
public class SysRoleDeptQueryVO {
	
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
