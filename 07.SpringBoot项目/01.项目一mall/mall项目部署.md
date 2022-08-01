---
title: mall项目部署
categories: 7.项目一mall
abbrlink: 6a49
date: 2022-01-15 16:18:23
tags: [项目一]
---

文档地址

http://www.macrozheng.com/#/



# 写在前面

## 项目简介

mall项目是一套电商系统，包括前台商城系统及后台管理系统，基于SpringBoot+MyBatis实现，采用Docker容器化部署。

前台商城系统包含首页门户、商品推荐、商品搜索、商品展示、购物车、订单流程、会员中心、客户服务、帮助中心等模块。

后台管理系统包含商品管理、订单管理、会员管理、促销管理、运营管理、内容管理、统计报表、财务管理、权限管理、设置等模块。



```
mall
├── mall-common -- 工具类及通用代码
├── mall-mbg -- MyBatisGenerator生成的数据库操作代码
├── mall-security -- SpringSecurity封装公用模块
├── mall-admin -- 后台商城管理系统接口
├── mall-search -- 基于Elasticsearch的商品搜索系统
├── mall-portal -- 前台商城系统接口
└── mall-demo -- 框架搭建时的测试代码
```



## 项目演示

- 后台项目演示：http://www.macrozheng.com/admin/index.html

- 移动端项目演示：http://www.macrozheng.com/app/index.html

## mall中使用的技术

> mall采用现阶段主流技术实现，涵盖了一般项目中几乎所有使用的技术。

| 技术             | 版本    | 说明                |
| ---------------- | ------- | ------------------- |
| Spring Boot      | 2.3.0   | 容器+MVC框架        |
| Spring Security  | 5.1.4   | 认证和授权框架      |
| MyBatis          | 3.4.6   | ORM框架             |
| MyBatisGenerator | 1.3.3   | 数据层代码生成      |
| PageHelper       | 5.1.8   | MyBatis物理分页插件 |
| Swagger-UI       | 2.9.2   | 文档生产工具        |
| Elasticsearch    | 7.6.2   | 搜索引擎            |
| RabbitMq         | 3.7.14  | 消息队列            |
| Redis            | 5.0     | 分布式缓存          |
| MongoDb          | 4.2.5   | NoSql数据库         |
| Docker           | 18.09.0 | 应用容器引擎        |
| Druid            | 1.1.10  | 数据库连接池        |
| OSS              | 2.5.0   | 对象存储            |
| JWT              | 0.9.0   | JWT登录支持         |
| Lombok           | 1.18.6  | 简化对象封装工具    |

## mall实现的功能概览

- 商品模块
  - 商品管理
  - 商品分类管理
  - 商品类型管理
  - 品牌管理
- 订单模块
  - 订单管理
  - 订单设置
  - 退货申请处理
  - 退货原因设置
- 营销模块
  - 秒杀活动管理
  - 优惠价管理
  - 品牌推荐管理
  - 新品推荐管理
  - 人气推荐管理
  - 专题推荐管理
  - 首页广告管理



## mall数据库表概览

> mall项目目前有71张数据表，业务逻辑有一定复杂度，平时做项目参考也够了。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/mall_mysql_all.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116114951356.png)





### 数据库表前缀说明

- cms_*：内容管理模块相关表
- oms_*：订单管理模块相关表
- pms_*：商品模块相关表
- sms_*：营销模块相关表
- ums_*：会员模块相关表

## 学习所需知识点

> 由于mall项目涉及到很多知识点，比如SpringBoot、ElasticSearch、Redis、Mongodb等，本教程不会详细讲述这些，只会讲述本项目相关部分，所以推荐以下资料。对其中一些知识点并不熟悉的同学，可以看下下面推荐的资料。

### IDEA

《IntelliJ-IDEA-Tutorial》：https://github.com/judasn/IntelliJ-IDEA-Tutorial

> 特别全的IDEA使用教程，可以学到很多实用的技巧。

### Spring

《Spring实战（第4版）》：https://book.douban.com/subject/26767354/

> 经典的、畅销的Spring学习和实践指南,从此书可以学习到Spring的实用用法，对Spring有个整体的了解，推荐整本阅读。

### SpringBoot

《Spring Boot实战》：https://book.douban.com/subject/26857423/

> SpringBoot的入门书，一共也就200多页，反正我是看完了，其中关于Groovy和Grails部分大可不看。

### MyBatis

《MyBatis从入门到精通》：https://book.douban.com/subject/27074809/

> 很好的一本MyBatis入门书，作者是开源插件PageHelper的项目主，平时忘了MyBatis的一些用法的时候可以当工具书使用，推荐整本阅读

### MySQL

《深入浅出MySQL》：https://book.douban.com/subject/25817684/

> 网易DBA写的一本MySql书籍，作为一个开发者，我们只要看第一部分基础篇、第二部分开发篇、第三部分优化篇即可。

### Linux

《循序渐进Linux（第2版）》：https://book.douban.com/subject/26758194/

> 南非蚂蚁写的一本Linux书籍，作为一个开发者，我们只要看第一篇基础知识篇、第二篇服务器搭建篇即可，后面讲到生产环境部署项目会用到。

### Elasticsearch

《Elasticsearch 权威指南》：https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html

> Elasticsearch官方推荐的中文学习资料，基于Elasticsearch2.4.x版本，比较老，但是可堪一用。大多数成熟的框架，版本迭代用法相差不会很大。

《Elasticsearch 技术解析与实战》：https://book.douban.com/subject/26967826/

> 如果你觉得上面那本ElasticSearch版本太老的话可以看这本。

### MongoDB

《MongoDB实战(第二版)》：https://book.douban.com/subject/27061123/

> 很好的一本MongoDB实战书，作者参与过MongoDB的驱动开发，感兴趣的可以都看下。

### Docker

《Spring Cloud与Docker微服务架构实战》：https://book.douban.com/subject/27028228/

> 我们只需要看下这本书的Docker部分即可，后面讲到生产环境部署项目会用到。

### 结语

>  如果你按照我的推荐看了以上部分的资料，或者你已经有了以上部分的基础，那么你学习mall的时候会非常顺利。



# 部署

## 在Windows环境下部署

> 主要包括IDEA、Mysql、Redis、Mongodb、RabbitMQ、Elasticsearch、Logstash、Kibana、OSS。



## 在Linux环境下部署（基于Docker容器）

> 涉及在Docker容器中安装MySQL、Redis、Nginx、RabbitMQ、MongoDB、Elasticsearch、Logstash、Kibana，以及SpringBoot应用部署，基于`CenterOS7.6`。



## 在Linux环境下部署（基于Docker Compose容器）

> 最简单的mall在Linux下部署方式，使用两个Docker Compose脚本即可完成部署。第一个脚本用于部署mall运行所依赖的服务（MySQL、Redis、Nginx、RabbitMQ、MongoDB、Elasticsearch、Logstash、Kibana），第二个脚本用于部署mall中的应用（mall-admin、mall-search、mall-portal）。

### docker环境搭建及使用

具体参考：开发者必备Docker命令

> 主要讲解Docker环境的安装以及Docker常用命令的使用，掌握这些对Docker环境下应用的部署具有很大帮助。

#### Docker简介

Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows机器上。使用Docker可以更方便地打包、测试以及部署应用程序。

#### Docker环境安装

- 安装yum-utils：

```bash
yum install -y yum-utils device-mapper-persistent-data lvm2
```

- 为yum源添加docker仓库位置：

```bash
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
# 也可以使用下面阿里云的
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

- 安装docker:

```bash
yum install docker-ce
```

- 启动docker:

```bash
systemctl start docker
```

#### Docker镜像常用命令

##### 下载镜像

```sh
docker pull java:8
```

##### 列出镜像

```sh
docker images
```

##### 删除镜像

```sh
# 指定名称删除镜像
docker rmi java:8
# 指定名称删除镜像（强制）
docker rmi -f java:8
# 强制删除所有镜像
docker rmi -f $(docker images)
```

#### Docker容器常用命令

##### 新建并启动容器

```sh
docker run -p 80:80 --name nginx -d nginx:1.17.0
```



```sh
# Docker run命令参数整理
Usage: docker run [OPTIONS] IMAGE [COMMAND] [ARG...]   	
	-d, --detach=false         指定容器运行于前台还是后台，默认为false
	-m, --memory=""            指定容器的内存上限 
	-P, --publish-all=false    指定容器暴露的端口
	-h, --hostname=""          指定容器的主机名
	-v, --volume=[]            给容器挂载存储卷，挂载到容器的某个目录
	--entrypoint=""            覆盖image的入口点
	--name=""                  指定容器名字，后续可以通过名字进行容器管理，links特性需要使用名字  
	--restart="no"             指定容器停止后的重启策略: 
                                no：容器退出时不重启   
                                on-failure：容器故障退出（返回值非零）时重启  
                                always：容器退出时总是重启  
```





##### 列出容器

```sh
# 列出运行中的容器
docker ps
# 列出所有容器
docker ps -a
```

##### 停止容器

```sh
# $ContainerName及$ContainerId可以用docker ps命令查询出来
docker stop $ContainerName(或者$ContainerId)
```

比如：

```sh
docker stop nginx
#或者
docker stop c5f5d5125587
```

##### 进入容器

- 先查询出容器的pid：

```bash
docker inspect --format "{{.State.Pid}}" $ContainerName(或者$ContainerId)
```

- 根据容器的pid进入容器：

```bash
nsenter --target "$pid" --mount --uts --ipc --net --pid
```

##### 删除容器

```bash
# 删除指定容器：
docker rm $ContainerName(或者$ContainerId)
# 强制删除所有容器
docker rm -f $(docker ps -a -q)
```

##### 查看容器的日志

```sh
# 查看当前全部日志
docker logs $ContainerName(或者$ContainerId)
# 动态查看
docker logs $ContainerName(或者$ContainerId) -f
```

##### 在宿主机查看docker使用cpu、内存、网络、io情况

```sh
# 查看指定容器情况：
docker stats $ContainerName(或者$ContainerId)
# 查看所有容器情况：
docker stats -a
```

##### 查看Docker磁盘使用情况

```sh
docker system df
```

##### 进入Docker容器内部的bash

```sh
docker exec -it $ContainerName /bin/bash
```



#### 修改Docker镜像的存放位置

##### 查看Docker镜像的存放位置：

```sh
docker info | grep "Docker Root Dir"
```

##### 关闭Docker服务：

```bash
systemctl stop docker
```

##### 移动目录到目标路径：

```bash
mv /var/lib/docker /mydata/docker
```

##### 建立软连接：

```bash
ln -s /mydata/docker /var/lib/docker
```





### docker-compose环境搭建及使用

具体参考：使用Docker Compose部署SpringBoot应用

> Docker Compose是一个用于定义和运行多个docker容器应用的工具。使用Compose你可以用YAML文件来配置你的应用服务，然后使用一个命令，你就可以部署你配置的所有服务了。

#### 安装

##### 下载Docker Compose

```sh
curl -L https://get.daocloud.io/docker/compose/releases/download/1.24.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
```

##### 修改该文件的权限为可执行

```sh
chmod +x /usr/local/bin/docker-compose
```

##### 查看是否已经安装成功

```sh
docker-compose --version
```



#### 使用Docker Compose的步骤

- 使用Dockerfile定义应用程序环境，一般需要修改初始镜像行为时才需要使用；
- 使用docker-compose.yml定义需要部署的应用程序服务，以便执行脚本一次性部署；
- 使用docker-compose up命令将所有应用服务一次性部署起来。



#### docker-compose.yml常用命令

##### image

> 指定运行的镜像名称

```yaml
# 运行的是mysql5.7的镜像
image: mysql:5.7
```

##### container_name

> 配置容器名称

```yaml
# 容器名称为mysql
container_name: mysql
```

##### ports

> 指定宿主机和容器的端口映射（HOST:CONTAINER）

```yaml
# 将宿主机的3306端口映射到容器的3306端口
ports:
  - 3306:3306
```

##### volumes

> 将宿主机的文件或目录挂载到容器中（HOST:CONTAINER）

```yaml
# 将外部文件挂载到myql容器中
volumes:
  - /mydata/mysql/log:/var/log/mysql
  - /mydata/mysql/data:/var/lib/mysql
  - /mydata/mysql/conf:/etc/mysql
```

##### environment

> 配置环境变量

```yaml
# 设置mysqlroot帐号密码的环境变量
environment:
  - MYSQL_ROOT_PASSWORD=root
```

##### links

> 连接其他容器的服务（SERVICE:ALIAS）

```yaml
# 可以以database为域名访问服务名称为db的容器
links:
  - db:database
```



#### Docker Compose常用命令

##### 构建、创建、启动相关容器

```bash
# -d表示在后台运行
docker-compose up -d
```

##### 指定文件启动

```bash
docker-compose -f docker-compose.yml up -d
```

##### 停止所有相关容器

```bash
docker-compose stop
```

##### 列出所有容器信息

```bash
docker-compose ps
```



#### 使用Docker Compose 部署应用
##### 编写docker-compose.yml文件

> Docker Compose将所管理的容器分为三层，工程、服务及容器。docker-compose.yml中定义所有服务组成了一个工程，services节点下即为服务，服务之下为容器。
>
> 容器与容器直之间可以以服务名称为域名进行访问，比如在mall-tiny-docker-compose服务中可以通过jdbc:mysql//db:3306这个地址来访问db这个mysql服务。

```yaml
version: '3'
services:
  # 指定服务名称
  db:
    # 指定服务使用的镜像
    image: mysql:5.7
    # 指定容器名称
    container_name: mysql
    # 指定服务运行的端口
    ports:
      - 3306:3306
    # 指定容器中需要挂载的文件
    volumes:
      - /mydata/mysql/log:/var/log/mysql
      - /mydata/mysql/data:/var/lib/mysql
      - /mydata/mysql/conf:/etc/mysql
    # 指定容器的环境变量
    environment:
      - MYSQL_ROOT_PASSWORD=root
  # 指定服务名称
  mall-tiny-docker-compose:
    # 指定服务使用的镜像
    image: mall-tiny/mall-tiny-docker-compose:0.0.1-SNAPSHOT
    # 指定容器名称
    container_name: mall-tiny-docker-compose
    # 指定服务运行的端口
    ports:
      - 8080:8080
    # 指定容器中需要挂载的文件
    volumes:
      - /etc/localtime:/etc/localtime
      - /mydata/app/mall-tiny-docker-compose/logs:/var/logs
```

**注意：如果遇到mall-tiny-docker-compose服务无法连接到mysql，需要在mysql中建立mall数据库，同时导入mall.sql脚本。具体参考[使用Dockerfile为SpringBoot应用构建Docker镜像](https://mp.weixin.qq.com/s/U_OcNMpLAJJum_s9jbZLGg)中的运行mysql服务并设置部分。**





##### 使用maven插件构建mall-tiny-docker-compose镜像



##### 运行Docker Compose命令启动所有服务

先将`docker-compose.yml`上传至Linux服务器，再在当前目录下运行如下命令：

```bash
docker-compose up -d
```





### mall项目的docker-compose部署

#### 运行配置要求

CenterOS7.6版本，推荐4G以上内存。

#### 部署相关文件

- 数据库脚本`mall.sql`：https://github.com/macrozheng/mall/blob/master/document/sql/mall.sql
- nginx配置文件`nginx.conf`：https://github.com/macrozheng/mall/blob/master/document/docker/nginx.conf
- Logstash配置文件`logstash.conf`：https://github.com/macrozheng/mall/blob/master/document/elk/logstash.conf
- 系统服务运行脚本`docker-compose-env.yml`：https://github.com/macrozheng/mall/tree/master/document/docker/docker-compose-env.yml
- 应用服务运行脚本`docker-compose-app.yml`：https://github.com/macrozheng/mall/tree/master/document/docker/docker-compose-app.yml

#### 部署前准备

##### 打包并上传mall应用的镜像

需要打包mall-admin、mall-search、mall-portal的docker镜像，具体参考：使用Maven插件为SpringBoot应用构建Docker镜像

> 本文主要介绍如何使用Maven插件将SpringBoot应用打包为Docker镜像，并上传到私有镜像仓库Docker Registry的过程。



```sh
docker run -d -p 5001:5001 --restart=always --name registry2 registry:2
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116012154107.png)



##### 下载所有需要安装的Docker镜像

```sh
docker pull mysql:5.7
docker pull redis:5
docker pull nginx:1.10
docker pull rabbitmq:3.7.15-management
docker pull elasticsearch:7.6.2
docker pull kibana:7.6.2
docker pull logstash:7.6.2
docker pull mongo:4.2.5
```

##### [Elasticsearch](http://www.macrozheng.com/#/deploy/mall_deploy_docker_compose?id=elasticsearch)

- 需要设置系统内核参数，否则会因为内存不足无法启动；

```bash
# 改变设置
sysctl -w vm.max_map_count=262144
# 使之立即生效
sysctl -p
```

- 需要创建`/mydata/elasticsearch/data`目录并设置权限，否则会因为无权限访问而启动失败。

```bash
# 创建目录
mkdir /mydata/elasticsearch/data/
# 创建并改变该目录权限
chmod 777 /mydata/elasticsearch/data
```

##### [Nginx](http://www.macrozheng.com/#/deploy/mall_deploy_docker_compose?id=nginx)

需要拷贝nginx配置文件，否则挂载时会因为没有配置文件而启动失败。

```bash
# 创建目录之后将nginx.conf文件上传到该目录下面
mkdir /mydata/nginx/
```

##### [Logstash](http://www.macrozheng.com/#/deploy/mall_deploy_docker_compose?id=logstash)

修改Logstash的配置文件`logstash.conf`中`output`节点下的Elasticsearch连接地址为`es:9200`。

```properties
output {
  elasticsearch {
    hosts => "es:9200"
    index => "mall-%{type}-%{+YYYY.MM.dd}"
  }
}
```

创建`/mydata/logstash`目录，并将Logstash的配置文件`logstash.conf`拷贝到该目录。

```bash
mkdir /mydata/logstash
```



#### 执行docker-compose-env.yml脚本

> 将该文件上传的linux服务器上，执行docker-compose up命令即可启动mall所依赖的所有服务。

```yaml
version: '3'
services:
  mysql:
    image: mysql:5.7
    container_name: mysql
    command: mysqld --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root #设置root帐号密码
    ports:
      - 3306:3306
    volumes:
      - /mydata/mysql/data/db:/var/lib/mysql #数据文件挂载
      - /mydata/mysql/data/conf:/etc/mysql/conf.d #配置文件挂载
      - /mydata/mysql/log:/var/log/mysql #日志文件挂载
  redis:
    image: redis:5
    container_name: redis
    command: redis-server --appendonly yes
    volumes:
      - /mydata/redis/data:/data #数据文件挂载
    ports:
      - 6379:6379
  nginx:
    image: nginx:1.10
    container_name: nginx
    volumes:
      - /mydata/nginx/nginx.conf:/etc/nginx/nginx.conf #配置文件挂载
      - /mydata/nginx/html:/usr/share/nginx/html #静态资源根目录挂载
      - /mydata/nginx/log:/var/log/nginx #日志文件挂载
    ports:
      - 80:80
  rabbitmq:
    image: rabbitmq:3.7.15-management
    container_name: rabbitmq
    volumes:
      - /mydata/rabbitmq/data:/var/lib/rabbitmq #数据文件挂载
      - /mydata/rabbitmq/log:/var/log/rabbitmq #日志文件挂载
    ports:
      - 5672:5672
      - 15672:15672
  elasticsearch:
    image: elasticsearch:7.6.2
    container_name: elasticsearch
    environment:
      - "cluster.name=elasticsearch" #设置集群名称为elasticsearch
      - "discovery.type=single-node" #以单一节点模式启动
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m" #设置使用jvm内存大小
    volumes:
      - /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins #插件文件挂载
      - /mydata/elasticsearch/data:/usr/share/elasticsearch/data #数据文件挂载
    ports:
      - 9200:9200
      - 9300:9300
  logstash:
    image: logstash:7.6.2
    container_name: logstash
    environment:
      - TZ=Asia/Shanghai
    volumes:
      - /mydata/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf #挂载logstash的配置文件
    depends_on:
      - elasticsearch #kibana在elasticsearch启动之后再启动
    links:
      - elasticsearch:es #可以用es这个域名访问elasticsearch服务
    ports:
      - 4560:4560
      - 4561:4561
      - 4562:4562
      - 4563:4563
  kibana:
    image: kibana:7.6.2
    container_name: kibana
    links:
      - elasticsearch:es #可以用es这个域名访问elasticsearch服务
    depends_on:
      - elasticsearch #kibana在elasticsearch启动之后再启动
    environment:
      - "elasticsearch.hosts=http://es:9200" #设置访问elasticsearch的地址
    ports:
      - 5601:5601
  mongo:
    image: mongo:4.2.5
    container_name: mongo
    volumes:
      - /mydata/mongo/db:/data/db #数据文件挂载
    ports:
      - 27017:27017
```

上传完后在当前目录下执行如下命令：

```bash
docker-compose -f docker-compose-env.yml up -d
```



#### 对依赖服务进行以下设置

当所有依赖服务启动完成后，需要对以下服务进行一些设置。

##### mysql

> 需要创建mall数据库并创建一个可以远程访问的对象reader。

- 将mall.sql文件拷贝到mysql容器的/目录下：

```bash
docker cp /mydata/mall.sql mysql:/
```

- 进入mysql容器并执行如下操作：

```bash
#进入mysql容器
docker exec -it mysql /bin/bash
#连接到mysql服务
mysql -uroot -proot --default-character-set=utf8
#创建远程访问用户
grant all privileges on *.* to 'reader' @'%' identified by '123456';
#创建mall数据库
create database mall character set utf8;
#使用mall数据库
use mall;
#导入mall.sql脚本
source /mall.sql;
```

##### elasticsearch

> 需要安装中文分词器IKAnalyzer，并重新启动。

```bash
docker exec -it elasticsearch /bin/bash
#此命令需要在容器中运行
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.6.2/elasticsearch-analysis-ik-7.6.2.zip
docker restart elasticsearch
```

##### logstash

> 需要安装`json_lines`插件，并重新启动。

```bash
docker exec -it logstash /bin/bash
logstash-plugin install logstash-codec-json_lines
docker restart logstash
```

##### rabbitmq

> 需要创建一个mall用户并设置虚拟host为/mall。

- 访问管理页面地址：[http://192.168.3.101:15672](http://192.168.3.101:15672/)

- 输入账号密码并登录：guest guest
- 创建帐号并设置其角色为管理员：mall mall

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116113210563.png)



- 创建一个新的虚拟host为：/mall

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116113425378.png)



- 点击mall用户进入用户配置页面
- 给mall用户配置该虚拟host的权限

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116113617975.png)



#### 执行docker-compose-app.yml脚本

将该文件上传的linux服务器上，执行docker-compose up命令即可启动mall所有的应用。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116113741955.png)



> 容器全部启动，内存占用情况：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116113821591.png)

#### 开启防火墙即可在其他主机上访问所有服务
```sh
systemctl stop firewalld
```

#### 至此所有服务已经正常启动

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116114008607.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116114243627.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220116115109582.png)








<hr>








## 在Linux环境下的自动化部署（基于Jenkins容器）

## mall前端项目的安装与部署

> 本文主要讲解mall前端项目mall-admin-web的在Windows和Linux环境下的安装及部署。

[项目源码地址](https://github.com/macrozheng/mall-admin-web)



### Windows下的安装及部署

下载nodejs并安装

下载mall-admin-web的代码

从IDEA中打开mall-admin-web项目

打开控制台输入命令安装相关依赖

已经搭建了mall后台环境的启动

未搭建mall后台环境的启动





### Linux下的部署

- 修改prod.env.js文件的配置
- 使用命令进行打包
- 打包后的代码位置
- 将dist目录打包为dist.tar.gz文件
- Linux上nginx的安装可以参考[mall在Linux环境下的部署（基于Docker容器）](https://mp.weixin.qq.com/s/0fVMK107i5bBq8kGQqg8KA)中的nginx部分
- 将dist.tar.gz上传到linux服务器（nginx相关目录）
- 使用该命令进行解压操作
- 删除nginx的html文件夹
- 移动dist文件夹到html文件夹
- 运行mall-admin服务
- 重启nginx
- 访问首页并登录：[http://192.168.3.101](http://192.168.3.101/)

- 发现调用的是Linux服务器地址





<hr>



## mall-swarm在Windows环境下的部署

## mall-swarm在Linux环境下的部署（基于Docker容器）



## 微服务架构下的自动化部署，使用Jenkins来实现！

## mall-swarm微服务项目在K8S下的实践！

## 常用的自动化部署技巧，贼好用，推荐给大家！

