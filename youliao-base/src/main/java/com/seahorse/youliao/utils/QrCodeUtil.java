package com.seahorse.youliao.utils;

import com.seahorse.youliao.entity.QRCode;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: QrCodeUtil
 * @Description: 二维码工具类
 * @author:songqiang
 * @Date:2020-03-25 13:59
 **/
public class QrCodeUtil {


    /**
     * 二维码生成转换为base64
     *
     * @param content 内容
     * @return 返回结果
     */
    public static String qrCodeToBase64(String content) {
        String enCode = "";
        try {
            QRCode qrCode = new QRCode(200, 200);
            //生成二进制图片信息
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            qrCode.creatQrCode(content, outputStream);
            //将图片转换为 Byte 型
            byte[] data = outputStream.toByteArray();
            //将二进制转换为 base64
            BASE64Encoder encoder = new BASE64Encoder();
            // 返回Base64编码过的字节数组字符串
            enCode = encoder.encode(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //返回结果
        return enCode;
    }
}
