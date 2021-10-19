package cn.javastack.test.designpattern.factory.abst;

import cn.javastack.test.designpattern.factory.simple.Agent;
import cn.javastack.test.designpattern.factory.simple.Customer;

/**
 * 代理商工厂
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class AgentFactory implements CustomerFactory {

    @Override
    public Customer createCustomer(String type, String name) {
        return new Agent(type, name);
    }

    @Override
    public CustomerExt createCustomerExt() {
        return new AgentExt();
    }

}
