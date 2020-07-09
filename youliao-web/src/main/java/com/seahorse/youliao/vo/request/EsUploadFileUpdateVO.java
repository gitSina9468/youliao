package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* es 文档上传
* @author  gitsina
* @date    2020-06-23 03:09:37.684
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsUploadFileUpdateVO {
    /**
     * 
     */
    @ApiModelProperty("")
	private String fileId;
    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
	private String filePath;
    /**
     * 文件sha256
     */
    @ApiModelProperty("文件sha256")
	private String sha256;
    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
	private Integer fileSize;
    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
	private String fileType;
    /**
     * 文件描述
     */
    @ApiModelProperty("文件描述")
	private String fileDesc;
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
	private String fileName;
    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
	private String originalFileName;

}
