package com.seahorse.youliao.config;

import com.seahorse.youliao.service.ScheduleConfigService;
import com.seahorse.youliao.service.entity.ScheduleConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.config
 * @ClassName: ScheduleTaskComponent
 * @Description: 定时任务组件初始化加载定时任务
 * @author:songqiang
 * @Date:2020-06-24 16:38
 **/
@Slf4j
@Component
public class ScheduleTaskComponent {

    // 保存任务
    private Map<String, ScheduledFuture<?>> futuresMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

    @Autowired
    private ScheduleConfigService scheduleConfigService;

    // 创建ThreadPoolTaskScheduler线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    // 初始化任务
    @PostConstruct
    public void initSchedule(){
        List<ScheduleConfigDTO> list = scheduleConfigService.getList(null);
        for (ScheduleConfigDTO config : list){
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(getRunnable(config), getTrigger(config));
            futuresMap.put(config.getJobName(), future);
        }
    }

    /**
     * 暂停任务
     * @param key
     * @return
     */
    public boolean pauseeTask(String key) {
        ScheduledFuture toBeRemovedFuture = futuresMap.remove(key);
        if (toBeRemovedFuture != null) {
            toBeRemovedFuture.cancel(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加任务
     * @param config
     */
    public void addTask(ScheduleConfigDTO config){
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(getRunnable(config), getTrigger(config));
        futuresMap.put(config.getJobName(), future);
    }

    /**
     * 更新任务
     * @param config
     */
    public void updateTask(ScheduleConfigDTO config) {
        ScheduledFuture toBeRemovedFuture = futuresMap.remove(config.getJobName());
        if (toBeRemovedFuture != null) {
            toBeRemovedFuture.cancel(true);
        }
        addTask(config);
    }


    /**
     * 转换首字母小写
     *
     * @param str
     * @return
     */
    public static String lowerFirstCapse(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * runnable
     * @param scheduleConfig
     * @return
     */
    private Runnable getRunnable(ScheduleConfigDTO scheduleConfig){
        return new Runnable() {
            @Override
            public void run() {
                Class<?> clazz;
                try {
                    clazz = Class.forName(scheduleConfig.getClassName());
                    Method method = clazz.getMethod(scheduleConfig.getMethod());
                    //调用方法
                    method.invoke(clazz.newInstance());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        };
    }

    /**
     * Trigger
     * @param scheduleConfig
     * @return
     */
    private Trigger getTrigger(ScheduleConfigDTO scheduleConfig) {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger trigger = new CronTrigger(scheduleConfig.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };
    }
}
