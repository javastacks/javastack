package cn.javastack.test.jdk.collection;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 删除 Map 重复元素
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class MapRemoveDuplicated {

    public Map<String, String> initMap = new HashMap<>() {{
        put("user1", "张三");
        put("user2", "李四");
        put("user3", "张三");
        put("user4", "李四");
        put("user5", "王五");
        put("user6", "赵六");
        put("user7", "李四");
        put("user8", "王五");
    }};

    /**
     * 新创建 Map 添加不重复元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void removeDuplicated1() {
        Map<String, String> map = new HashMap<>();
        initMap.forEach((k, v) -> {
            if (!map.containsValue(v)) {
                map.put(k, v);
            }
        });
        System.out.println(map);
    }

    /**
     * 添加 Set 再删除重复元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void removeDuplicated2() {
        Set<String> set = new HashSet<>();
        Iterator<Map.Entry<String, String>> iterator = initMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (!set.add(entry.getValue())) {
                iterator.remove();
            }
        }
        System.out.println(initMap);
    }

    /**
     * 使用 Stream 删除重复元素
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void removeDuplicated3() {
        Map<String, String> resultMap = initMap.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (key1, key2) -> key1)
        ).entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (key1, key2) -> key1)
        );
        System.out.println(resultMap);
    }


}
