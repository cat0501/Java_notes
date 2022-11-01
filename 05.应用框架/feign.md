# Netflix Feign or Open Feign？



## 一次使用过程

> `feignCilent`接口类

```java
package com.atguigu.springcloud.feignclient;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cat
 * @date 2022/1/6 下午3:05
 */
@FeignClient(name = "4A-SERVER")
public interface IModuleFeignClient{

    @ApiOperation(value = "查询功能列表")
    @GetMapping("/restapi/4a/module")
    public JSONObject moduleList();


    @ApiOperation(value = "查询功能树")
    @GetMapping("/restapi/4a/module/moduleTree")
    public JSONObject moduleTree();

}
```

> 测试

```java
package com.atguigu.springcloud.feignclient;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cat
 * @date 2022/1/6 下午3:05
 */
@RestController
public class TestController {

    @Autowired
    IModuleFeignClient moduleFeignClient;

    @GetMapping("/moduleList")
    public JSONObject moduleList(){
        return moduleFeignClient.moduleList();
    }

    @GetMapping("/moduleTree")
    public JSONObject moduleTree(){
        return moduleFeignClient.moduleTree();
    }

}
```



> 逻辑：`cloud-cousumer-feign`服务的`TestController`接口调用`4a-server`服务的接口

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220106161038021.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220106161319527.png)



<hr>



## 前言

说到HTTP客户端，Java自己源生的就有`java.net`包下的`HttpURLConnection`（虽然不太好用），同时优秀的开源产品更是百花齐放：
- Apache HttpClient
- OkHttp
- Spring的RestTemplate（基于以上3种Client做的包装）
- Feign

> feign的中文意思：假装，装作，捏造，想象



JDK源生的HC偏于底层且不好用，在Java9之前你几乎可以忽略它；作为老牌的`Apache HttpClient`凭借着各种优秀特征，似乎已成为了事实的标准；后起之秀`0kHttp`有着低网络延迟、更优秀的连接池性能，亦是一股不可轻视的力量； Spring它自己并不去重复发明轮子，而是借力打力提供了Http统一调用方式`RestTemplate` ，屏蔽了细节，规范了开发者的使用。

> 说明：如果你在Spring环境，并不建议直接使用具体的HttpCLient技术，而是使用面向中间语言的`RestTemplate`。

本专栏将介绍一种声明式Http客户端： `Feign`。很多人认识Feign、使用Feign是因为Spring Cloud，它作为Spring Cloud最重要的组件之一，深入了解Feign对我们在云计算领域实践将具有很强的实战意义。

我们从最源生的Feign出发，再到和Spring整合、和Spring Boot整合，最后到Spring Cloud上的应用。



## 版本声明

Spring Cloud版本号：Hoxton.SR10

Spring Boot版本号：  2.3.9 RELEASE（需要`2.3.x`版本）

Feign版本号：10.10.1

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220106172524428.png)

> 说明：以上列出的是开发中常见的库的版本号，基本上只要保持大版本号不变，可以替换成你自己的版本号，不会有冲突和不兼容现象（特别说明：okhttp并不建议使用4．x版本哦，因为3．x版本也一直在开发维护着．．．）





## 官网介绍

## 使用实例

## Netflix Feign还是Open Feign？
首先需要明确：

在这个时间节点上，很多人对这“两种”Feign傻傻分不清楚，不知有何区别和联系，本文将给与告知。
首先需要明确：他俩是属于同一个东西， Feign属于Netflix开源的组件。针对于于差异性，下面分这几个方面展开做出对比
1、 GAV坐标差异
1  
feign-cores/artifactId> dependency
寸


 8
cartifactId>feign-cores/artifactId> 
2、官网地址差异：
https://github.com/Netflix/feign和https://github.com/0penFeign/feign。不过现在访问 https：//github.com/Netflix/feign已经被重定向到了后者上。
3、发版历史
. Netfix Feign： 1.0.0发布于2013.6，于2016.7月发布其最后一个版本8.18.0
. Open Feign：首个版本便是9.0.0版，于2016.7月发布，然后一直持续发布到现在（未停止）
从以上3个特点其实你可以很清楚的看到两者的区别和联系，简单的理解： Netflix Feign仅仅只是改名成为了Open Feign而已，然后Open Feign项目在其基础上继续发展至今。
9.0版本之前它叫Nettix Feign， 自9.0版本起它改名叫Open Feign了。
但是请注意，虽然GAV完全变了，但是源码的包名和核心API是没有任何变化的，所以扔具有很好的向下兼容性（并不是100%向下兼容）。




## 总结









