Nexus2可以通过管理界面来上传jar包到私库中，而最新的Nexus3却找不到了上传界面，只能通过以下方式来发布到私库。

## 发布第三方jar包

这种情况是maven远程仓库没有，本地有的第三方jar包，需要发布到私库。

```
mvn deploy:deploy-file 
  -DgroupId=com.example
  -DartifactId=test
  -Dversion=0.0.1
  -Dpackaging=jar
  -Dfile=E:\workspace\test\WebRoot\WEB-INF\lib\test-0.0.1.jar
  -Durl=http://nexus.example.com:8081/repository/3rd-repo/
  -DrepositoryId=Nexus
```

注意file的目录不能和本地仓库目录一致，不然会报错。

## 发布自有项目

公司自己的项目需要发布到Nexus私库提供给其他人依赖，这时可以用上面的方式先打成jar包再发布，也可以使用maven的eclipse插件。

添置maven pom配置：


```
<distributionManagement>
	<repository>
		<id>Nexus</id>
		<name>Releases</name>
		<url>http://nexus.example.com:8081/repository/maven-releases</url>
	</repository>
	<snapshotRepository>
		<id>Nexus</id>
		<name>Snapshot</name>
		<url>http://nexus.example.com:8081/repository/maven-snapshots</url>
	</snapshotRepository>
</distributionManagement>
```


再在eclipse项目上使用Run As..>Maven build..，在Goals里面输入deploy发布即可。

## Maven settings配置

以上两点其中Nexus指的是maven server的id，大小写敏感，增加以下配置。

```
<servers>

    <server><id>Nexus</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
    
</servers>
```

关于Maven的使用及私库的搭建可以翻阅之前的Maven精选系列文章。