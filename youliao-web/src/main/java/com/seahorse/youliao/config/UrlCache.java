package com.seahorse.youliao.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: youliao-project
 * @Package: com.seahorse.youliao.config
 * @ClassName: UrlCache
 * @Description: 内存缓存 防止重复提交
 * @author:songqiang
 * @Date:2020-01-03 11:33
 **/
@Configuration
public class UrlCache {


    @Bean
    public Cache<String, Integer> getCache() {
        // 缓存有效期为2秒
        return CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.SECONDS).build();
    }
}
