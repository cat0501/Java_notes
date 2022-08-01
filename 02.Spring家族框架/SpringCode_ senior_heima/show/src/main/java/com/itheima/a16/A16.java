package com.itheima.a16;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

// 通过切点判断目标方法是否需要增强
// AspectJExpressionPointcut通过切点表达式判断目标方法是否需要增强，以下学习一些例子
// 需求：希望T1这个类的bar()方法执行时增强，foo()方法执行时不增强，这个时候我们需要根据方法的名字进行匹配。我们用execution 指定方法的名字execution(* bar()

public class A16 {
    public static void main(String[] args) throws NoSuchMethodException {
//        AspectJExpressionPointcut pt1 = new AspectJExpressionPointcut();
//        pt1.setExpression("execution(* bar())");
//        System.out.println(pt1.matches(T1.class.getMethod("foo"), T1.class));
//        System.out.println(pt1.matches(T1.class.getMethod("bar"), T1.class));
//
          // 根据方法上的注解来进行匹配，使用 execution()就不太方便了，用@annotation 这个切面表达式来进行匹配
//        AspectJExpressionPointcut pt2 = new AspectJExpressionPointcut();
//        pt2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
//        System.out.println(pt2.matches(T1.class.getMethod("foo"), T1.class));
//        System.out.println(pt2.matches(T1.class.getMethod("bar"), T1.class));

        StaticMethodMatcherPointcut pt3 = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 检查方法上是否加了 Transactional 注解
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 查看类上是否加了 Transactional 注解
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                return false;
            }
        };

        System.out.println(pt3.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt3.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println(pt3.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(pt3.matches(T3.class.getMethod("foo"), T3.class));

        /*
            学到了什么
                a. 底层切点实现是如何匹配的: 调用了 aspectj 的匹配方法
                b. 比较关键的是它实现了 MethodMatcher 接口, 用来执行方法的匹配
         */
    }


    // @Transactional可以加在方法上、类上、接口上。都能实现增强。
    static class T1 {
        @Transactional
        public void foo() {
        }
        public void bar() {
        }
    }

    @Transactional
    static class T2 {
        public void foo() {
        }
    }

    @Transactional
    interface I3 {
        void foo();
    }
    static class T3 implements I3 {
        public void foo() {
        }
    }
}
