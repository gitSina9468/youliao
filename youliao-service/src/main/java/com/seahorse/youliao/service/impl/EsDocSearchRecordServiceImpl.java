package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.EsDocSearchRecordDTO;
import com.seahorse.youliao.dao.EsDocSearchRecordDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.EsDocSearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* es 文档搜索
* @author  gitsina
* @date    2020-06-23 03:09:37.330
**/
@Service
public class EsDocSearchRecordServiceImpl extends BaseServiceImpl<EsDocSearchRecordDTO> implements EsDocSearchRecordService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsDocSearchRecordServiceImpl.class);

	@Autowired
	private EsDocSearchRecordDao esDocSearchRecordDao;

}
