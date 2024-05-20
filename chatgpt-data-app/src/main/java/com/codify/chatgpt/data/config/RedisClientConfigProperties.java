package com.codify.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: Sky
 * redis连接配置
 */
@Data
@ConfigurationProperties(prefix = "redis.sdk.config",ignoreInvalidFields = true)//ignoreInvalidFields遇到无效字段是否忽略
public class RedisClientConfigProperties {
    /**
     * host:ip
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 连接池的大小:默认64
     */
    private int poolSize = 64;

    /**
     * 连接池最小空闲链接数:默认设为10
     */
    private int minIdleSize = 10;

    /**
     * 连接池最大空闲时间
     */
    private int idleTimeout = 10000;

    /**
     * 连接超时时间
     */
    private int connectTimeout = 10000;

    /**
     * 连接重试次数
     */
    private int retryAttempts = 3;

    /**
     * 连接重试的间隔时间
     */
    private int retryInterval = 1000;

    /**
     * 定期检查连接是否可用的间隔 默认为0 表示不检查
     */
    private int pingInterval = 0;

    /**
     * 设置是否保持长连接
     */
     private boolean keepAlive = true;
}
