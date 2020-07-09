package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.ScheduleConfigDTO;
import com.seahorse.youliao.dao.ScheduleConfigDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.ScheduleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 定时任务表配置
* @author  gitsina
* @date    2020-06-25 02:38:07.153
**/
@Service
public class ScheduleConfigServiceImpl extends BaseServiceImpl<ScheduleConfigDTO> implements ScheduleConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfigServiceImpl.class);

	@Autowired
	private ScheduleConfigDao scheduleConfigDao;

}
