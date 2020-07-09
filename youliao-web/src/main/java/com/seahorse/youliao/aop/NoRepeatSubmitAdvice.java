package com.seahorse.youliao.aop;

import com.google.common.cache.Cache;
import com.seahorse.youliao.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: youliao-project
 * @Package: com.seahorse.youliao.aop
 * @ClassName: NoRepeatSubmitAdvice
 * @Description: 防止重复提交切面
 * @author:songqiang
 * @Date:2020-01-03 11:31
 **/
@Aspect
@Component
public class NoRepeatSubmitAdvice {

    private Logger logger = LoggerFactory.getLogger(NoRepeatSubmitAdvice.class);

    @Autowired
    private Cache<String, Integer> cache;


    @Pointcut("@annotation(nrs)")
    public void noRepeatSubmit(NoRepeatSubmit nrs){

    }


    @Around("noRepeatSubmit(nrs)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        HttpServletRequest request = attributes.getRequest();
        String key = sessionId + "-" + request.getServletPath();
        // 如果缓存中有这个url视为重复提交
        if (cache.getIfPresent(key) == null) {
            Object o = pjp.proceed();
            cache.put(key, 0);
            return o;
        } else {
            logger.info(request.getServletPath() + "重复提交");
            throw new BusinessException("请勿重复操作");
        }

    }
}
