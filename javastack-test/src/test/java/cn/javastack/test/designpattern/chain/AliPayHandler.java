package cn.javastack.test.designpattern.chain;

/**
 * 支付宝处理器
 * @author: R哥
 * @from: 公众号Java技术栈
 */
public class AliPayHandler extends PaymentHandler {

    public AliPayHandler() {
        super(2);
    }

    @Override
    protected void process(PaymentRequest request) {
        System.out.println("正在处理支付宝支付");

        // 支付处理逻辑
    }

}