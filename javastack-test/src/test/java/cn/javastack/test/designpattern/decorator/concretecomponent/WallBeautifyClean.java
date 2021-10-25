package cn.javastack.test.designpattern.decorator.concretecomponent;

import cn.javastack.test.designpattern.decorator.component.WallBeautify;

/**
 * 墙面装修基本实现（清理墙面）
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class WallBeautifyClean implements WallBeautify {

    @Override
    public void operation() {
        System.out.println("开始清理墙面");
    }

}
