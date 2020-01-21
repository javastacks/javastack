![image](http://img.javastack.cn/18-1-31/1092928.jpg)
**2018/01/31，Spring Boot团队发布了Spring Boot 1.5.10。**

**Maven：**

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.10.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

**Gradle：**

```
dependencies {
    compile("org.springframework.boot:spring-boot-starter-web:1.5.10.RELEASE")
}
```

Spring Boot 1.5.10 已经正式提交到了以下仓库中。

> https://repo.spring.io/release\
> https://search.maven.org/

**这个版本主要修复了一个重要安全漏洞（CVE-2018-1196）！！！**

这个漏洞会威胁到所有使用Spring Boot的系统，这个漏洞的披露详情将很快公布。

**此外，该版本还将依赖的Spring Security包升级到了最新版本（修复了漏洞CVE-2017-8030）。**

CVE-2017-8030漏洞可见说明：
> https://pivotal.io/security/cve-2017-8030

**除了安全漏洞修复，Spring Boot 1.5.10还修复了超过55+的bug，其他改善，及一些依赖包的版本更新。**

大家可以在Spring Boot官网看到最新的发布版本。

> https://projects.spring.io/spring-boot/

