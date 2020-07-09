package com.seahorse.youliao.service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.entity
 * @ClassName: PageDTO
 * @Description: 分页实体
 * @author:songqiang
 * @Date:2020-01-03 10:15
 **/
@Getter
@Setter
@ToString
public class PageDTO<T> implements Serializable {


    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 结果集
     */
    private List<T> list;
}
