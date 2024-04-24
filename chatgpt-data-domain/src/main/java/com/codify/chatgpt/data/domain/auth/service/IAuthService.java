package com.codify.chatgpt.data.domain.auth.service;

import com.codify.chatgpt.data.domain.auth.model.entity.AuthStateEntity;

/**
 *鉴权验证服务接口
 */
public interface IAuthService {
    /**
     * 登录验证
     * code 验证码
     * @return token
     */

    AuthStateEntity doLogin(String code);

    boolean checkToken(String token);

    String openid(String token);
}
