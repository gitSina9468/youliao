package com.seahorse.youliao.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @description: 全局返回实体
 * @author: Mr.Song
 * @create: 2020-01-03 22:38
 **/
@ApiModel
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = -6547410396516217748L;

    /**
     * 响应编码
     */
    @ApiModelProperty(name="code",value="响应编码：200->成功,400->校验失败,408->登录超时,500->内部服务器异常")
    private int code = ResponseCode.SUCCESS.getCode();

    /**
     * 响应备注
     */
    @ApiModelProperty(name="remark",value="备注信息，有异常信息会填写到这个字段中")
    private String remark = ResponseCode.SUCCESS.getDesc();

    /**
     * 数据
     */
    private T result;

    public static ResponseEntity build(){
        return new ResponseEntity();
    }

    public static ResponseEntity ok(String remark,Object result){
        return new ResponseEntity(ResponseCode.SUCCESS.getCode(),remark,result);
    }

    public static ResponseEntity ok(String remark){
        return new ResponseEntity(ResponseCode.SUCCESS.getCode(),remark,null);
    }

    public static ResponseEntity ok(Object result){
        return new ResponseEntity(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(),result);
    }

    public static ResponseEntity error(String remark,Object result){
        return new ResponseEntity(ResponseCode.INTERNAL_ERROR.getCode(),remark,result);
    }

    public static ResponseEntity error(String remark){
        return new ResponseEntity(ResponseCode.INTERNAL_ERROR.getCode(),remark,null);
    }

}
