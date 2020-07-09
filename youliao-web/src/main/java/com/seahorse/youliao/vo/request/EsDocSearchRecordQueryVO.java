package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;

/**
* es 文档搜索
* @author  gitsina
* @date   2020-06-23 03:09:37.330
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsDocSearchRecordQueryVO {

    /**
     * 搜索id
     */
    @ApiModelProperty("搜索id")
	private Integer searchRecordId;

    /**
     * 搜索关键词
     */
    @ApiModelProperty("搜索关键词")
	private String searchKeyword;

    /**
     * 搜索后阅读了哪篇文档
     */
    @ApiModelProperty("搜索后阅读了哪篇文档")
	private Integer docId;

    /**
     * 搜索人
     */
    @ApiModelProperty("搜索人")
	private Integer userId;

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
