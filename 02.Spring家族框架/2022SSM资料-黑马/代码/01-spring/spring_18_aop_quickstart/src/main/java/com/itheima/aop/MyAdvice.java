package com.itheima.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//通知类必须配置成Spring管理的bean
@Component
//设置当前类为切面类类
@Aspect
//【核心概念4,通知类（通知方法所在的类）】
public class MyAdvice {
    //【核心概念2,切入点Pointcut（哪些方法要追加功能）（匹配连接点的式子）】
    //设置切入点，要求配置在方法上方
    @Pointcut("execution(void com.itheima.dao.BookDao.update())")
    private void pt(){}

    //【核心概念5,切面Aspect（通知和切入点之间的关系描述）】
    //设置在切入点pt()的前面运行当前操作（前置通知）
     @Before("pt()")

    //【核心概念3,通知Advice（存放共性功能的方法）】
    public void method(){
        System.out.println(System.currentTimeMillis());
    }
}


//AOP中核心概念分别指的是什么?
//        - 1,连接点（原始方法）
//        - 2,切入点Pointcut（哪些方法要追加功能）（匹配连接点的式子）
//        - 3,通知Advice（存放共性功能的方法）
//        - 4,通知类（通知方法所在的类）
//        - 5,切面Aspect（通知和切入点之间的关系描述）