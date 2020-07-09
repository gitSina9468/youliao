package com.seahorse.youliao.listenter;

import com.seahorse.youliao.logfilter.LoggerMessage;
import com.zengtengpeng.annotation.MQListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.listenter
 * @ClassName: MQLogListeners
 * @Description: 日志收集监听 注入到spring容器中
 * @author:songqiang
 * @Date:2020-01-07 10:55
 **/
@Component
public class MQLogListeners {


    /**
     * 接受消息方式一:PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic (如果消息类型不明确,请使用Object 接收消息)
     * @param charSequence
     * @param o
     */
    @MQListener(name = "logMq")
    public void logMq(CharSequence charSequence, LoggerMessage o, Object object){
        System.out.println("charSequence="+charSequence);
        System.out.println("收到消息2"+o);
    }
}
