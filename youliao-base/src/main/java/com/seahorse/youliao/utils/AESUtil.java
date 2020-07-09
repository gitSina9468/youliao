package com.seahorse.youliao.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description: AES 加密 解密
 * @author: Mr.Song
 * @create: 2020-03-07 19:36
 **/
public class AESUtil {



    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data,String password) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(MD5.MD5Encode(password).toLowerCase().getBytes(), ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     * 注意：AES的256位密钥加解密报 java.security.InvalidKeyException:
     * Illegal key size or default parameters 异常的处理及处理工具
     *
     * 为了数据代码在传输过程中的安全，很多时候我们都会将要传输的数据进行加密，
     * 然后等对方拿到后再解密使用。我们在使用AES加解密的时候，在遇到128位密钥加解密的时候，
     * 没有进行什么特殊处理；然而，在使用256位密钥加解密的时候，如果不进行特殊处理的话，
     * 往往会出现报“java.security.InvalidKeyException: Illegal key size or default parameters”的异常，
     *
     * 解决方案：在我们安装的JRE目录下有这样一个文件夹：%JAVE_HOME%\jre\lib\security（%JAVE_HOME%是自己电脑的Java路径，
     * 一版默认是：C:\Program Files\Java，具体看自己当时安装JDK和JRE时选择的路径是什么），
     * 其中包含有两个.jar文件：“local_policy.jar ”和“US_export_policy.jar”
     *
     * 如果没有资源下载 系统提供下载入口
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(MD5.MD5Encode(password).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = Base64Util.decode(base64Data);
        byte[] doFinal = cipher.doFinal(decode);
        return new String(doFinal,"utf-8");
    }

}
