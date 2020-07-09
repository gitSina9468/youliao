package com.seahorse.youliao.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.websocket
 * @ClassName: WebSocketConfig
 * @Description: 添加ServerEndpointExporter配置bean
 * @author:songqiang
 * @Date:2020-01-06 18:32
 **/
@Configuration
public class WebSocketConfig {

    /**
     * 服务器节点
     *
     * 如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
     * 因为它将由容器自己提供和管理
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
