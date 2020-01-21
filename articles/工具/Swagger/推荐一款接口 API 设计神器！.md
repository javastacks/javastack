今天栈长给大家推荐一款接口 API 设计神器，传说中的，牛逼哄洪的 Swagger，它到底是什么？今天为大家揭开谜底！

## Swagger是什么？

> 官网：https://swagger.io/

![](http://img.javastack.cn/18-10-25/30041.jpg)

Swagger 如官网所示，它是最好的 API 构建工具。

它是一个围绕 OpenAPI 规范构建的开源工具，它可以帮助我们设计、构建、记录和使用 REST API 接口。

Swagger 包含的主要套件：

- Swagger Editor - 基于浏览器的编辑器，用来编写 OpenAPI 规范。
- Swagger UI - 基于 OpenAPI 规范动态生成 API 规范文档。
- Swagger Codegen - 个模板驱动引擎，用来生成客户端代码。

![](http://img.javastack.cn/18-10-25/15444134.jpg)

图片来源见博客水印。

## OpenAPI是什么？

上面有说到 Swagger 是一个围绕 OpenAPI 规范构建的开源工具，那么 OpenAPI 是什么呢？

OpenAPI 规范，以前叫 Swagger 规范。它是一个为 REST APIs的接口定义的规范。OpenAPI 可以定义的 API 实体内容包括以下几个部分。

- 请求地址（如：/user）
- 请求类型（如：GET、POST 等）
- 请求参数
- 响应参数
- 验证方式
- 文档信息：如联系人、许可证、服务条件等

这个 OpenAPI 规范可以用 YAML 或者 JSON 来编写，这种格式非常易于学习，可读性对开发人员非常友好。

完整的 OpenAPI 规范可以去官网看一下。

> https://github.com/OAI/OpenAPI-Specification

编写文档地址：

> http://editor.swagger.io/

![](http://img.javastack.cn/18-10-25/51053663.jpg)

## 为什么需要Swagger？

现在的互联网架构都是前后端分离的模式，还有现在是移动互联网时代了，APP 需要与后端服务器通信也需要维护一套接口，API文档自然就成了前后端开发人员联系的纽带。

编写 API 文档的方式也各有不同，有用 WORD 编写的，有用 confluence 等编写的，但这些方式都不能动态更新，每次接口变更都需要手动维护文档，甚是麻烦。有了 Swagger，可以先做完接口，通过 Swagger 来动态生成和更新 API 文档。

后面的文章会继续介绍如何使用 Swagger 注解来自动生成 API 文档，及如何集成 Spring Boot 来应用实战，关注Java技术栈微信公众号，在后台回复关键字 "工具" 可获取所有历史  Java 工具类文章教程及更新。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "工具" 可获取更多，转载请原样保留本信息。