---
title: 精进 Spring Boot 源码分析 —— 调试环境搭建 （Spring Boot 2.6.0版本）
abbrlink: 25f
date: 2022-02-26 23:42:25
tags:
---



# 精进 Spring Boot 源码分析 —— 调试环境搭建 （Spring Boot 2.6.0版本）



> 友情提示：如果胖友对 Spring Boot 的使用不是很了解，可以看看艿艿写的 https://github.com/YunaiV/SpringBoot-Labs 系列文章，大概有 **100+** 个 Spring Boot 使用案例。

今儿，我们来搭建一个 Spring Boot 调试环境，目标是：**启动 Spring Boot，成功调试它的启动过程**。

视频可见 B 站：https://www.bilibili.com/video/BV1WA411P7Pz/

<hr>

## 依赖工具

### 1.1 IDEA

当然是 Jetbrains 出品的 IDEA 工具，相信绝大多数胖友都使用的它。

艿艿使用的是 **2020.3** 版本，胖友尽量保证不低于该版本哈。

> 友情提示：如果不知道怎么激活 IDEA 的胖友，可以访问 [《IDEA 激活指南》](https://www.iocoder.cn/IDEA/idea-2020-reset-eval/) 地址，花 5 分钟学习！支持所有版本~



## 源码拉取

需要使用 JDK 编译 Spring Boot 的代码，这里艿艿使用的是 JDK **1.8** 版本

```bash
$ java -version
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
```



使用 IDEA 从官方仓库 https://github.com/spring-projects/spring-boot 克隆项目。

> 友情提示：如果网络不是很好的胖友，可以选择和艿艿一样，使用 Gitee 提供的镜像仓库 https://gitee.com/mirrors/spring-boot

这里，我们使用的 Spring Boot 版本是 [**2.6.0-SNAPSHOT**](https://github.com/spring-projects/spring-boot/blob/main/gradle.properties#L1)。

> 友情提示：胖友可以考虑 Fork 下[官方仓库](https://github.com/spring-projects/spring-boot)，为什么呢？
>
> 既然开始阅读、调试源码，我们可能会写一些注释，有了自己的仓库，可以进行自由的提交。😜



## 下载依赖

① 克隆完成 Spring Boot 项目之后，IDEA 会自动下载需要的 Gradle 工具。如下图所示：

![下载 Gradle 工具](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E4%B8%8B%E8%BD%BD%20Gradle%20%E5%B7%A5%E5%85%B7.png)



这里，我们使用的 Gradle 版本是 **6.9**。

> 友情提示：由于是从国外的网址下载，所以需要耐心等待一会，艿艿花费了 2 分多钟。

② 下载完 Gradle 工具之后，IDEA 就会使用它自动下载相关的依赖库。如下图所示：



因为 Gradle 支持使用 Maven 依赖，所以我们可以使用阿里云的 Maven 镜像 `https://maven.aliyun.com/nexus/content/groups/public/`。修改 `build.gradle` 文件，如下图所示：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E4%B8%8B%E8%BD%BD%E4%BE%9D%E8%B5%962.png)



效果非常不错，不过艿艿还是花了 20 分钟才下载完了依赖。主要原因是，Spring Boot 内置了 50+ 个 Starter 的实现，导致引入了非常多的依赖。

ps：如果中间下载失败，点击【绿色刷新】按钮，继续下载依赖即可。



## 调试SpringBoot示例

依赖下载完后，我们通过调试 Spring Boot 提供的示例，了解 Spring Boot 的**启动过程**。在 `spring-boot-smoke-tests` 项目下，我们可以看到大量的示例，如下图所示：

![Spring Boot 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/Spring%20Boot%20%E7%A4%BA%E4%BE%8B.png)



这里，我们使用 `spring-boot-smoke-test-tomcat` 项目，最为熟悉的 Spring MVC + Tomcat 的组合。

① 在 SampleTomcatApplication 和 SpringApplication 分别打上断点，如下图所示：

![打上断点](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E6%89%93%E4%B8%8A%E6%96%AD%E7%82%B9.png)



② Debug 运行 SampleTomcatApplication 类，首次构建会需要几秒钟，成功进入断点，可以愉快的调试 Spring Boot **启动过程**。如下图所示：

![调试 Spring Boot](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E8%B0%83%E8%AF%95%20Spring%20Boot.gif)







<hr>

## 源码解析

Spring Boot 的代码量，目前有 35w 行左右，不过绝大多数情况下，我们只要搞懂 Spring Boot 的启动流程，如何实现自动配置的原理，足够满足工作与面试了。

这里，艿艿来推荐下 Spring Boot 相关的源码解析的书籍，帮助大家更好的阅读。

① 艿艿写的 [《精尽 Spring Boot 源码解析》](http://svip.iocoder.cn/categories/Spring-Boot/)，针对 Spring Boot 2.2.X 版本，工作中使用的主流版本。

如果大家在阅读中有碰到什么问题，欢迎星球给艿艿提问哈，大胆的，自己人！

② [《SpringBoot 源码解读与原理分析》](https://github.com/YunaiV/books)，针对 Spring Boot 2.1.9 版本。

写的还行，内容也讲了一些 Spring IOC 和 AOP 相关的源码，细节上不够详细，可以选择看看。

③ [《Spring Boot 技术内幕》](https://github.com/YunaiV/books)，针对 Spring Boot 2.X 版本，豆瓣暂无评分，预计评分在 6.5 左右。

> 链接: https://pan.baidu.com/s/19O9ShDQcbmFopr-Vym-OFQ 提取码: zay7 复制这段内容后打开百度网盘手机App，操作更方便哦

这本书出的太晚，艿艿暂时还没细看，后续有时间准备瞅瞅~































