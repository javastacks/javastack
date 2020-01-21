![image](http://s1.51cto.com/wyfs02/M01/9E/8C/wKioL1mTIqHT6t3uAACjzS9yw9I624.jpg-wh_651x-s_1547569217.jpg)

#### JWT介绍

JSON Web Token(JWT)是一种开放标准(RFC 7519)，它定义了一种紧凑独立的基于JSON对象在各方之间安全地传输信息的方式。这些信息可以被验证和信任，因为它是数字签名的。JWTs可以使用一个密钥(HMAC算法)，或使用RSA的公钥/私钥密钥对对信息进行签名。

让我们进一步解释这个定义的一些概念。

- **紧凑**

由于其较小的体积，JWTs可以通过URL、POST参数或HTTP头部参数进行传递，体积小也意味着其传输速度会相当快。

- **独立**

有效负载包含了所需要的关于用户的所有信息，避免了多次查询数据库的需要。

#### JWT的应用场景

- **认证**

这是使用JWT最常见的场景，一旦用户登录后，每个后续的请求都会包含JWT token，允许用户访问该token所允许的路由、服务、资源等。如单点登录是目前广泛使用JWT的一项功能，因为它的开销很小，而且在不同的域名中很容易使用。

- **信息交换**

JWT是一种在多方之间传递数据比较好的方式，因为JWT能使用签名，能确保发送者是谁，也可以验证传递过来的的信息是否被篡改。

#### JWT的结构

JWT由以下三部分组成：

1. **Header（头部）**
1. **Payload（载荷）**
1. **Signature（签名）**

因此，JWT通常看起来如下。

```
xxxxx.yyyyy.zzzzz
```

**Header**

header通常由两个部分组成：token类型（即JWT)和正在使用的散列算法，如HMAC SHA256或RSA。

如：

```
{
  "alg": "HS256",
  "typ": "JWT"
}
```

这个JSON经过Base64编码后形成JWT的第一部分。

**Payload**

载荷包含了对实体的申明，用户和一些其他元信息。申明以下三种。

1. 注册的申明
1. 公开的申明
1. 私有的申明

如：

```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

这个JSON经过Base64编码后形成JWT的第二部分。

**Signature**

签名用于验证JWT的发送者是谁，并确保消息在过程中不会被篡改。

创建签名部分，你需要用到编码后的header、编码后的payload、密钥、在header中指定的算法。

如下使用HMAC SHA256算法创建签名的方式：

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```

讲完了上面3个部门，最后就是由这3部分组成了。每个部分经过base64编码后，以.分隔。它能很容易的在HTML和HTTP环境中传递，也比像类似xml标准格式这样的更紧凑。

![image](https://cdn.auth0.com/content/jwt/encoded-jwt3.png)

如果想使用JWT并将这些概念应用到实践中，您可以使用官网首页下面的调试器来解码、验证和生成JWTs。

![image](https://cdn.auth0.com/blog/legacy-app-auth/legacy-app-auth-5.png)

#### JWT的工作原理

在身份验证中，当用户成功地使用他们的凭证登录时，将返回一个JWT的token，并且必须在本地保存(通常在本地保存，但也可以使用cookie)，而不是在服务器中创建会话并返回cookie的传统方法。

当用户想要访问受保护的路由或资源时，用户代理应该发送token，通常是在使用Bearer模式的Authorization头参数中。标题的内容应该如下所示:

```
Authorization: Bearer <token>
```

这是一个无状态的身份验证机制，因为用户状态永远不会保存在服务器内存中。服务器的受保护路由将在授权头中检查有效的JWT，如果它存在，用户将被允许访问受保护的资源。由于JWTs是独立的，所以所有必要的信息都在那里，减少了多次查询数据库的需求。

这使得完全可以依赖无状态的数据api，甚至向下游服务发出请求。哪个域名api服务并不重要，因为CORS攻击不会成为一个问题，因为它不使用cookie。

工作流程如下：

![image](https://cdn.auth0.com/content/jwt/jwt-diagram.png)

#### JWT的优点

- 因为JSON数据格式的通用性，所以JWT是可以跨语言的，主流语言都可以支持。
- payload部分可以存储其他业务逻辑所必要的非敏感信息。
- JWT构成简单，字节占用很小，所以非常便于传输的。
- 不需要在服务端保存会话信息，易于应用的扩展和安全等。

#### JWT的使用注意

1. 不要在payload存放敏感信息，因为该部分是可解密的。
1. 保存好secret私钥十分重要。
1. 尽量使用https协议

#### JWT参考网站

> 官网：https://jwt.io/

> 官方介绍：https://jwt.io/introduction/

> 支持类库：https://jwt.io/#libraries-io

> RFC 7519规范：https://tools.ietf.org/html/rfc7519

明天带来JWT的Java实战。

