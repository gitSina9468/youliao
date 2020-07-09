 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.SysEmailDTO;

/**
* 邮件发送
* @author  sq
* @date    2020-05-09 03:24:02.125
**/
public interface SysEmailService  extends BaseService<SysEmailDTO> {


    /**
     * 发送邮件
     * @param id
     * @return
     */
    boolean send(Integer id);
}
