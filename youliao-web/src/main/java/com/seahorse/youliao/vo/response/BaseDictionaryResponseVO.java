package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @description: 数据字典返回
 * @author: Mr.Song
 * @create: 2019-12-15 20:48
 **/
@ApiModel
@Getter
@Setter
@ToString
public class BaseDictionaryResponseVO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    private String code;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String name;

    /**
     * 字典类型编码
     */
    @ApiModelProperty("字典类型编码")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @ApiModelProperty("字典类型名称")
    private String typeName;

    /**
     * 删除状态
     */
    @ApiModelProperty("删除状态")
    private Boolean deleted;

    /**
     * 使用状态
     */
    @ApiModelProperty("使用状态")
    private Boolean status;
    /**
     * 系统状态
     */
    @ApiModelProperty("系统状态")
    private Boolean systemStatus;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 数据创建时间
     */
    @ApiModelProperty("数据创建时间")
    private Date createTime;

    /**
     * 数据更新时间
     */
    @ApiModelProperty("数据更新时间")
    private Date updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;
}
