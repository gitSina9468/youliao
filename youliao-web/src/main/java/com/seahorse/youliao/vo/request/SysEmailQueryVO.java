package com.seahorse.youliao.vo.request;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import java.util.Date;

/**
* 邮件发送
* @author  sq
* @date   2020-05-09 03:24:02.125
**/
@ApiModel
@Data
public class SysEmailQueryVO {

    /**
     * id
     */
    @ApiModelProperty("id")
	private Integer id;

    /**
     * 发送到邮件地址
     */
    @ApiModelProperty("发送到邮件地址")
	private String receiveEmail;

    /**
     * 主题
     */
    @ApiModelProperty("主题")
	private String subject;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
	private String content;

    /**
     * 附件地址
     */
    @ApiModelProperty("附件地址")
	private String attachFiles;

    /**
     * 发送时间
     */
    @ApiModelProperty("发送时间")
	private Date sendTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
	private String createBy;

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
