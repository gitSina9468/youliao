package com.seahorse.youliao.vo.request.ws;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;
import java.util.Date;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request.ws
 * @ClassName: ScreenVO
 * @Description: TODO
 * @author:songqiang
 * @Date:2020-01-10 17:20
 **/
@Getter
@Setter
public class ScreenVO {


    private String sessionId;
    private String reqId;
    private Date connectTime;
    private Session webSocketSession;
}
