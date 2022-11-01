package cn.iocoder.springboot.lab01.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 简单来说：认证解决“你是谁”的问题，授权解决“你能做什么”的问题。
// 在 Java 生态中，目前有 Spring Security 和 Apache Shiro 两个安全框架，可以完成认证和授权的功能。

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
