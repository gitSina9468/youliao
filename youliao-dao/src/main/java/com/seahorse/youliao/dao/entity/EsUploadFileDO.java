package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* es 文档上传
* @author  gitsina
* @date 2020-06-23 03:09:37.684
**/
@Getter
@Setter
@ToString
public class EsUploadFileDO {
    /**
     * 
     */
    private String fileId;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件sha256
     */
    private String sha256;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件描述
     */
    private String fileDesc;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原始文件名
     */
    private String originalFileName;
}
