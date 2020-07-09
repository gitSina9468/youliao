//package com.seahorse.youliao.logfilter;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * @ProjectName: youliao
// * @Package: com.seahorse.youliao.logfilter
// * @ClassName: LoggerQueue
// * @Description: TODO
// * @author:songqiang
// * @Date:2020-01-07 11:29
// **/
//public class LoggerQueue {
//
//
//    //队列大小
//    public static final int QUEUE_MAX_SIZE = Integer.MAX_VALUE;
//    private static LoggerQueue alarmMessageQueue = new LoggerQueue();
//    //阻塞队列
//    private BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);
//
//    private LoggerQueue() {
//    }
//
//    public static LoggerQueue getInstance() {
//        return alarmMessageQueue;
//    }
//    /**
//     * 消息入队
//     * @param log
//     * @return
//     */
//    public boolean push(LoggerMessage log) {
//        //队列满了就抛出异常，不阻塞
//        return this.blockingQueue.add(log);
//    }
//    /**
//     * 消息出队
//     * @return
//     */
//    public LoggerMessage poll() {
//        LoggerMessage result = (LoggerMessage)this.blockingQueue.poll();
//        return result;
//    }
//}
