package com.itheima.shiro;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 启动类
 * @MethodName ConfigStart
 * @author Shuwq
 * @date 2019/10/26 22:08
 * @param
 * @return
 */
@SpringBootApplication(scanBasePackages={"com.itheima.shiro"})
@EnableTransactionManagement
@EnableDubbo(scanBasePackages = {"com.itheima.shiro"})
public class HandlerConfigStart {

    public static void main(String[] args) {

        SpringApplication.run(HandlerConfigStart.class, args

        );
    }


}