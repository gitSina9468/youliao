package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysRoleDeptDao;
import com.seahorse.youliao.service.SysRoleDeptService;
import com.seahorse.youliao.service.entity.SysRoleDeptDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 角色和部门关联表
* @author : songqiang
* @date: 2020-01-17 09:23:45.758
**/
@Service
public class SysRoleDeptServiceImpl extends BaseServiceImpl<SysRoleDeptDTO> implements SysRoleDeptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleDeptServiceImpl.class);
	
	@Autowired
	private SysRoleDeptDao sysRoleDeptDao;

}
