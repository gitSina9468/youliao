package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 用户菜单
 * @author: Mr.Song
 * @create: 2020-02-26 09:32
 **/
@ApiModel
@Getter
@Setter
@ToString
public class SysUserMenuResponseVO {


    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Integer id;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menu;

    /**
     * 是否外链
     */
    @ApiModelProperty("是否外链")
    private Boolean isFrame;

    /**
     * 组件
     */
    @ApiModelProperty("组件")
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
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 菜单状态:显示,隐藏
     */
    @ApiModelProperty("菜单状态:显示,隐藏")
    private Boolean visible;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}
