package com.yyc.testqpid.handler;

import com.alibaba.fastjson.JSONObject;
import com.gosuncn.gm.messagechannel.GxxRequestSession;
import com.yyc.testqpid.BaseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: yyc
 * @Date : Created in 2018/9/30 9:50
 * @Description: 可以测试请求回复
 */
public class TestReqAndRespHandler extends BaseHandler {
	private static final Logger logger = LoggerFactory.getLogger(TestReqAndRespHandler.class);

	private Map<String, GxxRequestSession> requestSession = new HashMap<>();

	public boolean start() {
		BaseHandler baseHandler = new BaseHandler();
		baseHandler.initDispatcher("request", "", this);
		baseHandler.initDispatcher("response", "", this);
		return true;
	}

	@Override
	public void onMessage(String subject, String text) {
		try {
			if(Objects.equals(subject, "response")) {
				JSONObject jsonObject = JSONObject.parseObject(text);
				logger.info("收到请求：{}" + text);
				String sequence = jsonObject.getString("sequence");

				if(requestSession.containsKey(sequence)){
					GxxRequestSession gxxRequestSession = requestSession.remove(sequence);
					int i = gxxChannel.response(gxxRequestSession, text);
					logger.info(i + "返回数据：" + text);
				}
			}
		}catch (Exception e){
			logger.error(e.getMessage());
		}
	}

	@Override
	public String onRequest(GxxRequestSession gxxRequestSession, String subject, String text) {
		logger.info("onRequest收到消息：" + text + " subject: " + subject);

		if (null == gxxRequestSession.getReplayTo()){
			logger.info("gxxRequestSession.getReplayTo() 为null :" + gxxRequestSession.toString());
			return null;
		}

		requestSession.put(gxxRequestSession.getSequence(), gxxRequestSession);
		gxxChannel.publish("response", text);
		gxxRequestSession.setSync(false);
		return null;
	}
}
