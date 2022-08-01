package com.itheima.a11;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CglibProxyDemo {

    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }

    // 1、代理是子类型, 目标是父类型（jdk代理中是兄弟关系，都实现了相同的接口）
    // Cglib代理的限制：父类型不能被final修饰，因为不能有子类。会报错。如果方法用final修饰，不会报错但是不会增强。因为final修饰的方法不能被重写。
    // 即父类不能是final的，父类的方法如果加了final不能被增强。
    // 2、特色：基于methodProxy避免反射调用目标方法
    public static void main(String[] param) {
//        Target target = new Target();

        // CglibProxy是通过Enhancer这个类来创建的。参数1：指定父类  参数2：决定了代理类中方法执行时的行为MethodInterceptor()
        // 参数1：代理类自己  参数2：当前代理类中执行的方法  参数3：方法执行时的实际参数  参数4：methodProxy避免反射调用目标方法
        // 区别于jdk，基于父子继承关系来创建代理。

        // 原始是这样。使用lambda改进,更加简洁
        // new MethodInterceptor()决定了代理类中方法执行时的行为
        Enhancer.create(Target.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object p, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                return null;
            }
        });

        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (p, method, args, methodProxy) -> {
            System.out.println("before...");
//            Object result = method.invoke(target, args); // 用方法反射调用目标
            // methodProxy 它可以避免反射调用
//            Object result = methodProxy.invoke(target, args); // 内部没有用反射, 需要目标 （spring）
            Object result = methodProxy.invokeSuper(p, args); // 内部没有用反射, 需要代理
            System.out.println("after...");
            return result;
        });

        proxy.foo();

    }
}