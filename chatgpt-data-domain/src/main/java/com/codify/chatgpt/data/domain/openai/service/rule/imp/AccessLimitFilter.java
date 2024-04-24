package com.codify.chatgpt.data.domain.openai.service.rule.imp;

import com.codify.chatgpt.data.domain.openai.annotion.LogicStrategy;
import com.codify.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.codify.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.codify.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.codify.chatgpt.data.domain.openai.service.rule.ILogicFilter;
import com.codify.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Sky
 * 访问次数限制
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.ACCESS_LIMIT)
public class AccessLimitFilter implements ILogicFilter {
    @Value("${app.config.limit-count:10}")
    private Integer limitCount;
    @Value("${app.config.white-list}")
    private String whiteListStr;
    @Resource
    private Cache<String, Integer> visitCache;

    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess) throws Exception {
        //1。白名单用户直接放行
        if(chatProcess.isWhiteList(whiteListStr)){
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS).data(chatProcess).build();
        }

        String openid = chatProcess.getOpenid();
        //2.访问次数判断
        int visitCount = visitCache.get(openid,()->0);
        if(visitCount < limitCount){
            visitCache.put(openid,visitCount+1);
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS).data(chatProcess).build();
        }
        return RuleLogicEntity.<ChatProcessAggregate>builder()
                .info("您今日的免费" + limitCount + "次，已耗尽！")
                .type(LogicCheckTypeVO.REFUSE).data(chatProcess).build();
    }
}
