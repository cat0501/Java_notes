# 容器与Bean



## 第一讲 BeanFactory 与 ApplicationContext 的功能

- BeanFactory 能做哪些事

- ApplicationContext 有哪些扩展功能

- 事件解耦



```java
ConfigurableApplicationContext context = SpringApplication.run(A01.class, args);
```





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220603112156589.png)





- （1）到底什么是 BeanFactory
  - 它是 ApplicationContext 的父接口
  - 它才是 Spring 的核心容器, 主要的 ApplicationContext 实现都【组合】了它的功能（BeanFactory是ApplicationContext的一个成员变量）

- （2）BeanFactory 能干点啥
  - 表面上只有 `getBean`
  - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供（DefaultListableBeanFactory）





```java
public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    ConfigurableApplicationContext context = SpringApplication.run(A01.class, args);
    System.out.println(context);
    Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
    singletonObjects.setAccessible(true);
    
    ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
    
    map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
            .forEach(e -> {
                System.out.println(e.getKey() + "=" + e.getValue());
            });
}

// 注册的2个bean
@Component
public class Component1 {
    private static final Logger log = LoggerFactory.getLogger(Component1.class);
}

@Component
public class Component2 {
    private static final Logger log = LoggerFactory.getLogger(Component2.class);
}
```

打印结果

```bash
component1=com.itheima.a01.Component1@4d8539de
component2=com.itheima.a01.Component2@3eba57a7
```

- （3）ApplicationContext 比 BeanFactory 多点啥（扩展的功能主要体现在继承的4个父接口上）⭐️
  - 多语言能力（国际化能力）
  
    ```java
    System.out.println(context.getMessage("hi", null, Locale.CHINA));
    System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
    System.out.println(context.getMessage("hi", null, Locale.JAPANESE));
    ```
  
    存在的未解决的问题
  
    Exception in thread "main" org.springframework.context.NoSuchMessageException: No message found under code 'hi' for locale 'zh_CN'.
  
  - 通配符匹配资源的能力
  
    ```java
    Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
    for (Resource resource : resources) {
      	System.out.println(resource);
    }
    ```
  
    打印结果：
  
    ```java
    URL [jar:file:/Users/cat/environment/repo/org/springframework/boot/spring-boot/2.5.5/spring-boot-2.5.5.jar!/META-INF/spring.factories]
    URL [jar:file:/Users/cat/environment/repo/org/springframework/boot/spring-boot-autoconfigure/2.5.5/spring-boot-autoconfigure-2.5.5.jar!/META-INF/spring.factories]
    URL [jar:file:/Users/cat/environment/repo/org/springframework/spring-beans/5.3.10/spring-beans-5.3.10.jar!/META-INF/spring.factories]
    ```
  
  - 发布事件对象（如实现用户注册和发布事件的解耦）
  
    ```java
    @EventListener
    public void aaa(UserRegisteredEvent event) {
        log.debug("{}", event);
        log.debug("发送短信");
    }
    ```
  
    工作中的使用如下：[基于自定义注解、AOP和Spring事件发布实现日志记录](https://blog.51cto.com/u_15473389/5356183)
  
  - 环境信息
  
    ```java
    System.out.println(context.getEnvironment().getProperty("JAVA_8_HOME"));
    System.out.println(context.getEnvironment().getProperty("JAVA_11_HOME"));
    System.out.println(context.getEnvironment().getProperty("spring.messages.basename"));
    ```
  
    打印结果
  
    ```java
    /Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home
    /Library/Java/JavaVirtualMachines/jdk-11.0.14.jdk/Contents/Home
    messages://messages_zh.properties
    ```
  
    



<br>

总结一下：

a.  BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系, ApplicationContext 组合并扩展了 BeanFactory 的功能

b.  又新学一种代码之间解耦途径



## 第二讲 BeanFactory 与 ApplicationContext 的实现

- BeanFactory 实现的特点

- ApplicationContext 的常见实现和用法
- 内嵌容器、注册 DispatcherServlet

### BeanFactory实现（了解DefaultListableBeanFactory就够了）

原始功能并不丰富，扩展功能是通过【后处理器】完成。

```java
public static void main(String[] args) {
  DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
  // step1:bean 的定义（class, scope, 初始化, 销毁）
  AbstractBeanDefinition beanDefinition =
    BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
  // step2:bean 的注册
  beanFactory.registerBeanDefinition("config", beanDefinition);

  // 给 BeanFactory 添加一些常用的后处理器（解析@Configuration、@Bean ，扩展了beanFactory）
  AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

  // 第一类处理器：BeanFactory 后处理器主要功能，补充了一些 bean 定义
  beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
    // 这里是执行bean工厂后处理器
    beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
  });

  //for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
  //    System.out.println(beanDefinitionName);
  //}

  System.out.println("---------------------> p9");
  //System.out.println(beanFactory.getBean(Bean1.class).getBean2());
  // null
  // 原因：@Autowired对于beanFactory也属于扩展功能

  // 第二类处理器：Bean 后处理器, 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
  beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

  //for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
  //    System.out.println(beanDefinitionName);
  //}

  System.out.println("添加 Bean 后处理器后...");
  System.out.println(beanFactory.getBean(Bean1.class).getBean2());

  // 上面我们看到的bean都是延时加载的，需要用到时才创建
  // 我们可以预先实例化所有的单例对象
  beanFactory.preInstantiateSingletons(); // 准备好所有单例
}

@Configuration
static class Config {
    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }
    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }
    @Bean
    public Bean3 bean3() {
        return new Bean3();
    }
    @Bean
    public Bean4 bean4() {
        return new Bean4();
    }
}

interface Inter {}
static class Bean3 implements Inter {
}
static class Bean4 implements Inter {
}

static class Bean1 {
    private static final Logger log = LoggerFactory.getLogger(Bean1.class);

    public Bean1() {
        log.debug("构造 Bean1()");
    }

    @Autowired
    private Bean2 bean2;
    public Bean2 getBean2() {
        return bean2;
    }

    @Autowired
    @Resource(name = "bean4")
    private Inter bean3;
    public Inter getInter() {
        return bean3;
    }
}

static class Bean2 {
    private static final Logger log = LoggerFactory.getLogger(Bean2.class);

    public Bean2() {
        log.debug("构造 Bean2()");
    }
}
```

打印结果

```java
---------------------> p9
config
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
bean1
bean2
bean3
bean4
添加 Bean 后处理器后...
23:03:28.128 [main] DEBUG com.itheima.a02.TestBeanFactory$Bean1 - 构造 Bean1()
23:03:28.142 [main] DEBUG com.itheima.a02.TestBeanFactory$Bean2 - 构造 Bean2()
com.itheima.a02.TestBeanFactory$Bean2@52aa2946  
```

a. beanFactory 不会做的事

- （1）不会主动调用 BeanFactory 后处理器
- （2）不会主动添加 Bean 后处理器
- （3）不会主动初始化单例
- （4）不会解析beanFactory 还不会解析 ${ } 与 #{ }



b. bean 后处理器会有排序的逻辑

```java
beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanPostProcessor -> {
  System.out.println(">>>>" + beanPostProcessor);
  beanFactory.addBeanPostProcessor(beanPostProcessor);
});
```

结果

- AutowiredAnnotationBeanPostProcessor 后处理器先解析@Autowired注解
- CommonAnnotationBeanPostProcessor 后处理器后解析@Resource注解

```bash
>>>>org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@1e9e725a
>>>>org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@15d9bc04
```



### ApplicationContext 的实现（常见四个）

#### ClassPathXmlApplicationContext（了解）



```java
// ⬇️较为经典的容器, 基于 classpath 下 xml 格式的配置文件来创建
private static void testClassPathXmlApplicationContext() {
  ClassPathXmlApplicationContext context =
    new ClassPathXmlApplicationContext("a02.xml");

  for (String name : context.getBeanDefinitionNames()) {
    System.out.println(name);
  }

  System.out.println(context.getBean(Bean2.class).getBean1());
}
```



#### FileSystemXmlApplicationContext

```java
// ⬇️基于磁盘路径下 xml 格式的配置文件来创建
private static void testFileSystemXmlApplicationContext() {
    FileSystemXmlApplicationContext context =
            new FileSystemXmlApplicationContext(
                    "src\\main\\resources\\a02.xml");
    for (String name : context.getBeanDefinitionNames()) {
        System.out.println(name);
    }
    System.out.println(context.getBean(Bean2.class).getBean1());
}
```





#### AnnotationConfigApplicationContext

```java
// ⬇️较为经典的容器, 基于 java 配置类来创建
private static void testAnnotationConfigApplicationContext() {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config.class)
    for (String name : context.getBeanDefinitionNames()) {
        System.out.println(name);
    }
    System.out.println(context.getBean(Bean2.class).getBean1());
}
```





#### AnnotationConfigServletWebServerApplicationContext

```java
// ⬇️较为经典的容器, 基于 java 配置类来创建, 用于 web 环境
private static void testAnnotationConfigServletWebServerApplicationContext() {
  AnnotationConfigServletWebServerApplicationContext context =
    new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
  for (String name : context.getBeanDefinitionNames()) {
    System.out.println(name);
  }
}

@Configuration
static class WebConfig {
  @Bean
  public ServletWebServerFactory servletWebServerFactory(){
    return new TomcatServletWebServerFactory();
  }
  @Bean
  public DispatcherServlet dispatcherServlet() {
    return new DispatcherServlet();
  }
  @Bean
  public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet) {
    return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
  }
  @Bean("/hello")
  public Controller controller1() {
    return (request, response) -> {
      response.getWriter().print("hello");
      return null;
    };
  }
}
```



<br>

总结一下

- a. 常见的 ApplicationContext 容器实现
- b. 内嵌容器、DispatcherServlet 的创建方法、作用



## 第三讲 bean生命周期和模板方法

- Spring bean 生命周期各个阶段
- 模板设计模式

### bean 生命周期

- 生命周期的4个方法（按执行顺序）
  - 构造
  - 依赖注入
  - 初始化
  - 销毁

```java
@Component
public class LifeCycleBean {
    private static final Logger log = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        System.out.println("构造");
        log.debug("构造");
    }

    @Autowired
    public void autowire(@Value("${JAVA_11_HOME}") String home) {
        log.debug("依赖注入: {}", home);
    }

    @PostConstruct
    public void init() {
        log.debug("初始化");
    }

    @PreDestroy
    public void destroy() {
        log.debug("销毁");
    }
}
```

打印结果

```java
2022-06-04 10:32:13.696 DEBUG 6655 --- [           main] com.itheima.a03.LifeCycleBean            : 构造
2022-06-04 10:32:13.700 DEBUG 6655 --- [           main] com.itheima.a03.LifeCycleBean            : 依赖注入: /Library/Java/JavaVirtualMachines/jdk-11.0.14.jdk/Contents/Home
2022-06-04 10:32:13.701 DEBUG 6655 --- [           main] com.itheima.a03.LifeCycleBean            : 初始化
2022-06-04 10:32:14.068  INFO 6655 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-06-04 10:32:14.078  INFO 6655 --- [           main] com.itheima.a03.A03                      : Started A03 in 1.785 seconds (JVM running for 2.359)
2022-06-04 10:32:15.246 DEBUG 6655 --- [           main] com.itheima.a03.LifeCycleBean            : 销毁
```



- bean后处理器：提供bean生命周期各个阶段的一些扩展

```java
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<< 销毁之前执行, 如 @PreDestroy");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<< 实例化之前执行, 这里返回的对象会替换掉原本的 bean");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean")) {
            log.debug("<<<<<< 实例化之后执行, 这里如果返回 false 会跳过依赖注入阶段");
						// return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<< 依赖注入阶段执行, 如 @Autowired、@Value、@Resource");
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<< 初始化之前执行, 这里返回的对象会替换掉原本的 bean, 如 @PostConstruct、@ConfigurationProperties");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            log.debug("<<<<<< 初始化之后执行, 这里返回的对象会替换掉原本的 bean, 如代理增强");
        return bean;
    }
}
```

打印结果

```bash
com.itheima.a03.MyBeanPostProcessor      : <<<<<< 实例化之前执行, 这里返回的对象会替换掉原本的 bean
com.itheima.a03.LifeCycleBean            : 构造
com.itheima.a03.MyBeanPostProcessor      : <<<<<< 实例化之后执行, 这里如果返回 false 会跳过依赖注入阶段

com.itheima.a03.MyBeanPostProcessor      : <<<<<< 依赖注入阶段执行, 如 @Autowired、@Value、@Resource
com.itheima.a03.LifeCycleBean            : 依赖注入: /Library/Java/JavaVirtualMachines/jdk-11.0.14.jdk/Contents/Home

com.itheima.a03.MyBeanPostProcessor      : <<<<<< 初始化之前执行, 这里返回的对象会替换掉原本的 bean, 如 @PostConstruct、@ConfigurationProperties
com.itheima.a03.LifeCycleBean            : 初始化
com.itheima.a03.MyBeanPostProcessor      : <<<<<< 初始化之后执行, 这里返回的对象会替换掉原本的 bean, 如代理增强

2022-06-04 10:39:04.966  INFO 7002 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

com.itheima.a03.A03                      : Started A03 in 2.547 seconds (JVM running for 3.277)
com.itheima.a03.MyBeanPostProcessor      : <<<<<< 销毁之前执行, 如 @PreDestroy
com.itheima.a03.LifeCycleBean            : 销毁
```

- 学到了
  - bean生命周期的4个阶段：创建、依赖注入、初始化、销毁
  - 各个阶段会做功能增强，后处理器提供。（执行时机：实例化前后、依赖注入前后、初始化前后、销毁之前）



### 模板方法设计模式

- 重点
  - 静：固定不变的逻辑
  - 动：扩展的部分

```java
public class TestMethodTemplate {

    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析 @Autowired"));
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析 @Resource"));
        beanFactory.getBean();
    }

    // 模板方法  Template Method Pattern
    static class MyBeanFactory {
        public Object getBean() {
            Object bean = new Object();
            System.out.println("构造 " + bean);
            System.out.println("依赖注入 " + bean); // @Autowired, @Resource
          
          	// 动的部分
            for (BeanPostProcessor processor : processors) {
                System.out.println("---------> " + processor);
                processor.inject(bean);
            }
            System.out.println("初始化 " + bean);
            return bean;
        }

        // 设置一个集合
        private List<BeanPostProcessor> processors = new ArrayList<>();

        public void addBeanPostProcessor(BeanPostProcessor processor) {
            processors.add(processor);
        }
    }

    static interface BeanPostProcessor {
        public void inject(Object bean); // 对依赖注入阶段的扩展
    }
}
```



打印结果

```bash
构造 java.lang.Object@76fb509a
依赖注入 java.lang.Object@76fb509a
---------> com.itheima.a03.TestMethodTemplate$$Lambda$1/2084435065@300ffa5d
解析 @Autowired
---------> com.itheima.a03.TestMethodTemplate$$Lambda$2/1702297201@1f17ae12
解析 @Resource
初始化 java.lang.Object@76fb509a
```



## 第四讲 bean后处理器

- Bean 后处理器的作用：为bean生命周期各个阶段提供扩展
- 常见的后处理器



```java
// ⬇️GenericApplicationContext 是一个【干净】的容器
GenericApplicationContext context = new GenericApplicationContext();

context.registerBean("bean1", Bean1.class);
context.registerBean("bean2", Bean2.class);

context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
context.registerBean(AutowiredAnnotationBeanPostProcessor.class); // 解析@Autowired @Value 的bean后处理器

context.registerBean(CommonAnnotationBeanPostProcessor.class); // @Resource @PostConstruct @PreDestroy

ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());// 初始化前进行属性绑定
// ⬇️初始化容器
context.refresh(); // 执行beanFactory后处理器, 添加bean后处理器, 初始化所有单例

System.out.println(context.getBean(Bean4.class));
```



### @Autowired bean后处理器执行分析

```java
// 1. 查找哪些属性、方法加了 @Autowired, 这称之为 InjectionMetadata
AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
processor.setBeanFactory(beanFactory);

Bean1 bean1 = new Bean1();

Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
findAutowiringMetadata.setAccessible(true);
InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);// 获取 Bean1 上加了 @Value @Autowired 的成员变量，方法参数信息
System.out.println(metadata);

// 2. 调用 InjectionMetadata 来进行依赖注入, 注入时按类型查找值
metadata.inject(bean1, "bean1", null);
System.out.println(bean1);
```











## 第五讲 工厂后处理器



