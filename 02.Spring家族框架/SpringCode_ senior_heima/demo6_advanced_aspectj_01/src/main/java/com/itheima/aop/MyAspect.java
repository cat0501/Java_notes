package com.itheima.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// 切面类

@Aspect // ⬅️注意此切面并未被 Spring 管理
public class MyAspect {

    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);

    // 前置通知方法：匹配的是MyService的foo()方法
    @Before("execution(* com.itheima.service.MyService.foo())")
    public void before() {
        log.debug("before()");
    }
}
