package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 数据字典分页
 * @author: Mr.Song
 * @create: 2019-12-15 20:48
 **/
@ApiModel
@Getter
@Setter
@ToString
public class BaseDictionaryPageInfoVO implements Serializable {
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
    private List<BaseDictionaryResponseVO> list;


}
