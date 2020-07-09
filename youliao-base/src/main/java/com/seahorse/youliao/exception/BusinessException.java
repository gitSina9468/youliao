package com.seahorse.youliao.exception;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.exception
 * @ClassName: BusinessException
 * @Description: 自定义业务异常
 * @author:songqiang
 * @Date:2020-01-03 10:31
 **/
public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BusinessException(code=" + this.getCode() + ")";
    }
}

