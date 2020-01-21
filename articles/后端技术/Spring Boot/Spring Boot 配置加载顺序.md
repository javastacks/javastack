如果加载的配置有重复的，它们的加载顺序是这样的，数字越小的优先级越高，即优先级高的覆盖优先级低的配置。

1. Devtools global settings properties on your home directory (~/.spring-boot-devtools.properties when devtools is active).
1. @TestPropertySource annotations on your tests.
1. @SpringBootTest#properties annotation attribute on your tests.
1. Command line arguments.
1. Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property)
1. ServletConfig init parameters.
1. ServletContext init parameters.
1. JNDI attributes from java:comp/env.
1. Java System properties (System.getProperties()).
1. OS environment variables.
1. A RandomValuePropertySource that only has properties in random.*.
1. Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants)
1. Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants)
1. Application properties outside of your packaged jar (application.properties and YAML variants).
1. Application properties packaged inside your jar (application.properties and YAML variants).
1. @PropertySource annotations on your @Configuration classes.
1. Default properties (specified using SpringApplication.setDefaultProperties).

知道了它们的加载顺序，我们就能知道从哪一步来替换配置。