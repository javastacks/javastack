![image](http://img.javastack.cn/18-1-20/16848962.jpg)

现在大多项目都是使用Mybatis了，但也有些公司使用Hibernate。使用Mybatis最大的特性就是sql需要自己写，而写sql就需要传递多个参数。面对各种复杂的业务场景，传递参数也是一种学问。

**下面给大家总结了以下几种多参数传递的方法。**

**方法1：顺序传参法**

```
public User selectUser(String name, int deptId);

<select id="selectUser" resultMap="UserResultMap">
    select * from user
    where user_name = #{0} and dept_id = #{1}
</select>
```

`#{}`里面的数字代表你传入参数的顺序。

这种方法不建议使用，sql层表达不直观，且一旦顺序调整容易出错。

**方法2：@Param注解传参法**

```
public User selectUser(@Param("userName") String name, int @Param("deptId") deptId);

<select id="selectUser" resultMap="UserResultMap">
    select * from user
    where user_name = #{userName} and dept_id = #{deptId}
</select>
```

`#{}`里面的名称对应的是注解`@Param`括号里面修饰的名称。

这种方法在参数不多的情况还是比较直观的，推荐使用。

**方法3：Map传参法**

```
public User selectUser(Map<String, Object> params);

<select id="selectUser" parameterType="java.util.Map" resultMap="UserResultMap">
    select * from user
    where user_name = #{userName} and dept_id = #{deptId}
</select>
```

`#{}`里面的名称对应的是`Map`里面的key名称。

这种方法适合传递多个参数，且参数易变能灵活传递的情况。

**方法4：Java Bean传参法**

```
public User selectUser(Map<String, Object> params);

<select id="selectUser" parameterType="com.test.User" resultMap="UserResultMap">
    select * from user
    where user_name = #{userName} and dept_id = #{deptId}
</select>
```

`#{}`里面的名称对应的是`User`类里面的成员属性。

这种方法很直观，但需要建一个实体类，扩展不容易，需要加属性，看情况使用。

有收获的话，分享下朋友圈给更多的人吧！

