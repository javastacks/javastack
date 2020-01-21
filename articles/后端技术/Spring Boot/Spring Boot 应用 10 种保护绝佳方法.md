> 原文：developer.okta.com/blog/2018/07/30/10-ways-to-secure-spring-boot
译文：www.jdon.com/49653

Spring Boot大大简化了Spring应用程序的开发。它的自动配置和启动依赖大大减少了开始一个应用所需的代码和配置量，如果你已经习惯了Spring和大量XML配置，Spring Boot无疑是一股清新的空气。

Spring Boot于2014年首次发布，自那以后发生了很多变化。安全性问题与代码质量和测试非常相似，已经日渐成为开发人员关心的问题，如果你是开发人员并且不关心安全性，那么也许认为一切理所当然。本文目的是介绍如何创建更安全的Spring Boot应用程序。

马特雷布尔与Simon Maple合作完成了这篇文章，他们都是为安全公司工作，热爱Java，并希望帮助开发人员创建更安全的应用程序。我们认为撰写这篇文章将是回馈社区的有趣方式。

#### 1.在生产中使用HTTPS

传输层安全性（TLS）是HTTPS的官方名称，你可能听说过它称为SSL（安全套接字层），SSL是已弃用的名称，TLS是一种加密协议，可通过计算机网络提供安全通信。其主要目标是确保计算机应用程序之间的隐私和数据完整性。

过去，TLS / SSL证书很昂贵，而且HTTPS被认为很慢，现在机器变得更快，已经解决了性能问题，Let's Encrypt提供免费的TLS证书,这两项发展改变了游戏，并使TLS成为主流。

截至2018年7月24日，Google Chrome 将HTTP网站标记为“不安全”。虽然这在网络社区引起了相当多的争议。知名安全研究员特洛伊亨特创建了一个为什么不适用HTTPS？跟踪不使用HTTPS的大型网站的网站。

Let’s Encrypt TLS证书可以自动化生成和更新，由于他们是免费的，所以没有理由不去做！Spring Boot Secured By Let’s Encrypt的加密是如何做到这一点的有用指南。

要在Spring Boot应用程序中强制使用HTTPS，您可以扩展WebSecurityConfigurerAdapter并要求安全连接。

```
@Configuration
public class WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().requiresSecure();
    }
}
```

另一个重要的事情是使用HTTP严格传输安全性（HSTS）。HSTS是一种Web安全策略机制，可以保护网站免受协议降级攻击和cookie劫持。服务器使用名为Strict-Transport-Security的响应头字段将HSTS策略传送到浏览器。Spring Security默认发送此标头，以避免在开始时出现不必要的HTTP跃点，点击这里一分钟开启Tomcat https支持。

#### 2.使用Snyk检查你的依赖关系

你很可能不知道应用程序使用了多少直接依赖项，这通常是正确的，尽管依赖性构成了整个应用程序的大部分。攻击者越来越多地针对开源依赖项，因为它们的重用为恶意黑客提供了许多受害者，确保应用程序的整个依赖关系树中没有已知的漏洞非常重要。

Snyk测试你的应用程序构建包，标记那些已知漏洞的依赖项。它在仪表板在应用程序中使用的软件包中存在的漏洞列表。

此外，它还将建议升级的版本或提供补丁，并提供针对源代码存储库的拉取请求来修复您的安全问题。Snyk还确保在你的存储库上提交的任何拉取请求（通过webhooks）时都是通过自动测试的，以确保它们不会引入新的已知漏洞。

每天都会在现有项目和库中发现新的漏洞，因此监控和保护生产部署也很重要。Snyk拍摄快照并监控你的部署，以便在发现新漏洞时，你可以通过JIRA，slack或电子邮件自动收到通知，并创建拉取请求以提供新漏洞的升级和补丁。

Snyk可通过Web UI和CLI获得，因此您可以轻松地将其与CI环境集成，并将其配置为在存在严重性超出设定阈值的漏洞时中断构建。

你可以免费使用Snyk进行开源项目或使用有限数量的私有项目。

#### 3.升级到最新版本

定期升级应用程序中的依赖项有多种原因。安全性是让您有升级动力的最重要原因之一。该start.spring.io起始页面采用了最新的春季版本的软件包，以及依赖关系，在可能的情况。

基础架构升级通常不如依赖项升级具有破坏性，因为库作者对向后兼容性和版本之间的行为更改的敏感性各不相同。话虽如此，当你在配置中发现安全漏洞时，您有三种选择：升级，修补程序或忽略。

在对应用程序进行必要的更改以使用较新版本之后，就应用程序的整体运行状况而言，升级是最安全的。

#### 4.启用CSRF保护

跨站点请求伪造(Cross-Site Request Forgery )是一种攻击，强制用户在他们当前登录的应用程序中执行不需要的操作。如果用户是普通用户，一个成功攻击可能涉及请求的状态更改，如转移资金或更改其电子邮件地址，如果用户具有提升管理员的权限，则CSRF攻击可能会危及整个应用程序。

Spring Security具有出色的CSRF支持，如果您正在使用Spring MVC的<form:form>标签或Thymeleaf @EnableWebSecurity，默认情况下处于启用状态，CSRF令牌将自动添加为隐藏输入字段。

如果你使用的是像Angular或React这样的JavaScript框架，则需要配置CookieCsrfTokenRepository以便JavaScript可以读取cookie。


```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
```


如果你正在使用Angular，这就是你需要做的。如果您使用的是React，则需要读取XSRF-TOKENcookie并将其作为X-XSRF-TOKEN标题发回。

当请求通过HTTPS发生时，Spring Security会自动加入一个secure标识到XSRF-TOKENcookie 。Spring Security对于CSRF cookie不使用SameSite=strict 的标志，但它在使用Spring Session或WebFlux会话处理时会使用，这对会话cookie有意义，因为它有助于识别用户，但是没有为CSRF cookie提供太多价值，因为CSRF令牌也需要在请求中。点击这里了解CSRF更多详情。

#### 5.使用内容安全策略防止XSS攻击

内容安全策略（CSP）是一个增加的安全层，可帮助缓解XSS（跨站点脚本）和数据注入攻击。要启用它，你需要配置应用程序以返回Content-Security-Policy标题。你还可以在HTML页面中<meta http-equiv="Content-Security-Policy">使用标记。

Spring安全性默认提供了许多安全标头：


```
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Content-Type-Options: nosniff
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
```


Spring Security * 默认情况下不添加 CSP。你可以使用以下配置在Spring Boot应用程序中启用CSP标头。


```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
            .contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/");
    }
}
```


CSP是防止XSS攻击的良好防御，请记住，打开CSP能让CDN访问许多非常古老且易受攻击的JavaScript库，这意味着使用CDN不会为安全性增加太多价值。点击这里了解XSS更多详情。

你可以在securityheaders.com测试你的CSP标头是否有用。

#### 6.使用OpenID Connect进行身份验证

OAuth 2.0是行业标准的授权协议。它使用scope来定义授权用户可以执行的操作的权限。但是，OAuth 2.0不是身份验证协议，并且不提供有关经过身份验证的用户的信息。

OpenID Connect（OIDC）是一个OAuth 2.0扩展，提供用户信息，除了访问令牌之外，它还添加了ID令牌，以及/userinfo可以从中获取其他信息的端点，它还添加了发现功能和动态客户端注册的端点。

如果使用OIDC进行身份验证，则无需担心如何存储用户、密码或对用户进行身份验证。相反，你可以使用身份提供商（IdP）为你执行此操作，你的IdP甚至可能提供多因素身份验证（MFA）等安全附加组件。

要了解如何在Spring Boot应用程序中使用OIDC，请参阅Spring Security 5.0和OIDC入门。要总结如何使用它，你需要向项目添加一些依赖项，然后在application.yml文件中配置一些属性。


```
spring:
  security:
    oauth2:
      client:
        registration:
          okta:
            client-id: {clientId}
            client-secret: {clientSecret}
            scope: openid email profile
        provider:
          okta:
            issuer-uri: https://{yourOktaDomain}/oauth2/default
```


注意：issuer-uri仅在Spring Security 5.1中支持使用，Spring Security 5.1正在积极开发中并计划于2018年9月发布。

你可以使用像Keycloak这样的开源系统来设置自己的OIDC服务器。如果你不想在生产中维护自己的服务器，可以使用Okta的Developer API。

#### 7.管理密码？使用密码哈希！

以纯文本格式存储密码是最糟糕的事情之一。幸运的是，Spring Security默认情况下不允许使用纯文本密码。它还附带了一个加密模块，可用于对称加密，生成密钥和密码散列（也就是密码编码）。

PasswordEncoder 是Spring Security中密码哈希的主要接口，如下所示：

```
public interface PasswordEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
```

Spring Security提供了几种实现，最受欢迎的是BCryptPasswordEncoder和Pbkdf2PasswordEncoder。

对于一般的密码管理，我们建议使用SCrypt或Argon2, SCrypt现在已经过时了（已经有一段时间了），并且有一个额外的复杂因素，BCrypt没有这个因素，这使得暴力破解变得加倍地困难。它由着名的密码学家/安全人员（Colin Percival）编写，并且在几乎所有编程语言中都有很好的库，SCrypt也得到Latacora的认可。

Spring Security 5.1（即2018年9月下旬）将附带UserDetailsPasswordService API，允许您升级密码存储。

#### 8.安全地存储秘密

应谨慎处理敏感信息，如密码，访问令牌等，你不能以纯文本形式传递，或者如果将它们保存在本地存储中。由于（GitHub）的历史已经一次又一次证明，开发人员并没有仔细考虑如何存储他们的秘密。

一个好的做法是将保密信息存储在保管库中，该保管库可用于存储，提供对应用程序可能使用的服务的访问权限，甚至生成凭据。HashiCorp的Vault使得存储机密变得很轻松，并提供了许多额外的服务。

如果您对此感兴趣，请务必花一些时间查看Spring Vault，它为HashiCorp Vault添加抽象，为客户提供基于Spring注释的访问，允许他们访问、存储和撤销机密而不会迷失在基础架构中。以下代码段显示了使用注释从Spring Vault中提取密码的方便程度。

```
@Value("${password}")
String password;
```

#### 9.使用OWASP的ZAP测试您的应用程序

OWASP ZAP安全工具是针对在运行活动的应用程序进行渗透测试的代理。它是一个受欢迎的（超过4k星）免费的开源项目，托管在GitHub上。

OWASP ZAP用于查找漏洞的两种方法是Spider和Active Scan。

Spider工具以URL种子开头，它将访问并解析每个响应，识别超链接并将它们添加到列表中。然后，它将访问这些新找到的URL并以递归方式继续，为您的Web应用程序创建URL映射。

Active Scan工具将根据潜在漏洞列表自动测试你选择的目标。它提供了一个报告，显示Web应用程序可被利用的位置以及有关漏洞的详细信息。

#### 10.让你的安全团队进行代码审查

代码评审对任何高性能软件开发团队都至关重要。在Okta，我们所有的生产代码和官方开源项目都需要通过我们的专家安全团队进行分析，你的公司可能没有安全专家，但如果你正在处理敏感数据，也许你应该这样做！

