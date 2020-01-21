前段时间分享了《[阅读跟踪 Java 源码的几个小技巧](https://mp.weixin.qq.com/s/gWMQT6yQhOwcylhKqNJknQ)》是基于 Eclipse 版本的，看大家的留言都是想要 IDEA 版本的源码阅读技巧。

所以，为了满足众多 IDEA 粉丝的要求，栈长我特意做一期 IDEA 版的。

## 1、定位到方法实现类

```
public static Object getBean(String name) {
	return applicationContext.getBean(name);
}
```

如以上代码，IDEA 如何跳转到 getBean 方法的实现类？

在 IDEA 中，CTRL + 鼠标左击 默认会跳到方法所在的接口，如 getBean 就会跳到 BeanFactory 接口里面去。

> org.springframework.beans.factory.BeanFactory

![](http://img.javastack.cn/18-11-8/28812875.jpg)

在 IDEA 中略显麻烦，在引用的方法上 CTRL + ALT + 鼠标左击（B）可以实现跳转至实现类，如果有多个实现类会弹出让你选择。

![](http://img.javastack.cn/18-11-8/8414314.jpg)

点击右上角 Open as Tool Window 图标还能展开更多详细。

![](http://img.javastack.cn/18-11-8/76662103.jpg)

## 2、查看类层级关系

到了 BeanFactory 类，在任意地方使用快捷键 CTRL + H 可以打开类的继承层级面板。

![](http://img.javastack.cn/18-11-8/49197466.jpg)

或者直接选中 BeanFactory 类名称，再按 CTRL + ALT + 鼠标左击（B），同样可以展示 BeanFactory 类的所有继承类的关系。

![](http://img.javastack.cn/18-11-8/19092696.jpg)

还有更屌的！

右键任意类，选择 Diagrams > Show Diagram... 可以打开类的继承图。

![](http://img.javastack.cn/18-11-8/15269334.jpg)

![](http://img.javastack.cn/18-11-8/989246.jpg)

![](http://img.javastack.cn/18-11-8/84258483.jpg)

![](http://img.javastack.cn/18-11-8/84659140.jpg)

看起来有点乱，那是因为我选择了所有实现类展示，实际请合理利用  Show Parents 和 Show Implementations 菜单。

## 3、查看类结构

![](http://img.javastack.cn/18-11-8/55959388.jpg)

![](http://img.javastack.cn/18-11-8/81818680.jpg)

如上图所示，IDEA 中也有 Eclipse 中 outline 的面板，叫作：Structure，有了这个面板，可以快速浏览一个类的大纲，也可以快速定位到类的方法、变量等。

也可以使用快捷键 Alt + 7 调出这个面板。

## 4、源码统计

![](http://img.javastack.cn/18-11-8/10605575.jpg)

![](http://img.javastack.cn/18-11-8/18502731.jpg)

如图所示，栈长在 IDEA 中装了一个 Statistic 插件，这个插件只支持 JDK 1.8+。

有了这个插件，它可以在项目中按文件类型进行代码的行数、数量、大小统计。这样你就能知道整个源码的总体数量，你也可以把已经阅读的做统计，慢慢实现源码阅读的攻破。

熟练的运用 IDEA 中各个小技巧，让阅读跟踪源码变得更轻松。

更多往期 IDEA 技术文章可以关注Java技术栈微信公众号，在后台回复关键字：IDEA。

## 知识延伸

本文大部分内容摘自《Java技术栈》知识星球。

公众号留言不能联系上下文，有些问题不是很方便进行回复。想学习更多的 IDEA 技巧，可以点击「Java技术栈」链接加入栈长的知识星球，有问题可以在上面提问，栈长有空都会给予回复。作为付费粉丝的回报，栈长也会偶尔在星球上分享一些工作中 IDEA 使用的小技巧。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "idea" 可获取更多，转载请原样保留本信息。