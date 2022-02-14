package cn.javastack.test.jdk.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 女朋友类
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class GirlFriend implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

/**
 * 创建对象的 5 种方式
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ObjectGenerator {


    /**
     * new一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend1() {
        GirlFriend girlFriend = new GirlFriend("new一个对象");
        System.out.println(girlFriend);
    }

    /**
     * 克隆一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend2() throws CloneNotSupportedException {
        GirlFriend girlFriend1 = new GirlFriend("克隆一个对象");
        GirlFriend girlFriend2 = (GirlFriend) girlFriend1.clone();
        System.out.println(girlFriend2);
    }

    /**
     * 类派发一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend3() throws InstantiationException, IllegalAccessException {
        GirlFriend girlFriend = GirlFriend.class.newInstance();
        girlFriend.setName("类派发一个对象");
        System.out.println(girlFriend);
    }

    /**
     * 反射一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend4() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        GirlFriend girlFriend = (GirlFriend) Class.forName("cn.javastack.test.jdk.core.GirlFriend").newInstance();
        girlFriend.setName("反射一个对象");
        System.out.println(girlFriend);
    }

    /**
     * 构造一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend5() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GirlFriend girlFriend = GirlFriend.class.getConstructor().newInstance();
        girlFriend.setName("构造一个对象");
        System.out.println(girlFriend);
    }

    /**
     * 反序列化一个对象
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void girlFriend6() throws IOException, ClassNotFoundException {
        GirlFriend girlFriend1 = new GirlFriend("反序列化一个对象");

        // 序列化一个女朋友
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("gf.obj"));
        objectOutputStream.writeObject(girlFriend1);
        objectOutputStream.close();

        // 反序列化出来
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("gf.obj"));
        GirlFriend girlFriend2 = (GirlFriend) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(girlFriend2);
    }


}