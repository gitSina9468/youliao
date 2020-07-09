package com.seahorse.youliao.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: youliao-project
 * @Package: com.seahorse.youliao.aop
 * @ClassName: NoRepeatSubmit
 * @Description: 防止重复提交标记注解
 * @author:songqiang
 * @Date:2020-01-03 11:31
 **/
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface NoRepeatRedisSubmit {

    /**
     * 设置请求锁定时间
     *
     * @return
     */
    int lockTime() default 10;
}
