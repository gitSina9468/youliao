package com.seahorse.youliao.config;

import com.alipay.demo.trade.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.config
 * @ClassName: YouLiaoStartConfig
 * @Description: 项目启动配置
 * @author:songqiang
 * @Date:2020-01-06 19:35
 **/
@Component
public class YouLiaoStartConfig implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(YouLiaoStartConfig.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {


        //初始化 支付宝-微信参数配置参数
        logger.info("加载微信支付配置文件wxinfo.properties-----------------");
        WeChatPayConfigs.init("wxinfo.properties");
        logger.info("微信配置文件加载成功----------------------------------");

        logger.info("加载支付宝支付配置文件zfb.properties-----------------");
        AliPayConfigs.init("zfb.properties");
        Configs.init("zfb.properties");
        logger.info("支付宝配置文件加载成功----------------------------------");

        //打印系统相关参数 -- 根据个人爱好打印
        logger.info("file.separator : " + System.getProperty("file.separator"));
        logger.info("line.separator : " + System.getProperty("line.separator"));
        logger.info("path.separator : " + System.getProperty("path.separator"));

        logger.info("\n----------------Application 参数打印:---------------------\n\t" +
                "java.home : " + System.getProperty("java.home") + "/\n\t" +
                "java.class.version : " + System.getProperty("java.class.version") + "/\n\t" +
                "java.class.path : " + System.getProperty("java.class.path") + "/\n\t" +
                "java.library.path : " + System.getProperty("java.library.path") + "/\n\t" +
                "java.io.tmpdir : " + System.getProperty("java.io.tmpdir") + "/\n\t" +
                "java.compiler : " + System.getProperty("java.compiler") + "/\n\t" +
                "java.ext.dirs : " + System.getProperty("java.ext.dirs") + "/\n\t" +
                "user.name : " + System.getProperty("user.name") + "/\n\t" +
                "user.home : " + System.getProperty("user.home") + "/\n\t" +
                "user.dir : " + System.getProperty("user.dir") + "/\n" +
                "----------------------------------------------------------");

        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = "8080";
        logger.info("\n----------------------------------------------------------\n\t" +
                "Application You Liao is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/api/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/api/\n\t" +
                "swagger-ui: http://" + ip + ":" + port + "/api/swagger-ui.html\n\t" +
                "XDoc: \t\thttp://" + ip + ":" + port + "/api/xdoc/index.html\n\t" +
                "Druid: \t\thttp://" + ip + ":" + port + "/api/druid/login.html\n\t" +
                "Pdf: \t\thttp://" + ip + ":" + port + "/api/generic/web/viewer.html\n\t" +
                "JVMLog: \thttp://" + ip + ":" + port + "/api/log/view\n" +
                "----------------------------------------------------------");

    }
}
