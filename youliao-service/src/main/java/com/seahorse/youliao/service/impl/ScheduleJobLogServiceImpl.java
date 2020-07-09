package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.service.entity.ScheduleJobLogDTO;
import com.seahorse.youliao.dao.ScheduleJobLogDao;
import org.springframework.stereotype.Service;
import com.seahorse.youliao.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 定时任务执行日志
* @author  gitsina
* @date    2020-06-25 02:49:51.396
**/
@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogDTO> implements ScheduleJobLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobLogServiceImpl.class);

	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;

}
