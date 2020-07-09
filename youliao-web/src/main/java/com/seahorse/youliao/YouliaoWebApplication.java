package com.seahorse.youliao;

import com.zengtengpeng.annotation.EnableCache;
import com.zengtengpeng.annotation.EnableMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author songqiang
 * EnableXDoc <--- 加上此注解以便启用XDOC在线HTML文档
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.seahorse.youliao.*"})
@EnableMQ
@EnableCache(value = {"menuCache"})
public class YouliaoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouliaoWebApplication.class, args);

        System.out.println(" 1 \n" +
                " 2                        .::::.\n" +
                " 3                      .::::::::.\n" +
                " 4                     :::::::::::\n" +
                " 5                  ..:::::::::::'\n" +
                " 6               '::::::::::::'\n" +
                " 7                 .::::::::::\n" +
                " 8            '::::::::::::::..\n" +
                " 9                 ..::::::::::::.\n" +
                "10               ``::::::::::::::::\n" +
                "11                ::::``:::::::::'        .:::.\n" +
                "12               ::::'   ':::::'       .::::::::.\n" +
                "13             .::::'      ::::     .:::::::'::::.\n" +
                "14            .:::'       :::::  .:::::::::' ':::::.\n" +
                "15           .::'        :::::.:::::::::'      ':::::.\n" +
                "16          .::'         ::::::::::::::'         ``::::.\n" +
                "17      ...:::           ::::::::::::'              ``::.\n" +
                "18     ```` ':.          ':::::::::'                  ::::..\n" +
                "19                        '.:::::'                    ':'````..\n"+
                "20             美女保佑             永无BUG            ");
    }
}
