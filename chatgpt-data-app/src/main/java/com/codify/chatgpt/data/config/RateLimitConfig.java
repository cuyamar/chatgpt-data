package com.codify.chatgpt.data.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限次配置类
 * @author: Sky
 */
@Configuration
public class RateLimitConfig {

    @Value("3")
    private Integer rateLimitCount;

    @Value("${app.config.limit-time-second}")
    private Integer limitTime;

    @Bean(name = "rateLimiter")
    public RateLimiter rateLimiter(){
        return RateLimiter.create((double)rateLimitCount/limitTime);
    }
}
