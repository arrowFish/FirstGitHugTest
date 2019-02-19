package com.yyc.testqpid.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author: yyc
 * @Date: Create in 2019/1/25
 * @Description:
 */
@Service
public interface QpidService {

	/**
	 * 发送qpid
	 * @param body      消息实体
	 * @param subject   主题
	 * @param outTime   超时时间
	 */
	void sendMsg(String body, String subject, long outTime);
}
