package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.seahorse.youliao.service.ScheduleJobLogService;
import com.seahorse.youliao.vo.response.ScheduleJobLogResponseVO;
import com.seahorse.youliao.vo.request.ScheduleJobLogQueryVO;
import com.seahorse.youliao.vo.response.ScheduleJobLogPageInfoVO;
import com.seahorse.youliao.service.entity.ScheduleJobLogDTO;

/**
* 定时任务执行日志
* @author  gitsina
* @date    2020-06-25 02:49:51.396
**/
@RestController
@Api(tags = "定时任务执行日志")
@RequestMapping("scheduleJobLog")
public class ScheduleJobLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobLogController.class);

	@Autowired
    private ScheduleJobLogService scheduleJobLogService;
    /**
     * 分页查询
     * @param scheduleJobLogQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public ScheduleJobLogPageInfoVO selectPageList(@RequestBody @Valid ScheduleJobLogQueryVO scheduleJobLogQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", scheduleJobLogQueryVO.toString());
        LOGGER.info(info);
        ScheduleJobLogDTO scheduleJobLog = BeanUtil.convert(scheduleJobLogQueryVO, ScheduleJobLogDTO.class);

        Page<ScheduleJobLogResponseVO> page = PageHelper.startPage(scheduleJobLogQueryVO.getPageNum(), scheduleJobLogQueryVO.getPageSize());
        List<ScheduleJobLogDTO> scheduleJobLogList = scheduleJobLogService.getList(scheduleJobLog);
        ScheduleJobLogPageInfoVO scheduleJobLogPageInfo = new ScheduleJobLogPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), scheduleJobLogPageInfo);
        List<ScheduleJobLogResponseVO> voList = BeanUtil.convert(scheduleJobLogList, ScheduleJobLogResponseVO.class);
        scheduleJobLogPageInfo.setList(voList);

        return scheduleJobLogPageInfo;
    }


}
