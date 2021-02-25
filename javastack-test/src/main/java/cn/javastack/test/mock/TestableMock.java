package cn.javastack.test.mock;


/**
 * @from 公众号：Java技术栈
 * @author 栈长
 */
public class TestableMock {

    /**
     * 调用任意方法
     */
    public String commonMethod() {
        return " www ".trim() + "." + " javastack".substring(1) + "www.javastack.cn".startsWith(".com");
    }


    /**
     * 调用成员、静态方法
     */
    public String memberMethod(String s) {
        return "{ \"result\": \"" + innerMethod(s) + staticMethod() + "\"}";
    }

    private static String staticMethod() {
        return "WWW_JAVASTACK_CN";
    }

    private String innerMethod(String website) {
        return "our website is: " + website;
    }


}
