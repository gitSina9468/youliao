package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* es 文档搜索
* @author  gitsina
* @date 2020-06-23 03:09:37.330
**/
@Getter
@Setter
@ToString
public class EsDocSearchRecordDO {
    /**
     * 搜索id
     */
    private Integer searchRecordId;
    /**
     * 搜索关键词
     */
    private String searchKeyword;
    /**
     * 搜索后阅读了哪篇文档
     */
    private Integer docId;
    /**
     * 搜索人
     */
    private Integer userId;
}
