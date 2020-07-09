package com.seahorse.youliao.websocket;

import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.vo.request.ws.GuardQueueInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.websocket
 * @ClassName: ScreenTaskJob
 * @Description: 大屏定时刷新
 * @author:songqiang
 * @Date:2020-01-10 17:16
 **/
@Component
public class ScreenTaskJob {


    private static Logger logger = LoggerFactory.getLogger(ScreenTaskJob.class);

    private static int start=0;
    private static int end=10;
    private static int startS=0;
    private static int endS=12;
    @PostConstruct
    public void isCheckInTimeOut(){

        logger.info("task 执行了");
//分页推送投屏信息
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                try{
                    Map map = new HashMap();
                    map.put("page","true");
                    map.put("start",start);
                    map.put("end",end);
                    List<GuardQueueInfo> queueList = new ArrayList<>();

                    GuardQueueInfo guardQueueInfo = new GuardQueueInfo();
                    guardQueueInfo.setCarNo("川A09c6k");
                    guardQueueInfo.setCaretTime(new Date());
                    guardQueueInfo.setEndAddr("四川省成都是");
                    guardQueueInfo.setFactoryId("factoryId");
                    guardQueueInfo.setFactoryName("大型工厂");
                    guardQueueInfo.setWarehouseName("仓库1");
                    guardQueueInfo.setGuardName("张三");
                    guardQueueInfo.setRouteCode("code");
                    guardQueueInfo.setSkuName("啤酒");
                    guardQueueInfo.setStartAddr("北京");
                    guardQueueInfo.setWaitNum(10);
                    guardQueueInfo.setStatus(0);
                    guardQueueInfo.setStatusName("等待");
                    queueList.add(guardQueueInfo);

                    GuardQueueInfo guardQueueInfo2 = new GuardQueueInfo();
                    guardQueueInfo2.setCarNo("川A09c6k");
                    guardQueueInfo2.setCaretTime(new Date());
                    guardQueueInfo2.setEndAddr("重庆");
                    guardQueueInfo2.setFactoryId("factoryId");
                    guardQueueInfo2.setFactoryName("大型工厂");
                    guardQueueInfo2.setWarehouseName("仓库2");
                    guardQueueInfo2.setGuardName("李四");
                    guardQueueInfo2.setRouteCode("code2");
                    guardQueueInfo2.setSkuName("美酒");
                    guardQueueInfo2.setStartAddr("香港");
                    guardQueueInfo2.setWaitNum(100);
                    guardQueueInfo2.setStatus(4);
                    guardQueueInfo2.setStatusName("等待");
                    queueList.add(guardQueueInfo2);

                    if(queueList!=null && queueList.size()<10){
                        start=0;
                        end=10;
                    }else{
                        start = end;
                        end = end+10;
                    }
                    if(queueList==null){
                        queueList = new ArrayList<GuardQueueInfo>();
                    }
                    WebSocketScreenManager.sendQueueInfo(JSONObject.toJSONString(queueList),"data");
                    Map query = new HashMap();
                    query.put("start",startS);
                    query.put("end",endS);
                    List<String> carList = new ArrayList<>();
                    carList.add("川B234k");
                    carList.add("川A234k");
                    if(carList!=null && carList.size()<12){
                        startS=0;
                        endS=10;
                    }else{
                        startS = endS;
                        endS = endS+12;
                    }
                    if(carList==null){
                        carList = new ArrayList<String>();
                    }
                    WebSocketScreenManager.sendQueueInfo(JSONObject.toJSONString(carList),"carList");
                }catch (Exception e){
                    System.out.println("轮询任务出错："+e.getMessage());
                    e.printStackTrace();
                }
            }

        },5,10, TimeUnit.SECONDS);
    }
}
