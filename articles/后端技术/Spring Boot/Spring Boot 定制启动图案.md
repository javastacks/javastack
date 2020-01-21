
## 启动图案

Spring Boot在启动的时候会显示一个默认的Spring的图案，对应的类为SpringBootBanner。

```
.   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.5.6.RELEASE)
```

图案输出有以下几种模式，默认是CONSOLE的，即只打印到控制台，也可以输出到日志文件。

```
enum Mode {

	/**
	 * Disable printing of the banner.
	 */
	OFF,

	/**
	 * Print the banner to System.out.
	 */
	CONSOLE,

	/**
	 * Print the banner to the log file.
	 */
	LOG

}
```

## 关闭图案

```
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).bannerMode(Banner.Mode.OFF)
				.run(args);
	}
	
}
```

## 定制图案

很简单，只要在classpath目录下创建banner.txt即可，把图案放入该文件就行，这是Spring Boot默认的图案位置，Spring Boot会自动加载该文件显示图案。

生成图案的网站：http://patorjk.com

也可以使用图片，更详细的可以研究Banner接口及其子类，不过这也没什么卵用，有兴趣的可以深入了解下。


当然也支持通过application配置文件来定制图案。

```
# BANNER
banner.charset=UTF-8 # Banner file encoding.
banner.location=classpath:banner.txt # Banner file location.
banner.image.location=classpath:banner.gif # Banner image file location (jpg/png can also be used).
banner.image.width= # Width of the banner image in chars (default 76)
banner.image.height= # Height of the banner image in chars (default based on image height)
banner.image.margin= # Left hand image margin in chars (default 2)
banner.image.invert= # If images should be inverted for dark terminal themes (default false)
```
