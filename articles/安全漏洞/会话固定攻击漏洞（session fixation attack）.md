
#### 什么是会话固定攻击？

会话固定攻击（session fixation attack）是利用应用系统在服务器的会话ID固定不变机制，借助他人用相同的会话ID获取认证和授权，然后利用该会话ID劫持他人的会话以成功冒充他人，造成会话固定攻击。

**看下面Session Fixation攻击的一个简单例子：**

![image](http://img.javastack.cn/18-1-27/44844045.jpg)

**整个攻击流程是：**

1、攻击者Attacker能正常访问该应用网站；

2、应用网站服务器返回一个会话ID给他；

3、攻击者Attacker用该会话ID构造一个该网站链接发给受害者Victim；

4-5、受害者Victim点击该链接，携带攻击者的会话ID和用户名密码正常登录了该网站，会话成功建立；

6、攻击者Attacker用该会话ID成功冒充并劫持了受害者Victim的会话。

> 更多攻击例子参考：https://www.owasp.org/index.php/Session_fixation

#### 攻击分析

攻击的整个过程，会话ID是没变过的，所以导致此漏洞。

#### 攻击修复

**1、登录重建会话**

每次登录后都重置会话ID，并生成一个新的会话ID，这样攻击者就无法用自己的会话ID来劫持会话，核心代码如下。

```
// 会话失效
session.invalidate();

// 会话重建
session=request.getSession(true);
```

**2、禁用客户端访问Cookie**

此方法也避免了配合XSS攻击来获取Cookie中的会话信息以达成会话固定攻击。在Http响应头中启用HttpOnly属性，或者在tomcat容器中配置。关于HttpOnly更多详细说明大家可以自行百度。

道高一尺，魔高一丈。目前我们已经对全线系统及时填补了该漏洞，以免给攻击者留下突破口。

转发到朋友圈给更多的朋友吧！
