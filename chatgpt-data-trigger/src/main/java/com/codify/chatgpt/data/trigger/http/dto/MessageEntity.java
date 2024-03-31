package com.codify.chatgpt.data.trigger.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sky
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    private String role;
    private String content;
    private String name;

}

