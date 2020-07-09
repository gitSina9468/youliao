package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request
 * @ClassName: UserRequestVO
 * @Description: 用户
 * @author:songqiang
 * @Date:2019-12-31 15:01
 **/
@ApiModel
@Getter
@Setter
@ToString
public class UserRequestVO {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private String age;

}
