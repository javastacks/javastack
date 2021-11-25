package cn.javastack.test.jdk.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 删除 List 重复元素
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ListRemoveDuplicated {

    /**
     * 3 个张三，2 个李强
     */
    public List<String> initList = Arrays.asList("张三", "李四", "张三", "周一", "刘四", "李强", "李白", "张三", "李强", "王五");

    /**
     * for 循环添加去重
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove1() {
        List<String> list = new ArrayList(initList);
        List<String> list2 = new ArrayList<>();
        for (String element : list) {
            if (!list2.contains(element)) {
                list2.add(element);
            }
        }
        System.out.println(list2);
    }

    /**
     * for 双循环删除
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove2() {
        List<String> list = new ArrayList(initList);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
    }

    /**
     * for 循环重复坐标删除
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove3() {
        List<String> list = new ArrayList(initList);
        List<String> list2 = new ArrayList(initList);
        for (String element : list2) {
            if (list.indexOf(element) != list.lastIndexOf(element)) {
                list.remove(list.lastIndexOf(element));
            }
        }
        System.out.println(list);
    }

    /**
     * Set 去重
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove4() {
        List<String> list = new ArrayList(initList);
        List<String> list2 = new ArrayList(new LinkedHashSet(list));
        System.out.println(list2);
    }

    /**
     * Stream 去重
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove5() {
        List<String> list = new ArrayList(initList);
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println(list);
    }


}
