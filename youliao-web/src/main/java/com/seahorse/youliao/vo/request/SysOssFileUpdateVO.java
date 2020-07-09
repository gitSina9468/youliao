package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
/**
* describe: oss文件管理
* @author : songqiang
* @date: 2020-03-16 02:55:41.341
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysOssFileUpdateVO {
    /**
     * 
     */
    @ApiModelProperty("")
	private Integer id;
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
	private String fileName;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
	private String fileUrl;
    /**
     * 文件大小 kb
     */
    @ApiModelProperty("文件大小 kb")
	private BigDecimal fileSize;
    /**
     * 文件类型 excel ppt pdf txt ...
     */
    @ApiModelProperty("文件类型 excel ppt pdf txt ...")
	private String type;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
	private String createBy;

}
