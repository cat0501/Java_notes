package cn.itcast.log;

import cn.itcast.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "cn.itcast.log.mapper")
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class,args);
    }

    // 生成分布式ID
    // 不要每次都new,交给spring容器来进行管理
    @Bean
    public IdWorker idworker(){
        return new IdWorker(0,0);
    }
}