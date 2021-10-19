package cn.javastack.test.designpattern.factory.simple;

/**
 * 客户简单工厂
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class CustomerFactory {

    private static Merchant createMerchant(String type, String name) {
        return new Merchant(type, name);
    }

    private static BankPartner createBankPartner(String type, String name) {
        return new BankPartner(type, name);
    }

    private static Agent createAgent(String type, String name) {
        return new Agent(type, name);
    }

    public static Customer create(String type, String name) {
        if ("M".equals(type)) {
            return createMerchant(type, name);
        } else if ("B".equals(type)) {
            return createBankPartner(type, name);
        } else if ("A".equals(type)) {
            return createAgent(type, name);
        }
        return null;
    }

}
