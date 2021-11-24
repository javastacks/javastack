package cn.javastack.test.jdk.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ListRemove {

    public List<String> initList = Arrays.asList("张三", "李四", "周一", "刘四", "李强", "李白");

    /**
     * 普通 for 循环删除
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove1() {
        List<String> list = new ArrayList(initList);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str.startsWith("李")) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    /**
     * 普通 for 循环删除（size提出变量）
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove2() {
        List<String> list = new ArrayList(initList);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String str = list.get(i);
            if (str.startsWith("李")) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    /**
     * 增强 for 循环删除
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove3() {
        List<String> list = new ArrayList(initList);
        for (String element : list) {
            if (element.startsWith("李")) {
                list.remove(element);
            }
        }
        System.out.println(list);
    }

    /**
     * 迭代器循环删除（iterator.remove）
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove4() {
        List<String> list = new ArrayList(initList);
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String str = iterator.next();
            if (str.contains("李")) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    /**
     * 迭代器循环删除（list.remove）
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove5() {
        List<String> list = new ArrayList(initList);
        for (Iterator<String> ite = list.iterator(); ite.hasNext(); ) {
            String str = ite.next();
            if (str.contains("李")) {
                list.remove(str);
            }
        }
        System.out.println(list);
    }

    /**
     * list.forEach 删除
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove6() {
        List<String> list = new ArrayList(initList);
        list.forEach((e) -> {
            if (e.contains("李")) {
                list.remove(e);
            }
        });
        System.out.println(list);
    }

    /**
     * stream filter 过滤
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void remove7() {
        List<String> list = new ArrayList(initList);
        list = list.stream().filter(e -> !e.startsWith("李")).collect(Collectors.toList());
        System.out.println(list);
    }


}
