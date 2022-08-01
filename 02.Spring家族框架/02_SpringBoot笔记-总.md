# 写在前面



定位📌

```bash
# 小白
- 初步掌握SpringBoot程序的开发流程，能够基于SpringBoot实现基础的SSM框架整合。

# 初学者
- 掌握各种第三方技术与SpringBoot整合的方案。
- 积累基于SpringBoot的实战开发经验。

# 开发者
- 提升对Spring和SpringBoot原理的理解层次。
- 基于原理理解基础上，实现整合任意技术。
```



技术点📚

> - 能够创建SpringBoot工程；
> - 基于SpringBoot实现ssm整合；
>
> <br>
>
> - 能够掌握SpringBoot应用多环境开发；
> - 能够基于Linux系统发布SpringBoot应用；
> - 能够解决线上灵活配置SpringBoot应用的需求；
> - 能够基于SpringBoot整合任意第三方技术；
>
> <br>
>
> - 掌握SpringBoot内部工作流程；
> - 理解SpringBoot整合第三方技术的原理；
> - 实现自定义开发整合第三方技术的组件；



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304174139594.png)



SpringBoot 是由 `Pivotal` 团队提供的全新框架，其设计目的是用来<font color=red>简化 </font>`Spring` 应用的<font color=red>初始搭建</font>以及<font color=red>开发过程。</font>



# 一、基础篇

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302124415188.png)



对比Spring

```bash
# Spring程序缺点
- 依赖设置繁琐
- 配置繁琐

# SpringBoot程序优点
- 起步依赖（简化依赖配置）
- 自动配置（简化常用工程相关配置）
- 辅助功能（内置服务器，……）
```





## 1. 四种创建方式：

> - 基于Idea创建SpringBoot工程
> - 基于官网创建SpringBoot工程
> - 基于阿里云创建SpringBoot工程
> - 手工创建Maven工程修改为SpringBoot工程



### 1.1基于IDEA

①：创建新模块，选择Spring Initializr，并配置模块相关基础信息

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220327162853957.png?w=600)



②：选择当前模块需要使用的技术集

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302125704010.png?w=600)



③：开发控制器类

```java
//Rest模式
@RestController
@RequestMapping("/books")
public class BookController {
    @GetMapping
    public String getById(){
        System.out.println("springboot is running...");
        return "springboot is running..."; 
    } 
}
```



④：运行自动生成的Application类

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220327162921543.png?w=550)



```bash
# 最简SpringBoot程序所包含的基础文件：
- pom.xml文件
- Application类
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130025852.png?w=600)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130040514.png?w=600)



> Spring程序与SpringBoot程序对比



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130152848.png)

注：基于idea开发SpringBoot程序需要确保联网且能够加载到程序框架结构。





### 1.2 基于官网：https://start.spring.io/

- 打开SpringBoot官网，选择Quickstart Your Project
- 创建工程，并保存项目
- 解压项目，通过IDE导入项目



### 1.3 基于阿里云：https://start.aliyun.com

- 阿里云提供的坐标版本较低，如果需要使用高版本，进入工程后手工切换SpringBoot版本
- 阿里云提供的工程模板与Spring官网提供的工程模板略有不同



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302131047519.png?w=600)



### 1.4 手工创建Maven工程修改为SpringBoot工程（推荐）

> 1. 创建普通Maven工程
> 2. 继承spring-boot-starter-parent
> 3. 添加依赖spring-boot-starter-web
> 4. 制作引导类Application



## 2. IDEA中隐藏指定文件/文件夹

```bash
Setting → File Types → Ignored Files and Folders
输入要隐藏的文件名，支持*号通配符
回车确认添加
```



## 3. 初步解析



### 3.1 parent

```bash
# 总结
1. 开发SpringBoot程序要继承spring-boot-starter-parent，各版本间存在着诸多坐标版本不同
2. spring-boot-starter-parent中定义了若干个依赖管理（依赖管理，而非依赖）
3. 继承parent模块可以避免多个依赖使用相同技术时出现依赖版本冲突
4. 继承parent的形式也可以采用引入依赖的形式实现效果
- 方式1：继承spring-boot-starter-parent
- 方式2：使用<dependencyManagement>依赖spring-boot-dependencies
```

```xml
<!-- 两种方式效果同 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.4</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!-- 因为继承只能一次，采用如下方式2同时可继承其它父模块 -->
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



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302152433847.png?w=600)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302152545767.png?w=600)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220326182257433.png)



### 3.2 starter

SpringBoot中常见项目名称，定义了当前项目使用的所有依赖坐标，以达到减少依赖配置的目的。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302173752751.png?w=600)



```bash
# parent 和 stater 主要解决配置问题！！！

# 实际开发
使用任意坐标时，仅书写GAV中的G和A，V由SpringBoot提供，除非SpringBoot未提供对应版本V
如发生坐标错误，再指定Version（要小心版本冲突）

1. 开发SpringBoot程序需要导入坐标时通常导入对应的starter
2. 每个不同的starter根据功能不同，通常包含多个依赖坐标
3. 使用starter可以实现快速配置的效果，达到简化配置的目的
```



### 3.3 引导类

```java
@SpringBootApplication
public class Springboot01QuickstartApplication {
    public static void main(String[] args) {
        SpringApplication.run(Springboot01QuickstartApplication.class, args);
    } 
}
```



```bash
SpringBoot的引导类是Boot工程的执行入口，运行main方法就可以启动项目
SpringBoot工程运行后初始化Spring容器，扫描引导类所在包加载bean
```



### 3.4 内嵌tomcat

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220326181954751.png?w=600)



#### Jetty

> 使用maven依赖管理变更起步依赖项

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302192727997.png?w=550)

Jetty比Tomcat更轻量级，可扩展性更强（相较于Tomcat），谷歌应用引擎（GAE）已经全面切换为Jetty。

> tomcat(默认)：apache出品，粉丝多，应用面广，负载了若干较重的组件
>
> jetty：更轻量级，负载性能远不及tomcat
>
> undertow：undertow，负载性能勉强跑赢tomcat



```bash
# 总结
1. 内嵌Tomcat服务器是SpringBoot辅助功能之一
2. 内嵌Tomcat工作原理是将Tomcat服务器作为对象运行，并将该对象交给Spring容器管理
3. 变更内嵌服务器思想是去除现有服务器，添加全新的服务器
```



## 4. 基础配置

SpringBoot默认配置文件`application.properties`，通过键值对配置对应属性。

[SpringBoot内置属性查询：（官方文档中参考文档第一项：Application Properties）](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties)



```bash
# SpringBoot提供了3种配置文件格式，可共存，加载顺序为：
application.properties（传统/默认格式） > application.yml （主流格式）> application.yaml

# 自动提示功能消失解决方案
Setting → Project Structure → Facets → 选中对应项目/工程 → Customize Spring Boot → 选择配置文件
```



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



封装全部数据到Environment对象。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302200833382.png?w=600)



#### 痛点2：

> 统一定义前缀
>
> 使用@ConfigurationProperties注解绑定配置信息到封装类中
>
> 封装类需要定义为Spring管理的bean，否则无法进行属性注入

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302202542461.png)



> 自定义对象封装指定数据的作用

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302202643245.png?w=600)





## 4. 整合第三方技术

### 4.1 整合Junit

@SpringBootTest 测试类注解，测试类定义上方。作用是设置 JUnit 加载的SpringBoot启动类。

```bash
1. 导入测试对应的starter
2. 测试类使用@SpringBootTest修饰
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



### 4.2 整合Mybatis

```bash
# 思考🤔
核心配置：数据库连接相关信息（连什么？连谁？什么权限）
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
> 数据库SQL映射需要添加@Mapper被容器识别到

```java
@Mapper
public interface UserDao {
		@Select("select * from user")
		public List<User> getAll();
}
```



> （4）测试类中注入dao接口，测试功能

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



### 4.3 整合Mybatis-Plus

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



### 4.4 整合Druid

```bash
# 整合步骤
1. 指定数据源类型
2. 导入Druid对应的starter

# 可以变更Druid的配置方式
```



> ① 指定数据源类型

```bash
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
```



> ② 导入Druid对应的starter

```xml
<dependency> 
  	<groupId>com.alibaba</groupId> 
  	<artifactId>druid-spring-boot-starter</artifactId> 
  	<version>1.2.6</version>
</dependency>
```



> 变更Druid的配置方式

```bash
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
      username: root
      password: root
```



### 4.5 整合任意第三方技术

```bash
导入对应的starter
根据提供的配置格式，配置非默认值对应的配置项
```



## 5. 基于SpringBoot的SSMP整合案例

> 分析：
>
> 1. 实体类开发————使用Lombok快速制作实体类
> 2. Dao开发————整合MyBatisPlus，制作数据层测试类
> 3. Service开发————基于MyBatisPlus进行增量开发，制作业务层测试类
> 4. Controller开发————基于Restful开发，使用PostMan测试接口功能
> 5. Controller开发————前后端开发协议制作
> 6. 页面开发————基于VUE+ElementUI制作，前后端联调，页面数据处理，页面消息处理（列表、新增、修改、删除、分页、查询）
> 7. 项目异常处理
> 8. 按条件查询————页面功能调整、Controller修正功能、Service修正功能



```bash
- 先开发基础CRUD功能，做一层测一层
- 调通页面，确认异步提交成功后，制作所有功能
- 添加分页功能与查询功能
```



<br>

### 5.1搭建SpringBoot应用

```bash
勾选SpringMVC与MySQL坐标

修改配置文件为yml格式

设置端口为80方便访问（可选）
```



### 5.2 实体类开发

```bash
Lombok，一个Java类库，提供了一组注解，简化POJO实体类开发

lombok版本由SpringBoot提供，无需指定版本。

常用注解：@Data

为当前实体类在编译期设置对应的get/set方法，toString方法，hashCode方法，equals方法等
```



```java
@Data
public class Book {
		private Integer id;
		private String type;
		private String name;
		private String description; 
}
```



### 5.3 数据层开发

> 技术实现方案
> - MyBatisPlus
> - Druid

```bash
1.导入MyBatisPlus与Druid对应的starter
2.配置数据源与MyBatisPlus对应的基础配置（id生成策略使用数据库自增策略）
3.继承BaseMapper并指定泛型
4.制作测试类测试结果
```

2

```java
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm_db?servierTimezone=UTC
      username: root
      password: root

mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_
      id-type: auto
```

3

```java
@Mapper
public interface BookDao extends BaseMapper<Book> {
    
}
```

4

```java
@SpringBootTest
public class BookDaoTestCase {

    @Autowired
    private BookDao bookDao;

    @Test
    void testSave(){
        Book book = new Book();
        book.setName("测试数据");
        book.setType("测试类型");
        bookDao.insert(book);
    }

    @Test
    void testGetById() {
        System.out.println(bookDao.selectById(1));
    }

}
```



> 为方便调试可以开启MyBatisPlus的日志（使用配置方式开启日志，设置日志输出方式为标准输出）

```yaml
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_
      id-type: auto
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220303151011943.png?w=600)



### 5.4 数据层开发分页功能

> 分页操作需要设定分页对象IPage，IPage对象中封装了分页操作中的所有数据（数据、当前页码值、每页数据总量、最大页码值、数据总量）。
>
> 分页操作是在MyBatisPlus的常规操作基础上增强得到，内部是动态的拼写SQL语句，因此需要增强对应的功能，使用MyBatisPlus拦截器实现



```java
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cat
 * @description MyBatisPlus拦截器
 * @date 2022/3/3 下午3:33
 */
@Configuration
public class MPConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //1.定义Mp拦截
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2.添加具体的拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220303153905971.png)



### 5.5 数据层开发条件查询功能 QueryWrapper

> 使用QueryWrapper对象封装查询条件，推荐使用LambdaQueryWrapper对象，所有查询操作封装成方法调用



```java
/**
 * @description 条件查询功能
 * @author Lemonade
 * @param:
 * @updateTime 2022/3/3 下午3:50
 */
@Test
void testGetByCondition(){
    IPage page = new Page(1,10);
    LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
    lqw.like(Book::getName,"Spring");
    bookDao.selectPage(page,lqw);
}

@Test
void testGetByCondition2(){
    QueryWrapper<Book> qw = new QueryWrapper<Book>();
    qw.like("name","Spring");
    bookDao.selectList(qw);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220303155351978.png?w=600)



> 支持动态拼写查询条件  Strings.isNotEmpty(name)

```java
@Test
void testGetByCondition(){
    String name = "Spring";
    IPage page = new Page(1,10);
    LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
    lqw.like(Strings.isNotEmpty(name),Book::getName,"Spring");
    bookDao.selectPage(page,lqw);
}
```



### 5.6 业务层开发

```bash
# Service层接口定义与数据层接口定义具有较大区别，不要混用

业务层关注的是业务名称
login(String username,String password);

数据层关注的是数据库操作
selectByUserNameAndPassword(String username,String password);
```



```java
// 接口定义

public interface BookService {

    boolean save(Book book);

    boolean delete(Integer id);

    boolean update(Book book);

    Book getById(Integer id);

    List<Book> getAll();

    IPage<Book> getByPage(int currentPage,int pageSize);
}
```



```java
// 实现类定义

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    public Boolean save(Book book) {
        return bookDao.insert(book) > 0; 
    }

    public Boolean delete(Integer id) {
        return bookDao.deleteById(id) > 0; 
    }

    public Boolean update(Book book) {
        return bookDao.updateById(book) > 0; 
    } 

    public Book getById(Integer id) {
        return bookDao.selectById(id);
    }

    public List<Book> getAll() {
        return bookDao.selectList(null);
    }

    public IPage<Book> getByPage(int currentPage, int pageSize) {
        IPage page = new Page<Book>(currentPage,pageSize);
        return bookDao.selectPage(page,null);
    }

}
```



```bash
测试类定义注意添加@SpringBootTest注解。

1. Service接口名称定义成业务名称，并与Dao接口名称进行区分
2. 制作测试类测试Service功能是否有效
```



### 5.7 业务层开发——快速开发☞使用ISerivce<T>和ServiceImpl<M,T>

> 快速开发方案
>
> - 使用MyBatisPlus提供的业务层通用接口（ISerivce<T>）与业务层通用实现类（ServiceImpl<M,T>）
> - 在通用类基础上做功能重载或功能追加
> - 注意重载时不要覆盖原始操作，避免原始提供的功能丢失



接口

```java
public interface BookService extends IService<Book> {

    // 追加的操作与原始操作通过名称区分，功能类似
    boolean saveBook(Book book);

    boolean modify(Book book);

    boolean delete(Integer id);

    IPage<Book> getPage(int currentPage, int pageSize);

    IPage<Book> getPage(int currentPage, int pageSize, Book book);

}
```



实现类

```java
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean saveBook(Book book) {
        return bookDao.insert(book) > 0;
    }

    @Override
    public boolean modify(Book book) {
        return bookDao.updateById(book) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookDao.deleteById(id) > 0;
    }

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage, pageSize);
        bookDao.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize, Book book) {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
        lqw.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
        lqw.like(Strings.isNotEmpty(book.getName()), Book::getName, book.getName());
        lqw.like(Strings.isNotEmpty(book.getDescription()), Book::getDescription, book.getDescription());
        IPage page = new Page(currentPage, pageSize);
        bookDao.selectPage(page, lqw);
        return page;
    }
}
```



### 5.8 表现层开发

> - 基于Restful进行表现层接口开发
>
> - 使用Postman测试表现层接口功能
>
> - 功能测试



```java
// 功能测试
@GetMapping("/{currentPage}/{pageSize}")
public R getPage(@PathVariable int currentPage, @PathVariable int pageSize, Book book) {

    IPage<Book> page = bookService.getPage(currentPage, pageSize, book);
    // 如果当前页码大于了总页码，那么将最大页码值作为当前页码，重新执行查询操作
    // 源码中  long pages = this.getTotal() / this.getSize();
    if (currentPage > page.getPages()) {
        page = bookService.getPage((int) page.getPages(), pageSize, book);
    }
    return new R(true, page);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220326182452830.png)



> 1. 基于Restful制作表现层接口
>
>   - 新增：POST
>
>   - 删除：DELETE
>
>   - 修改：PUT
>
>   - 查询：GET
>
>
> 2. 接收参数
> - 实体数据：@RequestBody
> - 路径变量：@PathVariable



### 5.9 表现层消息一致性处理 R（统一返回值）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304093035557.png?w=600)



```bash
# 设计表现层返回结果的模型类，用于后端与前端进行数据格式统一，也称为前后端数据协议
1. 设计统一的返回值结果类型便于前端开发读取数据
2. 返回值结果类型可以根据需求自行设定，没有固定格式
3. 返回值结果模型类用于后端与前端进行数据格式统一，也称为前后端数据协议

- flag：false
- Data: null
- 消息(msg): 要显示信息
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304093337419.png?w=600)



### 5.10 前后端协议联调


> 前后端分离结构设计中页面归属前端服务器
>
> 单体工程中页面放置在resources目录下的static目录中（建议执行clean）



前端发送异步请求，调用后端接口



> 1. 单体项目中页面放置在resources/static目录下
> 2. created钩子函数用于初始化页面时发起调用
> 3. 页面使用axios发送异步请求获取数据后确认前后端是否联通



```js
//列表
getAll() {
    axios.get("/books").then((res)=>{
    		console.log(res.data);
    });
},
```





#### 查询

将查询数据返回到页面，利用前端数据双向绑定进行数据展示

```js
//列表
getAll() {
    axios.get("/books").then((res)=>{
    		this.dataList = res.data.data;
    });
},
```

#### 添加

```bash
1. 请求方式使用POST调用后台对应操作
2. 添加操作结束后动态刷新页面加载数据
3. 根据操作结果不同，显示对应的提示信息
4. 弹出添加Div时清除表单数据
```



```js
//弹出添加窗口
handleCreate() {
    this.dialogFormVisible = true;
},
  
//清除数据，重置表单
resetForm() {
		this.formData = {};
},
  
//弹出添加窗口
handleCreate() {
		this.dialogFormVisible = true;
		this.resetForm();
},  
  
//添加
handleAdd () {
		//发送异步请求
		axios.post("/books",this.formData).then((res)=>{
				//如果操作成功，关闭弹层，显示数据
				if(res.data.flag){
						this.dialogFormVisible = false;
						this.$message.success("添加成功");
				}else {
					this.$message.error("添加失败");
				}
		}).finally(()=>{
				this.getAll();
		});
},  
  
//取消添加
cancel(){
		this.dialogFormVisible = false;
		this.$message.info("操作取消");
},  
```



#### 删除

```bash
1. 请求方式使用Delete调用后台对应操作
2. 删除操作需要传递当前行数据对应的id值到后台
3. 删除操作结束后动态刷新页面加载数据
4. 根据操作结果不同，显示对应的提示信息
5. 删除操作前弹出提示框避免误操作
```



```js
// 删除
handleDelete(row) {
    axios.delete("/books/"+row.id).then((res)=>{
        if(res.data.flag){
            this.$message.success("删除成功");
        }else{
            this.$message.error("删除失败");
        }
    }).finally(()=>{
        this.getAll();
    });
}

// 删除
handleDelete(row) {
    //1.弹出提示框
    this.$confirm("此操作永久删除当前数据，是否继续？","提示",{
        type:'info'
    }).then(()=>{
        //2.做删除业务
        axios.delete("/books/"+row.id).then((res)=>{
        ……
        }).finally(()=>{
            this.getAll();
        });
    }).catch(()=>{
        //3.取消删除
        this.$message.info("取消删除操作");
    });
}
```





```js
//弹出编辑窗口
handleUpdate(row) {
    axios.get("/books/"+row.id).then((res)=>{
        if(res.data.flag){
            //展示弹层，加载数据
            this.formData = res.data.data;
            this.dialogFormVisible4Edit = true; 
        }else{
            this.$message.error("数据同步失败，自动刷新"); }
        });
},
  
//删除
handleDelete(row) {
    axios.delete("/books/"+row.id).then((res)=>{
        if(res.data.flag){
            this.$message.success("删除成功");
        }else{
            this.$message.error("数据同步失败，自动刷新"); 
        }
    }).finally(()=>{
        this.getAll();
    });
}  
```



> 1. 加载要修改数据通过传递当前行数据对应的id值到后台查询数据
> 2. 利用前端数据双向绑定将查询到的数据进行回显



#### 修改

```bash
1. 请求方式使用PUT调用后台对应操作
2. 修改操作结束后动态刷新页面加载数据（同新增）
3. 根据操作结果不同，显示对应的提示信息（同新增）
```



```js
//修改
handleEdit() {
    axios.put("/books",this.formData).then((res)=>{
        //如果操作成功，关闭弹层并刷新页面
        if(res.data.flag){
            this.dialogFormVisible4Edit = false;
            this.$message.success("修改成功");
        }else {
            this.$message.error("修改失败，请重试");
        }
    }).finally(()=>{
        this.getAll();
    });
},
  
// 取消添加和修改
cancel(){
    this.dialogFormVisible = false;
    this.dialogFormVisible4Edit = false;
    this.$message.info("操作取消");
},
```



### 5.11 业务消息一致性处理

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304095957398.png?w=600)



> 对异常进行统一处理，出现异常后，返回指定信息



```java
// 作为springmvc的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {

    // 拦截所有的异常信息
    @ExceptionHandler(Exception.class)
    public R doException(Exception e){
        // 记录日志
        // 通知运维
        // 通知开发
        e.printStackTrace();
        return new R("服务器故障，请稍后重试哈！");
    }
}
```



> 1. 使用注解@RestControllerAdvice定义SpringMVC异常处理器用来处理异常的
> 2. 异常处理器必须被扫描加载，否则无法生效
> 3. 表现层返回结果的模型类中添加消息属性用来传递消息到页面



### 5.12 分页功能

> 页面使用el分页组件添加分页功能
>
> 定义分页组件需要使用的数据并将数据绑定到分页组件
>
> 替换查询全部功能为分页功能
>
> 加载分页数据
>
> 分页页码值切换



> 1. 使用el分页组件
> 2. 定义分页组件绑定的数据模型
> 3. 异步调用获取分页数据
> 4. 分页数据页面回显





### 总结一下


![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304102107465.png)



> 1. 整合JUint
> 2. 整合MyBatis
> 3. 整合MyBatis-Plus
> 4. 整合Druid
> 5. 基于SpringBoot的SSMP整合案例



# 二、实用篇之运维实用篇

> - 能够掌握SpringBoot程序多环境开发
>
> - 能够基于Linux系统发布SpringBoot工程
>
> - 能够解决线上灵活配置SpringBoot工程的需求



## 6. 打包与运行

### 6.1 程序打包与运行（Windows版）

①：对SpringBoot项目打包（执行Maven构建指令package）

```bash
mvn package
```

②：运行项目（执行启动指令）
```bash
java –jar springboot.jar
```



```bash
# SpringBoot工程可以基于java环境下独立运行jar文件启动服务

# SpringBoot工程执行mvn命令package进行打包
确认打包时是否具有SpringBoot对应的maven插件，使用SpringBoot提供的maven插件可以将工程打包成可执行jar包 

# 运行
执行jar命令：java –jar 工程名.jar
```



```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <excludes>
            <exclude>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </exclude>
        </excludes>
    </configuration>
</plugin>
```



#### 可执行jar包目录结构

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



#### Windonws端口被占用

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



### 6.2 程序运行（Linux版）

```bash
# 环境准备
基于Linux（CenterOS7）
安装JDK，且版本不低于打包时使用的JDK版本
安装包保存在/usr/local/自定义目录中或$HOME下

Boot程序打包依赖SpringBoot对应的Maven插件即可打包出可执行的jar包
其他操作参照Windows版进行

# 运行
1. 使用`electerm`等工具上传包
2. 执行jar命令：java –jar 工程名.jar
```



## 7. 配置高级

### 7.1 临时属性设置

```shell
# 带属性参数启动SpringBoot（携带多个属性启动SpringBoot，属性间使用空格分隔）
java –jar springboot.jar –-server.port=80
```

[属性加载优先顺序](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)



### 7.2 临时属性设置（开发环境）

> 带属性启动SpringBoot程序，为程序添加运行属性

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304124924581.png?w=600)



> 通过编程形式带参数启动SpringBoot程序，为程序添加运行参数

```java
public static void main(String[] args) {
    String[] arg = new String[1];
    arg[0] = "--server.port=8080";
    SpringApplication.run(SSMPApplication.class, arg);
}

// 不携带参数启动SpringBoot程序
public static void main(String[] args) {
    SpringApplication.run(SSMPApplication.class, arg);
}
```



### 7.3 配置文件分类&&自定义配置文件

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



> 通过启动参数加载配置文件（无需书写配置文件扩展名)
>
> 通过启动参数加载指定文件路径下的配置文件
>
> 通过启动参数加载指定文件路径下的配置文件时可以加载多个配置
>
> 多配置文件常用于将配置进行分类，进行独立管理，或将可选配置单独制作便于上线更新维护

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304130940441.png?w=600)



```bash
# 自定义配置文件——重要说明

- 单服务器项目：使用自定义配置文件需求较低

- 多服务器项目：使用自定义配置文件需求较高，将所有配置放置在一个目录中，统一管理

- 基于SpringCloud技术，所有的服务器将不再设置配置文件，而是通过配置中心进行设定，动态加载配置信息
```



```bash
# 总结
1. 配置文件可以修改名称，通过启动参数设定
2. 配置文件可以修改路径，通过启动参数设定
3. 微服务开发中配置文件通过配置中心进行设置
```

### 总结

```bash
1. SpringBoot在开发和运行环境均支持使用临时参数修改工程配置
2. SpringBoot支持4级配置文件，应用于开发与线上环境进行配置的灵活设置
3. SpringBoot支持使用自定义配置文件的形式修改配置文件存储位置
4. 基于微服务开发时配置文件将使用配置中心进行管理
```



## 8. 多环境开发

### 8.1 多环境开发（YAML版）



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304131844961.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304132430520.png)



> 1. 多环境开发需要设置若干种常用环境，例如开发、生产、测试环境
> 2. yaml格式中设置多环境使用---区分环境设置边界
> 3. 每种环境的区别在于加载的配置属性不同
> 4. 启用某种环境时需要指定启动时使用该环境



#### 多环境开发（YAML版）多配置文件格式

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304132614555.png)



##### 多环境开发配置文件书写技巧（一）

> - 主配置文件中设置公共配置（全局）
> - 环境分类配置文件中常用于设置冲突属性（局部）



> 1. 可以使用独立配置文件定义环境属性
> 2. 独立配置文件便于线上系统维护更新并保障系统安全性



> 多环境开发（Properties版）多配置文件格式
>
> properties文件多环境配置仅支持多文件格式

![image-20220304132944416](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304132944416.png)



#### 多环境开发独立配置文件书写技巧（二）

> 根据功能对配置文件中的信息进行拆分，并制作成独立的配置文件，命名规则如下

```yaml
application-devDB.yml
application-devRedis.yml
application-devMVC.yml
```

> 使用include属性在激活指定环境的情况下，同时对多个环境进行加载使其生效，多个环境间使用逗号分隔

```yaml
spring:
  profiles:
    active: dev
    include: devDB,devRedis,devMVC
```
> 当主环境dev与其他环境有相同属性时，主环境属性生效；其他环境中有相同属性时，最后加载的环境属性生效



> 从Spring2.4版开始使用group属性替代include属性，降低了配置书写量
> 使用group属性定义多种主环境与子环境的包含关系
>
> 多环境开发使用group属性设置配置文件分组，便于线上维护管理

```yaml
spring:
  profiles:
    active: dev
    group:
      "dev": devDB,devRedis,devMVC
      "pro": proDB,proRedis,proMVC
      "test": testDB,testRedis,testMVC
```



### 8.2 多环境开发（Properties版）



### 8.3 多环境开发控制

#### Maven与SpringBoot多环境兼容

> ①：Maven中设置多环境属性
>
> ②：SpringBoot中引用Maven属性
>
> ③：执行Maven打包指令，并在生成的boot打包文件.jar文件中查看对应信息

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304133822623.png)



> 1. 当Maven与SpringBoot同时对多环境进行控制时，以Mavn为主，SpringBoot使用@..@占位符读取Maven对应的配置属性值
> 2. 基于SpringBoot读取Maven配置属性的前提下，如果在Idea下测试工程时pom.xml每次更新需要手动compile方可生效



## 9. 日志

### 9.1 日志基础

> 日志作用
> - 编程期调试代码
> - 运营期记录信息
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

#### 总结一下

```bash
1. 日志用于记录开发调试与运维过程消息

2. 日志的级别共6种，通常使用4种即可，分别是DEBUG，INFO, WARN, ERROR

3. 可以通过日志组或代码包的形式进行日志显示级别的控制
```

#### 教你一招：@Slf4j 优化日志对象创建代码

```bash
使用lombok提供的注解@Slf4j简化开发，减少日志对象的声明操作 

基于`lombok`提供的`@Slf4j`注解为类快速添加日志对象
```



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



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304135439828.png?w=600)



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



### 9.3 日志文件设置



#### 设置日志记录到文件&&日志文件详细配置

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

> 能够基于SpringBoot整合任意第三方技术



## 10. 热部署

#### 手动启动热部署











#### 自动启动热部署

#### 热部署范围配置

#### 关闭热部署













## 11. 配置高级

## 12. 测试

## 13. 数据层解决方案

**现有数据层解决方案技术选型：Druid + MyBatis-Plus + MySQL**

<hr>

> - 数据源：Druid DataSource
>- 持久化技术：MyBatis-Plus / MyBatis
> - 数据库：MySQL



#### 13.1 数据源配置

##### 数据源配置2种格式



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304170632398.png)

##### SpringBoot提供了3种内嵌的数据源对象供开发者选择



> - HikariCP：**默认**内置数据源对象
>- Tomcat提供DataSource：HikariCP不可用的情况下，且在web环境中，将使用tomcat服务器配置的数据源对象
> - Commons DBCP：Hikari不可用，tomcat数据源也不可用，将使用dbcp数据源
> 
> 通用配置无法设置具体的数据源配置信息，仅提供基本的连接相关配置，如需配置，在下一级配置中设置具体设定



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304171048530.png)



#### 13.2 SpringBoot内置持久化解决方案——JdbcTemplate



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304172412869.png)



添加依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```



> JdbcTemplate配置

```yaml
spring: 
  jdbc:  
    template:
      query-timeout: -1   # 查询超时时间
      max-rows: 500       # 最大行数
      fetch-size: -1      # 缓存行数
```



> 总结：
>
> 1. SpringBoot内置JdbcTemplate持久化解决方案
> 2. 使用JdbcTemplate需要导入spring-boot-starter-jdbc



#### 13.3 内嵌数据库H2

##### SpringBoot提供了3种内嵌数据库供开发者选择，提高开发测试效率



> - H2
>- HSQL
> - Derby



- 导入H2相关坐标

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```



- 设置当前项目为web工程，并配置H2管理控制台参数（访问用户名sa，默认密码123456）

```yml
server:
	port: 80
spring:
	h2:
		console:
			path: /h2
			enabled: true

```



- 操作数据库（创建表）

```sql
create table tbl_book (id int,name varchar,type varchar,description varchar)
```



- 设置访问数据源（SpringBoot可以根据url地址自动识别数据库种类，在保障驱动类存在的情况下，可以省略配置）

```yaml
server:
  port: 80

spring:
  datasource:
  	# 可省
    driver-class-name: org.h2.Driver
    url: jdbc:h2:~/test
    username: sa
    password: 123456
  h2:
    console:
      path: /h2
      enabled: true
```



- H2数据库控制台仅用于开发阶段，线上项目请务必关闭控制台功能

```yaml
server:
  port: 80
spring:
 	h2:
 	  console:
 	    path: /h2
 	    enabled: false
```



> H2内嵌式数据库启动方式
>
> H2数据库线上运行时请务必关闭



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304221121219.png)



#### 13.4 NoSQL解决方案1_Redis

> 市面上常见的NoSQL解决方案
>
> - Redis
> - Mongo
> - ES
> - Solr
>
> 说明：上述技术通常在Linux系统中安装部署，为降低学习者压力，本课程制作基于Windows版安装所有的软件并基于Windows版安装的软件进行课程制作





<hr>

> Redis是一款key-value存储结构的内存级NoSQL数据库
>
> - 支持多种数据存储格式
> - 支持持久化
> - 支持集群



> Redis下载（ Windows版）
>
> - https://github.com/tporadowski/redis/releases
>
> Redis安装与启动（ Windows版）
>
> - Windows解压安装或一键式安装
> - 服务端启动命令：redis-server.exe redis.windows.conf
> - 客户端启动命令：redis-cli.exe



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304222858659.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304223048629.png?w=600)



> （1）导入SpringBoot整合Redis坐标

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```



> （2）配置Redis（采用默认配置）

```yaml
spring:
  redis:
    host: localhost # 127.0.0.1
    port: 6379
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220304223454886.png?w=600)



> （3）RedisTemplate提供操作各种数据存储类型的接口API

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305115839226.png?w=600)



> 问题：cmd命令窗口和IDEA中RedisTemplate操作的是同一个Redis吗？
>
> 是同一个。RedisTemplate<K, V>的K,V未指定都是Object。客户端RedisTemplate以对象作为key和value，内部对数据进行序列化
>
> StringRedisTemplate extends RedisTemplate<String, String>的K,V都是String，和cmd命令窗口一致。客户端StringRedisTemplate以字符串作为key和value，与Redis客户端操作等效



> 总结：
>
> - RedisTemplate
> - StringRedisTemplate（常用）



#### 13.5 客户端选择Jedis（另外的lettuce是内部默认实现）

依赖

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```



配置

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



> lettcus与jedis区别
> - jedis连接Redis服务器是直连模式，当多线程模式下使用jedis会存在线程安全问题，解决方案可以通过配置连接池使每个连接专用，这样整体性能就大受影响。
> - lettcus基于Netty框架进行与Redis服务器连接，底层设计中采用StatefulRedisConnection。StatefulRedisConnection自身是线程安全的，可以保障并发访问安全问题，所以一个连接可以被多线程复用。当然lettcus也支持多连接实例一起工作。



> SpringBoot整合Redis客户端选择
>
> - lettuce（默认）
> - jedis





> 机构化数据存储，速度又很快的数据结构。

#### 13.6 NoSQL解决方案2_Mongodb

> MongoDB是一个开源、高性能、无模式的文档型数据库。NoSQL数据库产品中的一种，是最像关系型数据库的非关系型数据库



##### 13.6.1 应用场景

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305211859839.png?w=600)



##### 13.6.2 安装&启动&可视化客户端

###### windows

```bash
# 下载
https://www.mongodb.com/try/download
# 安装
解压缩后设置数据目录
# 服务端启动
mongod --dbpath=..\data\db
# 客户端启动
mongo --host=127.0.0.1 --port=27017
# 可视化客户端——Robo 3T

```

###### macos

```bash
# 启动
cat at zhangjianlindeMacBook-Pro in ~/environment/mongodb 
$ mongod --fork --dbpath data --logpath log/mongodb.log --logappend

$ mongo
# 可视化客户端
Robo 3T

# 退出
# 验证权限；赋予权限；关闭
> db.auth('root','123456')
1
> db.grantRolesToUser("root",[{role:"hostManager",db: "admin"}])
> use admin
switched to db admin
> db.shutdownServer({force:true})
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305213734907.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305214047976.png)



```sql
# 基础操作CRUD
// 添加数据（文档）
// db.book.save({"name":"springboot",type:"springboot"})

// 删除操作
// db.book.remove({type:"springboot"})

// 修改操作
db.book.update({name:"springboot"},{$set:{name:"springboot2"}})

// 查询操作
// db.getCollection('book').find({})
db.book.find()
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305214850867.png)



##### 13.6.3 springboot整合

###### 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

###### 配置

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost/itheima
```

###### 测试

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



#### 13.7 NoSQL解决方案3_ElasticSearch（ES）

##### 13.7.1 应用场景&&相关概念

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305220531243.png)

##### 13.7.2 安装&启动

###### windows

```bash
# 下载
https://www.elastic.co/cn/downloads/elasticsearch
# 安装与启动
运行 elasticsearch.bat

# IK分词器 
下载：https://github.com/medcl/elasticsearch-analysis-ik/releases
```



###### macos

```bash
# 启动
cat at zhangjilindembp in ~/environment/elasticsearch-7.6.2/bin 
$ sh elasticsearch
# 访问
127.0.0.1:9200
```



##### 13.7.3 ES索引操作

```bash
# 创建/查询/删除索引
PUT	http://localhost:9200/books
GET	http://localhost:9200/books
DELETE	http://localhost:9200/books
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220305222028604.png)









## 14. 整合第三方技术

### 14.1 缓存

#### 缓存作用（数据库成为系统操作的瓶颈）&自定义缓存

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



#### springboot缓存（专业的做法）

> SpringBoot提供了缓存技术，方便缓存使用
>
> - 启用缓存
> - 设置进入缓存的数据
> - 设置读取缓存的数据



###### 导入坐标，启用缓存@EnableCaching、@Cacheable

```xml
<!--cache-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```



```java
@SpringBootApplication
//开启缓存功能
@EnableCaching
public class Springboot19CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot19CacheApplication.class, args);
    }
}
```

<br>

```java
@Override
@Cacheable(value="cacheSpace",key="#id")
// 设置当前操作的结果数据进入缓存
public Book getById(Integer id) {
    return bookDao.selectById(id);
}
```



#### 多种缓存技术

```bash
# SpringBoot提供的缓存技术除了提供默认的缓存方案Simple
# 还可以对其他缓存技术进行整合，统一接口，方便缓存技术的开发与管理

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



#### 缓存使用案例——手机验证码

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



#### 缓存供应商变更 Ehcache



```xml
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

Spring 体系外的技术，有自己的配置

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



#### 缓存供应商变更 Redis



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



#### 缓存供应商变更 memcached

##### 安装&启动

```bash
# windows

```





```bash
# macos 
$ brew install memcached

```



#### 缓存供应商变更 jetcache（阿里）

jetCache对SpringCache进行了封装，在原有功能基础上实现了多级缓存、缓存统计、自动刷新、异步调用、数据报表等功能。

jetCache设定了本地缓存与远程缓存的多级缓存解决方案。

- 本地缓存（local）
  - LinkedHashMap
  - Caffeine

- 远程缓存（remote）
  - Redis
  - Tair

```bash
加入jetcache坐标
配置远程缓存必要属性
配置本地缓存必要属性
开启jetcache注解支持
声明缓存对象
操作缓存
```



<br>



```xml
<dependency>
  <groupId>com.alicp.jetcache</groupId>
  <artifactId>jetcache-starter-redis</artifactId>
  <version>2.6.2</version>
</dependency>
```



#### 缓存供应商变更 j2cache

j2cache是一个缓存整合框架，可以提供缓存的整合方案，使各种缓存搭配使用，自身不提供缓存功能

基于 ehcache + redis 进行整合



### 14.2 任务

```bash
# 定时任务是企业级应用中的常见操作
- 年度报表
- 缓存统计报告
- … …

# 市面上流行的定时任务技术
- Quartz
- Spring Task
```



#### SpringBoot整合Quartz

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



#### Spring Task

```bash
1.开启定时任务功能
2.设置定时执行的任务，并设定执行周期
3.定时任务相关配置
```





### 14.3 邮件





### 14.4 消息





## 15. 监控

### 15.1 监控的意义

```bash
监控服务状态是否宕机

监控服务运行指标（内存、虚拟机、线程、请求等）

监控日志

管理服务（服务下线）
```



监控服务：获取和显示

被监控服务：上报可以被谁监控，以及被监控的数据有多少种

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220306082728330.png?w=600)





### 15.2 可视化监控平台Admin

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

web依赖
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



### 15.3 监控原理actuator

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





### 15.4 自定义监控指标

















# 四、原理篇







































