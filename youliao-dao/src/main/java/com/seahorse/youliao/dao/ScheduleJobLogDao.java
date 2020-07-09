package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.ScheduleJobLogDO;

/**
* 定时任务执行日志
* @author  gitsina
* @date    2020-06-25 02:49:51.396
**/
@Repository
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogDO, Integer> {

}
