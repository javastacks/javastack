
## 当前用户

现在我们能够开始做一些我们真正关心的事情——执行安全操作。

当保护我们的应用程序时，我们对自己可能提出的最为相关的问题是“当前用户是谁”或“当前用户是否被允许做XXX”。

当我们编写代码或设计用户接口时，问这些问题是很常见的：应用程序通常是基于用户的背景情况建立的，且你想基于每个用户标准体现（保障）功能。因此，对于我们考虑应用程序安全的最自然的方式是基于当前用户。

Shiro的API使用它的Subject概念从根本上代表了“当前用户”的概念。

几乎在所有的环境中，你可以通过下面的调用获取当前正在执行的用户：


```
Subject currentUser = SecurityUtils.getSubject();
```

使用 SecurityUtils.getSubject()，我们可以获得当前正在执行的Subject。Subject是一个安全术语，它基本上的意思是“当前正在执行的用户的特定的安全视图”。它并没有被称为"User"是因为"User"一词通常和人类相关联。

在安全界，术语"Subject"可以表示为人类，而且可是第三方进程，cron job，daemonaccount，或其他类似的东西。它仅仅意味着“该事物目前正与软件交互”。

对于大多数的意图和目的，你可以把 Subject 看成是 Shiro 的"User"概念。

getSubject()在一个独立的应用程序中调用，可以返回一个在应用程序特定位置的基于用户数据的Subject，并且在服务器环境中（例如，Web 应用程序），它获取的Subject 是基于关联了当前线程或传入请求的用户数据的。

## 当前用户会话

现在你拥有了一个Subject，你能拿它来做什么？
如果你想在应用程序的当前会话中使事物对于用户可用，你可以获得他们的会话：


```
Session session = currentUser.getSession();
session.setAttribute( "someKey", "aValue" );
```

Session是一个Shiro的特定实例，它提供了大部分你经常与 HttpSessoins使用的东西，除了一些额外的好处以及一
个巨大的区别：它不需要一个 HTTP 环境！

如果在一个Web应用程序内部部署，默认的Session将会是基于 HttpSession 的。但在一个非 Web 环境中，像这
个简单的教程应用程序，Shiro将会默认自动地使用它的 Enterprise Session Management。这意味着你会使用相同的API在你的应用程序，在任何层，不论部署环境！这开辟了应用程序的新世界，由于任何需要会话的应用程序不必再被强制使用HttpSession或EJB Stateful Session Beans。并且，任何客户端技术现在能够共享会话数据。

因此，现在你能获取一个Subject以及他们的Session。如果他们被允许做某些事，如对角色和权限的检查，像“检查”真正有用的地方在哪呢？

## 权限检查

嗯，我们只能为一个已知的用户做这些检查。我们上面的 Subject实例代表了当前用户，但谁又是当前用户？呃，
他们是匿名的——也就是说，直到直到他们至少登录一次。那么，让我像下面这样做：


```
if ( !currentUser.isAuthenticated() ) {
    //collect user principals and credentials in a gui specific manner
    //such as username/password html form, X509 certificate, OpenID, etc.
    //We'll use the username/password example here since it is the most common.
    //(do you know what movie this is from? ;)
    UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
    //this is all you have to do to support 'remember me' (no config - built in!):
    token.setRememberMe(true);
    currentUser.login(token);
}
```

这就是了！它再简单不过了。

但如果他们的登录尝试失败了会怎样？你能够捕获各种具体的异常来告诉你到底发生了什么，并允许你去处理并作
出相应反应：


```
try {
    currentUser.login( token );
    //if no exception, that's it, we're done!
} catch ( UnknownAccountException uae ) {
    //username wasn't in the system, show them an error message?
} catch ( IncorrectCredentialsException ice ) {
    //password didn't match, try again?
} catch ( LockedAccountException lae ) {
    //account for that username is locked - can't login.  Show them a message?
}
    ... more types exceptions to check if you want ...
} catch ( AuthenticationException ae ) {
    //unexpected condition - error?
}
```

你能够检查到许多不同类型的异常，或抛出你自己的自定义条件的异常——Shiro 可能不提供的。请参见[AuthenticationException JavaDoc](https://shiro.apache.org/static/current/apidocs/org/apache/shiro/authc/AuthenticationException.html) 获取更多。


> **Handy Hint**
>
> 最安全的做法是给普通的登录失败消息给用户，因为你当然不想帮助试图闯入你系统的攻击者。

好了，到现在为止，我们已经有了一个登录用户。我们还能做些什么？

比方说，他们是是谁：


```
//print their identifying principal (in this case, a username): 
log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
```

我们也可以测试他们是否有特定的角色：


```
if ( currentUser.hasRole( "schwartz" ) ) {
    log.info("May the Schwartz be with you!" );
} else {
    log.info( "Hello, mere mortal." );
}
```

我们还可以判断他们是否有权限在一个确定类型的实体上进行操作：


```
if ( currentUser.isPermitted( "lightsaber:weild" ) ) {
    log.info("You may use a lightsaber ring.  Use it wisely.");
} else {
    log.info("Sorry, lightsaber rings are for schwartz masters only.");
}
```

当然，我们可以执行极其强大的实例级权限检查——判断用户是否有能力访问某一类型的特定实例的能力：


```
if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
    log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
                "Here are the keys - have fun!");
} else {
    log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
}
```

小菜一碟，对吧？

最后，当用户完成了对应用程序的使用，他们可以注销：


```
currentUser.logout(); //removes all identifying information and invalidates their session too.
```

## 总结

希望此次推出的教程帮助您了解如何在一个基本的应用程序设置 Shiro 以及 Shiro 的主要设计理念。

