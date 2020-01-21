
## Cookie属性


了解这几个属性之后，就知道如何进行Cookie的操作了。

#### name

Cookie的名称。

#### value

Cookie的值。

#### maxAge

Cookie的失效时间，有以下几种值，默认为-1

值 | 说明
---|---
负数 | 浏览器关闭后cookie就失效
0 | 马上清除cookie
正数 | 设置过期时间，单位：秒

#### path

Cookie的有效路径，`/`表示这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问。


## 获取所有Cookie


```
public static Cookie[] getCookies(HttpServletRequest request) {
	return request.getCookies();
}
```

获取cookie很简单，直接从request中获取即可。

## 根据名称获取指定Cookie


```
public static Cookie getCookieByName(HttpServletRequest request, String name) {
	if (StringUtils.isBlank(name)) {
		return null;
	}
	Cookie[] cookies = getCookies(request);
	if (null != cookies) {
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
	}
	return null;
}
```

从所有cookie中循环判断进行获取指定cookie。

## 添加Cookie


```
public static boolean addCookie(HttpServletResponse response, String name, String value,
			int maxAge) {
	if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
		return false;
	}
	Cookie cookie = new Cookie(name.trim(), value.trim());
	if (maxAge <= 0) {
		maxAge = Integer.MAX_VALUE;
	}
	cookie.setMaxAge(maxAge);
	cookie.setPath("/");
	response.addCookie(cookie);
	return true;
}

```
这个添加很简单。

## 删除Cookie


```
public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response,
			String name) {
	if (StringUtils.isBlank(name)) {
		return false;
	}
	Cookie[] cookies = getCookies(request);
	if (null != cookies) {
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				return true;
			}

		}
	}
	return false;
}
```

删除cookie，把`value`设置为null，把`max-age`设置为0就行了。

## 注意

编辑操作和删除操作一样，但是需要注意的是修改、删除Cookie时，除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。 

