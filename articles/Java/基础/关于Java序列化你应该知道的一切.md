
## 什么是序列化

我们的对象并不只是存在内存中，还需要传输网络，或者保存起来下次再加载出来用，所以需要Java序列化技术。

Java序列化技术正是将对象转变成一串由二进制字节组成的数组，可以通过将二进制数据保存到磁盘或者传输网络，磁盘或者网络接收者可以在对象的属类的模板上来反序列化类的对象，达到对象持久化的目的。

## 怎么序列化一个对象？

要序列化一个对象，这个对象所在类就必须实现Java序列化的接口：java.io.Serializable。

##### 1、类添加序列化接口
```
import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = -8475669200846811112L;

    private String username;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
}
```

##### 2、序列化/反序列化

可以借助commons-lang3工具包里面的类实现对象的序列化及反序列化，你没有必要自己写。

```
import org.apache.commons.lang3.SerializationUtils;

public class Test {

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Java");
        user.setAddress("China");
        byte[] bytes = SerializationUtils.serialize(user);

        User u = SerializationUtils.deserialize(bytes);
        System.out.println(u);

    }

}
```
输出：
```
User{username='Java', address='China'}
```
上例通过序列化对象字节到内存然后反序列化，当然里面也提供了序列化磁盘然后再反序列化的方法，原理都是一样的，只是目标地不一样。

## 序列化注意事项

- 序列化对象必须实现序列化接口。

- 序列化对象里面的属性是对象的话也要实现序列化接口。

- 类的对象序列化后，类的序列化ID不能轻易修改，不然反序列化会失败。

- 类的对象序列化后，类的属性有增加或者删除不会影响序列化，只是值会丢失。

- 如果父类序列化了，子类会继承父类的序列化，子类无需添加序列化接口。

- 如果父类没有序列化，子类序列化了，子类中的属性能正常序列化，但父类的属性会丢失，不能序列化。

- 用Java序列化的二进制字节数据只能由Java反序列化，不能被其他语言反序列化。如果要进行前后端或者不同语言之间的交互一般需要将对象转变成Json/Xml通用格式的数据，再恢复原来的对象。

