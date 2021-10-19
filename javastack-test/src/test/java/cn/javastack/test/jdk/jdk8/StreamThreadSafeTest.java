package cn.javastack.test.jdk.jdk8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class StreamThreadSafeTest {

    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "公众号：Java技术栈 000");


    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void test1() {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list1.add(i);
        }

        List<Integer> list2 = new ArrayList<>();
        list1.parallelStream().forEach(e -> list2.add(e));
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void test2() {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add("hi " + i);
        }

        threadLocal.set("公众号：Java技术栈 111");

        System.out.println("=====stream=====");

        List list2 = list1.stream().map(e -> {
            String result = e + " " + threadLocal.get();
            return result;
        }).collect(Collectors.toList());
        list2.forEach(System.out::println);

        System.out.println("=====parallelStream=====");

        List list3 = list1.parallelStream().map(e -> {
            String result = e + " " + threadLocal.get();
            System.out.println(Thread.currentThread().getName());
            return result;
        }).collect(Collectors.toList());
        list3.forEach(System.out::println);
    }


}
