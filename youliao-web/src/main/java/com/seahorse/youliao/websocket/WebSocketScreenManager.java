package com.seahorse.youliao.websocket;

import com.seahorse.youliao.vo.request.ws.MsgVO;
import com.seahorse.youliao.vo.request.ws.ScreenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.websocket
 * @ClassName: WebSocketScreenManager
 * @Description: TODO
 * @author:songqiang
 * @Date:2020-01-10 17:21
 **/
@Component
public class WebSocketScreenManager {


    private static Logger logger = LoggerFactory.getLogger(WebSocketScreenManager.class);

    public static ConcurrentHashMap<String,ScreenVO> USER_SOCKET_MAP = new ConcurrentHashMap<String, ScreenVO>();

    public static synchronized void  add(String sessionId,ScreenVO userSocketVO){
        if(USER_SOCKET_MAP==null){
            USER_SOCKET_MAP = new ConcurrentHashMap<String, ScreenVO>();
        }
        USER_SOCKET_MAP.put(sessionId,userSocketVO);
    }
    public static synchronized ScreenVO  get(String sessionId){
        if(USER_SOCKET_MAP.containsKey(sessionId)){
            return USER_SOCKET_MAP.get(sessionId);
        }
        return null;
    }

    public static synchronized void del(String sessionId){
        if(USER_SOCKET_MAP.containsKey(sessionId)){
            USER_SOCKET_MAP.remove(sessionId);
        }
    }
    public static Boolean containKey(String key){
        return USER_SOCKET_MAP.contains(key);
    }


    public List<ScreenVO> getUserSocketList(){
        List<ScreenVO> userSocketVOList = new ArrayList<ScreenVO>();
        if(USER_SOCKET_MAP != null && USER_SOCKET_MAP.size()>0) {
            Enumeration<String> keys = USER_SOCKET_MAP.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                ScreenVO user = get(key);
                userSocketVOList.add(user);
            }
        }
        return userSocketVOList;
    }

    @PostConstruct
    public void removeSession() throws InterruptedException {

        logger.info("socket screen remove session");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    List<String> sessionIds = new ArrayList<String>();
                    if(WebSocketScreenManager.USER_SOCKET_MAP != null){
                        Enumeration<String> keys = WebSocketScreenManager.USER_SOCKET_MAP.keys();
                        while(keys.hasMoreElements()){
                            String key = keys.nextElement();
                            ScreenVO userSocketVO = WebSocketScreenManager.USER_SOCKET_MAP.get(key);
                            Session session = userSocketVO.getWebSocketSession();
                            //判断会话是否
                            if(session.isOpen()){
                                MsgVO heartPack = MsgVO.msgHeart();
                                String heart= com.alibaba.fastjson.JSONObject.toJSONString(heartPack);
                                try {
                                    session.getBasicRemote().sendText(heart);
                                } catch (IOException e) {
                                    closeSocket(session);
                                    sessionIds.add(key);
                                    e.printStackTrace();
                                }
                            }
                        }
                        removeUserSocket(sessionIds);
                    }else{
                        WebSocketScreenManager.USER_SOCKET_MAP = new ConcurrentHashMap<String, ScreenVO>();
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },5,30, TimeUnit.SECONDS);

    }
    public static void sendQueueInfo(String data,String type){
        if(WebSocketScreenManager.USER_SOCKET_MAP != null){
            Enumeration<String> keys = WebSocketScreenManager.USER_SOCKET_MAP.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement();
                ScreenVO userSocketVO = WebSocketScreenManager.USER_SOCKET_MAP.get(key);
                Session session = userSocketVO.getWebSocketSession();
                //判断会话是否
                if(session.isOpen()){
                    MsgVO msgVO = new MsgVO();
                    msgVO.setType(type);
                    msgVO.setData(data);
                    msgVO.setCommand("");
                    String heart= com.alibaba.fastjson.JSONObject.toJSONString(msgVO);
                    try {
                        session.getBasicRemote().sendText(heart);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void sendInfoToUser(String userId,String data,String type){
        if(WebSocketScreenManager.USER_SOCKET_MAP != null){
            Enumeration<String> keys = WebSocketScreenManager.USER_SOCKET_MAP.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement();
                if(userId.equals(key)){
                    ScreenVO userSocketVO = WebSocketScreenManager.USER_SOCKET_MAP.get(key);
                    Session session = userSocketVO.getWebSocketSession();
                    //判断会话是否
                    if(session.isOpen()){
                        MsgVO msgVO = new MsgVO();
                        msgVO.setType(type);
                        msgVO.setData(data);
                        msgVO.setCommand("");
                        String heart= com.alibaba.fastjson.JSONObject.toJSONString(msgVO);
                        try {
                            session.getBasicRemote().sendText(heart);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void closeSocket(Session session){
        if(session.isOpen()){
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"TimeOut"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeUserSocket(List<String> sessionIds){
        if(sessionIds!=null && sessionIds.size()>0){
            for(String sessionId : sessionIds){
                WebSocketScreenManager.del(sessionId);
            }
        }
    }
}
