package com.seahorse.youliao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.SysMenuDO;

import java.util.List;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@Repository
public interface SysMenuDao extends BaseDao<SysMenuDO, Integer> {


    /**
     * 根据pid批量查询
     * @return
     */
    List<SysMenuDO> getListByPid(@Param("pid") Integer pid);

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    List<SysMenuDO> findMenuByUserId(@Param("userId") Integer userId);

    /**
     * 查询用户按钮权限
     * @param userId
     * @return
     */
    List<String> getPermissionsByUserId(@Param("userId") Integer userId);

    /**
     * 查询权限标识是否重复
     * @param permission
     * @param id
     * @return
     */
    int checkPermission(@Param("permission") String permission,@Param("id") Integer id);
}