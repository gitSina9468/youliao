package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.EsUploadFileDTO;
import com.seahorse.youliao.dao.EsUploadFileDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.EsUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* es 文档上传
* @author  gitsina
* @date    2020-06-23 03:09:37.684
**/
@Service
public class EsUploadFileServiceImpl extends BaseServiceImpl<EsUploadFileDTO> implements EsUploadFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsUploadFileServiceImpl.class);

	@Autowired
	private EsUploadFileDao esUploadFileDao;

}
