package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.EsDocDO;

/**
* es 文档索引
* @author  gitsina
* @date    2020-06-23 03:09:37.134
**/
@Repository
public interface EsDocDao extends BaseDao<EsDocDO, Integer> {

}
