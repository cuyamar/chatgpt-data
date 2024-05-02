package com.codify.chatgpt.data.trigger.http;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.codify.chatgpt.data.domain.auth.service.IAuthService;
import com.codify.chatgpt.data.domain.order.model.entity.PayOrderEntity;
import com.codify.chatgpt.data.domain.order.model.entity.ShopCartEntity;
import com.codify.chatgpt.data.domain.order.service.IOrderService;
import com.codify.chatgpt.data.trigger.http.dto.SaleProductDTO;
import com.codify.chatgpt.data.types.common.Constants;
import com.codify.chatgpt.data.types.model.Response;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Sky
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/${app.config.api-version}/alipay/")
public class AliPayController {
    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;
    @Resource
    private IOrderService orderService;

    @Resource
    private EventBus eventBus;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * http://localhost:8091/api/v1/alipay/create_pay_order?userId=xfg&productId=1001
     *
     * @param
     * @param productId
     * @return
     */
    @RequestMapping(value = "create_pay_order", method = RequestMethod.POST)
    public Response<String> createParOrder(@RequestParam String userId, @RequestParam String productId) {
        try {
            log.info("商品下单，根据商品ID创建支付单开始 userId:{} productId:{}", userId, productId);
            ShopCartEntity shopCartEntity = ShopCartEntity.builder().openid(userId).productId(Integer.parseInt(productId)).build();
            PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);
            log.info("商品下单，根据商品ID创建支付单完成 userId:{} productId:{} orderId:{}", userId, productId, payOrderEntity.getOrderId());
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(payOrderEntity.getPayUrl())
                    .build();
        } catch (Exception e) {
            log.error("商品下单，根据商品ID创建支付单失败 userId:{} productId:{}", userId, productId, e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


    @RequestMapping(value = "pay_notify", method = RequestMethod.POST)
    public String payNotify(HttpServletRequest request) {
        try {
            log.info("支付回调，消息接收 {}", request.getParameter("trade_status"));
            if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
                Map<String, String> params = new HashMap<>();
                Map<String, String[]> requestParams = request.getParameterMap();
                for (String name : requestParams.keySet()) {
                    params.put(name, request.getParameter(name));
                }

                String tradeNo = params.get("out_trade_no");
                String gmtPayment = params.get("gmt_payment");
                String alipayTradeNo = params.get("trade_no");

                String sign = params.get("sign");
                String content = AlipaySignature.getSignCheckContentV1(params);
                boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名
                // 支付宝验签
                if (checkSignature) {
                    // 验签通过
                    log.info("支付回调，交易名称: {}", params.get("subject"));
                    log.info("支付回调，交易状态: {}", params.get("trade_status"));
                    log.info("支付回调，支付宝交易凭证号: {}", params.get("trade_no"));
                    log.info("支付回调，商户订单号: {}", params.get("out_trade_no"));
                    log.info("支付回调，交易金额: {}", params.get("total_amount"));
                    log.info("支付回调，买家在支付宝唯一id: {}", params.get("buyer_id"));
                    log.info("支付回调，买家付款时间: {}", params.get("gmt_payment"));
                    log.info("支付回调，买家付款金额: {}", params.get("buyer_pay_amount"));
                    log.info("支付回调，支付回调，更新订单 {}", tradeNo);
                    // 更新订单未已支付
                    orderService.changeOrderPaySuccess(tradeNo,params.get("trade_no"),new BigDecimal(params.get("total_amount")).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP),dateFormat.parse(params.get("gmt_payment")));
                    // 推送消息【自己的业务场景中可以使用MQ消息】
                    eventBus.post(tradeNo);
                }
            }
            return "success";
        } catch (Exception e) {
            log.error("支付回调，处理失败", e);
            return "false";
        }
    }

}
