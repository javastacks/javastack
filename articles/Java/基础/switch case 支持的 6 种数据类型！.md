
有粉丝建议可以偶尔推送一些 Java 方面的基础知识，一方面可以帮助一初学者，也可以兼顾中高级的开发者。

那么今天就讲一下 Java 中的 switch case 语句吧，有忘记的同学正好可以温习一下。

**Java 中 switch case 语句用来判断一个变量与一系列值中某个值是否相等，每个值称为一个分支。**

**语法格式如下：**

```
switch(expression){
    case value :
       //语句
       break; //可选
    case value :
       //语句
       break; //可选
    //你可以有任意数量的case语句
    default : //可选
       //语句
}
```

**这里的 `expression` 都支持哪些类型呢？**

- 基本数据类型：byte, short, char, int

- 包装数据类型：Byte, Short, Character, Integer

- 枚举类型：Enum

- 字符串类型：String（Jdk 7+ 开始支持）

基本数据类型和字符串很简单不用说，下面举一个使用包装类型和枚举的，其实也不难，注意只能用在 switch 块里面。

```
// 使用包装类型
Integer value = 5;
switch (value) {
	case 3:
		System.out.println("3");
		break;
	case 5:
		System.out.println("5");
		break;
	default:
		System.out.println("default");
}

// 使用枚举类型
Status status = Status.PROCESSING;
switch (status) {
	case OPEN:
		System.out.println("open");
		break;
	case PROCESSING:
		System.out.println("processing");
		break;
	case CLOSE:
		System.out.println("close");
		break;
	default:
		System.out.println("default");
}
```

以下为官网的介绍文档。
> https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html

包装类不懂的看这篇文章《[深入浅出 Java 中的包装类](https://mp.weixin.qq.com/s/uoNVT9IoRQmWnmy8w22UUQ)》。

**使用 switch case 语句也有以下几点需要注意。**

1. case 里面必须跟 break，不然程序会一个个 case 执行下去，直到最后一个 break 的 case 或者 default 出现。

2. case 条件里面只能是常量或者字面常量。

3. default 语句可有可无，最多只能有一个。

有问题请留言，希望本文能对你有有所帮助！

