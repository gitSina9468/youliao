package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.util.Date;

/**
* es 文档索引
* @author  gitsina
* @date   2020-06-23 03:09:37.134
**/
@ApiModel
@Getter
@Setter
@ToString
public class EsDocQueryVO {

    /**
     * 
     */
    @ApiModelProperty("")
	private Integer docId;

    /**
     * 文档名称
     */
    @ApiModelProperty("文档名称")
	private String docName;

    /**
     * 文档大小
     */
    @ApiModelProperty("文档大小")
	private Long docSize;

    /**
     * 文档sha256
     */
    @ApiModelProperty("文档sha256")
	private String docSha256;

    /**
     * 文档创建时间
     */
    @ApiModelProperty("文档创建时间")
	private Date docCreateDate;

    /**
     * 文档创建人
     */
    @ApiModelProperty("文档创建人")
	private Integer docUserId;

    /**
     * 文件id
     */
    @ApiModelProperty("文件id")
	private String docFileId;

    /**
     * 
     */
    @ApiModelProperty("")
	private Integer docOpen;

    /**
     * 文档类型
     */
    @ApiModelProperty("文档类型")
	private String docType;

    /**
     * 文档标题
     */
    @ApiModelProperty("文档标题")
	private String docTitle;

    /**
     * 文档内容
     */
    @ApiModelProperty("文档内容")
	private String docContent;

    /**
     * 文档删除
     */
    @ApiModelProperty("文档删除")
	private Integer docDelete;

    /**
     * 文档修改时间
     */
    @ApiModelProperty("文档修改时间")
	private Date docModifyDate;

    /**
     * 文档索引
     */
    @ApiModelProperty("文档索引")
	private Integer docIndex;

    /**
     * 
     */
    @ApiModelProperty("")
	private String source;

    /**
     * 
     */
    @ApiModelProperty("")
	private String sourceUrl;

    /**
     * 文档状态
     */
    @ApiModelProperty("文档状态")
	private Integer docStatus;

    /**
     * 文档转换
     */
    @ApiModelProperty("文档转换")
	private Integer docConvert;

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
