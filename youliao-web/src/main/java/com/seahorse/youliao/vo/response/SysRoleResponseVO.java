package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
@ApiModel
@Getter
@Setter
@ToString
public class SysRoleResponseVO {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Integer id;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}