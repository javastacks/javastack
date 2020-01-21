## 添加web功能启动器

添加了Spring Boot基础依赖后，如要使用web mvc功能，只需要添加如下启动器即可，Spring Boot会自动装配web功能。


```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

## 添加启动类

然后添加启动类，建议放在根包下，这里把Controller和启动类放在一起也是没问题的，实际请分包出来。

启动类必须要有main方法，并添加启动方法。

```
package hello;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
```

## 添加编译打包插件

```
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<executions>
				<execution>
					<goals>
						<goal>repackage</goal>
					</goals>
					<configuration>
						<classifier>exec</classifier>
						<fork>true</fork>
						<excludeDevtools>true</excludeDevtools>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

## 运行Spring Boot的3种方式

1. 运行启动类的main方法。
2. 使用spring-boot:run命令。
3. 打成jar包后使用java -jar xx.jar命令。

Spring Boot默认的端口是8080，可以通过server.port=8081来修改，或者通过命令行指定也行。

启动工程后，访问localhost:8080/就能输出：Hello World!


## Spring Boot快速构建方式

上面是通过maven手动创建工程的方式添加Spring Boot应用 ，当然新手也可以打开Spring Boot的快速构建网站http://start.spring.io/，来选择对应的启动器生成项目，最后导入进来即可。