package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request
 * @ClassName: LoginRequestVO
 * @Description: 登录bean
 * @author:songqiang
 * @Date:2020-01-10 11:28
 **/
@ApiModel
@Getter
@Setter
@ToString
public class LoginRequestVO {

    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String imgCode;

    /**
     * 验证码唯一标识
     */
    @ApiModelProperty("uuid")
    private String uuid;
}
