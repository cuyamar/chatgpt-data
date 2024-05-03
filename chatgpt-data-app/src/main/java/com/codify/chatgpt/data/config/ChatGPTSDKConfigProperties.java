package com.codify.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "chatgpt.sdk.config", ignoreInvalidFields = true)
public class ChatGPTSDKConfigProperties {
    /** 转发地址 <a href="https://api.xfg.im/b8b6/">https://api.xfg.im/b8b6/</a> */
    private String apiHost;
    /** 可以申请 sk-*** */
    private String apiKey;
}
