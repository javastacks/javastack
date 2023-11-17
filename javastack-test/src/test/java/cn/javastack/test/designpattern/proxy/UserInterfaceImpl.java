package cn.javastack.test.designpattern.proxy;

/**
 * 来源公众号：Java技术栈
 */
public class UserInterfaceImpl implements UserInterface {

    @Override
    public boolean saveUser(User user) {
        System.out.println("保存用户：" + user.getName());
        return true;
    }

}
