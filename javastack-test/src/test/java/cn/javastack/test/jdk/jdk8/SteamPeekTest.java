package cn.javastack.test.jdk.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamPeekTest {

    private List<String> languageList = new ArrayList<String>() {{
        add("java");
        add("python");
        add("c++");
        add("php");
        add("go");
    }};

    private List<User> userList = new ArrayList<User>() {{
        add(new User("张三"));
        add(new User("李四"));
        add(new User("王五"));
        add(new User("赵六"));
    }};

    @Test
    public void peekTest1() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    @Test
    public void peekTest2() {
        languageList.stream()
//                .peek(e -> "hi " + e)
                .forEach(System.out::println);
    }

    @Test
    public void peekTest3() {
        userList.stream()
                .peek(user -> user.setName("peek: " + user.getName()))
                .forEach(System.out::println);
    }

    @Test
    public void mapTest() {
        languageList.stream()
                .map(e -> "hi: " + e)
                .forEach(System.out::println);
    }

    @Data
    @AllArgsConstructor
    class User {

        private String name;

    }


}
