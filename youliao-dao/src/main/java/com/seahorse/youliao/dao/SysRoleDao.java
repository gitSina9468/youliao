package com.seahorse.youliao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.SysRoleDO;

import java.util.List;

/**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
@Repository
public interface SysRoleDao extends BaseDao<SysRoleDO, Integer> {


    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    List<SysRoleDO> getRolesByUserId(@Param("userId") Integer userId);
}