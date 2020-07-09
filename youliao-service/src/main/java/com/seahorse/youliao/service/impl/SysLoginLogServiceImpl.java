package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysLoginLogDao;
import com.seahorse.youliao.service.SysLoginLogService;
import com.seahorse.youliao.service.entity.SysLoginLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 系统访问记录
* @author : songqiang
* @date: 2020-01-17 09:23:45.040
**/
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogDTO> implements SysLoginLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysLoginLogServiceImpl.class);
	
	@Autowired
	private SysLoginLogDao sysLoginLogDao;

}
