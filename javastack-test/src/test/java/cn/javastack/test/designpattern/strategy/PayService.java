package cn.javastack.test.designpattern.strategy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付服务
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@RestController
public class PayService {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 支付接口
     * @param amount
     * @param paymentType
     * @return
     */
    @RequestMapping("/pay")
    public PayResult pay(@RequestParam("amount") int amount,
                    @RequestParam("paymentType") String paymentType) {
        Order order = new Order();
        order.setAmount(amount);
        order.setPaymentType(paymentType);

        // 根据支付类型获取对应的策略 bean
        IPayment payment = applicationContext.getBean(order.getPaymentType(), IPayment.class);

        // 开始支付
        PayResult payResult = payment.pay(order);

        return payResult;
    }

}
