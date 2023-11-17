package cn.javastack.test.designpattern.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.Proxy;


/**
 * 来源公众号：Java技术栈
 */
public class CglibDynamicProxy {

    /**
     * CGLIB 基于接口实现动态代理
     */
    private static final InvocationHandler INTERFACE_USER_HANDLE = (proxy, method, args) -> {
        System.out.println("Cglib接口动态代理-开始保存用户");
        Object result = method.invoke(new UserInterfaceImpl(), args);
        System.out.println("Cglib接口动态代理-保存用户结果：" + result);
        System.out.println();
        return result;
    };

    public static UserInterface getUserProxy() {
        return (UserInterface) Proxy.newProxyInstance(CglibDynamicProxy.class.getClassLoader(),
                new Class[]{UserInterface.class}, INTERFACE_USER_HANDLE);
    }

    /**
     * CGLIB 基于类实现动态代理
     */
    private static final MethodInterceptor CLASS_USER_HANDLE = (obj, method, args, proxy) -> {
        System.out.println("Cglib类动态代理-开始保存用户");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("Cglib类动态代理-保存用户结果：" + result);
        System.out.println();
        return result;
    };

    public static Object getUserProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(CLASS_USER_HANDLE);
        return enhancer.create();
    }

}