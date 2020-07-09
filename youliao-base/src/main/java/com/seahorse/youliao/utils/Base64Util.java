package com.seahorse.youliao.utils;

import java.util.Base64;

/**
 * @description: base64 加密 解密
 * @author: Mr.Song
 * @create: 2020-03-07 19:37
 **/
public class Base64Util {

    /**
     * 解码
     * @param encodedText
     * @return
     */
    public static byte[] decode(String encodedText){
        final Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encodedText);
    }
    /**
     * 编码
     * @param data
     * @return
     */
    public static String encode(byte[] data){
        final Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }
}
