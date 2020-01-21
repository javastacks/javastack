
## 什么是重复注解

下面是JDK8中的重复注解（`java.lang.annotation.Repeatable`）定义的源码。

```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Repeatable {
    Class<? extends Annotation> value();
}
```

重复注解，即一个注解可以在一个类、方法或者字段上同时使用多次，如Spring中可以使用多个扫描组件来扫描多个包的注解。

```
@ComponentScan
@ComponentScan
public class Configuration{
    
}
```

**ComponentScan**

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
```

**ComponentScans**

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
...
```

## 重复注解实现

下面来演示下重复注解的实现。


```
public static void main(String[] args) {
	for (Token token : UserToken.class.getAnnotationsByType(Token.class)) {
		System.out.println(token.value());
	}
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tokens {
	Token[] value();
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Tokens.class)
public @interface Token {
	String value();
}

@Token("666666")
@Token("888888")
public interface UserToken {
}
```

输出结果：

```
666666
888888
```

