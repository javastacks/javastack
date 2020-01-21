经过了前面四篇文章的学习，相信大家对Stream流已经是相当的熟悉了，同时也掌握了一些高级功能了，如果你之前有阅读过集合框架的基石`Collection`接口，是不是在经过前面的学习，以前看不懂的东西，突然之间就恍然大悟了呢？

今天我们的主角是Stream流里面的映射。由于之前，映射并没有再我们的Demo，例子中出现过，所以对大家来说可能会稍微有一点点陌生的，但通过这一篇文章，我相信能解决你的疑问。

在正式开始之前，我和大家继续说说流API操作，不知道大家有没有注意到，其实我们所有的流API操作都是针对流中的元素进行的，并且都是基于同一流里面的，大家有没有这样的疑问，怎么样把一个流的元素弄到另一个流里面呢？怎么把流中的一些满足条件的元素放到一个新流里面呢？

通过这一节，你将会掌握解决刚才问题的本领。另外再提一点，如果流操作只有中间操作，没有终端操作，那么这些中间操作是不会执行的，换句话说，只有终端操作才能触发中间操作的运行。

#### 我们为什么需要映射？

因为在很多时候，将一个流的元素映射到另一个流对我们是非常有帮助的。比如有一个包含有名字，手机号码和钱的数据库构成的流，可能你只想要映射钱这个字段到另一个流，这时候可能之前学到的知识就还不能解决，于是映射就站了出来了。

另外，如果你希望对流中的元素应用一些转换，然后把转换的元素映射到一个新流里面，这时候也可以用映射。

我们先来看看流API库给我们提供了什么样的支持

```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);//line2
    IntStream mapToInt(ToIntFunction<? super T> mapper);//line3
    LongStream mapToLong(ToLongFunction<? super T> mapper);//line4
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);//line5
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);//line6
    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);//line7
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);//line8
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);//line9
}
```

我和大家分析一个最具有一般性的映射方法map()，相信大家就能举一反三了，map()定义如下，

 **<R> Stream<R> map(Function<? super T, ? extends R> mapper);**

其中，R指定新流的元素类型，T指定调用流的元素类型，mapper是完成映射的Function实例，被称为映射函数，映射函数必须是无状态和不干预的（大家对这二个约束条件应该很熟悉了吧）。因为map()方法会返回一个新流，因此它是一个中间操作。

`Function`是 `java.util.function`包中声明的一个函数式接口，声明如下：

```
@FunctionalInterface
public interface Function<T, R> {
     R apply(T t);
}
```
在map()的使有过程中，T是调用流的元素类型，R是映射的结果类型。其中,apply(T t)中的t是对被映射对象的引用，被返回映射结果。下面我们将上一篇中的例子进行变形，用映射来完成他:

`假设List里面有三个Integer类型的元素分别为1，2，3。现在的需求是分别让List里面的每个元素都放大两倍后，再求积。这个需求的正确答案应该是48;`

```
private static void learnMap() {
     List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
     //使用并行流来处理
     Integer product = lists.parallelStream().reduce(1, (a, b) -> a *  (b * 2),
                                                        (a, b) -> a * b);
     System.out.println("product:" + product);//48

     //使用映射来处理 
     //Integer productMap = lists.parallelStream().map((a) -> a * 2).reduce(1, (a, b) -> a * b);
     Stream<Integer> productNewMapStream = lists.parallelStream().map((a) -> a * 2);
     Integer productMap = productNewMapStream.reduce(1, (a, b) -> a * b);
     System.out.println("productMap:" + productMap);//48
}
```

与使用并行流不同，在使用映射处理的时候，元素扩大2倍发生时机不一样了，使用并行流元素扩大是在缩减的过程当中的，而使用映射处理时，元素扩大是发生在映射过程中的。因此映射过程完程之后，不需要reduce()提供合并器了。

上面的这个例子还是简单了一点，下面再举一个例子,王者荣耀团队经济计算：

```
#玩家使用的英雄以及当前获得的金币数
public class HeroPlayerGold {
    /** 使用的英雄名字 */
    private String hero;
    /** 玩家的ID */
    private String player;
    /** 获得的金币数 */
    private int gold;

    public HeroPlayerGold(String hero, String player, int gold) {
        this.hero = hero;
        this.player = player;
        this.gold = gold;
    }
  //省略get/set/toString
}

#玩家获得的金币数
public class Gold {
    /** 获得的金币数 */
    private int gold;

    public Gold(int gold) {
        this.gold = gold;
    }
 //省略get/set/toString
｝

#测试类
public class Main {
    public static void main(String[] args) {
        learnMap2th();
    }

    private static void learnMap2th() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));

        //计算团队经济
        int teamMoney = lists.stream()
                .map(player -> new Gold(player.getGold()))//note1
                .mapToInt(Gold::getGold)
                .reduce(0, (a, b) -> a + b);
        System.out.println("团队经济：" + teamMoney);//1700


        //计算团队经济2
        double teamMoney2 = lists.stream()
                .mapToDouble(HeroPlayerGold::getGold)
                .reduce(0, (a, b) -> a + b);
        System.out.println("团队经济：" + teamMoney2);//1700.0
    }
}

```

代码应该不难理解，通过代码，大家应该知道我们假设的场景了。我们的RNG去参加王者荣耀比赛了，像这种团队游戏，观众在经济方面关注更多的可能是团队经济，而不是个人经济。

在我们`HeroPlayerGold`类里面存有明星玩家，使用的英雄，和这局比赛某个玩家当前获得的金币数，我们另有一个专们管理金币的`Gold`类，我们第一种计算团队经济的方式，使把`HeroPlayerGold`里面的`gold`字段转换到`Gold`里面了<sup> `//note1` </sup>,这里产生的新流只包含了原始流中选定的`gold`字段，因为我们的原始流中包含了`hero`、`player`、`gold`,三个字段，我们只选取了`gold`字段（因为我们只关心这个字段），所以其它的两个字段被丢弃了。然后从新流取出`Gold`里面的`gold`字段并把他转成一个`IntStream`，然后我们就要以通过缩减操作完成我们的团队经济计算了。

第一种方式，大家需要好好理解，理解了，我相信你们的项目中，很多很多地方可以用得上了，再也不需要动不动就查数据库了，怎样效率高怎样来，只是一种建议。第二种只是快速计算团队经济而已，没什么值得讲的。

接下来讲一下他的扩展方向：大家还记得我在第二篇中介绍中间操作概念的时候吗？`中间操作会产生另一个流。因此中间操作可以用来创建执行一系列动作的管道。`我们可以把多个中间操作放到管道中，所以我们很容易就创建出很强大的组合操作了，发挥你的想象，打出你们的组合拳;

我现在举一个例子：比如现在相统计团队里面两个C位的经济占了多少，代码看起来可能就是这样了：
```
 private static void learnMap2th() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));
        
        //计算两个C位的经济和
        lists.stream()
                .filter(player-> "RNG-Xiaohu".equals(player.getPlayer()) || "RNG-UZI".equals(player.getPlayer()))
                .map(player->new Gold(player.getGold()))
                .mapToInt(Gold::getGold)
                .reduce((a,b)->a+b)
                .ifPresent(System.out::println);//800
    }
```

大家有没有感觉，这种操作怎么带有点数据库的风格啊？其实在创建数据库查询的时候，这种过滤操作十分常见，如果你经常在你的项目中使用流API，这几个条件算什么?等你们把流API用熟了之后，你们完全可以通过这种链式操作创建出非常复杂的查询，合并和选择的操作。

通过前面的例子，我们已经把`map()`，`mapToInt()`，`mapToLong()`，`mapToDouble`都讲了。那么剩下的就是flatMap()方法了。本来想让大家自行去理解这个方法的，因为怕这篇文章写得太长了。但是后面想想，还是我来给大家分析一下吧。

**<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);**

通过前面的学习我们知道`mapper`是一个映射函数，它和map()方法也一样也会返回一个新流，我们把返回的新流称为映射流。**我们提供的映射函数会处理原始流中的每一个元素，而映射流中包含了所有经过我们映射函数处理后产生的新元素。** 加粗部份需要重点理解。

我们来看一下源码对flatMap()的注释:

> The  flatMap() operation has the effect of applying a one-to-many  transformation to the elements of the stream, and then flattening the resulting elements into a new stream.

大意就是：**flatMap()操作能把原始流中的元素进行一对多的转换，并且将新生成的元素全都合并到它返回的流里面。**根据我们所学的知识，他的这种一对多的转换功能肯定就是映射函数提供的，这一点没有疑问吧！然后源码的注释上面还提供了一个例子，通过注释加例子，我相信大家都能非常清楚地理解flatMap()了。

```
    /* <p>If {@code orders} is a stream of purchase orders, and each purchase
     * order contains a collection of line items, then the following produces a
     * stream containing all the line items in all the orders:
     * <pre>{@code
     *     orders.flatMap(order -> order.getLineItems().stream())...
     * }</pre>
     */
```

**如果orders是一批采购订单对应的流，并且每一个采购订单都包含一系列的采购项，那么`orders.flatMap(order -> order.getLineItems().stream())...`生成的新流将包含这一批采购订单中所有采购项。** 

我们用伪代码来就更加清晰了`Stream<Orders<OrderItem>>`====>Stream<OrderItem>。大家能理解了吗？还没理解？再来一个例子：

```
 private static void learnFlatMap() {
        //(广州  深圳  上海  北京)的全拼的一些组合,下面我们就把每一个城市都划分一下
        List<String> citys = Arrays.asList("GuangZhou ShangHai", "GuangZhou ShenZhen",
                "ShangHai ShenZhen", "BeiJing ShangHai", "GuangZhou BeiJing", "ShenZhen BeiJing");

        //这里打印的数组对应的地址
        citys.stream().map(mCitys -> Arrays.stream(mCitys.split(" "))).forEach(System.out::println);//note1

        System.out.println();

        //流里面的元素还是一个数组
        citys.stream()
                .map(mCities -> Arrays.stream(mCities.split(" ")))//流里面的每个元素还是数组
                .forEach(cities ->cities.forEach(city-> System.out.print(city+" ")));//note2

        System.out.println();
        System.out.println();

        //直接一个flatMap()就把数组合并到映射流里面了
        citys.stream().flatMap(mCities->Arrays.stream(mCities.split(" "))).forEach(System.out::println);//note3

        System.out.println();

        //使用distinct()方法去重！
        citys.stream().flatMap(mCities->Arrays.stream(mCities.split(" "))).distinct().forEach(System.out::println);//note4

    }
```

其中<sup> `//note1` </sup>处是无法打印元素的，使用map()打印元素的方式在<sup> `//note2` </sup>，原因也在注释中交待了，但是使用了flatMap()方法后，直接就可以打印了<sup> `//note3` </sup>，到这里，应该就能理解**如果orders是一批采购订单对应的流，并且每一个采购订单都包含一系列的采购项，那么`orders.flatMap(order -> order.getLineItems().stream())...`生成的新流将包含这一批采购订单中所有采购项。** 了吧。最后<sup> `//note4` </sup>是一个去重的方法，大家运行一遍吧。

#### 小结一下

通过这一篇文章，相信大家对流API中的映射已经不再陌生了，其实最需要注意的一个点是,map()和flatMap()的区别，我也一步步地带着大家理解和应用了。其实在流API这一块中，大家单单掌握概念是没什么用的，一定要去实战了，一个项目里面，集合框架这种东西用得还是特别多的，用到集合框架的大部份情况，其实都可以考虑一下用Stream流去操作一下，不仅增加效率，还可以增加业务流程的清晰度。
