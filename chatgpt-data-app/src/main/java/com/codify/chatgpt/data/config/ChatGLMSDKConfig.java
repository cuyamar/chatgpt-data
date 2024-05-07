package com.codify.chatgpt.data.config;

import com.codify.chatglm.session.OpenAiSessionFactory;
import com.codify.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import com.codify.chatglm.session.OpenAiSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Sky
 * chatGLM工厂配置开启
 */
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig {
    @Bean(name = "chatGLMOpenAiSession")
    @ConditionalOnProperty(value = "chatglm.sdk.config.enabled", havingValue = "true", matchIfMissing = true)
    public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties){
        com.codify.chatglm.session.Configuration configuration = new com.codify.chatglm.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setApiSecretKey(properties.getApiSecretKey());

        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        return  factory.openSession();
    }
}
