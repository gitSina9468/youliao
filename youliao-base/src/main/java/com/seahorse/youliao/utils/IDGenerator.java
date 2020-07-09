package com.seahorse.youliao.utils;

import java.util.UUID;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: IDGenerator
 * @Description: id生成
 * @author:songqiang
 * @Date:2020-01-03 10:41
 **/
public class IDGenerator {


    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
