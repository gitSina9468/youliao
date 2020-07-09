package com.seahorse.youliao.aop;

import com.seahorse.youliao.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.aop
 * @ClassName: NoRepeatSubmitRedisAdvice
 * @Description: redis 锁 防止重复提交切面
 * 引荐：https://www.jianshu.com/p/09860b74658e
 * https://www.cnblogs.com/linjiqin/p/8003838.html
 * https://gitee.com/yintianwen7/taven-springboot-learning/blob/master/repeat-submit-intercept
 * /src/main/java/com/gitee/taven/utils/RedisLock.java
 * @author:songqiang
 * @Date:2020-01-03 14:43
 **/
@Aspect
@Component
public class NoRepeatSubmitRedisAdvice {


    private final static Logger LOGGER = LoggerFactory.getLogger(NoRepeatSubmitRedisAdvice.class);

    //jedis 只适用于单机版redis环境 jedis 未集成  redis集群部署使用 Redisson
//    @Autowired
//    private RedisLock redisLock;

    @Pointcut("@annotation(noRepeatRedisSubmit)")
    public void pointCut(NoRepeatRedisSubmit noRepeatRedisSubmit) {
    }

    /**
     * 加锁必要条件 可靠性：
     * 1：互斥性。在任意时刻，只有一个客户端能持有锁。
     * 2：不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
     * 3：具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
     * 4：解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了
     * @param pjp
     * @param noRepeatRedisSubmit
     * @return
     * @throws Throwable
     */
    @Around("pointCut(noRepeatRedisSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatRedisSubmit noRepeatRedisSubmit) throws Throwable {
        int lockSeconds = noRepeatRedisSubmit.lockTime();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Assert.notNull(request, "request can not null");

        // 此处可以用token或者JSessionId
        String token = request.getHeader("token");
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = getClientId();

        boolean isSuccess = true;
//        boolean isSuccess = redisLock.tryLock(key, clientId, lockSeconds);

        if (isSuccess) {
            LOGGER.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            // 获取锁成功, 执行进程
            Object result;
            try {
                result = pjp.proceed();

            } finally {
                // 解锁
//                redisLock.releaseLock(key, clientId);
                LOGGER.info("releaseLock success, key = [{}], clientId = [{}]", key, clientId);

            }

            return result;

        } else {
            // 获取锁失败，认为是重复提交的请求
            LOGGER.info("tryLock fail, key = [{}],clientId = [{}]", key,clientId);
            LOGGER.info("请求路径"+request.getServletPath()+"重复请求，请稍后再试");
            throw new BusinessException("请求路径"+request.getServletPath() + "重复请求，请稍后再试");
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }

}
