---
title: 精尽 Spring Boot 源码分析 —— 调试环境搭建（Spring Boot 2.2.0 版本）
abbrlink: '1760'
date: 2022-02-27 08:32:40
tags:
---





# 精尽-Spring-Boot-源码分析-——-调试环境搭建（Spring-Boot-2-2-0-版本）







## 依赖工具

- 

- Maven

  > 终于不用是 Spring 所使用的 Gradle 构建工具了。痛苦了一堆人。哈哈哈哈。

- Git

- JDK8+

  > 艿艿自己使用的是 JDK11 。

- IntelliJ IDEA

- Kotlin

  > 这个 IDEA 自带插件。有点记不清楚了。先无视，继续往下跑。如果碰到什么问题，星球给艿艿留言。

> 笔者目前使用的系统版本是 macOS Mojave 10.14 。所以，如果胖友是 Windows 环境，胖到一些问题，请在星球给我留言。



> 如果胖友真的搭建不起来，建议可以先新建一个项目，搭建一个 Spring Boot Demo 来调试。
>
> 😈 总之，我们的目的是，一定一定一定要调试。酱紫，才能更好的阅读 Spring Boot 的代码。



## 源码拉取

从官方仓库 https://github.com/spring-projects/spring-boot `Fork` 出属于自己的仓库。

- 为什么要 `Fork` ？既然开始阅读、调试源码，我们可能会写一些注释，有了自己的仓库，可以进行自由的提交。😈
- 本文使用的 Spring 版本为 `2.2.0.BUILD-SNAPSHOT` 。
- 使用 `IntelliJ IDEA` 从 `Fork` 出来的仓库拉取代码。因为 Spring 项目比较大，从仓库中拉取代码的时间会比较长。

拉取完成后，Maven 会开始自动 **Build** 项目。因为 Build 的过程中，会下载非常多的依赖，请耐心等待。



> 艿艿：这个时间，真的有点久啊。建议做两件事情：
>
> - 1、IDEA 的 Maven 使用自己本地装的 Maven 。
> - 2、Maven 记得使用阿里云的 Maven 仓库。



## 直接运行

没错！正如标题所说，无需任何配置，就直接开始调试 Spring Boot 项目。开心不开心🙂 。我们来打开 `spring-boot-hibernate52-tests` 项目的 Hibernate52Application 类，直接右键 Debug 运行 `#main(String[] args)` 方法即可。



## 拓展阅读

实际上，截止 [「3. 直接运行」](http://svip.iocoder.cn/Spring-Boot/build-debugging-environment/#) 小节，我们已经能够愉快的调试。但是，我们是一个热爱折腾的人，所以又做了点点事情。

### 4.1 解决 pom 的报错

在根目录的 `pom.xml` 中，会看到 `${disable.checks}` 报错。它是用来配置，是否开启 Maven 代码检查的插件。因为，我们目的是为了调试代码，所以自然是去禁用它。仅仅需要在 `pom.xml` 配置如下：



maven org.eclipse.m2e:lifecycle-mapping的问题

https://www.jianshu.com/p/92c7340879ce



### 4.2 搭建 Spring Boot MVC 调试环境

搭建一个 Spring Boot MVC 环境，想必胖友已经炉火纯青。所以呢，艿艿就不装比献丑了。

> 旁白君：不要脸，其实想偷懒。

如下是艿艿搭建的代码，胖友可以简单瞅瞅 [Spring Boot MVC 调试示例](https://github.com/YunaiV/spring-boot/blob/74690873857801615f1bf404c229a29986b96238/spring-boot-tests/spring-boot-yunai-tests/spring-boot-yunai-mvc-tests/pom.xml) 。



### 4.3 更多示例

在 `spring-boot-samples` 项目下，提供了大量的 Spring Boot 和各种框架集成的示例。默认情况下，根 `pom.xml` 并未包含它们。如果我们想将它引入，只需在 IDEA 里，将 Maven Profile 增加 `m2e` 选项即可。

但是，因为 `spring-boot-samples` 提供的示例实在实在太多，会导致我们的 Maven 报 GC overhead limit exceeded ，并且 Maven 解决依赖时间巨长。所以呢，我们可以在 `spring-boot-samples-invoker` 项目下，引入自己感兴趣的项目。例如：

```xml
<modules>
    <module>../spring-boot-samples/spring-boot-sample-undertow</module>
</modules>
```





<hr>

## 彩蛋

笔者开始更新 Spring Boot 源码解析系列，让我们在 2019 一起**精尽** Spring Boot 。

啥也不唠了，撸起袖子。

以德服人，不服就干。









































