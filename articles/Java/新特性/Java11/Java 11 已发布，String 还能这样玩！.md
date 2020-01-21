
在文章《[Java 11 正式发布，这 8 个逆天新特性教你写出更牛逼的代码](https://mp.weixin.qq.com/s/SXEqAmfbmK4NklihukbE-Q)》中，我有介绍到 Java 11 的八个新特性，其中关于 String 加强部分，我觉得有点意思，这里单独再拉出来讲。

**Java 11 增加了一系列的字符串处理方法，如以下所示。**

```
// 判断字符串是否为空白
" ".isBlank();                // true

// 去除首尾空格
" Javastack ".strip();          // "Javastack"

// 去除尾部空格 
" Javastack ".stripTrailing();  // " Javastack"

// 去除首部空格 
" Javastack ".stripLeading();   // "Javastack "

// 复制字符串
"Java".repeat(3);             // "JavaJavaJava"

// 行数统计
"A\nB\nC".lines().count();    // 3
```

最有意思的是 `repeat` 和 `lines` 方法了，来看下还能怎么玩！

#### repeat

repeat 方法的作用就是重复一个字符串 N 遍，可以用来代替工具类：`org.apache.commons.lang3.StringUtils#repeat(java.lang.String, int)`，来看下 `repeat` 的源码。

```
public String repeat(int count) {
    if (count < 0) {
        throw new IllegalArgumentException("count is negative: " + count);
    }
    if (count == 1) {
        return this;
    }
    final int len = value.length;
    if (len == 0 || count == 0) {
        return "";
    }
    if (len == 1) {
        final byte[] single = new byte[count];
        Arrays.fill(single, value[0]);
        return new String(single, coder);
    }
    if (Integer.MAX_VALUE / count < len) {
        throw new OutOfMemoryError("Repeating " + len + " bytes String " + count +
                " times will produce a String exceeding maximum size.");
    }
    final int limit = len * count;
    final byte[] multiple = new byte[limit];
    System.arraycopy(value, 0, multiple, 0, len);
    int copied = len;
    for (; copied < limit - copied; copied <<= 1) {
        System.arraycopy(multiple, 0, multiple, copied, copied);
    }
    System.arraycopy(multiple, 0, multiple, copied, limit - copied);
    return new String(multiple, coder);
}
```

来看下更多的用法。

```
String str = "Java";

// 小于0：java.lang.IllegalArgumentException
System.out.println(str.repeat(-2));

// 等于0：空白串（""）
System.out.println(str.repeat(0));

// JavaJavaJava
System.out.println(str.repeat(3));

// java.lang.OutOfMemoryError
System.out.println(str.repeat(Integer.MAX_VALUE));
```

所以说 repeat 并不是可以无限增长的，有使用限制的，达到一定量就会报内存溢出异常。

#### lines

```
public Stream<String> lines() {
    return isLatin1() ? StringLatin1.lines(value)
                      : StringUTF16.lines(value);
}
```
lines 方法返回一个字符串 Stream, 可以识别 `\n` 和 `\r` 换行符换行。

```
// 4
System.out.println("A\nB\nC\rD".lines().count());
```

是不是很好？在将来肯定有武之地！如批量读取文件内容到一个 Stream 中，就能很好的识别行结束符了。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "新特性" 可获取更多，转载请原样保留本信息。

