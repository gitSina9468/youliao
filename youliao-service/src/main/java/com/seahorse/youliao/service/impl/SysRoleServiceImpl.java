package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysRoleDao;
import com.seahorse.youliao.dao.entity.SysRoleDO;
import com.seahorse.youliao.service.SysRoleService;
import com.seahorse.youliao.service.entity.SysRoleDTO;
import com.seahorse.youliao.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDTO> implements SysRoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);
	
	@Autowired
	private SysRoleDao sysRoleDao;

	/**
	 * 查询用户角色
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRoleDTO> getRolesByUserId(Integer userId) {

		List<SysRoleDO> list = sysRoleDao.getRolesByUserId(userId);
		return BeanUtil.convert(list,SysRoleDTO.class);
	}
}
