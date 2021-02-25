package cn.javastack.test.designpattern.builder;

import java.util.Date;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class Test {

    public static void main(String[] args) {
        testBuilder();
        testLombokBuilder();
        testLombokBuilder2();
        testJava8Builder();
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void testBuilder() {
        Task task = new Task.TaskBuilder(99, "紧急任务")
                .type(1)
                .content("处理一下这个任务")
                .status(0)
                .finishDate(new Date())
                .build();
        System.out.println(task);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void testLombokBuilder() {
        LombokTask lombokTask = new LombokTask.LombokTaskBuilder()
                .id(99)
                .name("紧急任务")
                .type(1)
                .content("处理一下这个任务")
                .status(0)
                .finishDate(new Date())
                .build();
        System.out.println(lombokTask);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void testLombokBuilder2() {
        LombokTask lombokTask = LombokTask.builder()
                .id(99)
                .name("紧急任务")
                .type(1)
                .content("处理一下这个任务")
                .status(0)
                .finishDate(new Date())
                .build();
        System.out.println(lombokTask);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void testJava8Builder() {
        Java8Task java8Task = GenericBuilder.of(Java8Task::new)
                .with(Java8Task::setId, 99L)
                .with(Java8Task::setName, "紧急任务")
                .with(Java8Task::setType, 1)
                .with(Java8Task::setContent, "处理一下这个任务")
                .with(Java8Task::setStatus, 0)
                .with(Java8Task::setFinishDate, new Date())
                .build();
        System.out.println(java8Task);
    }

}
