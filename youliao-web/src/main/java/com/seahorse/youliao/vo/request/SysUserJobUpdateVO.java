package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* describe: 用户与岗位关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.367
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysUserJobUpdateVO {
    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位ID")
	private Integer jobId;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
	private Integer userId;

}
