package cn.javastack.test.designpattern.strategy;

import org.springframework.stereotype.Service;

/**
 * 微信支付
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Service("WechatPay")
public class WechatPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("微信支付成功");
    }

}
