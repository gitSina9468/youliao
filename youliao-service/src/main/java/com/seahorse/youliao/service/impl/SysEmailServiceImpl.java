package com.seahorse.youliao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.dao.SysEmailDao;
import com.seahorse.youliao.dao.entity.SysEmailDO;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.MailService;
import com.seahorse.youliao.service.SysEmailService;
import com.seahorse.youliao.service.entity.SysEmailDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 邮件发送
* @author  sq
* @date    2020-05-09 03:24:02.125
**/
@Service
public class SysEmailServiceImpl extends BaseServiceImpl<SysEmailDTO> implements SysEmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysEmailServiceImpl.class);

	@Autowired
	private SysEmailDao sysEmailDao;
	
	@Autowired
	private MailService mailService;

	/**
	 * 发送邮件
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean send(Integer id) {
		
		//查询记录
		SysEmailDO mail = sysEmailDao.getById(id);
		if(mail == null){
			throw new BusinessException("邮件记录为空");
		}
		if(StringUtils.isEmpty(mail.getAttachFiles())){
			mailService.sendMail(mail.getReceiveEmail(),mail.getSubject(),mail.getContent());
		}else{
			//带附件发送
			List<String> list = JSONObject.parseArray(mail.getAttachFiles(),String.class);
			mailService.sendMailAttachments(mail.getReceiveEmail(),mail.getSubject(),mail.getContent(),list);
		}
		//记录发送时间
		mail.setSendTime(new Date());
		return sysEmailDao.update(mail) > 0;
	}
}
