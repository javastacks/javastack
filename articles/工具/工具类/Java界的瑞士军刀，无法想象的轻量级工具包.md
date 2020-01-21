
![](http://img.javastack.cn/18-2-27/74126275.jpg)

#### Jodd介绍

Jodd是对于Java开发更便捷的开源迷你框架，包含工具类、实用功能的集合，总包体积不到1.7M。

Jodd构建于通用场景使开发变得简单，但Jodd并不简单！它能让你把事情做得更好，实现你的想法，让你享受编码的乐趣。

简而言之，如果你准备造轮子，先看看Jodd实现了没。你可以把Jodd想象成Java的"瑞士军刀",不仅小，锋利而且包含许多便利的功能。

#### Jodd提供的功能

1. 提供操作Java bean，
2. 可以从各种数据源加载 Bean,
3. 简化 JDBC 的接连与代码，
4. 剖析 SQL 查询，
5. 处理时间与日期，
6. 操作与格式化 String,
7. 搜索本地硬盘上的文件，
8. 帮助处理 Servlet 请求等。
9. 包含一个很小但实用的基于JSP的MVC框架。

#### Jodd组成

Jodd 被分成众多模块，按需选择。

**Jodd工具包**

高性能的工具集合

- TypeConverter 一个强大的类型转换工具
- BeanUtil 高效的 Bean 工具，支持嵌套的属性，以及标准的集合类
- Base64/Base32
- JDateTime 增强的时间类
- IO 快速高性能的各种 Buffer、Writer、OutputStream
- Wildcard 通配符工具
- Servlet Servlet 工具，JSP 的标签、函数扩展
- FindFile/ClassFinder 强大的文件/类搜索工具，支持通配符&正则表达式匹配
- Cache 简单易用的 LRU、LRU、FIFO 缓存
- StringUtil 强大的字符串处理工具

**Madvoc**

轻量级快速开发MVC框架

- 自动扫描 Actions & Results
- 参数自动注入，支持嵌套的属性注入，支持标准集合参数的注入
- 自动导出需要输出的参数
- 支持通配符/正则表达式路由地址
- 支持 REST 风格的 URL
- 支持通过配置文件设置 Action 的拦截器
- 开放的 API，可轻松扩展

**HTTP**

轻量级Http客户端

- 基于socket
- 支持 cookies
- 支持上传文件
- 支持自定义 HTTP 头
- 支持 gzip
- 支持 Basic authentication

**Props**

增强的properties

- 支持 UTF-8 编码
- 支持插值操作
- 支持 ini 类型的区段
- 支持使用 += 附加值
- 支持多行模式

**Email**

易用的 Email 接收发送工具，基于 javax.mail

- 支持 SSL
- 支持添加附件
- 支持 POP3、IMAP
- IMAP 接受模式支持定义过滤器
- 支持解析 EML 文件

**Petite**

轻量级 IoC 容器

**Db & DbOom**

轻量级 ORM 框架

**Lagarto**

高性能的 xml/html 解析框架

**Jerry**

HTML 解析框架，Java 中的 jQuery，基于 Lagarto

**VTor**

基于注解的字段验证框架

- 可设置多配置
- 易扩展

**Proxetta**

高性能的代理生成器

#### 总结

可以看出，Jodd集合了Apache的Commons工具包(Lang，IO，BeanUtils，Codec，Email)中的核心功能，MVC、IOC/DI、ORM统统使用自有解决方案（想想Spring），加上简化而优雅的Http处理类（流式调用，媲美基于HttpClient的HCFluent），类似JQuery般操作HTML的Jerry，媲美Joda的JDataTime，甚至疯狂的定义了自己的Prop类用于管理属性。

**总之，Jodd = tools + ioc + mvc + db + aop + tx + json + html < 1.7 Mb**

> 更多关于Jodd的介绍与使用可以到它的官网：https://jodd.org/

