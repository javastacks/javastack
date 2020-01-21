Redis 你再牛逼也得设置密码啊，不然会有安全漏洞，造成一些隐患。

还有，比如像出现下面这样的错，需要设置密码，或者关闭保护模式，所以还是设置密码比较安全。不然只能本地操作，不能远程连接。

```
DENIED Redis is running in protected mode because protected mode is enabled…
```

下面介绍几种设置密码的方式，及如何使用。

###### 命令设置密码

这种方式是临时的，重启后需要重新设置。

> config set requirepass 123456

###### 配置设置密码

redis.conf中添加配置：

```
requirepass 123456
```

###### 授权密码：

有两种方式：

> auth 123456

> $ ./redis-cli -p 9001 -a 123456