package com.codify.chatgpt.data.domain.openai.service.rule.imp;

import com.codify.chatgpt.data.domain.openai.annotion.LogicStrategy;
import com.codify.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.codify.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.codify.chatgpt.data.domain.openai.model.entity.UserAccountQuotaEntity;
import com.codify.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.codify.chatgpt.data.domain.openai.service.rule.ILogicFilter;
import com.codify.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Sky
 * 允许访问的模型
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.MODEL_TYPE)
public class ModelTypeFilter implements ILogicFilter<UserAccountQuotaEntity> {
    @Override
    public RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, UserAccountQuotaEntity data) throws Exception {
        //1.用户可用模型
        List<String> allowModelTypeList = data.getAllowModelTypeList();
        String model = chatProcess.getModel();

        //2.模型校验成功
        if(allowModelTypeList.contains(model)){
            return RuleLogicEntity.<ChatProcessAggregate>builder()
                    .type(LogicCheckTypeVO.SUCCESS)
                    .data(chatProcess)
                    .build();
        }

        //3.模型校验拦截
        return RuleLogicEntity.<ChatProcessAggregate>builder()
                .type(LogicCheckTypeVO.REFUSE)
                .info("当前账号不能使用"+model+"模型，请联系客服升级账户！")
                .data(chatProcess)
                .build();
    }
}
