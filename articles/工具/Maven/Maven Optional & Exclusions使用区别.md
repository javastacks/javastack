
Optional和Exclusions都是用来排除jar包依赖使用的，两者在使用上却是相反。

Optional定义后，该依赖只能在本项目中传递，不会传递到引用该项目的父项目中，父项目需要主动引用该依赖才行。

Exclusions则是主动排除子项目传递过来的依赖。

##### 用法区别

> Project-X -> Project-A\
> Project-A -> Project-B

```
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>sample.ProjectB</groupId>
      <artifactId>Project-B</artifactId>
      <version>1.0</version>
      <scope>compile</scope>
      <optional>true</optional>
    </dependency>
  </dependencies>
</project>
```

如上X依赖A，A依赖B用的`<optional>true</optional>`，这时B只能在A中使用，而不会主动传递到X中，X需要主动引用B才有B的依赖。

如果A不用`<optional>true</optional>`引用B，则会传递到X中，X如果不需要B则需要主动排除A传递过来的B。

```
<dependencies>
    <dependency>
      <groupId>sample.ProjectA</groupId>
      <artifactId>Project-A</artifactId>
      <version>1.0</version>
      <scope>compile</scope>
      <exclusions>
        <exclusion>
          <groupId>sample.ProjectB</groupId>
          <artifactId>Project-B</artifactId>
        </exclusion>
      </exclusions> 
    </dependency>
</dependencies>
```

所以，Optional和Exclusions都是用来控制依赖的传递，可以根据实际场景灵活应用。

更多具体的定义即说明可参考官方说明文档：

> http://maven.apache.org/guides/introduction/introduction-to-optional-and-excludes-dependencies.html
