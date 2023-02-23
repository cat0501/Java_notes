

# 0 写在前面



## 推荐

### 文章

[一定一定一定认真看MyBatis 官方文档](https://mybatis.org/mybatis-3/zh/index.html)

[Github仓库](https://github.com/mybatis/mybatis-3)

[Mybatis3.4.x 技术内幕（二十三）：Mybatis 面试问题集锦（大结局）](https://my.oschina.net/zudajun/blog/747682)





### 视频

[尚硅谷杨博超20220223](https://www.bilibili.com/video/BV1VP4y1c7j7?from=search&seid=12656264273391643854&spm_id_from=333.337.0.0)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220409160011565.png)



### 书籍



## 面试







模糊查询

```xml
    <select id="getLikeUser" resultType="com.atguigu.mybatis.pojo.User">
        <!--  方式1：使用${...}  可能会有sql注入的问题-->
        select * from t_user where username like '%${username}';
        <!-- 方式2：       -->
        select * from t_user
        <where>
            <if test="username">
                and t_user.username like "%"#{username}
            </if>
        </where>
      
      	<!-- 方式3：使用concat()函数连接参数-->
				select * from t_user where username like CONCAT("%", #{username}, "%")

    </select>
```



什么时候用${ }取值？（常见的使用${}的情况）

1.当sql中**表名**是从参数中取的情况

2.**order by排序语句中**，因为order by 后边必须跟**字段名**，这个字段名不能带引号，如果带引号会被识别会字符串，而不是字段。

<br>

**总结一下，就是当我们需要拼接的变量上不能带单引号时，就必须使用${}。其他情况都尽量使用#{}的方式，因为${}会有sql注入的问题。**




# 1 简介与搭建

## 历史

-    MyBatis最初是Apache的一个开源项目`iBatis`, 2010年6月这个项目由Apache Software Foundation迁移到了Google Code。随着开发团队转投Google Code旗下，iBatis3.x正式更名为MyBatis。代码于2013年11月迁移到Github
-    iBatis一词来源于“internet”和“abatis”的组合，是一个基于Java的持久层框架。iBatis提供的持久层框架包括SQL Maps和Data Access Objects（DAO）



## 特性

1. MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架；
2. MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集；
3. MyBatis 可以使用简单的 XML 或注解用于配置和原始映射，将接口和 Java 的 POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录；
4. MyBatis 是一个 半自动的 ORM（Object Relation Mapping）框架。



## 和其它持久化层技术对比

- JDBC  
  - SQL 夹杂在Java代码中耦合度高，导致硬编码内伤  
  - 维护不易且实际开发需求中 SQL 有变化，频繁修改的情况多见  
  - 代码冗长，开发效率低
- Hibernate 和 JPA
  - 操作简便，开发效率高  
  - 程序中的长难复杂 SQL 需要绕过框架  
  - 内部自动生产的 SQL，不容易做特殊优化  
  - 基于全映射的全自动框架，大量字段的 POJO 进行部分映射时比较困难。  
  - 反射操作太多，导致数据库性能下降
- MyBatis
  - 轻量级，性能出色； 
  - SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据 ； 
  - 开发效率稍逊于HIbernate，但是完全能够接受。



## 搭建

- IDE：idea 2019.2  
- 构建工具：maven 3.5.4  
- MySQL版本：MySQL 5.7  
- MyBatis版本：MyBatis 3.5.7



```bash
# MyBatis 编程步骤

创建 SqlSessionFactory 对象。
通过 SqlSessionFactory 获取 SqlSession 对象。
通过 SqlSession 获得 Mapper 代理对象。
通过 Mapper 代理对象，执行数据库操作。
执行成功，则使用 SqlSession 提交事务。
执行失败，则使用 SqlSession 回滚事务。
最终，关闭会话。
```



### 1）创建 maven 工程

- 打包方式：jar
- 引入依赖

```xml
<dependencies>
	<!-- Mybatis核心 -->
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>3.5.7</version>
	</dependency>
	<!-- junit测试 -->
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.12</version>
		<scope>test</scope>
	</dependency>
	<!-- MySQL驱动 -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>5.1.3</version>
		</dependency>
</dependencies>

```



### 2）创建 MyBatis 的核心配置文件

- 习惯命名为 `mybatis-config.xml`，但只是建议，并非强制要求。整合Spring之后，这个配置文件可以省略。
- 主要用于配置 连接数据库的环境 和 MyBatis 的全局配置信息。
- 存放的位置是 `src/main/resources` 目录下。



```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE configuration  
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"  
"http://mybatis.org/dtd/mybatis-3-config.dtd">  
<configuration>  
	<!--设置连接数据库的环境-->  
	<environments default="development">  
		<environment id="development">  
			<transactionManager type="JDBC"/>  
			<dataSource type="POOLED">  
				<property name="driver" value="com.mysql.cj.jdbc.Driver"/>  
				<property name="url" value="jdbc:mysql://localhost:3306/MyBatis"/>  
				<property name="username" value="root"/>  
				<property name="password" value="123456"/>  
			</dataSource>  
		</environment>  
	</environments>  
  
	<!--引入映射文件-->  
	<mappers>  
		<mapper resource="mappers/UserMapper.xml"/>  
	</mappers>  
</configuration>
```

### 3）创建mapper接口

MyBatis 中的 mapper 接口相当于以前的 dao。区别在于，mapper 仅仅是接口，**不需要提供实现类。**

```java
package com.atguigu.mybatis.mapper;  
  
public interface UserMapper {  
	/**  
	* 添加用户信息  
	*/  
	int insertUser();  
}
```



**问题1：最佳实践中，通常一个 XML 映射文件，都会写一个 Mapper 接口与之对应。请问，这个 Mapper 接口的工作原理是什么？Mapper 接口里的方法，参数不同时，方法能重载吗？（京东）**

Mapper 接口，对应的关系如下：

- 接口的全限名，就是映射文件中的 `"namespace"` 的值；
- 接口的方法名，就是映射文件中 MappedStatement 的 `"id"` 值；
- 接口方法内的参数，就是传递给 SQL 的参数。



Mapper 接口是没有实现类的，当调用接口方法时，接口全限名 + 方法名拼接字符串作为 key 值，可唯一定位一个对应的 MappedStatement 。举例：`com.mybatis3.mappers.StudentDao.findStudentById` ，可以唯一找到 `"namespace"` 为 `com.mybatis3.mappers.StudentDao` 下面 `"id"` 为 `findStudentById` 的 MappedStatement 。



总结来说，在 Mybatis 中，每一个 `<select />`、`<insert />`、`<update />`、`<delete />` 标签，都会被解析为一个 MappedStatement 对象。

```java
// DefaultSqlSession.java

@Override
public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
    try {
        // 获得 MappedStatement 对象
        MappedStatement ms = configuration.getMappedStatement(statement);
        // 执行查询
        executor.query(ms, wrapCollection(parameter), rowBounds, handler);
    } catch (Exception e) {
        throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
    } finally {
        ErrorContext.instance().reset();
    }
}
```



Mapper 接口里的方法，**是不能重载的**，因为是**全限名 + 方法名**的保存和寻找策略。😈 所以有时，想个 Mapper 接口里的方法名，还是蛮闹心的，嘿嘿。



**问题2：Mapper 接口绑定有几种实现方式,分别是怎么实现的?**

- （1）通过 **XML** 里面写 SQL 来绑定。在这种情况下，要指定 XML 映射文件里面的 `"namespace"` 必须为接口的全路径名。
- （2）通过**注解**绑定，就是在接口的方法上面加上 `@Select`、`@Update`、`@Insert`、`@Delete` 注解，里面包含 SQL 语句来绑定。
- （3）是第二种的特例，也是通过**注解**绑定，在接口的方法上面加上 `@SelectProvider`、`@UpdateProvider`、`@InsertProvider`、`@DeleteProvider` 注解，通过 Java 代码，生成对应的动态 SQL 。

实际场景下，最最最推荐的是**第一种**方式。因为，SQL 通过注解写在 Java 代码中，会非常杂乱。而写在 XML 中，更加有整体性，并且可以更加方便的使用 OGNL 表达式。



### 4）创建MyBatis的映射文件

- 相关概念：ORM（Object Relationship Mapping）对象关系映射。  
  - 对象：Java的实体类对象  
  - 关系：关系型数据库  
  - 映射：二者之间的对应关系

| Java概念 | 数据库概念 |
| -------- | ---------- |
| 类       | 表         |
| 属性     | 字段/列    |
| 对象     | 记录/行    |



**映射文件的命名规则：**

- 表所对应的实体类的类名+Mapper.xml

> 例如：表t_user，映射的实体类为User，所对应的映射文件为UserMapper.xml

- 因此一个映射文件对应一个实体类，对应一张表的操作

- MyBatis映射文件用于编写SQL，访问以及操作表中的数据

- MyBatis映射文件存放的位置是`src/main/resources/mappers`目录下

<br>

**MyBatis中可以面向接口操作数据，要保证两个一致：**

- mapper接口的**全类名**和映射文件的**命名空间（namespace）**保持一致
- mapper接口中方法的**方法名**和映射文件中SQL的标签的 `id` 属性保持一致



```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper  
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
<mapper namespace="com.atguigu.mybatis.mapper.UserMapper">  
	<!--int insertUser();-->  
	<insert id="insertUser">  
		insert into t_user values(null,'张三','123',23,'女')  
	</insert>  
</mapper>
```



**问题1：Mybatis 的 XML Mapper文件中，不同的 XML 映射文件，id 是否可以重复？**

要看是否配置了namespace。毕竟`"namespace"` 不是必须的，只是最佳实践而已。`namespace + id` 是作为 `Map<String, MappedStatement>` 的 key 使用的。

- 不同的 XML Mapper 文件，如果配置了 `"namespace"` ，那么 id 可以重复。
  - `"namespace"`不同，`namespace + id` 自然也就不同。
- 如果没有配置 `"namespace"` ，那么 id 不能重复。
  -  id 重复会导致数据互相覆盖。



**问题2：如何获取自动生成的(主)键值?**

不同的数据库，获取自动生成的主键值的方式是不同的。MySQL 有两种方式，其中方式一较为常用。Oracle 有两种方式，序列和触发器。 

```xml
// 方式一，使用 useGeneratedKeys + keyProperty 属性
<insert id="insert" parameterType="Person" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO person(name, pswd)
    VALUE (#{name}, #{pswd})
</insert>
    
// 方式二，使用 `<selectKey />` 标签
<insert id="insert" parameterType="Person">
    <selectKey keyProperty="id" resultType="long" order="AFTER">
        SELECT LAST_INSERT_ID()
    </selectKey>
        
    INSERT INTO person(name, pswd)
    VALUE (#{name}, #{pswd})
</insert>
```



### 5）通过junit测试功能

- SqlSession：代表 Java 程序和**数据库**之间的**会话**。（HttpSession是Java程序和浏览器之间的会话）
- SqlSessionFactory：是生产 SqlSession 的 工厂。
  - 工厂模式：如果创建某一个对象，使用的过程基本固定，那么我们就可以把创建这个对象的相关代码封装到一个“工厂类”中，以后都使用这个工厂类来“生产”我们需要的对象




`SqlSessionFactoryBuilder` `SqlSessionFactory` `SqlSession` `UserMapper`

```java
//读取MyBatis的核心配置文件
InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

//获取SqlSessionFactoryBuilder对象
SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

//通过核心配置文件所对应的字节输入流创建工厂类SqlSessionFactory，生产SqlSession对象
SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

//获取sqlSession，此时通过SqlSession对象所操作的sql都必须手动提交或回滚事务
//SqlSession sqlSession = sqlSessionFactory.openSession();
//创建SqlSession对象，此时通过SqlSession对象所操作的sql都会自动提交  
SqlSession sqlSession = sqlSessionFactory.openSession(true);

//通过代理模式创建UserMapper接口的 【代理实现类对象】
UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//调用UserMapper接口中的方法，就可以根据UserMapper的全类名匹配元素文件，通过调用的方法名匹配映射文件中的SQL标签，
//并执行标签中的SQL语句
int result = userMapper.insertUser();

//提交事务
//sqlSession.commit();
System.out.println("result:" + result);

```



### 6）加入log4j日志功能

1. 添加依赖

```xml
<!-- log4j日志 -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2. 添加 log4j 的配置文件

- log4j 的配置文件名为`log4j.xml`，存放的位置是`src/main/resources`目录下
- 日志的级别：FATAL(致命) > ERROR(错误) > WARN(警告) > INFO(信息) > DEBUG(调试) 从左到右打印的内容越来越详细

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
  
    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8" />
        <layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m (%F:%L) \n" />
        </layout>
    </appender>
  
    <logger name="java.sql">
        <level value="debug" />
    </logger>
  
    <logger name="org.apache.ibatis">
        <level value="info" />
    </logger>
  
    <root>
        <level value="debug" />
        <appender-ref ref="STDOUT" />
    </root>
  
</log4j:configuration>
```



# 2 核心配置文件详解

核心配置文件中的标签必须按照固定的顺序(有的标签可以不写，但顺序一定不能乱)

```xml
properties、settings、typeAliases、typeHandlers、
objectFactory、objectWrapperFactory、reflectorFactory、
plugins、environments、databaseIdProvider、mappers
```

<br>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//MyBatis.org//DTD Config 3.0//EN"
        "http://MyBatis.org/dtd/MyBatis-3-config.dtd">
<configuration>
    <!--(1)引入properties文件，此时就可以${属性名}的方式访问属性值-->
    <properties resource="jdbc.properties"></properties>
  	
  	<!--(2)-->
    <settings>
        <!--将表中字段的下划线自动转换为驼峰-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <!--开启延迟加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
    </settings>
  	
  	<!--(3)别名-->
    <typeAliases>
        <!--
        typeAlias：设置某个具体的类型的别名
        		属性：
        		type：需要设置别名的类型的全类名
        		alias：设置此类型的别名，且别名不区分大小写。若不设置此属性，该类型拥有默认的别名，即类名
        -->
        <!--<typeAlias type="com.atguigu.mybatis.bean.User"></typeAlias>-->
        <!--<typeAlias type="com.atguigu.mybatis.bean.User" alias="user"></typeAlias>-->
      
        <!--以包为单位，设置改包下所有的类型都拥有默认的别名，即类名且不区分大小写-->
        <package name="com.atguigu.mybatis.bean"/>
    </typeAliases>
  
    <!--（4）数据库环境
 		environments：设置多个连接数据库的环境
    		属性：
	    	default：设置默认使用的环境的id
    -->
    <environments default="mysql_test">
        <!--
        environment：设置具体的连接数据库的环境信息
        属性：
	        id：设置环境的唯一标识，可通过environments标签中的default设置某一个环境的id，表示默认使用的环境
        -->
        <environment id="mysql_test">
            <!--
            transactionManager：设置事务管理方式
            属性：
	            type：设置事务管理方式，type="JDBC|MANAGED"
	            type="JDBC"：设置当前环境的事务管理都必须手动处理
	            type="MANAGED"：设置事务被管理，例如spring中的AOP
            -->
            <transactionManager type="JDBC"/>
            <!--
            dataSource：设置数据源
            属性：
	            type：设置数据源的类型，type="POOLED|UNPOOLED|JNDI"
	            type="POOLED"：使用数据库连接池，即会将创建的连接进行缓存，下次使用可以从缓存中直接获取，不需要重新创建
	            type="UNPOOLED"：不使用数据库连接池，即每次使用连接都需要重新创建
	            type="JNDI"：调用上下文中的数据源
            -->
            <dataSource type="POOLED">
                <!--设置驱动类的全类名-->
                <property name="driver" value="${jdbc.driver}"/>
                <!--设置连接数据库的连接地址-->
                <property name="url" value="${jdbc.url}"/>
                <!--设置连接数据库的用户名-->
                <property name="username" value="${jdbc.username}"/>
                <!--设置连接数据库的密码-->
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
  	
  	<!--(5)别名-->
    <!--引入映射文件-->
    <mappers>
        <mapper resource="UserMapper.xml"/>
        <!--
        以包为单位，将包下所有的映射文件引入核心配置文件
        注意：1.此方式必须保证mapper接口和mapper映射文件必须在相同的包下  2. mapper接口要和mapper映射文件的名字一致
        -->
        <package name="com.atguigu.mybatis.mapper"/>
    </mappers>
</configuration>
```



# 3 MyBatis的增删改查

## 3.1 基础

通过`insert` `delete` `update` `select` 标签。



1. 添加

   ```xml
   <!--int insertUser();-->
   <insert id="insertUser">
   	insert into t_user values(null,'admin','123456',23,'男','12345@qq.com')
   </insert>
   ```

2. 删除

   ```xml
   <!--int deleteUser();-->
    <delete id="deleteUser">
        delete from t_user where id = 6
    </delete>
   ```

3. 修改

   ```xml
   <!--int updateUser();-->
    <update id="updateUser">
        update t_user set username = '张三' where id = 5
    </update>
   ```

4. 查询一个实体类对象

   ```xml
   <!--User getUserById();-->  
   <select id="getUserById" resultType="com.atguigu.mybatis.bean.User">  
   	select * from t_user where id = 2  
   </select>
   ```

5. 查询集合

   ```xml
   <!--List<User> getUserList();-->
   <select id="getUserList" resultType="com.atguigu.mybatis.bean.User">
   	select * from t_user
   </select>
   ```



注意：

- 查询的标签select必须设置属性 resultType 或 resultMap ，用于设置实体类和数据库表的映射关系  

  - resultType：自动映射，用于属性名和表中字段名一致的情况  

  - resultMap：自定义映射，用于一对多或多对一或字段名和属性名不一致的情况  

- 当查询的数据为多条时，不能使用实体类作为返回值，只能使用集合，否则会抛出异常TooManyResultsException；但是若查询的数据只有一条，可以使用实体类或集合作为返回值

### XML 映射文件中，除了常见的 select | insert | update | delete标 签之外，还有哪些标签？

如下部分，可见 [《MyBatis 文档 —— Mapper XML 文件》](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html) ：

- `<cache />` 标签，给定命名空间的缓存配置。
  - `<cache-ref />` 标签，其他命名空间缓存配置的引用。
- `<resultMap />` 标签，是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- `<parameterMap />` 标签，已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。~~
- `<sql />` 标签，可被其他语句引用的可重用语句块。
  - `<include /> `标签，引用 <sql /> 标签的语句。
- `<selectKey />` 标签，不支持自增的主键生成策略标签。

如下部分，可见 [《MyBatis 文档 —— 动态 SQL》](http://www.mybatis.org/mybatis-3/zh/dynamic-sql.html) ：

- `<if />`
- `<choose />`、`<when />`、`<otherwise />`
- `<trim />`、`<where />`、`<set />`
- `<foreach />`
- `<bind />`



## 3.2 查询

### 查询一个实体类对象



```java
/**
 * 根据用户id查询用户信息
 * @param id
 * @return
 */
User getUserById(@Param("id") int id);
```



```java
<!--User getUserById(@Param("id") int id);-->
  
<select id="getUserById" resultType="User">
	select * from t_user where id = #{id}
</select>
```



### 查询一个List集合

```java
/**
 * 查询所有用户信息
 * @return
 */
List<User> getUserList();
```



```java
<!--List<User> getUserList();-->
  
<select id="getUserList" resultType="User">
	select * from t_user
</select>
```



### 查询单个数据



```java
/**  
 * 查询用户的总记录数  
 * @return  
 * 在MyBatis中，对于Java中常用的类型都设置了类型别名  
 * 例如：java.lang.Integer-->int|integer  
 * 例如：int-->_int|_integer  
 * 例如：Map-->map,List-->list  
 */  
int getCount();
```



```java
<!--int getCount();-->
<select id="getCount" resultType="_integer">
	select count(id) from t_user
</select>
```



### 查询一条数据为map集合

```java
/**  
 * 根据用户id查询用户信息为map集合  
 * @param id  
 * @return  
 */  
Map<String, Object> getUserToMap(@Param("id") int id);
```

<br>

```java
<!--Map<String, Object> getUserToMap(@Param("id") int id);-->
  
<select id="getUserToMap" resultType="map">
	select * from t_user where id = #{id}
</select>
<!--结果：{password=123456, sex=男, id=1, age=23, username=admin}-->
```



### 查询多条数据为map集合

#### 方法一

```java
/**  
 * 查询所有用户信息为map集合  
 * @return  
 * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，此时可以将这些map放在一个list集合中获取  
 */  
List<Map<String, Object>> getAllUserToMap();
```

<br>

```java
<!--Map<String, Object> getAllUserToMap();-->  
<select id="getAllUserToMap" resultType="map">  
	select * from t_user  
</select>
<!--
	结果：
	[{password=123456, sex=男, id=1, age=23, username=admin},
	{password=123456, sex=男, id=2, age=23, username=张三},
	{password=123456, sex=男, id=3, age=23, username=张三}]
-->
```





#### 方法二

```java
/**
 * 查询所有用户信息为map集合
 * @return
 * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，并且最终要以一个map的方式返回数据，此时需要通过@MapKey注解设置map集合的键，值是每条数据所对应的map集合
 */
@MapKey("id")
Map<String, Object> getAllUserToMap();
```

<br>

```java
<!--Map<String, Object> getAllUserToMap();-->
<select id="getAllUserToMap" resultType="map">
	select * from t_user
</select>
<!--
	结果：
	{
	1={password=123456, sex=男, id=1, age=23, username=admin},
	2={password=123456, sex=男, id=2, age=23, username=张三},
	3={password=123456, sex=男, id=3, age=23, username=张三}
	}
-->
```





## 3.3 特殊SQL的执行

### 模糊查询

```java
/**
 * 根据用户名进行模糊查询
 * @param username 
 * @return java.util.List<com.atguigu.mybatis.pojo.User>
 * @date 2022/2/26 21:56
 */
List<User> getUserByLike(@Param("username") String username);
```

<br>

```xml
<!--List<User> getUserByLike(@Param("username") String username);-->
<select id="getUserByLike" resultType="User">
	<!--select * from t_user where username like '%${mohu}%'-->  
	<!--select * from t_user where username like concat('%',#{mohu},'%')-->  
	select * from t_user where username like "%"#{mohu}"%"
</select>
```



其中`select * from t_user where username like "%"#{mohu}"%"`是最常用的



### 批量删除

只能使用\${}，如果使用#{}，则解析后的sql语句为`delete from t_user where id in ('1,2,3')`，这样是将`1,2,3`看做是一个整体，只有id为`1,2,3`的数据会被删除。正确的语句应该是`delete from t_user where id in (1,2,3)`，或者`delete from t_user where id in ('1','2','3')`

```java
/**
 * 根据id批量删除
 * @param ids 
 * @return int
 * @date 2022/2/26 22:06
 */
int deleteMore(@Param("ids") String ids);
```

<br>

```xml
<delete id="deleteMore">
	delete from t_user where id in (${ids})
</delete>
```



```java
//测试类
@Test
public void deleteMore() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
	int result = mapper.deleteMore("1,2,3,8");
	System.out.println(result);
}
```



### 动态设置表名

只能使用${}，因为表名不能加单引号

```java
/**
 * 查询指定表中的数据
 * @param tableName 
 * @return java.util.List<com.atguigu.mybatis.pojo.User>
 * @date 2022/2/27 14:41
 */
List<User> getUserByTable(@Param("tableName") String tableName);
```

<br>

```xml
<!--List<User> getUserByTable(@Param("tableName") String tableName);-->
<select id="getUserByTable" resultType="User">
	select * from ${tableName}
</select>
```



### 添加功能 获取自增的主键

t_clazz(clazz_id,clazz_name)  

t_student(student_id,student_name,clazz_id)  

1. 添加班级信息  
2. 获取新添加的班级的id  
3. 为班级分配学生，即将某学生的班级id修改为新添加的班级的id



```java
/**
 * 添加用户信息
 * @param user 
 * @date 2022/2/27 15:04
 */
void insertUser(User user);
```

在mapper.xml中设置两个属性

- useGeneratedKeys：设置使用自增的主键  
- keyProperty：因为增删改有统一的返回值是受影响的行数，因此只能将获取的自增的主键放在传输的参数user对象的某个属性中

```xml
<!--void insertUser(User user);-->
<insert id="insertUser" useGeneratedKeys="true" keyProperty="id">
	insert into t_user values (null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```

<br>

```java
//测试类
@Test
public void insertUser() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
	User user = new User(null, "ton", "123", 23, "男", "123@321.com");
  
	mapper.insertUser(user);
	System.out.println(user);
	//输出：user{id=10, username='ton', password='123', age=23, sex='男', email='123@321.com'}，自增主键存放到了user的id属性中
}
```





## 3.4 自定义映射resultMap

### resultMap处理字段和属性的映射关系

若字段名和实体类中的属性名不一致，则可以通过resultMap设置自定义映射

即使字段名和属性名一致的属性也要映射，也就是全部属性都要列出来

```xml
<!--
		resultMap：设置自定义映射 
		属性： 
		id：表示自定义映射的唯一标识 
		type：查询的数据要映射的实体类的类型 
		
        子标签： 
        id：设置主键的映射关系 
        result：设置普通字段的映射关系 
        association：设置多对一的映射关系 
        collection：设置一对多的映射关系 

            属性： 
            property：设置映射关系中实体类中的属性名 
            column：设置映射关系中表中的字段名 
-->

<resultMap id="empResultMap" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
</resultMap>

<!--List<Emp> getAllEmp();-->
<select id="getAllEmp" resultMap="empResultMap">
	select * from t_emp
</select>
```



若字段名和实体类中的属性名不一致，但是字段名符合数据库的规则（使用_），实体类中的属性名符合Java的规则（使用驼峰）。

此时也可通过以下两种方式处理字段名和实体类中的属性的映射关系  

- a>可以通过为字段起别名的方式，保证和实体类中的属性名保持一致

- 可以在MyBatis的核心配置文件中设置一个全局配置信息`mapUnderscoreToCamelCase`，可以在查询表中数据时，自动将_类型的字段名转换为驼峰

```xml
可以在MyBatis的核心配置文件中的`setting`标签中，设置一个全局配置信息mapUnderscoreToCamelCase
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```

例如：字段名user_name，设置了mapUnderscoreToCamelCase，此时字段名就会转换为userName



### 多对一映射处理

查询员工信息以及员工所对应的部门信息

#### 方式一：级联方式处理映射关系

```xml
<resultMap id="empAndDeptResultMapOne" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
  
	<result property="dept.did" column="did"></result>
	<result property="dept.deptName" column="dept_name"></result>
</resultMap>

<!--Emp getEmpAndDept(@Param("eid")Integer eid);-->

<select id="getEmpAndDept" resultMap="empAndDeptResultMapOne">
  select emp.*,dept.* from t_emp emp left join t_dept dept 
  		on emp.did = dept.did 
  		where emp.eid = #{eid}
</select>
```



#### 方式二：使用association处理映射关系

```xaml
<resultMap id="empAndDeptResultMapTwo" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
  
	<association property="dept" javaType="Dept">
		<id property="did" column="did"></id>
		<result property="deptName" column="dept_name"></result>
	</association>
</resultMap>

<!--Emp getEmpAndDept(@Param("eid")Integer eid);-->

<select id="getEmpAndDept" resultMap="empAndDeptResultMapTwo">
	select emp.*,dept.* from t_emp emp left join t_dept dept 
  		on emp.did = dept.did 
  		where emp.eid = #{eid}
</select>
```



#### 方式三：分步查询

1）查询员工信息

```java
//EmpMapper里的方法
/**
 * 通过分步查询，获得员工及所对应的部门信息
 * 分步查询第一步：查询员工信息
 */
Emp getEmpAndDeptByStepOne(@Param("eid") Integer eid);
```

<br>

```xml
<resultMap id="empAndDeptByStepResultMap" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
  
  <!--
			select：设置分步查询，查询某个属性的值的sql的标识（namespace.sqlId） 
			column：将sql以及查询结果中的某个字段设置为分步查询的条件 
	-->
	<association property="dept"
				 select="com.atguigu.mybatis.mapper.DeptMapper.getEmpAndDeptByStepTwo"
				 column="did">
  </association>
</resultMap>

<!--Emp getEmpAndDeptByStepOne(@Param("eid") Integer eid);-->
<select id="getEmpAndDeptByStepOne" resultMap="empAndDeptByStepResultMap">
	select * from t_emp where eid = #{eid}
</select>
```

2）根据员工所对应的部门id查询部门信息

```java
//DeptMapper里的方法
/**
 * 通过分步查询，员工及所对应的部门信息
 * 分步查询第二步：通过did查询员工对应的部门信息
 */
Dept getEmpAndDeptByStepTwo(@Param("did") Integer did);
```

<br>

```xml
<!--此处的resultMap仅是处理字段和属性的映射关系-->
<resultMap id="EmpAndDeptByStepTwoResultMap" type="Dept">
	<id property="did" column="did"></id>
	<result property="deptName" column="dept_name"></result>
</resultMap>

<!--Dept getEmpAndDeptByStepTwo(@Param("did") Integer did);-->
<select id="getEmpAndDeptByStepTwo" resultMap="EmpAndDeptByStepTwoResultMap">
	select * from t_dept where did = #{did}
</select>
```



### 一对多映射处理

```java
public class Dept {
    private Integer did;
    private String deptName;
    private List<Emp> emps;
	//...构造器、get、set方法等
}
```



#### 方式一：collection

```xml
<!--
	根据部门id查新部门以及部门中的员工信息
-->

<resultMap id="DeptAndEmpResultMap" type="Dept">
	<id property="did" column="did"></id>
	<result property="deptName" column="dept_name"></result>
  
  <!--
				ofType：设置collection标签所处理的集合属性中存储数据的类型 
	-->
	<collection property="emps" ofType="Emp">
		<id property="eid" column="eid"></id>
		<result property="empName" column="emp_name"></result>
		<result property="age" column="age"></result>
		<result property="sex" column="sex"></result>
		<result property="email" column="email"></result>
	</collection>
</resultMap>

<!--Dept getDeptAndEmp(@Param("did") Integer did);-->
<select id="getDeptAndEmp" resultMap="DeptAndEmpResultMap">
  select dept.*,emp.* from t_dept dept left join t_emp emp 
  		on dept.did = emp.did 
  		where dept.did = #{did}
</select>
```





#### 方式二：分步查询

1. 查询部门信息

```java
/**
 * 通过分步查询，查询部门及对应的所有员工信息
 * 分步查询第一步：查询部门信息
 */
Dept getDeptAndEmpByStepOne(@Param("did") Integer did);
```

<br>

```xml
<resultMap id="DeptAndEmpByStepOneResultMap" type="Dept">
	<id property="did" column="did"></id>
	<result property="deptName" column="dept_name"></result>
	<collection property="emps"
				select="com.atguigu.mybatis.mapper.EmpMapper.getDeptAndEmpByStepTwo"
				column="did">
  </collection>
</resultMap>

<!--Dept getDeptAndEmpByStepOne(@Param("did") Integer did);-->
<select id="getDeptAndEmpByStepOne" resultMap="DeptAndEmpByStepOneResultMap">
	select * from t_dept where did = #{did}
</select>
```



2. 根据部门id查询部门中的所有员工

```java
/**
 * 通过分步查询，查询部门及对应的所有员工信息
 * 分步查询第二步：根据部门id查询部门中的所有员工
 */
List<Emp> getDeptAndEmpByStepTwo(@Param("did") Integer did);
```

<br>

```xml
<!--List<Emp> getDeptAndEmpByStepTwo(@Param("did") Integer did);-->
<select id="getDeptAndEmpByStepTwo" resultType="Emp">
	select * from t_emp where did = #{did}
</select>
```





### 延迟加载

分步查询的优点：可以实现延迟加载，但是必须在核心配置文件中设置全局配置信息：

- lazyLoadingEnabled：延迟加载的全局开关。当开启时，所有关联对象都会延迟加载  
- aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性。 否则，每个属性会按需加载

<br>

此时就可以实现按需加载，获取的数据是什么，就只会执行相应的sql。此时可通过association和collection中的fetchType属性设置当前的分步查询是否使用延迟加载，fetchType="lazy(延迟加载)|eager(立即加载)"





# 4 MyBatis获取参数值（重点）

## 4.1 #{} 和 ${} 的区别是什么？

- ${}：本质就是字符串替换。
  - 可以用于 XML 标签属性值
  - 可以用于SQL 内部（不推荐，有sql注入风险）

```xml
<dataSource type="UNPOOLED">
    <property name="driver" value="${driver}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${username}"/>
</dataSource>

<select id="getSubject3" parameterType="Integer" resultType="Subject">
    SELECT * FROM subject
    WHERE id = ${id}
</select>
```

- #{}：本质就是sql的参数占位符
  - Mybatis 会将 SQL 中的 `#{}` 替换为 `?` 号
  - 在 SQL 执行前会使用 PreparedStatement 的参数设置方法，按序给 SQL 的 `?` 号占位符设置参数值，比如 `ps.setInt(0, parameterValue)` 。 所以，`#{}` 是**预编译处理**，可以有效防止 SQL 注入，提高系统安全性。

## 4.2 单个字面量类型的方法参数

可以使用 \${}和#{}以任意的名称（最好见名识意）获取参数的值，注意${}需要手动加单引号



```xml
<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">
	select * from t_user where username = #{username}
</select>
```

```xml
<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">  
	select * from t_user where username = '${username}'  
</select>
```



## 4.3 多个字面量类型的方法参数

若mapper接口中的方法参数为多个时，MyBatis会自动将这些参数放在一个**map**集合中

```bash
1. 以arg0,arg1...为键，以参数为值；
2. 以param1,param2...为键，以参数为值；
```

因此只需要通过\${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号。

使用arg或者param都行，要注意的是，**arg是从arg0开始的，param是从param1开始的**

```xml
<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">  
	select * from t_user where username = #{arg0} and password = #{arg1}  
</select>
```



```xml
<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">
	select * from t_user where username = '${param1}' and password = '${param2}'
</select>
```



## 4.4 map集合类型的参数

若mapper接口中的方法需要的参数为多个时，此时可以手动创建map集合，将这些数据放在map中

只需要通过\${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号

```xml
<!--User checkLoginByMap(Map<String,Object> map);-->
<select id="checkLoginByMap" resultType="User">
	select * from t_user where username = #{username} and password = #{password}
</select>
```

<br>

```java
@Test
public void checkLoginByMap() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
  
	Map<String,Object> map = new HashMap<>();
	map.put("usermane","admin");
	map.put("password","123456");
  
	User user = mapper.checkLoginByMap(map);
	System.out.println(user);
}
```



## 4.5 实体类对象类型的方法参数

可以使用\${}和#{}，**通过访问实体类对象中的属性名获取属性值**，注意${}需要手动加单引号

```xml
<!--int insertUser(User user);-->
<insert id="insertUser">
	insert into t_user values(null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```

<br>

```java
@Test
public void insertUser() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
  
	User user = new User(null,"Tom","123456",12,"男","123@321.com");
	mapper.insertUser(user);
}
```



### 当实体类中的属性名和表中的字段名不一样 ，怎么办？

#### 方法1：在查询的 SQL 语句中定义字段名的别名，让字段名的别名和实体类的属性名一致。

```xml
<select id="selectOrder" parameterType="Integer" resultType="Order"> 
    SELECT order_id AS id, order_no AS orderno, order_price AS price 
    FROM orders 
    WHERE order_id = #{id}
</select>
```

建议：

- 1、数据库的关键字，统一使用大写，例如：`SELECT`、`AS`、`FROM`、`WHERE` 。
- 2、每 5 个查询字段换一行，保持整齐。
- 3、`,` 的后面，和 `=` 的前后，需要有空格，更加清晰。
- 4、`SELECT`、`FROM`、`WHERE` 等，单独一行，高端大气。

#### 方法2：通过配置实现自动的下划线转驼峰

大多数场景下，数据库字段名和实体类中的属性名差，主要是前者为**下划线风格**，后者为**驼峰风格**。在这种情况下，可以直接配置如下，实现自动的下划线转驼峰的功能。

```xml
<setting name="logImpl" value="LOG4J"/>
    <setting name="mapUnderscoreToCamelCase" value="true" />
</settings>
```

也就说，约定大于配置。非常推荐！

#### 方法3：通过 `<resultMap>` 来映射字段名和实体类属性名的一一对应的关系。

```xml
<resultMap type="me.gacl.domain.Order" id=”OrderResultMap”> 
    <!–- 用 id 属性来映射主键字段 -–> 
    <id property="id" column="order_id"> 
    <!–- 用 result 属性来映射非主键字段，property 为实体类属性名，column 为数据表中的属性 -–> 
    <result property="orderNo" column ="order_no" /> 
    <result property="price" column="order_price" /> 
</resultMap>

<select id="getOrder" parameterType="Integer" resultMap="OrderResultMap">
    SELECT * 
    FROM orders 
    WHERE order_id = #{id}
</select>
```

- 此处 `SELECT *` 仅仅作为示例只用，实际场景下，千万千万千万不要这么干。用多少字段，查询多少字段。
- 相比第一种，第三种的**重用性**会好一些。



## 4.6 使用@Param注解标识参数

可以通过@Param注解标识mapper接口中的方法参数，此时会将这些参数放在**map集合**中。

```bash
1. 以@Param注解的value属性值为键，以参数为值；
2. 以param1,param2...为键，以参数为值；
```

只需要通过\${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号

```xml
接口中
<!--User CheckLoginByParam(@Param("username") String username, @Param("password") String password);-->

xml中
<select id="CheckLoginByParam" resultType="User">
  select * from t_user where username = #{username} and password = #{password}
</select>
```



```java
@Test
public void checkLoginByParam() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
  
	mapper.CheckLoginByParam("admin","123456");
}
```

## 最后总结⭐️

建议分成两种情况进行处理：

1. 实体类类型的参数；
2. 使用 @Param 标识参数。



# 5 动态SQL

Mybatis框架的动态SQL技术是一种**根据特定条件动态拼装SQL语句**的功能，它存在的意义是为了解决拼接SQL语句字符串时的痛点问题。

## if（通过test属性的表达式进行判断）

if标签可通过test属性的表达式进行判断，若表达式的结果为true，则标签中的内容会执行；反之标签中的内容不会执行

> 这个`1=1`可以用来拼接`and`语句，例如：当empName为null时

```xml
<!--List<Emp> getEmpByCondition(Emp emp);-->

<select id="getEmpByCondition" resultType="Emp">
	select * from t_emp where 1=1
	<if test="empName != null and empName !=''">
		and emp_name = #{empName}
	</if>
	<if test="age != null and age !=''">
		and age = #{age}
	</if>
	<if test="sex != null and sex !=''">
		and sex = #{sex}
	</if>
	<if test="email != null and email !=''">
		and email = #{email}
	</if>
</select>
```

## where（结合if使用）

where和if一般结合使用：

- 若where标签中的if条件都不满足，则where标签**没有任何功能，即不会添加where关键字**  
- 若where标签中的if条件满足，则where标签会**自动添加where关键字，并将条件最前方多余的and/or去掉**

注意：where标签不能去掉条件最后多余的and

```xml
<!--List<Emp> getEmpByCondition(Emp emp);-->

<select id="getEmpByCondition" resultType="Emp">
	select * from t_emp
	<where>
		<if test="empName != null and empName !=''">
			emp_name = #{empName}
		</if>
		<if test="age != null and age !=''">
			and age = #{age}
		</if>
		<if test="sex != null and sex !=''">
			and sex = #{sex}
		</if>
		<if test="email != null and email !=''">
			and email = #{email}
		</if>
	</where>
</select>
```



## trim（去掉或添加标签中的内容）

trim用于去掉或添加标签中的内容。常用属性有：

| 属性            | 作用                                 |
| --------------- | ------------------------------------ |
| prefix          | 在trim标签中的内容的前面添加某些内容 |
| suffix          | 在trim标签中的内容的后面添加某些内容 |
| prefixOverrides | 在trim标签中的内容的前面去掉某些内容 |
| suffixOverrides | 在trim标签中的内容的后面去掉某些内容 |



```xml
<!--List<Emp> getEmpByCondition(Emp emp);-->

<select id="getEmpByCondition" resultType="Emp">
	select * from t_emp
	<trim prefix="where" suffixOverrides="and|or">
		<if test="empName != null and empName !=''">
			emp_name = #{empName} and
		</if>
		<if test="age != null and age !=''">
			age = #{age} and
		</if>
		<if test="sex != null and sex !=''">
			sex = #{sex} or
		</if>
		<if test="email != null and email !=''">
			email = #{email}
		</if>
	</trim>
</select>
```



若trim中的标签都不满足条件，则trim标签没有任何效果，也就是只剩下`select * from t_emp`

```java
//测试类
@Test
public void getEmpByCondition() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
  
	List<Emp> emps= mapper.getEmpByCondition(new Emp(null, "张三", null, null, null, null));
	System.out.println(emps);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220409184519417.png)



## choose、when、otherwise（相当于 if...else  if..else）

相当于`if a else if b else if c else d`，只会执行其中一个

```xml
<select id="getEmpByChoose" resultType="Emp">
	select * from t_emp
	<where>
		<choose>
        <when test="empName != null and empName != ''">
          emp_name = #{empName}
        </when>
        <when test="age != null and age != ''">
          age = #{age}
        </when>
        <when test="sex != null and sex != ''">
          sex = #{sex}
        </when>
        <when test="email != null and email != ''">
          email = #{email}
        </when>
        <otherwise>
          did = 1
        </otherwise>
		</choose>
	</where>
</select>
```

<br>

```java
@Test
public void getEmpByChoose() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
  
	List<Emp> emps = mapper.getEmpByChoose(new Emp(null, "张三", 23, "男", "123@qq.com", null));
	System.out.println(emps);
}
```

![image-20220409184841599](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220409184841599.png)



## foreach

| 属性       | 作用                                                      |
| ---------- | --------------------------------------------------------- |
| collection | 设置要循环的数组或集合                                    |
| item       | 表示集合或数组中的每一个数据                              |
| separator  | 设置循环体之间的分隔符，分隔符前后默认有一个空格，如` , ` |
| open       | 设置foreach标签中的内容的开始符                           |
| close      | 设置foreach标签中的内容的结束符                           |



.xml

```xml
<!--int deleteMoreByArray(Integer[] eids);-->
<delete id="deleteMoreByArray">
	delete from t_emp where eid in
	<foreach collection="eids" item="eid" separator="," open="(" close=")">
		#{eid}
	</foreach>
</delete>

<!--int insertMoreByList(@Param("emps") List<Emp> emps);-->
<insert id="insertMoreByList">
	insert into t_emp values
	<foreach collection="emps" item="emp" separator=",">
		(null,#{emp.empName},#{emp.age},#{emp.sex},#{emp.email},null)
	</foreach>
</insert>

<!--int deleteMoreByArray(int[] eids);--> 
<delete id="deleteMoreByArray"> 
  	delete from t_emp where 
  	<foreach collection="eids" item="eid" separator="or"> 
      	eid = #{eid} 
  	</foreach>
</delete>

<!--int deleteMoreByArray(Integer[] eids);-->
<delete id="deleteMoreByArray">
    delete from t_emp where eid in
    <foreach collection="eids" item="eid" separator="," open="(" close=")">
      	#{eid}
    </foreach>
</delete>

```



.java

```java
@Test
public void deleteMoreByArray() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
	int result = mapper.deleteMoreByArray(new Integer[]{6, 7, 8, 9});
	System.out.println(result);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/foreach%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C1.png)



```java
@Test
public void insertMoreByList() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
	Emp emp1 = new Emp(null,"a",1,"男","123@321.com",null);
	Emp emp2 = new Emp(null,"b",1,"男","123@321.com",null);
	Emp emp3 = new Emp(null,"c",1,"男","123@321.com",null);
  
	List<Emp> emps = Arrays.asList(emp1, emp2, emp3);
	int result = mapper.insertMoreByList(emps);
	System.out.println(result);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/foreach%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C2.png)







## SQL片段的声明和引用

sql片段：可以记录一段公共sql片段，在使用的地方通过 `include` 标签进行引入。

```xml
<!--List<Emp> getEmpByCondition(Emp emp);-->
<sql id="empColumns">
  	eid,emp_name,age,sex,email
</sql>

<select id="getEmpByCondition" resultType="Emp">
	select <include refid="empColumns"></include> from t_emp
</select>
```





# 6 MyBatis的缓存机制

转载于 博客园 [吴振照](https://gitee.com/link?target=https%3A%2F%2Fhome.cnblogs.com%2Fu%2Fwuzhenzhao%2F) 的博客：[https://www.cnblogs.com/wuzhenzhao/p/11103043.html](https://gitee.com/link?target=https%3A%2F%2Fwww.cnblogs.com%2Fwuzhenzhao%2Fp%2F11103043.html)

## 前言

缓存是一般的ORM 框架都会提供的功能，目的就是提升查询的效率和减少数据库的压力。跟Hibernate 一样，MyBatis 也有一级缓存和二级缓存，并且预留了集成第三方缓存的接口。

<br>

缓存体系结构：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220531190312489.png)



MyBatis 跟缓存相关的类都在cache 包里面，其中有一个Cache 接口，只有一个默认的实现类 PerpetualCache，它是用HashMap 实现的。我们可以通过 以下类找到这个缓存的庐山真面目

```bash
DefaultSqlSession

　　-> BaseExecutor

　　　　-> PerpetualCache localCache

　　　　　　->private Map<Object, Object> cache = new HashMap<>();
```

除此之外，还有很多的装饰器，通过这些装饰器可以额外实现很多的功能：回收策略、日志记录、定时刷新等等。

但是无论怎么装饰，经过多少层装饰，最后使用的还是基本的实现类（默认PerpetualCache）。可以通过 CachingExecutor 类 Debug 去查看。



所有的缓存实现类总体上可分为三类：基本缓存、淘汰算法缓存、装饰器缓存。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1383365-20190628172253737-1751427739.png)



## MyBatis的一级缓存（本地缓存）

- 一级缓存也叫本地缓存，MyBatis 的一级缓存是在会话（SqlSession）层面进行缓存的。
  - 通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据，就会从缓存中直接获取，不会从数据库重新访问  

- MyBatis 的一级缓存是默认开启的，不需要任何的配置。

首先我们必须去弄清楚一个问题，在MyBatis 执行的流程里面，涉及到这么多的对象，那么缓存PerpetualCache 应该放在哪个对象里面去维护？如果要在同一个会话里面共享一级缓存，这个对象肯定是在SqlSession 里面创建的，作为SqlSession 的一个属性。

DefaultSqlSession 里面只有两个属性，Configuration 是全局的，所以缓存只可能放在Executor 里面维护——SimpleExecutor/ReuseExecutor/BatchExecutor 的父类BaseExecutor 的构造函数中持有了PerpetualCache。在同一个会话里面，多次执行相同的SQL 语句，会直接从内存取到缓存的结果，不会再发送SQL 到数据库。但是不同的会话里面，即使执行的SQL 一模一样（通过一个Mapper 的同一个方法的相同参数调用），也不能使用到一级缓存。

<br>

每当我们使用MyBatis开启一次和数据库的会话，MyBatis会创建出一个SqlSession对象表示一次数据库会话。

在对数据库的一次会话中，我们有可能会反复地执行完全相同的查询语句，如果不采取一些措施的话，每一次查询都会查询一次数据库,而我们在极短的时间内做了完全相同的查询，那么它们的结果极有可能完全相同，由于查询一次数据库的代价很大，这有可能造成很大的资源浪费。

为了解决这一问题，减少资源的浪费，MyBatis会在表示会话的SqlSession对象中建立一个简单的缓存，将每次查询到的结果结果缓存起来，当下次查询的时候，如果判断先前有个完全一样的查询，会直接从缓存中直接将结果取出，返回给用户，不需要再进行一次数据库查询了。

如下图所示，MyBatis会在一次会话的表示----一个SqlSession对象中创建一个本地缓存(local cache)，对于每一次查询，都会尝试根据查询的条件去本地缓存中查找是否在缓存中，如果在缓存中，就直接从缓存中取出，然后返回给用户；否则，从数据库读取数据，将查询结果存入缓存并返回给用户。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220531204625468.png)

一级缓存的生命周期有多长？

1. MyBatis在开启一个数据库会话时，会 创建一个新的SqlSession对象，SqlSession对象中会有一个新的Executor对象，Executor对象中持有一个新的PerpetualCache对象；当会话结束时，SqlSession对象及其内部的Executor对象还有PerpetualCache对象也一并释放掉。
2. 如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用；
3. 如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用；
4. SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该对象可以继续使用；

SqlSession 一级缓存的工作流程：

1. 对于某个查询，根据statementId,params,rowBounds来构建一个key值，根据这个key值去缓存Cache中取出对应的key值存储的缓存结果
2. 判断从Cache中根据特定的key值取的数据数据是否为空，即是否命中；
3. 如果命中，则直接将缓存结果返回；
4. 如果没命中：
5. 1. 去数据库中查询数据，得到查询结果；
   2. 将key和查询到的结果分别作为key,value对存储到Cache中；
   3. 将查询结果返回；



接下来我们来验证一下，MyBatis 的一级缓存到底是不是只能在一个会话里面共享，以及跨会话（不同session）操作相同的数据会产生什么问题。判断是否命中缓存：如果再次发送SQL 到数据库执行，说明没有命中缓存；如果直接打印对象，说明是从内存缓存中取到了结果。

1、在同一个session 中共享（不同session 不能共享）

```java
//同Session
SqlSession session1 = sqlSessionFactory.openSession();
BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
System.out.println(mapper1.selectBlogById(1002));
System.out.println(mapper1.selectBlogById(1002));
```

执行以上sql我们可以看到控制台打印如下信息（需配置mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl），会发现我们两次的查询就发送了一次查询数据库的操作，这说明了缓存在发生作用：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1383365-20190628173854959-1659491558.png)

PS：一级缓存在BaseExecutor 的query()——queryFromDatabase()中存入。在queryFromDatabase()之前会get()。

```java
public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
        ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
　　　　。。。。。。try {
                ++this.queryStack;//从缓存中获取
                list = resultHandler == null ? (List)this.localCache.getObject(key) : null;
                if (list != null) {
                    this.handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
                } else {//缓存中获取不到，查询数据库
                    list = this.queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
                }
　　　　。。。。。。
    }
```

2、同一个会话中，update（包括delete）会导致一级缓存被清空

```java
//同Session
SqlSession session1 = sqlSessionFactory.openSession();
BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
System.out.println(mapper1.selectBlogById(1002));
Blog blog3 = new Blog();
blog3.setBid(1002);
blog3.setName("mybatis缓存机制修改");
mapper1.updateBlog(blog3);
session1.commit();// 注意要提交事务，否则不会清除缓存
System.out.println(mapper1.selectBlogById(1002));
```

一级缓存是在BaseExecutor 中的update()方法中调用clearLocalCache()清空的（无条件）

```java
public int update(MappedStatement ms, Object parameter) throws SQLException {
        ErrorContext.instance().resource(ms.getResource()).activity("executing an update").object(ms.getId());
        if (this.closed) {
            throw new ExecutorException("Executor was closed.");
        } else {　　　　　　  //清除本地缓存
            this.clearLocalCache();
            return this.doUpdate(ms, parameter);
        }
}
```



3、其他会话更新了数据，导致读取到脏数据（一级缓存不能跨会话共享）

```java
SqlSession session1 = sqlSessionFactory.openSession();
BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
SqlSession session2 = sqlSessionFactory.openSession();
BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
// mapper2查询
System.out.println(mapper2.selectBlogById(1002));
// mapper1更新
Blog blog3 = new Blog();
blog3.setBid(1002);
blog3.setName("mybatis缓存机制1");
mapper1.updateBlog(blog3);

session1.commit();
System.out.println(mapper2.selectBlogById(1002));
```

一级缓存的不足：

 		使用一级缓存的时候，因为缓存不能跨会话共享，不同的会话之间对于相同的数据可能有不一样的缓存。在有多个会话或者分布式环境下，会存在脏数据的问题。如果要解决这个问题，就要用到二级缓存。MyBatis 一级缓存（MyBaits 称其为 Local Cache）无法关闭，但是有两种级别可选：

1. session 级别的缓存，在同一个 sqlSession 内，对同样的查询将不再查询数据库，直接从缓存中。
2. statement 级别的缓存，避坑： 为了避免这个问题，可以将一级缓存的级别设为 statement 级别的，这样每次查询结束都会清掉一级缓存。



使一级缓存失效的四种情况：  

1) 不同的SqlSession对应不同的一级缓存

2) 同一个SqlSession但是查询条件不同

3) 同一个SqlSession两次查询期间执行了任何一次增删改操作

4) 同一个SqlSession两次查询期间手动清空了缓存

## MyBatis的二级缓存

二级缓存是用来解决一级缓存不能跨会话共享的问题的，范围是namespace 级别的，可以被多个SqlSession 共享（只要是同一个接口里面的相同方法，都可以共享），生命周期和应用同步。

如果你的MyBatis使用了二级缓存，并且你的Mapper和select语句也配置使用了二级缓存，那么在执行select查询的时候，MyBatis会先从二级缓存中取数据，其次才是一级缓存，即MyBatis查询数据的顺序是：二级缓存 —> 一级缓存 —> 数据库。



作为一个作用范围更广的缓存，它肯定是在SqlSession 的外层，否则不可能被多个SqlSession 共享。而一级缓存是在SqlSession 内部的，所以第一个问题，肯定是工作在一级缓存之前，也就是只有取不到二级缓存的情况下才到一个会话中去取一级缓存。第二个问题，二级缓存放在哪个对象中维护呢？ 要跨会话共享的话，SqlSession 本身和它里面的BaseExecutor 已经满足不了需求了，那我们应该在BaseExecutor 之外创建一个对象。

实际上MyBatis 用了一个装饰器的类来维护，就是CachingExecutor。如果启用了二级缓存，MyBatis 在创建Executor 对象的时候会对Executor 进行装饰。CachingExecutor 对于查询请求，会判断二级缓存是否有缓存结果，如果有就直接返回，如果没有委派交给真正的查询器Executor 实现类，比如SimpleExecutor 来执行查询，再走到一级缓存的流程。最后会把结果缓存起来，并且返回给用户。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220531210642192.png)



二级缓存开启的条件：

1. 配置 mybatis.configuration.cache-enabled=true，只要没有显式地设置cacheEnabled=false，都会用CachingExecutor 装饰基本的执行器。默认为true，不需要设置。
2. 在映射文件中设置标签<cache />

```xml
<cache type="org.apache.ibatis.cache.impl.PerpetualCache"
    size="1024"
eviction="LRU"
flushInterval="120000"
readOnly="false"/>
```

基本上就是这样。这个简单语句的效果如下:

- 映射语句文件中的所有 select 语句的结果将会被缓存。
- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
- 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

这个更高级的配置创建了一个 FIFO 缓存，每隔 60 秒刷新，最多可以存储结果对象或列表的 512 个引用，而且返回的对象被认为是只读的，因此对它们进行修改可能会在不同线程中的调用者产生冲突。可用的清除策略有：

- `LRU` – 最近最少使用：移除最长时间不被使用的对象。
- `FIFO` – 先进先出：按对象进入缓存的顺序来移除它们。
- `SOFT` – 软引用：基于垃圾回收器状态和软引用规则移除对象。
- `WEAK` – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。

默认的清除策略是 LRU。

Mapper.xml 配置了之后，select()会被缓存。update()、delete()、insert()会刷新缓存。

如果cacheEnabled=true，Mapper.xml 没有配置标签，还有二级缓存吗？（没有）还会出现CachingExecutor 包装对象吗？（会）



只要cacheEnabled=true 基本执行器就会被装饰。有没有配置，决定了在启动的时候会不会创建这个mapper 的Cache 对象，只是最终会影响到CachingExecutorquery 方法里面的判断。**如果某些查询方法对数据的实时性要求很高，不需要二级缓存，怎么办？**我们可以在单个Statement ID 上显式关闭二级缓存（默认是true）：

```java
<select id="selectBlog" resultMap="BaseResultMap" useCache="false">
```



3. 二级缓存必须在SqlSession关闭或提交之后有效
4. 查询的数据所转换的实体类类型必须实现序列化的接口



<br>

二级缓存验证（验证二级缓存需要先开启二级缓存）

1、事务不提交，二级缓存不存在

```java
System.out.println(mapper1.selectBlogById(1002));
// 事务不提交的情况下，二级缓存不会写入
// session1.commit();
System.out.println(mapper2.selectBlogById(1002));
```

2、使用不同的session 和mapper，验证二级缓存可以跨session 存在（取消以上commit()的注释）

3、在其他的session 中执行增删改操作，验证缓存会被刷新

```java
System.out.println(mapper1.selectBlogById(1002));
//主键自增返回测试
Blog blog3 = new Blog();
blog3.setBid(1002);
blog3.setName("mybatis缓存机制");
mapper1.updateBlog(blog3);
session1.commit();
System.out.println(mapper2.selectBlogById(1002));
```

什么时候开启二级缓存？

一级缓存默认是打开的，二级缓存需要配置才可以开启。那么我们必须思考一个问题，在什么情况下才**有必要**去开启二级缓存？

1. 因为所有的增删改都会刷新二级缓存，导致二级缓存失效，所以适合在查询为主的应用中使用，比如历史交易、历史订单的查询。否则缓存就失去了意义。

2. 如果多个namespace 中有针对于同一个表的操作，比如Blog 表，如果在一个namespace 中刷新了缓存，另一个namespace 中没有刷新，就会出现读到脏数据的情况。所以，**推荐在一个Mapper 里面只操作单表的情况使用**。

如果要让多个namespace 共享一个二级缓存，应该怎么做？跨namespace 的缓存共享的问题，可以使用来解决：

```xml
<cache-ref namespace="com.wuzz.crud.dao.DepartmentMapper" />
```

cache-ref 代表引用别的命名空间的Cache 配置，两个命名空间的操作使用的是同一个Cache。在关联的表比较少，或者按照业务可以对表进行分组的时候可以使用。

注意：在这种情况下，多个Mapper 的操作都会引起缓存刷新，缓存的意义已经不大了。

<br>

第三方缓存做二级缓存

除了MyBatis 自带的二级缓存之外，我们也可以通过实现Cache 接口来自定义二级缓存。MyBatis 官方提供了一些第三方缓存集成方式，比如ehcache 和redis：[https://github.com/mybatis/redis-cache](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Fmybatis%2Fredis-cache) ,这里就不过多介绍了。当然，我们也可以使用独立的缓存服务，不使用MyBatis 自带的二级缓存。



使二级缓存失效的情况：两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效





## 二级缓存的相关配置

在mapper配置文件中添加的cache标签可以设置一些属性：

- eviction属性：缓存回收策略  

  - LRU（Least Recently Used） – 最近最少使用的：移除最长时间不被使用的对象。  
  - FIFO（First in First out） – 先进先出：按对象进入缓存的顺序来移除它们。  
  - SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。  
  - WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
  - 默认的是 LRU

- flushInterval属性：刷新间隔，单位毫秒

  - 默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句（增删改）时刷新

- size属性：引用数目，正整数

  - 代表缓存最多可以存储多少个对象，太大容易导致内存溢出

- readOnly属性：只读，true/false

  - true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。  
  - false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是false

  

## MyBatis缓存查询的顺序

- 先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用  
- 如果二级缓存没有命中，再查询一级缓存  
- 如果一级缓存也没有命中，则查询数据库  
- SqlSession关闭之后，一级缓存中的数据会写入二级缓存



## 整合第三方缓存EHCache（了解）



### a. 添加依赖

```xml
<!-- Mybatis EHCache整合包 -->
<dependency>
	<groupId>org.mybatis.caches</groupId>
	<artifactId>mybatis-ehcache</artifactId>
	<version>1.2.1</version>
</dependency>
<!-- slf4j日志门面的一个具体实现 -->
<dependency>
	<groupId>ch.qos.logback</groupId>
	<artifactId>logback-classic</artifactId>
	<version>1.2.3</version>
</dependency>
```



### b. 各个jar包的功能

| jar包名称       | 作用                            |
| --------------- | ------------------------------- |
| mybatis-ehcache | Mybatis和EHCache的整合包        |
| ehcache         | EHCache核心包                   |
| slf4j-api       | SLF4J日志门面包                 |
| logback-classic | 支持SLF4J门面接口的一个具体实现 |



### c. 创建EHCache的配置文件ehcache.xml

名字必须叫`ehcache.xml`

```xml
<?xml version="1.0" encoding="utf-8" ?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="../config/ehcache.xsd">
    <!-- 磁盘保存路径 -->
    <diskStore path="D:\atguigu\ehcache"/>
    <defaultCache
            maxElementsInMemory="1000"
            maxElementsOnDisk="10000000"
            eternal="false"
            overflowToDisk="true"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            diskExpiryThreadIntervalSeconds="120"
            memoryStoreEvictionPolicy="LRU">
    </defaultCache>
</ehcache>
```





### d. 设置二级缓存的类型

在xxxMapper.xml文件中设置二级缓存类型

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```



### e. 加入logback日志

存在SLF4J时，作为简易日志的log4j将失效，此时我们需要借助SLF4J的具体实现logback来打印日志。

创建logback的配置文件`logback.xml`，名字固定，不可改变

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!-- 指定日志输出的位置 -->
    <appender name="STDOUT"
              class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 日志输出的格式 -->
            <!-- 按照顺序分别是：时间、日志级别、线程名称、打印日志的类、日志主体内容、换行 -->
            <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%thread] [%logger] [%msg]%n</pattern>
        </encoder>
    </appender>
    <!-- 设置全局日志级别。日志级别按顺序分别是：DEBUG、INFO、WARN、ERROR -->
    <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。 -->
    <root level="DEBUG">
        <!-- 指定打印日志的appender，这里通过“STDOUT”引用了前面配置的appender -->
        <appender-ref ref="STDOUT" />
    </root>
    <!-- 根据特殊需求指定局部日志级别 -->
    <logger name="com.atguigu.crowd.mapper" level="DEBUG"/>
</configuration>
```



### f. EHCache配置文件说明



| 属性名                          | 是否必须 | 作用                                                         |
| ------------------------------- | -------- | ------------------------------------------------------------ |
| maxElementsInMemory             | 是       | 在内存中缓存的element的最大数目                              |
| maxElementsOnDisk               | 是       | 在磁盘上缓存的element的最大数目，若是0表示无穷大             |
| eternal                         | 是       | 设定缓存的elements是否永远不过期。 如果为true，则缓存的数据始终有效， 如果为false那么还要根据timeToIdleSeconds、timeToLiveSeconds判断 |
| overflowToDisk                  | 是       | 设定当内存缓存溢出的时候是否将过期的element缓存到磁盘上      |
| timeToIdleSeconds               | 否       | 当缓存在EhCache中的数据前后两次访问的时间超过timeToIdleSeconds的属性取值时， 这些数据便会删除，默认值是0,也就是可闲置时间无穷大 |
| timeToLiveSeconds               | 否       | 缓存element的有效生命期，默认是0.,也就是element存活时间无穷大 |
| diskSpoolBufferSizeMB           | 否       | DiskStore(磁盘缓存)的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区 |
| diskPersistent                  | 否       | 在VM重启的时候是否启用磁盘保存EhCache中的数据，默认是false   |
| diskExpiryThreadIntervalSeconds | 否       | 磁盘缓存的清理线程运行间隔，默认是120秒。每个120s， 相应的线程会进行一次EhCache中数据的清理工作 |
| memoryStoreEvictionPolicy       | 否       | 当内存缓存达到最大，有新的element加入的时候， 移除缓存中element的策略。 默认是LRU（最近最少使用），可选的有LFU（最不常使用）和FIFO（先进先出 |



# 7 MyBatis的逆向工程

- 正向工程：先创建Java实体类，由框架负责根据实体类生成数据库表。Hibernate是支持正向工程的
- 逆向工程：先创建数据库表，由框架负责根据数据库表，反向生成如下资源：
  - Java实体类
  - Mapper接口
  - Mapper映射文件

<br>

## 创建逆向工程的步骤

### 添加依赖和插件

```xml
<dependencies>
	<!-- MyBatis核心依赖包 -->
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>3.5.9</version>
	</dependency>
	<!-- junit测试 -->
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.13.2</version>
		<scope>test</scope>
	</dependency>
	<!-- MySQL驱动 -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>8.0.27</version>
	</dependency>
	<!-- log4j日志 -->
	<dependency>
		<groupId>log4j</groupId>
		<artifactId>log4j</artifactId>
		<version>1.2.17</version>
	</dependency>
</dependencies>


<!-- 控制Maven在构建过程中相关配置 -->
<build>
	<!-- 构建过程中用到的插件 -->
	<plugins>
		<!-- 具体插件，逆向工程的操作是以构建过程中插件形式出现的 -->
		<plugin>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-maven-plugin</artifactId>
			<version>1.3.0</version>
			<!-- 插件的依赖 -->
			<dependencies>
				<!-- 逆向工程的核心依赖 -->
				<dependency>
					<groupId>org.mybatis.generator</groupId>
					<artifactId>mybatis-generator-core</artifactId>
					<version>1.3.2</version>
				</dependency>
				<!-- 数据库连接池 -->
				<dependency>
					<groupId>com.mchange</groupId>
					<artifactId>c3p0</artifactId>
					<version>0.9.2</version>
				</dependency>
				<!-- MySQL驱动 -->
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
					<version>8.0.27</version>
				</dependency>
			</dependencies>
		</plugin>
	</plugins>
</build>
```

### 创建MyBatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <typeAliases>
        <package name=""/>
    </typeAliases>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <package name=""/>
    </mappers>
</configuration>
```

### 创建逆向工程的配置文件

文件名必须是：`generatorConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!--
    targetRuntime: 执行生成的逆向工程的版本
    MyBatis3Simple: 生成基本的CRUD（清新简洁版）
    MyBatis3: 生成带条件的CRUD（奢华尊享版）
    -->
    <context id="DB2Tables" targetRuntime="MyBatis3Simple">
        <!-- 数据库的连接信息 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/mybatis"
                        userId="root"
                        password="123456">
        </jdbcConnection>
        <!-- javaBean的生成策略-->
        <javaModelGenerator targetPackage="com.atguigu.mybatis.pojo" targetProject=".\src\main\java">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- SQL映射文件的生成策略 -->
        <sqlMapGenerator targetPackage="com.atguigu.mybatis.mapper"
                         targetProject=".\src\main\resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
        <!-- Mapper接口的生成策略 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="com.atguigu.mybatis.mapper" targetProject=".\src\main\java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>
        <!-- 逆向分析的表 -->
        <!-- tableName设置为*号，可以对应所有表，此时不写domainObjectName -->
        <!-- domainObjectName属性指定生成出来的实体类的类名 -->
        <table tableName="t_emp" domainObjectName="Emp"/>
        <table tableName="t_dept" domainObjectName="Dept"/>
    </context>
</generatorConfiguration>
```



### 执行MBG插件的generate插件

执行结果

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E9%80%86%E5%90%91%E6%89%A7%E8%A1%8C%E7%BB%93%E6%9E%9C.png)



## QBC

### 查询

`selectByExample`：按条件查询，需要传入一个example对象或者null；如果传入一个null，则表示没有条件，也就是查询所有数据

`example.createCriteria().xxx`：创建条件对象，通过 `andXXX` 方法为SQL添加查询条件，每个条件之间是 `and` 关系

`example.or().xxx`：将之前添加的条件通过 `or` 拼接其他条件



```java
@Test public void testMBG() throws IOException {
    InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
  
    EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
  
  	//创建条件对象，通过andXXX方法为SQL添加查询添加，每个条件之间是and关系
    EmpExample example = new EmpExample();
    //名字为张三，且年龄大于等于20
    example.createCriteria().andEmpNameEqualTo("张三").andAgeGreaterThanOrEqualTo(20);
  	//将之前添加的条件通过or拼接其他条件  
  	//或者did不为空
    example.or().andDidIsNotNull();
  
    List<Emp> emps = mapper.selectByExample(example);
    emps.forEach(System.out::println);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/example%E7%9A%84%E6%96%B9%E6%B3%95.png)

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/example%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C.png)



### 增改

`updateByPrimaryKey`：通过主键进行数据修改，如果某一个值为null，也会将对应的字段改为null

```java
mapper.updateByPrimaryKey(new Emp(1,"admin",22,null,"456@qq.com",3));
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E5%A2%9E%E5%88%A0%E6%94%B9%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C1.png)



`updateByPrimaryKeySelective()`：通过主键进行选择性数据修改，如果某个值为null，则不修改这个字段

```java
mapper.updateByPrimaryKeySelective(new Emp(2,"admin2",22,null,"456@qq.com",3));
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E5%A2%9E%E5%88%A0%E6%94%B9%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C2.png)





# 8 分页插件  pagehelper

- 添加依赖

```xml
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
<dependency>
	<groupId>com.github.pagehelper</groupId>
	<artifactId>pagehelper</artifactId>
	<version>5.2.0</version>
</dependency>
```

- 配置

在 MyBatis 的核心配置文件（mybatis-config.xml）中配置插件

```xml
<plugins>
	<!--设置分页插件-->
	<plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
</plugins>
```



- 开启分页功能

在查询功能之前使用 `PageHelper.startPage(int pageNum, int pageSize)` 开启分页功能，

其中 `pageNum` 是当前页的页码  、`pageSize` 是每页显示的条数。



```java
@Test
public void testPageHelper() throws IOException {
	InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
	SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
	SqlSession sqlSession = sqlSessionFactory.openSession(true);
	EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
	// 访问第一页，每页四条数据
	PageHelper.startPage(1,4);
	List<Emp> emps = mapper.selectByExample(null);
	emps.forEach(System.out::println);
}
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E5%88%86%E9%A1%B5%E6%B5%8B%E8%AF%95%E7%BB%93%E6%9E%9C.png)



- 分页相关数据

  - 方法一：直接输出

  ```java
  @Test
  public void testPageHelper() throws IOException {
  	InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
  	SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
  	SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
  	SqlSession sqlSession = sqlSessionFactory.openSession(true);
  	EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
  	//访问第一页，每页四条数据
  	Page<Object> page = PageHelper.startPage(1, 4);
  	List<Emp> emps = mapper.selectByExample(null);
  	//在查询到List集合后，打印分页数据
  	System.out.println(page);
  }
  ```

  分页相关数据

  ```java
  Page{count=true, pageNum=1, pageSize=4, startRow=0, endRow=4, total=8, pages=2, reasonable=false, pageSizeZero=false}[Emp{eid=1, empName='admin', age=22, sex='男', email='456@qq.com', did=3}, Emp{eid=2, empName='admin2', age=22, sex='男', email='456@qq.com', did=3}, Emp{eid=3, empName='王五', age=12, sex='女', email='123@qq.com', did=3}, Emp{eid=4, empName='赵六', age=32, sex='男', email='123@qq.com', did=1}]
  ```

  - 方法二：使用PageInfo

    在查询获取list集合之后，使用`PageInfo<T> pageInfo = new PageInfo<>(List<T> list, intnavigatePages)`获取分页相关数据

    其中，list 是分页之后的数据  、navigatePages 是导航分页的页码数

    ```java
    @Test
    public void testPageHelper() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        PageHelper.startPage(1, 4);
        List<Emp> emps = mapper.selectByExample(null);
      
        PageInfo<Emp> page = new PageInfo<>(emps,5);
        System.out.println(page);
    }
    ```

    

    ```java
    PageInfo{
    pageNum=1, pageSize=4, size=4, startRow=1, endRow=4, total=8, pages=2, 
    list=Page{count=true, pageNum=1, pageSize=4, startRow=0, endRow=4, total=8, pages=2, reasonable=false, pageSizeZero=false}[Emp{eid=1, empName='admin', age=22, sex='男', email='456@qq.com', did=3}, Emp{eid=2, empName='admin2', age=22, sex='男', email='456@qq.com', did=3}, Emp{eid=3, empName='王五', age=12, sex='女', email='123@qq.com', did=3}, Emp{eid=4, empName='赵六', age=32, sex='男', email='123@qq.com', did=1}], 
    prePage=0, nextPage=2, isFirstPage=true, isLastPage=false, hasPreviousPage=false, hasNextPage=true, navigatePages=5, navigateFirstPage=1, navigateLastPage=2, navigatepageNums=[1, 2]}
    ```

    其中list中的数据等同于方法一中直接输出的page数据





| 序号 | 字段                        | 内容                         |
| ---- | --------------------------- | ---------------------------- |
| 1    | pageNum                     | 当前页的页码                 |
| 2    | pageSize                    | 每页显示的条数               |
| 3    | size                        | 当前页显示的真实条数         |
| 4    | total                       | 总记录数                     |
| 5    | pages                       | 总页数                       |
| 6    | prePage                     | 上一页的页码                 |
| 7    | nextPage                    | 下一页的页码                 |
| 8    | isFirstPage/isLastPage      | 是否为第一页/最后一页        |
| 9    | hasPreviousPage/hasNextPage | 是否存在上一页/下一页        |
| 10   | navigatePages               | 导航分页的页码数             |
| 11   | navigatepageNums            | 导航分页的页码，\[1,2,3,4,5] |







# 9 写在后面









































































