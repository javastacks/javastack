今天从 0 开始搭建 Redis Cluster 官方集群，解决搭建过程中遇到的问题，超详细。

#### 安装ruby环境

因为官方提供的创建集群的工具是用ruby写的，需要ruby2.2.2+版本支持，ruby安装需要指定openssl。

**安装openssl**

```
$ wget https://www.openssl.org/source/openssl-1.0.2m.tar.gz
$ tar -zxvf openssl-1.0.2m.tar.gz
$ cd openssl-1.0.2m
$ ./config --prefix=/usr/local/openssl
$ ./config -t
$ make
$ make install
$ openssl version
```

**安装ruby**

```
$ yum remove ruby
$ wget https://cache.ruby-lang.org/pub/ruby/2.4/ruby-2.4.2.tar.gz
$ tar -zxvf ruby-2.4.2.tar.gz
$ cd ruby-2.4.2
$ ./configure --with-openssl-dir=/usr/local/openssl
$ make
$ make install
$ sudo ln -s /usr/local/bin/ruby /usr/bin/ruby
```

**安装rubygems**

```
$ wget https://rubygems.org/rubygems/rubygems-2.3.0.tgz
$ tar -zxvf rubygems-2.3.0.tgz
$ cd rubygems-2.3.0
$ ruby setup.rb
```

**安装zlib**


```
$ vi /ruby-2.4.2/ext/zlib/Makefile
```

```
zlib.o: $(top_srcdir)/include/ruby.h
修改为：
zlib.o: ../../include/ruby.h
```

```
$ yum install zlib*
$ cd /ruby-2.4.2/ext/zlib
$ ruby extconf.rb
$ make
$ make install
```

#### 安装redis库


```
$ gem install redis
```


```
ERROR:  While executing gem ... (Gem::Exception)
    Unable to require openssl, install OpenSSL and rebuild ruby (preferred) or use non-HTTPS sources
```

报这个错，安装openssl-devel，并重新编译ruby即可。

> 参考：https://www.larshaendler.com/2015/05/20/unable-to-require-openssl-install-openssl-and-rebuild-ruby-on-ubuntu-14-lts/

#### 搭建集群

1、创建集群目录

首先进入一个新目录，创建六个以端口号为名字的子目录。


```
$ mkdir redis-cluster
$ cd redis-cluster
$ mkdir 9001 9002 9003 9004 9005 9006
```


2、添加集群配置文件

在文件夹9001~9006中各建一个redis.conf文件，修改对应文件夹的端口，内容如下：

```
port 9001
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```

- cluster-enabled：用于开实例的集群模式

- cluster-conf-file：设定了保存节点配置文件的路径，默认值为nodes.conf，节点配置文件无须人为修改，它由 Redis集群在启动时创建， 并在有需要时自动进行更新。

要让集群正常运作至少需要三个主节点，不过在刚开始试用集群功能时， 强烈建议使用六个节点： 其中三个为主节点， 而其余三个则是各个主节点的从节点。

3、添加redis服务文件

把编译好的redis-server文件复制到redis-cluster文件夹中。

4、启动集群实例

进入到9001~90066每个目录下，启动每个实例：


```
$ cd 9001
$ ../redis-server ./redis.conf
```


5、创建集群

现在我们已经有了六个正在运行中的Redis实例，接下来我们需要使用这些实例来创建集群，并为每个节点编写配置文件。通过使用Redis集群命令行工具redis-trib，编写节点配置文件的工作可以非常容易地完成：redis-trib位于Redis源码的src文件夹中，它是一个Ruby程序，这个程序通过向实例发送特殊命令来完成创建新集群，检查集群，或者对集群进行重新分片（reshared）等工作。


```
$ ./redis-trib.rb create --replicas 1 127.0.0.1:9001 127.0.0.1:9002 127.0.0.1:9003 127.0.0.1:9004 127.0.0.1:9005 127.0.0.1:9006
```


```
>>> Creating cluster
>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
127.0.0.1:9001
127.0.0.1:9002
127.0.0.1:9003
Adding replica 127.0.0.1:9004 to 127.0.0.1:9001
Adding replica 127.0.0.1:9005 to 127.0.0.1:9002
Adding replica 127.0.0.1:9006 to 127.0.0.1:9003
M: bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0 127.0.0.1:9001
   slots:0-5460 (5461 slots) master
M: 688b8cdbdc38fe6b9e81b410aae2f1c048f5907c 127.0.0.1:9002
   slots:5461-10922 (5462 slots) master
M: 33b757db6091e486af2032f1463d1fb07e8e89a7 127.0.0.1:9003
   slots:10923-16383 (5461 slots) master
S: b00b464e4deb93a661755923641d36cadf648fcd 127.0.0.1:9004
   replicates bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0
S: b3ec3a9c125cf168807231a16bacab946974d563 127.0.0.1:9005
   replicates 688b8cdbdc38fe6b9e81b410aae2f1c048f5907c
S: 06a207f7a4dd3023f88e01fad8635cb471d004eb 127.0.0.1:9006
   replicates 33b757db6091e486af2032f1463d1fb07e8e89a7
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join....
>>> Performing Cluster Check (using node 127.0.0.1:9001)
M: bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0 127.0.0.1:9001
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
S: 06a207f7a4dd3023f88e01fad8635cb471d004eb 127.0.0.1:9006
   slots: (0 slots) slave
   replicates 33b757db6091e486af2032f1463d1fb07e8e89a7
S: b00b464e4deb93a661755923641d36cadf648fcd 127.0.0.1:9004
   slots: (0 slots) slave
   replicates bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0
M: 688b8cdbdc38fe6b9e81b410aae2f1c048f5907c 127.0.0.1:9002
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
S: b3ec3a9c125cf168807231a16bacab946974d563 127.0.0.1:9005
   slots: (0 slots) slave
   replicates 688b8cdbdc38fe6b9e81b410aae2f1c048f5907c
M: 33b757db6091e486af2032f1463d1fb07e8e89a7 127.0.0.1:9003
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

这表示集群中的 16384 个槽都有至少一个主节点在处理， 集群运作正常。

6、查看集群节点

```
192.168.10.38:9001> cluster nodes
bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0 127.0.0.1:9001@19001 myself,master - 0 1511774435000 1 connected 0-5460
06a207f7a4dd3023f88e01fad8635cb471d004eb 127.0.0.1:9006@19006 slave 33b757db6091e486af2032f1463d1fb07e8e89a7 0 1511774436000 6 connected
b00b464e4deb93a661755923641d36cadf648fcd 127.0.0.1:9004@19004 slave bd330d41ffcc57a5a5d32e3f738ddf82c48cfed0 0 1511774436557 4 connected
688b8cdbdc38fe6b9e81b410aae2f1c048f5907c 127.0.0.1:9002@19002 master - 0 1511774436557 2 connected 5461-10922
b3ec3a9c125cf168807231a16bacab946974d563 127.0.0.1:9005@19005 slave 688b8cdbdc38fe6b9e81b410aae2f1c048f5907c 0 1511774436657 5 connected
33b757db6091e486af2032f1463d1fb07e8e89a7 127.0.0.1:9003@19003 master - 0 1511774436000 3 connected 10923-16383
```

#### 连接使用集群


```
$ ./redis-cli -c -h 192.168.1.8 -p 9002 -a 123456
```


-c：cluster，连接到集群模式，否则key不落在本实例将会报错。

-h：host，指定连接主机。

-p：port，指定连接端口。

-a：auth，指定密码，集群模式需要指定，不然移动会认证失败。

```
$ ./redis-cli -c -h 192.168.1.8 -p 9002 -a 123456
127.0.0.1:9002> set hnad 21233
-> Redirected to slot [2114] located at 127.0.0.1:9001
OK
```

如上，键hnad被转移到实例9001。


#### 主从复制不能同步问题解决

搭建一个3主3从的Redis Cluster集群发现从实例不能同步主实例的数据，但确认搭建的步骤和参数都没啥问题啊，官网也没有给出对应的问题解决方案。

#### 解决方案

后来查各种资料发现，是因为主实例设置了密码，从实例配置中需要配置主实例的连接密码才能实现主从复制同步。

就是下面这个配置：

```
# If the master is password protected (using the "requirepass" configuration
# directive below) it is possible to tell the slave to authenticate before
# starting the replication synchronization process, otherwise the master will
# refuse the slave request.
#
# masterauth <master-password>
```

翻译：


```
如果master是密码保护的，下面的配置就是可以告诉从实例在启动集群同步复制进程之前要经过认证，否则主实例会拒绝从实例的请求。
```

#### 解决流程

所以，要解决不同步问题，先停止6个从实例，然后在每个实例的redis.conf文件中加入对应主备实例的认证密码，然后再启动各个从实例。如：

```
masterauth 123456
```

然后主实例上的数据实时变化都会同步到从实例，问题解决。

