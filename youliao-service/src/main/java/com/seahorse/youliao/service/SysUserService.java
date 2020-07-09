 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.SysMenuDTO;
 import com.seahorse.youliao.service.entity.SysUserDTO;

 import java.util.List;

 /**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
public interface SysUserService  extends BaseService<SysUserDTO> {


    /**
     * 新增用户
     * @param userDTO
     * @param selectedRoles
     * @return
     */
    boolean insertUser(SysUserDTO userDTO, String selectedRoles);

    /**
     * 更新用户
     * @param userDTO
     * @param selectedRoles
     * @return
     */
    boolean updateUser(SysUserDTO userDTO, String selectedRoles);

     /**
      * 批量更新用户部门id
      * @param deptId
      * @param userIdList
      * @return
      */
    boolean updateDeptIdByUserIds(Integer deptId, List<String> userIdList);

     /**
      * 批量 更新用户状态
      * @param status
      * @param ids
      * @return
      */
     boolean frozenBatch(boolean status, String ids);

     /**
      * 删除用户部门关系
      * @param userId
      * @return
      */
     boolean deleteUserDept(Integer userId);

     /**
      * 批量删除用户部门关系
      * @param userIds
      * @return
      */
     boolean deleteUserDeptBatch(String userIds);

     /**
      * 查询用户权限
      * @return
      */
     List<SysMenuDTO> getUserNav(String userName);

     /**
      * 查询用户信息
      * @param username
      * @return
      */
     SysUserDTO findByUsername(String username);

     /**
      * 修改密码
      * @param userName
      * @param password
      * @return
      */
     boolean changePassword(String userName, String password);
 }
