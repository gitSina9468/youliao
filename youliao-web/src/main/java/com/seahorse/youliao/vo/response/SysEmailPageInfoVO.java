package com.seahorse.youliao.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.response
 * @ClassName: SysEmailPageInfoVO
 * @Description: TODO
 * @author:songqiang
 * @Date:2020-05-09 15:27
 **/
@ApiModel
@Data
public class SysEmailPageInfoVO implements Serializable {

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
    private List<SysEmailResponseVO> list;
}
