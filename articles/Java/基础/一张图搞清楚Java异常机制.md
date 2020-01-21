
下面是Java异常类的组织结构，红色区域的异常类表示是程序需要显示捕捉或者抛出的。

![image](https://www.programcreek.com/wp-content/uploads/2009/02/Exception-Hierarchy-Diagram.jpeg)

### Throwable
Throwable是Java异常的顶级类，所有的异常都继承于这个类。

Error，Exception是异常类的两个大分类。

### Error

Error是非程序异常，即程序不能捕获的异常，一般是编译或者系统性的错误，如OutOfMemorry内存溢出异常等。

### Exception
Exception是程序异常类，由程序内部产生。Exception又分为运行时异常、非运行时异常。

#### 运行时异常

运行时异常的特点是Java编译器不会检查它，也就是说，当程序中可能出现这类异常，即使没有用try-catch语句捕获它，也没有用throws子句声明抛出它，也会编译通过，运行时异常可处理或者不处理。运行时异常一般常出来定义系统的自定义异常，业务根据自定义异常做出不同的处理。

常见的运行时异常如NullPointException、ArrayIndexOutOfBoundsException等。

#### 非运行时异常

非运行时异常是程序必须进行处理的异常，捕获或者抛出，如果不处理程序就不能编译通过。如常见的IOException、ClassNotFoundException等。

