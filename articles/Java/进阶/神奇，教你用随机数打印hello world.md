
下面是一段随机数程序。

```
public static void main(String[] args) {
	System.out.println(randomString(-229985452) + " " + randomString(-147909649));
}

public static String randomString(int seed) {
	Random ran = new Random(seed);
	StringBuilder sb = new StringBuilder();
	while (true) {
		int k = ran.nextInt(27);
		if (k == 0) {
			break;
		}
		sb.append((char) ('`' + k));
	}
	return sb.toString();
}
```

**每次都会输出：hello world，这是为什么呢？？**

Random构造函数，参数seed是初始种子，相同的种子每次产生的随机数都一样，所以无论怎么随机，构造出来的随机数都一样。

**Random r = new Random(-229985452)**

会产生以下5位不为0的随机数：


```
8
5
12
12
15
```

**Random r = new Random(-147909649)**

会产生以下5位不为0的随机数：


```
23
15
18
12
4
```

程序里面用\`符号相加，\`代表96.

所以，有下面的结果：


```
8  + 96 = 104 --> h
5  + 96 = 101 --> e
12 + 96 = 108 --> l
12 + 96 = 108 --> l
15 + 96 = 111 --> o

23 + 96 = 119 --> w
15 + 96 = 111 --> o
18 + 96 = 114 --> r
12 + 96 = 108 --> l
4  + 96 = 100 --> d
```

一段很简单的程序却如此神奇，分享给别人，看他知道不？

