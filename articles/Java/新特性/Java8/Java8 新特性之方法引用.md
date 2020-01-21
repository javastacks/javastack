
## 什么是方法引用

方法引用是只需要使用方法的名字，而具体调用交给函数式接口，需要和Lambda表达式配合使用。

如：


```
List<String> list = Arrays.asList("a","b","c");
list.forEach(str -> System.out.print(str));
list.forEach(System.out::print);
```

上面两种写法是等价的。


## 方法引用分类

### 1、构造器方法引用

格式：`Class::new`，调用默认构造器。

### 2、类静态方法引用

格式：`Class::static_method`

### 3、类普通方法引用

格式：`Class::method`，方法不能带参数。

### 4、实例方法引用

格式：`instance::method`

## 示例

往User类添加方法引用方法：


```
public static User create(Supplier<User> supplier){
	return supplier.get();
}

public static void updateUsername(User user){
	user.setUsername(user.getUsername() + " updated.");
}

public void updateAge(){
	this.setAge(this.getAge() + 10);
}

public void changeAge(User user){
	user.setAge(user.getAge() + 10);
}
```

方法引用测试：

```
public static void main(String[] args) {
	List<User> list = initList();

	// 1、构造器方法引用
	User newUser = User.create(User::new);
	newUser.setAge(1);
	newUser.setUsername("new");
	System.out.println(newUser);

	// 2、类静态方法引用
	list.forEach(User::updateUsername);

	// 3、类普通方法引用
	list.forEach(User::updateAge);

	// 4、实例方法引用
	User user = new User();
	list.forEach(user::changeAge);

	list.forEach(System.out::println);
}

private static List<User> initList() {
	List<User> list = new ArrayList<>();
	list.add(new User("oaby", 23));
	list.add(new User("tom", 11));
	list.add(new User("john", 16));
	list.add(new User("jennis", 26));
	list.add(new User("tin", 26));
	list.add(new User("army", 26));
	list.add(new User("mack", 19));
	list.add(new User("jobs", 65));
	list.add(new User("jordan", 23));
	return list;
}
```
输出结果：

```
User [username=new, age=1]
User [username=oaby updated., age=43]
User [username=tom updated., age=31]
User [username=john updated., age=36]
User [username=jennis updated., age=46]
User [username=tin updated., age=46]
User [username=army updated., age=46]
User [username=mack updated., age=39]
User [username=jobs updated., age=85]
User [username=jordan updated., age=43]
```

可以看出方法引用都生效了，username和age都相应更新了。
