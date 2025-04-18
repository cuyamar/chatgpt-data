package com.codify.chatgpt.data.domain.order.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 */
@Getter
@AllArgsConstructor
public enum PayStatusVO {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付完成"),
    FAIL(2, "支付失败"),
    ABANDON(3, "放弃支付"),
    ;

    private final Integer code;
    private final String desc;

    public static PayStatusVO get(Integer code) {
        switch (code) {
            case 0:
                return PayStatusVO.WAIT;
            case 1:
                return PayStatusVO.SUCCESS;
            case 2:
                return PayStatusVO.FAIL;
            case 3:
                return PayStatusVO.ABANDON;
            default:
                return PayStatusVO.WAIT;
        }
    }
}
