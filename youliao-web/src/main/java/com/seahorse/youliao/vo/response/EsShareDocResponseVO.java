package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
* es  分享文档
* @author  gitsina
* @date    2020-06-23 03:09:37.503
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsShareDocResponseVO {

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

}
