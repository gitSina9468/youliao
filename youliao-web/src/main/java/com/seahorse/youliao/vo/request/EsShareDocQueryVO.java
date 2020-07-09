package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.util.Date;

/**
* es  分享文档
* @author  gitsina
* @date   2020-06-23 03:09:37.503
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsShareDocQueryVO {

    /**
     * 分享id
     */
    @ApiModelProperty("分享id")
	private Integer shareId;

    /**
     * 分享给哪个用户
     */
    @ApiModelProperty("分享给哪个用户")
	private Integer shareTo;

    /**
     * 分享日期
     */
    @ApiModelProperty("分享日期")
	private Date shareDate;

    /**
     * 文档id
     */
    @ApiModelProperty("文档id")
	private Integer docId;

    /**
     * 文档有效期，单位秒，0表示无限
     */
    @ApiModelProperty("文档有效期，单位秒，0表示无限")
	private Integer validTime;

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
