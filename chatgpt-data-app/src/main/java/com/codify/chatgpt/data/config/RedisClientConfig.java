package com.codify.chatgpt.data.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codify.chatgpt.data.trigger.mq.OrderPaySuccessListener;
import com.codify.chatgpt.data.types.redis.RedisTopic;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.config.Config;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author: Sky
 * redis连接配置参数
 */
@Configuration
@EnableConfigurationProperties(RedisClientConfigProperties.class)
public class RedisClientConfig {

    @Bean("redissonClient")
    public RedissonClient redissonClient(ConfigurableApplicationContext applicationContext, RedisClientConfigProperties properties){
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword())
                .setConnectionPoolSize(properties.getPoolSize())
                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                .setIdleConnectionTimeout(properties.getIdleTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setPingConnectionInterval(properties.getPingInterval())
                .setKeepAlive(properties.isKeepAlive())
        ;

        RedissonClient redissonClient = Redisson.create(config);

        String[] beanNamesForType = applicationContext.getBeanNamesForType(MessageListener.class);
        for(String beanName : beanNamesForType){
            MessageListener bean = applicationContext.getBean(beanName, MessageListener.class);

            Class<? extends MessageListener> beanClass = bean.getClass();
            if(beanClass.isAnnotationPresent(RedisTopic.class)){
                RedisTopic redisTopic = beanClass.getAnnotation(RedisTopic.class);

                RTopic topic = redissonClient.getTopic(redisTopic.topic());
                topic.addListener(String.class,bean);

                ConfigurableBeanFactory beanFactory = applicationContext.getBeanFactory();
                beanFactory.registerSingleton(redisTopic.topic(), topic);
            }
        }
        return redissonClient;
    }

    @Bean("redisTopic")
    public RTopic redisTopicListener(RedissonClient redissonClient, OrderPaySuccessListener orderPaySuccessListener){
        RTopic topic = redissonClient.getTopic("cuyar-dev-topic");
        topic.addListener(String.class,orderPaySuccessListener);
        return topic;
    }


    /**
     * 手动配置
     */

    static class RedisCodec extends BaseCodec{

        private final Encoder encoder = in ->{
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            try {
                ByteBufOutputStream os = new ByteBufOutputStream(out);
                JSON.writeJSONString(os,in, SerializerFeature.WriteClassName);
                return os.buffer();
            }catch (IOException e){
                out.release();
                throw e;
            }catch (Exception e){
                out.release();
                throw new IOException(e);
            }
        };

        private final Decoder<Object> decoder = (buf,state)->JSON.parseObject(new ByteBufInputStream(buf),Object.class);

        @Override
        public Decoder<Object> getValueDecoder() {
            return decoder;
        }

        @Override
        public Encoder getValueEncoder() {
            return encoder;
        }
    }
}
