
## MongoDB启动器

加入Spring Boot依赖后，添加MongoDB启动器即可。

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

## MongoDB配置

这是MongoDB自动配置的所有参考配置：

```
# MONGODB (MongoProperties)
spring.data.mongodb.authentication-database= # Authentication database name.
spring.data.mongodb.database=test # Database name.
spring.data.mongodb.field-naming-strategy= # Fully qualified name of the FieldNamingStrategy to use.
spring.data.mongodb.grid-fs-database= # GridFS database name.
spring.data.mongodb.host=localhost # Mongo server host. Cannot be set with uri.
spring.data.mongodb.password= # Login password of the mongo server. Cannot be set with uri.
spring.data.mongodb.port=27017 # Mongo server port. Cannot be set with uri.
spring.data.mongodb.reactive-repositories.enabled=true # Enable Mongo reactive repositories.
spring.data.mongodb.repositories.enabled=true # Enable Mongo repositories.
spring.data.mongodb.uri=mongodb://localhost/test # Mongo database URI. Cannot be set with host, port and credentials.
spring.data.mongodb.username= # Login user of the mongo server. Cannot be set with uri.
```

下面是参考配置，连接本地的dev数据库。

```
spring: 
  data:
      mongodb:
        host: 127.0.0.1
        port: 27017
        database: dev
```

## MongoDB的使用

**1、继承MongoRepository**


```
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IpCodeRepository extends MongoRepository<IpCode, String> {

}
```


**2、注入MongoTemplate**


```
@Autowired
private MongoTemplate mongoTemplate;
```

第一种是一种简单的使用方式，高级查询需要使用第二种。

