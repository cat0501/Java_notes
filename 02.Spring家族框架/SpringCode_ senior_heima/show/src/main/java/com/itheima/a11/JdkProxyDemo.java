package com.itheima.a11;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;



// 核心代码如下

/**
     // 目标对象
     Target target = new Target();

     ClassLoader loader = JdkProxyDemo.class.getClassLoader(); // 用来加载在运行期间动态生成的字节码
     Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (p, method, args) -> {
         System.out.println("before...");
         // 调用结果相同，只不过下面的是反射调用
         // 目标.方法(参数)
         // 方法.invoke(目标, 参数);
         Object result = method.invoke(target, args);
         System.out.println("after....");
         return result; // 让代理也返回目标方法执行的结果
     });

     System.out.println(proxy.getClass());

     // 调用
     proxy.foo();


 */


public class JdkProxyDemo {

    interface Foo {
        void foo();
    }

    static final class Target implements Foo {
        public void foo() {
            System.out.println("target foo");
        }
    }

    // jdk 只能针对接口代理（限制）---------（java自带的）
    // cglib--------- (第三方的代理技术)
    public static void main(String[] param) throws IOException {
        // 目标对象
        Target target = new Target();

        ClassLoader loader = JdkProxyDemo.class.getClassLoader(); // 用来加载在运行期间动态生成的字节码
        // Proxy.newProxyInstance()创建一个新的代理实例对象
        // 参数1：类加载器   参数2：代理类将来要实现的接口    参数3：方法行为的封装（匿名内部类的方式创建）
        // （代理类的方法被调用时，就会执行到InvocationHandler的invoke()方法）
        // InvocationHandler的3个参数 ——> 参数1：代理对象  参数2：正在执行的方法对象  参数3：方法实际传过来的参数
        // 此处是lambda 写法

        // 前后增强逻辑（before和after的打印）都是在InvocationHandler中代理执行期间加入
        // 以上就是jdk动态代理的一个使用
        // Jdk动态代理的一个特点：jdk动态代理Foo proxy和目标Target target的关系是兄弟关系（都实现了相同的接口）
        // 兄弟之间不可以强制类型转换，但是都可以强转成接口类型
        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (p, method, args) -> {
            System.out.println("before...");
            // 调用结果相同，只不过下面的是反射调用
            // 目标.方法(参数)
            // 方法.invoke(目标, 参数);
            Object result = method.invoke(target, args);
            System.out.println("after....");
            return result; // 让代理也返回目标方法执行的结果
        });

        System.out.println(proxy.getClass());

        // 代理的foo()方法，代理的foo()方法执行  就会执行InvocationHandler中的逻辑
        proxy.foo();

        System.in.read();
    }
}




// P36 jdk代理原理
// 内部实现在newProxyInstance()中







