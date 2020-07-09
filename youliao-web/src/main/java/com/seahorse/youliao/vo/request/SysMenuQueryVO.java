package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.util.Date;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@ApiModel
@Getter
@Setter
@ToString
public class SysMenuQueryVO {
	
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
     * 组件名称
     */
    @ApiModelProperty("组件名称")
	private String component;

    /**
     * 请求地址
     */
    @ApiModelProperty("请求地址")
	private String path;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
	private Integer pid;

    /**
     * 类型:1目录,2菜单,3按钮
     */
    @ApiModelProperty("类型:1目录,2菜单,3按钮")
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
