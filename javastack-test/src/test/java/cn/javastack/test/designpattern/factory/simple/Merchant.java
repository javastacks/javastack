package cn.javastack.test.designpattern.factory.simple;

import lombok.Data;
import lombok.ToString;

/**
 * 商户
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
@ToString(callSuper = true)
public class Merchant extends Customer {

    /**
     * 合同类型
     */
    private int contractType;

    /**
     * 结算周期（天）
     */
    private int settmentDays;

    public Merchant(String name, String type) {
        super(name, type);
    }
}
