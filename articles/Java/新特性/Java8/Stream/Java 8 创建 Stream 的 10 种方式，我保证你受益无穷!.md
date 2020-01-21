
之前栈长分享过 Java 8 一系列新特性的文章，其中重点介绍了 Stream.

![](http://img.javastack.cn/20190613135450.png)
![](http://img.javastack.cn/20190613135537.png)

获取上面这份 Java 8~12 系列新特性干货文章，请在微信搜索关注微信公众号：Java技术栈，在公众号后台回复：java。

今天来分享下在 Java 8 中创建 Stream 的 10 种方式，我就整理了 10 种，其实还有更多，仅供大家参考学习下。

## 1、Stream.of 可变参数

```
Stream<String> stream1 = Stream.of("A", "B", "C");
System.out.println("stream1:" + stream1.collect(joining()));
```

程序输出：

> stream1:ABC

## 2、Stream.of 数组

```
String[] values = new String[]{"A", "B", "C"};
Stream<String> stream2 = Stream.of(values);
System.out.println("stream2:" + stream2.collect(joining()));
```

程序输出：

> stream2:ABC

看 Stream.of 源码，上面这两种方式其实就是第三种方式的包装版。

```
public static<T> Stream<T> of(T... values) {
    return Arrays.stream(values);
}
```

我们直接使用源码中的方式也是一样的。

## 3、Arrays.stream

```
String[] values = new String[]{"A", "B", "C"};
Stream<String> stream3 = Arrays.stream(values);
System.out.println("stream3:" + stream3.collect(joining()));
```

程序输出：

> stream3:ABC

## 4、List

```
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream4 = list.stream();
System.out.println("stream4:" + stream4.collect(joining()));
```

程序输出：

> stream4:ABC

## 5、Set

```
Set<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));
Stream<String> stream5 = set.stream();
System.out.println("stream5:" + stream5.collect(joining()));
```

程序输出：

> stream5:ABC

## 6、Map

```
Map<String, String> map = new HashMap<>();
map.put("1", "A");
map.put("2", "B");
map.put("3", "C");
Stream<String> stream6 = map.values().stream();
System.out.println("stream6:" + stream6.collect(joining()));
```

程序输出：

> stream6:ABC

## 7、Stream.iterate

```
Stream<String> stream7 = Stream.iterate("A", e -> String.valueOf((char) (e.charAt(0) + 1))).limit(3);
System.out.println("stream7:" + stream7.collect(joining()));
```

程序输出：

> stream7:ABC

## 8、Pattern

```
String value = "A B C";
Stream<String> stream8 = Pattern.compile("\\W").splitAsStream(value);
System.out.println("stream8:" + stream8.collect(joining()));
```

程序输出：

> stream8:ABC

## 9、Files.lines

```
try {
    Stream<String> stream9 = Files.lines(Paths.get("d:/data.txt"));
    System.out.println("stream9:" + stream9.collect(joining()));
} catch (IOException e) {
    e.printStackTrace();
}
```

data.txt文件内容如下：

```
A
B
C
```

程序输出：

> stream9:ABC

## 10、Stream.generate

```
Stream<String> stream10 = Stream.generate(() -> "A").limit(3);
System.out.println("stream10:" + stream10.collect(joining()));
```

程序输出：

> stream10:AAA

好了，这是栈长整理的 10 种创建 Stream 的方式，是不是很骚？如果你还知道其他的骚操作，可以留言告诉大家，让大家记住你的头像和ID。

转发分享一下吧，我保证你日后总有机会用得着的。

大量 Java 8~12 的新特性文章我正在编写中，栈长将陆续分享出来，微信搜索关注微信公众号：Java技术栈，公众号将第一时间推送，不要错过。

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。
 
