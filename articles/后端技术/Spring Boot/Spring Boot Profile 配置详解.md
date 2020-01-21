## Profile是什么

Profile我也找不出合适的中文来定义，简单来说，Profile就是Spring Boot可以对不同环境或者指令来读取不同的配置文件。

## Profile使用

假如有开发、测试、生产三个不同的环境，需要定义三个不同环境下的配置。

#### 基于properties文件类型

你可以另外建立3个环境下的配置文件：

applcation.properties\
application-dev.properties\
application-test.properties\
application-prod.properties

然后在applcation.properties文件中指定当前的环境：
spring.profiles.active=test\
这时候读取的就是application-test.properties文件。

#### 基于yml文件类型

只需要一个applcation.yml文件就能搞定，推荐此方式。

```
spring:
  profiles: 
    active: prod

---
spring: 
  profiles: dev  
  
server: 
  port: 8080  
  
---
spring: 
  profiles: test  
  
server: 
  port: 8081    
  
---
spring.profiles: prod
spring.profiles.include:
  - proddb
  - prodmq
  
server: 
  port: 8082      
  
---
spring: 
  profiles: proddb  
  
db:
  name: mysql   
  
---
spring: 
  profiles: prodmq   

mq: 
  address: localhost
```
此时读取的就是prod的配置，prod包含proddb,prodmq，此时可以读取proddb,prodmq下的配置。    

也可以同时激活三个配置。

```
spring.profiles.active: prod,proddb,prodmq
```

#### 基于Java代码

在JAVA配置代码中也可以加不同Profile下定义不同的配置文件，@Profile注解只能组合使用@Configuration和@Component注解。

```
@Configuration
@Profile("prod")
public class ProductionConfiguration {

    // ...

}
```

## 指定Profile

#### main方法启动方式：

```
// 在Eclipse Arguments里面添加
--spring.profiles.active=prod
```


#### 插件启动方式：

```
spring-boot:run -Drun.profiles=prod
```


#### jar运行方式：

```
java -jar xx.jar --spring.profiles.active=prod
```

除了在配置文件和命令行中指定Profile，还可以在启动类中写死指定，通过SpringApplication.setAdditionalProfiles方法。

SpringApplication.class


```
public void setAdditionalProfiles(String... profiles) {
	this.additionalProfiles = new LinkedHashSet<String>(Arrays.asList(profiles));
}
```
