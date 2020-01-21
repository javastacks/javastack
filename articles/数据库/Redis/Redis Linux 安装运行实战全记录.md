
#### 下载Redis

去Redis官网下载最新的Linux包，Redis官方没有Windows版的下载。

> https://redis.io/

下载后把包上传到Linux服务器。

#### 安装Redis

1、解压Redis包


```
> tar -zxvf redis-4.0.2.tar.gz
```


2、切换到Redis解压目录


```
> cd redis-4.0.2
```

3、编译Redis

```
> make
```

如报错按以下错误解决。


> make: cc：命令未找到
>
> make: *** [adlist.o] 错误 127


```
> yum install gcc
```

> collect2: ld returned 1 exit status
> 
> make[1]: *** [redis-server] Error 1
> 
> make[1]: Leaving directory `/usr/local/redis/src'
> 
> make: *** [all] Error 2


```
> vi src/.make-settings，修改OPT=-O2 -march=x86-64
```

4、编译测试

```
> make test
```


报错解决。

> make[1]: Entering directory `/home/test/redis-4.0.2/src'
>     CC Makefile.dep
> make[1]: Leaving directory `/home/test/redis-4.0.2/src'
> make[1]: Entering directory `/home/test/redis-4.0.2/src'
> You need tcl 8.5 or newer in order to run the Redis test
> make[1]: *** [test] Error 1
> make[1]: Leaving directory `/home/test/redis-4.0.2/src'
> 


```
> wget http://downloads.sourceforge.net/tcl/tcl8.6.7-src.tar.gz

> tar -zxvf tcl8.6.7-src.tar.gz

> cd tcl8.6.7/unix/

> ./configure

> make

> make install
```

5、安装

切换到redis目录执行安装。


```
> make install
```

#### 启动Redis

启动redis src目录下的redis-server命令来启动Redis服务。


```
> ./redis-server ../redis.conf
```

启动成功画面：

```
6651:C 17 Nov 09:24:43.145 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
6651:C 17 Nov 09:24:43.145 # Redis version=4.0.2, bits=64, commit=00000000, modified=0, pid=6651, just started
6651:C 17 Nov 09:24:43.145 # Configuration loaded
6651:M 17 Nov 09:24:43.147 # You requested maxclients of 10000 requiring at least 10032 max file descriptors.
6651:M 17 Nov 09:24:43.147 # Server can't set maximum open files to 10032 because of OS error: Operation not permitted.
6651:M 17 Nov 09:24:43.147 # Current maximum open files is 4096. maxclients has been reduced to 4064 to compensate for low ulimit. If you need higher maxclients increase 'ulimit -n'.
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 4.0.2 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                   
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 6651
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           http://redis.io        
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'                                               

6651:M 17 Nov 09:24:43.157 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
6651:M 17 Nov 09:24:43.158 # Server initialized
6651:M 17 Nov 09:24:43.158 # WARNING overcommit_memory is set to 0! Background save may fail under low memory condition. To fix this issue add 'vm.overcommit_memory = 1' to /etc/sysctl.conf and then reboot or run the command 'sysctl vm.overcommit_memory=1' for this to take effect.
6651:M 17 Nov 09:24:43.158 # WARNING you have Transparent Huge Pages (THP) support enabled in your kernel. This will create latency and memory usage issues with Redis. To fix this issue run the command 'echo never > /sys/kernel/mm/transparent_hugepage/enabled' as root, and add it to your /etc/rc.local in order to retain the setting after a reboot. Redis must be restarted after THP is disabled.
6651:M 17 Nov 09:24:43.158 * Ready to accept connections
```

## 连接Redis

启动redis src目录下的redis-cli命令来连接到Redis服务。


```
> ./redis-cli
```

连接成功：


```
127.0.0.1:6379>
```

另外推荐使用客户端连接工具：redis desktop manager。

> https://redisdesktop.com/download

