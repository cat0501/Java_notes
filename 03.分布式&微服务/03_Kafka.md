# 

# Kafka入门和基本使用

- Stay focused and work hard！

## 0 为什么要学习

- 到 2019 年，当下互联网行业最火的技术当属 ABC 了，即所谓的 AI 人工智能、BigData 大数据和 Cloud 云计算云平台。
  - 坦率说 A 和 C 是有点曲高和寡的，不是所有玩家都能入场。

- Kafka 有着非常广阔的应用场景。不谦虚地说，目前 Apache Kafka 被认为是整个消息引擎领域的执牛耳者。

- 从学习技术的角度而言，Kafka 也是很有亮点的。
  - 仅需要学习一套框架就能在实际业务系统中实现消息引擎应用、应用程序集成、分布式存储构建，甚至是流处理应用的开发与部署。

- 学透 Kafka 有什么路径吗？
  - 根据你掌握的编程语言去寻找对应的 Kafka 客户端。
    - 当前 Kafka 最重要的两大客户端是 `Java` 客户端和 `libkafka` 客户端，它们更新和维护的速度很快，非常适合你持续花时间投入。
  - 确定了要使用的客户端，马上去官网上学习一下代码示例，如果能够正确编译和运行这些样例，你就能轻松地驾驭客户端了。
  - 尝试修改样例代码尝试去理解并使用其他的 API，之后观测你修改的结果。
  - 自己编写一个小型项目来验证下学习成果，然后就是改善和提升客户端的可靠性和性能了。
  - 可以熟读一遍 Kafka 官网文档，确保你理解了那些可能影响可靠性和性能的参数。
  - 学习 Kafka 的高级功能，比如流处理应用开发。
    - 流处理 API 不仅能够生产和消费消息，还能执行高级的流式处理操作，比如时间窗口聚合、流处理连接等。

- 可从六个方面展开

  ![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/kafka.png)

  - 1、介绍消息引擎这类系统大致的原理和用途，以及作为优秀消息引擎代表的 Kafka 在这方面的表现。
  - 2、重点探讨 Kafka 如何用于生产环境，特别是线上环境方案的制定。
  - 3、学习 Kafka 客户端的方方面面，既有生产者的实操讲解也有消费者的原理剖析。
  - 4、着重介绍 Kafka 最核心的设计原理，包括 Controller 的设计机制、请求处理全流程解析等。
  - 5、Kafka 运维与有效监控
  - 6、Kafka 流处理组件 Kafka Streams 的实战应用



## 1 消息引擎系统ABC

- Kafka 是什么呢？
  - Apache Kafka 是一款开源的消息引擎系统。
  - 相关提法：“消息队列”、“消息中间件”

- 消息引擎系统，那这类系统是做什么用的呢？
  - 根据维基百科的定义，消息引擎系统是一组规范。企业利用这组规范在不同系统之间传递语义准确的消息，实现松耦合的异步式数据传递。
  - 民间版：系统 A 发送消息给消息引擎系统，系统 B 从消息引擎系统中读取 A 发送的消息。

- 最基础的消息引擎就是做民间版这点事的！其中提到了两个重要的事实：
  - 消息引擎传输的对象是消息；
  - 如何传输消息属于消息引擎设计机制的一部分。

<br>

- 消息格式
  - 使用的是纯二进制的字节序列。
  - 当然消息还是结构化的，只是在使用之前都要将其转换成二进制的字节序列。

- 具体的传输协议（即我用什么方法把消息传输出去）（Kafka 同时支持这两种消息引擎模型）
  - **点对点模型**（也叫消息队列模型）：系统 A 发送的消息只能被系统 B 接收，其他任何系统都不能读取 A 发送的消息。
  - **发布 / 订阅模型**：可能存在多个【发布者】向相同的【主题（Topic）】发送消息，而【订阅者】也可能存在多个，它们都能接收到相同主题的消息。

<br>

- 为什么要使用它？（削峰填谷 + 松耦合）
  - 削峰填谷：指缓冲上下游瞬时突发流量，使其更平滑。
    - 有效地对抗上游的流量冲击，真正做到将上游的“峰”填满到“谷”中
  - 另一大好处在于发送方和接收方的松耦合，这也在一定程度上简化了应用的开发，减少了系统间不必要的交互。

- 极客时间购买课程案例
  - 当引入了 Kafka 之后。上游订单服务不再直接与下游子服务进行交互。
  - 当新订单生成后它仅仅是向 `Kafka Broker` 发送一条订单消息即可。
  - 类似地，下游的各个子服务订阅 Kafka 中的对应主题，并实时从该主题的各自分区（Partition）中获取到订单消息进行处理。
  - 从而实现了上游订单服务与下游订单处理服务的解耦。
  - 当出现秒杀业务时，Kafka 能够将瞬时增加的订单流量全部以消息形式保存在对应的主题中，既不影响上游服务的 TPS，同时也给下游子服务留出了充足的时间去消费它们。

- 最后

```bash
在今天结束之前，我还想和你分享一个自己的小故事。
在 2015 年那会儿，我花了将近 1 年的时间阅读 Kafka 源代码，期间多次想要放弃。你要知道阅读将近 50 万行源码是多么痛的领悟。
我还记得当初为了手写源代码注释，自己写满了一个厚厚的笔记本。
不过幸运的是我坚持了下来，之前的所有努力也没有白费，以至于后面写书、写极客时间专栏就变成了一件件水到渠成的事情。

最后我想送给你一句话：聪明人也要下死功夫。
我不记得这是曾国藩说的还是季羡林说的，但这句话对我有很大影响，当我感到浮躁的时候它能帮我静下心来踏踏实实做事情。
希望这句话对你也有所启发。切记：聪明人要下死功夫！
```



## 2 快速搞定Kafka术语

- 消息：Record。Kafka 是消息引擎嘛，这里的消息就是指 Kafka 处理的主要对象。
- 主题：Topic。主题是承载消息的逻辑容器，在实际使用中多用来区分具体的业务。
- 分区：Partition。一个有序不变的消息序列。每个主题下可以有多个分区。
- 消息位移：Offset。表示分区中每条消息的位置信息，是一个单调递增且不变的值。
- 副本：Replica。Kafka 中同一条消息能够被拷贝到多个地方以提供数据冗余，这些地方就是所谓的副本。副本还分为领导者副本和追随者副本，各自有不同的角色划分。副本是在分区层级下的，即每个分区可配置多个副本实现高可用。
- 生产者：Producer。向主题发布新消息的应用程序。
- 消费者：Consumer。从主题订阅新消息的应用程序。
- 消费者位移：Consumer Offset。表征消费者消费进度，每个消费者都有自己的消费者位移。
- 消费者组：Consumer Group。多个消费者实例共同组成的一个组，同时消费多个分区以实现高吞吐。
- 重平衡：Rebalance。消费者组内某个消费者实例挂掉后，其他消费者实例自动重新分配订阅主题分区的过程。Rebalance 是 Kafka 消费者端实现高可用的重要手段。



![img](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/58c35d3ab0921bf0476e3ba14069d291.jpg)

- Kafka体系架构=M个producer +N个broker +K个consumer+ZK集群

### 基础架构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220726221521894.png)



- Producer：消息生产者，就是向 Kafka broker 发消息的客户端。
- Consumer：消息消费者，向 Kafka broker 取消息的客户端。
- Consumer Group（CG）：消费者组，由多个 consumer 组成。
  - 消费者组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费；
  - 消费者组之间互不影响。
  - 所有的消费者都属于某个消费者组，即**消费者组是逻辑上的一个订阅者**。

- Broker：一台 `Kafka` 服务器就是一个 `broker`。
  - 一个集群由多个 broker 组成。一个`broker` 可以容纳多个 `topic`。

- Topic：可以理解为一个队列，**生产者和消费者面向的都是一个 topic**。
- Partition：为了实现扩展性，一个非常大的 topic 可以分布到多个 broker（即服务器）上，一个 topic 可以分为多个 partition，每个 partition 是一个有序的队列。
- Replica：副本。一个 topic 的每个分区都有若干个副本，一个 `Leader` 和若干个 `Follower`。
- Leader：每个分区多个**副本的“主”**，生产者发送数据的对象，以及消费者消费数据的对象都是 Leader。
- Follower：每个分区多个**副本中的“从**”，实时从 Leader 中同步数据，保持和Leader 数据的同步。Leader 发生故障时，某个Follower 会成为新的 Leader。



## 3 Kafka只是消息引擎系统吗？

Apache Kafka 是消息引擎系统，也是一个分布式流处理平台（Distributed Streaming Platform）。



## 4 我应该选择哪种Kafka？

- Apache Kafka（社区版 Kafka）
  - 优势在于迭代速度快，社区响应度高，使用它可以让你有更高的把控度；
  - 缺陷在于仅提供基础核心组件，缺失一些高级的特性。

- Confluent Kafka（Confluent 公司提供）
  - 优势在于集成了很多高级特性且由 Kafka 原班人马打造，质量上有保证；
  - 缺陷在于相关文档资料不全，普及率较低，没有太多可供参考的范例。

- CDH/HDP Kafka（大数据云公司提供的 Kafka，内嵌 Apache Kafka）
  - 优势在于操作简单，节省运维成本；
  - 缺陷在于把控度低，演进速度较慢。

<br>

- 推荐的监控工具
  - 试试JMXTrans + InfluxDB + Grafana
  - 一个是 kafka tools ，能够清晰的看到kafka存储结构。一个是 granafa，能看到消费的折线图。
  - 滴滴开源https://github.com/didi/Logi-KafkaManager，是目前市面上最好用的一站式 Kafka 集群指标监控与运维管控平台。





## 5 聊聊Kafka的版本号

- Kafka 版本命名：大版本号 - 小版本号 - Patch 号，如 kafka-2.11-2.1.1
  - 2.11是 `Scala` 编译器版本
  - 真正的 `Kafka` 版本号实际上是 2.1.1
    - 前面的 2 表示大版本号，即 `Major Version`；中间的 1 表示小版本号或次版本号，即 `Minor Version`；最后的 1 表示修订版本号，也就是 `Patch` 号。

- Kafka 版本演进

  - 0.7版本：只提供最基础的消息队列功能
  - 0.8版本
    - 引入了副本机制（成为了一个真正意义上完备的分布式高可靠消息队列解决方案）（较好地做到消息无丢失）
    - 0.8.2.0 版本社区引入了新版本 `Producer API`，即需要指定 Broker 地址的 Producer

  - 0.9版本
    - 增加了基础的安全认证 / 权限功能，Java重写了新的consumer API；（不建议使用consumer API）
    - 引入了 Kafka Connect 组件用于实现高性能的数据抽取； 
  - 0.10版本（里程碑式的大版本）
    - 引入Kafka Streams功能（Kafka 正式升级成分布式流处理平台）；
    - 建议版本0.10.2.2；建议使用新版consumer API 
  - 0.11版本（2017 年 6 月）
    - 提供幂等性 Producer API 以及事务（Transaction） API；
    - 对 Kafka 消息格式做了重构。
    - 建议版本0.11.0.3（目前最主流的版本之一）。

  - 1.0 和 2.0 版本：主要还是 Kafka Streams 的各种改进，在消息引擎方面并未引入太多的重大功能特性。
    - 如果你是 Kafka Streams 的用户，至少选择 2.0.0 版本吧。
    - 如果你在意的依然是消息引擎，那么这两个大版本都是适合于生产环境的。
  - 3.0版本

- 最后建议，不论你用的是哪个版本，都请尽量保持服务器端版本和客户端版本一致，否则你将损失很多 Kafka 为你提供的性能优化收益。



## 6 Kafka 生产者

### 生产者消息发送流程

- 发送原理：在消息发送的过程中，涉及到了两个线程——`main` 线程和 `Sender` 线程。
  - 在 `main` 线程中创建了一个双端队列 `RecordAccumulator` ,
  - main 线程将消息发送给 RecordAccumulator，
  - `Sender` 线程不断从 RecordAccumulator 中拉取消息发送到 `Kafka Broker`。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/kafka_send.png)



- 生产者重要参数列表

| 参数名称                              | 描述                                                         |
| ------------------------------------- | ------------------------------------------------------------ |
| bootstrap.servers                     | 生产者连接集群所需的 broker 地 址 清 单 。 <br/>例如 hadoop102:9092,hadoop103:9092,hadoop104:9092，可以设置 1 个或者多个，中间用逗号隔开。注意这里并非需要所有的 broker 地址，因为生产者从给定的 broker里查找到其他 broker 信息。 |
| key.serializer 和 value.serializer    | 指定发送消息的 key 和 value 的序列化类型。一定要写全类名。   |
| buffer.memory                         | RecordAccumulator 缓冲区总大小，默认 32m。                   |
| batch.size                            | 缓冲区一批数据最大值，默认 16k。适当增加该值，可以提高吞吐量，但是如果该值设置太大，会导致数据传输延迟增加。 |
| linger.ms                             | 如果数据迟迟未达到 batch.size，sender 等待 linger.time之后就会发送数据。单位 ms，默认值是 0ms，表示没有延迟。生产环境建议该值大小为 5-100ms 之间。 |
| acks                                  | 0：生产者发送过来的数据，不需要等数据落盘应答。<br/>1：生产者发送过来的数据，Leader 收到数据后应答。<br/>-1（all）：生产者发送过来的数据，Leader+和 isr 队列里面的所有节点收齐数据后应答。默认值是-1，-1 和all 是等价的。 |
| max.in.flight.requests.per.connection | 允许最多没有返回 ack 的次数，默认为 5，开启幂等性要保证该值是 1-5 的数字。 |
| retries                               | 当消息发送出现错误的时候，系统会重发消息。<br/>retries表示重试次数。默认是 int 最大值，2147483647。<br/>如果设置了重试，还想保证消息的有序性，需要设置MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION=1，否则在重试此失败消息的时候，其他的消息可能发送成功了。 |
| retry.backoff.ms                      | 两次重试之间的时间间隔，默认是 100ms。                       |
| enable.idempotence                    | 是否开启幂等性，默认 true，开启幂等性。                      |
| compression.type                      | 生产者发送的所有数据的压缩方式。默认是 none，也就是不压缩。<br/>支持压缩类型：none、gzip、snappy、lz4 和 zstd。 |



### 异步发送 API

#### 普通异步发送

需求：创建 Kafka 生产者，采用异步的方式发送到 Kafka Broker。

- 创建工程 kafka
- 导入依赖

```java
<dependencies>
 	<dependency>
 		<groupId>org.apache.kafka</groupId>
 		<artifactId>kafka-clients</artifactId>
 		<version>3.0.0</version>
 	</dependency>
</dependencies>
```

- 创建包名：com.atguigu.kafka.producer
- 编写不带回调函数的 API 代码

```java
package com.atguigu.kafka.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducer {

    public static void main(String[] args) {

        // 0 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092,hadoop103:9092");

        // 指定对应的key和value的序列化类型 key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1 创建kafka生产者对象
        // "" hello
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first","atguigu"+i));
        }

        // 3 关闭资源
        kafkaProducer.close();
    }
}
```

- 测试

  - ①在 hadoop102 上开启 Kafka 消费者。

  ```bash
  [atguigu@hadoop103 kafka]$ bin/kafka-console-consumer.sh --bootstrap-server hadoop102:9092 --topic first
  ```

  - ②在 IDEA 中执行代码，观察 hadoop102 控制台中是否接收到消息。

  ```bash
  [atguigu@hadoop102 kafka]$ bin/kafka-console-consumer.sh --bootstrap-server hadoop102:9092 --topic first
  atguigu 0
  atguigu 1
  atguigu 2
  atguigu 3
  atguigu 4
  ```



#### 带回调函数的异步发送

- 回调函数会在 `producer` 收到 `ack` 时调用，为异步调用
  - 该方法有两个参数，分别是元数据信息（RecordMetadata）和异常信息（Exception）
  - 如果 `Exception` 为 `null`，说明消息发送成功，如果 Exception 不为 null，说明消息发送失败。

```java
package com.atguigu.kafka.producer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerCallback {

    public static void main(String[] args) throws InterruptedException {

        // 0 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092,hadoop103:9092");

        // 指定对应的key和value的序列化类型 key.serializer
        // properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1 创建kafka生产者对象
        // "" hello
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        for (int i = 0; i < 500; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", "atguigu" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {

                    if (exception == null){
                        System.out.println("主题： "+metadata.topic() + " 分区： "+ metadata.partition());
                    }
                }
            });

            Thread.sleep(2);
        }

        // 3 关闭资源
        kafkaProducer.close();
    }
}
```



- 测试

  - ①在 hadoop102 上开启 Kafka 消费者。
  - ②在 IDEA 中执行代码，观察 hadoop102 控制台中是否接收到消息。

  ```bash
  [atguigu@hadoop102 kafka]$ bin/kafka-console-consumer.sh --bootstrap-server hadoop102:9092 --topic first
  atguigu 0
  atguigu 1
  atguigu 2
  atguigu 3
  atguigu 4
  ```

  - ③在 IDEA 控制台观察回调信息。

  ```bash
  主题：first->分区：0
  主题：first->分区：0
  主题：first->分区：1
  主题：first->分区：1
  主题：first->分区：1
  ```

  

### 同步发送 API

- 只需在异步发送的基础上，再调用一下 `get()` 方法即可。

```java
package com.atguigu.kafka.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CustomProducerSync {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 0 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092,hadoop103:9092");

        // 指定对应的key和value的序列化类型 key.serializer
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1 创建kafka生产者对象
        // "" hello
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first","atguigu"+i)).get();
        }

        // 3 关闭资源
        kafkaProducer.close();
    }
}
```



### 生产者分区

- 分区好处
  - 便于合理使用存储资源
  - 提高并行度：生产者可以以分区为单位发送数据；消费者可以以分区为单位进行消费数据。



- 生产者发送消息的分区策略

### 生产经验——生产者如何提高吞吐量

```java
public class CustomProducerParameters {

    public static void main(String[] args) {

        // 0 配置
        Properties properties = new Properties();

        // 连接kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092,hadoop103:9092");

        // 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 缓冲区大小（默认32M）
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);

        // 批次大小（默认16K）
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);

        // linger.ms（等待时间，默认0）
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        // 压缩（默认 none，可配置值 gzip、snappy、lz4 和 zstd）
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");


        // 1 创建生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first","atguigu"+i));
        }

        // 3 关闭资源
        kafkaProducer.close();
    }
}
```

- 测试

  - ①在 hadoop102 上开启 Kafka 消费者。
  - ②在 IDEA 中执行代码，观察 hadoop102 控制台中是否接收到消息。

  ```bash
  [atguigu@hadoop102 kafka]$ bin/kafka-console-consumer.sh --bootstrap-server hadoop102:9092 --topic first
  atguigu 0
  atguigu 1
  atguigu 2
  atguigu 3
  atguigu 4
  ```

  

### 生产经验——数据可靠性









### 生产经验——数据去重



#### 数据传递语义







#### 幂等性

- 所谓幂等性，指Producer不论向Broker发送多少次重复数据，Broker端都只会持久化一条，保证了不重复。

- 如何使用
  - 开启参数 `enable.idempotence` 默认为 true，false 关闭。

#### 生产者事务





### 生产经验——数据有序



### 生产经验——数据乱序





## 7 Kafka Broker

### Kafka Broker 工作流程

### 生产经验——节点服役和退役





## 8 Kafka 消费者



## 9 安装

- 下载：https://kafka.apache.org/downloads.html

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220729214407826.png)





















## 参考与推荐

- Kafka 核心技术与实战——胡夕（极客时间 专栏）
- 2022版Kafka3.x教程（从入门到调优，深入全面）（B站 尚硅谷）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220726221028995.png)
