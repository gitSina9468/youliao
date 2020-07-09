package com.seahorse.youliao.common;

/**
 * @description: 全局code
 * @author: Mr.Song
 * @create: 2020-01-03 22:29
 **/
public enum ResponseCode {

    //成功
    SUCCESS(200, "成功"),
    //校验失败
    VALIDATE_FAIL(400, "校验失败"),
    //拒绝访问
    ACCESS_DENIED(403, "拒绝访问"),
    //登录超时
    OVER_TIME(408,"登录超时，请重新登陆"),
    //内部服务器异常
    INTERNAL_ERROR(500, "内部服务器异常");

    private Integer code;

    private String desc;

    ResponseCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public ResponseCode setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ResponseCode setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public static ResponseCode parse(Integer code) {
        ResponseCode[] ses = ResponseCode.values();
        for (ResponseCode se : ses) {
            if (se.getCode().equals(code)) {
                return se;
            }
        }
        return null;
    }
}
