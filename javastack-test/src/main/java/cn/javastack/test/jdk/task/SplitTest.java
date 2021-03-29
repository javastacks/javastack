package cn.javastack.test.jdk.task;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class SplitTest {

    private static final int MAX_LOOP = 1;

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.toString());
        for (int i = 0; i < 1000; i++) {
            sb.append(new Random().nextInt()).append(" ");
        }
        split(sb.toString());
        stringTokenizer(sb.toString());
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void split(String str) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_LOOP; i++) {
            String[] arr = str.split("\\s");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j]);
            }
        }
        System.out.printf("split 耗时 %s ms\n", System.currentTimeMillis() - start);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void stringTokenizer(String str) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_LOOP; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
            StringBuilder sb = new StringBuilder();
            while (stringTokenizer.hasMoreTokens()) {
                sb.append(stringTokenizer.nextToken());
            }
        }
        System.out.printf("StringTokenizer 耗时 %s ms", System.currentTimeMillis() - start);
    }

}
