package cn.javastack.test.designpattern.strategy;

import lombok.Data;

/**
 * 订单信息
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
public class Order {

    /**
     * 金额
     */
    private int amount;

    /**
     * 支付类型
     */
    private String paymentType;

}
