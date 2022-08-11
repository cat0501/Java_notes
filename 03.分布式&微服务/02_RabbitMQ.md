# 写在前面

## 推荐







# 服务异步通信RabbitMQ



## 初识MQ

### 同步通讯

微服务间基于Feign的调用就属于同步方式，存在一些问题。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410171419640.png)



同步调用存在的问题

> 耦合度高，违反开闭原则

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410171452063.png)



```bash
# 同步调用的优点：
时效性较强，可以立即得到结果

# 同步调用的问题：
耦合度高
性能和吞吐能力下降
有额外的资源消耗
有级联失败问题
```



### 异步通讯

异步调用常见实现就是事件驱动模式

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410172708529.png)



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410172743457.png)



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410172812702.png)



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410172834096.png)



```bash
# 异步通信的优点：
耦合度低
吞吐量提升
故障隔离
流量削峰

# 异步通信的缺点：
依赖于Broker的可靠性、安全性、吞吐能力
架构复杂了，业务没有明显的流程线，不好追踪管理
```





### MQ常见框架

MQ （MessageQueue），中文是消息队列，字面来看就是存放消息的队列。也就是事件驱动架构中的Broker。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410173618414.png)





## RabbitMQ快速入门

### RabbitMQ概述和安装(可靠性、稳定性、高可用)

RabbitMQ是基于Erlang语言开发的开源消息通信中间件，官网地址：https://www.rabbitmq.com/

可以基于docker安装



> 各个用户虚拟主机进行隔离



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410174040438.png)



```bash
# RabbitMQ中的几个概念：
channel：操作MQ的工具
exchange：路由消息到队列中
queue：缓存消息
virtual host：虚拟主机，是对queue、exchange等资源的逻辑分组
```







### 常见消息模型

MQ的官方文档中给出了5个MQ的Demo示例，对应了几种不同的用法：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410174623504.png)



> 前两种不是完整的消息驱动模型



### 快速入门 HelloWorld案例

官方的HelloWorld是基于最基础的消息队列模型来实现的，只包括三个角色：

- publisher：消息发布者，将消息发送到队列queue
- queue：消息队列，负责接受并缓存消息
- consumer：订阅队列，处理队列中的消息



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410175654089.png)



实现步骤：

- 导入课前资料中的demo工程
- 运行publisher服务中的测试类PublisherTest中的测试方法testSendMessage()
- 查看RabbitMQ控制台的消息
- 启动consumer服务，查看是否能接收消息



<br>

```bash
# 基本消息队列的消息发送流程：
建立connection
创建channel
利用channel声明队列
利用channel向队列发送消息

# 基本消息队列的消息接收流程：
建立connection
创建channel
利用channel声明队列
定义consumer的消费行为handleDelivery()
利用channel将消费者与队列绑定
```





## SpringAMQP



SpringAmqp的官方地址：https://spring.io/projects/spring-amqp

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410181100816.png)



### Basic Queue 简单队列模型

#### 利用SpringAMQP实现HelloWorld中的基础消息队列功能

流程如下：

步骤1：在父工程中引入spring-amqp的依赖

> 因为publisher和consumer服务都需要amqp依赖，因此这里把依赖直接放到父工程mq-demo中

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410181536945.png)



<br>

步骤2：在publisher服务中利用RabbitTemplate发送消息到simple.queue这个队列（使用很优雅）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410181655809.png)

<br>

在consumer服务中编写消费逻辑，绑定simple.queue这个队列

![image-20220410181751954](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410181751954.png)



```bash
# 什么是AMQP？
应用间消息通信的一种协议，与语言和平台无关。

# SpringAMQP如何发送消息？
引入amqp的starter依赖
配置RabbitMQ地址
利用RabbitTemplate的convertAndSend方法

# SpringAMQP如何接收消息？
引入amqp的starter依赖
yml配置RabbitMQ地址
定义类，添加@Component注解
类中声明方法，添加@RabbitListener注解，方法参数就是消息
注意：消息一旦消费就会从队列删除，RabbitMQ没有消息回溯功能
```





### Work Queue 工作队列模型

Work queue，工作队列，可以提高消息处理速度，避免队列消息堆积

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410184405468.png)

#### 模拟WorkQueue，实现一个队列绑定多个消费者

```bash
基本思路如下：
在publisher服务中定义测试方法，每秒产生50条消息，发送到simple.queue
在consumer服务中定义两个消息监听者，都监听simple.queue队列
消费者1每秒处理50条消息，消费者2每秒处理10条消息
```



步骤1：生产者循环发送消息到simple.queue

在publisher服务中添加一个测试方法，循环发送50条消息到simple.queue队列

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410184817475.png)

<br>

步骤2：编写两个消费者，都监听simple.queue

在consumer服务中添加一个消费者，也监听simple.queue：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410184908534.png)



#### 消费预取限制

修改application.yml文件，设置preFetch这个值，可以控制预取消息的上限：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410184948736.png)



<br>

```bash
Work模型的使用：
多个消费者绑定到一个队列，同一条消息只会被一个消费者处理
通过设置prefetch来控制消费者预取的消息数量
```



### 发布、订阅模型-Fanout

发布订阅模式与之前案例的区别就是允许将同一消息发送给多个消费者。实现方式是加入了exchange（交换机）。

<br>

常见exchange类型包括：

- Fanout：广播
- Direct：路由
- Topic：话题



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410185817593.png)

注意：exchange负责消息路由，而不是存储，路由失败则消息丢失

<hr>





Fanout Exchange 会将接收到的消息广播到每一个跟其绑定的queue

![image-20220410194230317](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410194230317.png)

#### 利用SpringAMQP演示FanoutExchange的使用

```bash
# 实现思路如下：
在consumer服务中，利用代码声明队列、交换机，并将两者绑定
在consumer服务中，编写两个消费者方法，分别监听fanout.queue1和fanout.queue2
在publisher中编写测试方法，向itcast.fanout发送消息
```



步骤1：在consumer服务声明Exchange、Queue、Binding

SpringAMQP提供了声明交换机、队列、绑定关系的API，例如：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410195551662.png)



<br>

在consumer服务创建一个类，添加@Configuration注解，并声明FanoutExchange、Queue和绑定关系对象Binding，代码如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410191341735.png)



步骤2：在consumer服务声明两个消费者 

在consumer服务的SpringRabbitListener类中，添加两个方法，分别监听fanout.queue1和fanout.queue2：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410191411663.png)



步骤3：在publisher服务发送消息到FanoutExchange

在publisher服务的SpringAmqpTest类中添加测试方法：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410195722519.png)



<br>

```bash
# 交换机的作用是什么？
接收publisher发送的消息
将消息按照规则路由到与之绑定的队列
不能缓存消息，路由失败，消息丢失
FanoutExchange的会将消息路由到每个绑定的队列

# 声明队列、交换机、绑定关系的Bean是什么？
Queue
FanoutExchange
Binding
```



### 发布、订阅模型-Direct

```bash
# Direct Exchange 会将接收到的消息根据规则路由到指定的Queue，因此称为路由模式（routes）。
每一个Queue都与Exchange设置一个BindingKey
发布者发送消息时，指定消息的RoutingKey
Exchange将消息路由到BindingKey与消息RoutingKey一致的队列
```

#### 利用SpringAMQP演示DirectExchange的使用





```bash
# 描述下Direct交换机与Fanout交换机的差异？
Fanout交换机将消息路由给每一个与之绑定的队列
Direct交换机根据RoutingKey判断路由给哪个队列
如果多个队列具有相同的RoutingKey，则与Fanout功能类似

# 基于@RabbitListener注解声明队列和交换机有哪些常见注解？
@Queue
@Exchange
```





### 发布、订阅模型-Topic消息转换器

```bash
TopicExchange与DirectExchange类似，区别在于routingKey必须是多个单词的列表，并且以 . 分割。
Queue与Exchange指定BindingKey时可以使用通配符：
#：代指0个或多个单词
*：代指一个单词
```



> china.news 代表有中国的新闻消息；
>
> china.weather 代表中国的天气消息；
>
> japan.news 则代表日本新闻
>
> japan.weather 代表日本的天气消息；









```bash
# SpringAMQP中消息的序列化和反序列化是怎么实现的？
利用MessageConverter实现的，默认是JDK的序列化
注意发送方与接收方必须使用相同的MessageConverter
```











