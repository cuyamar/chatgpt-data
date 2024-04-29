package com.codify.chatgpt.data.domain.order.model.entity;

import com.codify.chatgpt.data.domain.order.model.valobj.PayStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: Sky
 * 未支付订单实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidOrderEntity {
    /**
     * 用户ID
     */
    private String openid;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 支付地址：创建支付后的URL
     */
    private String payUrl;

    /**
     * 支付状态；0-等待支付、1-支付完成、2-支付失败、3-放弃支付
     */
    private PayStatusVO payStatus;

}
