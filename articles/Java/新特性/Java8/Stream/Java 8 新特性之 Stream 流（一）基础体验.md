Java8新增的功能中，要数lambda表达式和流API最为重要了这篇文章主要介绍流API的基础，也是流API系列的第一篇文章，话不多说，直奔主题

#### 什么是流API? 它能做一些什么?

我们应该知道(绝对知道~)API是一个程序向使用者提供的一些方法，通过这些方法就能实现某些功能。所以对于流API来说，重点是怎么理解"流"这个概念，所谓的流:就是数据的渠道，所以，流代表的是一个对象的序列它和Java I/O类里使用的"流"不同。虽然在概念上与java.util.stream中定义的流是类似的，但它们是不同的，流API中的流是描述某个流类型的对象。

流API中的流操作的数据源，是数组或者是集合。它本身是不存储数据的，只是移动数据，在移动过程中可能会对数据进行过滤，排序或者其它操作，但是，一般情况下(绝大数情况下)，流操作本身不会修改数据源，比如，对流排序不会修改数据源的顺序。相反，它会创建一个新的流，其中包含排序后的结果。

#### 从一个简单的例子，体验流API的强大与优雅
  
这个简单的Demo，主要是对一个由1-6乱序组成的List对应的流进行操作，然后通过这个流，就可以获取到列表里面最大最小值、排序、过滤某些元素等等的操作。并且这此操作不会改变原List里面的数据。Demo里面需要注意的地方就是流API里面的"终端操作"和"中间操作"的区别:其实也很简单，终端操作会消费流，一个被消费过的流是不能被再次利用的，但我们在实际应用的时候，并不会受到太大的影响(Ps:如果你们能动手实践一下我相信你体验更好，强烈推荐！)

```
public class Main {


    public static void main(String[] args) { 
        learnStream();
    }

    private static void learnStream() {
        //首先，创建一个1-6乱序的List
        List<Integer> lists = new ArrayList<>();
        lists.add(4);
        lists.add(3);
        lists.add(6);
        lists.add(1);
        lists.add(5);
        lists.add(2);

        //看看List里面的数据是什么样子的先
        System.out.print("List里面的数据:");
        for (Integer elem : lists) System.out.print(elem + " ");// 4 3 6 1 5 2

        System.out.println();

        //最小值
        System.out.print("List中最小的值为:");
        Stream<Integer> stream = lists.stream();
        Optional<Integer> min = stream.min(Integer::compareTo);
        if (min.isPresent()) {
            System.out.println(min.get());//1
        }


        //最大值
        System.out.print("List中最大的值为:");
        lists.stream().max(Integer::compareTo).ifPresent(System.out::println);//6

        //排序
        System.out.print("将List流进行排序:");
        Stream<Integer> sorted = lists.stream().sorted();
        sorted.forEach(elem -> System.out.print(elem + " "));// 1 2 3 4 5 6

        System.out.println();

        //过滤
        System.out.print("过滤List流，只剩下那些大于3的元素:");
        lists.stream()
                .filter(elem -> elem > 3)
                .forEach(elem -> System.out.print(elem + " "));// 4 5 6

        System.out.println();

        //过滤
        System.out.println("过滤List流，只剩下那些大于0并且小于4的元素:\n=====begin=====");
        lists.stream()
                .filter(elem -> elem > 0)
                .filter(elem -> elem < 4)
                .sorted(Integer::compareTo)
                .forEach(System.out::println);// 1 2 3

        System.out.println("=====end=====");
        //经过了前面的这么多流操作，我们再来看看List里面的值有没有发生什么改变
        System.out.print("原List里面的数据:");
        for (Integer elem : lists) System.out.print(elem + " ");// 4 3 6 1 5 2
    }
}
```

如果刚才的Demo你认真读了，我相信你心里面多多少少都会产生一点点涟漪，没错，流API结合lambda表达式，就是这么优美!下面我详细介绍一下整个Demo，让大家更加清淅:

### 最小值

```
 //最小值
System.out.print("List中最小的值为:");
Stream<Integer> stream = lists.stream();
Optional<Integer> min = stream.min(Integer::compareTo);
if (min.isPresent()) {
     System.out.println(min.get());//1
}
```
首先通过stream()方法获取List对应的流，如果你对Java8的集合框架有一定的了解，你应该知道stream()是由Collection接口提供的。然后就可以通过min()获取流中的最小值了，当然这个流中的最小值肯定也是List里面的最小值。min()方法接收一个Comparator类型的参数，这个比较器是用于比较流中的两个元素的。我们这里把Integer的compareTo()的引用传递给了min()。它返回的类型是Optional，Optional可谓是NullPointException的大杀器啊，感兴趣的同学，了解一下。然后判断最小值存不存在，如果存在，就通过Optional的get()读取出来。很简单有木有!

#### 最大值

```
//最大值
System.out.print("List中最大的值为:");
lists.stream().max(Integer::compareTo).ifPresent(System.out::println);//6
```
语法糖爽YY有木有，不过需要注意的一点，因为min()是一个终端操作，所以这个流是不可以再用了，因此我们需要通过stream()重新生成一个流，(但这其实并不影响我们的实际生产的:①方法功能单一原则②还有其它很多很强大的方法组合能让你实现各种功能啊.)ifPresent其实和上面的最小值的if判断是一定要，如果存在最大值，我们就打印一下，这里只不过用了一些函数式写法而已。

#### 排序和遍历

```
 //排序
System.out.print("将List流进行排序:");
Stream<Integer> sorted = lists.stream().sorted();
sorted.forEach(elem -> System.out.print(elem + " "));// 1 2 3 4 5 6
```
通过上面的讲解，相信这个已经难不了你了，sorted()方法是用于排序的，它的一个重载方法可以接收一个Comparator类型的参数，让你自定义你的排序规则。forEach方法就遍历。

#### 过滤

```
//过滤
System.out.println("过滤List流，只剩下那些大于0并且小于4的元素:\n=====begin=====");
lists.stream()
          .filter(elem -> elem > 0)
          .filter(elem -> elem < 4)
          .sorted(Integer::compareTo)
          .forEach(System.out::println);// 1 2 3

System.out.println("=====end=====");
```
filter()是基于一个谓词过滤流，它返回一个只包含满足谓词的元素的新流。它的参数形式是Predicate<? super T>是在java.util.function包下的泛型函数式接口。并且filter是一个中间操作，而且还可以同时存在多个filter。这里的两个过滤器，我们都传递了lambda表达式。

#### 小结一下

其实基本的流API使用就是这么简单，结合lambda表达式后，一切都变得特别清淅，这个简单的Demo展示了一些基础的功能，它或许就扩展了你操作数组或者集合框架的思路，让你操作集合和数组，变得更加的容易，简单和高效。当然流API的的功能肯定不止这一点点，我会陆陆续续完善整个系列，大家跟着我的步伐，就这一次，踏踏实实学好流式API，走向人生巅峰，迎娶白富美！
