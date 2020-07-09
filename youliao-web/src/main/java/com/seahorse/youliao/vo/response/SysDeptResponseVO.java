package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* describe: 部门表
* @author : songqiang
* @date: 2020-01-17 09:23:44.242
**/
@ApiModel
@Getter
@Setter
@ToString
public class SysDeptResponseVO {

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Integer id;

    /**
     * 父部门id
     */
    @ApiModelProperty("父部门id")
    private Integer pid;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer sort;

    /**
     * 树形数据key
     */
    @ApiModelProperty("树形数据key")
    private String key;

    /**
     *  树形数据值
     */
    @ApiModelProperty("树形数据值")
    private String value;

    /**
     * 树形数据title
     */
    @ApiModelProperty("树形数据title")
    private String title;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


    /**
     * 子部门
     */
    @ApiModelProperty("子部门")
    private List<SysDeptResponseVO> children;

}