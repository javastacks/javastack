package cn.javastack.test.designpattern.factory.method;

import cn.javastack.test.designpattern.factory.simple.Customer;

/**
 * 工厂方法客户接口
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public interface CustomerFactory {

    Customer create(String type, String name);

}
