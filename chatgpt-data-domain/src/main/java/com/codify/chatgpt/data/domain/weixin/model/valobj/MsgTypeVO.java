package com.codify.chatgpt.data.domain.weixin.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *微信公众号消息类型值对象，用于描述对象属性的值，为值对象。
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MsgTypeVO {
    EVENT("event","事件消息"),
    TEXT("text","文本消息");

    private String code;
    private String desc;

}
