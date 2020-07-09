package com.seahorse.youliao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.config
 * @ClassName: WebConfig
 * @Description: 自定义一些Handler，Interceptor，ViewResolver，MessageConverter
 * @author:songqiang
 * @Date:2020-01-10 9:31
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 配置静态访问资源
     * application.yml 配置文件配置 二选其一
     * 暂时选用 配置文件 可根据自身选择
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static")
//                .addResourceLocations("classpath:/statics")
//                .addResourceLocations("classpath:/public")
//                .addResourceLocations("classpath:/resources")
//                .addResourceLocations("classpath:/META-INF/resources")
//                .addResourceLocations("file:///E:/jinkai-project/doc/");
//
//    }

//    /**
//     * 跨域设置 针对于 前后端分离项目
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 允许跨域访问的路径
//        registry.addMapping("/**")
//                // 允许跨域访问的源
//                .allowedOrigins("*")
//                // 允许请求方法
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                // 预检间隔时间 减少重复响应
//                .maxAge(168000)
//                // 允许头部设置
//                .allowedHeaders("*")
//                // 是否发送cookie
//                //这个属性大家需要注意，它是一个可选属性，但是如果你要用到Session必须要打开
//                .allowCredentials(true);
//    }

    /**
     * 跨域设置 针对于 前后端分离项目
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        /* 是否允许请求带有验证信息 */
        corsConfiguration.setAllowCredentials(true);
        /* 允许访问的客户端域名 */
        corsConfiguration.addAllowedOrigin("*");
        /* 允许服务端访问的客户端请求头 */
        corsConfiguration.addAllowedHeader("*");
        /* 允许访问的方法名,GET POST等 */
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
