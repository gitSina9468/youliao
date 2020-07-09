package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysUserRoleDao;
import com.seahorse.youliao.service.SysUserRoleService;
import com.seahorse.youliao.service.entity.SysUserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 用户和角色关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.660
**/
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDTO> implements SysUserRoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

}
