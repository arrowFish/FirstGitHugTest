package com.yyc.testqpid.handler;

import com.alibaba.fastjson.JSONObject;
import com.gosuncn.gm.messagechannel.GxxRequestSession;
import com.yyc.testqpid.BaseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: yyc
 * @Date : Created in 2018/9/30 9:50
 * @Description: 可以测试请求回复
 */
public class TestReqHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(TestReqHandler.class);

	private Map<String, GxxRequestSession> requestSession = new HashMap<>();

	public boolean start() {
		BaseHandler baseHandler = new BaseHandler();
		baseHandler.initDispatcher("receive", "", this);
		return true;
	}

	@Override
	public void onMessage(String subject, String text) {
		logger.info("主题[{}]收到消息[{}]", subject, text);
	}

	@Override
	public String onRequest(GxxRequestSession gxxRequestSession, String subject, String text) {
		return null;
	}
}
