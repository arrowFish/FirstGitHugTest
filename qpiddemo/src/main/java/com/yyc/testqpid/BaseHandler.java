package com.yyc.testqpid;

import com.alibaba.fastjson.JSONObject;
import com.gosuncn.gm.messagechannel.GxxRequestSession;
import com.gosuncn.gm.messagechannel.IGxxChannel;
import com.gosuncn.gm.messagechannel.IGxxChannelListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.testng.util.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yyc
 * @Date : Created in 2018/11/2 22:04
 * @Description:
 */
public class BaseHandler implements IGxxChannelListener {
    private static Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    @Autowired
    @Qualifier("gxxMsgChannel")
    public IGxxChannel gxxChannel;

    private static Map<String, IGxxChannelListener> dispatcherMap = new HashMap<>();

    public boolean initDispatcher(String subject, String type, IGxxChannelListener listener){
        if (Strings.isNullOrEmpty(type)){
            dispatcherMap.put(subject, listener);
        }else {
            dispatcherMap.put(subject + "#" + type, listener);
        }
        return true;
    }

    public boolean init() {
        return gxxChannel.setListener(this);
    }

    @Override
    public void onMessage(String subject, String text) throws Exception {
            JSONObject jsonMessage = JSONObject.parseObject(text);
            String commandType = "";
            if (jsonMessage.containsKey("commandType"))
                commandType = jsonMessage.getString("commandType");
            else if (jsonMessage.containsKey("CommandType")) {
                commandType = jsonMessage.getString("CommandType");
            }

            //优先根据commandType派发，次之根据主题派发
            if (dispatcherMap.containsKey(subject + "#" + commandType)) {
                dispatcherMap.get(subject + "#" + commandType).onMessage(subject, text);
            } else if (dispatcherMap.containsKey(subject)) {
                dispatcherMap.get(subject).onMessage(subject, text);
            }
    }

    @Override
    public String onRequest(GxxRequestSession gxxRequestSession, String subject, String text) {
        JSONObject jsonMessage = JSONObject.parseObject(text);
        String result = null;

        String commandType = "";
        if (jsonMessage.containsKey("commandType"))
            commandType = jsonMessage.getString("commandType");
        else if (jsonMessage.containsKey("CommandType")) {
            commandType = jsonMessage.getString("CommandType");
        }

        //优先根据commandType派发，次之根据主题派发
        if (dispatcherMap.containsKey(subject + "#" + commandType)) {
            result = dispatcherMap.get(subject + "#" + commandType).onRequest(gxxRequestSession, subject, text);
        } else if (dispatcherMap.containsKey(subject)) {
            result = dispatcherMap.get(subject).onRequest(gxxRequestSession, subject, text);
        }

        return result;
    }
}
