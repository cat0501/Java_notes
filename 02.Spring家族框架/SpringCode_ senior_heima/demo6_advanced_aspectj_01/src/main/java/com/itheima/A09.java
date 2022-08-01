package com.itheima;

import com.itheima.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/*
    注意几点
    1. 版本选择了 java 8, 因为目前的 aspectj-maven-plugin 1.14.0 最高只支持到 java 16
    2. 一定要用 maven 的 compile 来编译, idea 不会调用 ajc 编译器
 */
@SpringBootApplication
public class A09 {

    private static final Logger log = LoggerFactory.getLogger(A09.class);

    public static void main(String[] args) {
        //ConfigurableApplicationContext context = SpringApplication.run(A09.class, args);
        //MyService service = context.getBean(MyService.class);
        //
        //log.debug("service class: {}", service.getClass());
        //service.foo();
        //
        //context.close();


        // -------------------------------AOP实现之ajc编译器（AOP的另一种实现及原理）---------------------------------------
        // 通过aspectj的编译器来改动class类文件来实现功能增强
        // 使用的人少，绝大多数还是使用的代理
        // 代理的本质是通过方法重写来实现的。可以突破代理的一些限制：如果目标类是静态方法，代理无法增强。但是编译器增强可以。
        // 测试：需要使用maven的compile
        new MyService().foo();

        /*
            学到了什么
            1. aop 的原理并非代理一种, 编译器也能玩出花样
         */
    }
}
