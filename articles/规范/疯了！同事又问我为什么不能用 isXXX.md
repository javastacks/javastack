最近在做Code Review，写下了这篇文章：[代码写成这样，老夫无可奈何！](https://mp.weixin.qq.com/s/_ehJH-JWEBXg4FznDsDMYA)，说多了都是泪啊。。

**最近又有人同事跑过来质疑我：** 为什么变量名取名不能用 `isXXX` 这种方式，这样有什么问题？！

醉了，讲了好多次都记不住，我让他自己去看阿里巴巴的《Java开发手册》，或者自行百度，说实话，有点工作经验人都知道，这都是基础的东西。

本没什么好写的，鉴于有好多小白程序员，今天栈长就把为什么不能用 `isXXX` 拿出来分享一篇文章吧，希望对你有用。

首先我们来看阿里巴巴的《Java开发手册》关于 `isXXX` 是怎么定义的吧：

>  【强制】POJO 类中布尔类型变量都不要加 is 前缀，否则部分框架解析会引起序列化错误。
> 反例：定义为基本数据类型 Boolean isDeleted 的属性，它的方法也是 isDeleted()，RPC 框架在反向解析的时候，“误以为”对应的属性名称是 deleted，导致属性获取不到，进而抛出异常。

上面的规范很容易理解吧，就是如果使用 `isXXX` 这种命名形式会引起潜在的异常呗！

获取阿里巴巴的《Java开发手册》完整版，请关注微信公众号：Java技术栈，在后台回复：手册。

**我们再来看一段 IDE 生成的 getter/setter 代码：**

```
public class Staff {

    private String name;
    private boolean graduated;
    private boolean isMarried;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

}
```

变量 `isMarried` 生成的 getter/setter 方法是：isMarried/setMarried，有些框架就会去找对应的 `married` 变量，然后就找不到了…

再看变量 `graduated`，因为都是 boolean 变量，所有生成的逻辑和 `isMarried` 一样，根据上述规范，它不会产生找不着值的情况，这也是为什么不建议使用 `isXXX` 的命名形式。

这个问题我之前有个同事在使用某个 Web 框架的时候就遇到过，在页面上使用该框架的标签显示对象的值，如：xx.xxx，然后就死活取不到值，还报异常，最后定位就是这个问题。

再看到有同事这样写，或者问你为什么，把这篇文章丢给他吧。。

关注**Java技术栈**微信公众号，栈长将继续分享好玩的 Java 技术，公众号第一时间推送，在公众号后台回复：Java，可以获取历史 Java 教程，都是干货。

