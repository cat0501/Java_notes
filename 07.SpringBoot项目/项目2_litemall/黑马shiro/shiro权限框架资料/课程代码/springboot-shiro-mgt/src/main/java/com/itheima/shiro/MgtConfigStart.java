package com.itheima.shiro;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 启动类
 * @return
 */
@SpringBootApplication(scanBasePackages={"com.itheima.shiro.interceptor","com.itheima.shiro.client",
        "com.itheima.shiro.service","com.itheima.shiro.config","com.itheima.shiro.intialize"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableDubbo(scanBasePackages = {"com.itheima.shiro.web","com.itheima.shiro.client"})
public class MgtConfigStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MgtConfigStart.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MgtConfigStart.class);
    }

    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                factory.setPort(8080);
                factory.setContextPath("/shiro");
            }
        };
    }

}