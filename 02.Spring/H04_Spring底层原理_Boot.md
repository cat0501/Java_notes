# Boot

## 第三十九讲 Boot 启动流程

```java
@Configuration
public class A39_1 {

    public static void main(String[] args) throws Exception {
    }
}
```





### 构造

```java
System.out.println("1. 演示获取 Bean Definition 源");
SpringApplication spring = new SpringApplication(A39_1.class);
spring.setSources(Set.of("classpath:b01.xml"));

ConfigurableApplicationContext context = spring.run(args);
for (String name : context.getBeanDefinitionNames()) {
    System.out.println("name: " + name + " 来源：" + context.getBeanFactory().getBeanDefinition(name).getResourceDescription());
}
```



Spring源码

```java
static WebApplicationType deduceFromClasspath() {
  if (ClassUtils.isPresent(WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent(WEBMVC_INDICATOR_CLASS, null)
      && !ClassUtils.isPresent(JERSEY_INDICATOR_CLASS, null)) {
    return WebApplicationType.REACTIVE;
  }
  for (String className : SERVLET_INDICATOR_CLASSES) {
    if (!ClassUtils.isPresent(className, null)) {
      return WebApplicationType.NONE;
    }
  }
  return WebApplicationType.SERVLET;
}
```

测试代码

```java
System.out.println("2. 演示推断应用类型");
Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
deduceFromClasspath.setAccessible(true);
System.out.println("\t应用类型为:"+deduceFromClasspath.invoke(null));
```





```java
System.out.println("3. 演示 ApplicationContext 初始化器");
spring.addInitializers(applicationContext -> {
    if (applicationContext instanceof GenericApplicationContext gac) {
        gac.registerBean("bean3", Bean3.class);
    }
});
```



```java
setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
```



```java
System.out.println("4. 演示监听器与事件");
spring.addListeners(event -> System.out.println("\t事件为:" + event.getClass()));
```





### run

1. 得到 SpringApplicationRunListeners，即事件发布器
   - 发布 application starting 事件1️⃣
2. 封装启动 args
3. 准备 Environment 添加命令行参数（*）
4. ConfigurationPropertySources 处理（*）
   - 发布 application environment 已准备事件2️⃣
5. 通过 EnvironmentPostProcessorApplicationListener 进行 env 后处理（*）
   * application.properties，由 StandardConfigDataLocationResolver 解析
   * spring.application.json
6. 绑定 spring.main 到 SpringApplication 对象（*）
7. 打印 banner（*）
8. 创建容器
9. 准备容器
   - 发布 application context 已初始化事件3️⃣
10. 加载 bean 定义

    * 发布 application prepared 事件4️⃣
11. refresh 容器

    * 发布 application started 事件5️⃣

12. 执行 runner

    * 发布 application ready 事件6️⃣

    * 这其中有异常，发布 application failed 事件7️⃣

> 带 * 的有独立的示例







## 第四十讲 Tomcat 内嵌容器

```
Server
└───Service
    ├───Connector (协议, 端口)
    └───Engine
        └───Host(虚拟主机 localhost)
            ├───Context1 (应用1, 可以设置虚拟路径, / 即 url 起始路径; 项目磁盘路径, 即 docBase )
            │   │   index.html
            │   └───WEB-INF
            │       │   web.xml (servlet, filter, listener) 3.0
            │       ├───classes (servlet, controller, service ...)
            │       ├───jsp
            │       └───lib (第三方 jar 包)
            └───Context2 (应用2)
                │   index.html
                └───WEB-INF
                        web.xml
```







## 第四十一讲 自动配置

### AopAutoConfiguration

Spring Boot 是利用了自动配置类来简化了 aop 相关配置

- AOP 自动配置类为 `org.springframework.boot.autoconfigure.aop.AopAutoConfiguration`
- 可以通过 `spring.aop.auto=false` 禁用 aop 自动配置
- AOP 自动配置的本质是通过 `@EnableAspectJAutoProxy` 来开启了自动代理，如果在引导类上自己添加了 `@EnableAspectJAutoProxy` 那么以自己添加的为准





### MybatisAutoConfiguration

- MyBatis 自动配置类为 `org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration`





































