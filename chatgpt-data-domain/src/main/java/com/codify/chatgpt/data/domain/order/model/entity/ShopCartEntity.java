package com.codify.chatgpt.data.domain.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sky
 * 简单购物车实体对象；大型电商中，购物车还会包括；商品类型【实物/虚拟】、商品数量、优惠信息、配送信息、增值服务
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopCartEntity {
    /**
     * 用户微信唯一ID
     */
    private String openid;

    /**
     * 商品ID
     */
    private Integer productId;

    @Override
    public String toString(){
        return "ShopCartEntity{" +
                "openid='" + openid + '\'' +
                ", productId=" + productId +
                '}';
    }
}
