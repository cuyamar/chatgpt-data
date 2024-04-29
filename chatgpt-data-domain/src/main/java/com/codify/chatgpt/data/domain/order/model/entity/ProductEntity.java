package com.codify.chatgpt.data.domain.order.model.entity;

import com.codify.chatgpt.data.types.enums.OpenAIProductEnableModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: Sky
 * 商品的实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 额度次限
     */
    private Integer quota;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品状态:0无效，1有效
     */
    private OpenAIProductEnableModel enable;

    /**
     * 判断是否有效:true  有效    false  无效
     */
    public boolean isAvailable(){
        return OpenAIProductEnableModel.OPEN.equals(enable);
    }
}
