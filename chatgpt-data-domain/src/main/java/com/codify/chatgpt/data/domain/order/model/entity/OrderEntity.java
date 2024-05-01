package com.codify.chatgpt.data.domain.order.model.entity;

import com.codify.chatgpt.data.domain.order.model.valobj.OrderStatusVO;
import com.codify.chatgpt.data.domain.order.model.valobj.PayTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Sky
 * 订单实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    private OrderStatusVO orderStatus;

    /**
     * 支付类型
     */
    private PayTypeVO payTypeVO;

}
