

### 问题描述

公司某个系统使用了tomcat自带的集群session复制功能，然后后报了一个oracle驱动包里面的连接不能被序列化的异常。

```
01-Nov-2017 16:45:26.653 SEVERE [https-jsse-nio-8443-exec-2] org.apache.catalina.ha.session.DeltaManager.requestCompleted Unable to serialize delta request for sessionid [F825A52DD9E0E7F8FD6BB3E3F721F841]
 java.io.NotSerializableException: oracle.jdbc.driver.T4CConnection
    at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1183)
    at java.io.ObjectOutputStream.defaultWriteFields(ObjectOutputStream.java:1547)
    at java.io.ObjectOutputStream.writeSerialData(ObjectOutputStream.java:1508)
    at java.io.ObjectOutputStream.writeOrdinaryObject(ObjectOutputStream.java:1431)
    at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1177)
    at java.io.ObjectOutputStream.defaultWriteFields(ObjectOutputStream.java:1547)
    at java.io.ObjectOutputStream.writeSerialData(ObjectOutputStream.java:1508)
```

### 解决方案

看到这个问题，首先检查整个系统里面的代码有没有用到T4CConnection或者java.sql.Connection并将它们放到了session中。

检查后发现系统并没有用到任何及相关的类放在session中。

接下来就运维的同学头疼了，换tomcat版本、改各种tomcat相关的配置都没有解决。

然后我判断可能是程序或者数据导致的问题，果然，在经过逐一排查穷举测试，发现干掉某个对象后功能正常了，不报序列化的错误了。而在干掉的那个对象里面发现使用了java.sql.Clob类型，曾几何时在网上有看到过这个异常，在tomcat session复制时使用了这个类型的字段是会出问题的。

真是茅塞顿开，经常确认，那个Clob字段在实际应用中并没有使用到，所以最后去掉该字段，折腾了整个团队两三天的问题最终得已解决。

tomcat真是醉了，session复制你不支持Clob字段，你报一个oracle驱动不能序列化的错误，有点误导，让人迷失方向，还好问题解决了。。