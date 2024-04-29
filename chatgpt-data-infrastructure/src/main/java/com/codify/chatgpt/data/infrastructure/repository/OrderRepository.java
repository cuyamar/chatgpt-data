package com.codify.chatgpt.data.infrastructure.repository;

import com.codify.chatgpt.data.domain.order.model.aggregates.CreateOrderAggregate;
import com.codify.chatgpt.data.domain.order.model.entity.ProductEntity;
import com.codify.chatgpt.data.domain.order.model.entity.ShopCartEntity;
import com.codify.chatgpt.data.domain.order.model.entity.UnpaidOrderEntity;
import com.codify.chatgpt.data.domain.order.repository.IOrderRepository;
import com.codify.chatgpt.data.infrastructure.dao.IOpenAIOrderDao;
import com.codify.chatgpt.data.infrastructure.dao.IOpenAIProductDao;
import com.codify.chatgpt.data.infrastructure.dao.IUserAccountDao;
import com.codify.chatgpt.data.infrastructure.po.OpenAIOrderPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Sky
 * 订单仓储服务
 */
@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IOpenAIOrderDao openAIOrderDao;

    @Resource
    private IOpenAIProductDao openAIProductDao;

    @Resource
    private IUserAccountDao userAccountDao;

    @Override
    public UnpaidOrderEntity queryUnpaidOrder(ShopCartEntity shopCartEntity) {
        OpenAIOrderPO openAIOrderPOReq = new OpenAIOrderPO();
        openAIOrderPOReq.setOpenid(shopCartEntity.getOpenid());
        openAIOrderPOReq.setProductId(shopCartEntity.getProductId());
        OpenAIOrderPO openAIOrderPORes = openAIOrderDao.queryUnpaidOrder(openAIOrderPOReq);
        return null;
    }

    @Override
    public ProductEntity queryProduct(Integer productId) {
        return null;
    }

    @Override
    public void saveOrder(CreateOrderAggregate aggregate) {

    }

    @Override
    public boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal totalAmount, Date payTime) {
        return false;
    }

    @Override
    public CreateOrderAggregate queryOrder(String orderId) {
        return null;
    }

    @Override
    public void deliverGoods(String oderId) {

    }

    @Override
    public List<String> queryReplenishmentOrder() {
        return null;
    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return null;
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return null;
    }

    @Override
    public boolean changeOrderClose(String oderId) {
        return false;
    }

    @Override
    public List<ProductEntity> queryProductList() {
        return null;
    }
}
