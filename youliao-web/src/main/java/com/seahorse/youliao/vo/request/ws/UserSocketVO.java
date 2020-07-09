package com.seahorse.youliao.vo.request.ws;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.websocket.Session;
import java.util.Date;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request.ws
 * @ClassName: UserSocketVO
 * @Description: 用户socket
 * @author:songqiang
 * @Date:2020-01-06 18:43
 **/
@Getter
@Setter
@ToString
public class UserSocketVO {


    private String sessionId;
    private String reqId;
    private Date connectTime;
    private Date expireTime;
    private Session webSocketSession;

}
