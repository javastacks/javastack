package cn.javastack.test.designpattern.factory.abst;

import cn.javastack.test.designpattern.factory.simple.Customer;
import cn.javastack.test.designpattern.factory.simple.Merchant;

/**
 * 商户工厂
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class MerchantFactory implements CustomerFactory {

    @Override
    public Customer createCustomer(String type, String name) {
        return new Merchant(type, name);
    }

    @Override
    public CustomerExt createCustomerExt() {
        return new MerchantExt();
    }

}
