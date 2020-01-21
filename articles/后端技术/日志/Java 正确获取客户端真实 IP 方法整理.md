![image](http://img.javastack.cn/18-2-4/79455273.jpg)

在JSP里，获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址了。

如果使用了反向代理软件，将http://192.168.1.110:2046/的URL反向代理为http://www.abc.com/的URL时，用request.getRemoteAddr()方法获取的IP地址是：127.0.0.1或192.168.1.110，而并不是客户端的真实IP。

经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求的地址返回给客户端。但是在转发请求的HTTP头信息中，增加了X-FORWARDED-FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。

当我们访问http://www.abc.com/index.jsp/时，其实并不是我们浏览器真正访问到了服务器上的index.jsp文件，而是先由代理服务器去访问http://192.168.1.110：2046/index.jsp，代理服务器再将访问到的结果返回给我们的浏览器，因为是代理服务器去访问index.jsp的，所以index.jsp中通过request.getRemoteAddr()的方法获取的IP实际上是代理服务器的地址，并不是客户端的IP地址。

**外界流传的JAVA/PHP服务器端获取客户端IP都是这么取的：**

伪代码：

1）ip = request.getHeader("X-FORWARDED-FOR ")

2）如果该值为空或数组长度为0或等于"unknown"，那么：\
ip = request.getHeader("Proxy-Client-IP")

3）如果该值为空或数组长度为0或等于"unknown"，那么：\
ip = request.getHeader("WL-Proxy-Client-IP")

4）如果该值为空或数组长度为0或等于"unknown"，那么：\
ip = request.getHeader("HTTP_CLIENT_IP")

5）如果该值为空或数组长度为0或等于"unknown"，那么：\
ip = request.getHeader("X-Real-IP")

6）如果该值为空或数组长度为0或等于"unknown"，那么：\
ip = request.getRemoteAddr ()

**先说说这些请求头的意思**

- X-Forwarded-For

这是一个 Squid 开发的字段，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项。

格式为X-Forwarded-For:client1,proxy1,proxy2，一般情况下，第一个ip为客户端真实ip，后面的为经过的代理服务器ip。现在大部分的代理都会加上这个请求头。

- Proxy-Client-IP/WL- Proxy-Client-IP

这个一般是经过apache http服务器的请求才会有，用apache http做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。

- HTTP_CLIENT_IP

有些代理服务器会加上此请求头。

- X-Real-IP
nginx代理一般会加上此请求头。

**下面是一个参考获取客户端IP地址的方法：**

```
public static String getIpAddress(HttpServletRequest request) {
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
	if (ip.contains(",")) {
		return ip.split(",")[0];
	} else {
		return ip;
	}
}
```

> 如果使用的是Druid连接池，可以参考使用：com.alibaba.druid.util.DruidWebUtils#getRemoteAddr方法，但这个是经过多级代理的IP地址，需要自己处理下获取第一个。

**有几点要注意**

1. 这些请求头都不是http协议里的标准请求头，也就是说这个是各个代理服务器自己规定的表示客户端地址的请求头。如果哪天有一个代理服务器软件用oooo-client-ip这个请求头代表客户端请求，那上面的代码就不行了。

1. 这些请求头不是代理服务器一定会带上的，网络上的很多匿名代理就没有这些请求头，所以获取到的客户端ip不一定是真实的客户端ip。代理服务器一般都可以自定义请求头设置。

1. 即使请求经过的代理都会按自己的规范附上代理请求头，上面的代码也不能确保获得的一定是客户端ip。不同的网络架构，判断请求头的顺序是不一样的。

1. 最重要的一点，请求头都是可以伪造的。如果一些对客户端校验较严格的应用（比如投票）要获取客户端ip，应该直接使用ip=request.getRemoteAddr()，虽然获取到的可能是代理的ip而不是客户端的ip，但这个获取到的ip基本上是不可能伪造的，也就杜绝了刷票的可能。(有分析说arp欺骗+syn有可能伪造此ip，如果真的可以，这是所有基于TCP协议都存在的漏洞)，这个ip是tcp连接里的ip。

> 参考\
> http://blog.csdn.net/sgx425021234/article/details/19043459\
> http://blog.csdn.net/fengwind1/article/details/51992528

