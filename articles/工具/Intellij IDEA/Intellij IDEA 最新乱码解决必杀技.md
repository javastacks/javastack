![](http://img.javastack.cn/20190903111112.png)

大家在使用 Intellij IDEA 的时候会经常遇到各种乱码问题，甚是烦扰。

栈长也偶尔会用下IDEA，也有一些解决乱码的经验，我给大家总结了以下几类乱码的场景，绝壁能帮你解决 IDEA 各种乱码问题。

## 常见乱码场景及解决方案

#### 1、项目源代码中文乱码

> Settings > Editor > File Encodings > Global Encodings & Project Encodings 设置为：`UTF-8`。

![](http://img.javastack.cn/20190807113633.png)

上面红色区域都统一设置一下。

#### 2、Main方法运行，控制台中文乱码

> Settings > Build, Execution, Deployment > Compile > Java Compiler > Additional command line parameters > 设置为：`-encoding utf-8`。

![](http://img.javastack.cn/20190807113844.png)

#### 3、Tomcat运行，控制台中文乱码

> Edit Configurations > Tomcat Server > server > VM options > 设置为：`-Dfile.encoding=UTF-8`。

![](http://img.javastack.cn/20190807114037.png)

> idea > bin > idea.exe.vmoptions 或者 idea64.exe.vmoptions 配置文件末尾添加：`-Dfile.encoding=UTF-8`。

![](http://img.javastack.cn/20190807134723.png)

如果还乱码，继续往下设置。

> idea > Help 菜单 > Edit Custom VM Options...菜单，编辑配置文件，在末尾添加：`-Dfile.encoding=UTF-8`。

![](http://img.javastack.cn/20190807114251.png)
![](http://img.javastack.cn/20190807114334.png)

好了，今天的表演就到这了，倍儿爽，建议收藏+转发，日后绝对有用~

关注Java技术栈微信公众号，栈长将继续分享 Intellij IDEA 的实战教程，公众号第一时间推送，持续关注。

在Java技术栈微信公众号后台回复：idea，获取栈长整理的更多的 Intellij IDEA 教程，都是实战干货，以下仅为部分预览。

- Intellij IDEA 最常用配置详细图解
- Intellij IDEA 非常6的10个姿势
- Intellij IDEA 所有乱码解决方案
- Intellij IDEA 阅读源码的4个绝技
- Intellij IDEA Debug调试技巧
- ……

如果你喜欢 IDEA, 可以点击阅读原文链接加入我们的知识星球，我会经常在上面分享 IDEA 的使用小技巧，对 IDEA 有什么不懂的也可以在上面向我和大家提问。
