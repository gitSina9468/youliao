package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysUserJobDao;
import com.seahorse.youliao.service.SysUserJobService;
import com.seahorse.youliao.service.entity.SysUserJobDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 用户与岗位关联表
* @author : songqiang
* @date: 2020-01-17 09:23:46.367
**/
@Service
public class SysUserJobServiceImpl extends BaseServiceImpl<SysUserJobDTO> implements SysUserJobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserJobServiceImpl.class);
	
	@Autowired
	private SysUserJobDao sysUserJobDao;

}
