package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @description: 邮件服务
 * @author: Mr.Song
 * @create: 2020-04-20 17:01
 **/
@Slf4j
@Service
public class MailServiceImpl implements MailService {


    @Resource
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String from;

    /**
     *  发送邮件
     * @param to 发送到
     * @param subject 主题
     * @param text 内容
     */
    @Async
    @Override
    public void sendMail(String to, String subject, String text) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper =  new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送邮件失败",e.toString());
            throw new BusinessException("发送邮件失败");
        }
    }

    /**
     * 发送邮件
     *
     * @param to          发送到
     * @param subject     主题
     * @param text        内容
     * @param attachFiles 附件
     */
    @Async
    @Override
    public void sendMailAttachments(String to, String subject, String text, List<String> attachFiles) {

        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            //验证附件数据是否为空
            if(null != attachFiles){
                FileSystemResource file=null;
                for (String attachFile : attachFiles) {
                    //添加附件
                    file = new FileSystemResource(attachFile);
                    helper.addAttachment(attachFile.substring(attachFile.lastIndexOf("/")+1), file);
                }
            }
            mailSender.send(message);
            log.info("带附件的邮件发送成功");
        }catch (Exception e){
            log.error("发送带附件的邮件失败",e.toString());
            throw new BusinessException("发送邮件失败");
        }
    }

}
