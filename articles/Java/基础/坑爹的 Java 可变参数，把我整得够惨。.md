最近在写一个功能点，用了 Java 中的可变参数，真是把我搞得够惨。。

**什么是可变参数？**

就是方法参数用 `Object... args` 三个点形式，一个参数可以接收多个参数。

实际的代码就不帖了，来看这个示例吧：

**示例1**

```
public static void main(String[] args) {
    test("name=%s&memo=%s", "Java技术栈", "666");
}

private static void test(String text, Object... params) {
    String result = String.format(text, params);
    System.out.println(result);
}
```

大家猜结果是什么？结果如我们想象：

```
name=Java技术栈&memo=666
```

**示例2**

```
public static void main(String[] args) {
    test("name=%s&memo=%s", "Java技术栈");
}

private static void test(String text, Object... params) {
    String result = String.format(text, params, "666");
    System.out.println(result);
}
```

我把 "666" 移到了子方法里面，放到了 format 最后，再来看下结果是什么。

```
name=[Ljava.lang.Object;@4cb2c100&memo=666
```

这并非是我想要的结果，把可变参数 params 数组对象地址作为值输出出来了，把我搞得够惨，最后我干掉了可变参数。。

JDK里面很多有用到可变参数的，可实际开发中，并不建议使用可变参考，它带来的困扰和潜在的问题会远大于便利性，比如在方法重构、重写等也会带来很多问题。

关于可变参数，也是有开发规范的，不能随便写。我找到了阿里巴巴的《Java开发手册》中关于可变参数的规约。

> 相同参数类型，相同业务含义，才可以使用 Java 的可变参数，避免使用 Object 。
> 
> 说明：可变参数必须放置在参数列表的最后。 （ 提倡同学们尽量不用可变参数编程 ）
> 
> 正例： public List<User> listUsers(String type, Long... ids) {...}

获取这份阿里巴巴的Java开发手册最新PDF版，大家可以关注Java技术栈微信公众号，在后台回复：手册，即可获取。

阿里巴巴也是不建议大家合作可变参数的，可知它带来的坑会有多坑。。

另外，栈长已经整理了大量 Java 系列核心技术知识点文章，关注Java技术栈微信公众号，在后台回复关键字：java，即可获取最新版。

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。