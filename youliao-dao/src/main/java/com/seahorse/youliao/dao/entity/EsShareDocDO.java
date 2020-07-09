package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* es  分享文档
* @author  gitsina
* @date 2020-06-23 03:09:37.503
**/
@Getter
@Setter
@ToString
public class EsShareDocDO {
    /**
     * 分享id
     */
    private Integer shareId;
    /**
     * 分享给哪个用户
     */
    private Integer shareTo;
    /**
     * 分享日期
     */
    private Date shareDate;
    /**
     * 文档id
     */
    private Integer docId;
    /**
     * 文档有效期，单位秒，0表示无限
     */
    private Integer validTime;
}
