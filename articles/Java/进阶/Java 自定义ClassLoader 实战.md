
假如我们的类不在classpath下，而我们又想读取一个自定义的目录下的class，如果做呢？

## 读取自定义目录的类

示例读取c:/test/com/test.jdk/Key.class这个类。

```
package com.test.jdk;

public class Key {
    private String key = "111111";
}
```

## 自定义ClassLoader

```
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalClassLoader extends ClassLoader {

    private String path = "c:/test/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cls = findLoadedClass(name);
        if (cls != null) {
            return cls;
        }

        if (!name.endsWith(".Key")) {
            return super.loadClass(name);
        }

        try {
            InputStream is = new FileInputStream(path + name.replace(".", "/") + ".class");
            byte[] bytes = IOUtils.toByteArray(is);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.loadClass(name);
    }
}
```

## 开始读取类

```
public static void main(String[] args) {
    try {
        LocalClassLoader lcl = new LocalClassLoader();
        Class<?> cls = lcl.loadClass("com.test.jdk.Key");
        Field field = FieldUtils.getField(cls, "key", true);
        Object value = field.get(cls.newInstance());
        System.out.println(value);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
自定义类加载器正常加载到类，程序最后输出：111111

## URLClassLoader

上面自定义一个类加载器来读取自定义的目录，其实可以直接使用URLClassLoader就能读取，它已经实现了路径下类的读取逻辑。

```
public static void main(String[] args) {
    try {
        URLClassLoader ucl = new URLClassLoader(new URL[]{new URL("c:/test/")});
        Class<?> cls = ucl.loadClass("com.test.jdk.Key");
        Field field = FieldUtils.getField(cls, "key", true);
        Object value = field.get(cls.newInstance());
        System.out.println(value);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

