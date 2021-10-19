package cn.javastack.test.designpattern.strategy;

/**
 * 支付接口
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public interface IPayment {

    /**
     * 支付
     * @param order
     * @return
     */
    PayResult pay(Order order);

}
