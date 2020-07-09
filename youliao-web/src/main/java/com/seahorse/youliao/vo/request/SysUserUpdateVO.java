package com.seahorse.youliao.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;
/**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
@ApiModel()
@Getter
@Setter
@ToString
public class SysUserUpdateVO {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
	private Integer id;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
	private String userName;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
	private String nickName;
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    @NotBlank(message = "用户邮件不能为空")
	private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码不能为空")
	private String phone;
    /**
     * 生日
     */
    @ApiModelProperty("生日")
	private String birthday;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
	private String sex;
    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    @NotBlank(message = "用户头像不能为空")
	private String avatar;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
	private String password;
    /**
     * 确认密码
     */
    @ApiModelProperty("确认密码")
	private String confirmPassword;
    /**
     * 帐号状态:1正常,0禁用
     */
    @ApiModelProperty("帐号状态:1正常,0禁用")
	private Boolean enabled;
    /**
     * 选择的角色 id 集合字符串
     */
    @ApiModelProperty("角色id字符串")
    private String selectedroles;
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
	private Integer deptId;
    /**
     * 岗位id
     */
    @ApiModelProperty("岗位id")
	private Integer jobId;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
	private String createBy;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
	private String remark;

}
