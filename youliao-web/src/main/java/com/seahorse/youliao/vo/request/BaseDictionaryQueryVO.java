package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * @description: 查询数据库字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:47
 **/
@ApiModel
@Getter
@Setter
@ToString
public class BaseDictionaryQueryVO {

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
     * 使用状态
     */
    @ApiModelProperty("使用状态")
    private Boolean status;


    /**
     * 当前页|必填
     */
    @ApiModelProperty("当前页")
    @Min(value = 1,message="当前页不小于1")
    private Integer pageNum;
    /**
     * 分页大小|必填
     */
    @ApiModelProperty("分页大小")
    @Min(value = 1,message="每页条数不能小于1")
    private Integer pageSize;
}
