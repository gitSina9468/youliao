 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.SysRoleDTO;

 import java.util.List;

 /**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
public interface SysRoleService  extends BaseService<SysRoleDTO> {


     /**
      * 查询用户角色
      * @param userId
      * @return
      */
    List<SysRoleDTO> getRolesByUserId(Integer userId);


}
