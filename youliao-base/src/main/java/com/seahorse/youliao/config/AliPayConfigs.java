package com.seahorse.youliao.config;

import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @description: 支付宝回调地址
 * @author: Mr.Song
 * @create: 2020-03-05 21:18
 **/
public class AliPayConfigs {

    private static Configuration configs;
    /**
     * 异步回调地址
     */
    private static String notify_url;
    /**
     * 同步回调地址
     */
    private static String return_url;

    public AliPayConfigs() {
    }

    /**
     * 根据文件名读取配置文件，文件后缀名必须为.properties
     * @param filePath
     */
    public synchronized static void init(String filePath) {
        if (configs != null) {
            return;
        }

        try {
            configs = new PropertiesConfiguration(filePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (configs == null) {
            throw new IllegalStateException("can`t find file by path:" + filePath);
        }


        // 配置地址
        notify_url = configs.getString("notify_url");
        return_url = configs.getString("return_url");
    }


    /**
     * 保证单列
     */
    private static class Singleton{
        /**
         * 静态初始化
         */
        private  static AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    /**
     * 获取交易service
     * @return
     */
    public static AlipayTradeService getAlipayTradeService(){
        return Singleton.tradeService;
    }

    public static Configuration getConfigs() {
        return configs;
    }

    public static String getNotify_url() {
        return notify_url;
    }

    public static void setNotify_url(String notify_url) {
        AliPayConfigs.notify_url = notify_url;
    }

    public static String getReturn_url() {
        return return_url;
    }

    public static void setReturn_url(String return_url) {
        AliPayConfigs.return_url = return_url;
    }
}
