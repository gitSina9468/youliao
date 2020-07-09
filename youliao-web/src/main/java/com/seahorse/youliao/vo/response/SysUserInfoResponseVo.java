package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @description: 登录用户信息
 * @author: Mr.Song
 * @create: 2020-02-26 15:53
 **/
@ApiModel
@Getter
@Setter
@ToString
public class SysUserInfoResponseVo {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer id;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
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
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
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
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 帐号状态:1正常,0禁用
     */
    @ApiModelProperty("帐号状态:1正常,0禁用")
    private Boolean enabled;

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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 最后登录ip
     */
    @ApiModelProperty("最后登录ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private Date loginTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 角色信息
     */
    @ApiModelProperty("角色信息")
    private List<SysRoleResponseVO> roles;

    /**
     * 按钮权限集合
     */
    @ApiModelProperty("按钮权限集合")
    private List<String> permissions;
}
