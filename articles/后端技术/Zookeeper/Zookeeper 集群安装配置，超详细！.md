今天，栈长分享下 Zookeeper 的集群安装及配置。

#### 下载

> 下载地址：http://zookeeper.apache.org/

下载过程就不说了，我们下载了最新的`zookeeper-3.4.11`。

#### 安装

**1、上传安装包**

把下载的最新的包（如：zookeeper-3.4.11.tar.gz）上传到服务器，上传的方式也不多说了。

**2、解压**

```
$ tar zxvf zookeeper-3.4.11.tar.gz
```

**3、移动到/usr/local目录下**

```
$ mv zookeeper-3.4.11 /usr/local/zookeeper
```

#### 集群配置

Zookeeper集群原则上需要2n+1个实例才能保证集群有效性，所以集群规模至少是3台。

下面演示如何创建3台的Zookeeper集群，N台也是如此。

**1、创建数据文件存储目录**

```
$ cd /usr/local/zookeeper
$ mkdir data
```

**2、添加主配置文件**

```
$ cd conf
$ cp zoo_sample.cfg zoo.cfg
```

**3、修改配置**

```
$ vi zoo.cfg
```

先把`dataDir=/tmp/zookeeper`注释掉，然后添加以下核心配置。

```
dataDir=/usr/local/zookeeper/data
server.1=192.168.10.31:2888:3888
server.2=192.168.10.32:2888:3888
server.3=192.168.10.33:2888:3888
```

**4、创建myid文件**


```
$ cd ../data
$ touch myid
$ echo "1">>myid
```


每台机器的myid里面的值对应server.后面的数字x。

**5、开放3个端口**

```
$ sudo /sbin/iptables -I INPUT -p tcp --dport 2181 -j ACCEPT
$ sudo /sbin/iptables -I INPUT -p tcp --dport 2888 -j ACCEPT
$ sudo /sbin/iptables -I INPUT -p tcp --dport 3888 -j ACCEPT

$ sudo /etc/rc.d/init.d/iptables save
$ sudo /etc/init.d/iptables restart

$ sudo /sbin/iptables -L -n
Chain INPUT (policy ACCEPT)
target     prot opt source               destination         
ACCEPT     tcp  --  0.0.0.0/0            0.0.0.0/0           tcp dpt:3888 
ACCEPT     tcp  --  0.0.0.0/0            0.0.0.0/0           tcp dpt:2888 
ACCEPT     tcp  --  0.0.0.0/0            0.0.0.0/0           tcp dpt:2181
```

**6、配置集群其他机器**

把配置好的Zookeeper目录复制到其他两台机器上，重复上面4-5步。

```
$ scp -r /usr/local/zookeeper test@192.168.10.32:/usr/local/
```

**7、重启集群**

```
$ /usr/local/zookeeper/bin/zkServer.sh start
```

3个Zookeeper都要启动。

**8、查看集群状态**

```
$ /usr/local/zookeeper/bin/zkServer.sh status 
ZooKeeper JMX enabled by default
Using config: /usr/local/zookeeper/bin/../conf/zoo.cfg
Mode: follower
```

#### 客户端连接

```
./zkCli.sh -server 192.168.10.31:2181
```

连接本机的不用带-server。

#### 注意

如果是在单机创建的多个Zookeeper伪集群，需要对应修改配置中的端口、日志文件、数据文件位置等配置信息。

跟着栈长学 Zookeeper，可以在Java技术栈微信公众号回复关键字：Zookeeper，后续会陆续更新 Zookeeper 系列文章。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。