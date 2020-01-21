## 常用注解

- Controller

注解一个类表示控制器，Spring MVC会自动扫描标注了这个注解的类。

- RequestMapping

请求路径映射，可以标注类，也可以是方法，可以指定请求类型，默认不指定为全部接收。

- RequestParam

放在参数前，表示只能接收参数a=b格式的数据，即`Content-Type`为`application/x-www-form-urlencoded`类型的内容。

- RequestBody

放在参数前，表示参数从request body中获取，而不是从地址栏获取，所以这肯定是接收一个POST请求的非a=b格式的数据，即`Content-Type`不为`application/x-www-form-urlencoded`类型的内容。

- ResponseBody

放在方法上或者返回类型前，表示此方法返回的数据放在response body里面，而不是跳转页面。一般用于ajax请求，返回json数据。

- RestController

这个是Controller和ResponseBody的组合注解，表示@Controller标识的类里面的所有返回参数都放在response body里面。

- PathVariable

路径绑定变量，用于绑定restful路径上的变量。

- @RequestHeader

放在方法参数前，用来获取request header中的参数值。

- @CookieValue;

放在方法参数前，用来获取request header cookie中的参数值。

- GetMapping PostMapping PutMapping..
*Mapping的是Spring4.3加入的新注解，表示特定的请求类型路径映射，而不需要写RequestMethod来指定请求类型。

## 演示


```
import org.dom4j.util.UserDataElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/get/{no}", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("no") String no) {
		return new UserDataElement("");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestBody UserDataElement user) {
		
	}

}
```

