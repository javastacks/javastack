
1、如何判断一个变量是数组还是字符串？

使用typeof。

2、“\==”与“===”的区别？

"=="是相等运算符，会隐式转换。
"==="是严格运算符，会判断类型。

3、如何去除字符串中的所有空格？

function trim(str){
    return str.replace(/\s|\xA0/g,"");    
}

4、如何获取当前浏览的URL？

window.location.href

5、怎样添加、删除、移动、复制、创建和查找节点？

1）创建新节点

createDocumentFragment() //创建一个DOM片段

createElement() //创建一个具体的元素

createTextNode() //创建一个文本节点

2）添加、移除、替换、插入

appendChild() //添加

removeChild() //移除

replaceChild() //替换

insertBefore() //插入

3）查找

getElementsByTagName() //通过标签名称

getElementsByName() //通过元素的Name属性的值

getElementById() //通过元素Id，唯一性

实现一个函数clone，可以对JavaScript中的5种主要的数据类型(包括Number、String、Object、Array、Boolean)进行值复制。

6、什么是闭包？

闭包就是能够读取其他函数内部变量的函数。

7、JS中const、let、var之间的区别

1）const定义的变量不可以修改，而且必须初始化。

2）var定义的变量可以修改，如果不初始化会输出undefined，不会报错。

3）let是块级作用域，函数内部使用let定义后，对函数外部无影响。

8、什么叫同源策略？有什么用？

同源策略是指域名，协议，端口相同，只有同源的脚本才会被执行。

9、什么是Ajax? 适用于哪些应用场景？

Ajax是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术。
如：无刷新判断验证码是否输入正确等。

10、Ajax请求时get和post有什么区别？

1）get参数放在url后面，post放在http body里面
2）有大小限制,get受url长度限制，post受内存限制
3）安全问题，get明文传输不侬好
4）应用不同，get用于获取数据，post提交数据

11、谈谈Jsonp的原理？

动态创建script标签，回调函数

12、split()和join()函数的区别？

前者是切割成数组的形式，后者是将数组转换成字符串

13、写出3个使用this的典型应用。

1）事件：如onclick  this->发生事件的对象
2）构造函数：this->new 出来的object
3）call/apply：改变this

14、怎么使用JS改变元素的class？

object.ClassName = xxxx

15、普通事件与绑定事件有什么区别？

普通事件只支持单个事件，而事件绑定可以添加多个事件

16、请描述js里事件的三个阶段。

捕获、处于目标阶段、冒泡阶段(IE8以下只支持冒泡)

17、数组方法pop() push() unshift() shift()都有什么用？

push()：尾部添加 
pop()：尾部删除
unshift()：头部添加 
shift()：头部删除

18、IE和DOM事件流的区别是什么？

1）执行顺序不一样
2）参数不一样
3）事件加不加on
4）this指向问题

19、call和apply有什么区别？

call与apply的区别就是参数写法不同:

a.call(b,arg1,arg2,...)
a.apply(b,[arg1,arg2,...])

20、什么是事件委托？

让利用事件冒泡的原理，让自己的所触发的事件，让他的父元素代替执行！

21、JS中的本地对象，内置对象和宿主对象分别有哪些？

本地对象：array obj regExp等可以new实例化的
内置对象：Math等不可以实例化的
宿主对象：document window等浏览器自带的

22、document load 和 document ready 的区别是什么？

load必须等到页面内包括图片的所有元素加载完毕后才能执行。 ready是DOM结构绘制完毕后就执行，不必等到加载完毕。 

23、你怎么理解WebPack和Grunt和Gulp？

Webpack可以看做是模块打包器，把你的代码转换成合适的格式供浏览器使用；

常用webpack构建本地服务器，可以让浏览器监听你代码的修改，自动刷新现实后的结果；

Gulp/Grunt是一种能够优化前端开发的流程工具，而WebPack是一种模块化的解决方案；

WebPack有4个配置选项，打包速度越快，负面作用就越大，会不利于调试，文件的执行效率也有一定的影响；开发阶段使用：eval-source-map：使用eval打包源文件模块，在同一个文件中生成干净的完整的source map。这个选项可以在不影响构建速度的前提下生成完整的sourcemap，但是对打包后输出的JS文件的执行具有性能和安全的隐患。在开发阶段这是一个非常好的选项，在生产阶段则一定不要启用这个选项；

babel是一个编译js的平台，把es6/es7转换成浏览器支持的es5提供浏览器使用；

24、JS有哪几种数据类型？

String,Number,Boolean,Null,Undefined；Object,Array,Function（引用类型）

25、什么是ES？ES和JS有什么联系？

要讲清楚这个问题，需要回顾历史。1996年11月，JavaScript的创造者Netscape公司，决定将JavaScript提交给国际标准化组织ECMA，希望这种语言能够成为国际标准。次年，ECMA发布262号标准文件（ECMA-262）的第一版，规定了浏览器脚本语言的标准，并将这种语言称为ECMAScript，这个版本就是1.0版。

该标准从一开始就是针对JavaScript语言制定的，但是之所以不叫JavaScript，有两个原因。一是商标，Java是Sun公司的商标，根据授权协议，只有Netscape公司可以合法地使用JavaScript这个名字，且JavaScript本身也已经被Netscape公司注册为商标。二是想体现这门语言的制定者是ECMA，不是Netscape，这样有利于保证这门语言的开放性和中立性。

因此，ECMAScript和JavaScript的关系是，前者是后者的规格，后者是前者的一种实现（另外的ECMAScript方言还有Jscript和ActionScript）。在日常场合，这两个词是可以互换的。

26、ES6有哪些新特性？

箭头操作符；对class的支持（constructor构造函数）；不定参数...x；let和const关键字；for of遍历；模块的支持import；promise异步函数的处理模式(pending等待中；resolve返回成功，reject返回失败)；

27、typeof都会返回什么数据类型？

Object number function boolean underfind

28、请例举3种强制类型转换和2种隐式类型转换。

29、截取字符串javastack的java?

30、如何规避javascript多人开发函数重名问题？

31、检测一个变量是否是String类型有哪几种方式？

 1）typeof(obj) == 'string'
 2）obj.constructor == String;

32、请说出至少三种减低页面加载时间的方法。

1）压缩css、js文件
2）合并js、css文件，减少http请求
3）外部js、css文件放在最底下
4）减少dom操作，尽可能用变量替代不必要的dom操作

33、请详细解释Ajax的工作原理。

1）创建ajax对象（XMLHttpRequest/ActiveXObject(Microsoft.XMLHttp)）
2）判断数据传输方式(GET/POST)
3）打开链接 open()
4）发送 send()
5）当ajax对象完成第四步（onreadystatechange）数据接收完成，判断http响应状态（status）200-300之间或者304（缓存）执行回调函数

34、JS中有哪几种函数？

具名函数（命名函数）和匿名函数。

35、请至少写出3种创建函数的方式。

1）声明函数

function fn1(){}

2）创建匿名函数表达式

var fn1 = function (){}

3）创建具名函数表达式

var fn1 = function javastack(){};

36、什么是跨域？解决跨域的方法有哪些？

由于浏览器同源策略，凡是发送请求url的协议、域名、端口三者之间任意一个与当前页面地址不同即为跨域。

解决方案：JSONP、CORS、代理、服务器实现等。

---

获取更多面试题及答案请关注微信公众号：Java技术栈，回复：面试。

![](http://img.javastack.cn/javastack.png)
