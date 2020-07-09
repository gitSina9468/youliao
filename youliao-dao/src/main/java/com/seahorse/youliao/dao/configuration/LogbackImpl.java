package com.seahorse.youliao.dao.configuration;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: LogbackImpl
 * @author: Mr.Song
 * @create: 2019-12-15 20:20
 **/
@Alias("LOGBACK")
public class LogbackImpl implements Log {
    private Logger logger;

    public LogbackImpl(String clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    @Override
    public void error(String message, Throwable e) {
        this.logger.info(message, e);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void debug(String message) {
        this.logger.info(message);
    }

    @Override
    public void trace(String message) {
        this.logger.trace(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }
}
