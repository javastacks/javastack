### 一、Docker简介

- Docker是开源应用容器引擎，轻量级容器技术。
- 基于Go语言，并遵循Apache2.0协议开源
- Docker可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的Linux系统上，也可以实现虚拟化
- 容器完全使用沙箱技术，相互之间不会有任何接口
- 类似于虚拟机技术(vmware、vitural)，但docker直接运行在操作系统(Linux)上，而不是运行在虚拟机中，速度快，性能开销极低

**白话文，简介就是:**

> Docker支持将软件编译成一个镜像，然后在镜像中各种软件做好配置，将镜像发布出去，其他使用者可以直接使用这个镜像。 
> 运行中的这个镜像称为容器，容器启动是非常快速的。类似windows里面的ghost操 作系统，安装好后什么都有了。

### 二、Docker核心概念

- docker镜像(Images)：Docker镜像是用于创建Docker容器的模板
- docker容器(Container)：镜像启动后的一个实例称为容器，容器是独立运行的一个或一组应用，
- docker客户端(Client)：客户端通过命令行或其他工具使用Docker API(https://docs.docker.com/reference/api/docker_remote_api)与Docker的守护进程进行通信
- docker主机(Host)：一个物理或虚拟的机器用来执行Docker守护进程和容器
- docker仓库(Registry)：Docker仓库用来存储镜像，可以理解为代码控制中的代码仓库，Docker Hub(https://hub.docker.com) 提供了庞大的镜像集合供使用

### 三、Docker安装及启停

#### 1. 查看centos版本

> Docker 要求 CentOS 系统的内核版本高于 3.10

通过命令：

```
uname -r
```

查看当前centos版本，如版本不符，需升级系统版本

#### 2 升级软件包及内核(可选)

```
yum update
```

#### 3. 安装docker

```
yum install docker
```

#### 4. 启动docker

```
systemctl start docker
```

#### 5. 将docker服务设为开机启动

```
systemtctl enable docker
```

#### 5. 停止docker

```
systemtctl stop docker
```

### 四、Docker常用命令及操作

#### 4.1 docker镜像命令

> 通常情况下，Docker的镜像都放在Docker的官网 Docker Hub上，[点此前往官网](https://hub.docker.com/explore/)

##### 4.1.1 镜像检索

除了可以在[Docker Hub](https://hub.docker.com/explore/)上搜索镜像外，还可以通过命令 `docker search xxx` 进行搜索，下面以 mysql 为例：

```
docker search mysql
```

结果如下：

![](https://upload-images.jianshu.io/upload_images/1900599-b0939b50cc4738ce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


##### 4.1.2 镜像下载

下载命名为：`docker pull 镜像名:tag`，其中`tag`多为系统的版本，可选的，默认为`least`。
```
docker pull mysql
```

##### 4.1.3 镜像列表

获取已下载镜像列表命令：`docker images`

![](https://upload-images.jianshu.io/upload_images/1900599-c339bb96f54cbabc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

其中，

`RESPOSITORY`为镜像名
`TAG`为镜像版本，`least`代表最新版
`IMAGE_ID`为该镜像唯一ID
`CREATED`为该镜像创建时间
`SIZE`为该镜像大小

##### 4.1.1 镜像删除

删除指定镜像：

```
docker rmi image-id
```

删除所有镜像：

```
docker rmi $(docker images -q)
```

#### 4.2 容器操作

> 可以理解为软件下载(下载QQ)-->安装(QQ)-->运行(QQ)的过程。
> 下面以Tomcat为例

##### 4.2.1 搜索镜像

```
docker search tomcat
```

##### 4.2.2 下载镜像

```
docker pull tomcat
```

##### 4.2.3 根据镜像启动容器

最简单的运行镜像为容器的命令如下：

```
docker run --name container-name -d image-name
```

运行一个容器，使用`docker run`命令即可。

`-- name`：为容器起一个名称
`-d`：detached，执行完这句命令后，控制台将不会阻塞，可以继续输入命令操作
`image-name`：要运行的镜像名称

##### 4.2.4 查看运行中容器

可通过如下命令，查看运行中的容器列表：

```
docker ps
```

![](https://upload-images.jianshu.io/upload_images/1900599-f620ae32f4356669.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`CONTAINER ID`：启动时生成的ID
`IMAGE`：该容器使用的镜像
`COMMAND`：容器启动时执行的命令
`CREATED`：容器创建时间
`STATUS`：当前容器状态
`PORTS`：当前容器所使用的默认端口号
`NAMES`：启动时给容器设置的名称

##### 4.2.5 停止运行中容器

通过以下命令来停止运行中的容器：

```
docker stop container-name/container-id
```

##### 4.2.5 查看所有的容器

通过以下命令可查看运行和停止的所有容器：

```
docker ps -a
```

##### 4.2.6 启动容器

通过以下命令启动容器：

```
docker start container-name/container-id
```

##### 4.2.7 删除容器

删除单个容器：

```
docker rm container-id
```

删除所有容器：

```
docker rm $(docker ps -a -q )
```

##### 4.2.8 启动做端口映射的容器

Docker运行容器之后却发现没IP，没端口，也就是说，启动容器的时候如果不指定对应参数，在容器外部是无法通过网络来访问容器内的网络应用和服务的。 所以需要通过Docker端口映射来实现网络访问。

Docker的端口映射通过`-p`参数实现，命令如下：

```
docker run --name tomcat1 -d tomcat
docker run --name tomcat2 -d -p 8888:8080 tomcat
```
如上，就把主机端口8888请求映射到Docker容器内部端口8080了。

执行完这两条命令后，通过`docker ps`查看：

![](https://upload-images.jianshu.io/upload_images/1900599-86662f6e11bef5e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过`PORTS`可以看出，`tomcat2`是做了端口映射的，`tomcat1`是没进行映射过的。

分别通过浏览器访问：

`http://*.*.*.*:8080/` // tomcat1默认端口

`http://*.*.*.*:8888/` //

做过端口映射的Tomcat2，8888会转发请求到tomcat2的8080
结果如下：

```
第一个请求是无法请求到的，原因开篇处说过了。
第二个请求是可以正常进行请求的，会由tomcat2容器进行处理
```

端口映射格式：

```
ip:hostport:containerport  

#指定ip、指定主机port、指定容器port
ip::containerport        

#指定ip、未指定主机port、指定容器port
hostport:container         

#未指定ip port、指定主机port、指定容器port  
```

##### 4.2.9 查看容器日志

查看当前容器日志，可通过如下命令：

```
docker logs container-id/container-name
```

##### 4.2.10 查看端口映射

可以通过如下命令查看容器映射了哪些端口及协议：

```
docker port container-id
```

示例：

```
[root@docker ~]#docker port 46114af6b44e
8080/tcp -> 0.0.0.0:8888
[root@docker ~]#docker port cea668ee4db0
```
如果返回空，则代表没进行端口映射。

##### 4.2.11 登录退出容器

运行中的容器其实是一个功能完备的Linux操作系统，所以我们可以像常规系统一样进行登陆及退出操作。

登录命令为：

```
docker exec -it container-id/container-name bash
```

退出命令为：

```
exit
```

##### 4.2.12 更多操作命令

> 更多命令可以参考：
> https://docs.docker.com/engine/reference/commandline/docker/

