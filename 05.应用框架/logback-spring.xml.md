

市面上的日志框架：JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j…

| 日志门面 （日志的抽象层）                                    | 日志实现                                          |
| ------------------------------------------------------------ | ------------------------------------------------- |
| JCL（Jakarta Commons Logging） SLF4j（Simple Logging Facade for Java） **jboss-logging** | Log4j JUL（java.util.logging） Log4j2 **Logback** |

SpringBoot 选用 SLF4j（日志抽象层）和 logback（日志实现）

SpringBoot 配置文件的加载顺序：logback.xml—>application.properties—>logback-spring.xml

日志级别从低到高分为：TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出



```sh
  一个父标签：configuration
  两种属性：contextName和property
  三个节点：appender、root、logger
```





IDEA  快捷键

调到上一次看的方法：ctrl + alt + 方向键





@项目问题记录：

handle escapes 处理转义

```java
public static String handleEscapes(String inputString) {

    if (!StringUtils.isEmpty(inputString)) {
        return inputString.replace("%", "////%").replaceAll("[\n|\r\t]", "_");
    }
    return inputString;
}
```



MyBatis-Plus方法

```
List<T> getRecords();
```



四种访问修饰符的权限区别





# 参考

https://blog.csdn.net/hansome_hong/article/details/124434864





















































