package cn.javastack.test.designpattern.factory.abst;

import lombok.Data;
import lombok.ToString;

/**
 * 商户
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
@ToString(callSuper = true)
public class MerchantExt extends CustomerExt {

    /**
     * 介绍人
     */
    private int introduceName;

    /**
     * 介绍人电话
     */
    private String introduceTel;

}
