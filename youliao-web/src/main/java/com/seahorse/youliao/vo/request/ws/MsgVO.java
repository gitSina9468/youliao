package com.seahorse.youliao.vo.request.ws;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request.ws
 * @ClassName: MsgVO
 * @Description: TODO
 * @author:songqiang
 * @Date:2020-01-06 19:43
 **/
@Getter
@Setter
@ToString
public class MsgVO {

    private String type;
    private String command;
    private String data;
    private String checkVal;

    public static MsgVO msgHeart(){
        MsgVO heart = new MsgVO();
        heart.setType("alive");
        return heart;
    }
}
