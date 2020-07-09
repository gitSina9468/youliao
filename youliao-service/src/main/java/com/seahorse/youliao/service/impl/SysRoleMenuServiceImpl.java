package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysRoleMenuDao;
import com.seahorse.youliao.dao.entity.SysRoleMenuDO;
import com.seahorse.youliao.service.SysRoleMenuService;
import com.seahorse.youliao.service.entity.SysRoleMenuDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* describe: 角色和菜单关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.934
**/
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDTO> implements SysRoleMenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	/**
	 * 保存用户角色收取南
	 *
	 * @param roleId
	 * @param permissionIds
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRolePermission(Integer roleId, String permissionIds) {

		//先删除角色菜单
		SysRoleMenuDO roleMenuDO = new SysRoleMenuDO();
		roleMenuDO.setRoleId(roleId);
		sysRoleMenuDao.delete(roleMenuDO);

		//批量添加角色菜单
		String[] menuIds = permissionIds.split(",");
		List<SysRoleMenuDO> list  = new ArrayList<>();
		for (String menuId : menuIds) {
			SysRoleMenuDO sysRoleMenuDO = new SysRoleMenuDO();
			sysRoleMenuDO.setRoleId(roleId);
			sysRoleMenuDO.setMenuId(Integer.valueOf(menuId));
			list.add(sysRoleMenuDO);
		}
		sysRoleMenuDao.insertInBatch(list);
	}
}
