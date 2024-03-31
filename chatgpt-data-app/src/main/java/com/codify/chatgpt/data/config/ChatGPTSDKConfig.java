package com.codify.chatgpt.data.config;


import com.codify.chatgpt.session.OpenAiSession;
import com.codify.chatgpt.session.OpenAiSessionFactory;
import com.codify.chatgpt.session.defaults.DefaultOpenAiSessionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//工厂配置开启
@Configuration
@EnableConfigurationProperties(ChatGPTSDKConfigProperties.class)
public class ChatGPTSDKConfig {

    public OpenAiSession openAiSession(ChatGPTSDKConfigProperties properties) {
        // 1. 配置文件
        com.codify.chatgpt.session.Configuration configuration = new com.codify.chatgpt.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setAuthToken(properties.getAuthToken());
        configuration.setApiKey(properties.getApiKey());

        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        // 3. 开启会话
        return factory.openSession();
    }

}

