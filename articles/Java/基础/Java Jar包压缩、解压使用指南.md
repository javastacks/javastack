
![](http://img.javastack.cn/18-2-27/79692618.jpg)

#### 什么是jar包

JAR（Java Archive）是Java的归档文件，它是一种与平台无关的文件格式，它允许将许多文件组合成一个压缩文件。

#### 如何打/解包

使用jdk/bin/jar.exe工具，配置完环境变量后直接使得jar命令即可。

#### jar命令格式

jar {c t x u f }[ v m e 0 M i ][-C 目录]文件名...
 
{ctxu}，这四个参数必须选选其一。

[v f m e 0 M i]，这几个是可选参数，文件名也是必须的。

参数 | 说明
---|---
-c | 创建一个jar包
-t | 显示jar中的内容列表
-x | 解压jar包
-u | 添加文件到jar包中
-f | 指定jar包的文件名
-v | 输出详细报告
-m | 指定MANIFEST.MF文件
-0 | 生成jar包时不压缩内容
-M | 不生成清单文件MANIFEST.MF
-i | 为指定的jar文件创建索引文件
-C | 可在相应的目录下执行命令

关于MANIFEST.MF定义：
> https://baike.baidu.com/item/MANIFEST.MF

#### 演示

**往jar包添加文件**

> jar uf xxx.jar BOOT-INF/classes/application.yml

**解压jar包**

> jar -xvf xxx.jar

**打jar包，不生成清单文件，不压缩**

> jar -cvfM0 xxx.jar BOOT-INF/ META-INF/ org/

或者

> jar -cvfM0 xxx.jar *

如果要往线上jar包添加、更新部分文件到jar包，这些命令也许对你有用。
