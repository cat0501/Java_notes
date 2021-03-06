package org.example.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午5:17
 */
@EnableFeignClients(basePackages = "org.example.member.feign")
@EnableDiscoveryClient
@MapperScan("org.example.member.dao")
@SpringBootApplication
public class PassjavaMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaMemberApplication.class, args);
    }
}
