package com.yyc.testqpid.impl;

import com.gosuncn.gm.messagechannel.IGxxChannel;
import com.yyc.testqpid.service.QpidService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author: yyc
 * @Date: Create in 2019/1/25
 * @Description:
 */
@Service
public class QpidImpl implements QpidService {
	private static Logger logger = LoggerFactory.getLogger(QpidImpl.class);

	@Autowired
	@Qualifier("gxxMsgChannel")
	public IGxxChannel gxxChannel;

	@Override
	public void sendMsg(String body, String subject, long outTime) {
		gxxChannel.notify(subject, body);
		logger.info("主题[{}]发送了消息[{}]", subject, body);
	}
}
