package cn.javastack.test.designpattern.chain;

/**
 * 微信支付处理器
 * @author: R哥
 * @from: 公众号Java技术栈
 */
public class WechatPayHandler extends PaymentHandler {

    public WechatPayHandler() {
        super(1);
    }

    @Override
    protected void process(PaymentRequest request) {
        System.out.println("正在处理微信支付");

        // 支付处理逻辑
    }

}