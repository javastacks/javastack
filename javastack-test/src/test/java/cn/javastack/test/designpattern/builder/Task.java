package cn.javastack.test.designpattern.builder;

import java.util.Date;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class Task {

    private long id;
    private String name;
    private String content;
    private int type;
    private int status;
    private Date finishDate;

    private Task(TaskBuilder taskBuilder) {
        this.id = taskBuilder.id;
        this.name = taskBuilder.name;
        this.content = taskBuilder.content;
        this.type = taskBuilder.type;
        this.status = taskBuilder.status;
        this.finishDate = taskBuilder.finishDate;
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    public static class TaskBuilder {

        private long id;
        private String name;
        private String content;
        private int type;
        private int status;
        private Date finishDate;

        public TaskBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public TaskBuilder content(String content) {
            this.content = content;
            return this;
        }

        public TaskBuilder type(int type) {
            this.type = type;
            return this;
        }

        public TaskBuilder status(int status) {
            this.status = status;
            return this;
        }

        public TaskBuilder finishDate(Date finishDate) {
            this.finishDate = finishDate;
            return this;
        }

        public Task build(){
            return new Task(this);
        }

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", finishDate=" + finishDate +
                '}';
    }

}
