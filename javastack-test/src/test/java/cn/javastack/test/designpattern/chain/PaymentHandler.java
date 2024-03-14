package cn.javastack.test.designpattern.chain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 支付处理器抽象类
 * @author: R哥
 * @from: 公众号Java技术栈
 */
@Data
@RequiredArgsConstructor
public abstract class PaymentHandler {

    /**
     * 支付类型
     */
    private final int type;

    /**
     * 下一个支付处理器
     */
    private PaymentHandler nextHandler;

    /**
     * 处理支付请求（模板方法）
     */
    public final void handleRequest(PaymentRequest paymentRequest) {
        if (paymentRequest.getType() == this.type) {
            System.out.println("找到对应的支付处理器");
            process(paymentRequest);
        } else {
            if (this.nextHandler != null) {
                System.out.println("没找到对应的支付处理器，转发给下一个支付处理器");
                this.nextHandler.handleRequest(paymentRequest);
            } else {
                System.out.println("支付链处理完成。");
            }
        }
    }

    /**
     * 处理器实现类具体处理逻辑
     */
    protected abstract void process(PaymentRequest paymentRequest);

}