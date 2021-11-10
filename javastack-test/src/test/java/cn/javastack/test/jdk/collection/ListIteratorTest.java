package cn.javastack.test.jdk.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ListIteratorTest {

    private static List<Integer> list = new ArrayList<>();

    static {
        for (int i = 0; i < 100000000; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        normalFor();
        enhancedFor();
        iterator();
        forEach();
    }

    /**
     * 普通 for 循环遍历
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void normalFor() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            Integer element = list.get(i);
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("for:" + time);
    }

    /**
     * 增强 for 循环遍历
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void enhancedFor() {
        long start = System.currentTimeMillis();
        for (Integer element : list) {

        }
        long time = System.currentTimeMillis() - start;
        System.out.println("enhanced for:" + time);
    }

    /**
     * 迭代器 for 循环遍历
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void iterator() {
        long start = System.currentTimeMillis();
        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
            Integer element = iterator.next();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("iterator:" + time);
    }

    /**
     * 集合自带 forEach 遍历
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void forEach() {
        long start = System.currentTimeMillis();
        list.forEach(e -> {
        });
        long time = System.currentTimeMillis() - start;
        System.out.println("forEach:" + time);
    }

}
