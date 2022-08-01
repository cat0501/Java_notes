package com.itheima.shiro;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 启动类
 * @param 
 * @return
 */
@SpringBootApplication(scanBasePackages={"com.itheima.shiro"})
@EnableTransactionManagement
@EnableDubbo(scanBasePackages = {"com.itheima.shiro.service"})
public class DubboAppConfigStart {

    public static void main(String[] args) {

        SpringApplication.run(DubboAppConfigStart.class, args

        );
    }


}