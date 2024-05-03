package com.codify.chatgpt.data.domain.openai.service.channel;

import com.codify.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * 服务组
 */
public interface OpenAiGroupService {
    void doMessageResponse(ChatProcessAggregate chatProcessAggregate, ResponseBodyEmitter emitter)throws Exception,JsonProcessingException;
}
