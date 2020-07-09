package com.seahorse.youliao.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 全局参数 handler
 * @author: Mr.Song
 * @create: 2020-01-03 22:43
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResponseHandler {
    boolean handler() default true;
}
