package com.codify.chatgpt.data.domain.order.service;

import com.codify.chatgpt.data.domain.order.model.entity.*;
import com.codify.chatgpt.data.domain.order.model.valobj.PayStatusVO;
import com.codify.chatgpt.data.domain.order.repository.IOrderRepository;
import com.codify.chatgpt.data.types.common.Constants;
import com.codify.chatgpt.data.types.exception.ChatGPTException;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Sky
 * 抽象订单服务
 */
@Slf4j
public abstract class AbstractOrderService implements IOrderService{
    @Resource
    protected IOrderRepository orderRepository;

    @Override
    public PayOrderEntity createOrder(ShopCartEntity shopCartEntity){
        try {
            // 基础配置
            String openid = shopCartEntity.getOpenid();
            Integer productId = shopCartEntity.getProductId();

            //1.查询有效的未支付订单，如果有则直接返回微信支付
            UnpaidOrderEntity unpaidOrderEntity = orderRepository.queryUnpaidOrder(shopCartEntity);
            if(unpaidOrderEntity != null && PayStatusVO.WAIT.equals(unpaidOrderEntity.getPayStatus()) && unpaidOrderEntity.getPayUrl() != null){
                log.info("创建订单-存在，已生成微信支付，返回 openid: {} orderId: {} payUrl: {}", openid, unpaidOrderEntity.getOrderId(), unpaidOrderEntity.getPayUrl());
                return PayOrderEntity.builder()
                        .orderId(unpaidOrderEntity.getOrderId())
                        .openid(openid)
                        .payUrl(unpaidOrderEntity.getPayUrl())
                        .payStatus(unpaidOrderEntity.getPayStatus())
                        .build();
            }else if(null != unpaidOrderEntity && unpaidOrderEntity.getPayUrl() == null){
                log.info("创建订单-存在，未生成微信支付，返回 openid: {} orderId: {}", openid, unpaidOrderEntity.getOrderId());
                PayOrderEntity payOrderEntity = this.doPrepayOrder(openid, unpaidOrderEntity.getOrderId(), unpaidOrderEntity.getProductName(), unpaidOrderEntity.getTotalAmount());
                log.info("创建订单-完成，生成支付单，openid: {} orderId: {} payUrl: {}", openid, payOrderEntity.getOrderId(), payOrderEntity.getPayUrl());
                return payOrderEntity;
            }

            //2.商品查询
            ProductEntity productEntity = orderRepository.queryProduct(productId);
            if(!productEntity.isAvailable()){
                throw new ChatGPTException(Constants.ResponseCode.ORDER_PRODUCT_ERR.getCode(), Constants.ResponseCode.ORDER_PRODUCT_ERR.getInfo());
            }

            //3.保存订单
            OrderEntity orderEntity = this.doSaveOrder(openid,productEntity);

            //4.创建支付
            PayOrderEntity payOrderEntity = this.doPrepayOrder(openid, orderEntity.getOrderId(), productEntity.getProductName(), orderEntity.getTotalAmount());
            log.info("创建订单-完成，生成支付单。openid: {} orderId: {} payUrl: {}", openid, orderEntity.getOrderId(), payOrderEntity.getPayUrl());

            return payOrderEntity;
        }catch (Exception e){
            log.error("创建订单，已生成微信支付，返回 openid: {} productId: {}", shopCartEntity.getOpenid(), shopCartEntity.getProductId());
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }

    protected abstract OrderEntity doSaveOrder(String openid, ProductEntity productEntity);

    protected abstract PayOrderEntity doPrepayOrder(String openid, String orderId, String productName, BigDecimal amountTotal);
}
