package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;


/**
* describe: 岗位信息表
* @author : songqiang
* @date: 2020-01-17 09:23:44.813
**/
@ApiModel
@Getter
@Setter
@ToString
public class SysJobPageInfoVO  implements Serializable  {
    /**
    * 当前页
    **/
    @ApiModelProperty("当前页")
    private Integer pageNum;
    /**
    * 每页条数
    **/
    @ApiModelProperty("每页的数量")
    private Integer pageSize;
    /**
    * 总记录数
    **/
    @ApiModelProperty("总记录数")
    private Long total;
   /**
    * 结果集
    **/
    @ApiModelProperty("结果集")
    private List<SysJobResponseVO> list;


}
