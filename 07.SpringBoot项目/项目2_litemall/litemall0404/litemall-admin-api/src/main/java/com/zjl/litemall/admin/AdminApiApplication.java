package com.zjl.litemall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zjl.litemall.db", "com.zjl.litemall.core", "com.zjl.litemall.admin"})
@MapperScan("com.zjl.litemall.db.dao")
@EnableTransactionManagement
@EnableScheduling
public class AdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }

}