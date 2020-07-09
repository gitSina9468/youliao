package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.ScheduleConfigDO;

/**
* 定时任务表配置
* @author  gitsina
* @date    2020-06-25 02:38:07.153
**/
@Repository
public interface ScheduleConfigDao extends BaseDao<ScheduleConfigDO, Integer> {

}
