package com.codify.chatgpt.data.domain.weixin.repository;

/**
 *微信服务仓储
 */
public interface IWeiXinRepository {
    String getCode(String openId);
}
