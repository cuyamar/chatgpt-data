package com.codify.chatgpt.data.domain.order.model.aggregates;

import com.codify.chatgpt.data.domain.order.model.entity.OrderEntity;
import com.codify.chatgpt.data.domain.order.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sky
 * 下单聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {
    /**
     * 用户ID：微信用户唯一id
     */
    private String openid;

    /**
     * 商品
     */
    private ProductEntity product;

    /**
     * 订单
     */
     private OrderEntity order;
}
