package cn.javastack.test.designpattern.decorator;

import cn.javastack.test.designpattern.decorator.component.WallBeautify;
import cn.javastack.test.designpattern.decorator.concretecomponent.WallBeautifyClean;
import cn.javastack.test.designpattern.decorator.concretedecorator.WallBeautifyHang;
import cn.javastack.test.designpattern.decorator.concretedecorator.WallBeautifyPaint;
import cn.javastack.test.designpattern.decorator.concretedecorator.WallBeautifyPutty;

/**
 * 装饰器模式测试类
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class DecoratorTest {

    public static void main(String[] args) {
        // 清理墙面
        WallBeautify wallBeautifyClean = new WallBeautifyClean();
        wallBeautifyClean.operation();
        System.out.println("--------------");

        // 刮腻子
        WallBeautify wallBeautifyPutty = new WallBeautifyPutty(wallBeautifyClean);
        wallBeautifyPutty.operation();
        System.out.println("--------------");

        // 涂油漆
        WallBeautify wallBeautifyPaint = new WallBeautifyPaint(wallBeautifyPutty);
        wallBeautifyPaint.operation();
        System.out.println("--------------");

        // 挂壁画
        WallBeautify wallBeautifyHang = new WallBeautifyHang(wallBeautifyPaint);
        wallBeautifyHang.operation();
        System.out.println("--------------");

        // 多层嵌套
        WallBeautify wbh = new WallBeautifyHang(new WallBeautifyPaint(
                new WallBeautifyPutty(new WallBeautifyClean())));
        wbh.operation();
        System.out.println("--------------");
    }

}
