package cn.javastack.test.jdk.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class StreamSearch {


    public static List<User> list = new ArrayList<>();

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @BeforeEach
    public void initList() {
        list.add(new User("公众号Java技术栈-Petty", 22, 1));
        list.add(new User("公众号Java技术栈-Tom", 38, 1));
        list.add(new User("公众号Java技术栈-Jessica", 43, 0));
        list.add(new User("公众号Java技术栈-John", 15, 1));
        list.add(new User("公众号Java技术栈-Lily", 25, 0));
        list.add(new User("公众号Java技术栈-Lambs", 28, 0));
        list.add(new User("公众号Java技术栈-Jack", 45, 1));
        list.add(new User("公众号Java技术栈-Addy", 9, 0));
        list.add(new User("公众号Java技术栈-Bob", 61, 1));
        list.add(new User("公众号Java技术栈-Candy", 26, 0));
    }

    /**
     * 集合过滤
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void filter() {
        System.out.println("搜索所有姓名含有 c 的人");
        list.stream().filter(u -> u.getName().contains("c")).forEach(System.out::println);
    }

    /**
     * 集合搜索第一个
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void findFirst() {
        System.out.println("搜索第一个年经大于 30 的人");
        User user = list.stream().filter(u -> u.getAge() > 30).findFirst().get();
        System.out.println(user);
    }

    /**
     * 集合搜索任意一个
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void findAny() {
        System.out.println("搜索任意一个年经大于 30 的人");
        User user = list.parallelStream().filter(u -> u.getAge() > 30).findAny().get();
        System.out.println(user);
    }

    /**
     * 集合匹配任意元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void anyMatch() {
        System.out.println("是否存在 Jack：" + list.stream().anyMatch(u -> u.getName().contains("Jack")));
        System.out.println("是否存在 Jet：" + list.stream().anyMatch(u -> u.getName().contains("Jet")));
    }

    /**
     * 集合不匹配任意元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void noneMatch() {
        System.out.println("是否不存在 Jack：" + list.stream().noneMatch(u -> u.getName().contains("Jack")));
        System.out.println("是否不存在 Jet：" + list.stream().noneMatch(u -> u.getName().contains("Jet")));
    }

    /**
     * 集合匹配全部元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void allMatch() {
        System.out.println("所有人的年纪都大于3：" + list.stream().allMatch(u -> u.getAge() > 2));
        System.out.println("所有人的年纪都大于30：" + list.stream().allMatch(u -> u.getAge() > 30));
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @AllArgsConstructor
    @Data
    class User {

        private String name;
        private int age;
        private int sex;

    }


}