
## 是什么是Stream流

`java.util.stream.Stream`

Stream流和传统的IO流，它们都叫流，却是两个完全不一样的概念和东西。

流可以简单的说是处理数据集合的东西，可以申明式流式API来处理集合，而不是写一个逻辑实现。

## 流分类

流分为顺序流及并行流，顺序流即每个指令按顺序执行，并行流即集合里面的操作并行执行。

```
List<Integer> numbers = Arrays.asList(1, 2, 3);

//　顺序流
numbers.stream().forEach(n -> System.out.print(n));

//并行流
numbers.parallelStream().forEach(n -> System.out.print(n));
```

以上例子，顺序流总是输出`123`，而并行流则每次结果都不一定，并行流使用了ForkJoinPool分而治之，所以明白了ForkJoinPool原理的同学就知道并行流的真面目了。

## 创建流

1、调用集合的stream()方法或者parallelStream()方法。

2、Stream.of()方法，有针对int,long的专用流IntStream，LongStream。

## 使用流

以下举了流的一些常用的用法。

```
public class StreamTest {

	public static void main(String[] args) {
		System.out.println("过滤－找出年纪大于18岁的人");
		List<User> list = initList();
		list.stream().filter((User user) -> user.getAge() > 18).collect(Collectors.toList())
				.forEach(System.out::println);
		System.out.println();

		System.out.println("最大值－找出最大年纪的人");
		list = initList();
		Optional<User> max = list.stream().max((u1, u2) -> u1.getAge() - u2.getAge());
		System.out.println(max.get());
		System.out.println();

		System.out.println("映射-规纳－求所有人的年纪总和");
		list = initList();
		Optional<Integer> reduce = list.stream().map(User::getAge).reduce(Integer::sum);
		System.out.println(reduce.get());
		System.out.println();

		System.out.println("分组－按年纪分组");
		list = initList();
		Map<Integer, List<User>> userMap = list.stream()
				.collect(Collectors.groupingBy(User::getAge));
		MapUtils.verbosePrint(System.out, null, userMap);
		System.out.println();

		System.out.println("创建－去重－统计");
		Stream<User> userStream = Stream
				.of(new User("u1", 1), new User("u2", 21), new User("u2", 21));
		System.out.println(userStream.distinct().count());
		System.out.println();

	}

	public static List<User> initList() {
		List<User> list = new ArrayList<>();
		list.add(new User("oaby", 23));
		list.add(new User("tom", 11));
		list.add(new User("john", 16));
		list.add(new User("jennis", 26));
		list.add(new User("tin", 26));
		list.add(new User("army", 26));
		list.add(new User("mack", 19));
		list.add(new User("jobs", 65));
		list.add(new User("jordan", 23));
		return list;
	}

}
```

输出结果：


```
过滤－找出年纪大于18岁的人
User [username=oaby, age=23]
User [username=jennis, age=26]
User [username=tin, age=26]
User [username=army, age=26]
User [username=mack, age=19]
User [username=jobs, age=65]
User [username=jordan, age=23]

最大值－找出最大年纪的人
User [username=jobs, age=65]

映射-规纳－求所有人的年纪总和
235

分组－按年纪分组
{
    16 = [User [username=john, age=16]]
    65 = [User [username=jobs, age=65]]
    19 = [User [username=mack, age=19]]
    23 = [User [username=oaby, age=23], User [username=jordan, age=23]]
    26 = [User [username=jennis, age=26], User [username=tin, age=26], User [username=army, age=26]]
    11 = [User [username=tom, age=11]]
}

创建－去重－统计
2
```

可以看出流操作数据集合很强大吧，但需要注意的是流只能执行一次，再次使用需要重要打开。

更多的玩法可以自己去研究吧。


