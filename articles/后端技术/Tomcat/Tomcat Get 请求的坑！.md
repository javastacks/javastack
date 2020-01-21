
![](http://img.javastack.cn/18-2-27/1096232.jpg)

**Tomcat8.5，当Get请求中包含了未经编码的中文字符时，会报以下错误，请求未到应用程序在Tomcat层就被拦截了。**

Tomcat报错：

> java.lang.IllegalArgumentException: Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986

返回400错误：

> Transfer-Encoding--->[chunked]\
> null--->[HTTP/1.1 400 Bad Request]\
> Server--->[Apache-Coyote/1.1]\
> Connection--->[close]\
> Date--->[Wed, 07 Feb 2018 03:19:04 GMT]

根据错误找到了Tomcat最新的源码：

org/apache/coyote/http11/LocalStrings.properties

```
iib.invalidRequestTarget=Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
```

org/apache/coyote/http11/Http11InputBuffer.java


```
boolean parseRequestLine(boolean keptAlive) throws IOException {

...
 
    } else if (HttpParser.isNotRequestTarget(chr)) {
        throw new IllegalArgumentException(sm.getString("iib.invalidRequestTarget"));
    }

...

}
```

java/org/apache/tomcat/util/http/parser/HttpParser.java


```
public static boolean isNotRequestTarget(int c) {
    // Fast for valid request target characters, slower for some incorrect
    // ones
    try {
        return IS_NOT_REQUEST_TARGET[c];
    } catch (ArrayIndexOutOfBoundsException ex) {
        return true;
    }
}
```

**查源码发现在Tomcat7.0.73就已经添加了RFC 3986这个规范。**

RFC 3986文档对Url的编解码问题做出了详细的建议，指出了哪些字符需要被编码才不会引起Url语义的转变，以及对为什么这些字符需要编码做出了相应的解释。

RFC 3986文档规定，Url中只允许包含英文字母（a-zA-Z）、数字（0-9）、-_.~4个特殊字符以及所有保留字符（! * ' ( ) ; : @ & = + $ , / ? # [ ]）。

还有一些字符当直接放在Url中的时候，可能会引起解析程序的歧义，这些字符被视为不安全字符。

- 空格：Url在传输的过程，或者用户在排版的过程，或者文本处理程序在处理Url的过程，都有可能引入无关紧要的空格，或者将那些有意义的空格给去掉。
- 引号以及<>：引号和尖括号通常用于在普通文本中起到分隔Url的作用
- #：通常用于表示书签或者锚点
- %：百分号本身用作对不安全字符进行编码时使用的特殊字符，因此本身需要编码
- {}|\^[]`~：某一些网关或者传输代理会篡改这些字符

**对于此问题，有以下几种解决方案。**

1、切换版本到7.0.73以下，这个不实际。

2、修改Tomcat源码，这个也不实际。

3、前端请求对URL编码。

4、修改Get方法为Post方法。

5、因{}是不安全字符，默认被 tomcat拦截。如果需要在URL中传输json数据，在catalina.properties中添加支持。
> tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}

**总结**

如果Get请求在合作方，而合作方不愿意修改代码，那1、2种方法可以尝试。如果Get请求在自己，可以尝试3、4种方法。仅需要在URL上传输json数据，使用第5种方法即可。
