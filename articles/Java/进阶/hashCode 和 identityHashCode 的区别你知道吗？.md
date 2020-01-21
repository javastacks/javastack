
## hashCode

关于hashCode参考之前的文章，[点击](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247483797&idx=1&sn=e3bb47b13d0c3f25341470a0ce598752&chksm=eb5384a3dc240db5e6378dc5160fa01fd10569ffb5f95f5d5daa4589c3308515139b29e2b314#rd)参考之前文章。

## identityHashCode

identityHashCode是System里面提供的本地方法，java.lang.System#identityHashCode。

```
/**
 * Returns the same hash code for the given object as
 * would be returned by the default method hashCode(),
 * whether or not the given object's class overrides
 * hashCode().
 * The hash code for the null reference is zero.
 *
 * @param x object for which the hashCode is to be calculated
 * @return  the hashCode
 * @since   JDK1.1
 */
public static native int identityHashCode(Object x);
```

identityHashCode和hashCode的区别是，identityHashCode会返回对象的hashCode，而不管对象是否重写了hashCode方法。

## 示例


```
public static void main(String[] args) {
	String str1 = new String("abc");
	String str2 = new String("abc");
	System.out.println("str1 hashCode: " + str1.hashCode());
	System.out.println("str2 hashCode: " + str2.hashCode());
	System.out.println("str1 identityHashCode: " + System.identityHashCode(str1));
	System.out.println("str2 identityHashCode: " + System.identityHashCode(str2));

	User user = new User("test", 1);
	System.out.println("user hashCode: " + user.hashCode());
	System.out.println("user identityHashCode: " + System.identityHashCode(user));
}
```

输出结果：

```
str1 hashCode: 96354
str2 hashCode: 96354
str1 identityHashCode: 1173230247
str2 identityHashCode: 856419764
user hashCode: 621009875
user identityHashCode: 621009875
```

结果分析：

1、str1和str2的hashCode是相同的，是因为String类重写了hashCode方法，它根据String的值来确定hashCode的值，所以只要值一样，hashCode就会一样。

2、str1和str2的identityHashCode不一样，虽然String重写了hashCode方法，identityHashCode永远返回根据对象物理内存地址产生的hash值，所以每个String对象的物理地址不一样，identityHashCode也会不一样。

3、User对象没重写hashCode方法，所以hashCode和identityHashCode返回的值一样。

## 结论

hashCode方法可以被重写并返回重写后的值，identityHashCode会返回对象的hash值而不管对象是否重写了hashCode方法。

