package cn.javastack.test.designpattern.chain;

/**
 * 支付客户端
 * @author: R哥
 * @from: 公众号Java技术栈
 */
public class PaymentClient {
    public static void main(String[] args) {
        // 创建责任链处理器
        PaymentHandler wechatPayPaymentHandler = new WechatPayHandler();
        AliPayHandler aliPayPaymentHandler = new AliPayHandler();
        PaymentHandler creditCardHandler = new CreditCardHandler();

        // 设置支付责任链
        wechatPayPaymentHandler.setNextHandler(aliPayPaymentHandler);
        aliPayPaymentHandler.setNextHandler(creditCardHandler);

        // 模拟支付宝支付请求
        System.out.println("开始支付宝支付请求");
        wechatPayPaymentHandler.handleRequest(new PaymentRequest(2));
        System.out.println("-----------");

        // 模拟微信支付请求
        System.out.println("开始微信支付请求");
        wechatPayPaymentHandler.handleRequest(new PaymentRequest(1));
        System.out.println("-----------");

        // 模拟信用卡支付请求
        System.out.println("开始信用卡支付请求");
        wechatPayPaymentHandler.handleRequest(new PaymentRequest(3));
    }
}