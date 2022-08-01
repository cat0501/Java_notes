package com.itheima.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect // ⬅️注意此切面并未被 Spring 管理
public class MyAspect {

    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);

    // 前置通知和切点表达式（匹配MyService的所有方法，希望foo()和bar()方法都被增强）
    @Before("execution(* com.itheima.service.MyService.*())")
    public void before() {
        log.debug("before()");
    }
}
