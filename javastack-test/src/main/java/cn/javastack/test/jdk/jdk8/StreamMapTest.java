package cn.javastack.test.jdk.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class StreamMapTest {

    private static List<String> LIST = Arrays.asList("https://", "www", ".", "javastack", ".", "cn");
    private static List<String> NUMBERS_LIST = Arrays.asList("22", "19", "89", "90");

    public static void main(String[] args) {
        map();
        mapToLong();
        flatMap();
    }

    /**
     * map 转换
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void flatMap() {
        String[] arr1 = {"https://", "www", ".", "javastack", ".", "cn"};
        String[] arr2 = {"公众号", ":", "Java技术栈"};
        String[] arr3 = {"作者", ":", "栈长"};

        System.out.println("=====arrays list=====");
        List<String[]> list = Stream.of(arr1, arr2, arr3).collect(Collectors.toList());
        list.forEach(System.out::print);
        System.out.println("\narrays list size: " + list.size());
        System.out.println();

        System.out.println("=====flatmap list=====");
        List<String> mapList = list.stream().flatMap(Arrays::stream).collect(Collectors.toList());
        mapList.forEach(System.out::print);
        System.out.println("\nflatmap list size: " + mapList.size());
        System.out.println();
    }

    /**
     * map 转换
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void map() {
        System.out.println("=====map list=====");
        List<String> mapList = LIST.stream().map(e -> e.concat("---")).collect(Collectors.toList());
        mapList.forEach(System.out::print);
        System.out.println("\nmap list size: " + mapList.size());
        System.out.println();
    }

    /**
     * mapToLong 转换
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void mapToLong() {
        System.out.println("=====map to long list=====");
        List<Long> longList = NUMBERS_LIST.stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
        longList.forEach(System.out::println);
        System.out.println("map to long list size: " + longList.size());
        System.out.println();
    }

}
