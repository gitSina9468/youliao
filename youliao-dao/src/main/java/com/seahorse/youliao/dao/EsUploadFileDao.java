package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.EsUploadFileDO;

/**
* es 文档上传
* @author  gitsina
* @date    2020-06-23 03:09:37.684
**/
@Repository
public interface EsUploadFileDao extends BaseDao<EsUploadFileDO, String> {

}
