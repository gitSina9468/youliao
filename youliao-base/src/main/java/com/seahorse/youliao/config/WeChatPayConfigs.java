package com.seahorse.youliao.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @description: 微信支付配置
 * @author: Mr.Song
 * @create: 2020-03-05 21:18
 **/
@Slf4j
public class WeChatPayConfigs {

    private static Configuration configs;
    /**
     * 服务号的应用ID
     */
    public  static String APP_ID;
    /**
     * 服务号的应用密钥
     */
    public  static String APP_SECRET;
    /**
     * 服务号的配置token
     */
    public  static String TOKEN;
    /**
     * 商户号
     */
    public  static String MCH_ID;
    /**
     * API密钥
     */
    public  static String API_KEY;
    /**
     * 签名加密方式
     */
    public  static String SIGN_TYPE;
    /**
     * 微信支付证书
     */
    public  static String CERT_PATH;

    /**
     * 微信支付回调
     */
    public  static String PAY_NOTIFY_URL;
    /**
     * 微信退款回调
     */
    public  static String REFUND_NOTIFY_URL;

    public static synchronized void init(String filePath) {
        if (configs != null) {
            return;
        }
        try {
            configs = new PropertiesConfiguration(filePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (configs == null) {
            throw new IllegalStateException("can`t find file by path:"
                    + filePath);
        }
        APP_ID = configs.getString("APP_ID");
        APP_SECRET = configs.getString("APP_SECRET");
        TOKEN = configs.getString("TOKEN");
        MCH_ID = configs.getString("MCH_ID");
        API_KEY = configs.getString("API_KEY");
        SIGN_TYPE = configs.getString("SIGN_TYPE");
        CERT_PATH = configs.getString("CERT_PATH");
        PAY_NOTIFY_URL = configs.getString("PAY_NOTIFY_URL");
        REFUND_NOTIFY_URL = configs.getString("REFUND_NOTIFY_URL");

        log.info("配置文件名: " + filePath);
        log.info(description());
    }


    public static String description() {
        StringBuilder sb = new StringBuilder("WeChatPayConfigs{");
        sb.append("微信支付配置: ").append("\n");

        sb.append(" APP_ID: ").append(APP_ID).append("\n");
        sb.append(", APP_SECRET: ").append(APP_SECRET).append("\n");
        sb.append(", TOKEN: ").append(TOKEN).append("\n");
        sb.append(", MCH_ID: ").append(MCH_ID).append("\n");

        sb.append(", API_KEY: ").append(API_KEY).append("\n");
        sb.append(", SIGN_TYPE: ").append(SIGN_TYPE).append("\n");
        sb.append(", CERT_PATH: ").append(CERT_PATH).append("\n");
        sb.append("}");
        return sb.toString();
    }


}
