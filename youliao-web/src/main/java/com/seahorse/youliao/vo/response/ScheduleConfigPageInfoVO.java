package com.seahorse.youliao.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;


/**
* describe: 定时任务表配置
* @author : gitsina
* @date: 2020-06-25 02:38:07.153
**/
@ApiModel
@Getter
@Setter
@ToString
public class ScheduleConfigPageInfoVO  implements Serializable  {
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
    private List<ScheduleConfigResponseVO> list;


}
