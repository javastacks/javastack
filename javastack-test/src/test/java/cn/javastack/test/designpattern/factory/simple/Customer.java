package cn.javastack.test.designpattern.factory.simple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Customer {

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户类型
     */
    private String type;

}
