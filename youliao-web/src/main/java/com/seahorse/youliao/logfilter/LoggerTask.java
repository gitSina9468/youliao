//package com.seahorse.youliao.logfilter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.seahorse.youliao.websocket.WebSocketUserManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * @ProjectName: youliao
// * @Package: com.seahorse.youliao.logfilter
// * @ClassName: LoggerTask
// * @Description: 日志定时器推送任务
// * @author:songqiang
// * @Date:2020-01-07 9:24
// **/
//@Component
//public class LoggerTask {
//
//    private static Logger logger = LoggerFactory.getLogger(LoggerTask.class);
//
//    /**
//     * 定时获取日志队列实现日志推送
//     */
//    @Scheduled(cron = "*/2 * * * * ?")
//    public void run1(){
//        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
//        exec.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                //将日志推送到websocket
//                try {
//                    LoggerMessage loggerMessage = LoggerQueue.getInstance().poll();
//                    if(loggerMessage != null){
//                        WebSocketUserManager.sendQueueInfo(JSONObject.toJSONString(loggerMessage),"log");
//                    }
//                } catch (Exception e) {
//                    logger.error(e.toString());
//                }
//            }
//        }, 0, 2, TimeUnit.SECONDS);
//    }
//
//}
