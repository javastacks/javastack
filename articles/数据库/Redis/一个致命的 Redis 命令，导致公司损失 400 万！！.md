
最近安全事故濒发啊，前几天发生了《[顺丰高级运维工程师的删库事件](https://mp.weixin.qq.com/s/51ti1Tn4OcD5ZKYYFG91dg)》，今天又看到了 PHP 工程师在线执行了 Redis 危险命令导致某公司损失 400 万。。

什么样的 Redis 命令会有如此威力，造成如此大的损失？

具体消息如下：

> 据云头条报道，某公司技术部发生 2 起本年度 PO 级特大事故，造成公司资金损失 400 万，原因如下：
> 
> 由于 PHP 工程师直接操作上线 redis，执行 keys * wxdb（此处省略）cf8* 这样的命令，导致redis锁住，导致 CPU 飙升，引起所有支付链路卡住，等十几秒结束后，所有的请求流量全部挤压到了 rds 数据库中，使数据库产生了雪崩效应，发生了数据库宕机事件。
> 
> 该公司表示，如再犯类似事故，将直接开除，并表示之后会逐步收回运维部各项权限。

看完这个消息后，我心又一惊，为什么这么低级的问题还在犯？为什么线上的危险命令没有被禁用？这事件报道出来真是觉得很低级。。。

且不说是哪家公司，发生这样的事故，不管是大公司还是小公司，我觉得都不应该，相关负责人应该引咎辞职！！！

对 Redis 稍微有点使用经验的人都知道线上是不能执行 `keys *` 相关命令的，虽然其模糊匹配功能使用非常方便也很强大，在小数据量情况下使用没什么问题，数据量大会导致 Redis 锁住及 CPU 飙升，在生产环境建议禁用或者重命名！


#### 还有哪些危险命令？

Redis 的危险命令主要有以下几个：

- **keys**

客户端可查询出所有存在的键。

- **flushdb**

> Delete all the keys of the currently selected DB. This command never fails.

删除 Redis 中当前所在数据库中的所有记录，并且此命令从不会执行失败。

- **flushall**

> Delete all the keys of all the existing databases, not just the currently selected one. This command never fails.

删除 Redis 中所有数据库中的所有记录，不只是当前所在数据库，并且此命令从不会执行失败。

- **config**

客户端可修改 Redis 配置。

#### 怎么禁用或重命名危险命令？

看下 `redis.conf` 默认配置文件，找到 `SECURITY` 区域，如以下所示。

```
################################## SECURITY ###################################

# Require clients to issue AUTH <PASSWORD> before processing any other
# commands.  This might be useful in environments in which you do not trust
# others with access to the host running redis-server.
#
# This should stay commented out for backward compatibility and because most
# people do not need auth (e.g. they run their own servers).
#
# Warning: since Redis is pretty fast an outside user can try up to
# 150k passwords per second against a good box. This means that you should
# use a very strong password otherwise it will be very easy to break.
#
# requirepass foobared

# Command renaming.
#
# It is possible to change the name of dangerous commands in a shared
# environment. For instance the CONFIG command may be renamed into something
# hard to guess so that it will still be available for internal-use tools
# but not available for general clients.
#
# Example:
#
# rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52
#
# It is also possible to completely kill a command by renaming it into
# an empty string:
#
# rename-command CONFIG ""
#
# Please note that changing the name of commands that are logged into the
# AOF file or transmitted to slaves may cause problems.
```

看说明，添加 `rename-command` 配置即可达到安全目的。

**1）禁用命令**

```
rename-command KEYS     ""
rename-command FLUSHALL ""
rename-command FLUSHDB  ""
rename-command CONFIG   ""
```


**2）重命名命令**

```
rename-command KEYS     "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
rename-command FLUSHALL "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
rename-command FLUSHDB  "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
rename-command CONFIG   "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```

上面的 XX 可以定义新命令名称，或者用随机字符代替。

经过以上的设置之后，危险命令就不会被客户端执行了。关注微信公众号：Java技术栈，回复:redis, 可以获取所有 redis 系列文章。
