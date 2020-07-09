package com.seahorse.youliao.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.vo.request.ws.MsgVO;
import com.seahorse.youliao.vo.request.ws.UserSocketVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.websocket
 * @ClassName: WebSocketUserManager
 * @Description: websocket 用户管理
 * @author:songqiang
 * @Date:2020-01-06 18:36
 **/
@Component
public class WebSocketUserManager {


    private static Logger logger = LoggerFactory.getLogger(WebSocketUserManager.class);

    public static ConcurrentHashMap<String,UserSocketVO> USER_SOCKET_MAP = new ConcurrentHashMap<String, UserSocketVO>();

    public static synchronized void  add(String sessionId,UserSocketVO userSocketVO){
        if(USER_SOCKET_MAP==null){
            USER_SOCKET_MAP = new ConcurrentHashMap<String, UserSocketVO>();
        }
        USER_SOCKET_MAP.put(sessionId,userSocketVO);
    }
    public static synchronized UserSocketVO  get(String sessionId){
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

    public int getCurrentUserAmount(){
        if(USER_SOCKET_MAP!=null) {
            return USER_SOCKET_MAP.size();
        }
        return 0;
    }

    public List<String> getUserSocketSessionList(){
        List<String> sessionIds = new ArrayList<String>();
        if(USER_SOCKET_MAP != null && USER_SOCKET_MAP.size()>0) {
            Enumeration<String> keys = USER_SOCKET_MAP.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                sessionIds.add(key);
            }
        }
        return sessionIds;
    }

    public List<UserSocketVO> getUserSocketList(){
        List<UserSocketVO> userSocketVOList = new ArrayList<UserSocketVO>();
        if(USER_SOCKET_MAP != null && USER_SOCKET_MAP.size()>0) {
            Enumeration<String> keys = USER_SOCKET_MAP.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                UserSocketVO user = get(key);
                userSocketVOList.add(user);
            }
        }
        return userSocketVOList;
    }

    @PostConstruct
    public void removeSession() throws InterruptedException {

        logger.info("socket user remove session");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> sessionIds = new ArrayList<String>();
                    if(WebSocketUserManager.USER_SOCKET_MAP != null){
                        Enumeration<String> keys = WebSocketUserManager.USER_SOCKET_MAP.keys();
                        while(keys.hasMoreElements()){
                            String key = keys.nextElement();
                            UserSocketVO userSocketVO = WebSocketUserManager.USER_SOCKET_MAP.get(key);
                           if(userSocketVO != null && userSocketVO.getExpireTime() != null){
                               Date now = new Date();
                               //判断会话是否过期，会话过期关闭会话，并移出用户
                               if(userSocketVO.getExpireTime().before(now)){
                                   Session session = userSocketVO.getWebSocketSession();
                                   closeSocket(session);
                                   sessionIds.add(key);
                               }
                           }
                        }
                        removeUserSocket(sessionIds);
                    }else{
                        WebSocketUserManager.USER_SOCKET_MAP = new ConcurrentHashMap<String, UserSocketVO>();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },5,30, TimeUnit.SECONDS);

    }
    private void closeSocket(Session session){
        if(session.isOpen()){
            try {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("type","timeout");
                session.getBasicRemote().sendText(JSON.toJSONString(jsonObj));
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"TimeOut"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeUserSocket(List<String> sessionIds){
        if(sessionIds!=null && sessionIds.size()>0){
            for(String sessionId : sessionIds){
                WebSocketUserManager.del(sessionId);
            }
        }
    }


    public static void sendQueueInfo(String data,String type){
        if(WebSocketUserManager.USER_SOCKET_MAP != null){
            Enumeration<String> keys = WebSocketUserManager.USER_SOCKET_MAP.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement();
                UserSocketVO userSocketVO = WebSocketUserManager.USER_SOCKET_MAP.get(key);
                Session session = userSocketVO.getWebSocketSession();
                synchronized (session) {
                    //发送消息
                    sendText(data, type, session);
                }
            }
        }
    }


    public static void sendInfoToUser(String userId,String data,String type){
        if(WebSocketUserManager.USER_SOCKET_MAP != null){
            Enumeration<String> keys = WebSocketUserManager.USER_SOCKET_MAP.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement();
                if(userId.equals(key)){
                    UserSocketVO userSocketVO = WebSocketUserManager.USER_SOCKET_MAP.get(key);
                    Session session = userSocketVO.getWebSocketSession();
                    //发送消息
                    synchronized (session) {
                        sendText(data, type, session);
                    }
                }
            }
        }
    }

    private static void sendText(String data, String type, Session session) {
        //判断会话是否开启
        if(session.isOpen()){
            MsgVO msgVO = new MsgVO();
            msgVO.setType(type);
            msgVO.setData(data);
            msgVO.setCommand("");
            String heart= JSONObject.toJSONString(msgVO);
            try {
                synchronized (session) {
                    session.getBasicRemote().sendText(heart);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
