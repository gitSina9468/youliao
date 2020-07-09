package com.seahorse.youliao.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description: 全局返回参数封装
 * @author: Mr.Song
 * @create: 2020-01-03 22:41
 **/
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {


    public GlobalResponseHandler() {
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        String returnTypeName = methodParameter.getParameterType().getName();
        ResponseHandler annotation = methodParameter.getMethod().getAnnotation(ResponseHandler.class);
        boolean handle = annotation == null || annotation.handler();
        return handle && !"org.springframework.http.ResponseEntity".equals(returnTypeName) && !"com.seahorse.youliao.common.ResponseEntity".equals(returnTypeName);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return o;
        } else {
            ResponseEntity<Object> response = ResponseEntity.ok(ResponseCode.SUCCESS.getDesc(),o);
            return response;
        }
    }
}
