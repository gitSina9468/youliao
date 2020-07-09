package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.BusinessLogDao;
import com.seahorse.youliao.service.entity.BusinessLogDTO;
import com.seahorse.youliao.service.BusinessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 业务日志表
* @author : songqiang
* @date: 2019-12-17 07:11:15.561
**/
@Service
public class BusinessLogServiceImpl extends BaseServiceImpl<BusinessLogDTO> implements BusinessLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessLogServiceImpl.class);
	
	@Autowired
	private BusinessLogDao businessLogDao;

}
