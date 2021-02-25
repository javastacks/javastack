package cn.javastack.test.designpattern.builder;

import lombok.Builder;

import java.util.Date;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Builder
public class LombokTask {

    private long id;
    private String name;
    private String content;
    private int type;
    private int status;
    private Date finishDate;


}
