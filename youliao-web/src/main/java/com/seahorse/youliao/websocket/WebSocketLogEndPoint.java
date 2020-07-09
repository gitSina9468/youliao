package com.seahorse.youliao.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.vo.request.ws.UserSocketVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.websocket
 * @ClassName: QueueLogBackSocket
 * @Description: 日志socket
 * @author:songqiang
 * @Date:2020-01-06 18:34
 **/
@ServerEndpoint(value = "/connect/{param}")
@Component
public class WebSocketLogEndPoint {


    private static Logger logger = LoggerFactory.getLogger(WebSocketLogEndPoint.class);

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("param")String  param) {

        logger.info("websocket open param = " + param);

        Map<String, String> map = session.getPathParameters();
        logger.info("session.getPathParameters()"+map.toString());

        logger.info("websocket on open");
        this.session = session;
        UserSocketVO userSocketVO = null;
        String sessionId = session.getId();
        Date now = new Date();

        String reqId = UUID.randomUUID().toString();
        if(WebSocketUserManager.containKey(sessionId)){
            userSocketVO = WebSocketUserManager.get(sessionId);
            userSocketVO.setReqId(reqId);
        }else{
            userSocketVO = new UserSocketVO();
            userSocketVO.setSessionId(sessionId);
            userSocketVO.setConnectTime(now);
            userSocketVO.setWebSocketSession(session);
            userSocketVO.setReqId(reqId);
        }
        WebSocketUserManager.add(sessionId,userSocketVO);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("command","");
        jsonObj.put("type","connect");
        jsonObj.put("checkVal",sessionId);
        jsonObj.put("data","");

        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(jsonObj));
        } catch (IOException e) {
            logger.error("IO异常");
            logger.error(e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        logger.info("websocket closed");
        WebSocketUserManager.del(this.session.getId());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("websocket 连接 onerror 错误");
        logger.info(error.getMessage());
    }


    @OnMessage
    public void onMessage(String message, Session session){

        logger.error("message = "+message);

    }
}
