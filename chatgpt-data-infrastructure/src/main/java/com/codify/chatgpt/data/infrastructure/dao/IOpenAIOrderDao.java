package com.codify.chatgpt.data.infrastructure.dao;

import com.codify.chatgpt.data.infrastructure.po.OpenAIOrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单的DAO
 */
@Mapper
public interface IOpenAIOrderDao {

    OpenAIOrderPO queryUnpaidOrder(OpenAIOrderPO openAIOrderPOReq);
}
