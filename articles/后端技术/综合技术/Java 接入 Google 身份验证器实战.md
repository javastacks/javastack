
#### 两步验证

大家应该对两步验证都熟悉吧？如苹果有自带的两步验证策略，防止用户账号密码被盗而锁定手机进行敲诈，这种例子屡见不鲜，所以苹果都建议大家开启两步验证的。

Google 的身份验证器一般也是用于登录进行两步验证，和苹果的两步验证是同样的道理。只不过 Google 的身份验证器用得更多更广泛，如 GitHub 的两步验证都是基于 Google 身份验证器。

#### Google Authenticator 简介

Google Authenticator 身份验证器是一款基于时间与哈希的一次性密码算法的两步验证软件令牌，用户需要下载手机 APP（Authenticator），该手机 APP 与网站进行绑定，当网站验证完用户名和密码之后会验证此 APP 上对应生成的 6 位验证码数字，验证通过则成功登录，否则登录失败。 

#### Google Authenticator 使用

我们来看下 Github 上的使用 Google 身份验证器开启两步验证的应用。

如图所示，默认 Github 是没有开启两步验证的，点击设置按钮进行设置。

![](http://img.javastack.cn/18-6-4/89202323.jpg)

Github 提供了基于 APP （谷歌身份验证器）和短信验证码两种两步验证的方式，我们选择第一种谷歌身份验证器。

![](http://img.javastack.cn/18-6-4/60561582.jpg)

进入第一种验证模式，接下来展示了一堆的恢复码，用来当 APP 验证器不能工作的紧急情况使用。把它们保存起来，然后点击下一步。

![](http://img.javastack.cn/18-6-4/53581436.jpg)

这个就是身份验证器的关键了，下载 Google 的 `Authenticator` APP，然后扫描这个二维码进行绑定。

![](http://img.javastack.cn/18-6-4/22628043.jpg)

绑定之后，APP Github 模块下面会显示一个 6 位的验证码，把它输入到上面那个框里面就行了。

![](http://img.javastack.cn/18-6-4/52228687.jpg)

如下图所示，已经成功开启两步验证了。

![](http://img.javastack.cn/18-6-4/67672750.jpg)

接下来我们退出 Github 再重新登录，页面就会提示要输入 Google 的身份验证器验证码了，如果 APP 不能正常工作，最下方还能通过之前保存下来的恢复码进行登录。

![](http://img.javastack.cn/18-6-4/8537646.jpg)

好了，Google Authenticator 使用就到这里，那它是如何工作的，它是什么原理呢？我们的网站、APP 如何接入 Google Authenticator，接下来我们一一拉开谜底。

#### Google Authenticator 工作流程

实际上 Google Authenticator 采用的是 TOTP 算法（Time-Based One-Time Password，即基于时间的一次性密码），其核心内容包括以下三点。

**1、安全密钥**

是客户端和服务端约定的安全密钥，也是手机端 APP 身份验证器绑定（手机端通过扫描或者手输安全密钥进行绑定）和验证码的验证都需要的一个唯一的安全密钥，该密钥由加密算法生成，并最后由 Base32 编码而成。

**2、验证时间**

Google 选择了 30 秒作为时间片，T的数值为 从Unix epoch（1970年1月1日 00:00:00）来经历的 30 秒的个数，所以在 Google Authenticator 中我们可以看见验证码每个 30 秒就会刷新一次。

更详细原理参考：

> https://blog.seetee.me/post/2011/google-two-step-verification/

**3、签署算法**

Google 使用的是 HMAC-SHA1 算法，全称是：Hash-based message authentication code(哈希运算消息认证码)，它是以一个密钥和一个消息为输入，生成一个消息摘要作为输出，这里以 SHA1 算法作为消息输入。

使用 HMAC 算法是因为只有用户本身知道正确的输入密钥，因此会得到唯一的输出，其算法可以简单表示为：

> hmac = SHA1(secret + SHA1(secret + input))

事实上，TOTP 是 HMAC-OTP（基于HMAC的一次密码生成）的超集，区别是 TOTP 是以当前时间作为输入，而HMAC-OTP 则是以自增计算器作为输入，该计数器使用时需要进行同步。

#### Google Authenticator 实战

知道上面的原理，我们就可以来应用实战了。

```
/**
 * 微信公众号：Java技术栈
 */
public class AuthTest {

	@Test
	public void genSecretTest() {
		String secret = GoogleAuthenticator.generateSecretKey();
		String qrcode = GoogleAuthenticator.getQRBarcodeURL("Java技术栈", "javastack.cn", secret);
		System.out.println("二维码地址:" + qrcode);
		System.out.println("密钥:" + secret);
	}

	@Test
	public void verifyTest() {
		String secret = "ZJTAQGLVOZ7ATWH2";
		long code = 956235;
		GoogleAuthenticator ga = new GoogleAuthenticator();
		boolean r = ga.verifCode(secret, code);
		System.out.println("是否正确：" + r);
	}
}
```

第一个方法是生成密钥和一个扫描二维码绑定的URL。

第二个方法是根据密钥和验证码进行验证。

这里仅提供一下 GoogleAuthenticator 类的源码逻辑参考。

> http://awtqty-zhang.iteye.com/blog/1986275

如果有收获欢迎点赞转发，也可以留言发表你的疑问和看法。

