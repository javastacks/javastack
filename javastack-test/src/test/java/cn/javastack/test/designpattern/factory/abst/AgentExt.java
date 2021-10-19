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
public class AgentExt extends CustomerExt {

    /**
     * 来源
     */
    private String source;

    /**
     * 资质
     */
    private String certification;

}
