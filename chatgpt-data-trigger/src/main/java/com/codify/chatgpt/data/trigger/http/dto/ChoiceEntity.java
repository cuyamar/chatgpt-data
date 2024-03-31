package com.codify.chatgpt.data.trigger.http.dto;

import lombok.Data;

/**
 * @author: Sky
 */
@Data
public class ChoiceEntity {

    /** stream = true 请求参数里返回的属性是 delta */
    private MessageEntity delta;
    /** stream = false 请求参数里返回的属性是 delta */
    private MessageEntity message;

}

