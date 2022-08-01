package com.itheima.shiro;

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
 * @MethodName ConfigStart
 * @author Shuwq
 * @date 2019/10/26 22:08
 * @param 
 * @return
 */
@SpringBootApplication(scanBasePackages={"com.itheima.shiro.interceptor",
        "com.itheima.shiro.service","com.itheima.shiro.config","com.itheima.shiro.intialize"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SpringbootShiroConfigStartA extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroConfigStartA.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootShiroConfigStartA.class);
    }

    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                factory.setPort(8081);
                factory.setContextPath("/shiro");
            }
        };
    }

}