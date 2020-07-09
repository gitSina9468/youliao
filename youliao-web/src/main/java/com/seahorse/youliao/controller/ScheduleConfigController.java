package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.config.ScheduleTaskComponent;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.ScheduleConfigUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.seahorse.youliao.service.ScheduleConfigService;
import com.seahorse.youliao.vo.response.ScheduleConfigResponseVO;
import com.seahorse.youliao.vo.request.ScheduleConfigQueryVO;
import com.seahorse.youliao.vo.response.ScheduleConfigPageInfoVO;
import com.seahorse.youliao.service.entity.ScheduleConfigDTO;

/**
* 定时任务配置
* @author  songqiang
* @date    2020-06-24 10:45:00.296
**/
@RestController
@Api(tags = "定时任务配置")
@RequestMapping("/scheduleConfig")
public class ScheduleConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfigController.class);

	@Autowired
    private ScheduleConfigService scheduleConfigService;

	@Autowired
    private ScheduleTaskComponent scheduleTaskComponent;

    /**
     * 分页查询
     * @param scheduleConfigQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public ScheduleConfigPageInfoVO selectPageList(@RequestBody @Valid ScheduleConfigQueryVO scheduleConfigQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", scheduleConfigQueryVO.toString());
        LOGGER.info(info);
        ScheduleConfigDTO scheduleConfig = BeanUtil.convert(scheduleConfigQueryVO, ScheduleConfigDTO.class);

        Page<ScheduleConfigResponseVO> page = PageHelper.startPage(scheduleConfigQueryVO.getPageNum(), scheduleConfigQueryVO.getPageSize());
        List<ScheduleConfigDTO> scheduleConfigList = scheduleConfigService.getList(scheduleConfig);
        ScheduleConfigPageInfoVO scheduleConfigPageInfo = new ScheduleConfigPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), scheduleConfigPageInfo);
        List<ScheduleConfigResponseVO> voList = BeanUtil.convert(scheduleConfigList, ScheduleConfigResponseVO.class);
        scheduleConfigPageInfo.setList(voList);

        return scheduleConfigPageInfo;
    }

    /**
     * 添加任务
     * @return
     */
    @Log(type = Log.OperationType.ADD,button = "添加任务",menu = "定时任务管理")
    @ApiOperation("添加任务")
    @PostMapping("/insert")
    public void insertTask(@RequestBody @Valid ScheduleConfigUpdateVO vo){

        ScheduleConfigDTO dto = BeanUtil.convert(vo, ScheduleConfigDTO.class);
        dto.setCreateBy(SecurityUtils.getUsername());
        dto.setCreateTime(new Date());
        scheduleConfigService.insert(dto);
        //新增任务task
        scheduleTaskComponent.addTask(dto);
    }

    /**
     * 修改任务
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "修改任务",menu = "定时任务管理")
    @ApiOperation("修改任务")
    @PutMapping("/update")
    public void updateTask(@RequestBody @Valid ScheduleConfigUpdateVO vo){

        if(vo.getId() == null){
            throw new BusinessException("主键不能为空");
        }
        ScheduleConfigDTO dto = BeanUtil.convert(vo, ScheduleConfigDTO.class);
        dto.setUpdateBy(SecurityUtils.getUsername());
        dto.setUpdateTime(new Date());
        scheduleConfigService.update(dto);
        //更新任务task
        scheduleTaskComponent.updateTask(dto);
    }

    /**
     * 暂停任务
     */
    @Log(type = Log.OperationType.UPDATE,button = "暂停任务",menu = "定时任务管理")
    @ApiOperation("暂停任务")
    @GetMapping("/pause")
    public void pauseTask(@RequestParam Integer id){

        ScheduleConfigDTO dto = new ScheduleConfigDTO();
        dto.setId(id);
        dto.setEnabled(false);
        dto.setUpdateBy(SecurityUtils.getUsername());
        dto.setUpdateTime(new Date());
        scheduleConfigService.update(dto);
        //暂停任务task
        scheduleTaskComponent.pauseeTask(scheduleConfigService.getById(id).getJobName());
    }

    /**
     * 开启任务
     */
    @Log(type = Log.OperationType.UPDATE,button = "开启任务",menu = "定时任务管理")
    @ApiOperation("开启任务")
    @GetMapping("/start")
    public void startTask(@RequestParam Integer id){

        ScheduleConfigDTO dto = new ScheduleConfigDTO();
        dto.setId(id);
        dto.setEnabled(true);
        dto.setUpdateBy(SecurityUtils.getUsername());
        dto.setUpdateTime(new Date());
        scheduleConfigService.update(dto);
        //开启任务task
        scheduleTaskComponent.addTask(scheduleConfigService.getById(id));
    }

    /**
     * 执行一次
     */
    @Log(type = Log.OperationType.UPDATE,button = "执行一次",menu = "定时任务管理")
    @ApiOperation("执行一次")
    @GetMapping("/executeOnce")
    public void executeOnce(@RequestParam Integer id){

        ScheduleConfigDTO dto = scheduleConfigService.getById(id);
        Class<?> clazz;
        try {
            clazz = Class.forName(dto.getClassName());
            Method method = clazz.getMethod(dto.getMethod());
            //调用方法
            method.invoke(clazz.newInstance());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 删除任务
     */
    @Log(type = Log.OperationType.DELETE,button = "删除任务",menu = "定时任务管理")
    @ApiOperation("删除任务")
    @DeleteMapping("/delete")
    public void delTask(@RequestParam Integer id){

        ScheduleConfigDTO dto = scheduleConfigService.getById(id);
        scheduleConfigService.delete(dto);
        scheduleTaskComponent.pauseeTask(dto.getJobName());
    }
}
