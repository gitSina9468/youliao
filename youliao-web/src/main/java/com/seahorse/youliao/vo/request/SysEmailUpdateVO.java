package com.seahorse.youliao.vo.request;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
* 邮件发送
* @author  sq
* @date    2020-05-09 03:24:02.125
**/
@ApiModel
@Data
public class SysEmailUpdateVO {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
	private Integer id;
    /**
     * 收件地址
     */
    @ApiModelProperty("收件地址")
    @NotBlank(message = "收件地址不能为空")
	private String receiveEmail;
    /**
     * 主题
     */
    @ApiModelProperty("主题")
    @NotBlank(message = "主题不能为空")
	private String subject;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
	private String content;
    /**
     * 附件地址
     */
    @ApiModelProperty("附件地址")
	private List<String> list;

}
