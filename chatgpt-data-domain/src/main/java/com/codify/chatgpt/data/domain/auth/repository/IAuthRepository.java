package com.codify.chatgpt.data.domain.auth.repository;

/**
 *认证仓储服务接口
 */
public interface IAuthRepository {
    String getCodeUserOpenId(String code);

    void removeCodeByOpenId(String code,String openId);
}
