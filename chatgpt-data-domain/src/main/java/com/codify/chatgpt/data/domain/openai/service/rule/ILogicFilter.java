package com.codify.chatgpt.data.domain.openai.service.rule;

import com.codify.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.codify.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;

/**
 * 规则过滤接口
 */
public interface ILogicFilter {
    RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess) throws Exception;
}
