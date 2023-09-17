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

