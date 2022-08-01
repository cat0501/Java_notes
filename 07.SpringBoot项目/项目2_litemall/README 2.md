# litemall-0224



## 1. 项目地址

> 王道项目二
>
> 码云：https://gitee.com/linlinjava/litemall         
>
> Github：https://github.com/linlinjava/litemall



## 2. 项目简介

又一个小商场系统，Spring Boot后端 + Vue管理员前端 + 微信小程序用户前端 + Vue用户移动端。

### 2.1 项目架构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/project-structure.png)

### 2.2 技术栈

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/technology-stack.png)

### 2.3 功能

###### 小商城功能

- 首页
- 专题列表、专题详情
- 分类列表、分类详情
- 品牌列表、品牌详情
- 新品首发、人气推荐
- 优惠券列表、优惠券选择
- 团购
- 搜索
- 商品详情、商品评价、商品分享
- 购物车
- 下单
- 订单列表、订单详情、订单售后
- 地址、收藏、足迹、意见反馈
- 客服



###### 管理平台功能

- 会员管理
- 商城管理
- 商品管理
- 推广管理
- 系统管理
- 配置管理
- 统计报表



<hr>
## 3. 项目完成情况

### 3.1 个人维护地址（从0开始搭建，git推送）

https://gitee.com/Lemonade19/litemall-0224



> 3月15日
>
> - 阿里云服务器部署一套。对照着这一套进行本地开发。



### 3.2 本地起一套服务









### 3.3 阿里云服务器上部署

```sh
# 环境准备
## Jdk
- 下载：https://www.oracle.com/java/technologies/javase-jsp-downloads.html
- 上传到阿里云服务器/opt/enviroment路径下，解压：
tar -xzvf jdk-8u321-linux-x64.tar.gz
- 将jdk配置到/etc/profile，才可以在任何一个目录访问jdk：vim /etc/profile
在profile文件尾部添加如下内容：
export JAVA_HOME=/opt/enviroment/jdk1.8.0_321  #jdk安装目录
export JRE_HOME=${JAVA_HOME}/jre

export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:$CLASSPATH
export JAVA_PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin
export PATH=$PATH:${JAVA_PATH}
- 刷新生效
source /etc/profile
- 测试是否安装成功
javac
java -version
- 参考：https://blog.csdn.net/pdsu161530247/article/details/81582980


## MySQL
- 下载
- 上传到阿里云服务器/opt/enviroment路径下，解压：
tar -xvf mysql-5.7.26-linux-glibc2.12-x86_64.tar.gz
- 参考：https://blog.csdn.net/qq_37598011/article/details/93489404
- 参考：https://blog.csdn.net/qq_34218345/article/details/106951035

- 数据库依次导入litemall-db/sql下的数据库文件

## Nodejs

## nginx
- 官网：http://nginx.org/en/download.html
- 参考
https://blog.51cto.com/favccxx/1620159
https://blog.51cto.com/favccxx/1620160
https://blog.csdn.net/weixin_43930641/article/details/105313937
https://blog.csdn.net/hybaym/article/details/50929958

https://www.cnblogs.com/zxiaoy/p/14927922.html
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220315233334983.png)



> nginx
>
> ./configure进行初始化即可。注意这里生成的配置文件，尤其箭头所指的方向，是启动nginx时的路径。
>
> /usr/local/nginx/sbin/nginx

![image-20220316101341781](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220316101341781.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220316102001376.png)



```sh
# litemall-admin-api打Jar包，上传服务器，脚本运行。
```



> 管理后台地址：http://39.101.189.62/#/dashboard
> 管理后台接口swagger地址：http://39.101.189.62:8089/swagger-ui.html



## 4. 项目分析和总结

```lua
litemall
├── litemall-admin -- 管理后台VUE前端
├── litemall-admin-api -- 管理后台Spring Boot后端
├── litemall-core -- SpringSecurity封装公用模块
├── litemall-db -- 后台商城管理系统接口
├── 
├── litemall-wx -- 小商城微信小程序前端
├── litemall-vue -- 轻商城VUE前端
└── litemall-wx-api -- 小商城Spring Boot后端
```



##### 技术点1：shiro登陆接口的分析

###### Jackson之ObjectMapper对象的使用



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-json</artifactId>
</dependency>
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220316155414693.png)



###### 流程

```java
// 1、参数校验：用户名、密码
String username = JacksonUtil.parseString(body, "username");
String password = JacksonUtil.parseString(body, "password");

if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
    return ResponseUtil.badArgument();
}
```



##### 技术点2：接口文档swagger

- http://39.101.189.62:8089/swagger-ui.html

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220319202718498.png)



- http://39.101.189.62:8089/doc.html

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220319202534587.png)







## 5. 从0开发

> 第一个列入计划的项目
>
> 每多一行代码都要记录清楚



见开发计划.txt











<hr>







## 6. 致谢


> 本项目基于或参考以下项目：

1. [nideshop-mini-program](https://github.com/tumobi/nideshop-mini-program)

   项目介绍：基于Node.js+MySQL开发的开源微信小程序商城（微信小程序）

   项目参考：

   1. litemall项目数据库基于nideshop-mini-program项目数据库；
   2. litemall项目的litemall-wx模块基于nideshop-mini-program开发。

2. [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)

   项目介绍： 一个基于Vue和Element的后台集成方案

   项目参考：litemall项目的litemall-admin模块的前端框架基于vue-element-admin项目修改扩展。

3. [mall-admin-web](https://github.com/macrozheng/mall-admin-web)

   项目介绍：mall-admin-web是一个电商后台管理系统的前端项目，基于Vue+Element实现。

   项目参考：litemall项目的litemall-admin模块的一些页面布局样式参考了mall-admin-web项目。

4. [biu](https://github.com/CaiBaoHong/biu)

   项目介绍：管理后台项目开发脚手架，基于vue-element-admin和springboot搭建，前后端分离方式开发和部署。

   项目参考：litemall项目的权限管理功能参考了biu项目。

5. [vant--mobile-mall](https://github.com/qianzhaoy/vant--mobile-mall)

   项目介绍：基于有赞 vant 组件库的移动商城。

   项目参考：litemall项目的litemall-vue模块基于vant--mobile-mall项目开发。



## 7. 推荐

1. [Flutter_Mall](https://github.com/youxinLu/mall)

   项目介绍：Flutter_Mall是一款Flutter开源在线商城应用程序。

2. [Taro_Mall](https://github.com/jiechud/taro-mall)

   项目介绍：Taro_Mall是一款多端开源在线商城应用程序，后台是基于litemall基础上进行开发，前端采用Taro框架编写。

































