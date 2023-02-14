package com.atguigu;

import com.atguigu.scheduler.bean.QuartzScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YuntaiApplication {
    public static void main(String[] args) throws Exception {
        // 启动Spring应用
        SpringApplication.run(YuntaiApplication.class, args);

        // 启动定时任务调度器
        QuartzScheduler.getInstance().start();
    }
}


/**
 #功能模块：
 用户权限模块、数据统计模块、报表管理模块、任务调度模块、数据治理模块



 TODO:
 1,云数据库：使用腾讯云服务器
 2,前端改造：新增模块/页面


 */













