package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysMenuUpdateVO {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
	private Integer id;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
	private String menu;
    /**
     * 是否外链
     */
    @ApiModelProperty("是否外链")
	private Boolean isFrame;
    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
	private String component;
    /**
     * 组件名称
     */
    @ApiModelProperty("组件名称")
	private String componentName;
    /**
     * 请求地址
     */
    @ApiModelProperty("请求地址")
	private String path;
    /**
     * 重定向
     */
    @ApiModelProperty("重定向")
	private String redirect;
    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
	private Integer pid;
    /**
     * 类型:1目录,2菜单,3按钮
     */
    @ApiModelProperty("类型:1目录,2菜单,3按钮")
    @NotNull(message = "菜单类型不能为空")
	private Integer type;
    /**
     * 菜单状态:显示,隐藏
     */
    @ApiModelProperty("菜单状态:显示,隐藏")
	private Boolean visible;
    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
	private String permission;
    /**
     * 缓存
     */
    @ApiModelProperty("缓存")
	private Boolean cache;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
	private String icon;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
	private Integer sort;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
	private String remark;

}
