package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* es 文档索引
* @author  gitsina
* @date 2020-06-23 03:09:37.134
**/
@Getter
@Setter
@ToString
public class EsDocDO {
    /**
     * 
     */
    private Integer docId;
    /**
     * 文档名称
     */
    private String docName;
    /**
     * 文档大小
     */
    private Long docSize;
    /**
     * 文档sha256
     */
    private String docSha256;
    /**
     * 文档创建时间
     */
    private Date docCreateDate;
    /**
     * 文档创建人
     */
    private Integer docUserId;
    /**
     * 文件id
     */
    private String docFileId;
    /**
     * 
     */
    private Integer docOpen;
    /**
     * 文档类型
     */
    private String docType;
    /**
     * 文档标题
     */
    private String docTitle;
    /**
     * 文档内容
     */
    private String docContent;
    /**
     * 文档删除
     */
    private Integer docDelete;
    /**
     * 文档修改时间
     */
    private Date docModifyDate;
    /**
     * 文档索引
     */
    private Integer docIndex;
    /**
     * 
     */
    private String source;
    /**
     * 
     */
    private String sourceUrl;
    /**
     * 文档状态
     */
    private Integer docStatus;
    /**
     * 文档转换
     */
    private Integer docConvert;
}
