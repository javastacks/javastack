package cn.javastack.test.designpattern.chain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 支付请求
 * @author: R哥
 * @from: 公众号Java技术栈
 */
@Data
@RequiredArgsConstructor
public class PaymentRequest {

    /**
     * 支付类型，1 - WechatPay、2 - Alipay、3 - CreditCard
     */
    final int type;

}