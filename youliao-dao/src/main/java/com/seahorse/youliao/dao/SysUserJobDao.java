package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.SysUserJobDO;

/**
* describe: 用户与岗位关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.367
**/
@Repository
public interface SysUserJobDao extends BaseDao<SysUserJobDO, Integer> {

}