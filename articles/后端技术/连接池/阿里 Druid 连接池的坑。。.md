## Druid的坑

当查询数据库的Clob转换为Oracle Clob类型的时候。

```
java.lang.ClassCastException: com.alibaba.druid.proxy.jdbc.ClobProxyImpl cannot be cast to oracle.sql.CLOB
```

## 问题原因

ClobProxyImpl不能转换为Oracle的Clob字段，这也是醉了。

原因是Druid为Clob字段增加了代理类：com.alibaba.druid.proxy.jdbc.ClobProxyImpl，然后代码里面强制转换成Oracle的Clob就出现了这个问题。

## 解决方案

现在的解决方案是先转换为Druid的代理类ClobProxy对象，然后获取原生的Oracle Clob字段内容即可。

```
public class ClobUtil {

	public static CLOB parseOracleClob(Clob clob) {
		SerializableClob sclob = (SerializableClob) clob;
		Clob wrappedClob = sclob.getWrappedClob();

		// 解决Druid的坑
		if (wrappedClob instanceof ClobProxy) {
			ClobProxy clobProxy = (ClobProxy) wrappedClob;
			wrappedClob = clobProxy.getRawClob();
		}

		return (CLOB) wrappedClob;
	}

}
```

