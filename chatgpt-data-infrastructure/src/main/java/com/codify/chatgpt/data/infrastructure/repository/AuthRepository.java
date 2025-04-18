package com.codify.chatgpt.data.infrastructure.repository;

import com.codify.chatgpt.data.domain.auth.repository.IAuthRepository;
import com.codify.chatgpt.data.infrastructure.redis.IRedisService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author: Sky
 * 认证鉴权仓储服务
 */
@Repository
public class AuthRepository implements IAuthRepository {

    private static final String Key = "weixin_code";

    @Resource
    private IRedisService redisService;

    @Override
    public String getCodeUserOpenId(String code) {
        return redisService.getValue(Key + "_"+code);
    }

    @Override
    public void removeCodeByOpenId(String code, String openId) {
        redisService.remove(Key+"_"+code);
        redisService.remove(Key+"_"+openId);
    }
}
