# 写在前面

键值数据库Redis

```bash
# 学习目标
知道NoSQL与SQL的差别
熟悉Redis的常用5种数据结构
熟悉Redis的常用命令
熟练使用Jedis或SpringDataRedis
```



# 入门



## 初始Redis

### 认识NoSQL







### 认识Redis

Redis诞生于2009年全称是Remote Dictionary Server，远程词典服务器，是一个基于内存的键值型NoSQL数据库。

<br>

特征：

- 键值（key-value）型，value支持多种不同数据结构，功能丰富
- 单线程，每个命令具备原子性
- 低延迟，速度快（基于内存、IO多路复用、良好的编码）。
- 支持数据持久化
- 支持主从集群、分片集群
- 支持多语言客户端





### 安装Redis





## Redis常见命令

### 5种常见数据结构

Redis是一个 key-value 的数据库，key一般是 `String` 类型，不过value的类型多种多样：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411231156458.png)



<br>

Redis为了方便我们学习，将操作不同数据类型的命令也做了分组，在官网（ https://redis.io/commands ）可以查看到不同的命令：





### 通用命令

通用指令是部分数据类型的，都可以使用的指令，常见的有：

- KEYS：查看符合模板的所有key
- DEL：删除一个指定的key
- EXISTS：判断key是否存在
- EXPIRE：给一个key设置有效期，有效期到期时该key会被自动删除
- TTL：查看一个KEY的剩余有效期通过

<br>

help [command] 可以查看一个命令的具体用法，例如：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411231329385.png)



### 不同数据结构的操作命令

### String类型

String类型，也就是字符串类型，是Redis中最简单的存储类型。

其value是字符串，不过根据字符串的格式不同，又可以分为3类：

- string：普通字符串
- int：整数类型，可以做自增、自减操作
- float：浮点类型，可以做自增、自减操作

不管是哪种格式，底层都是**字节数组**形式存储，只不过是编码方式不同。字符串类型的最大空间不能超过512m.



#### 常见命令有

| 命令        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| SET         | 添加或者修改已经存在的一个String类型的键值对                 |
| GET         | 根据key获取String类型的value                                 |
| MSET        | 批量添加多个String类型的键值对                               |
| MGET        | 根据多个key获取多个String类型的value                         |
| INCR        | 让一个整型的key自增1                                         |
| INCRBY      | 让一个整型的key自增并指定步长，例如：incrby num 2 让num值自增2 |
| INCRBYFLOAT | 让一个浮点类型的数字自增并指定步长                           |
| SETNX       | 添加一个String类型的键值对，前提是这个key不存在，否则不执行  |
| SETEX       | 添加一个String类型的键值对，并且指定有效期                   |



#### key 的结构

Redis的key允许有多个单词形成层级结构，多个单词之间用':'隔开，格式如下：



这个格式并非固定，也可以根据自己的需求来删除或添加词条。

例如我们的项目名称叫 heima，有user和product两种不同类型的数据，我们可以这样定义key：

- user相关的key：heima:user:1

- product相关的key：heima:product:1



如果Value是一个Java对象，例如一个User对象，则可以将对象序列化为JSON字符串后存储：

| **KEY**         | **VALUE**                                 |
| --------------- | ----------------------------------------- |
| heima:user:1    | {"id":1, "name": "Jack", "age": 21}       |
| heima:product:1 | {"id":1, "name": "小米11", "price": 4999} |







### Hash类型

Hash类型，也叫散列，其value是一个无序字典，类似于Java中的HashMap结构。



#### Hash类型的常见命令

| 命令                 | 说明                                                         |
| -------------------- | ------------------------------------------------------------ |
| HSET key field value | 添加或者修改hash类型key的field的值                           |
| HGET key field       | 获取一个hash类型key的field的值                               |
| HMSET                | 批量添加多个hash类型key的field的值                           |
| HMGET                | 批量获取多个hash类型key的field的值                           |
| HGETALL              | 获取一个hash类型的key中的所有的field和value                  |
| HKEYS                | 获取一个hash类型的key中的所有的field                         |
| HVALS                | 获取一个hash类型的key中的所有的value                         |
| HINCRBY              | 让一个hash类型key的字段值自增并指定步长                      |
| HSETNX               | 添加一个hash类型的key的field值，前提是这个field不存在，否则不执行 |





### List类型







### Set类型







### SortedSet类型











## Redis的Java客户端

### Jedis客户端







### SpringDataRedis客户端

SpringData是Spring中数据操作的模块，包含对各种数据库的集成，**其中对Redis的集成模块就叫做SpringDataRedis**，官网地址：https://spring.io/projects/spring-data-redis

提供了对不同Redis客户端的整合（Lettuce和Jedis）

提供了RedisTemplate统一API来操作Redis

支持Redis的发布订阅模型

支持Redis哨兵和Redis集群

支持基于Lettuce的响应式编程

支持基于JDK、JSON、字符串、Spring对象的数据序列化及反序列化

支持基于Redis的JDKCollection实现



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411232655714.png)



#### SpringDataRedis快速入门

SpringDataRedis中提供了**RedisTemplate**工具类，其中封装了各种对Redis的操作。并且将不同数据类型的操作API封装到了不同的类型中：

| **API**                         | **返回值类型**  | **说明**              |
| ------------------------------- | --------------- | --------------------- |
| **redisTemplate**.opsForValue() | ValueOperations | 操作String类型数据    |
| **redisTemplate**.opsForHash()  | HashOperations  | 操作Hash类型数据      |
| **redisTemplate**.opsForList()  | ListOperations  | 操作List类型数据      |
| **redisTemplate**.opsForSet()   | SetOperations   | 操作Set类型数据       |
| **redisTemplate**.opsForZSet()  | ZSetOperations  | 操作SortedSet类型数据 |
| **redisTemplate**               |                 | 通用的命令            |



```bash
# SpringDataRedis的使用步骤：
引入spring-boot-starter-data-redis依赖
在application.yml配置Redis信息
注入RedisTemplate
```



#### SpringDataRedis的序列化方式

RedisTemplate可以接收任意Object作为值写入Redis，只不过写入前会把Object序列化为字节形式，**默认是采用JDK序列化**，得到的结果是这样的：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411233305182.png)

​	

缺点：

- 可读性差

- 内存占用较大

<br>



我们可以自定义RedisTemplate的序列化方式，代码如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411233409366.png)



#### StringRedisTemplate

尽管JSON的序列化方式可以满足我们的需求，但依然存在一些问题，如图：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411233450191.png)

为了在反序列化时知道对象的类型，JSON序列化器会将类的class类型写入json结果中，存入Redis，会带来额外的内存开销。



为了节省内存空间，我们并不会使用JSON序列化器来处理value，而是统一使用String序列化器，要求只能存储String类型的key和value。**当需要存储Java对象时，手动完成对象的序列化和反序列化。**

<br>

Spring默认提供了一个 StringRedisTemplate 类，它的key和value的序列化方式默认就是 String 方式。省去了我们自定义RedisTemplate的过程：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411233648985.png)



<br>

```bash
# RedisTemplate的两种序列化实践方案：
方案一：
自定义RedisTemplate
修改RedisTemplate的序列化器为GenericJackson2JsonRedisSerializer

方案二：(采用)
使用StringRedisTemplate
写入Redis时，手动把对象序列化为JSON
读取Redis时，手动把读取到的JSON反序列化为对象
```







# 企业实战



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411233905319.png)



## 01 短信登陆



## 02 商品查询缓存

### 什么是缓存

缓存就是数据交换的缓冲区（称作Cache [ kæʃ ] ），是存贮数据的临时地方，一般读写性能较高。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411234238180.png)

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411234322211.png)



### 添加Redis缓存











### 缓存更新策略

### 缓存穿透

缓存穿透是指客户端请求的数据在缓存中和数据库中都不存在，这样缓存永远不会生效，**这些请求都会打到数据库**。

<br>

常见的解决方案有两种：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411234815212.png)

#### 缓存空对象

优点：实现简单，维护方便

缺点：额外的内存消耗可能造成短期的不一致

#### 布隆过滤

优点：内存占用较少，没有多余key

缺点：实现复杂存在误判可能



### 缓存雪崩

缓存雪崩是指在同一时段大量的缓存key同时失效或者Redis服务宕机，导致大量请求到达数据库，带来巨大压力。

解决方案：

- 给不同的Key的TTL添加随机值
- 利用Redis集群提高服务的可用性
- 给缓存业务添加降级限流策略
- 给业务添加多级缓存



### 缓存击穿（热点Key问题）

缓存击穿问题也叫热点Key问题，就是一个**被高并发访问**并且**缓存重建业务较复杂**的key突然失效了，无数的请求访问会在瞬间给数据库带来巨大的冲击。



常见的解决方案有两种：

互斥锁

逻辑过期

| **解决方案** | **优点**                                 | **缺点**                                 |
| ------------ | ---------------------------------------- | ---------------------------------------- |
| **互斥锁**   | 没有额外的内存消耗、保证一致性、实现简单 | 线程需要等待，性能受影响、可能有死锁风险 |
| **逻辑过期** | 线程无需等待，性能较好                   | 不保证一致性、有额外内存消耗、实现复杂   |





### 缓存工具封装









## 03 优惠券秒杀

### 全局唯一ID

每个店铺都可以发布优惠券：

当用户抢购时，就会生成订单并保存到tb_voucher_order这张表中，而订单表如果使用数据库自增ID就存在一些问题：

- id的规律性太明显

- 受单表数据量的限制





```bash
# 全局唯一ID生成策略：
UUID
Redis自增
snowflake算法
数据库自增

# Redis自增ID策略：
每天一个key，方便统计订单量
ID构造是 时间戳 + 计数器
```





### 实现优惠券秒杀下单







### 超卖问题

超卖问题是**典型的多线程安全问题，**针对这一问题的常见解决方案就是**加锁**：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411235453635.png)



乐观锁的关键是判断之前查询得到的数据是否有被修改过，常见的方式有两种： 







### 一人一单







### 分布式锁

分布式锁：满足分布式系统或集群模式下多进程可见并且互斥的锁。





### Redis优化秒杀





### Redis消息队列实现异步秒杀











## 04 达人探店





## 05 好友关注





## 06 附近的商户





## 07 用户签到





## 08 zUV统计







## 持久化



















