# 写在前面

技术点📚

> - SpringBoot 认知（背景、优势等）；
> - 基于 SpringBoot 实现 ssm 整合；
> - 基于 SpringBoot 整合各种第三方技术；
> - 运维相关：打包发布Linux、多环境开发、配置高级、日志；
> - 原理相关：SpringBoot 内部工作流程、整合第三方技术的原理、实现自定义开发整合第三方技术的组件；
>



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304174139594.png)



SpringBoot 是由 `Pivotal` 团队提供的全新框架，其设计目的是用来<font color=red>简化 </font>`Spring` 应用的<font color=red>初始搭建</font>以及<font color=red>开发过程。</font>

## 模块说明

- spring-core：spring 核心依赖，引入该依赖会同时引入 spring-core、spring-jcl
- spring-context：spring 容器依赖，引入该依赖会同时引入 spring-aop、spring-beans、spring-context、spring-core、spring-expression、spring-jcl，如果想使用 spring 做简单的开发，这个依赖基本足够使用。
- spring-jdbc：spring 对 jdbc 的封装，并提供了编程式事务控制，引入该依赖会同时引入spring-jdbc、spring-tx
- spring-tx：spring 提供的声明式事务控制，包含 @Transaction 声明式事务注解
- spring-web：spring 的 web 框架，引入该依赖会同时引入spring-beans、spring-core、spring-jcl、spring-web
- spring-webmvc：springmvc 框架，是 web 的一个实现，引入该依赖会同时引入spring-aop、spring-beans、spring-context、spring-core、spring-expression、spring-jcl、spring-web、spring-webmvc，做一般的 web 开发可以直接引入该依赖即可，不必引入其他 spring 依赖。



## 问题解决

- **（服务启动报错）IDEAError:Internal error: (java.io.IOException) Cannot find IntelliJ IDEA projec**

解决：控制面板->时间和区域->区域->管理选项卡->更改系统区域设置,取消 `utf8` 编码的勾,重启即可。

参考：https://blog.csdn.net/qq_44443306/article/details/109304856

- **（服务启动报错）.\demo0810-1.0-SNAPSHOT.jar 中没有主清单属性**

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

参考：https://blog.csdn.net/qq_31868149/article/details/122652305







# 一、基础篇

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302124415188.png)



对比Spring

```bash
# Spring 程序缺点
- 依赖设置繁琐
- 配置繁琐

# SpringBoot 程序优点
- 起步依赖（简化依赖配置）
- 自动配置（简化常用工程相关配置）
- 辅助功能（内置服务器，……）
```



## 1. 四种创建方式

- （1）基于IDEA

- （2）基于官网：https://start.spring.io/

- （3）基于阿里云：https://start.aliyun.com

- （4）手工创建Maven工程修改为SpringBoot工程（推荐）
  - 创建普通 Maven 工程
  - 继承 `spring-boot-starter-parent`
  - 添加依赖 `spring-boot-starter-web`
  - 制作引导类 `Application`

详见：https://juejin.cn/post/7132483144501952525

## 2. IDEA中隐藏指定文件/文件夹

```bash
Setting → File Types → Ignored Files and Folders

输入要隐藏的文件名，支持*号通配符
回车确认添加
```



## 3. 初步解析

### 3.1 parent

- 总结

```bash
1. 开发 SpringBoot程序要继承 spring-boot-starter-parent，各版本间存在着诸多坐标版本不同。
2. spring-boot-starter-parent中定义了若干个依赖管理（依赖管理，而非依赖）。
3. 继承parent模块可以避免多个依赖使用相同技术时出现依赖版本冲突。

4. 继承parent的形式也可以采用引入依赖的形式实现效果。
- 方式1：继承spring-boot-starter-parent
- 方式2：使用 <dependencyManagement> 依赖 spring-boot-dependencies
```

- 如下两种方式效果相同

```xml
<!-- 方式1 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.4</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!-- 方式2：因为继承只能一次，这样同时可继承其它父模块 -->
<dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>${spring-boot.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
</dependencyManagement>
```



### 3.2 starter

SpringBoot 中常见项目名称，定义了当前项目使用的所有依赖坐标，以达到减少依赖配置的目的。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302173752751.png?w=600)



```bash
# parent 和 stater 主要解决配置问题！！！

# 实际开发
使用任意坐标时，仅书写 GAV中的G和A，V由SpringBoot提供，除非SpringBoot未提供对应版本V。
如发生坐标错误，再指定 Version（要小心版本冲突）。

# starter
1. 开发 SpringBoot 程序需要导入坐标时通常导入对应的 starter；
2. 每个不同的 starter 根据功能不同，通常包含多个依赖坐标；
3. 使用 starter 可以实现快速配置的效果，达到简化配置的目的。
```



### 3.3 引导类

```java
// SpringBoot的引导类是Boot工程的执行入口，运行main方法就可以启动项目。
// SpringBoot工程运行后初始化 Spring容器，扫描引导类所在包加载 bean。
@SpringBootApplication
public class Springboot01QuickstartApplication {
    public static void main(String[] args) {
        SpringApplication.run(Springboot01QuickstartApplication.class, args);
    } 
}
```



### 3.4 内嵌tomcat

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220326181954751.png)



#### Jetty

> 使用maven依赖管理变更起步依赖项

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302192727997.png)

- Jetty 比 Tomcat 更轻量级，可扩展性更强（相较于 Tomcat），谷歌应用引擎（GAE）已经全面切换为 Jetty。
  - tomcat(默认)：apache出品，粉丝多，应用面广，负载了若干较重的组件
  - jetty：更轻量级，负载性能远不及tomcat
  - undertow：undertow，负载性能勉强跑赢 tomcat
- 总结
  - 内嵌 Tomcat 服务器是 SpringBoot 辅助功能之一。
  - 内嵌 Tomcat 工作原理是将 Tomcat 服务器作为对象运行，并将该对象交给 Spring 容器管理。
  - 变更内嵌服务器思想是去除现有服务器，添加全新的服务器。



## 4. 基础配置

- SpringBoot 默认配置文件 `application.properties`，通过键值对配置对应属性。
  - [SpringBoot内置属性查询（官方文档中参考文档第一项：Application Properties）](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties)

- SpringBoot 提供了3种配置文件格式，可共存，加载顺序为：
  - `application.properties`（传统/默认格式） > `application.yml` （主流格式）> application.yaml

- 自动提示功能消失解决方案
  - Setting → Project Structure → Facets → 选中对应项目/工程 → Customize Spring Boot → 选择配置文件

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302195211634.png)



### 4.1 yaml（YAML Ain't Markup Language）

```bash
# 介绍
一种数据序列化格式。

# 优点
容易阅读；容易与脚本语言交互；以数据为核心，重数据轻格式。

# YAML文件扩展名
.yml（主流）和 .yaml
```



#### 定义

> 字面值表示方式

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302195713072.png)

 

> 数组表示方式：在属性名书写位置的下方使用减号作为数据开始符号，每行书写一个数据，减号与数据间空格分隔

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302195914779.png?w=600)



#### 读取

> 使用@Value读取单个数据，属性名引用方式：${一级属性名.二级属性名……}

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302200300577.png)



#### 痛点1：读取数据过多

> 读取数据过多

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302200800230.png?w=600)



解决：封装全部数据到 Environment 对象。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302200833382.png?w=600)



#### 痛点2：前缀长

> 统一定义前缀
>
> 使用 `@ConfigurationProperties` 注解绑定配置信息到封装类中
>
> 封装类需要定义为 Spring 管理的 bean，否则无法进行属性注入

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302202542461.png)



> 自定义对象封装指定数据的作用

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302202643245.png?w=600)



## 4. 整合第三方技术

- 整合JUint
- 整合MyBatis
- 整合MyBatis-Plus
- 整合Druid
- 基于SpringBoot的SSMP整合案例



### 4.1 整合Junit

`@SpringBootTest` 测试类注解，测试类定义上方。作用是设置 JUnit 加载的SpringBoot启动类。

```bash
1. 导入测试对应的starter
2. 测试类使用 @SpringBootTest注解修饰
3. 使用自动装配的形式添加要测试的对象
```



```java
@SpringBootTest
class Springboot07JunitApplicationTests {
  @Autowired
  private BookService bookService;

  @Test
  public void testSave(){
    bookService.save();
  } 
}
```



### 4.2 整合 swagger 接口文档

- 官网：https://swagger.io/docs/

- 详见：https://juejin.cn/post/7132483144501952525/#heading-8



### 4.3 整合Mybatis

```bash
# 思考🤔
核心配置：数据库连接相关信息（连什么？连谁？什么权限？）
映射配置：SQL映射（XML/注解）

# 整合步骤
1.导入MyBatis对应的starter
2.设置数据源参数
3.定义数据层接口与映射配置
4.测试类中注入dao接口，测试功能
```



> （1）导入MyBatis对应的starter

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
  	<version>1.3.2</version>
</dependency>
```



> （2）设置数据源参数

```properties
spring:
	datasource:
		driver-class-name: com.mysql.cj.jdbc.Driver
		url: jdbc:mysql://localhost:3306/ssm_db
		username: root
		password: root
```



```bash
# 注意📢
SpringBoot版本低于2.4.3(不含)，Mysql驱动版本大于8.0时，需要在url连接串中配置时区
jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
或在MySQL数据库端配置时区解决此问题
```



> （3）定义数据层接口与映射配置
>
> 数据库SQL映射需要添加 @Mapper 被容器识别到

```java
@Mapper
public interface UserDao {
		@Select("select * from user")
		public List<User> getAll();
}
```



> （4）测试类中注入 dao 接口，测试功能

```java
@SpringBootTest
class Springboot08MybatisApplicationTests {
    @Autowired
    private BookDao bookDao;
    
    @Test
    public void testGetById() {
        Book book = bookDao.getById(1);
        System.out.println(book);
    } 
}
```



### 4.4 整合Mybatis-Plus

```bash
# MyBatis-Plus与MyBatis区别
- 导入坐标不同
- 数据层实现简化

# 整合步骤
1.手动添加SpringBoot整合MyBatis-Plus的坐标，可以通过mvn repository获取
- 由于SpringBoot中未收录MyBatis-Plus的坐标版本，需要指定对应的Version
2.定义数据层接口与映射配置，继承 BaseMapper
3.其他同SpringBoot整合MyBatis
```



> ① 添加坐标👈🏻

```xml
<dependency> 
  	<groupId>com.baomidou</groupId> 
  	<artifactId>mybatis-plus-boot-starter</artifactId> 
  	<version>3.4.3</version>
</dependency>
```



> ② 定义数据层接口与映射配置，继承**BaseMapper**
>
> ③ 其他同SpringBoot整合MyBatis

```java
@Mapper
public interface UserDao extends BaseMapper<User> {
  
}
```



### 4.5 整合Druid

```bash
# 整合步骤
1. 指定数据源类型
2. 导入Druid对应的starter

# 可以变更Druid的配置方式
```



> ① 指定数据源类型

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
```



> ② 导入 Druid 对应的 starter

```xml
<dependency> 
  	<groupId>com.alibaba</groupId> 
  	<artifactId>druid-spring-boot-starter</artifactId> 
  	<version>1.2.6</version>
</dependency>
```



> 变更Druid的配置方式

```yaml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
      username: root
      password: root
```



### 4.6 整合任意第三方技术

```bash
导入对应的starter
根据提供的配置格式，配置非默认值对应的配置项
```



## 5. 基于SpringBoot的 SSMP 整合案例

> 分析：
>
> 0. 搭建SpringBoot应用
>
> 1. 实体类开发——使用 Lombok快速制作实体类
> 2. 数据层 Dao 开发——整合 MyBatisPlus、Druid（继承 BaseMapper、分页、条件查询）
> 3. 业务层 Service 开发——基于 MyBatisPlus 进行增量开发（使用 ISerivce 和 ServiceImpl ）
> 4. 表现层 Controller开发——基于 Restful 开发，使用 PostMan测试接口功能（统一返回值R、项目异常处理）
> 5. 页面开发——基于 VUE+ElementUI 制作，（列表、新增、修改、删除、分页、查询）
> 6. 前后端联调

详见：https://juejin.cn/post/7132866171358937096

### 总结一下


![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304102107465.png)



# 二、实用篇之运维实用篇

- 关键词：多环境开发、打包和运行（Linux）、日志
- 掘金地址：https://juejin.cn/post/7133051767918444557



## 6. 打包与运行

- SpringBoot 打包插件，参考：https://blog.csdn.net/iss_jin/article/details/122463390

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

- SpringBoot 应用可以基于 `java` 环境下独立运行 `jar` 文件启动服务
- 一般流程

```bash
mvn -v clean package -DskipTests

java –jar xxx.jar
```

- 可执行 jar 包目录结构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304123510884.png?w=600)

> jar包描述文件（MANIFEST.MF）

```bash
# 普通工程
Manifest-Version: 1.0
Implementation-Title: springboot_08_ssmp
Implementation-Version: 0.0.1-SNAPSHOT
Build-Jdk-Spec: 1.8
Created-By: Maven Jar Plugin 3.2.0

# 基于spring-boot-maven-plugin打包的工程
Manifest-Version: 1.0
Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
Implementation-Title: springboot_08_ssmp
Implementation-Version: 0.0.1-SNAPSHOT
Spring-Boot-Layers-Index: BOOT-INF/layers.idx
Start-Class: com.itheima.SSMPApplication
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Build-Jdk-Spec: 1.8
Spring-Boot-Version: 2.5.4
Created-By: Maven Jar Plugin 3.2.0
Main-Class: org.springframework.boot.loader.JarLauncher   # jar启动器
```

- Windonws 端口被占用

```bash
# 查询端口
netstat -ano
# 查询指定端口
netstat -ano |findstr "端口号"

# 根据进程PID查询进程名称
tasklist |findstr "进程PID号"

# 根据PID杀死任务
taskkill /F /PID "进程PID号"
# 根据进程名称杀死任务
taskkill -f -t -im "进程名称"
```



## 7. 配置高级

- 带属性参数启动 SpringBoot（携带多个属性启动 SpringBoot，属性间使用空格分隔）[属性加载优先顺序](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)

  - 命令行

  ```sh
  java –jar springboot.jar –-server.port=80
  ```

  - IDEA 中配置

  ![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220304124924581.png)

  - 通过编程形式带参数启动 SpringBoot程序，为程序添加运行参数

  ```java
  public static void main(String[] args) {
      // 添加参数
      String[] arg = new String[1];
      arg[0] = "--server.port=8080";
      
      SpringApplication.run(SSMPApplication.class, arg);
  }
  ```

  

- 配置文件分类

```bash
# SpringBoot中4级配置文件
1. file ：config/application.yml 【最高】（运维组长整体调控，副行长类似）（工程路径config目录中配置文件）
2. file ：application.yml（运维人员 配置涉密线上环境）（工程路径配置文件）
3. classpath：config/application.yml（开发经理整体调控）（项目类路径config目录中配置文件）
4. classpath：application.yml 【最低】（程序员本机开发与测试👨🏻‍💻）（项目类路径配置文件）

# 如果yml与properties在不同层级中共存会是什么效果？
类路径application.properties属性是否覆盖文件系统config目录中application.yml属性

# 多层级配置文件间的属性采用叠加并覆盖的形式作用于程序
```

- 配置文件加载
  - 通过启动参数加载配置文件（无需书写配置文件扩展名)
  - 通过启动参数加载指定文件路径下的配置文件（可以加载多个）
  - 多配置文件常用于将配置进行分类，进行独立管理，或将可选配置单独制作便于上线更新维护

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304130940441.png?w=600)



## 8. 多环境开发

- 多环境开发需要设置若干种常用环境，例如**开发、生产、测试环境**
  - 每种环境的区别在于加载的配置属性不同
  - 指定启动时使用某种环境

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304131844961.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304132614555.png)

- 配置文件书写

  - 主配置文件中设置公共配置（全局）

  - 环境分类配置文件中常用于设置冲突属性（局部）

- 配置文件可拆分：可以根据功能对配置文件中的信息进行拆分，并制作成独立的配置文件，

  - 如下

  ```bash
  application-devDB.yml
  application-devRedis.yml
  application-devMVC.yml
  ```

  - `include` 属性：在激活指定环境的情况下，使用 `include` 属性同时对多个环境进行加载使其生效，多个环境间使用逗号分隔

  ```yaml
  spring:
    profiles:
      active: dev
      include: devDB,devRedis,devMVC
  ```

- 其它说明
  - 当主环境dev与其他环境有相同属性时，主环境属性生效；其他环境中有相同属性时，最后加载的环境属性生效
  - 从 Spring2.4  版开始使用 `group` 属性替代 `include` 属性，降低了配置书写量，使用 group属性定义多种主环境与子环境的包含关系。多环境开发使用 group 属性设置配置文件分组，便于线上维护管理。


```yaml
spring:
  profiles:
    active: dev
    group:
      "dev": devDB,devRedis,devMVC
      "pro": proDB,proRedis,proMVC
      "test": testDB,testRedis,testMVC
```



### 8.1 多环境开发控制

- `Maven` 与 `SpringBoot` 多环境兼容
- 两者同时对多环境进行控制时，以Mavn为主。SpringBoot使用 `@xxx@` 占位符读取 Maven 对应的配置属性值，pom.xml 每次更新需要手动 `compile` 方可生效。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304133822623.png)



## 9. 日志

### 9.1 日志基础

> 日志作用
> - 编程期调试代码
> - 运行期记录信息
>   - 记录日常运营重要信息（峰值流量、平均响应时长……） 
>   - 记录应用报错信息（错误堆栈）
>   - 记录运维过程数据（扩容、宕机、报警……）



#### 代码中使用日志工具记录日志

> ①：添加日志记录操作

```java
@RestController
@RequestMapping("/books")
public class BookController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public String getById(){
        System.out.println("springboot is running...");
        log.debug("debug ...");
        log.info("info ...");
        log.warn("warn ...");
        log.error("error ...");
        return "springboot is running..."; 
    } 
}
```

```bash
日志级别

- TRACE：运行堆栈信息，使用率低
- DEBUG：程序员调试代码使用
- INFO：记录运维过程数据
- WARN：记录运维过程报警数据
- ERROR：记录错误堆栈信息
- FATAL：灾难信息，合并计入ERROR
```



> ②：设置日志输出级别

```yaml
# 开启debug模式，输出调试信息，常用于检查系统运行状况
debug: true

# 设置日志级别，root表示根节点，即整体应用日志级别
logging:
  level:
    root: debug
```



> ③：设置日志组，控制指定包对应的日志输出级别，也可以直接控制指定包对应的日志输出级别

```yaml
logging:
# 设置日志组
  group:
    # 自定义组名，设置当前组中所包含的包
    ebank: com.itheima.controller
  level:
    root: warn
    # 为对应组设置日志级别
    ebank: debug
    # 为对包设置日志级别
    com.itheima.controller: debug
```

- 总结一下
  - 日志用于记录开发调试与运维过程消息
  - 日志的级别共6种，通常使用4种即可，分别是 DEBUG，INFO, WARN, ERROR
  - 可以通过**日志组**或**代码包**的形式进行日志显示级别的控制

#### @Slf4j 注解优化日志对象创建

- 基于`lombok`提供的 `@Slf4j` 注解为类快速添加日志对象

```java
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    @GetMapping
    public String getById(){
        System.out.println("springboot is running...");
        log.debug("debug info...");
        log.info("info info...");
        log.warn("warn info...");
        log.error("error info...");
        return "springboot is running..."; 
    }
}
```



### 9.2 日志输出格式控制

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304135548508.png?w=600)



```bash
# 说明
PID：进程ID，用于表明当前操作所处的进程，当多服务同时记录日志时，该值可用于协助程序员调试程序

所属类/接口名：当前显示信息为SpringBoot重写后的信息，名称过长时，简化包名书写为首字母，甚至直接删除

# 设置日志输出格式
%d：日期
%m：消息
%n：换行
```

如

```yaml
logging:
  pattern:
    console: "%d - %m%n"
    
logging:
  pattern:
    console: "%d %clr(%p) --- [%16t] %clr(%-40.40c){cyan} : %m %n"    
```



### 9.3 日志文件配置

- 设置日志记录到文件&&日志文件详细配置

```yaml
logging:
  file:
    name: server.log
  logback:
    rollingpolicy:
      max-file-size: 3KB  # 单文件最大值
      file-name-pattern: server.%d{yyyy-MM-dd}.%i.log  # 文件名
```

 

# 三、实用篇之开发实用篇

> 🍓10、11、12对应掘金博客：https://juejin.cn/post/7133126757812535304

## 10. 热部署

- 手动启动热部署
- 自动启动热部署
- 热部署范围配置
- 关闭热部署



## 11. 配置高级

### 11.1 @ConfigurationProperties

### 11.2 数据校验



## 12. 测试

- 加载测试专用属性
- 加载测试专用配置
- Web环境模拟测试
- 数据层测试回滚
- 测试用例数据设定



## 13. 数据层解决方案

- 技术选型：Druid + MyBatis-Plus + MySQL
  - 数据源：Druid DataSource
  - 持久化技术：MyBatis-Plus / MyBatis
  - 数据库：MySQL

### 13.1 数据源配置

- 2 种格式

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304170632398.png)

- SpringBoot提供了 3 种内嵌的数据源对象供开发者选择
  - HikariCP：默认内置数据源对象
  - Tomcat 提供DataSource：HikariCP不可用的情况下，且在web环境中，将使用 tomcat 服务器配置的数据源对象
  - Commons DBCP：Hikari不可用，tomcat数据源也不可用，将使用dbcp数据源

> 通用配置无法设置具体的数据源配置信息，仅提供基本的连接相关配置，如需配置，在下一级配置中设置具体设定

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220304171048530.png)



### 13.2 JdbcTemplate（SpringBoot内置持久化解决方案）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304172412869.png)

- 添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```



- JdbcTemplate 配置

```yaml
spring: 
  jdbc:  
    template:
      query-timeout: -1   # 查询超时时间
      max-rows: 500       # 最大行数
      fetch-size: -1      # 缓存行数
```



### 13.3 内嵌数据库 H2

- SpringBoot提供了3种内嵌数据库供开发者选择，提高开发测试效率
  - H2
  - HSQL
  - Derby



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304221121219.png)



### 13.4 Redis（NoSQL解决方案1）

> 市面上常见的NoSQL解决方案：
>
> - Redis
> - Mongo
> - ES
> - Solr
>



- Redis是一款 key-value 存储结构的内存级NoSQL数据库
  - 支持多种数据存储格式
  - 支持持久化
  - 支持集群

- Redis下载（ Windows版）：https://github.com/tporadowski/redis/releases

- Redis安装与启动（ Windows版）
  - Windows解压安装或一键式安装
  - 服务端启动命令：redis-server.exe redis.windows.conf
  - 客户端启动命令：redis-cli.exe

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304222858659.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304223048629.png?w=600)

- 整合

> （1）导入坐标

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```



> （2）配置 Redis（采用默认配置）

```yaml
spring:
  redis:
    host: localhost # 127.0.0.1
    port: 6379
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304223454886.png?w=600)



> （3）`RedisTemplate` 提供操作各种数据存储类型的接口API

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220305115839226.png)



- 🤔思考：cmd 命令窗口和 IDEA中 RedisTemplate 操作的是同一个 Redis 吗？
  - 是
  - `RedisTemplate<K, V>的 K,V` 未指定都是 Object。客户端 `RedisTemplate` 以对象作为key和value，内部对数据进行序列化。
  - 客户端 `StringRedisTemplate extends RedisTemplate<String, String>` 以字符串作为 key 和 value，与 Redis客户端（cmd命令窗口）操作等效。

- ⭐开发使用
	- RedisTemplate
	- StringRedisTemplate（常用）

- ⚡Redis client

```bash
# lettuce（内部默认实现）（连接是基于 Netty 的，Netty 是一个多线程、事件驱动的 I/O 框架。）
底层设计中采用 StatefulRedisConnection。
StatefulRedisConnection 自身是线程安全的，可以保障并发访问安全问题，所以一个连接可以被多线程复用。
当然lettcus也支持多连接实例一起工作。

# jedis（直接连接Redis Server即直连模式，如果在多线程环境下使用 jedis是非线程安全的。）
解决方案可以通过配置连接池使每个连接专用，这样整体性能就大受影响。
```



- 选择 jedis

  - 依赖

  ```xml
  <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
  </dependency>
  ```

  - 配置

  ```yaml
  spring:
    redis:
      host: localhost
      port: 6379
      client-type: jedis
      lettuce:
        pool:
          max-active: 16
      jedis:
        pool:
          max-active: 16
  ```

  

### 13.5 Mongodb（NoSQL解决方案2）

> MongoDB 是一个开源、高性能、无模式的**文档型数据库**。NoSQL数据库产品中的一种，是**最像关系型数据库**的非关系型数据库。



#### 13.5.1 应用场景

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305211859839.png?w=600)



#### 13.5.2 安装、启动、可视化客户端

- windows

```bash
# 下载 msi 文件、安装
https://www.mongodb.com/try/download/community
# 创建3个文件夹：/data、/log、/data/log/


# 服务端启动  C:\Program Files\MongoDB\Server\5.0\bin>
mongod --dbpath C:\enviroment\mongodb\data\db

# 客户端启动
mongo
# 配置环境变量
# 可视化客户端——Robo 3T、Navicat
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305213734907.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305214047976.png)



```bash
# 基础操作CRUD

// 添加数据（文档）
// db.book.save({"name":"springboot基础篇",type:"springboot"})

// 删除操作
// db.book.remove({type:"springboot"})

// 修改操作
db.book.update({name:"springboot"},{$set:{name:"springboot2"}})

// 查询操作
// db.getCollection('book').find({})
db.book.find()
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305214850867.png)



#### 13.5.3 与springboot 整合

- 坐标

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

- 配置

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost/itheima
```

- 测试

```java
@SpringBootTest
class Springboot17MongodbApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(2);
        book.setName("springboot2");
        book.setType("springboot2");
        book.setDescription("springboot2");

        mongoTemplate.save(book);
    }

    @Test
    void find(){
        List<Book> all = mongoTemplate.findAll(Book.class);
        System.out.println(all);
    }
}
```



### 13.6 ElasticSearch（ES）（NoSQL解决方案3）



#### 13.6.1 概念、应用场景

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305220531243.png)

#### 13.6.2 安装、启动

- windows

```bash
# 下载
https://www.elastic.co/cn/downloads/elasticsearch
# 安装与启动
运行 elasticsearch.bat

# IK分词器 
下载：https://github.com/medcl/elasticsearch-analysis-ik/releases
```

- macos

```bash
# 启动 in ~/environment/elasticsearch-7.6.2/bin 
$ sh elasticsearch

# 访问
127.0.0.1:9200
```



#### 13.6.3 ES索引操作

```bash
# 创建/查询/删除索引
PUT	http://localhost:9200/books
GET	http://localhost:9200/books
DELETE	http://localhost:9200/books
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305222028604.png)



## 14 缓存

### 14.1 概述

#### 缓存作用（数据库成为系统操作的瓶颈）& 自定义缓存

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305230034516.png)



> 模拟一下缓存（2个案例）

```java
private HashMap<Integer,Book> cache = new HashMap<Integer,Book>();

@Override
public Book getById(Integer id) {
    // 如果当前缓存中没有本次要查询的数据，则进行查询，否则直接从缓存中获取数据返回
    Book book = cache.get(id);
    if(book == null){
        book = bookDao.selectById(id);
        cache.put(id,book);
    }
    return book;
}
```



```java
@Service
public class MsgServiceImpl implements MsgService {
    private HashMap<String ,String> cache = new HashMap<String,String>();

    @Override
    public String get(String tele) {
        String code = tele.substring(tele.length() - 6);
        cache.put(tele,code);
        return code;
    }

    @Override
    public boolean check(String tele, String code) {
        String queryCode = cache.get(tele);
        return code.equals(queryCode);
    }
}
```



#### springboot 提供的缓存（专业的做法）

- 导入坐标，启用缓存 @EnableCaching、@Cacheable

```xml
<!--cache-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```



```java
//开启缓存功能
@EnableCaching
@SpringBootApplication
public class Springboot19CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(Springboot19CacheApplication.class, args);
    }
}
```

- 设置缓存数据

```java
// 设置当前操作的结果数据进入缓存
@Cacheable(value="cacheSpace",key="#id")
public Book getById(Integer id) {
    return bookDao.selectById(id);
}
```

- 读取缓存的数据



#### 多种缓存技术

```bash
# SpringBoot 除了提供默认的缓存方案 Simple，还可以对其他缓存技术进行整合，统一接口，方便缓存技术的开发与管理。
- Generic
- JCache
- [ Ehcache ]
- Hazelcast
- Infinispan
- Couchbase
- [ Redis ]
- Caffeine
- Simple（默认）
- [ memcached ]
```



### 14.2 缓存使用案例——手机验证码

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305232200622.png?w=600)



```java
@Service
public class SMSCodeServiceImpl implements SMSCodeService {
    @Autowired
    private CodeUtils codeUtils;

    @Override
    // 只往缓存中放数据
    @CachePut(value = "smsCode",key="#tele")
    public String sendCodeToSMS(String tele) {
        String code = codeUtils.generator(tele);
        return code;
    }

    @Override
    public boolean checkCode(SMSCode smsCode) {
        //取出内存中的验证码与传递过来的验证码比对，如果相同，返回true
        String code = smsCode.getCode();
        String cacheCode = codeUtils.get(smsCode.getTele());
        return code.equals(cacheCode);
    }
}
```



```java
@Component
public class CodeUtils {
		// 生成6位验证码
    private String [] patch = {"000000","00000","0000","000","00","0",""};
		
    public String generator(String tele){
        int hash = tele.hashCode();
        int encryption = 20206666;
        long result = hash ^ encryption;
        long nowTime = System.currentTimeMillis();
        result = result ^ nowTime;
        long code = result % 1000000;
        code = code < 0 ? -code : code;
        String codeStr = code + "";
        int len = codeStr.length();
        return patch[len] + codeStr;
    }
		
  	// 取缓存中的数据
    @Cacheable(value = "smsCode",key="#tele")
    public String get(String tele){
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println(new CodeUtils().generator("18866668888"));
//    }
}
```



```java
@RestController
@RequestMapping("/sms")
public class SMSCodeController {
    @Autowired
    private SMSCodeService smsCodeService;

    @GetMapping
    public String getCode(String tele){
        String code = smsCodeService.sendCodeToSMS(tele);
        return code;
    }

    @PostMapping
    public boolean checkCode(SMSCode smsCode){
        return smsCodeService.checkCode(smsCode);
    }
    
}
```



```java
@Data
public class SMSCode {
    private String tele;
    private String code;
}
```

### 14.3 缓存供应商

####  Ehcache

```xml
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

- Spring 体系外的技术，有自己的配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <diskStore path="D:\ehcache" />

    <!--默认缓存策略 -->
    <!-- external：是否永久存在，设置为true则不会被清除，此时与timeout冲突，通常设置为false-->
    <!-- diskPersistent：是否启用磁盘持久化-->
    <!-- maxElementsInMemory：最大缓存数量-->
    <!-- overflowToDisk：超过最大缓存数量是否持久化到磁盘-->
    <!-- timeToIdleSeconds：最大不活动间隔，设置过长缓存容易溢出，设置过短无效果，可用于记录时效性数据，例如验证码-->
    <!-- timeToLiveSeconds：最大存活时间-->
    <!-- memoryStoreEvictionPolicy：缓存清除策略-->
    <defaultCache
        eternal="false"
        diskPersistent="false"
        maxElementsInMemory="1000"
        overflowToDisk="false"
        timeToIdleSeconds="60"
        timeToLiveSeconds="60"
        memoryStoreEvictionPolicy="LRU" />
		
  	<!-- 注意这里的配置 -->
    <cache
        name="smsCode"
        eternal="false"
        diskPersistent="false"
        maxElementsInMemory="1000"
        overflowToDisk="false"
        timeToIdleSeconds="10"
        <!-- 设置缓存过期时间 -->
        timeToLiveSeconds="10"
        memoryStoreEvictionPolicy="LRU" />

</ehcache>
```



```yaml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
      username: root
      password: root
  cache:
    type: ehcache
```



#### Redis

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```



```yaml
#--redis--
spring:
  cache:
    # 配置Redis服务器，缓存设定为使用Redis
    type: redis
    redis:
      # 设置Redis相关配置
      use-key-prefix: false
      key-prefix: sms_
      cache-null-values: false
      time-to-live: 10s

  redis:
    host: localhost
    port: 6379
```



#### memcached

- 安装、启动

```bash
# windows

```



```bash
# macos 
$ brew install memcached

```



#### jetcache（阿里）

- jetCache对 SpringCache 进行了封装，在原有功能基础上实现了多级缓存、缓存统计、自动刷新、异步调用、数据报表等功能。

- jetCache 设定了本地缓存与远程缓存的多级缓存解决方案。

  - local 本地缓存：LinkedHashMap、Caffeine
  - remote 远程缓存：Redis、Tair


```bash
# 加入jetcache坐标

# 配置远程缓存必要属性
# 配置本地缓存必要属性
# 开启jetcache注解支持

# 声明缓存对象
# 操作缓存
```



```xml
<dependency>
  <groupId>com.alicp.jetcache</groupId>
  <artifactId>jetcache-starter-redis</artifactId>
  <version>2.6.2</version>
</dependency>
```



####  j2cache

- j2cache是一个缓存整合框架，可以提供缓存的整合方案，使各种缓存搭配使用，自身不提供缓存功能

- 基于 `ehcache + redis` 进行整合



## 15 任务

```bash
# 定时任务是企业级应用中的常见操作
- 年度报表
- 缓存统计报告
- … …

# 市面上流行的定时任务技术
- Quartz
- Spring Task
```



### SpringBoot整合Quartz

```bash
# 相关概念
- 工作（Job）：用于定义具体执行的工作
- 工作明细（JobDetail）：用于描述定时工作相关的信息
- 触发器（Trigger）：用于描述触发工作的规则，通常使用cron表达式定义调度规则
- 调度器（Scheduler）：描述了工作明细与触发器的对应关系

# 步骤
1.导入SpringBoot整合quartz的坐标
2.定义具体要执行的任务，继承QuartzJobBean
3.定义工作明细与触发器，并绑定对应关系
```



### Spring Task

```bash
1.开启定时任务功能
2.设置定时执行的任务，并设定执行周期
3.定时任务相关配置
```





## 16 邮件





## 17 消息



## 18 监控

### 18.1 监控的意义

```bash
监控服务状态是否宕机

监控服务运行指标（内存、虚拟机、线程、请求等）

监控日志

管理服务（服务下线）
```



监控服务：获取和显示

被监控服务：上报可以被谁监控，以及被监控的数据有多少种

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306082728330.png?w=600)





### 18.2 可视化监控平台Admin

> Spring Boot Admin，开源社区项目，用于管理和监控SpringBoot应用程序。 
>
> 客户端注册到服务端后，通过HTTP请求方式，服务端定期从客户端获取对应的信息，并通过UI界面展示对应信息。



#### server服务端 @EnableAdminServer

```yaml
server:
  port: 8080
```



```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.5.4</version>
</dependency>
```



```java
@SpringBootApplication
@EnableAdminServer
public class Springboot25AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot25AdminServerApplication.class, args);
	}
}
```



#### client客户端

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.5.4</version>
</dependency>
```



```yaml
spring:
  boot:
    admin:
      client:
        url: http://localhost:8080
server:
  port: 81
  
management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      exposure:
        include: "*"
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306092720847.png)



### 18.3 监控原理actuator

```bash
Actuator提供了SpringBoot生产就绪功能，通过端点的配置与访问，获取端点信息

端点描述了一组监控信息，SpringBoot提供了多个内置端点，也可以根据需要自定义端点信息

访问当前应用所有端点信息：/actuator

访问端点详细信息：/actuator/端点名称

可以自定义开放的端点数，*表示开放所有（13个）
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306095312638.png?w=600)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306095638455.png)



> 小工具

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306095920146.png?w=600)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306100145308.png?w=550)





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306100622550.png?w=600)





### 18.4 自定义监控指标









# 四、原理篇







































