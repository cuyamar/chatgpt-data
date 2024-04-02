package com.codify.chatgpt.data.domain.auth.service;

import com.codify.chatgpt.data.domain.auth.model.entity.AuthStateEntity;
import com.codify.chatgpt.data.domain.auth.model.valobj.AuthTypeVO;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Sky
 * 鉴权服务
 */
@Slf4j
@Service
public class AuthService extends AbstractAuthService{

    @Resource
    private Cache<String,String> codeCache;

    @Override
    protected AuthStateEntity checkCode(String code) {
        //获取验证码校验
        String openId = codeCache.getIfPresent(code);
        if(StringUtils.isBlank(openId)){
            log.info("鉴权，用户输入的验证码不存在{}",code);
            return AuthStateEntity.builder()
                    .code(AuthTypeVO.A0001.getCode())
                    .info(AuthTypeVO.A0001.getInfo())
                    .build();
        }

        //移除缓存key值
        codeCache.invalidate(openId);
        codeCache.invalidate(code);

        //验证码校验成功
        return AuthStateEntity.builder()
                .code(AuthTypeVO.A0000.getCode())
                .info(AuthTypeVO.A0000.getInfo())
                .build();
    }

    @Override
    public boolean checkToken(String token) {
        return isVerify(token);
    }
}
