package com.seahorse.youliao.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.logfilter.BusinessLog;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.BusinessLogService;
import com.seahorse.youliao.service.entity.BusinessLogDTO;
import com.seahorse.youliao.util.IpUtil;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.utils.IDGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ProjectName: jinkai-parent
 * @Package: com.scil.jinkai.log
 * @ClassName: LoggerAdvice
 * @Description: 日志切面
 * @author:songqiang
 * @Date:2020-01-15 15:58
 **/
@Aspect
@Component
public class LogAdvice {


    @Autowired
    private BusinessLogService businessLogService;

    private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    @Pointcut("@annotation(log)")
    public void loggerPointcut(Log log) {}

    @Around("loggerPointcut(log)")
    public Object around(ProceedingJoinPoint p, Log log) throws Throwable {
        Object result;
        BusinessLog logInfo = getLogBeforeProceed(p,log);
        try {
            //执行结果，返回参数
            result = p.proceed();
            logInfo.setResult("成功");
            logInfo.setResponseParams(JSONObject.toJSONString(result));
        } catch (Throwable e) {
            logger.error("日志记录 throwable = {}",e.toString());
            logInfo.setException(e.getMessage());
            logInfo.setResult("失败");
            //异常抛出
            throw e;
        }finally {
            //当前人
            logInfo.setOperationUser(SecurityUtils.getUsername());
            logInfo.setResponseTime(new Date());
            logInfo.setCreateTime(new Date());
            logInfo.setId(IDGenerator.getUUID());
            // 记录业务日志到数据库
            logger.info("业务日志记录到数据库 logInfo = {}",logInfo);
            businessLogService.insert(BeanUtil.convert(logInfo,BusinessLogDTO.class));

        }
        return result;
    }


    /**
     * 日志参数封装
     * @param p
     * @param log
     * @return
     */
    private BusinessLog getLogBeforeProceed(ProceedingJoinPoint p,Log log){
        BusinessLog result = new BusinessLog();
        result.setRequestTime(new Date());
        result.setTargetName(p.getTarget().getClass().getName());
        result.setMethodName(p.getSignature().getName());
        Object[] obj = p.getArgs();

        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                Object o = obj[i];
                if (o instanceof Model) {
                    continue;
                }
                if (o instanceof MultipartFile) {
                    buffer.append("上传文件名："+((MultipartFile) o).getOriginalFilename());
                    continue;
                }
                buffer.append("[参数" + (i + 1) + ":");
                String parameter = null;
                try {
                    parameter = JSON.toJSONString(o);
                } catch (Exception e) {
                    buffer.append("]");
                    continue;
                }
                buffer.append(parameter);
                buffer.append("]");
            }
        }

        String param = buffer.toString();

        result.setRequestParams(param);
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //ip信息
        result.setHost(IpUtil.getIpAddr(request));

        MethodSignature methodName = (MethodSignature) p.getSignature();
        Method method = methodName.getMethod();
        //类型 菜单 按钮名称；
        result.setOperationType(method.getAnnotation(Log.class).type().name());
        result.setMenu(method.getAnnotation(Log.class).menu());
        result.setButton(method.getAnnotation(Log.class).button());
        result.setDescription(method.getAnnotation(Log.class).description());
        return result;
    }

}
