![image](http://img.javastack.cn/18-2-4/38258738.jpg)

阿里的Druid大家都知道是最好的连接池，其强大的监控功能是我们追求的重要特性。但在实际情况中也有不少坑，说下最近遇到的一个坑吧！

#### 问题1：不断打印error级别的错误日志

> session ip change too many

**下面是其报错的关键源码**

> com.alibaba.druid.support.http.stat.WebSessionStat#addRemoteAddress

```
public void addRemoteAddress(String ip) {
    if (remoteAddresses == null) {
        this.remoteAddresses = ip;
        return;
    }

    if (remoteAddresses.contains(ip)) {
        return;
    }

    if (remoteAddresses.length() > 256) {
        LOG.error("session ip change too many");
        return;
    }

    remoteAddresses += ';' + ip;
}
```

**再来看看Druid连接池获取IP的方式**

> com.alibaba.druid.util.DruidWebUtils

```
public static String getRemoteAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
    }

    return ip;
}
```

**分析其源码**

这是阿里Druid连接池的session监控功能，会记录同一个会话ID的所有访问IP记录，当超过256个字符长度时就会打印这个错误日志，但实际功能不受影响。

看了下Druid session监控的页面，同一个会话请求次数并不多，但记录的IP却有问题，一个请求最多的会保存多级代理形成的多段IP（如192.168.1.2,192.168.1.3,192.168.1.4），这样一来5、6次请求就会使访问IP超出256长度从而打印这个错误。

**解决方案**

1、如果用不到session监控，就关闭此功能；

> https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_%E9%85%8D%E7%BD%AEWebStatFilter

```
<init-param>
  <param-name>sessionStatEnable</param-name>
  <param-value>false</param-value>
</init-param>
```

2、修改源码，如果有多段IP，截取第一段，并修改记录访问IP（256位）的长度；

作者去看了阿里最新的包，此问题还存在。

![](http://img.javastack.cn/18-1-29/92452744.jpg)

并且Github上的Druid官方错误申报里面也有同样的问题，阿里也没有修复的意思，所以我们已暂时关闭session监控功能。


#### 问题2：DruidStatView类异常

```
java.util.ConcurrentModificationException
    at java.util.LinkedHashMap$LinkedHashIterator.nextEntry(LinkedHashMap.java:394)
    at java.util.LinkedHashMap$ValueIterator.next(LinkedHashMap.java:409)
    at java.util.Collections$UnmodifiableCollection$1.next(Collections.java:1067)
    at com.alibaba.druid.support.http.stat.WebAppStat.getSessionStatDataList(WebAppStat.java:504)
    at com.alibaba.druid.support.http.stat.WebAppStatUtils.getSessionStatDataList(WebAppStatUtils.java:64)
    at com.alibaba.druid.support.http.stat.WebAppStatManager.getSessionStatData(WebAppStatManager.java:100)
    at com.alibaba.druid.stat.DruidStatService.getWebSessionStatDataList(DruidStatService.java:205)
    at com.alibaba.druid.stat.DruidStatService.service(DruidStatService.java:161)
    at com.alibaba.druid.support.http.StatViewServlet.process(StatViewServlet.java:162)
    at com.alibaba.druid.support.http.ResourceServlet.service(ResourceServlet.java:253)
```

**看源码，发现又是session监控的坑**

无力吐槽。。

![](http://img.javastack.cn/18-1-29/83615861.jpg)

for循环里面重复定义Map，可能在别的地方有元素变动，导致发生ConcurrentModificationException异常。

所以，最后关闭了session监控。

很好奇，阿里工程师都这种水平吗？
还是为了偷懒？
