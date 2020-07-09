package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysJobDao;
import com.seahorse.youliao.service.SysJobService;
import com.seahorse.youliao.service.entity.SysJobDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 岗位信息表
* @author : songqiang
* @date: 2020-01-17 09:23:44.813
**/
@Service
public class SysJobServiceImpl extends BaseServiceImpl<SysJobDTO> implements SysJobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysJobServiceImpl.class);
	
	@Autowired
	private SysJobDao sysJobDao;

}
