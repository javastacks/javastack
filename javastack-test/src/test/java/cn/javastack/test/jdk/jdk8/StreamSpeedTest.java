package cn.javastack.test.jdk.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class StreamSpeedTest {

    private static final int MAX = 10000000;

    private static List<SortTest.User> LIST = new ArrayList<>();

    private static void init(int size) {
        LIST.clear();
        for (int i = 0; i < size; i++) {
            LIST.add(new SortTest.User("user" + i, i));
        }
    }

    public static void main(String[] args) {
        int size = MAX;
        while (size > 10) {
            init(size);

//            streamSort();
//            parallelStreamSort();

            if (size < 10000) {
                streamProcess();
                parallelStreamProcess();
            }

            size /= 10;
        }
    }

    /**
     * 顺序流排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void streamSort() {
        long start = System.currentTimeMillis();
        List<SortTest.User> list = new ArrayList<>(LIST);

        list.stream().sorted(SortTest.User::compareAge).collect(Collectors.toList());

        System.out.println("\nList size: " + list.size() + " Stream.sorted: " + (System.currentTimeMillis() - start));
    }

    /**
     * 并行流排序
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void parallelStreamSort() {
        long start = System.currentTimeMillis();
        List<SortTest.User> list = new ArrayList<>(LIST);

        list.parallelStream().sorted(SortTest.User::compareAge).collect(Collectors.toList());

        System.out.println("List size: " + list.size() + " ParallelStream.sorted: " + (System.currentTimeMillis() - start));
    }

    /**
     * 顺序流数据处理
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void streamProcess() {
        long start = System.currentTimeMillis();
        List<SortTest.User> list = new ArrayList<>(LIST);

        list.stream().map(StreamSpeedTest::process).collect(Collectors.toList());

        System.out.println("\nList size: " + list.size() + " Stream process: " + (System.currentTimeMillis() - start));
    }

    /**
     * 并行流数据处理
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void parallelStreamProcess() {
        long start = System.currentTimeMillis();
        List<SortTest.User> list = new ArrayList<>(LIST);

        list.parallelStream().map(StreamSpeedTest::process).collect(Collectors.toList());

        System.out.println("List size: " + list.size() + " ParallelStream process: " + (System.currentTimeMillis() - start));
    }


    /**
     * 数据处理
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static SortTest.User process(SortTest.User user) {
        try {
            user.setName(user.getName() + ": process");
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

}
