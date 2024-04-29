package com.codify.chatgpt.data.infrastructure.dao;

import com.codify.chatgpt.data.infrastructure.po.OpenAIProductPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品的DAO
 */
@Mapper
public interface IOpenAIProductDao {
    OpenAIProductPO queryProductByProductId(Integer productId);

    List<OpenAIProductPO> queryProductList();
}
