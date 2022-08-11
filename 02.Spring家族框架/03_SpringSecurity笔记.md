# 

# 1 概述

在 Java 生态中，目前有 [Spring Security](https://spring.io/projects/spring-security) 和 [Apache Shiro](https://shiro.apache.org/) 两个安全框架，可以完成认证和授权的功能。



# 2 快速入门

实现访问 API 接口时，需要首先进行登录，才能进行访问。

- （1）引入依赖

```xml
<!-- 实现对 Spring MVC 的自动化配置 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 实现对 Spring Security 的自动化配置 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- （2）`Application` 启动类

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

- （3）`yml` 配置文件
  - 在 `spring.security` 配置项，设置 Spring Security 的配置，对应 `SecurityProperties` 配置类。
  - 默认情况下，Spring Boot `UserDetailsServiceAutoConfiguration` 自动化配置类，会创建一个**内存级别**的 `InMemoryUserDetailsManager` Bean 对象，提供认证的用户信息。
    - **添加了** `spring.security.user` 配置项，UserDetailsServiceAutoConfiguration 会基于配置的信息创建一个用户 `User` 在内存中。
    - 否则，会自动创建一个用户名为 `"user"` ，密码为 UUID 随机的用户 `User` 在内存中。

```yaml
spring:
  # Spring Security 配置项，对应 SecurityProperties 配置类
  security:
    # 配置默认的 InMemoryUserDetailsManager 的用户账号与密码。
    user:
      name: user # 账号
      password: user # 密码
      roles: ADMIN # 拥有角色
```

- （4）`AdminController` 控制器
  - 用于测试未登录时，会被拦截到登录界面。

```java
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/demo")
    public String demo() {
        return "示例返回";
    }
}
```

- （5）测试
  - 执行 `Application#main(String[] args)` 方法，运行项目。
  - 项目启动成功后，浏览器访问 http://127.0.0.1:8080/admin/demo 接口。因为未登录，所以被 Spring Security 拦截到登录界面。
  - 因为我们没有**自定义**登录界面，所以默认会使用 [DefaultLoginPageGeneratingFilter](https://github.com/spring-projects/spring-security/blob/master/web/src/main/java/org/springframework/security/web/authentication/ui/DefaultLoginPageGeneratingFilter.java) 类，生成上述界面。
  - 输入我们配置的「user/user」账号，进行登录。登录完成后，因为 Spring Security 会记录被拦截的访问地址，所以浏览器自动动跳转 http://127.0.0.1:8080/admin/demo 接口。



# 3 进阶使用

自定义 Spring Security 的配置，实现**权限控制**。





























