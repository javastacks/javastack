package cn.javastack.test.jdk.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class SortTest {

    public static List<User> LIST = new ArrayList() {
        {
            add(new User("Lisa", 23));
            add(new User("Tom", 11));
            add(new User("John", 16));
            add(new User("Jessie", 26));
            add(new User("Tony", 26));
            add(new User("Messy", 26));
            add(new User("Bob", 19));
            add(new User("Yoga", 65));
        }
    };

    /**
     * jdk8 Collections 排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithCollections() {
        System.out.println("=====jdk8 Collections 排序=====");
        List<User> list = new ArrayList<>(LIST);

        Collections.sort(list, User::compareAge);

//        Collections.sort(list, (u1, u2) -> u1.getAge().compareTo(u2.getAge()));
//        Collections.sort(list, Comparator.comparing(User::getAge));

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 并行 Stream 排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8parallelStream() {
        System.out.println("=====jdk8 Parallel Stream 排序=====");
        List<User> list = new ArrayList<>(LIST);

        list = list.parallelStream().sorted(User::compareAge).collect(Collectors.toList());

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 Stream 排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8Stream() {
        System.out.println("=====jdk8 Stream 排序=====");
        List<User> list = new ArrayList<>(LIST);

        list = list.stream().sorted(User::compareAge).collect(Collectors.toList());

//        list = list.stream().sorted((u1, u2) -> u1.getAge().compareTo(u2.getAge())).collect(Collectors.toList());
//        list = list.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 组合排序，Comparator提供的静态方法，先按年纪排序，年纪相同的按名称排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortGroupWithJdk8() {
        System.out.println("=====jdk8 组合排序=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort(Comparator.comparing(User::getAge).thenComparing(User::getName));

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 Comparator 工具类排序（降序）
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8ComparatorDesc() {
        System.out.println("=====jdk8 降序降序=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort(Comparator.comparing(User::getAge).reversed());

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 Comparator 工具类排序（升序）
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8ComparatorAsc() {
        System.out.println("=====jdk8 Comparator 工具类排序=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort(Comparator.comparing(User::getAge));

//        list.sort(Comparator.comparing((user) -> user.getAge()));

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 静态方法引用排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8StaticMethodRef() {
        System.out.println("=====jdk8 静态方法引用排序=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort(User::compareAge);

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 lambda 排序，不带参数类型
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8Lambda2() {
        System.out.println("=====jdk8 lambda 排序，不带参数类型=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort((u1, u2) -> u1.getAge().compareTo(u2.getAge()));

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 lambda 排序，带参数类型
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8Lambda1() {
        System.out.println("=====jdk8 lambda 排序，带参数类型=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort((User u1, User u2) -> u1.getAge().compareTo(u2.getAge()));

        list.forEach(System.out::println);
        System.out.println();
    }

    /**
     * jdk8 之前的排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortPreJdk8() {
        System.out.println("=====jdk8 之前的排序=====");
        List<User> list = new ArrayList<>(LIST);
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getAge().compareTo(u2.getAge());
            }
        });

        for (User user : list) {
            System.out.println(user);
        }
        System.out.println();
    }

    /**
     * jdk8 实例方法引用排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void sortWithJdk8InstanceMethodRef() {
        System.out.println("=====jdk8 实例方法引用排序=====");
        List<User> list = new ArrayList<>(LIST);

        list.sort(User.getInstance()::compare);

        list.forEach(System.out::println);
        System.out.println();
    }

    @Test
    public void sortSpeed() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(new User("user" + i, i));
        }

        long start = System.currentTimeMillis();
        List<User> list1 = new ArrayList<>(list);
        list1.sort(User::compareAge);
        System.out.println("List.sort: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<User> list2 = new ArrayList<>(list);
        Collections.sort(list2, User::compareAge);
        System.out.println("Collections.sort: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<User> list3 = new ArrayList<>(list);
        list3.stream().sorted(User::compareAge).collect(Collectors.toList());
        System.out.println("Stream.sorted: " + (System.currentTimeMillis() - start));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class User {

        private static final User USER = new User();
        private String name;
        private Integer age;

        public static int compareAge(User u1, User u2) {
            return u1.getAge().compareTo(u2.getAge());
        }

        public static User getInstance() {
            return USER;
        }

        public int compare(User u1, User u2) {
            return u1.getAge().compareTo(u2.getAge());
        }

        @Override
        public String toString() {
            return age + ": " + name;
        }
    }


}
