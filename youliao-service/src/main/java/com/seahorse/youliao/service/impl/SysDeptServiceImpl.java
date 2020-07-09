package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysDeptDao;
import com.seahorse.youliao.service.SysDeptService;
import com.seahorse.youliao.service.entity.SysDeptDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: 部门表
* @author : songqiang
* @date: 2020-01-17 09:23:44.242
**/
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptDTO> implements SysDeptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysDeptServiceImpl.class);
	
	@Autowired
	private SysDeptDao sysDeptDao;

}
