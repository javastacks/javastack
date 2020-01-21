
![image](https://upload.wikimedia.org/wikipedia/commons/0/06/Apache_Shiro_Logo.png)


## Shiro是什么？

Apache Shiro是一个强大而灵活的开源安全框架，它干净利落地处理身份认证，授权，企业会话管理和加密。

Apache Shiro的首要目标是易于使用和理解。安全有时候是很复杂的，甚至是痛苦的，但它没有必要这样。框架应该尽可能掩盖复杂的地方，露出一个干净而直观的API，来简化开发人员在使他们的应用程序安全上的努力。

> 官网：http://shiro.apache.org

## Shiro有什么用？

以下是你可以用Apache Shiro所做的事情：

- 验证用户来核实他们的身份

- 对用户执行访问控制，如：

1. 判断用户是否被分配了一个确定的安全角色；

2. 判断用户是否被允许做某事；

- 在任何环境下使用Session API，即使没有Web或EJB容器。

- 在身份验证，访问控制期间或在会话的生命周期，对事件作出反应。

- 聚集一个或多个用户安全数据的数据源，并作为一个单一的复合用户“视图”。

- 启用单点登录（SSO）功能。

- 为没有关联到登录的用户启用"Remember Me"服务

- 以及更多——全部集成到紧密结合的易于使用的API中。

Shiro 视图在所有应用程序环境下实现这些目标——从最简单的命令行应用程序到最大的企业应用，不强制依赖其
他第三方框架，容器，或应用服务器。当然，该项目的目标是尽可能地融入到这些环境，但它能够在任何环境下立
即可用。

## Shiro特性

Apache Shiro是一个拥有许多功能的综合性的程序安全框架。

![image](http://dl2.iteye.com/upload/attachment/0093/9788/d59f6d02-1f45-3285-8983-4ea5f18111d5.png)

##### Shiro把Shiro开发团队称为“应用程序的四大基石”——身份验证，授权，会话管理和加密作为其目标。

- Authentication：有时也简称为“登录”，这是一个证明用户是他们所说的他们是谁的行为。

- Authorization：访问控制的过程，也就是绝对“谁”去访问“什么”。

- Session Management：管理用户特定的会话，即使在非 Web 或 EJB 应用程序。

- Cryptography：通过使用加密算法保持数据安全同时易于使用。


##### 也提供了额外的功能来支持和加强在不同环境下所关注的方面，尤其是以下这些：

- Web Support：Shiro的web支持的API能够轻松地帮助保护 Web 应用程序。

- Caching：缓存是Apache Shiro中的第一层公民，来确保安全操作快速而又高效。

- Concurrency：Apache Shiro利用它的并发特性来支持多线程应用程序。

- Testing：测试支持的存在来帮助你编写单元测试和集成测试，并确保你的能够如预期的一样安全。

- "Run As"：一个允许用户假设为另一个用户身份（如果允许）的功能，有时候在管理脚本很有用。

- "Remember Me"：在会话中记住用户的身份，所以他们只需要在强制时候登录。

## Shiro 架构

Apache Shiro的设计目标是通过直观和易于使用来简化应用程序安全。Shiro 的核心设计体现了大多数人们是如何考
虑应用程序安全的——在某些人（或某些事）与应用程序交互的背景下。

应用软件通常是基于用户背景情况设计的。也就是说，你将经常设计用户接口或服务API，基于一个用户将要（或应该）如何与该软件交互。例如，你可能会说，“如果用户与我的应用程序交互的用户已经登录，我将显示一个他们能够点击的按钮来查看他们的帐户信息。如果他们没有登录，我将显示一个登录按钮。”

这个简单的陈述表明应用程序很大程度上的编写是为了满足用户的要求和需要。即使该“用户”是另一个软件系统而不是一个人类，你仍然得编写代码来响应行为，基于当前与你的软件进行交互的人或物。

Shiro在它自己的设计中体现了这些概念。通过匹配那些对于软件开发人员来说已经很直观的东西，Apache Shiro几
乎在任何应用程序保持了直观和易用性。

在最高的概念层次，Shiro的架构有3个主要的概念：Subject，SecurityManager 和 Realms。

下面的关系图是关于这
些组件是如何交互的高级概述，而且我们将会在下面讨论每一个概念：

![image](http://dl2.iteye.com/upload/attachment/0093/9790/5e0e9b41-0cca-367f-8c87-a8398910e7a6.png)

- **Subject**

在我们的教程中已经提到，Subject实质上是一个当前执行用户的特定的安全“视图”。鉴于"User"一词通常意味着一个人，而一个Subject可以是一个人，但它还可以代表第三方服务，daemon account，cron job，或其他类似的任何东西——基本上是当前正与软件进行交互的任何东西。

所有Subject实例都被绑定到（且这是必须的）一个SecurityManager上。当你与一个Subject交互时，那些交互作用转化为与SecurityManager交互的特定subject的交互作用。

- **SecurityManager**

SecurityManager是Shiro架构的心脏，并作为一种“保护伞”对象来协调内部的安全组件共同构成一个对象图。然而，一旦SecurityManager和它的内置对象图已经配置给一个应用程序，那么它单独留下来，且应用程序开发人员几乎使用他们所有的时间来处理Subject API。

稍后会更详细地讨论SecurityManager，但重要的是要认识到，当你正与一个Subject进行交互时，实质上是幕后的 SecurityManager处理所有繁重的Subject安全操作。这反映在上面的基本流程图。

- **Realms**

Realms担当Shiro和你的应用程序的安全数据之间的“桥梁”或“连接器”。当它实际上与安全相关的数据如用来执行身份验证（登录）及授权（访问控制）的用户帐户交互时，Shiro 从一个或多个为应用程序配置的Realm中寻找许多这样的东西。

在这个意义上说，Realm本质上是一个特定安全的DAO：它封装了数据源的连接详细信息，使Shiro所需的相关的数据可用。当配置Shiro时，你必须指定至少一个Realm用来进行身份验证和/或授权。SecurityManager可能配置多个Realms，但至少有一个是必须的。

Shiro提供了立即可用的Realms来连接一些安全数据源（即目录），如LDAP，关系数据库（JDBC），文本配置源，像 INI 及属性文件，以及更多。你可以插入你自己的Realm 实现来代表自定义的数据源，如果默认地Realm不符合你的需求。

像其他内置组件一样，Shiro SecurityManager控制 Realms是如何被用来获取安全和身份数据来代表 Subject 实例的。

下图展示了Shiro的核心架构概念，紧跟其后的是每个的简短总结：

![image](http://dl2.iteye.com/upload/attachment/0093/9792/9b959a65-799d-396e-b5f5-b4fcfe88f53c.png)

- **Subject**(org.apache.shiro.subject.Subject)

当前与软件进行交互的实体（用户，第三方服务，cron job，等等）的安全特定“视图”。



- **SecurityManager**(org.apache.shiro.mgt.SecurityManager)

如上所述，SecurityManager是Shiro架构的心脏。它基本上是一个“保护伞”对象，协调其管理的组件以确保它们能够一起顺利的工作。它还管理每个应用程序用户的Shiro 的视图，因此它知道如何执行每个用户的安全操作。


- **Authenticator**(org.apache.shiro.authc.Authenticator)

Authenticator是一个对执行及对用户的身份验证（登录）尝试负责的组件。当一个用户尝试登录时，该逻辑被 Authenticator执行。Authenticator知道如何与一个或多个Realm协调来存储相关的用户/帐户信息。从这些Realm中获得的数据被用来验证用户的身份来保证用户确实是他们所说的他们是谁。

- **Authentication Strategy**(org.apache.shiro.authc.pam.AuthenticationStrategy)

如果不止一个Realm被配置，则AuthenticationStrategy将会协调这些Realm来决定身份认证尝试成功或失败下的条件（例如，如果一个Realm成功，而其他的均失败，是否该尝试成功？是否所有的Realm必须成功？或只有第一个成功即可？）。

- **Authorizer**(org.apache.shiro.authz.Authorizer)

Authorizer是负责在应用程序中决定用户的访问控制的组件。它是一种最终判定用户是否被允许做某事的机制。与 Authenticator相似，Authorizer也知道如何协调多个后台数据源来访问角色恶化权限信息。Authorizer使用该信息来准确地决定用户是否被允许执行给定的动作。

- **SessionManager**(org.apache.shiro.session.SessionManager)

SessionManager知道如何去创建及管理用户Session生命周期来为所有环境下的用户提供一个强健的Session体验。这在安全框架界是一个独有的特色——Shiro拥有能够在任何环境下本地化管理用户Session的能力，即使没有可用的Web/Servlet或EJB容器，它将会使用它内置的企业级会话管理来提供同样的编程体验。SessionDAO的存在允许任何数据源能够在持久会话中使用。

- **SessionDAO**(org.apache.shiro.session.mgt.eis.SessionDAO)

SesssionDAO代表SessionManager执行Session持久化（CRUD）操作。这允许任何数据存储被插入到会话管理的基础之中。

- **CacheManager**(org.apahce.shiro.cache.CacheManager)

CacheManager创建并管理其他Shiro组件使用的Cache实例生命周期。因为Shiro能够访问许多后台数据源，由于身份验证，授权和会话管理，缓存在框架中一直是一流的架构功能，用来在同时使用这些数据源时提高性能。任何现代开源和/或企业的缓存产品能够被插入到Shiro来提供一个快速及高效的用户体验。

- **Cryptography**(org.apache.shiro.crypto.*)

Cryptography是对企业安全框架的一个很自然的补充。Shiro的crypto包包含量易于使用和理解的cryptographic Ciphers，Hasher（又名digests）以及不同的编码器实现的代表。所有在这个包中的类都被精心地设计以易于使用和易于理解。任何使用Java的本地密码支持的人都知道它可以是一个难以驯服的具有挑战性的动物。Shiro的cryptoAPI 简化了复杂的Java机制，并使加密对于普通人也易于使用。

- **Realms**(org.apache.shiro.realm.Realm)

如上所述，Realms在Shiro和你的应用程序的安全数据之间担当“桥梁”或“连接器”。当它实际上与安全相关的数据如用来执行身份验证（登录）及授权（访问控制）的用户帐户交互时，Shiro从一个或多个为应用程序配置的Realm中寻找许多这样的东西。你可以按你的需要配置多个Realm（通常一个数据源一个Realm），且Shiro将为身份验证和授权对它们进行必要的协调。

**The SecurityManager**

因为Shiro的API鼓励一个以Subject为中心的编程方式，大多数应用程序开发人员很少，如果真有，与SecurityManager直接进行交互（框架开发人员有时候会觉得它很有用）。即便如此，了解如何SecurityManager是如何工作的仍然是很重要的，尤其是在为应用程序配置一个SecurityManager的时候。

**Design**

如前所述，应用程序的SecurityManager执行安全操作并管理所有应用程序用户的状态。在Shiro的默认SecurityManager实现中，这包括：

- Authentication

- Authorization

- Session Management

- Cache Management

- Realm coordination

- Event propagation

- "Remember Me" Services

- Subject creation

- Logout

以及更多。

但这是许多功能来尝试管理一个单一的组件。而且，使这些东西灵活而又可定制将会是非常困难的，如果一切都集
中到一个单一的实现类。

为了简化配置并启用灵活配置/可插性，Shiro的实现都是高度模块化设计——由于如此的模块化，SecurityManager实现（以及它的类层次结构）并没有做很多事情。相反，SecurityManager 实现主要是作为一个轻量级的“容器”组
件，委托计划所有的行为到嵌套/包裹的组件。这种“包装”的设计体现在上面的详细构架图。

虽然组件实际上执行逻辑，但SecurityManager实现知道何时以及如何协调组件来完成正确的行为。SecurityManager 实现和组件都是兼容JavaBean的，它允许你（或某个配置机制）通过标准的JavaBean的accessor/mutator 方法（get*/set*）轻松地自定义可拔插组件。这意味着 Shiro 的架构的组件性能够把自定义行为转化为非常容易的配置文件。

> **Easy Configuration**
> 
> 由于JavaBeans的兼容性，通过任何支持JavaBean风格的配置的机制可以很容易的用自定义组件配置SecurityManager，如 Spring，Guice，JBoss，等等。

> 本文摘自：《Apache Shiro 开发手册》
> 整编：Java技术栈（公众号id: javastack）

