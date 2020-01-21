昨天讲解了JWT的介绍、应用场景、优点及注意事项等，今天来个JWT具体的使用实践吧。

从JWT官网支持的类库来看，jjwt是Java支持的算法中最全的，推荐使用，网址如下。

> https://github.com/jwtk/jjwt

下面来看看如何使用jjwt来实现JWT token的生成与解密，主要用到sha512算法来演示。

**1、导入jjwt的maven包。**

```
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```

注意：JJWT依赖Jackson 2.x，低版本将报错。

**2、建立一个JWTTest测试类。**

**3、创建密钥；**

这里使用sha512算法，所以需要一个密钥。

```
Key KEY = new SecretKeySpec("javastack".getBytes(),
			SignatureAlgorithm.HS512.getJcaName());
```

这样就生成了一个固定的密钥：javastack

**4、生成JWT token。**

核心代码如下：

```
Map<String, Object> stringObjectMap = new HashMap<>();
stringObjectMap.put("type", "1");
String payload = "{\"user_id\":\"1341137\", \"expire_time\":\"2018-01-01 0:00:00\"}";
String compactJws = Jwts.builder().setHeader(stringObjectMap)
.setPayload(payload).signWith(SignatureAlgorithm.HS512, KEY).compact();

System.out.println("jwt key:" + new String(KEY.getEncoded()));
System.out.println("jwt payload:" + payload);
System.out.println("jwt encoded:" + compactJws);
```

注意：header可以不用设置，claims不能和payload同时设置。

输出结果：

```
jwt key:javastack
jwt payload:{"user_id":"1341137", "expire_time":"2018-01-01 0:00:00"}
jwt encoded:eyJ0eXBlIjoiMSIsImFsZyI6IkhTNTEyIn0.eyJ1c2VyX2lkIjoiMTM0MTEzNyIsICJleHBpcmVfdGltZSI6IjIwMTgtMDEtMDEgMDowMDowMCJ9.cnyXRnwczgNcNYqV6TUY2MaMfk6vujsZltC8Q51l40dwYJg516oZcV4VDKOypPT8fD7AE63PIhfdm2ALVrfv5A
```

**5、解密JWT token内容。**

核心代码如下：

```
Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(compactJws);
JwsHeader header = claimsJws.getHeader();
Claims body = claimsJws.getBody();

System.out.println("jwt header:" + header);
System.out.println("jwt body:" + body);
System.out.println("jwt body user-id:" + body.get("user_id", String.class));
```

输出结果：

```
jwt header:{type=1, alg=HS512}
jwt body:{user_id=1341137, expire_time=2018-01-01 0:00:00}
jwt body user-id:1341137
```

再用密文去JWT官网的调试器解密一下，看是否成功。

![](http://img.javastack.cn/18-1-4/36539517.jpg)

解密成功，其他算法使用逻辑一样，这样我们可以使用JWT来实现不同服务之间数据的安全传递。

