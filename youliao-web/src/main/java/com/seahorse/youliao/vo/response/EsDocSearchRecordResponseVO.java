package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* es 文档搜索
* @author  gitsina
* @date    2020-06-23 03:09:37.330
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsDocSearchRecordResponseVO {

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

}
