package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.EsDocDTO;
import com.seahorse.youliao.dao.EsDocDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.EsDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* es 文档索引
* @author  gitsina
* @date    2020-06-23 03:09:37.134
**/
@Service
public class EsDocServiceImpl extends BaseServiceImpl<EsDocDTO> implements EsDocService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsDocServiceImpl.class);

	@Autowired
	private EsDocDao esDocDao;

}
