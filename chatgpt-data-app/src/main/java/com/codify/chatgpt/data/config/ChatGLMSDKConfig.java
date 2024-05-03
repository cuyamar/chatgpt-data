package com.codify.chatgpt.data.config;

import com.codify.chatglm.session.OpenAiSession;
import com.codify.chatglm.session.OpenAiSessionFactory;
import com.codify.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Sky
 * ChatGLMSDKConfig 工厂配置开启
 */
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig {
    @Bean(name = "chatGlMOpenAiSession")
    @ConditionalOnProperty(value = "chatglm.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties){
        //1.配置文件
        com.codify.chatglm.session.Configuration configuration = new com.codify.chatglm.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setApiSecretKey(properties.getApiSecretKey());

        //2.会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        //3.开启会话
        return factory.openSession();
    }
}
