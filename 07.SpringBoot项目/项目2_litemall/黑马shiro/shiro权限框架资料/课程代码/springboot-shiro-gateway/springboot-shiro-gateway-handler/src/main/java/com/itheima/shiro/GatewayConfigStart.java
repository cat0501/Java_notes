package com.itheima.shiro;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Description 启动类
 * @MethodName ConfigStart
 * @author Shuwq
 * @date 2019/10/26 22:08
 * @param 
 * @return
 */
@SpringBootApplication(scanBasePackages={"com.itheima.shiro"},exclude = DataSourceAutoConfiguration.class )
@EnableAspectJAutoProxy
@EnableDubbo(scanBasePackages = {"com.itheima.shiro.web","com.itheima.shiro.client"})
public class GatewayConfigStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayConfigStart.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GatewayConfigStart.class);
    }

    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                factory.setPort(8081);
                factory.setContextPath("/gatway");
            }
        };
    }

}