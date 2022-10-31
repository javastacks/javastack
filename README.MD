## 说明

可能有些人还不明白：

- 学 Java 到底该学什么？
- 现在市面上都流行什么技术？
- Java 面试都问些什么问题？
- 有什么汇总之类的教程？

带着这几个问题，栈长在这里整理了大量主流的 Java 系列文章和教程，供你学习、工作、面试复习参考。

大多内容为栈长原创，首发自微信公众号：**Java技术栈**，这些内容在**Java技术栈**公众号菜单中都有作分类整理，如果你想在微信上阅读，也可以关注微信公众号，在微信上阅读体验更好，也方便随时随地手机查阅，微信公众号第一时间更新。

**扫一扫关注微信公众号：**

![](http://img.javastack.cn/wx_search_javastack.png)

另外，教程会在Java技术栈<a target="_blank" href="https://www.javastack.cn/">官方博客</a>中同步更新。

本文会定期更新，欢迎 Star。

## 目录分类

点击快速跳转分类文章：

- [Java](#Java)  
  - [基础](#基础)
  - [集合](#集合)
  - [多线程](#多线程)
  - [JVM](#JVM)
  - [IO](#IO)
  - [新特性](#新特性)    
    - [Java8](#Java8)
      - [Stream](#Stream)
	- [Java9](#Java9)
	- [Java10](#Java10)
    - [Java11](#Java11)
    - [Java12](#Java12)    
  - [进阶](#进阶)  
- [后端技术](#后端技术)
  - [Spring](#Spring)
  - [Spring MVC](#Spring-MVC)
  - [Spring Boot](#Spring-Boot)
  - [Spring Cloud](#Spring-Cloud)
  - [MyBatis](#MyBatis)
  - [Docker](#Docker)
  - [Dubbo](#Dubbo)
  - [Elasticsearch](#Elasticsearch)
  - [Elastic Job](#Elastic-Job)
  - [JWT](#JWT)  
  - [Netty](#Netty)
  - [Shrio](#Shrio)  
  - [Tomcat](#Tomcat)
  - [Zookeeper](#Zookeeper)
  - [开源框架](#开源框架)
  - [日志](#日志)
  - [消息队列](#消息队列)
    - [Kafka](#Kafka)  
  - [缓存](#缓存)
  - [连接池](#连接池)
  - [综合技术](#综合技术)
- [数据库](#数据库)
  - [MySQL](#MySQL)  
  - [Redis](#Redis)
  - [MongoDB](#MongoDB)
- [分布式技术](#分布式技术)
- [架构](#架构)
- [设计模式](#设计模式)
- [数据结构 & 算法](#数据结构--算法)
- [规范](#规范)
- [安全漏洞](#安全漏洞)
- [性能优化](#性能优化)
- [行业动态](#行业动态)
- [Linux](#Linux)
- [区块链](#区块链)
- [工具](#工具)
  - [Eclipse](#Eclipse)  
  - [Intellij IDEA](#Intellij-IDEA)
  - [Git](#Git)
  - [Maven](#Maven)
  - [Swagger](#Swagger)  
  - [效率](#效率)
  - [工具类](#工具类)
- [面试](#面试)
- [程序人生](#程序人生)
- [资料](#资料)
- [其他](#其他)

## Java

#### 基础

- [Java 中的基本数据类型转换详解](articles/Java/基础/Java%20中的基本数据类型转换详解.md)
- [StringBuffer 和 StringBuilder 的 3 个区别](articles/Java/基础/StringBuffer%20和%20StringBuilder%20的%203%20个区别.md)
- [switch case 支持的 6 种数据类型！](articles/Java/基础/switch%20case%20支持的%206%20种数据类型！.md)
- [switch多值匹配骚操作，带你涨姿势！](articles/Java/基础/switch多值匹配骚操作，带你涨姿势！.md)
- [一张图搞清楚Java异常机制](articles/Java/基础/一张图搞清楚Java异常机制.md)
- [你真的搞懂 transient  关键字了吗？](articles/Java/基础/你真的搞懂%20transient%20关键字了吗？.md)
- [关于 Java 序列化你不知道的 5 件事](articles/Java/基础/关于%20Java%20序列化你不知道的%205%20件事.md)
- [关于Java序列化你应该知道的一切](articles/Java/基础/关于Java序列化你应该知道的一切.md)
- [坑爹的 Java 可变参数，把我整得够惨。](articles/Java/基础/坑爹的%20Java%20可变参数，把我整得够惨。.md)
- [字符串拼接 + 和 concat 的区别](articles/Java/基础/字符串拼接%20+%20和%20concat%20的区别.md)
- [常见的 3 种 Class 级别的错误](articles/Java/基础/常见的%203%20种%20Class%20级别的错误.md)
- [深入浅出 Java 中的包装类](articles/Java/基础/深入浅出%20Java%20中的包装类.md)
- [通用唯一标识码 UUID 的介绍及使用。](articles/Java/基础/通用唯一标识码%20UUID%20的介绍及使用。.md)
- [10 个深恶痛绝的 Java 异常。](articles/Java/基础/10%20个深恶痛绝的%20Java%20异常。。.md)
- [Java Base64 编码解码方案总结](articles/Java/基础/Java%20Base64%20编码解码方案总结.md)
- [Java Jar包压缩、解压使用指南](articles/Java/基础/Java%20Jar包压缩、解压使用指南.md)
- [Java 父类强制转换子类原则](articles/Java/基础/Java%20父类强制转换子类原则.md)

#### 集合

- [Java 中初始化 List 集合的 6 种方式!](articles/Java/集合/Java%20中初始化%20List%20集合的%206%20种方式!.md)
- [HashMap 和 Hashtable 的 6 个区别](articles/Java/集合/HashMap%20和%20Hashtable%20的%206%20个区别.md)
- [HashMap, ConcurrentHashMap 原理及源码](articles/Java/集合/HashMap,%20ConcurrentHashMap%20原理及源码.md)
- [Hashtable 为什么不叫 HashTable？](articles/Java/集合/Hashtable%20为什么不叫%20HashTable？.md)


#### 多线程

- [i++ 是线程安全的吗？](articles/Java/多线程/i++%20是线程安全的吗？.md)
- [Java 多线程 join 使用及原理](articles/Java/多线程/Java%20多线程%20join%20使用及原理.md)
- [Java 多线程可以分组，还能这样玩！](articles/Java/多线程/Java%20多线程可以分组，还能这样玩！.md)
- [Java 虚拟机对锁优化所做的努力](articles/Java/多线程/Java%20虚拟机对锁优化所做的努力.md)
- [sleep( ) 和 wait( ) 的这 5 个区别，你知道几个？](articles/Java/多线程/sleep(%20)%20和%20wait(%20)%20的这%205%20个区别，你知道几个？.md)
- [Synchronized 与 ReentrantLock 的区别！](articles/Java/多线程/Synchronized%20与%20ReentrantLock%20的区别！.md)
- [Synchronized 有几种用法？](articles/Java/多线程/Synchronized%20有几种用法？.md)
- [一文搞懂 Java 线程中断](articles/Java/多线程/一文搞懂%20Java%20线程中断.md)
- [出场率比较高的一道多线程安全面试题](articles/Java/多线程/出场率比较高的一道多线程安全面试题.md)
- [到底什么是重入锁，拜托，一次搞清楚！](articles/Java/多线程/到底什么是重入锁，拜托，一次搞清楚！.md)
- [多线程 start 和 run 方法到底有什么区别？](articles/Java/多线程/多线程%20start%20和%20run%20方法到底有什么区别？.md)
- [多线程 Thread.yield 方法到底有什么用？](articles/Java/多线程/多线程%20Thread.yield%20方法到底有什么用？.md)
- [多线程通信的三大法器，你真的会用吗？](articles/Java/多线程/多线程通信的三大法器，你真的会用吗？.md)
- [并行任务执行神器 Fork & Join 实战](articles/Java/多线程/并行任务执行神器%20Fork%20&%20Join%20实战.md)
- [教你如何监控 Java 线程池运行状态](articles/Java/多线程/教你如何监控%20Java%20线程池运行状态.md)
- [线程休眠只会用Thread.sleep？那就弱爆了！](articles/Java/多线程/线程休眠只会用Thread.sleep？那就弱爆了！.md)
- [面试官问线程安全的List，看完再也不怕了！](articles/Java/多线程/面试官问线程安全的List，看完再也不怕了！.md)

#### JVM

- [JVM运行时区域详解](articles/Java/JVM/JVM运行时区域详解.md)

#### IO

待更新……

#### 新特性

- [Java 5 ~ 10 新特性倾情整理！](articles/Java/新特性/Java%205%20~%2010%20新特性倾情整理！.md)

#### Java8

- [Java8 新特性之Lambda表达式](articles/Java/新特性/Java8/Java8%20新特性之Lambda表达式.md)
- [Java8 新特性之Optional](articles/Java/新特性/Java8/Java8%20新特性之Optional.md)
- [Java8 新特性之函数式接口](articles/Java/新特性/Java8/Java8%20新特性之函数式接口.md)
- [Java8 新特性之接口默认方法与静态方法](articles/Java/新特性/Java8/Java8%20新特性之接口默认方法与静态方法.md)
- [Java8 新特性之方法引用](articles/Java/新特性/Java8/Java8%20新特性之方法引用.md)
- [Java8 新特性之重复注解](articles/Java/新特性/Java8/Java8%20新特性之重复注解.md)
- [Java8 之新特性扩展篇](articles/Java/新特性/Java8/Java8%20之新特性扩展篇.md)

##### Stream

- [Java 8 新特性之Stream流](articles/Java/新特性/Java8/Stream/Java%208%20新特性之Stream流.md)
- [Java 8 创建 Stream 的 10 种方式，我保证你受益无穷!](articles/Java/新特性/Java8/Stream/Java%208%20创建%20Stream%20的%2010%20种方式，我保证你受益无穷!.md)
- [Java 8 新特性之 Stream 流（一）基础体验](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（一）基础体验.md)
- [Java 8 新特性之 Stream 流（七）流与迭代器，流系列大结局！](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（七）流与迭代器，流系列大结局！.md)
- [Java 8 新特性之 Stream 流（三）缩减操作](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（三）缩减操作.md)
- [Java 8 新特性之 Stream 流（二）关键知识点](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（二）关键知识点.md)
- [Java 8 新特性之 Stream 流（五）映射](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（五）映射.md)
- [Java 8 新特性之 Stream 流（六）收集](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（六）收集.md)
- [Java 8 新特性之 Stream 流（四）并行流](articles/Java/新特性/Java8/Stream/Java%208%20新特性之%20Stream%20流（四）并行流.md)

#### Java9

- [Java9 简化流关闭新姿势。](articles/Java/新特性/Java9/Java9%20简化流关闭新姿势。.md)

#### Java10

- [Java 10 局部变量类型推断介绍及实战](articles/Java/新特性/Java10/Java%2010%20局部变量类型推断介绍及实战.md)
- [Java 10 的 10 个新特性，将彻底改变你写代码的方式](articles/Java/新特性/Java10/Java%2010%20的%2010%20个新特性，将彻底改变你写代码的方式.md)

#### Java11

- [Java 11 已发布，String 还能这样玩！](articles/Java/新特性/Java11/Java%2011%20已发布，String%20还能这样玩！.md)
- [Java 11 正式发布，这 8 个新特性教你写出更牛逼的代码](articles/Java/新特性/Java11/Java%2011%20正式发布，这%208%20个新特性教你写出更牛逼的代码.md)

#### Java12

- [Java 12 骚操作， String居然还能这样玩！](articles/Java/新特性/Java12/Java%2012%20骚操作，%20String居然还能这样玩！.md)
- [Java 12 骚操作， switch居然还能这样玩！](articles/Java/新特性/Java12/Java%2012%20骚操作，%20switch居然还能这样玩！.md)
- [Java 12 骚操作， 文件比对居然还能这样玩！](articles/Java/新特性/Java12/Java%2012%20骚操作，%20文件比对居然还能这样玩！.md)

#### 进阶

- [hashCode 和 identityHashCode 的区别你知道吗？](articles/Java/进阶/hashCode%20和%20identityHashCode%20的区别你知道吗？.md)
- [Java 中的 6 颗语法糖](articles/Java/进阶/Java%20中的%206%20颗语法糖.md)
- [Java 中的 String 真的是不可变吗？](articles/Java/进阶/Java%20中的%20String%20真的是不可变吗？.md)
- [Java 中的伪共享详解及解决方案](articles/Java/进阶/Java%20中的伪共享详解及解决方案.md)
- [Java 中的宏变量，宏替换详解。](articles/Java/进阶/Java%20中的宏变量，宏替换详解。.md)
- [Java 中获取类名的三种方法，你知道几种？](articles/Java/进阶/Java%20中获取类名的三种方法，你知道几种？.md)
- [Java 对象引用四个级别（强、软、弱、虚）](articles/Java/进阶/Java%20对象引用四个级别（强、软、弱、虚）.md)
- [Java 异常处理 10 个最佳实践](articles/Java/进阶/Java%20异常处理%2010%20个最佳实践.md)
- [Java 类初始化顺序，3个示例带你躺坑。](articles/Java/进阶/Java%20类初始化顺序，3个示例带你躺坑。.md)
- [Java 自定义 ClassLoader 实战](articles/Java/进阶/Java%20自定义%20ClassLoader%20实战.md)
- [不能用 + 拼接字符串？ 这次我要吊打面试官！](articles/Java/进阶/不能用%20+%20拼接字符串？%20这次我要吊打面试官！.md)
- [别再写 bug 了，避免空指针的 5 个案例！](articles/Java/进阶/别再写%20bug%20了，避免空指针的%205%20个案例！.md)
- [字符串 substring 方法在 JDK 6,7,8 中的差异。](articles/Java/进阶/字符串%20substring%20方法在%20JDK%206,7,8%20中的差异。.md)
- [神奇，教你用随机数打印hello world](articles/Java/进阶/神奇，教你用随机数打印hello%20world.md)
- [金融系统中正确的金额计算及存储方式](articles/Java/进阶/金融系统中正确的金额计算及存储方式.md)
- [面试问我 Java 逃逸分析，瞬间被秒杀了。](articles/Java/进阶/面试问我%20Java%20逃逸分析，瞬间被秒杀了。.md)

## 后端技术

#### Spring

- [Spring 事务失效的 8 大原因，这次可以吊打面试官了！](articles/后端技术/Spring/Spring%20事务失效的%208%20大原因，这次可以吊打面试官了！.md)
- [Spring Aware 容器感知技术](articles/后端技术/Spring/Spring%20Aware%20容器感知技术.md)
- [Spring Enable 高级应用及原理](articles/后端技术/Spring/Spring%20Enable%20高级应用及原理.md)
- [Spring import 配置文件使用占位符](articles/后端技术/Spring/Spring%20import%20配置文件使用占位符.md)
- [Spring 开启方法异步执行](articles/后端技术/Spring/Spring%20开启方法异步执行.md)
- [Spring 快速开启计划任务](articles/后端技术/Spring/Spring%20快速开启计划任务.md)
- [终于，Spring 5.0 正式发布了！](articles/后端技术/Spring/终于，Spring%205.0%20正式发布了！.md)

#### Spring MVC

- [Spring MVC 常用注解](articles/后端技术/Spring%20MVC/Spring%20MVC%20常用注解.md)
- [Spring MVC 表单防重复提交](articles/后端技术/Spring%20MVC/Spring%20MVC%20表单防重复提交.md)
- [Spring MVC 表单验证器](articles/后端技术/Spring%20MVC/Spring%20MVC%20表单验证器.md)

#### Spring Boot

- [Spring Boot 2.2.2 发布，新增 2 个新特性！](articles/后端技术/Spring%20Boot/Spring%20Boot%202.2.2%20发布，新增%202%20个新特性！.md)
- [Spring Boot 2.2.1 发布，一个有点坑的版本！](articles/后端技术/Spring%20Boot/Spring%20Boot%202.2.1%20发布，一个有点坑的版本！.md)
- [Spring Boot 2.2 增加了一个新功能，启动飞起~](articles/后端技术/Spring%20Boot/Spring%20Boot%202.2%20增加了一个新功能，启动飞起~.md)
- [Spring Boot 2.1.5 正式发布，1.5.x 即将结束使命！](articles/后端技术/Spring%20Boot/Spring%20Boot%202.1.5%20正式发布，1.5.x%20即将结束使命！.md)
- [Spring Boot 2.1.0 已发布，7 个重大更新！](articles/后端技术/Spring%20Boot/Spring%20Boot%202.1.0%20已发布，7%20个重大更新！.md)
- [Spring Boot 2.0 正式发布！](articles/后端技术/Spring%20Boot/Spring%20Boot%202.0%20正式发布！.md)
- [Spring Boot 1.5.10 发布：修复重要安全漏洞！！！](articles/后端技术/Spring%20Boot/Spring%20Boot%201.5.10%20发布：修复重要安全漏洞！！！.md)
- [Spring Boot 2.x 与 1.x 的区别，以及如何做版本迁移](articles/后端技术/Spring%20Boot/Spring%20Boot%202.x%20与%201.x%20的区别，以及如何做版本迁移.md)
- [Spring Boot 2.x 启动全过程源码分析（上）入口类剖析](articles/后端技术/Spring%20Boot/Spring%20Boot%202.x%20启动全过程源码分析（上）入口类剖析.md)
- [Spring Boot 2.x 启动全过程源码分析（下）运行方法剖析](articles/后端技术/Spring%20Boot/Spring%20Boot%202.x%20启动全过程源码分析（下）运行方法剖析.md)
- [Spring Boot Debug调试，日后必定有用！](articles/后端技术/Spring%20Boot/Spring%20Boot%20Debug调试，日后必定有用！.md)
- [Spring Boot Profile 配置详解](articles/后端技术/Spring%20Boot/Spring%20Boot%20Profile%20配置详解.md)
- [Spring Boot Runner启动器](articles/后端技术/Spring%20Boot/Spring%20Boot%20Runner启动器.md)
- [Spring Boot Server容器配置](articles/后端技术/Spring%20Boot/Spring%20Boot%20Server容器配置.md)
- [Spring Boot Starters启动器](articles/后端技术/Spring%20Boot/Spring%20Boot%20Starters启动器.md)
- [Spring Boot YML 掀翻 Properties！！](articles/后端技术/Spring%20Boot/Spring%20Boot%20YML%20掀翻%20Properties！！.md)
- [Spring Boot 主类及目录结构介绍](articles/后端技术/Spring%20Boot/Spring%20Boot%20主类及目录结构介绍.md)
- [Spring Boot 之配置导入，强大到不行！](articles/后端技术/Spring%20Boot/Spring%20Boot%20之配置导入，强大到不行！.md)
- [Spring Boot 功能实战](articles/后端技术/Spring%20Boot/Spring%20Boot%20功能实战.md)
- [Spring Boot 单元测试详解+实战教程](articles/后端技术/Spring%20Boot/Spring%20Boot%20单元测试详解+实战教程.md)
- [Spring Boot 发布将 jar 包转为 war 包。](articles/后端技术/Spring%20Boot/Spring%20Boot%20发布将%20jar%20包转为%20war%20包。.md)
- [Spring Boot 发邮件，1 分钟教会你](articles/后端技术/Spring%20Boot/Spring%20Boot%20发邮件，1%20分钟教会你.md)
- [Spring Boot 国际化支持](articles/后端技术/Spring%20Boot/Spring%20Boot%20国际化支持.md)
- [Spring Boot 定制启动图案](articles/后端技术/Spring%20Boot/Spring%20Boot%20定制启动图案.md)
- [Spring Boot 实现热部署](articles/后端技术/Spring%20Boot/Spring%20Boot%20实现热部署.md)
- [Spring Boot 应用 10 种保护绝佳方法](articles/后端技术/Spring%20Boot/Spring%20Boot%20应用%2010%20种保护绝佳方法.md)
- [Spring Boot 开启的2种方式](articles/后端技术/Spring%20Boot/Spring%20Boot%20开启的2种方式.md)
- [Spring Boot 怎么打一个可执行 Jar 包？](articles/后端技术/Spring%20Boot/Spring%20Boot%20怎么打一个可执行%20Jar%20包？.md)
- [Spring Boot 排除自动配置的 4 种方法，关键时刻很有用！](articles/后端技术/Spring%20Boot/Spring%20Boot%20排除自动配置的%204%20种方法，关键时刻很有用！.md)
- [Spring Boot 支持 HTTPS 如此简单，So easy！](articles/后端技术/Spring%20Boot/Spring%20Boot%20支持%20HTTPS%20如此简单，So%20easy！.md)
- [Spring Boot 整合Thymeleaf模板引擎](articles/后端技术/Spring%20Boot/Spring%20Boot%20整合Thymeleaf模板引擎.md)
- [Spring Boot 日志集成](articles/后端技术/Spring%20Boot/Spring%20Boot%20日志集成.md)
- [Spring Boot 是什么？](articles/后端技术/Spring%20Boot/Spring%20Boot%20是什么？.md)
- [Spring Boot 最核心的 25 个注解，都是干货！](articles/后端技术/Spring%20Boot/Spring%20Boot%20最核心的%2025%20个注解，都是干货！.md)
- [Spring Boot 最核心的 3 个注解详解](articles/后端技术/Spring%20Boot/Spring%20Boot%20最核心的%203%20个注解详解.md)
- [Spring Boot 构造器参数绑定，越来越强大了！](articles/后端技术/Spring%20Boot/Spring%20Boot%20构造器参数绑定，越来越强大了！.md)
- [Spring Boot 核心配置](articles/后端技术/Spring%20Boot/Spring%20Boot%20核心配置.md)
- [Spring Boot 注册 Servlet 的三种方法，真是太有用了！](articles/后端技术/Spring%20Boot/Spring%20Boot%20注册%20Servlet%20的三种方法，真是太有用了！.md)
- [Spring Boot 的 10 个核心模块](articles/后端技术/Spring%20Boot/Spring%20Boot%20的%2010%20个核心模块.md)
- [Spring Boot 真是个牛逼货！](articles/后端技术/Spring%20Boot/Spring%20Boot%20真是个牛逼货！.md)
- [Spring Boot 自动配置原理、实战](articles/后端技术/Spring%20Boot/Spring%20Boot%20自动配置原理、实战.md)
- [Spring Boot 自定义日志详解](articles/后端技术/Spring%20Boot/Spring%20Boot%20自定义日志详解.md)
- [Spring Boot 读取配置的几种方式](articles/后端技术/Spring%20Boot/Spring%20Boot%20读取配置的几种方式.md)
- [Spring Boot 运行应用的 3 种方式](articles/后端技术/Spring%20Boot/Spring%20Boot%20运行应用的%203%20种方式.md)
- [Spring Boot 返回 JSON 数据，一分钟搞定！](articles/后端技术/Spring%20Boot/Spring%20Boot%20返回%20JSON%20数据，一分钟搞定！.md)
- [Spring Boot 返回 XML 数据，一分钟搞定！](articles/后端技术/Spring%20Boot/Spring%20Boot%20返回%20XML%20数据，一分钟搞定！.md)
- [Spring Boot 配置加载顺序](articles/后端技术/Spring%20Boot/Spring%20Boot%20配置加载顺序.md)
- [Spring Boot 配置加载顺序详解。](articles/后端技术/Spring%20Boot/Spring%20Boot%20配置加载顺序详解。.md)
- [Spring Boot 配置文件 bootstrap vs application 到底有什么区别？](articles/后端技术/Spring%20Boot/Spring%20Boot%20配置文件%20bootstrap%20vs%20application%20到底有什么区别？.md)
- [Spring Boot 配置随机数技巧](articles/后端技术/Spring%20Boot/Spring%20Boot%20配置随机数技巧.md)
- [Spring Boot 集成Mybatis双数据源](articles/后端技术/Spring%20Boot/Spring%20Boot%20集成Mybatis双数据源.md)
- [Spring Boot 面试，一个问题就干趴下了！](articles/后端技术/Spring%20Boot/Spring%20Boot%20面试，一个问题就干趴下了！.md)
- [Spring Boot 面试，一个问题就干趴下了！（下）](articles/后端技术/Spring%20Boot/Spring%20Boot%20面试，一个问题就干趴下了！（下）.md)
- [公司不用 Spring Boot，果断离职了！](articles/后端技术/Spring%20Boot/公司不用%20Spring%20Boot，果断离职了！.md)
- [年轻人的第一个 Spring Boot 应用，太爽了！](articles/后端技术/Spring%20Boot/年轻人的第一个%20Spring%20Boot%20应用，太爽了！.md)
- [年轻人的第一个自定义 Spring Boot Starter！](articles/后端技术/Spring%20Boot/年轻人的第一个自定义%20Spring%20Boot%20Starter！.md)

#### Spring Cloud

- [Spring Cloud 是什么？](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20是什么？.md)
- [Spring Cloud Greenwich.SR4 发布了，跟不上了……](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Greenwich.SR4%20发布了，跟不上了…….md)
- [Spring Cloud Greenwich 正式发布，Hystrix 即将寿终正寝。。](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Greenwich%20正式发布，Hystrix%20即将寿终正寝。。.md)
- [Spring Cloud 升级最新 Greenwich 版本，舒服了~](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20升级最新%20Greenwich%20版本，舒服了~.md)
- [Spring Cloud 升级最新 Finchley 版本，踩了所有的坑！](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20升级最新%20Finchley%20版本，踩了所有的坑！.md)
- [Spring Cloud Finchley 正式发布，包含 4个重大更新！](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Finchley%20正式发布，包含%204%20个重大更新！.md)
- [Spring Cloud Eureka 常用配置详解，建议收藏！](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Eureka%20常用配置详解，建议收藏！.md)
- [Spring Cloud Eureka 注册中心集群搭建，Greenwich 最新版！](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Eureka%20注册中心集群搭建，Greenwich%20最新版！.md)
- [Spring Cloud Eureka 注册安全一定要做到位！](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Eureka%20注册安全一定要做到位！.md)
- [Spring Cloud Eureka 自我保护机制](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Eureka%20自我保护机制.md)
- [Spring Cloud Eureka 自我保护机制实战分析](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Eureka%20自我保护机制实战分析.md)
- [Spring Cloud Gateway VS Zuul 比较，怎么选择？](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Gateway%20VS%20Zuul%20比较，怎么选择？.md)
- [Spring Cloud 多版本管理那些事。](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20多版本管理那些事。.md)
- [Spring Cloud 如何动态刷新 Git 仓库配置？](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20如何动态刷新%20Git%20仓库配置？.md)
- [Spring Cloud @RefreshScope 原理是什么？](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20@RefreshScope%20原理是什么？.md)
- [Spring Cloud 服务安全连接](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20服务安全连接.md)
- [Spring Cloud 注册中心高可用搭建](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20注册中心高可用搭建.md)
- [Spring Cloud 配置中心内容加密](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20配置中心内容加密.md)
- [Spring Cloud 配置中心客户端读取配置](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20配置中心客户端读取配置.md)
- [Spring Cloud 配置中心高可用搭建](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20配置中心高可用搭建.md)
- [Spring Cloud Alibaba Sentinel 整合 Feign 的设计实现](articles/后端技术/Spring%20Cloud/Spring%20Cloud%20Alibaba%20Sentinel%20整合%20Feign%20的设计实现.md)
- [凉凉了，Eureka 宣布停更，Spring Cloud 何去何从？](articles/后端技术/Spring%20Cloud/凉凉了，Eureka%20宣布停更，Spring%20Cloud%20何去何从？.md)
- [厉害了，Spring Cloud for Alibaba 来了！](articles/后端技术/Spring%20Cloud/厉害了，Spring%20Cloud%20for%20Alibaba%20来了！.md)

#### MyBatis

- [MyBatis 传递多个参数的 4 种方式](articles/后端技术/MyBatis/MyBatis%20传递多个参数的%204%20种方式.md)
- [推荐一款 MyBatis 开发神器，为简化而生！](articles/后端技术/MyBatis/推荐一款%20MyBatis%20开发神器，为简化而生！.md)

#### Docker

- [Docker核心概念、安装、端口映射及常用操作命令。](articles/后端技术/Docker/Docker核心概念、安装、端口映射及常用操作命令。.md)

#### Dubbo

- [Dubbo & Spring Boot 要来了](articles/后端技术/Dubbo/Dubbo%20&%20Spring%20Boot%20要来了.md)
- [厉害了，Dubbo 正式毕业！](articles/后端技术/Dubbo/厉害了，Dubbo%20正式毕业！.md)
- [惊艳，Dubbo域名已改，也不再局限于Java！！](articles/后端技术/Dubbo/惊艳，Dubbo域名已改，也不再局限于Java！！.md)
- [重大利好，Dubbo 3.0要来了。](articles/后端技术/Dubbo/重大利好，Dubbo%203.0要来了。.md)

#### Elasticsearch

待更新……

#### Elastic Job

- [Elastic Job - 分布式作业调度解决方案](articles/后端技术/Elastic%20Job/Elastic%20Job%20-%20分布式作业调度解决方案.md)
- [分布式作业 Elastic Job 如何动态调整？](articles/后端技术/Elastic%20Job/分布式作业%20Elastic%20Job%20如何动态调整？.md)
- [分布式作业 Elastic-Job 快速上手指南！](articles/后端技术/Elastic%20Job/分布式作业%20Elastic-Job%20快速上手指南！.md)

#### JWT

- [JWT - 服务端信息传输安全解决方案。](articles/后端技术/JWT/JWT%20-%20服务端信息传输安全解决方案。.md)
- [JWT 生成Token及解密实战。](articles/后端技术/JWT/JWT%20生成Token及解密实战。.md)

#### Netty

- [为什么 Netty 这么火？与 Mina 相比有什么优势？](articles/后端技术/Netty/为什么%20Netty%20这么火？与%20Mina%20相比有什么优势？.md)

#### Shrio

- [10 分钟快速上手 Shiro 新手教程](articles/后端技术/Shrio/10%20分钟快速上手%20Shiro%20新手教程.md)
- [Shiro Realm 权限的验证流程和缓存机制](articles/后端技术/Shrio/Shiro%20Realm%20权限的验证流程和缓存机制.md)
- [一文搞定 Spring Boot & Shiro 实战](articles/后端技术/Shrio/一文搞定%20Spring%20Boot%20&%20Shiro%20实战.md)
- [非常详尽的 Shiro 架构解析](articles/后端技术/Shrio/非常详尽的%20Shiro%20架构解析.md)

#### Tomcat

- [Tomcat Get 请求的坑！](articles/后端技术/Tomcat/Tomcat%20Get%20请求的坑！.md)
- [Tomcat 集群 session 复制与 Oracle 的坑。。](articles/后端技术/Tomcat/Tomcat%20集群%20session%20复制与%20Oracle%20的坑。。.md)
- [一分钟开启 Tomcat https 支持](articles/后端技术/Tomcat/一分钟开启%20Tomcat%20https%20支持.md)

#### Zookeeper

- [Zookeeper 集群安装配置，超详细！](articles/后端技术/Zookeeper/Zookeeper%20集群安装配置，超详细！.md)

#### 开源框架

- [2019 好戏连连，阿里又开源了一个分布式事务框架！](articles/后端技术/开源框架/2019%20好戏连连，阿里又开源了一个分布式事务框架！.md)
- [腾讯，排行前10的开源项目](articles/后端技术/开源框架/腾讯，排行前10的开源项目.md)
- [阿里启动新项目：Nacos，比 Eureka 更强！](articles/后端技术/开源框架/阿里启动新项目：Nacos，比%20Eureka%20更强！.md)
- [阿里巴巴，排行前 10 的开源项目](articles/后端技术/开源框架/阿里巴巴，排行前%2010%20的开源项目.md)

#### 日志

- [Java 正确获取客户端真实 IP 方法整理](articles/后端技术/日志/Java%20正确获取客户端真实%20IP%20方法整理.md)
- [Java 程序如何正确的打日志](articles/后端技术/日志/Java%20程序如何正确的打日志.md)
- [坑爹的日志无法按天切割问题！](articles/后端技术/日志/坑爹的日志无法按天切割问题！.md)
- [日志打印的 8 种级别，很详细](articles/后端技术/日志/日志打印的%208%20种级别，很详细.md)

#### 消息队列


#### Kafka

- [Spring Boot & Kafka 实战](articles/后端技术/消息队列/Kafka/Spring%20Boot%20&%20Kafka%20实战.md)
- [告诉你，Kafka 是个牛逼货！](articles/后端技术/消息队列/Kafka/告诉你，Kafka%20是个牛逼货！.md)
- [为什么高并发需要用消息队列？](articles/后端技术/消息队列/为什么高并发需要用消息队列？.md)
- [消息中间件如何选型？](articles/后端技术/消息队列/消息中间件如何选型？.md)

#### 缓存

- [缓存雪崩，穿透，预热，热备都是什么鬼？](articles/后端技术/缓存/缓存雪崩，穿透，预热，热备都是什么鬼？.md)

#### 连接池

- [Druid - 目前最好的连接池](articles/后端技术/连接池/Druid%20-%20目前最好的连接池.md)
- [一次 Druid 连接池泄露引发的血案。。](articles/后端技术/连接池/一次%20Druid%20连接池泄露引发的血案。。.md)
- [阿里 Druid 连接池的坑。。](articles/后端技术/连接池/阿里%20Druid%20连接池的坑。。.md)
- [阿里 Druid 连接池监控的两个坑](articles/后端技术/连接池/阿里%20Druid%20连接池监控的两个坑.md)

#### 综合技术

- [2018最新后端开发人员的路线图](articles/后端技术/综合技术/2018最新后端开发人员的路线图.md)
- [Java Cookie 增删改查操作。](articles/后端技术/综合技术/Java%20Cookie%20增删改查操作。.md)
- [Java 接入 Google 身份验证器实战](articles/后端技术/综合技术/Java%20接入%20Google%20身份验证器实战.md)
- [Java 程序员必备的 15 个框架，前 3 个地位无可动摇！](articles/后端技术/综合技术/Java%20程序员必备的%2015%20个框架，前%203%20个地位无可动摇！.md)
- [SaaS,PaaS,IaaS都是什么鬼？](articles/后端技术/综合技术/SaaS,PaaS,IaaS都是什么鬼？.md)
- [一个比 Spring Boot 快 44 倍的 Java 框架！](articles/后端技术/综合技术/一个比%20Spring%20Boot%20快%2044%20倍的%20Java%20框架！.md)
- [什么是 CPU 密集型、IO密集型？](articles/后端技术/综合技术/什么是%20CPU%20密集型、IO密集型？.md)
- [什么是Kotlin？Java的替代语言？](articles/后端技术/综合技术/什么是Kotlin？Java的替代语言？.md)
- [参数验证 @Validated 和 @Valid 的区别](articles/后端技术/综合技术/参数验证%20@Validated%20和%20@Valid%20的区别.md)

## 数据库

#### MySQL

待更新……

#### Redis

- [Redis是什么？4大优势，必学中间件！](articles/数据库/Redis/Redis是什么？4大优势，必学中间件！.md)
- [Redis 的 8 大应用场景！](articles/数据库/Redis/Redis%20的%208%20大应用场景！.md)
- [Redis Linux 安装运行实战全记录](articles/数据库/Redis/Redis%20Linux%20安装运行实战全记录.md)
- [超详细的 Redis Cluster 官方集群搭建指南](articles/数据库/Redis/超详细的%20Redis%20Cluster%20官方集群搭建指南.md)
- [Redis 再牛逼，也得设置密码！！](articles/数据库/Redis/Redis%20再牛逼，也得设置密码！！.md)
- [Redis 如何分析慢查询操作？](articles/数据库/Redis/Redis%20如何分析慢查询操作？.md)
- [Redis 常用操作命令，非常详细！](articles/数据库/Redis/Redis%20常用操作命令，非常详细！.md)
- [Redisson - Redis官方推荐的分布式协调客服端](articles/数据库/Redis/Redisson%20-%20Redis官方推荐的分布式协调客服端.md)
- [Spring Boot Redis Cluster 实战干货](articles/数据库/Redis/Spring%20Boot%20Redis%20Cluster%20实战干货.md)
- [Spring Data Redis 详解及实战一文搞定](articles/数据库/Redis/Spring%20Data%20Redis%20详解及实战一文搞定.md)
- [一个致命的 Redis 命令，导致公司损失 400 万！！](articles/数据库/Redis/一个致命的%20Redis%20命令，导致公司损失%20400%20万！！.md)

#### MongoDB

- [什么是 MongoDB](articles/数据库/MongoDB/什么是%20MongoDB.md)
- [MongoDB 删除数据用法](articles/数据库/MongoDB/MongoDB%20删除数据用法.md)
- [MongoDB 安装、配置、启动、连接](articles/数据库/MongoDB/MongoDB%20安装、配置、启动、连接.md)
- [MongoDB 插入数据用法](articles/数据库/MongoDB/MongoDB%20插入数据用法.md)
- [MongoDB 数据库与集合特性](articles/数据库/MongoDB/MongoDB%20数据库与集合特性.md)
- [MongoDB 更新数据用法](articles/数据库/MongoDB/MongoDB%20更新数据用法.md)
- [MongoDB 查询数据用法](articles/数据库/MongoDB/MongoDB%20查询数据用法.md)
- [MongoDB 索引篇](articles/数据库/MongoDB/MongoDB%20索引篇.md)
- [MongoDB 集成到SpringBoot](articles/数据库/MongoDB/MongoDB%20集成到SpringBoot.md)
- [MongoDB 从Oracle导入数据](articles/数据库/MongoDB/MongoDB%20从Oracle导入数据.md)

## 分布式技术

- [到底什么是分布式系统？](articles/分布式技术/到底什么是分布式系统？.md)
- [分布式 ID 生成器的解决方案总结](articles/分布式技术/分布式%20ID%20生成器的解决方案总结.md)
- [分布式 Session 共享解决方案](articles/分布式技术/分布式%20Session%20共享解决方案.md)
- [分布式服务防雪崩熔断器，Hystrix 理论实战。](articles/分布式技术/分布式服务防雪崩熔断器，Hystrix%20理论实战。.md)
- [分布式系统中处理参数配置的 4 种方案](articles/分布式技术/分布式系统中处理参数配置的%204%20种方案.md)
- [分布式系统架构常识：CAP理论。](articles/分布式技术/分布式系统架构常识：CAP理论。.md)

## 架构

- [一张图告诉你为什么是服务网关](articles/架构/一张图告诉你为什么是服务网关.md)
- [一张图告诉你什么是系统架构师](articles/架构/一张图告诉你什么是系统架构师.md)
- [到底什么是跨域？附解决方案！](articles/架构/到底什么是跨域？附解决方案！.md)
- [服务降级的概念及应用手段](articles/架构/服务降级的概念及应用手段.md)
- [服务高可用：幂等性设计](articles/架构/服务高可用：幂等性设计.md)
- [架构师必须掌握的 10 条设计原则](articles/架构/架构师必须掌握的%2010%20条设计原则.md)
- [SLA 服务可用性 4 个 9 是什么意思？怎么达到？](articles/架构/SLA%20服务可用性%204%20个%209%20是什么意思？怎么达到？.md)
- [自从用了灰度发布，睡觉真香！](articles/架构/自从用了灰度发布，睡觉真香！.md)

## 设计模式

待更新……

## 数据结构 & 算法

- [常用缓存淘汰算法详解](articles/算法/常用缓存淘汰算法详解.md)
- [接口限流算法：漏桶算法 & 令牌桶算法。](articles/算法/接口限流算法：漏桶算法%20&%20令牌桶算法。.md)
- [浅析负载均衡的 6 种算法，Ngnix 的 5 种算法。](articles/算法/浅析负载均衡的%206%20种算法，Ngnix%20的%205%20种算法。.md)
- [递归算法介绍及 Java 应用实战](articles/算法/递归算法介绍及%20Java%20应用实战.md)

## 规范

- [疯了！同事又问我为什么不能用 isXXX](articles/规范/疯了！同事又问我为什么不能用%20isXXX.md)
- [阿里巴巴制定了这 16 条设计规约！](articles/规范/阿里巴巴制定了这%2016%20条设计规约！.md)

## 安全漏洞

- [JDK 紧急漏洞，XMLDecoder反序列化攻击](articles/安全漏洞/JDK%20紧急漏洞，XMLDecoder反序列化攻击.md)
- [Slow HTTP POST 慢速攻击](articles/安全漏洞/Slow%20HTTP%20POST%20慢速攻击.md)
- [会话固定攻击漏洞（session fixation attack）](articles/安全漏洞/会话固定攻击漏洞（session%20fixation%20attack）.md)

## 性能优化

- [44 个 Java 代码性能优化总结](articles/性能优化/44%20个%20Java%20代码性能优化总结.md)
- [90 % Java 程序员被误导的一个性能优化策略](articles/性能优化/90%20%%20Java%20程序员被误导的一个性能优化策略.md)
- [8 条关于 Web 前端性能的优化建议](articles/性能优化/8%20条关于%20Web%20前端性能的优化建议.md)
- [网站性能测试指标详解](articles/性能优化/网站性能测试指标详解.md)

## 行业动态

- [GitHub for mobile 来了，码农苦逼了！](articles/行业动态/GitHub%20for%20mobile%20来了，码农苦逼了！.md)
- [全球 IPv4 地址正式耗尽](articles/行业动态/全球%20IPv4%20地址正式耗尽.md)
- [今天是 Java 诞生日，Java 24 岁了！](articles/行业动态/今天是%20Java%20诞生日，Java%2024%20岁了！.md)
- [6月来了，Java还是第一！](articles/行业动态/6月来了，Java还是第一！.md)
- [Elastic Search 上市了，市值翻倍，这群人财务自由了！](articles/行业动态/Elastic%20Search%20上市了，市值翻倍，这群人财务自由了！.md)
- [Hystrix 停止开发，Spring Cloud 何去何从？](articles/行业动态/Hystrix%20停止开发，Spring%20Cloud%20何去何从？.md)
- [Java 11 快要来了，编译 & 运行一个命令搞定！](articles/行业动态/Java%2011%20快要来了，编译%20&%20运行一个命令搞定！.md)
- [Java 13 发布了！](articles/行业动态/Java%2013%20发布了！.md)
- [Java 9 被无情抛弃，Java 8 直接升级到 Java10！！](articles/行业动态/Java%209%20被无情抛弃，Java%208%20直接升级到%20Java%2010！！.md)
- [Java 序列化技术即将被废除！！！](articles/行业动态/Java%20序列化技术即将被废除！！！.md)
- [Junit5 新功能一览](articles/行业动态/Junit5%20新功能一览.md)
- [天了噜，Java 8 要停止维护了！](articles/行业动态/天了噜，Java%208%20要停止维护了！.md)
- [屌炸天，Oracle 发布了一个全栈虚拟机！](articles/行业动态/屌炸天，Oracle%20发布了一个全栈虚拟机！.md)
- [恭喜微微软喜当爹，Github 嫁入豪门。](articles/行业动态/恭喜微微软喜当爹，Github%20嫁入豪门。.md)
- [新年放大招：Github 私库免费了！](articles/行业动态/新年放大招：Github%20私库免费了！.md)
- [Eclipse 代替 Oracle 接管 Java EE](articles/行业动态/Eclipse%20代替%20Oracle%20接管%20Java%20EE.md)

## Linux

- [Java程序员必须掌握的常用Linux命令。](articles/Linux/Java程序员必须掌握的常用Linux命令。.md)
- [开放端口命令](articles/Linux/开放端口命令.md)

## 区块链

- [2017 双11：区块链在阿里巴巴天猫中的应用](articles/区块链/2017%20双11：区块链在阿里巴巴天猫中的应用.md)
- [中山大学深圳研究院“智能金融和区块链技术”论坛（理论+实战PPT）](articles/区块链/中山大学深圳研究院“智能金融和区块链技术”论坛（理论+实战PPT）.md)
- [什么是以太坊？](articles/区块链/什么是以太坊？.md)
- [什么是共识机制？](articles/区块链/什么是共识机制？.md)
- [以太坊私有链搭建](articles/区块链/以太坊私有链搭建.md)
- [区块链怎么学习？](articles/区块链/区块链怎么学习？.md)

## 工具

#### Eclipse

- [10 个 Eclipse 珍藏插件推荐](articles/工具/Eclipse/10%20个%20Eclipse%20珍藏插件推荐.md)
- [Eclipse 最常用的 10 组快捷键！](articles/工具/Eclipse/Eclipse%20最常用的%2010%20组快捷键！.md)

#### Intellij IDEA

- [IDEA 公司推出新字体，极度舒适~](articles/工具/Intellij%20IDEA/IDEA%20公司推出新字体，极度舒适~.md)
- [Intellij IDEA 智能补全的 10 个姿势，太牛逼了。。](articles/工具/Intellij%20IDEA/Intellij%20IDEA%20智能补全的%2010%20个姿势，太牛逼了。。.md)
- [Intellij IDEA 最头大的问题，如何自定义注释模板？](articles/工具/Intellij%20IDEA/Intellij%20IDEA%20最头大的问题，如何自定义注释模板？.md)
- [Intellij IDEA 最新乱码解决必杀技](articles/工具/Intellij%20IDEA/Intellij%20IDEA%20最新乱码解决必杀技.md)
- [Intellij IDEA 最蛋疼的 10 个问题！](articles/工具/Intellij%20IDEA/Intellij%20IDEA%20最蛋疼的%2010%20个问题！.md)
- [Intellij IDEA 阅读源码的 4 个绝技！](articles/工具/Intellij%20IDEA/Intellij%20IDEA%20阅读源码的%204%20个绝技！.md)

#### Git

- [Git SSH 连接方式配置](articles/工具/Git/Git%20SSH%20连接方式配置.md)
- [Git 仓库操作命令](articles/工具/Git/Git%20仓库操作命令.md)
- [Git 分支管理](articles/工具/Git/Git%20分支管理.md)
- [Git 提交记住用户名和密码](articles/工具/Git/Git%20提交记住用户名和密码.md)
- [Git 文件操作命令](articles/工具/Git/Git%20文件操作命令.md)
- [Git 的安装配置](articles/工具/Git/Git%20的安装配置.md)
- [Git 的这个神技，学会爽歪歪~](articles/工具/Git/Git%20的这个神技，学会爽歪歪~.md)
- [一文告诉你如何导出 Git 变更文件](articles/工具/Git/一文告诉你如何导出%20Git%20变更文件.md)
- [推荐一个实用的 .gitignore 文件](articles/工具/Git/推荐一个实用的%20.gitignore%20文件.md)

#### Maven

- [Maven classifier 元素妙用](articles/工具/Maven/Maven%20classifier%20元素妙用.md)
- [Maven Optional & Exclusions使用区别](articles/工具/Maven/Maven%20Optional%20&%20Exclusions使用区别.md)
- [Maven 如何发布 jar 包到 Nexus 私库](articles/工具/Maven/Maven%20如何发布%20jar%20包到%20Nexus%20私库.md)
- [Maven 远程仓库地址](articles/工具/Maven/Maven%20远程仓库地址.md)

#### Swagger

- [Spring Boot 集成 Swagger，生成接口文档就这么简单！](articles/工具/Swagger/Spring%20Boot%20集成%20Swagger，生成接口文档就这么简单！.md)
- [推荐一款接口 API 设计神器！](articles/工具/Swagger/推荐一款接口%20API%20设计神器！.md)

#### 效率

- [3种骚操作，教你查看 Java 字节码！](articles/工具/效率/3种骚操作，教你查看%20Java%20字节码！.md)
- [推荐一个在线创作流程图、思维导图软件](articles/工具/效率/推荐一个在线创作流程图、思维导图软件.md)
- [推荐一款代码神器，代码量至少省一半！](articles/工具/效率/推荐一款代码神器，代码量至少省一半！.md)
- [推荐一款阿里开源的 Java 诊断工具，好用到爆！](articles/工具/效率/推荐一款阿里开源的%20Java%20诊断工具，好用到爆！.md)
- [神器，阿里巴巴 Java 代码检查插件](articles/工具/效率/神器，阿里巴巴%20Java%20代码检查插件.md)

#### 工具类

- [Java界的瑞士军刀，无法想象的轻量级工具包](articles/工具/工具类/Java界的瑞士军刀，无法想象的轻量级工具包.md)
- [排名前 16 的 Java 工具类](articles/工具/工具类/排名前%2016%20的%20Java%20工具类.md)

## 面试

- [10 道腾讯的 Java 面试题](articles/面试/10%20道腾讯的%20Java%20面试题.md)
- [18 道 Java 基础面试笔试题](articles/面试/18%20道%20Java%20基础面试笔试题.md)
- [75 道 BAJT 中高级 Java 面试题，你能答上几道？](articles/面试/75%20道%20BAJT%20中高级%20Java%20面试题，你能答上几道？.md)
- [Java 中创建 String 的两道面试题及详解](articles/面试/Java%20中创建%20String%20的两道面试题及详解.md)
- [Java 面试题经典 77 问（含答案）！](articles/面试/Java%20面试题经典%2077%20问！.md)
- [Redis 面试题 50 问，史上最全。](articles/面试/Redis%20面试题%2050%20问，史上最全。.md)
- [为什么面试你要25K，HR只给你20K？](articles/面试/为什么面试你要25K，HR只给你20K？.md)
- [为什么面试完，总是让你回去等通知？](articles/面试/为什么面试完，总是让你回去等通知？.md)
- [去 BAT 面试完的 MySQL 面试题总结](articles/面试/去%20BAT%20面试完的%20MySQL%20面试题总结.md)
- [史上最全 40 道 Dubbo 面试题及答案！](articles/面试/史上最全%2040%20道%20Dubbo%20面试题及答案！.md)
- [史上最全 Java 多线程面试题及答案](articles/面试/史上最全%20Java%20多线程面试题及答案.md)
- [史上最全 Javascript 面试题及答案](articles/面试/史上最全%20Javascript%20面试题及答案.md)
- [史上最全阿里 Java 面试题总结](articles/面试/史上最全阿里%20Java%20面试题总结.md)
- [吐血整理 20 道 Spring Boot 面试题！](articles/面试/吐血整理%2020%20道%20Spring%20Boot%20面试题！.md)
- [周末去面试，进去 5 分钟就出来了…](articles/面试/周末去面试，进去%205%20分钟就出来了….md)
- [程序员如何面对 HR 面试的 40 的问题！](articles/面试/程序员如何面对%20HR%20面试的%2040%20的问题！.md)
- [通往大神之路，百度Java面试题前 200 页。](articles/面试/通往大神之路，百度Java面试题前%20200%20页。.md)
- [阿里巴巴高级 Java 面试题 70 道](articles/面试/阿里巴巴高级%20Java%20面试题%2070%20道.md)
- [面试一个 3 年 Java 程序员，一个问题都不会！](articles/面试/面试一个%203%20年%20Java%20程序员，一个问题都不会！.md)


## 程序人生

- [10年老兵给程序员的 10 条建议！](articles/程序人生/10年老兵给程序员的%2010%20条建议！.md)
- [IG赢了，微博挂了](articles/程序人生/IG赢了，微博挂了.md)
- [一个程序员的国庆血泪相亲史，惨败而归…](articles/程序人生/一个程序员的国庆血泪相亲史，惨败而归….md)
- [为什么公司宁愿 25K 重新招人，也不给你加到 20K？](articles/程序人生/为什么公司宁愿%2025K%20重新招人，也不给你加到%2020K？.md)
- [从 0 开始搭建一个技术博客，私藏干货~](articles/程序人生/从%200%20开始搭建一个技术博客，私藏干货~.md)
- [国庆加了三天班，公司不给钱，咋办？](articles/程序人生/国庆加了三天班，公司不给钱，咋办？.md)
- [在外包干了三年，我废了……不吹不黑！](articles/程序人生/在外包干了三年，我废了……不吹不黑！.md)
- [年底了，是时候做个了断了。。。](articles/程序人生/年底了，是时候做个了断了。。。.md)
- [微博什么技术啊……还说支持八个明星并发出轨，结果…](articles/程序人生/微博什么技术啊……还说支持八个明星并发出轨，结果….md)
- [毕业不到一年，绩效打了个D！](articles/程序人生/毕业不到一年，绩效打了个D！.md)
- [相亲无数，全靠招数](articles/程序人生/相亲无数，全靠招数.md)
- [程序猿，你工作几年了？](articles/程序人生/程序猿，你工作几年了？.md)
- [聊聊我面试过的一个最奇葩的 Java 程序猿！](articles/程序人生/聊聊我面试过的一个最奇葩的%20Java%20程序猿！.md)
- [过了所有技术面，却倒在 HR 一个问题上。。](articles/程序人生/过了所有技术面，却倒在%20HR%20一个问题上。。.md)
- [过年回家，程序猿最怕的 5 件事，深有感触。](articles/程序人生/过年回家，程序猿最怕的%205%20件事，深有感触。.md)
- [都几套房了，还写个毛的代码！](articles/程序人生/都几套房了，还写个毛的代码！.md)

## 资料

- [《全球架构师峰会》参会回来，想和大家分享下](articles/资料/《全球架构师峰会》参会回来，想和大家分享下.md)

## 其他

- [代码写成这样，老夫无可奈何！](articles/其他/代码写成这样，老夫无可奈何！.md)
- [写完这段代码，就被开除了……](articles/其他/写完这段代码，就被开除了…….md)
- [刚写完排序算法，就被开除了…](articles/其他/刚写完排序算法，就被开除了….md)
- [20200202，千年难遇啊！](articles/其他/20200202，千年难遇啊！.md)


## 结束语

不定期陆续更新中……

希望大家能真正学到东西，有错误之处，欢迎提出。

如果你想投稿贡献、转载，关注微信公众号了解详情，关注微信公众号，最新干货，第一时间推送。

![](http://img.javastack.cn/javastack.png)
