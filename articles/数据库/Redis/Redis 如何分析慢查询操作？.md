
#### 什么是慢查询

和mysql的慢SQL日志分析一样，redis也有类似的功能，来帮助定位一些慢查询操作。

Redis **slowlog**是Redis用来记录查询执行时间的日志系统。

查询执行时间指的是不包括像客户端响应(talking)、发送回复等IO操作，而单单是执行一个查询命令所耗费的时间。

另外，slow log保存在内存里面，读写速度非常快，因此你可以放心地使用它，不必担心因为开启slow log而损害Redis的速度。

#### 慢查询参数

首先来关注下慢日志分析对应的两个参数：

1、**slowlog-log-slower-than**：预设阀值，即记录超过多少时间的记录，默认为10000微秒，即10毫秒。

2、**slowlog-max-len**：记录慢查询的条数，默认为128条，当超过设置的条数时最早进入队列的将被移除。线上建议增大数值，如：1000，这样可减少队列移除的频率。

```
127.0.0.1:6379> config get slowlog-log-slower-than
1) "slowlog-log-slower-than"
2) "10000"
127.0.0.1:6379> config get slowlog-max-len
1) "slowlog-max-len"
2) "128"
```

可以用config set对这两个参数进行调整，或者在配置文件中设置。


```
################################## SLOW LOG ###################################

# The Redis Slow Log is a system to log queries that exceeded a specified
# execution time. The execution time does not include the I/O operations
# like talking with the client, sending the reply and so forth,
# but just the time needed to actually execute the command (this is the only
# stage of command execution where the thread is blocked and can not serve
# other requests in the meantime).
#
# You can configure the slow log with two parameters: one tells Redis
# what is the execution time, in microseconds, to exceed in order for the
# command to get logged, and the other parameter is the length of the
# slow log. When a new command is logged the oldest one is removed from the
# queue of logged commands.

# The following time is expressed in microseconds, so 1000000 is equivalent
# to one second. Note that a negative number disables the slow log, while
# a value of zero forces the logging of every command.
slowlog-log-slower-than 10000

# There is no limit to this length. Just be aware that it will consume memory.
# You can reclaim memory used by the slow log with SLOWLOG RESET.
slowlog-max-len 128
```


#### 慢查询命令

> 语法：slowlog subcommand [argument]

如，进行查询慢查询、获取慢查询记录的数量、重置慢查询日志等操作：

```
192.168.10.38:9001> slowlog get
(empty list or set)
192.168.10.38:9001> slowlog get 10
(empty list or set)
192.168.10.38:9001> slowlog len 
(integer) 0
192.168.10.38:9001> slowlog reset
OK
```
