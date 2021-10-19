package cn.javastack.test.designpattern.factory.abst;

import cn.javastack.test.designpattern.factory.simple.BankPartner;
import cn.javastack.test.designpattern.factory.simple.Customer;

/**
 * 银行客户工厂
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class BankPartnerFactory implements CustomerFactory {

    @Override
    public Customer createCustomer(String type, String name) {
        return new BankPartner(type, name);
    }

    @Override
    public CustomerExt createCustomerExt() {
        return new BankPartnerExt();
    }

}
