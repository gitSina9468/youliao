 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.SysMenuDTO;

 import java.util.List;

 /**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
public interface SysMenuService  extends BaseService<SysMenuDTO> {

     /**
      * 新增菜单
      * @param menuDTO
      * @return
      */
     boolean insertMenu(SysMenuDTO menuDTO);

     /**
      * 编辑菜单
      * @param menuDTO
      * @return
      */
     boolean updateMenu(SysMenuDTO menuDTO);

     /**
      * 批量删除菜单
      * @param ids
      * @return
      */
    boolean deleteBatch(List<Integer> ids);

     /**
      * 删除单条
      * @param id
      * @return
      */
     boolean deleteById(Integer id);

     /**
      * 查询用户菜单
      * @param userId
      * @return
      */
     List<SysMenuDTO> findMenuByUserId(Integer userId);

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<String> getPermissionsByUserId(Integer userId);

 }
