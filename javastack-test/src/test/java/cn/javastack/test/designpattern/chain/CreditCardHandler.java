package cn.javastack.test.designpattern.chain;

/**
 * 信用卡处理器
 * @author: R哥
 * @from: 公众号Java技术栈
 */
public class CreditCardHandler extends PaymentHandler {

    public CreditCardHandler() {
        super(3);
    }

    @Override
    protected void process(PaymentRequest request) {
        System.out.println("正在处理信用卡支付");

        // 支付处理逻辑
    }

}