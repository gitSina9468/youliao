package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
* describe: 系统访问记录
* @author : songqiang
* @date: 2020-01-17 09:23:45.040
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysLoginLogUpdateVO {
    /**
     * 访问ID
     */
    @ApiModelProperty("访问ID")
	private Integer id;
    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
	private String loginName;
    /**
     * 登录IP地址
     */
    @ApiModelProperty("登录IP地址")
	private String ip;
    /**
     * 
     */
    @ApiModelProperty("")
	private String loginLocation;
    /**
     * 浏览器类型
     */
    @ApiModelProperty("浏览器类型")
	private String browser;
    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
	private String os;
    /**
     * 登录状态 0成功 1失败
     */
    @ApiModelProperty("登录状态 0成功 1失败")
	private Integer status;
    /**
     * 提示消息
     */
    @ApiModelProperty("提示消息")
	private String msg;
    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
	private Date loginTime;

}
