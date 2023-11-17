package cn.javastack.test.designpattern.proxy;

import org.junit.jupiter.api.Test;

/**
 * 来源公众号：Java技术栈
 */
public class ProxyTest {


    /**
     * 测试静态代理
     */
    @Test
    public void testStaticProxy() {
        User user = new User();
        user.setName("tom");
        new StaticProxy(new UserInterfaceImpl()).saveUser(user);
    }

    /**
     * 测试 JDK 动态代理
     */
    @Test
    public void testJDKProxy() {
        User user = new User();
        user.setName("tom");
        JdkDynamicProxy.getUserProxy().saveUser(user);
    }

    /**
     * 测试 CGLIB 基于接口的动态代理
     */
    @Test
    public void testCglibInterfaceProxy() {
        User user = new User();
        user.setName("tom");
        CglibDynamicProxy.getUserProxy().saveUser(user);
    }

    /**
     * 测试 CGLIB 基于类的动态代理
     */
    @Test
    public void testCglibClassProxy() {
        User user = new User();
        user.setName("tom");
        UserInterfaceImpl userImpl = (UserInterfaceImpl) CglibDynamicProxy.getUserProxy(new UserInterfaceImpl());
        userImpl.saveUser(user);
    }

}
