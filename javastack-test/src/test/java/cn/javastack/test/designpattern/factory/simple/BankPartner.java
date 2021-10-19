package cn.javastack.test.designpattern.factory.simple;

import lombok.Data;
import lombok.ToString;

/**
 * 银行客户
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
@ToString(callSuper = true)
public class BankPartner extends Customer {

    /**
     * 银行编码
     */
    private String code;

    /**
     * 银行地址
     */
    private String address;

    public BankPartner(String name, String type) {
        super(name, type);
    }
}
