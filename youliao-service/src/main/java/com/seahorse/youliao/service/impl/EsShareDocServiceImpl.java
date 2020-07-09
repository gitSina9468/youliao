package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.EsShareDocDTO;
import com.seahorse.youliao.dao.EsShareDocDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.EsShareDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* es  分享文档
* @author  gitsina
* @date    2020-06-23 03:09:37.503
**/
@Service
public class EsShareDocServiceImpl extends BaseServiceImpl<EsShareDocDTO> implements EsShareDocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsShareDocServiceImpl.class);

	@Autowired
	private EsShareDocDao esShareDocDao;

}
