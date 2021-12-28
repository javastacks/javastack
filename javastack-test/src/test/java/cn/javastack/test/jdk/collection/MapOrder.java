package cn.javastack.test.jdk.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map 排序
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class MapOrder {

    public static void main(String[] args) {
        hashOrder();
        insertOrder();
        accessOrder();
    }

    /**
     * HashMap排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void hashOrder() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("key1", "Java技术栈1");
        hashMap.put("key2", "Java技术栈2");
        hashMap.put("key3", "Java技术栈3");
        hashMap.put("key4", "Java技术栈4");
        hashMap.put("key5", "Java技术栈5");
        hashMap.put("key6", "Java技术栈6");
        hashMap.put("key7", "Java技术栈7");
        hashMap.put("key8", "Java技术栈8");
        print("hash order: ", hashMap);
    }

    /**
     * 插入排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void insertOrder() {
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("key1", "Java技术栈1");
        linkedHashMap.put("key2", "Java技术栈2");
        linkedHashMap.put("key3", "Java技术栈3");
        linkedHashMap.put("key4", "Java技术栈4");
        linkedHashMap.put("key5", "Java技术栈5");
        linkedHashMap.put("key6", "Java技术栈6");
        linkedHashMap.put("key7", "Java技术栈7");
        linkedHashMap.put("key8", "Java技术栈8");
        print("insert order: ", linkedHashMap);
    }

    /**
     * 访问排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void accessOrder() {
        Map<String, String> linkedHashMap = new LinkedHashMap<>(8, 0.75f, true);
        linkedHashMap.put("key1", "Java技术栈1");
        linkedHashMap.put("key2", "Java技术栈2");
        linkedHashMap.put("key3", "Java技术栈3");
        linkedHashMap.put("key4", "Java技术栈4");
        linkedHashMap.put("key5", "Java技术栈5");
        linkedHashMap.put("key6", "Java技术栈6");
        linkedHashMap.put("key7", "Java技术栈7");
        linkedHashMap.put("key8", "Java技术栈8");

        linkedHashMap.get("key6");
        linkedHashMap.get("key8");
        linkedHashMap.get("key3");
        linkedHashMap.get("key1");
        System.out.println();
        print("access order: ", linkedHashMap);

    }

    /**
     * 遍历打印 Map
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void print(String info, Map<String, String> map) {
        System.out.println(info);
        map.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
    }


}
