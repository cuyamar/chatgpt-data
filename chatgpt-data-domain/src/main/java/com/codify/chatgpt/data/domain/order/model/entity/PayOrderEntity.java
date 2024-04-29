package com.codify.chatgpt.data.domain.order.model.entity;

import com.codify.chatgpt.data.domain.order.model.valobj.PayStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sky
 * 支付单实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderEntity {
    /**
     * 用户ID
     */
    private String openid;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付地址:创建支付后获取的url地址
     */
    private String payUrl;

    /**
     * 支付状态
     */
    private PayStatusVO payStatus;

    @Override
    public String toString() {
        return "PayOrderEntity{" +
                "openid='" + openid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", payStatus=" + payStatus.getCode() + ": " + payStatus.getDesc() +
                '}';
    }
}
