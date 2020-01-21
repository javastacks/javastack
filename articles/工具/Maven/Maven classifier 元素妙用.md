
首先来看这么一个依赖


```
<dependency>
    <groupId>net.sf.json-lib</groupId>
    <artifactId>json-lib</artifactId>
    <version>2.4</version>
</dependency>
```

看似没问题吧？你觉得能下得下来吗？答案是否定的，下不下来。

来看看Maven的文件索引目录：

> Index of /maven2/net/sf/json-lib/json-lib/2.4/

```
../
json-lib-2.4-jdk13.jar                             2010-12-14 05:39               158091
json-lib-2.4-jdk13.jar.md5                         2012-11-27 09:40                   32
json-lib-2.4-jdk13.jar.sha1                        2012-11-27 09:21                   40
json-lib-2.4-jdk13-javadoc.jar                     2010-12-14 05:39               245414
json-lib-2.4-jdk13-javadoc.jar.md5                 2012-11-27 09:40                   32
json-lib-2.4-jdk13-javadoc.jar.sha1                2012-11-27 09:21                   40
json-lib-2.4-jdk13-sources.jar                     2010-12-14 05:39               102628
json-lib-2.4-jdk13-sources.jar.md5                 2012-11-27 09:40                   32
json-lib-2.4-jdk13-sources.jar.sha1                2012-11-27 09:21                   40
json-lib-2.4-jdk15.jar                             2010-12-14 05:39               159123
json-lib-2.4-jdk15.jar.md5                         2012-11-27 09:40                   32
json-lib-2.4-jdk15.jar.sha1                        2012-11-27 09:21                   40
json-lib-2.4-jdk15-javadoc.jar                     2010-12-14 05:39               248552
json-lib-2.4-jdk15-javadoc.jar.md5                 2012-11-27 09:40                   32
json-lib-2.4-jdk15-javadoc.jar.sha1                2012-11-27 09:21                   40
json-lib-2.4-jdk15-sources.jar                     2010-12-14 05:39               104992
json-lib-2.4-jdk15-sources.jar.md5                 2012-11-27 09:40                   32
json-lib-2.4-jdk15-sources.jar.sha1                2012-11-27 09:21                   40
json-lib-2.4.pom                                   2010-12-14 05:39                13082
json-lib-2.4.pom.md5                               2012-11-27 09:40                   32
json-lib-2.4.pom.sha1                              2012-11-27 09:21                   40
```

根据Maven默认组织包的结构来看，这里根本找不到json-lib-2.4.jar的包，所以也就下不下来。

没有标准的jar包，但是有扩展的，如：json-lib-2.4-jdk15.jar，所以，这里要引入classifier这个元素了，classifier元素用来帮助定义构件输出的一些附属构件。

所以，下面这里加入`classifier`元素来下载扩展包json-lib-2.4-jdk15.jar。

```
<dependency>
    <groupId>net.sf.json-lib</groupId>
    <artifactId>json-lib</artifactId>
    <version>2.4</version>
    <classifier>jdk15</classifier>
</dependency>
```

这样就能下载对应的附属构件了。

这里拿json-lib这个包来举例，但它已经停止更新维护了，不推荐使用，推荐使用fastjson、jackson、gson等json转换框架。


