package com.codify.chatgpt.data.config;

import com.codify.chatglm.session.OpenAiSession;
import com.codify.chatglm.session.OpenAiSessionFactory;
import com.codify.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//工厂配置开启
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig {
    @Bean(name = "chatGlMOpenAiSession")
    public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties){
        com.codify.chatglm.session.Configuration configuration = new com.codify.chatglm.session.Configuration();
        configuration.setApiSecretKey(properties.getApiSecretKey());
        configuration.setApiHost(properties.getApiHost());

        //创建会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        //开启会话
        return factory.openSession();
    }
}
