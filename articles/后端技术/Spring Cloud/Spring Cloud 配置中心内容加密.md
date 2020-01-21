从配置获取的配置默认是明文的，有些像数据源这样的配置需要加密的话，需要对配置中心进行加密处理。

下面使用对称性加密来加密配置，需要配置一个密钥，当然也可以使用RSA非对称性加密，但对称加密比较方便也够用了，这里就以对称加密来配置即可。

## 1、安装JCE

JDK下的JCR默认是有长度限制的，需要替换没有长度限制的JCE版本。

> http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

把下载包里面的两个jar文件复制替换到JAVA_HOME/jre/lib/security目录下。

## 2、添加加密KEY

配置中心配置文件中加入加密密钥。


```
encrypt: 
  key: 0e010e17-2529-4581-b907-c8edcfd6be09
```


## 3、查看加密功能状态


```
http://192.168.1.237:7100/encrypt/status
```

功能正常会显示OK

```
{"status":"OK"}
```



## 4、加密解密

对`develop`字符串加密


```
curl http://192.168.1.237:7100/encrypt -d  develop -u config-user:99282424-5939-4b08-a40f-87b2cbc403f6
```


对`develop`字符串解密


```
curl http://192.168.1.237:7100/decrypt -d  0fb593294187a31f35dea15e8bafaf77745328dcc20d6d6dd0dfa5ae753d6836 -u config-user:99282424-5939-4b08-a40f-87b2cbc403f6
```


-u username:password 为basic认证

## 5、配置文件


```
spring: 
  datasource: 
    username: '{cipher}0fb593294187a31f35dea15e8bafaf77745328dcc20d6d6dd0dfa5ae753d6836'
```


需要加密的内容以`{cipher}`开头，并注意要使节单引号包起来，不然报错。

## 6、读取配置

这样客户端读取出来的配置是自动解密了的，如果要关闭自动解密功能通过客户端自己来解密，同时也要保留加解密的端点可以通过关闭以下配置即可。


```
spring.cloud.config.server.encrypt.enabled=false
```
