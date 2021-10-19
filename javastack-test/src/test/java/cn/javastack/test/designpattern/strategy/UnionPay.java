package cn.javastack.test.designpattern.strategy;

import org.springframework.stereotype.Service;

/**
 * 银联云闪付
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Service("UnionPay")
public class UnionPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("云闪付支付成功");
    }

}
