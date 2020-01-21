## 下载安装

> https://www.mongodb.com/download-center

这里为了方便做测试所以用window版的，下载企业版的Windows 64位的，这是评估试用版的，虽然要求操作系统要Windows Server 2008 R2及以后的，但win7下也可用。

关于企业版的高级特性请[点击](https://www.mongodb.com/zh/products/mongodb-enterprise-advanced)查看。
## 配置

可以直接点bin下的mongod.exe运行并指定配置文件，路径等，也可以作为windows服务来启动，下面window服务配置为例。

1、添加Mongodb环境变量到path中。


2、新建配置目录

`C:\MongoDB\data\log`

`C:\MongoDB\data\db`


3、新建配置文件

C:\MongoDB\Server\3.4\mongod.cfg

```
systemLog:
    destination: file
    path: C:\MongoDB\data\log\mongod.log
storage:
    dbPath: C:\MongoDB\data\db
```

4、安装配置：


```
mongod --config C:\MongoDB\Server\3.4\mongod.cfg --install
```

## 启动


```
net start mongodb
```


## 停止


```
net stop mongodb
```


## 连接

可以输入命令行`mongo`，也可以使用客户端工具，推荐robomongo，有免费版和收费版。

> https://robomongo.org/

