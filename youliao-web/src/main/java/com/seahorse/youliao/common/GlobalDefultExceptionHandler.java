package com.seahorse.youliao.common;

import com.seahorse.youliao.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 全局异常处理类
 * @author: Mr.Song
 * @create: 2020-01-03 22:47
 **/
@ControllerAdvice
public class GlobalDefultExceptionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefultExceptionHandler.class);

    /**
     * 未知异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity defaultExcepitonHandler(Exception e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        responseEntity.setRemark(e.getMessage());
        LOGGER.error("未知异常捕获!",e);
        return responseEntity;
    }
    /**
     * AuthenticationException 认证异常
     * @param exception
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity authenticationExceptionHandler(Exception exception) {

        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        if (exception instanceof LockedException) {
            responseEntity.setRemark("账户被锁定，请联系管理员!");
            LOGGER.error("AuthenticationException 认证异常! 账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            responseEntity.setRemark("密码过期，请联系管理员!");
            LOGGER.error("AuthenticationException 认证异常! 密码过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            responseEntity.setRemark("账户被禁用，请联系管理员!");
            LOGGER.error("AuthenticationException 认证异常! 账户被禁用，请联系管理员!");
        } else if (exception instanceof BadCredentialsException) {
            responseEntity.setRemark("密码输入错误，请重新输入!");
            LOGGER.error("AuthenticationException 认证异常! 密码输入错误，请重新输入!");
        } else if(exception instanceof UsernameNotFoundException){
            responseEntity.setRemark("不存在的用户名!");
            LOGGER.error("AuthenticationException 认证异常! 不存在的用户名!");
        } else if(exception instanceof InsufficientAuthenticationException){
            responseEntity.setRemark("身份验证异常!");
            LOGGER.error("AuthenticationException 认证异常! 身份验证异常!");
        }
        return responseEntity;
    }

    /**
     * 访问被拒绝的异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity accessDeniedExceptionHandler(Exception e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.ACCESS_DENIED.getCode());
        responseEntity.setRemark("权限不足,访问被拒绝");
        LOGGER.error("权限不足,访问被拒绝",e);
        return responseEntity;
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity businessExceptionHandler(BusinessException e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        responseEntity.setRemark(e.getMessage());
        LOGGER.error("自定义业务异常!",e);
        return responseEntity;
    }
    /**
     * mediaType 不支持异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        responseEntity.setRemark("请求方式不支持");
        LOGGER.error("请求方式不支持!",e);
        return responseEntity;
    }
    /**
     * 请求参数格式解析异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        responseEntity.setRemark("请求参数格式不正确");
        LOGGER.error("请求异常：{}",e.getMessage());
        return responseEntity;
    }
    /**
     * hibernate 验证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity constraintViolationExceptionHandler(ConstraintViolationException e) {
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        String message = e.getMessage();
        Integer begin = message.indexOf(":")+1;
        Integer end = message.indexOf(",");
        if(end>begin){
            message= message.substring(begin,end);
        }else{
            message= message.substring(begin);
        }
        responseEntity.setRemark(message);
        LOGGER.error("hibernate 验证规则 异常!",e);
        return responseEntity;
    }

    /**
     * validation 校验参数
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                responseEntity.setRemark(((FieldError)errors.get(0)).getDefaultMessage());
                Iterator var6 = errors.iterator();

                while(var6.hasNext()) {
                    FieldError error = (FieldError)var6.next();
                    errorList.add(error.getDefaultMessage());
                }
            }
        }
        LOGGER.error("validation 校验参数异常!",e);
        responseEntity.setResult(errorList);
        return responseEntity;
    }

    /**
     * 文件大小超过最大限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseEntity sizeLimitExceededExceptionHandler(MaxUploadSizeExceededException e){
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        responseEntity.setRemark("文件大小超过限制");
        LOGGER.error("文件上传异常，文件大小超过限制！！！",e);
        return responseEntity;
    }

    /**
     * 重复的key 记录异常
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity duplicateKeyExceptionHandler(DuplicateKeyException e){

        ResponseEntity responseEntity=ResponseEntity.error("数据库已存在相同记录");
        LOGGER.error("数据重复 无法提交！！！",e);
        return responseEntity;
    }

    /**
     * 超出数据库字段大小异常
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        ResponseEntity responseEntity=ResponseEntity.error("字段太长,超出数据库字段的长度");
        LOGGER.error("字段太长,超出数据库字段的长度！！！",e);
        return responseEntity;
    }

}
