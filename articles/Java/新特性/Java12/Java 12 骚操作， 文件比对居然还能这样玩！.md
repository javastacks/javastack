Java 13 都快要来了，12必须跟栈长学起！

[Java 13 即将发布，新特性必须抢先看！](https://mp.weixin.qq.com/s/Gg6KKz7vhDRpzeMR8CG4DA)

之前分享了一些 Java 12 的骚操作，今天继续，今天要分享的是 Java 12 中的文件比对骚操作。

**我们或多或少会遇到这样的需求：怎么比对两个文件中的内容一样？**

你会把两个文件中的内容拉出来一个个字符对比，然后提交一大堆不怎么优雅的代码？

这样你就太 OUT 了！

在 Java 12 中，仅需要 1 行代码即可搞定！

来看示例代码：

```
public static void main(String[] args) throws IOException {
    Path dir = Paths.get("d:/");

    Path path1 = dir.resolve("javastack1.txt");

    Path path2 = dir.resolve("javastack2.txt");

    long result = Files.mismatch(path1, path2);

    System.out.println(result);
}
```

**Files.mismatch 一行搞定！**

javastack1 和 javastack2 的内容都是：

```
www.javastack.cn
```

这时候输出结果：-1。

现在把 javastack2 的内容改成：

```
http://www.javastack.cn
```

这时候输出结果：0。

再把 javastack2 的内容改成：

```
www.javastack.cn/
```

这时候输出结果：16。

**Files.mismatch**

Files.mismatch方法源码如下：

```
public static long mismatch(Path path, Path path2) throws IOException {
    if (isSameFile(path, path2)) {
        return -1;
    }
    byte[] buffer1 = new byte[BUFFER_SIZE];
    byte[] buffer2 = new byte[BUFFER_SIZE];
    try (InputStream in1 = Files.newInputStream(path);
         InputStream in2 = Files.newInputStream(path2);) {
        long totalRead = 0;
        while (true) {
            int nRead1 = in1.readNBytes(buffer1, 0, BUFFER_SIZE);
            int nRead2 = in2.readNBytes(buffer2, 0, BUFFER_SIZE);

            int i = Arrays.mismatch(buffer1, 0, nRead1, buffer2, 0, nRead2);
            if (i > -1) {
                return totalRead + i;
            }
            if (nRead1 < BUFFER_SIZE) {
                // we've reached the end of the files, but found no mismatch
                return -1;
            }
            totalRead += nRead1;
        }
    }
}
```

- 返回-1：同一文件，或者两个文件内容一样
- 返回其他数字：文件内容对比差异的位置，从0开始

**所以，只要返回 -1，说明文件内容相同。**

好了，今天的分享就到这，记住了，别说我没告诉你。。

收藏转发一下吧，多学习了解，日后必定有用！

历史 Java 新特性干货分享：

![](http://img.javastack.cn/20190613135450.png)
![](http://img.javastack.cn/20190613135537.png)

获取上面这份 Java 8~12 系列新特性干货文章，请在微信搜索关注微信公众号：Java技术栈，在公众号后台回复：java。

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。