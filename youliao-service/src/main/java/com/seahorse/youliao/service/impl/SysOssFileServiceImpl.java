package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysOssFileDao;
import com.seahorse.youliao.service.SysOssFileService;
import com.seahorse.youliao.service.entity.SysOssFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* describe: oss文件管理
* @author : songqiang
* @date: 2020-03-16 02:55:41.341
**/
@Service
public class SysOssFileServiceImpl extends BaseServiceImpl<SysOssFileDTO> implements SysOssFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysOssFileServiceImpl.class);
	
	@Autowired
	private SysOssFileDao sysOssFileDao;

}
