package cn.javastack.test.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 来源公众号：Java技术栈
 */
public class JdkDynamicProxy {

    private static final InvocationHandler USER_HANDLE = (proxy, method, args) -> {
        System.out.println("JDK接口动态代理-开始保存用户：");
        Object result = method.invoke(new UserInterfaceImpl(), args);
        System.out.println("JDK接口动态代理-保存用户结果：" + result);
        System.out.println();
        return result;
    };

    public static UserInterface getUserProxy() {
        return (UserInterface) Proxy.newProxyInstance(JdkDynamicProxy.class.getClassLoader(),
                new Class[]{UserInterface.class}, USER_HANDLE);
    }

}