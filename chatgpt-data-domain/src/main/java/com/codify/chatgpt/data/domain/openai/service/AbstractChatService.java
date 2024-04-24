package com.codify.chatgpt.data.domain.openai.service;

import com.codify.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.codify.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.codify.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import com.codify.chatgpt.session.OpenAiSession;
import com.codify.chatgpt.data.types.common.Constants;
import com.codify.chatgpt.data.types.exception.ChatGPTException;
import com.codify.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;

/**
 * @author: Sky
 */
@Slf4j
public abstract class AbstractChatService implements IChatService{

    @Resource
    protected OpenAiSession openAiSession;

    @Override
    public ResponseBodyEmitter completions(ResponseBodyEmitter emitter, ChatProcessAggregate chatProcess) {
        try {// 1. 请求应答
        emitter.onCompletion(() -> {
            log.info("流式问答请求完成，使用模型：{}", chatProcess.getModel());
        });
        emitter.onError(throwable -> log.error("流式问答请求疫情，使用模型：{}", chatProcess.getModel(), throwable));

        // 2. 规则过滤
        RuleLogicEntity<ChatProcessAggregate> ruleLogicEntity = this.doCheckLogic(chatProcess,
                DefaultLogicFactory.LogicModel.ACCESS_LIMIT.getCode(),
                DefaultLogicFactory.LogicModel.SENSITIVE_WORD.getCode());

        if (!LogicCheckTypeVO.SUCCESS.equals(ruleLogicEntity.getType())) {
            emitter.send(ruleLogicEntity.getInfo());
            emitter.complete();
            return emitter;
        }


        // 3. 应答处理
            this.doMessageResponse(ruleLogicEntity.getData(), emitter);
        } catch (Exception e) {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

        // 4. 返回结果
        return emitter;
    }

    protected abstract RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, String... logics) throws Exception;

    protected abstract void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter responseBodyEmitter) throws JsonProcessingException;

}
