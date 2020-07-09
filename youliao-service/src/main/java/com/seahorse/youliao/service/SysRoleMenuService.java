 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.SysRoleMenuDTO;

/**
* describe: 角色和菜单关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.934
**/
public interface SysRoleMenuService  extends BaseService<SysRoleMenuDTO> {


    /**
     * 保存用户角色收取南
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermission(Integer roleId, String permissionIds);
}
