//package com.seahorse.youliao.logfilter;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.filter.Filter;
//import ch.qos.logback.core.spi.FilterReply;
//import org.springframework.stereotype.Service;
//
//import java.text.DateFormat;
//import java.util.Date;
//
///**
// * @ProjectName: youliao
// * @Package: com.seahorse.youliao.logfilter
// * @ClassName: ProcessLogFilter
// * @Description: 日志收集filter
// * @author:songqiang
// * @Date:2020-01-06 19:06
// **/
//@Service
//public class ProcessLogFilter extends Filter<ILoggingEvent> {
//
//
//    @Override
//    public FilterReply decide(ILoggingEvent event) {
//        LoggerMessage loggerMessage = new LoggerMessage(
//                event.getMessage()
//                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
//                event.getThreadName(),
//                event.getLoggerName(),
//                event.getLevel().levelStr
//        );
//        LoggerQueue.getInstance().push(loggerMessage);
//        return FilterReply.ACCEPT;
//    }
//
//}
