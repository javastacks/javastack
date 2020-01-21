在之前的文章：[Spring Boot读取配置的几种方式](https://mp.weixin.qq.com/s/aen2PIh0ut-BSHad-Bw7hg)，我介绍到 Spring Boot 中基于 Java Bean 的参数绑定，在一个 Java Bean 类上用 `@ConfigurationProperties` 注解标识（更多 Spring Boot 的教程请关注公众号 "Java技术栈" 在后台回复：boot）。

前几天，Spring Boot 2.2.0 正式发布了：[Spring Boot 2.2.0 正式发布，支持 JDK 13！](https://mp.weixin.qq.com/s/3TF6ooiW3JUzSGeAiQ9m8g)，文中有提到基于构造器的参数绑定，那么今天栈长就带大家来实践一下，到底怎么用，有什么用。

**废话不说，先上示例代码：**

```
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * 微信公众号：Java技术栈
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "tom")
public class TomProperties {

    private String name;
    private String sex;
    private int age;
    private String country;
    private Date entryTime;

    public TomProperties(String name,
                         String sex,
                         int age,
                         @DefaultValue("China") String country,
                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date entryTime) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.country = country;
        this.entryTime = entryTime;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "TomProperties{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", entryTime=" + entryTime +
                '}';
    }

}
```

**配置文件内容：**

```
tom:
  name: Tom
  sex: man
  age: 18
  entry-time: 2012-12-12 12:00:00
```

**参数结果输出：**

> TomProperties{name='Tom', sex='man', age=18, country='China', entryTime=Wed Dec 12 12:00:00 CST 2012}

通过构造器的参数绑定，其实就是在 `@ConfigurationProperties` 注解的基础上再添加一个 '@ConstructorBinding' 注解。

**@ConstructorBinding几点总结：**

1、用了 `@ConstructorBinding` 这个注解，就标识这个类的参数优先通过带参数的构造器注入，如果没有带参数的构造器则再通过  setters  注入；

怎么判断是通过 setters 注入还是构造器注入，请看这个类的源码：

> org.springframework.boot.context.properties.ConfigurationPropertiesBean.BindMethod

2、当 `@ConstructorBinding` 用在类上时，该类只能有一个带参数的构造器；如果有多个构造器时，可以把 `@ConstructorBinding` 直接绑定到具体的构造方法上；

3、成员变量可以是 `final` 不可变；

4、支持该类的内部类构造器注入的形式；

5、支持默认值 `@DefaultValue `、`@DateTimeFormat` 时间格式等注解配合使用；

6、需要配合 `@ConfigurationProperties`、`@EnableConfigurationProperties` 注解使用；

7、不支持像 `@Component`、`@Bean`、`@Import` 等方式创建 bean 的构造器参数绑定；

**来看下它的源码：**

```
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConstructorBinding {
}
```

什么参数都没有，可以说明，它就是起到一个构造器参数绑定的标识作用。

涨姿势了吧？？又学会了一种绑定参数的新方法了！

获取所有 Spring Boot 示例代码，请关注微信公众号 "Java技术栈" 在后台回复关键字：bootcode。

未完，栈长将陆续分享 Spring Boot 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

![](http://img.javastack.cn/wx_search_javastack.png)