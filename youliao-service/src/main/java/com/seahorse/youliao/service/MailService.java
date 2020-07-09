package com.seahorse.youliao.service;

import java.util.List;

/**
 * @description: 邮件服务
 * @author: Mr.Song
 * @create: 2020-04-20 16:59
 **/
public interface MailService {

    /**
     *  发送邮件
     * @param to 发送到
     * @param subject 主题
     * @param text 内容
     */
    void sendMail(String to, String subject, String text);

    /**
     * 发送邮件
     * @param to  发送到
     * @param subject 主题
     * @param text  内容
     * @param attachFiles  附件
     */
    void sendMailAttachments(String to, String subject, String text,List<String> attachFiles);
}
