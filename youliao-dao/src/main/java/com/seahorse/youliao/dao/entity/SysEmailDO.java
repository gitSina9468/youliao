package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
* 邮件发送
* @author  sq
* @date 2020-05-09 03:24:02.125
**/
@Getter
@Setter
@ToString
public class SysEmailDO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 发送到邮件地址
     */
    private String receiveEmail;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件地址
     */
    private String attachFiles;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
}
