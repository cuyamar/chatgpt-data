package com.codify.chatgpt.data.domain.order.repository;

import com.codify.chatgpt.data.domain.order.model.aggregates.CreateOrderAggregate;
import com.codify.chatgpt.data.domain.order.model.entity.ProductEntity;
import com.codify.chatgpt.data.domain.order.model.entity.ShopCartEntity;
import com.codify.chatgpt.data.domain.order.model.entity.UnpaidOrderEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单仓储接口
 */
public interface IOrderRepository {
    /**
     * 查询未支付订单
     * @param shopCartEntity
     * @return
     */
    UnpaidOrderEntity queryUnpaidOrder(ShopCartEntity shopCartEntity);

    /**
     * 查询商品
     * @param productId
     * @return
     */
    ProductEntity queryProduct(Integer productId);

    /**
     * 保存订单
     * @param aggregate
     */
    void saveOrder(CreateOrderAggregate aggregate);

    /**
     * 查询订单是否支付成功
     * @param orderId
     * @param transactionId
     * @param totalAmount
     * @param payTime
     * @return
     */
    boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal totalAmount, Date payTime);


    /**
     * 查询用户订单
     * @param orderId
     * @return
     */
    CreateOrderAggregate queryOrder(String orderId);


    /**
     * 交付货物
     * @param oderId
     */
    void deliverGoods(String oderId);

    /**
     * 查询补货订单
     * @return
     */
    List<String> queryReplenishmentOrder();

    /**
     * 查询支付通知订单
     * @return
     */
    List<String> queryNoPayNotifyOrder();

    /**
     * 查询超时关闭订单
     * @return
     */
    List<String> queryTimeoutCloseOrderList();

    /**
     * 改变订单关闭
     * @param oderId
     * @return
     */
    boolean changeOrderClose(String oderId);

    /**
     * 查询商品列
     * @return
     */
    List<ProductEntity> queryProductList();
}
