
## 1、修改配置文件

打开tomcat/conf/server.xml配置文件，把下面这段配置注释取消掉，`keystorePass`为证书密钥需要手动添加，创建证书时指定的。

```
<Connector port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
               maxThreads="150" 
               SSLEnabled="true" 
               scheme="https" 
               secure="true"
               clientAuth="false" 
               sslProtocol="TLS"
               keystorePass="123456" />
```

## 2、创建证书

使用jdk工具类里面的`keytool`命令来生成证书，按照提示输入相应的信息。

```
C:\>keytool -genkey -alias https -keyalg RSA
输入密钥库口令:
您的名字与姓氏是什么?
  [Unknown]:  test
您的组织单位名称是什么?
  [Unknown]:  test
您的组织名称是什么?
  [Unknown]:  test
您所在的城市或区域名称是什么?
  [Unknown]:  test
您所在的省/市/自治区名称是什么?
  [Unknown]:  test
该单位的双字母国家/地区代码是什么?
  [Unknown]:  test
CN=test, OU=test, O=test, L=test, ST=test, C=test是否正确?
  [否]:  y

输入 <https> 的密钥口令
        (如果和密钥库口令相同, 按回车):
再次输入新口令:
```

这里的密钥口令就是配置文件中的`keystorePass`配置。

## 访问https

 通过`https://localhost:8443/your-project`就能访问https项目。
 
 这种方式只适合本地开启https测试，线上环境需要购买商业授权的证书，不过原理都是一样的。
 