package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysUserDao;
import com.seahorse.youliao.dao.entity.SysUserDO;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.SysMenuService;
import com.seahorse.youliao.service.SysUserRoleService;
import com.seahorse.youliao.service.SysUserService;
import com.seahorse.youliao.service.entity.SysMenuDTO;
import com.seahorse.youliao.service.entity.SysUserDTO;
import com.seahorse.youliao.service.entity.SysUserRoleDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDTO> implements SysUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);
	
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleService userRoleService;

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 新增用户
	 *
	 * @param userDTO
	 * @param selectedRoles
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	@Override
	public boolean insertUser(SysUserDTO userDTO,String selectedRoles) {

		//加密用户密码
		String encrypt = new BCryptPasswordEncoder().encode(MD5.md5(userDTO.getPassword()));
		userDTO.setPassword(encrypt);
		//添加用户
		SysUserDO user = BeanUtil.convert(userDTO, SysUserDO.class);
		sysUserDao.insert(user);

		if(!StringUtils.isBlank(selectedRoles)) {
            String[] roleId = selectedRoles.split(",");
			//添加用户角色
			List<SysUserRoleDTO> list = new ArrayList<>();
			insertUserRole(roleId, user, list);
			userRoleService.insertInBatch(list);
		}
		return true;
	}

	/**
	 * 更新用户
	 *
	 * @param userDTO
	 * @param selectedRoles
	 * @return
	 */
	@Override
	public boolean updateUser(SysUserDTO userDTO, String selectedRoles) {


		//修改用户
		SysUserDO user = BeanUtil.convert(userDTO, SysUserDO.class);
		sysUserDao.update(user);

		if(!StringUtils.isBlank(selectedRoles)) {
			//先删除用户角色信息
			SysUserRoleDTO userRoleDTO = new SysUserRoleDTO();
			userRoleDTO.setUserId(user.getId());
			userRoleService.delete(userRoleDTO);

			String[] roleId = selectedRoles.split(",");
			//再添加用户角色
			List<SysUserRoleDTO> list = new ArrayList<>();
			insertUserRole(roleId, user, list);
			userRoleService.insertInBatch(list);
		}

		return true;
	}

	/**
	 * 批量更新用户部门id
	 *
	 * @param deptId
	 * @param userIdList
	 * @return
	 */
	@Override
	public boolean updateDeptIdByUserIds(Integer deptId, List<String> userIdList) {

		return sysUserDao.updateDeptIdByUserIds(deptId,userIdList) > 0;
	}

	/**
	 * 批量 更新用户状态
	 *
	 * @param status
	 * @param ids
	 * @return
	 */
	@Override
	public boolean frozenBatch(boolean status, String ids) {

		List<String> idList = Arrays.asList(ids.split(","));
		return sysUserDao.frozenBatch(status,idList) > 0;
	}

	/**
	 * 删除用户部门关系
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public boolean deleteUserDept(Integer userId) {

		List<String> list = new ArrayList<>();
		list.add(userId.toString());
		return sysUserDao.updateDeptIdByUserIds(null,list) > 0;
	}

	/**
	 * 批量删除用户部门关系
	 *
	 * @param userIds
	 * @return
	 */
	@Override
	public boolean deleteUserDeptBatch(String userIds) {

		List<String> list = Arrays.asList(userIds.split(","));
		return sysUserDao.updateDeptIdByUserIds(null,list) > 0;
	}

	/**
	 * 查询用户权限
	 *
	 * @return
	 */
	@Override
	public List<SysMenuDTO> getUserNav(String userName) {

		//获取当前用户信息
		SysUserDO userDO = sysUserDao.findByName(userName);
		return sysMenuService.findMenuByUserId(userDO.getId());
	}

	/**
	 * 查询用户信息
	 *
	 * @param username
	 * @return
	 */
	@Override
	public SysUserDTO findByUsername(String username) {

		SysUserDO user = sysUserDao.findByName(username);
		return BeanUtil.convert(user,SysUserDTO.class);
	}

	/**
	 * 修改密码
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public boolean changePassword(String userName, String password) {

		//查询用户信息
		SysUserDO userDO = sysUserDao.findByName(userName);
		if(userDO == null){
			throw new BusinessException("不存在此用户");
		}
		//加密用户密码
		String encrypt = new BCryptPasswordEncoder().encode(MD5.md5(password));
		SysUserDO modify = new SysUserDO();
		modify.setId(userDO.getId());
		modify.setPassword(encrypt);

		return sysUserDao.update(modify) > 0;
	}


	private void insertUserRole(String[] roleId, SysUserDO user, List<SysUserRoleDTO> list) {
		for (String id : roleId) {
			SysUserRoleDTO roleDTO = new SysUserRoleDTO();
			roleDTO.setRoleId(Integer.valueOf(id));
			roleDTO.setUserId(user.getId());
			list.add(roleDTO);
		}
	}
}
