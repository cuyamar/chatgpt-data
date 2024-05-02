package com.codify;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.codify.chatgpt.data.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Sky
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AliPayTest {
    // 「沙箱环境」应用ID - 您的APPID，收款账号既是你的APPID对应支付宝账号。获取地址；https://open.alipay.com/develop/sandbox/app
    public static String app_id = "9021000136665154";
    // 「沙箱环境」商户私钥，你的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCu+QMFhBa6qWH3Itc3YfcwjMPmH9PFTnpDlCu78VWtsjh2pSE5FoItPkIiPjlVF0PapcG8H5GZD9IskY/99CBHV7McvkDl34rFgJcpR3GdvgYQB+Iq3MR0VhgHgdXjNn+LawhGWCtJHC7L62i9ASFvpWNWdKQjbWwUGhREqGrBUIBg7HTcl+QFAzyjUymqXbO3E3ysbAuD6dQ4OrLzdxo5bzzfvfENqzTpl0TcxbaFVKz+N2sBiwJi8aJ+R9zS4W7SlEn/hcWaFbxn78aQuulwrKnGq+FU+YGNp9eyG1KtHlrcu9+URENnJAxBt1JRDtjD1hLZagkAc7kBV7LPwE8bAgMBAAECggEBAJbF+qWRKejVNpa4Dg4qO9A5LEGroVd/BXogPwNntXXEgkUyjAteavRDlsddKRg6wPyzIttRtKvZZzmCgTrLV9lCKBSXmOK8mx8PbLJkXvIXoa/Eq05WCG9bFL+7UKzG9GcV5v5+0WXhhC2yAAIF7GMBeDUys5HXo286/cYf/kXvAtaBNxls23foE3Nf3MJouGPRlsjh0lZ57wm1ISZ54gIeOlhBBUkryPfoOBHYZRQCKGZfbT6ZnIgQSXezCfMHYykiEN1gmxQOK9PbaO5vwcFPaXmjV/OHQoeNCj47EaaUW3zimbkQV8fOIUK0J88aI3JRC3Q5bgJlWRJFhkypoTECgYEA6nkZa/QRZSx5umZoofk8HMAyCSy7emerCaCAjV28jVinkCL5/lXhMq4enKDwULSFvasyWrOdRhoQa7B/83IbPQz8ERcQ5+BG6ZXORv8jJUYNmsdLZQhC47GUXRndWPZ0pIlc0aFdR1Xtb5KfBXjFz90EFGlrfbiEchCpJ0erS9MCgYEAvwl0mit8DTb26hllxsTyqwe3gynGX/FK5HzTpE+CnkRlCBLLL2JSKlxuUTJ4daqNVqZ4nFHCgutVrn810bb4ZxH1eA/NUbd/2QIojF3RFRnSHJ1pey0P37fNyY5lCuZ3ARN3nL6H1tqhVLdylsP+xThE/wQ5Vnv7iYGqxC+XSpkCgYAEcKCbm7aMnOf9MJ1Am/CYdsc7xz4Rlk710rU8eA7u+kPKBe+H8mZeSE/KFX4XeSOjMM+2SfprbmjrMR9rI7kCUfGf/TjTuzp8h9qtG3214fq1+Joj4qEpoJ43mRrI6XnR98vrKEwU4rqpRzR9rQjJ49yE6m4fplPTuR8K4F+NAwKBgQCeHZJf5CvpuBuHyPufF/WOL3XpXtMtJ8aH9PUrIxAGNK+2je+USXmeTCCWngJryXzwQhrDrzwiJewl4G5IJHvWu1uDQvj6cLmlgI7VFvVt1JA+VN8wLyzbSwNNlsj0y6mlv1oZ5u0BnLU5LBjwxkoBLECXHgTjGv0nY6ZWeNlsyQKBgFAhwcrkFDclo80PrIEx7mK14N8GAsBc+UFAf68mAhiktpYXH2cwY2TE1Nm2+VnpjJ4Nui6/Ic2Js/EcYt0KpTV4odkgQLeTPd+G48fCCtX/ITYIVecHcvxhNuPRNj60vxzpMSBGpnZI3XkkCWVlW1xsVi22NfJvpubAs1OGQVdu";
    // 「沙箱环境」支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAltU9k3JXh2UNik6vq8B51WHBkEmvCHtB85ssUbiaIaw2lG9NWU2zeYUiPjOrTZAouKmzCrO1SkmaIq0eUHNe4LnMdMDAnCdmvlOivUcHVr0RxDVbL+bp1bGklIclPuX/aXixc9vxScDeWKGd42dyuaBUCJ/nD9EgRnVFeK3uP5zYvaJgmOs/ngkrz83XgpH8EgIZH68vCKfS09hhqbOF7jcla/RlYCWEBmCSboFfZ9q70Ao+CRjsBLTjkrqYyMTTZPVQf/eXV2axqd5cmHMwUz9ID7q8mXonKVP+3SuQztvtE9OUpyYIng493VNQ+Q4FqJYyYlJqL4C8sxN6k8b7zwIDAQAB";
    // 「沙箱环境」服务器异步通知回调地址
    public static String notify_url = "http://67166b32.r3.cpolar.top/api/v1/alipay/pay_notify";
    // 「沙箱环境」页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "https://github.com/cuyamar";
    // 「沙箱环境」
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";

    private AlipayClient alipayClient;

    @Before
    public void init() {
        this.alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id,
                merchant_private_key,
                "json",
                charset,
                alipay_public_key,
                sign_type);
    }

    @Test
    public void test_aliPay_pageExecute() throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(notify_url);
        request.setReturnUrl(return_url);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "daniel82AAAA000032333361X03");  // 我们自己生成的订单编号
        bizContent.put("total_amount", "0.01"); // 订单的总金额
        bizContent.put("subject", "测试商品");   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();
        log.info("测试结果：{}", form);

        /**
         * 会生成一个form表单；
         * <form name="punchout_form" method="post" action="https://openapi-sandbox.dl.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=CAAYYDIbvUNRDvY%2B%2BF5vghx2dL9wovodww8CK0%2FferNP1KtyXdytBVLdZKssaFJV%2B8QksVuKlU3qneWhWUuI7atLDgzpussJlJhxTMYQ3GpAfOP4PEBYQFE%2FORemzA2XPjEn88HU7esdJdUxCs602kiFoZO8nMac9iqN6P8deoGWYO4UAwE0RCV65PKeJTcy8mzhOTgkz7V018N9yIL0%2BEBf5iQJaP9tGXM4ODWwFRxJ4l1Egx46FNfjLAMzysy7D14LvTwBi5uDXV4Y%2Bp4VCnkxh3Jhkp%2BDP9SXx6Ay7QaoerxHA09kwYyLQrZ%2FdMZgoQ%2BxSEOgklIZtYj%2FLbfx1A%3D%3D&return_url=https%3A%2F%2Fgaga.plus&notify_url=http%3A%2F%2Fngrok.sscai.club%2Falipay%2FaliPayNotify_url&version=1.0&app_id=9021000132689924&sign_type=RSA2&timestamp=2023-12-13+11%3A36%3A29&alipay_sdk=alipay-sdk-java-4.38.157.ALL&format=json">
         * <input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;100001001&quot;,&quot;total_amount&quot;:&quot;1.00&quot;,&quot;subject&quot;:&quot;测试&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
         * <input type="submit" value="立即支付" style="display:none" >
         * </form>
         * <script>document.forms[0].submit();</script>
         */
    }

    /**
     * 查询订单
     */
    @Test
    public void test_alipay_certificateExecute() throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
        bizModel.setOutTradeNo("daniel82AAAA000032333361X03");
        request.setBizModel(bizModel);

        String body = alipayClient.certificateExecute(request).getBody();
        log.info("测试结果：{}", body);
    }

    /**
     * 退款接口
     */
    @Test
    public void test_alipay_refund() throws AlipayApiException {
        AlipayTradeRefundRequest request =new AlipayTradeRefundRequest();
        AlipayTradeRefundModel refundModel =new AlipayTradeRefundModel();
        refundModel.setOutTradeNo("daniel82AAAA000032333361X03");
        refundModel.setRefundAmount("1.00");
        refundModel.setRefundReason("退款说明");
        request.setBizModel(refundModel);

        AlipayTradeRefundResponse execute = alipayClient.execute(request);
        log.info("测试结果：{}", execute.isSuccess());
    }

}
