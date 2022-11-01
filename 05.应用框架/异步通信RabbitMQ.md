







### 服务异步通信：实用篇-RabbitMQ

#### 一、初识MQ

##### 同步通讯

同步通讯与异步通讯

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215033037334.png" alt="image-20211215033037334" style="zoom:33%;" />

微服务间基于Feign的调用就属于同步方式，存在一些问题。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215104549800.png" alt="image-20211215104549800" style="zoom:33%;" />

同步调用存在的问题：

![image-20211215104824281](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215104824281.png)

> 同步调用的优点：
> 		时效性较强，可以立即得到结果
> 同步调用的问题：
> 		耦合度高
> 		性能和吞吐能力下降
> 		有额外的资源消耗
> 		有级联失败问题

##### 异步通讯

异步调用常见实现就是事件驱动模式

###### 优势一：服务解耦

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215105604758.png" alt="image-20211215105604758" style="zoom:33%;" />

支付服务不负责调用这些服务，而是只发一个事件到`broker`就行了，一旦有新的业务出现，那你要做的就是去订阅broker事件就行。解除了服务间的耦合。

> 观察者模式



###### 优势二：性能提升，吞吐量提高（500ms到60ms）

![image-20211215110111751](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215110111751.png)

###### 优势三：服务没有强依赖，不担心级联失败问题

如果仓储服务挂了，并不影响支付服务的完成，后续可以采取重启仓储服务等方式。

###### 优势四：流量削峰

起到一个缓冲的作用，一次能处理几个就先取几个，处理业务的速度一直按照自己的能力来，压力都由broker来抗。对微服务起到保护作用，秒杀等高并发场景下可以用这样的技术。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215110744609.png" alt="image-20211215110744609" style="zoom:33%;" />

> 异步通信的优点：
> 		耦合度低：不需要调用对方，而是发布事件
> 		吞吐量提升：不存在调用，也就不需要等待对方完成，耗时更短
> 		故障隔离：没有调用，对方挂了和我没有关系
> 		流量削峰：有的功能，broker缓存事件
> 异步通信的缺点：
> 		依赖于Broker的可靠性、安全性、吞吐能力
> 		架构复杂了，业务没有明显的流程线，不好追踪管理

##### MQ常见架构

MQ （MessageQueue），中文是消息队列，字面来看就是存放消息的队列。也就是事件驱动架构中的Broker。

![image-20211215111950165](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215111950165.png)

> Kafka：海量数据，如日志
>
> RabbitMQ、RocketMQ：稳定性更强，可靠性更高：更适合对稳定性要求较高的，比如业务间的通信



#### 二、RabbitMQ快速入门

##### 1、概述和安装

RabbitMQ是基于Erlang语言开发的开源消息通信中间件，官网地址：https://www.rabbitmq.com/
安装RabbitMQ，参考课前资料	`RabbitMQ部署指南.md`

###### 单机部署

我们在Centos7虚拟机中使用Docker来安装。

（1）下载镜像

方式一：在线拉取

```sh
docker pull rabbitmq:3-management
```

方式二：从本地加载

在课前资料已经提供了镜像包：mq.tar

上传到虚拟机中后，使用命令加载镜像即可：

```sh
docker load -i mq.tar
```

（2）安装MQ：执行下面的命令来运行MQ容器：

```sh
docker run \
 -e RABBITMQ_DEFAULT_USER=root \  # 设置环境变量，访问mq和登陆管理平台都需要
 -e RABBITMQ_DEFAULT_PASS=1 \
 --name mq \ # 起个名字
 --hostname mq1 \ # 配置主机名 集群部署需要 
 -p 15672:15672 \ # 管理平台端口，UI界面
 -p 5672:5672 \ # 消息通信端口，收发消息
 -d \ # 后台运行
 rabbitmq:3-management # 镜像名称
```

![image-20211215114353363](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215114353363.png)

![image-20211215114440496](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215114440496.png)



###### 集群部署

如何安装RabbitMQ的集群。

（1）集群分类

在RabbitMQ的官方文档中，讲述了两种集群的配置方式：

- 普通模式：普通模式集群不进行数据同步，每个MQ都有自己的队列、数据信息（其它元数据信息如交换机等会同步）。例如我们有2个MQ：mq1，和mq2，如果你的消息在mq1，而你连接到了mq2，那么mq2会去mq1拉取消息，然后返回给你。如果mq1宕机，消息就会丢失。
- 镜像模式：与普通模式不同，队列会在各个mq的镜像节点之间同步，因此你连接到任何一个镜像节点，均可获取到消息。而且如果一个节点宕机，并不会导致数据丢失。不过，这种方式增加了数据同步的带宽消耗。

（2）设置网络

首先，我们需要让3台MQ互相知道对方的存在。
分别在3台机器中，设置 /etc/hosts文件，添加如下内容：

```sh
192.168.150.101 mq1
192.168.150.102 mq2
192.168.150.103 mq3
```

并在每台机器上测试，是否可以ping通对方。



各个虚拟主机之间是相互隔离的。

![image-20211215131643968](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215131643968.png)

> RabbitMQ中的几个概念：
> 		channel：操作MQ的工具
> 		exchange：路由消息到队列中
> 		queue：缓存消息
> 		virtual host：虚拟主机，是对queue、exchange等资源的逻辑分组



##### 2、常见消息模型

MQ的官方文档中给出了5个MQ的Demo示例，对应了几种不同的用法：

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215132136521.png" alt="image-20211215132136521" style="zoom:33%;" />



##### 3、快速入门

###### HelloWorld案例

**官方的HelloWorld是基于最基础的消息队列模型来实现的，只包括三个角色：**
		publisher：消息发布者，将消息发送到队列queue
		queue：消息队列，负责接受并缓存消息
		consumer：订阅队列，处理队列中的消息

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215132301860.png" alt="image-20211215132301860" style="zoom:33%;" />



**实现步骤：**

> 导入课前资料中的demo工程：mq-demo
> 运行publisher服务中的测试类PublisherTest中的测试方法testSendMessage()
> 查看RabbitMQ控制台的消息
> 启动consumer服务，查看是否能接收消息



**生产者生产消息：**

```java
package cn.itcast.mq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublisherTest {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost("172.16.126.5");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("1");
        // 1.2.建立连接
        Connection connection = factory.newConnection();

        // 2.创建通道Channel
        Channel channel = connection.createChannel();

        // 3.创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.发送消息
        String message = "hello, rabbitmq!";
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("发送消息成功：【" + message + "】");

        // 5.关闭通道和连接
        channel.close();
        connection.close();

    }
}
```



断点查看：建立连接、通道、队列

![image-20211215135546286](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215135546286.png)

查看消息

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215135433498.png" alt="image-20211215135433498" style="zoom:33%;" />

![image-20211215135623313](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215135623313.png)



**消费者消费消息：**

```java
package cn.itcast.mq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost("172.16.126.5");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("1");
        // 1.2.建立连接
        Connection connection = factory.newConnection();

        // 2.创建通道Channel
        Channel channel = connection.createChannel();

        // 3.创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.订阅消息
        // 匿名内部类的写法  回调函数后执行
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 5.处理消息
                String message = new String(body);
                System.out.println("接收到消息：【" + message + "】");
            }
        });
        System.out.println("等待接收消息。。。。");
    }
}
```



![image-20211215140610999](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215140610999.png)

Get Message(s)无

![image-20211215140900450](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215140900450.png)



> 基本消息队列的消息发送流程：
> 		建立connection
> 		创建channel
> 		利用channel声明队列
> 		利用channel向队列发送消息
> 基本消息队列的消息接收流程：
> 		建立connection
> 		创建channel
> 		利用channel声明队列
> 		定义consumer的消费行为handleDelivery()
> 		利用channel将消费者与队列绑定







#### 三、SpringAMQP

> 什么是SpringAMQP，利用它来实现5种消息模型

SpringAmqp的官方地址：https://spring.io/projects/spring-amqp

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215141520671.png" alt="image-20211215141520671" style="zoom:33%;" />



##### 案例1：Basic Queue 简单队列模型

###### 利用SpringAMQP实现HelloWorld中的基础消息队列功能

> 流程如下：
> 在父工程中引入spring-amqp的依赖
> 在publisher服务中利用RabbitTemplate发送消息到simple.queue这个队列
> 在consumer服务中编写消费逻辑，绑定simple.queue这个队列

（1）引入AMQP依赖

因为publisher和consumer服务都需要amqp依赖，因此这里把依赖直接放到父工程mq-demo中：

```xml
<!--AMQP依赖，包含RabbitMQ-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

（2）在publisher中编写测试方法，向simple.queue发送消息

在publisher服务中编写application.yml，添加mq连接信息：

```yaml
logging:
  pattern:
    dateformat: MM-dd HH:mm:ss:SSS
spring:
  rabbitmq:
    host: 172.16.126.5 # 主机名
    port: 5672 # 端口
    virtual-host: / # 虚拟主机
    username: root # 用户名
    password: 1 # 密码
```

> spring帮你建立连接，创建channel。你要做的就是用工具类发送消息就可以，测试类如下：

在publisher服务中新建一个测试类，编写测试方法：

```java
package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue(){
        String queueName = "simple.queue";  // 队列名称
        String message = "hello,spring amqp!";  // 消息
        rabbitTemplate.convertAndSend(queueName,message);
    }

}
```



> 什么是AMQP？
> 		应用间消息通信的一种协议，与语言和平台无关。
> SpringAMQP如何发送消息？
> 		引入amqp的starter依赖
> 		配置RabbitMQ地址
> 		利用RabbitTemplate的convertAndSend方法



（3）在consumer中编写消费逻辑，监听simple.queue

在consumer服务中编写application.yml，添加mq连接信息：

```yaml
spring:
  rabbitmq:
    host: 172.16.126.5 # 主机名
    port: 5672 # 端口
    virtual-host: / # 虚拟主机
    username: root # 用户名
    password: 1 # 密码
```

在consumer服务中新建一个类，编写消费逻辑：

> 声明为spring的一个bean，spring就可以找到了
>
> 声明队列名称，即监听哪个队列，一旦这个队列中有消息，就投放到下面的方法中，方法就可以处理对应的消息
>
> 十分优雅



```java
package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg){
        System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
    }

}
```

spring中的一个bean，启动主类即可

如下，浏览器中查看到消息已被消费，队列中为空

![image-20211215145428241](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215145428241.png)



> SpringAMQP如何接收消息？
> 		引入amqp的starter依赖
> 		配置RabbitMQ地址
> 		定义类，添加@Component注解
> 		类中声明方法，添加@RabbitListener注解，方法参数就时消息
> 注意：消息一旦消费就会从队列删除，RabbitMQ没有消息回溯功能



##### 案例2：Work Queue 工作队列模型

> Work queue，工作队列，可以提高消息处理速度，避免队列消息堆积

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215145741065.png" alt="image-20211215145741065" style="zoom:33%;" />



###### 模拟WorkQueue，实现一个队列绑定多个消费者

> 基本思路如下：
> 		在publisher服务中定义测试方法，每秒产生50条消息，发送到simple.queue
> 		在consumer服务中定义两个消息监听者，都监听simple.queue队列
> 		消费者1每秒处理50条消息，消费者2每秒处理10条消息



（1）生产者循环发送消息到simple.queue

在publisher服务中添加一个测试方法，循环发送50条消息到simple.queue队列

```java
package cn.itcast.mq.spring;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Test
//    public void testSimpleQueue(){
//        String queueName = "simple.queue";  // 队列名称
//        String message = "hello,spring amqp!";  // 消息
//        rabbitTemplate.convertAndSend(queueName,message);
//    }

    @Test
    public void testWorkQueue() throws InterruptedException {
        String queueName = "simple.queue";  // 队列名称
        String message = "hello,spring message__!";  // 消息
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName,message + i);
            Thread.sleep(20);
        }
    }

}
```



（2）步骤2：编写两个消费者，都监听simple.queue

在consumer服务中添加一个消费者，也监听simple.queue：

```java
package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {

//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueue(String msg){
//        System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
//    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到simple.queue的消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2接收到simple.......queue的消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(200);
    }

}
```

启动ConsumerApplication、testWorkQueue

![image-20211215153323071](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215153323071.png)

消费者1拿了所有的奇数消息，消费者1拿了所有的偶数消息。RocketMQ的消息预取机制（管它能不能处理，先拿过来再说）。

修改application.yml文件，设置`preFetch`这个值，可以控制预取消息的上限：

````yaml
spring:
  rabbitmq:
    host: 172.16.126.5 # 主机名
    port: 5672 # 端口
    virtual-host: / # 虚拟主机
    username: root # 用户名
    password: 1 # 密码
    listener:
      simple:
        prefetch: 1 # 每次只能获取一条消息，处理完成才能获取下一个消息
````

![image-20211215154449541](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215154449541.png)

> 1s处理完成，能者多劳

> Work模型的使用：
> 		多个消费者绑定到一个队列，同一条消息只会被一个消费者处理
> 		通过设置prefetch来控制消费者预取的消息数量



> 之前发出的消息只能被一个消费者消费，一旦消费完就会从队列中删除
>
> 这就无法满足课程开始提出的问题：支付服务成功后，通知订单、仓储、短信服务，这三个服务各自去完成自己的业务。也就是说，用户支付成功的消息要被3个消费者都接受到，这就要用到下面的发布和订阅。
>
> 相比之前的变化在于：我们关注的是，消息如何从发布者到达队列。



##### 发布、订阅模型-Fanout

###### 发布（ Publish ）、订阅（ Subscribe ）

> 发布订阅模式与之前案例的区别就是允许将同一消息发送给多个消费者。实现方式是加入了exchange（交换机）。
> 常见exchange交换机类型包括：
> 		Fanout：广播
> 		Direct：路由
> 		Topic：话题

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215160710848.png" alt="image-20211215160710848" style="zoom:33%;" />

> 注意：exchange负责消息路由，而不是存储，路由失败则消息丢失（三者共同特点）



<font color=red>**---->Fanout Exchange 会将接收到的消息广播到每一个跟其绑定的queue。**</font>

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215161027533.png" alt="image-20211215161027533" style="zoom:33%;" />

> 声明2个队列和1个交换机，使其绑定起来

###### 案例：利用SpringAMQP演示FanoutExchange的使用

> 实现思路如下：
> 		在consumer服务中，利用代码声明队列、交换机，并将两者绑定
> 		在consumer服务中，编写两个消费者方法，分别监听fanout.queue1和fanout.queue2
> 		在publisher中编写测试方法，向itcast.fanout发送消息

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215161341475.png" alt="image-20211215161341475" style="zoom:43%;" />

（1）在consumer服务声明Exchange、Queue、Binding

SpringAMQP提供了声明交换机、队列、绑定关系的API，例如：

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215161546889.png" alt="image-20211215161546889" style="zoom:33%;" />

在consumer服务常见一个类，添加@Configuration注解（配置类，可以添加各种bean），并声明FanoutExchange、Queue和绑定关系对象Binding，代码如下：

```java
package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    // itcast.fanout
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("itcast.fanout");
    }

    // fanout.queue1
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    // 绑定队列1到交换机
    @Bean
    public Binding fanoutBanding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue1)
                .to(fanoutExchange);
    }

    // fanout.queue2
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    // 绑定队列2到交换机
    @Bean
    public Binding fanoutBanding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue2)
                .to(fanoutExchange);
    }

}
```



（2）在consumer服务声明两个消费者

在consumer服务的SpringRabbitListener类中，添加两个方法，分别监听fanout.queue1和fanout.queue2：

```java
@RabbitListener(queues = "fanout.queue1")
public void listenFanoutQueue1(String msg){
  System.out.println("消费者接收到fanout.queue1的消息：【" + msg + "】");
}

@RabbitListener(queues = "fanout.queue2")
public void listenFanoutQueue2(String msg){
  System.out.println("消费者接收到fanout.queue2的消息：【" + msg + "】");
}
```

（3）在publisher服务发送消息到FanoutExchange

在publisher服务的SpringAmqpTest类中添加测试方法：

```java
// 以前是发送到队列，现在是发送到交换机，代码略有不同
@Test
public void testSentFanoutExchange(){
  // 交换机名称
  String exchangeName = "itcast.fanout";
  // 消息
  String message = "hello,every one!";
  // 发送消息，参数分别是：交互机名称、RoutingKey（暂时为空）、消息
  rabbitTemplate.convertAndSend(exchangeName,"",message);
}
```



一次发送，多个消费者都可以接收。

![image-20211215163820266](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215163820266.png)



> 交换机的作用是什么？
> 		接收publisher发送的消息
> 		将消息按照规则路由到与之绑定的队列
> 		不能缓存消息，路由失败，消息丢失
> 		FanoutExchange的会将消息路由到每个绑定的队列
> 声明队列、交换机、绑定关系的Bean是什么？
> 		Queue
> 		FanoutExchange
> 		Binding

##### 发布、订阅模型-Direct

<font color=red>**---->Direct Exchange 会将接收到的消息根据规则路由到指定的Queue，因此称为路由模式（routes）。**</font>		每一个Queue都与Exchange设置一个`BindingKey`
		发布者发送消息时，指定消息的RoutingKey
		Exchange将消息路由到BindingKey与消息RoutingKey一致的队列



###### 案例：利用SpringAMQP演示DirectExchange的使用

> 实现思路如下：
> 		利用@RabbitListener声明Exchange、Queue、RoutingKey
> 		在consumer服务中，编写两个消费者方法，分别监听direct.queue1和direct.queue2
> 		在publisher中编写测试方法，向itcast. direct发送消息







##### 发布、订阅模型-Topic







##### 消息转换器



### 分布式搜索引擎elasticsearch

#### 一、初识elasticsearch

##### 了解ES

elasticsearch是一款非常强大的开源搜索引擎，可以帮助我们从海量数据中快速找到需要的内容。

![image-20211215231831606](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215231831606.png)

elasticsearch结合kibana、Logstash、Beats，也就是elastic stack（ELK）。被广泛应用在日志数据分析、实时监控等领域。

elasticsearch是elastic stack的核心，负责存储、搜索、分析数据。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215232105443.png" alt="image-20211215232105443" style="zoom:33%;" />



###### 发展

`Lucene`是一个Java语言的搜索引擎类库，是Apache公司的顶级项目，由DougCutting于1999年研发。

官网地址：https://lucene.apache.org/ 。

> Lucene的优势：
> 		易扩展
> 		高性能（基于倒排索引）
> Lucene的缺点：
> 		只限于Java语言开发
> 		学习曲线陡峭
> 		不支持水平扩展



2004年Shay Banon基于Lucene开发了`Compass`
2010年Shay Banon 重写了Compass，取名为`Elasticsearch`。
官网地址: https://www.elastic.co/cn/
目前最新的版本是：7.12.1

> 相比与lucene，elasticsearch具备下列优势：
> 		支持分布式，可水平扩展
> 		提供Restful接口，可被任何语言调用



<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215232727565.png" alt="image-20211215232727565" style="zoom: 33%;" />

> 什么是elasticsearch？
> 		一个开源的分布式搜索引擎，可以用来实现搜索、日志统计、分析、系统监控等功能
> 什么是elastic stack（ELK）？
> 		是以elasticsearch为核心的技术栈，包括beats、Logstash、kibana、elasticsearch
> 什么是Lucene？
> 		是Apache的开源搜索引擎类库，提供了搜索引擎的核心API



##### 倒排索引





##### es的一些概念



##### 安装es、kibana







#### 二、索引库操作





#### 三、文档操作





#### 四、RestAPI



















