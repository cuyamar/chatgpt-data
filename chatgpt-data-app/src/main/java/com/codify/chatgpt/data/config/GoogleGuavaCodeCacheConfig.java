package com.codify.chatgpt.data.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author: Sky
 */
@Configuration
public class GoogleGuavaCodeCacheConfig {
    @Bean(name = "codeCache")
    public Cache<String,String> codeCache(){
        return CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build();
    }
}
