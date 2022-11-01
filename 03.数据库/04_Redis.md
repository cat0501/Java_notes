

# 写在前面



[黑马程序员Redis入门到实战教程，全面透析redis底层原理+redis分布式锁+企业解决方案+redis实战](https://www.bilibili.com/video/BV1cr4y1671t?spm_id_from=333.999.0.0)



```bash
175P

# 知识全面

# 与时俱进
# 贴合企业开发 
黑马点评网
学到了直接拿到公司里面用 

# 理论结合实际
理论背熟，没写代码，心里没底，不够自信。一行一行手写代码，要自信。 

# 由浅入深
常用操作烂熟于心
高级篇偏向运维
原理篇深入底层

## 通俗易懂
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220504210855793.png)



# 实用篇





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220309215630084.png)



```bash
# 初识Redis
- 认识NoSQL
- 认识Redis
- 安装Redis

# Redis常见命令
- 5种常见数据结构
- 通用命令
- 不同数据结构的操作命令

# Redis的Javae客户端
- Jedis客户端
- SpringDataRedis客户端
```



 

## 1.  初识Redis

键值型数据库，key-value

### 1.1 认识NoSQL

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220504212803028.png)



```bash
# 结构化&非结构化
# 关联的&无关联的
# SQL查询&非SQL（语法格式不统一） 
# 事务ACID&BASE
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220504212937043.png)



<br> ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220504213715188.png)



### 1.2 认识Redis

Redis诞生于2009年全称是Remote Dictionary Server，远程词典服务器，是一个基于内存的键值型NoSQL数据库。

特征：

- 键值（key-value）型，value支持多种不同数据结构，功能丰富
- 单线程，每个命令具备原子性
  - 6.0只是处理网络请求多线程，核心命令执行依然单线程

- 低延迟，速度快（基于内存、IO多路复用、良好的编码）
  - 内存存储数据相比磁盘（核心）
  - IO多路复用提高了吞吐能力
  - C语言写的
- 支持数据持久化
  - 解决安全性问题
- 支持主从集群、分片集群
  - 从结点备份主节点的数据
  - 数据拆分到不同的结点，水平扩展
- 支持多语言客户端



```bash
8种不同的数据结构，2种消息队列，4个运维操作，Script脚本和事务操作。

包含各个版本的新特性。
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220309224712500.png)



### 1.3 Linux安装

> Redis是基于C语言编写的，因此首先需要安装Redis所需要的gcc依赖：

```sh
yum install -y gcc tcl
```



> Redis安装包上传到虚拟机的任意目录，解压缩

```sh
tar -xzf redis-6.2.6.tar.gz
```



> 进入redis目录，编译

```sh
make && make install
```

<br>

```bash
默认的安装路径是在 `/usr/local/bin`目录下，该目录已经默认配置环境变量，因此可以在任意目录下运行这些命令。

- redis-cli：是redis提供的命令行客户端
- redis-server：是redis的服务端启动脚本
- redis-sentinel：是redis的哨兵启动脚本  
```





### 1.4 三种启动方式

#### 默认启动（不推荐）

> 安装完成后，在任意目录输入`redis-server`命令即可启动Redis。这种启动属于`前台启动`，会阻塞整个会话窗口，窗口关闭或者按下`CTRL + C`则Redis停止。不推荐使用。

```shell
redis-server
```



#### 指定配置启动

> 如果要让Redis以`后台`方式启动，则必须修改Redis配置文件，就在我们之前解压的redis安装包下（`/usr/local/src/redis-6.2.6`），名字叫redis.conf：



我们先将这个配置文件备份一份：

```sh
cp redis.conf redis.conf.bck
```



> 然后修改redis.conf文件中的一些配置：

```properties
# 允许访问的地址，默认是127.0.0.1，会导致只能在本地访问。修改为0.0.0.0则可以在任意IP访问，生产环境不要设置为0.0.0.0
bind 0.0.0.0
# 守护进程，修改为yes后即可后台运行
daemonize yes 
# 密码，设置后访问Redis必须输入密码
requirepass 123456
```



Redis的其它常见配置：

```properties
# 监听的端口
port 6379
# 工作目录，默认是当前目录，也就是运行redis-server时的命令，日志、持久化等文件会保存在这个目录
dir .
# 数据库数量，设置为1，代表只使用1个库，默认有16个库，编号0~15
databases 1
# 设置redis能够使用的最大内存
maxmemory 512mb
# 日志文件，默认为空，不记录日志，可以指定日志文件名
logfile "redis.log"
```



启动Redis：

```sh
# 进入redis安装目录 
cd /usr/local/src/redis-6.2.6
# 启动
redis-server redis.conf
```



停止服务：

```sh
# 利用redis-cli来执行 shutdown 命令，即可停止 Redis 服务，
# 因为之前配置了密码，因此需要通过 -u 来指定密码
redis-cli -u 123321 shutdown
```



#### 开机自启

> 我们也可以通过配置来实现开机自启。



首先，新建一个系统服务文件：

```sh
vi /etc/systemd/system/redis.service
```

内容如下：

```conf
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/bin/redis-server /usr/local/src/redis-6.2.6/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```



然后重载系统服务：

```sh
systemctl daemon-reload
```



现在，我们可以用下面这组命令来操作redis了：

```sh
# 启动
systemctl start redis
# 停止
systemctl stop redis
# 重启
systemctl restart redis
# 查看状态
systemctl status redis
```



执行下面的命令，可以让redis开机自启：

```sh
systemctl enable redis

# Created symlink from /etc/systemd/system/multi-user.target.wants/redis.service to /etc/systemd/system/redis.service.
```



>  启动Redis警告:WARNING overcommit_memory is set to 0 Background save may fail under low memory condition
>
> https://blog.csdn.net/cnwyt/article/details/118995722



### 1.5 客户端

> 安装完成Redis，我们就可以操作Redis，实现数据的CRUD了。这需要用到Redis客户端，包括：



#### 命令行客户端 redis-cli



> Redis安装完成后就自带了命令行客户端：redis-cli，使用方式如下：

```sh
redis-cli [options] [commonds]
```



>其中常见的有：
>
>- `-h 127.0.0.1`：指定要连接的redis节点的IP地址，默认是127.0.0.1
>- `-p 6379`：指定要连接的redis节点的端口，默认是6379
>- `-a 123321`：指定redis的访问密码 
>
>其中的commonds就是Redis的操作命令，例如：
>
>- `ping`：与redis服务端做心跳测试，服务端正常会返回`pong`
>
>不指定commond时，会进入`redis-cli`的交互控制台。



#### 图形化桌面客户端 Github大神编写

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311135943224.png)



Redis默认有16个仓库，编号从0至15。通过配置文件可以设置仓库数量，但是不超过16，并且不能自定义仓库名称。

如果是基于redis-cli连接Redis服务，可以通过select命令来选择数据库：

```sh
# 选择 0号库
select 0
```



#### 编程客户端



## 2. Redis常用命令



### Redis数据结构介绍

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311140514450.png)



Redis为了方便我们学习，将操作不同数据类型的命令也做了分组，在官网（ https://redis.io/commands ）可以查看到不同的命令

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311140842569.png)







### Redis通用命令

所有数据类型都可以使用的指令，常见的有：

- KEYS：查看符合模板的所有key
- DEL：删除一个指定的key
- EXISTS：判断key是否存在
- EXPIRE：给一个key设置有效期，有效期到期时该key会被自动删除
- TTL：查看一个KEY的剩余有效期

通过help [command] 可以查看一个命令的具体用法，例如：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311141206482.png)



### String类型

Redis中最简单的存储类型。

不过根据字符串的格式不同，又可以分为3类：

- string：普通字符串
- int：整数类型，可以做自增、自减操作
- float：浮点类型，可以做自增、自减操作

不管是哪种格式，底层都是字节数组形式存储，只不过是编码方式不同。字符串类型的最大空间不能超过512m。



| 命令        | 说明                                                         | 注    |
| ----------- | ------------------------------------------------------------ | ----- |
| SET         | 添加或者修改已经存在的一个String类型的键值对                 | 1     |
| GET         | 根据key获取String类型的value                                 | 1     |
| MSET        | 批量添加多个String类型的键值对                               | 1     |
| MGET        | 根据多个key获取多个String类型的value                         | 1     |
| INCR        | 让一个整型的key自增1                                         | 2     |
| INCRBY      | 让一个整型的key自增并指定步长，例如：incrby num 2 让num值自增2 | 2     |
| INCRBYFLOAT | 让一个浮点类型的数字自增并指定步长                           | 2     |
| SETNX       | 添加一个String类型的键值对，前提是这个key不存在，否则不执行  | 3重要 |
| SETEX       | 添加一个String类型的键值对，并且指定有效期                   | 3     |

#### key的层级格式

例如，需要存储用户、商品信息到redis，有一个用户id是1，有一个商品id恰好也是1

##### key

Redis的key允许有多个单词形成层级结构，多个单词之间用':'隔开，格式如下：

```bash
# 项目名:业务名:类型:id

user相关的key：heima:user:1
product相关的key：heima:product:1
```

##### value

如果Value是一个Java对象，例如一个User对象，则可以将对象序列化为JSON字符串后存储

| **KEY**         | **VALUE**                                 |
| --------------- | ----------------------------------------- |
| heima:user:1    | {"id":1, "name": "Jack", "age": 21}       |
| heima:product:1 | {"id":1, "name": "小米11", "price": 4999} |

 

### Hash类型

Hash类型，也叫散列，其value是一个无序字典，类似于Java中的HashMap结构。

String结构是将对象序列化为JSON字符串后存储，当需要修改对象某个字段时很不方便。

Hash结构可以将对象中的每个字段独立存储，可以针对单个字段做CRUD

| **KEY**      | value | value |
| ------------ | ----- | ----- |
|              | field | value |
| heima:user:1 | name  | Jack  |
|              | age   | 21    |
| heima:user:2 | name  | Rose  |
|              | age   | 18    |

常见命令： 

| 命令                 | 说明                               | 注   |
| -------------------- | ---------------------------------- | ---- |
| HSET key field value | 添加或者修改hash类型key的field的值 |      |
| HGET key field       | 获取一个hash类型key的field的值     |      |
| HMSET                | 批量添加多个hash类型key的field的值 |      |
|                      |                                    |      |
|                      |                                    |      |
|                      |                                    |      |
|                      |                                    |      |
|                      |                                    |      |
|                      |                                    |      |





### List类型（存储有序数据）

Redis中的List类型与Java中的LinkedList类似，可以看做是一个双向链表结构。既可以支持正向检索和也可以支持反向检索。

特征也与LinkedList类似：

- 有序
- 元素可以重复
- 插入和删除快
- 查询速度一般

**常用来存储一个有序数据，例如：朋友圈点赞列表，评论列表等。**

 

| 命令                   | 说明                                                         | 注   |
| ---------------------- | ------------------------------------------------------------ | ---- |
| LPUSH key  element ... | 向列表左侧插入一个或多个元素                                 |      |
| LPOP key               | 移除并返回列表左侧的第一个元素，没有则返回nil                |      |
| RPUSH key  element ... | 向列表右侧插入一个或多个元素                                 |      |
| RPOP key               | 移除并返回列表右侧的第一个元素                               |      |
| LRANGE key star end    | 返回一段角标范围内的所有元素                                 |      |
| BLPOP和BRPOP           | 与LPOP和RPOP类似，只不过在没有元素时等待指定时间，而不是直接返回nil |      |



![image-20220504223741051](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220504223741051.png)



```bash
# 如何利用List结构模拟一个栈?
入口和出口在同一边

# 如何利用List结构模拟一个队列?
入口和出口在不同边

# 如何利用List结构模拟一个阻塞队列?
- 入口和出口在不同边
- 出队时采用BLPOP或BRPOP
```





### Set类型

Redis的Set结构与Java中的HashSet类似，可以看做是一个value为null的HashMap。

因为也是一个hash表，因此具备与HashSet类似的特征：

- 无序
- 元素不可重复
- 查找快
- 支持交集、并集、差集等功能



常见命令：

| 命令                 | 说明                        | 注   |
| -------------------- | --------------------------- | ---- |
| SADD key member...   | 向set中添加一个或多个元素   | 1    |
| SREM key member...   | 移除set中的指定元素         | 1    |
| SCARD key            | 返回set中元素的个数         | 1    |
| SISMEMBER key member | 判断一个元素是否存在于set中 | 1    |
| SMEMBERS             | 获取set中的所有元素         | 1    |
| SINTER key1 key2 ... | 求key1与key2的交集          | 2    |
| SDIFF key1 key2 ...  | 差集                        | 2    |
| SUNION key1 key2 ... | 并集                        | 2    |



练习

```bash
# 将下列数据用Redis的Set集合来存储：
张三的好友有：李四、王五、赵六
李四的好友有：王五、麻子、二狗

# 利用Set的命令实现下列功能： 
计算张三的好友有几人
计算张三和李四有哪些共同好友
查询哪些人是张三的好友却不是李四的好友
查询张三和李四的好友总共有哪些人
判断李四是否是张三的好友
判断张三是否是李四的好友
将李四从张三的好友列表中移除
```





### SortedSet类型（排行榜）

Redis的SortedSet是一个可排序的set集合，与Java中的TreeSet有些类似，但底层数据结构却差别很大。

SortedSet中的每一个元素都带有一个score属性，可以基于score属性对元素排序，底层的实现是一个跳表（SkipList）加 hash表。

SortedSet具备下列特性：

- 可排序
- 元素不重复
- 查询速度快

因为SortedSet的可排序特性，经常被用来实现排行榜这样的功能。



常见命令



练习

```bash
将班级的下列学生得分存入Redis的SortedSet中：
Jack 85, Lucy 89, Rose 82, Tom 95, Jerry 78, Amy 92, Miles 76
并实现下列功能：

删除Tom同学
获取Amy同学的分数
获取Rose同学的排名
查询80分以下有几个学生
给Amy同学加2分
查出成绩前3名的同学
查出成绩80分以下的所有同学
```







## 3. Redis客户端

在Redis官网中提供了各种语言的客户端，地址：https://redis.io/clients



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311144515781.png)





### 3.1 Jedis

Jedis的官网地址： https://github.com/redis/jedis，我们先来个快速入门

```bash
# 使用
引入依赖；
创建Jedis对象，建立连接；
使用Jedis，方法名与Redis命令一致；
释放资源；
```

1

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.7.0</version>
</dependency>
```

2

3

4

```java
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes=SpringApplicationTest.class)
public class SpringApplicationTest {
    private Jedis jedis;

    @Test
    @Before  // Junit4,在每个测试方法之前执行。注解在非静态方法上。
    // @BeforeEach  Junit5
    public void testSetUp() {
        jedis = new Jedis("39.101.189.62", 6379);
        jedis.auth("123456");
        jedis.select(2);
    }

    @Test
    public void testString() {
        System.out.println("-------------插入数据---------------");
        String result = jedis.set("name", "李四");
        System.out.println("result: " + result);
        
        System.out.println("-------------根据key获取数据---------------");
        String name = jedis.get("name");
        System.out.println("name: " + name);
    }

    @Test
    @After  // Junit4,在每个测试方法之后执行。注解在非静态方法上。
    // @AfterEach  Junit5
    public void testDown() {
        // 释放资源
        if (jedis != null){
            jedis.close();
        }
    }

}
```

#### Jedis连接池

> Jedis本身是线程不安全的，并且频繁的创建和销毁连接会有性能损耗，因此我们**推荐大家使用Jedis连接池代替Jedis的直连方式**。

```java
@Component
public class JedisConnectionFactory {

    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接
        jedisPoolConfig.setMaxTotal(8);
        // 最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        // 最小空闲连接
        jedisPoolConfig.setMinIdle(4);
        // 设置最长等待时间， ms
        jedisPoolConfig.setMaxWaitMillis(200);
        jedisPool = new JedisPool(jedisPoolConfig, "39.101.189.62", 6379,
                1000, "123456");
    }

    // 获取Jedis对象
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}
```



### 3.2 SpringDataRedis 2.6.0

#### 说明

SpringData是Spring中数据操作的模块，包含对各种数据库的集成

其中对Redis的集成模块就叫做SpringDataRedis，官网地址：https://spring.io/projects/spring-data-redis

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311165524445.png)



SpringDataRedis中提供了RedisTemplate工具类，其中封装了各种对Redis的操作。

并且将不同数据类型的操作API封装到了不同的类型中：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311165737684.png)



> 填坑之路！Srpingboot+Redis redisTemplate 做测试时候报空指针异常（注入为NULL）
>
> stringRedisTemplate报空指针错误,原因是注入时候stringRedisTemplate就是null
>
> 查了一下后面发现是容器没有启动,单纯的测试是无法注入的.要在测试类上加上
>
> ```java
> @RunWith(SpringRunner.class)
> @SpringBootTest
> ```

> 这样子启动的话是启动spring容器来执行测试。加上后就会启动Spring ,这算是一个小小的坑。
>
> 具体参考：https://blog.csdn.net/weixin_42236404/article/details/88969718



#### 使用

```bash
SpringDataRedis的使用步骤：

- 引入spring-boot-starter-data-redis依赖
- 在application.yml配置Redis信息
- 注入RedisTemplate使用
```



1 引入依赖

```xml
<artifactId>Redis_02_SpringDataRedis</artifactId>

    <!--Redis依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!--连接池依赖-->
  	<!--底层基于实现连接池技术-->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
```

2 配置

```yaml
spring:
  redis:
    host: 39.101.189.62
    port: 6379
    password: 123456
    lettuce:
      pool:
        max-active: 8 # 最大连接
        max-idle: 8 # 最大空闲连接
        min-idle: 0 # 最小空闲连接
        max-wait: 100 # 连接等待时间
```



3 注入RedisTemplate

4 编写测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        // 插入一条string类型数据
        redisTemplate.opsForValue().set("name", "李四");
        // 读取一条string类型数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
    }
}
```



#### 序列化方式

RedisTemplate可以接收任意Object作为值写入Redis，只不过**写入前会把Object序列化为字节形式**，**默认是采用**

**JDK序列化**，得到的结果是这样的：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220311233856781.png)

> 缺点：
>
> - 可读性差
>
> - 内存占用较大



##### 使用JSON序列化器

我们可以自定义RedisTemplate的序列化方式，代码如下：

- key和 hashKey采用 string序列化
- value和 hashValue采用 JSON序列化

```java
@Configuration
public class JsonSerializable {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        // 创建Template
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // key和 hashKey采用 string序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value和 hashValue采用 JSON序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
```



结果：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220312095453601.png)



> 尽管JSON的序列化方式可以满足我们的需求，但依然存在一些问题，如图：
>
> 为了在反序列化时知道对象的类型，JSON序列化器会将类的class类型写入json结果中，存入Redis，会带来额外的内存开销。



##### String序列化器

为了节省内存空间，我们并不会使用JSON序列化器来处理value。

而是统一使用String序列化器，要求只能存储String类型的key和value。当需要存储Java对象时，手动完成对象的序列化和反序列化。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220312181152783.png)

> Spring默认提供了一个StringRedisTemplate类，它的key和value的序列化方式默认就是String方式。省去了我们自定义RedisTemplate的过程：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringRedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // SpringMVC 的Json处理工具
    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testStringRedisTemplate() throws JsonProcessingException {
        // 准备对象
        User user = new User("admin","123456");
        // 手动序列化
        String valueAsString = mapper.writeValueAsString(user);
        // 写入一条数据到Redis
        stringRedisTemplate.opsForValue().set("user",valueAsString);

        // 读取数据
        String userString = stringRedisTemplate.opsForValue().get("user");
        // 反序列化
        User userValue = mapper.readValue(userString, User.class);
        System.out.println(userValue);
    }

}
```



```bash
# RedisTemplate的两种序列化实践方案：
方案一：
自定义RedisTemplate
修改RedisTemplate的序列化器为GenericJackson2JsonRedisSerializer

方案二：
使用StringRedisTemplate
写入Redis时，手动把对象序列化为JSON
读取Redis时，手动把读取到的JSON反序列化为对象
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220312184622825.png)





## 4. Redis使用场景（企业实战）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220312194539984.png)



> 代码地址：https://gitee.com/huyi612/hm-dianping
>
> 仓库拉下来的代码，把处理订单的（VoucherOrderServiceImpl） 里面那两个死循环的方法注释掉，再启动，然后等后边讲到了再打开就好了
>
> config包下面的 RedissonConfig里面的redis地址也要换一下



### 4.1 短信登陆（共享session）

#### （1）导入黑马点评项目

项目架构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220312194846174.png)

```bash
# 导入SQL文件：hmdp.sql
tb_user：用户表
tb_user_info：用户详情表
tb_shop：商户信息表
tb_shop_type：商户类型表
tb_blog：用户日记表（达人探店日记）
tb_follow：用户关注表
tb_voucher：优惠券表
tb_voucher_order：优惠券的订单表

# 后端项目 hm-dianping
https://gitee.com/huyi612/hm-dianping
在浏览器访问：http://localhost:8081/shop-type/list ，如果可以看到数据则证明运行没有问题

# 前端项目
nginx-1.18.0
直接下载并解压到任意位置，把nginx的html目录下的 hmdp文件拷贝到你自己的nginx的html目录下即可。
总之：把html文件和nginx.conf替换掉就行了。
注意，如果brew方式安装，conf配置文件需要修改。
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313085826324.png)



#### （2）基于Session实现登陆

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313090352953.png)



##### 发送短信验证码

```java
@PostMapping("code")
public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
    // 发送短信验证码并保存验证码
    return userService.sendCode(phone, session);
}
```



```java
public Result sendCode(String phone, HttpSession session) {
    // 1.校验手机号
    if (RegexUtils.isPhoneInvalid(phone)) {
        // 2.如果不符合，返回错误信息
        return Result.fail("手机号格式错误！");
    }
    // 3.符合，生成验证码
    String code = RandomUtil.randomNumbers(6);

    // 4.保存验证码到 session
    session.setAttribute("code", code);
  	// 4.保存验证码到 redis
    //stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

    // 5.发送验证码
    log.debug("发送短信验证码成功，验证码：{}", code);
    // 返回ok
    return Result.ok();
}
```





##### 短信验证码登陆

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313150806987.png)



```java
/**
 * 登录功能
 * @param loginForm 登录参数：包含手机号、验证码；或者手机号、密码
 */
@PostMapping("/login")
public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
    // 实现登录功能
    return userService.login(loginForm, session);
}
```



```java
public Result login(LoginFormDTO loginForm, HttpSession session) {
    // 1.校验手机号
    String phone = loginForm.getPhone();
    if (RegexUtils.isPhoneInvalid(phone)) {
        // 2.如果不符合，返回错误信息
        return Result.fail("手机号格式错误！");
    }
    // 3.从redis获取验证码并校验
    String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
    String code = loginForm.getCode();
    if (cacheCode == null || !cacheCode.equals(code)) {
        // 不一致，报错
        return Result.fail("验证码错误");
    }

    // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
    User user = query().eq("phone", phone).one();

    // 5.判断用户是否存在
    if (user == null) {
        // 6.不存在，创建新用户并保存
        user = createUserWithPhone(phone);
    }

    // 7.保存用户信息到 redis中
    // 7.1.随机生成token，作为登录令牌
    String token = UUID.randomUUID().toString(true);
    // 7.2.将User对象转为HashMap存储
    UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
    Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
            CopyOptions.create()
                    .setIgnoreNullValue(true)
                    .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
    // 7.3.存储
    String tokenKey = LOGIN_USER_KEY + token;
    stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
    // 7.4.设置token有效期
    stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

    // 8.返回token
    return Result.ok(token);
}

private User createUserWithPhone(String phone) {
    // 1.创建用户
    User user = new User();
    user.setPhone(phone);
    user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
    // 2.保存用户
    save(user);
    return user;
}
```





##### 登录验证功能

> 设置一个拦截器

```java
package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor2 implements HandlerInterceptor {
    /**
     * @description 前置拦截
     * @updateTime 2022/3/13 上午9:45
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1,获取Session
        HttpSession session = request.getSession();
        // 2,获取Session中的用户
        Object user = session.getAttribute("user");
        // 3,判断用户是否存在
        if (user == null){
            // 4,不存在，拦截，返回401状态码
            response.setStatus(401);
            return false;
        }
        // 5,存在，保存用户信息到ThreadLocal  (ThreadLocal的实现原理可以自己下来学习下)
        UserHolder.saveUser((UserDTO) user);
        // 6,放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserHolder.removeUser();
    }
}
```



> 配置拦截器（使其生效）

```java
import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                ).order(1);
        // token刷新的拦截器
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
```





> 验证通过后返回用户

```java
@GetMapping("/me")
public Result me(){
    // 获取当前登录的用户并返回
    UserDTO user = UserHolder.getUser();
    return Result.ok(user);
}
```



#### （3）集群的Session共享问题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313151624030.png)



#### （4）基于Redis实现共享session登陆

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313153813753.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313154045384.png)



> 基于Redis代替session



```java
// 4.保存验证码到 Redis
stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
```





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313174037454.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313174333837.png)



```bash
# Redis代替session需要考虑的问题：

选择合适的数据结构
选择合适的key
选择合适的存储粒度（并未存储用户完整信息，而是部分信息UserDTO）
```



##### 登陆拦截器的优化（刷新 + 拦截）

> 原有拦截器基础上加一个新的拦截器：因为目前来说，如果用户一直访问的都是不需要拦截的请求，那么30分钟后用户的状态还是消失了。

> 新拦截器做刷新Token的动作，也即token续期。原拦截器才是实现拦截功能。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313203331552.png)



```java
public class LoginInterceptor implements HandlerInterceptor {

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.判断是否需要拦截（ThreadLocal中是否有用户）
        if (UserHolder.getUser() == null) {
            // 没有，需要拦截，设置状态码
            response.setStatus(401);
            // 拦截
            return false;
        }
        // 有用户，则放行
        return true;
    }
}
```



```java
package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_KEY;
import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            return true;
        }
        // 2.基于TOKEN获取redis中的用户
        String key  = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        // 3.判断用户是否存在
        if (userMap.isEmpty()) {
            return true;
        }
        // 5.将查询到的hash数据转为UserDTO
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        // 6.存在，保存用户信息到 ThreadLocal
        UserHolder.saveUser(userDTO);
        // 7.刷新token有效期
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 8.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserHolder.removeUser();
    }
}
```



```java
package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                ).order(1);
        // token刷新的拦截器
        //设置order为0，优先级更高，先执行
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
```





<hr>

### 4.2 商品查询缓存（缓存使用技巧、缓存雪崩和穿透问题）

> 借助商户查询业务，分析缓存使用的技巧，还有常见问题的解决方案。

##### 什么是缓存

> 缓存就是数据交换的缓冲区（称作Cache [ kæʃ ] ），是存贮数据的临时地方，一般读写性能较高。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313215032924.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313215101313.png)

> 中小型企业，数据量不大的情况下，也可以不使用缓存。



##### 添加Redis缓存

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313215158070.png)

> 未命中，查询数据库后需要写缓存到Redis



```java
public Result queryById2(Long id) {
    // 1.从Redis查询店铺缓存
    String key = CACHE_SHOP_KEY + id;
    String shopJson = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isNotBlank(shopJson)){
        // 3.存在，直接返回
        Shop shop = JSONUtil.toBean(shopJson, Shop.class);
        return Result.ok(shop);
    }
    // 4.不存在，根据id查询数据库
    Shop shop = getById(id);

    // 5.不存在，返回错误
    if (shop == null){
        return Result.fail("店铺不存在！");
    }
    stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop));
    // 6.存在，存入Redis
    // 7.返回
    return Result.ok(shop);
}
```



> 应用：给店铺类型查询业务添加缓存（店铺类型在首页和其它多个页面都会用到）,流程如上图。



##### 缓存更新策略

> 数据一致性问题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313225122274.png)

> 业务场景：
> 低一致性需求：使用内存淘汰机制。例如店铺类型的查询缓存
> 高一致性需求：主动更新，并以超时剔除作为兜底方案。例如店铺详情查询的缓存



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313215646728.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313225714221.png)



> 存在的线程安全问题（发生频率其实较高：查写缓存都很快，更新数据库较慢）

###### 先删除缓存，后操作数据库

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313230053602.png)

###### 先操作数据库，再删除缓存（胜出）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313230613870.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313230905524.png)



> 案例：给查询商铺的缓存添加超时剔除和主动更新的策略
>
> 修改ShopController中的业务逻辑，满足下面的需求：
> - 根据id查询店铺时，如果缓存未命中，则查询数据库，将数据库结果写入缓存，并设置超时时间
> - 根据id修改店铺时，先修改数据库，再删除缓存





<hr>

##### 缓存穿透

> 缓存穿透是指客户端请求的数据在缓存中和数据库中都不存在，这样缓存永远不会生效，这些请求都会打到数据库。
> 常见的解决方案有2种：
>
> - 缓存空对象
> - 布隆过滤

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314095851509.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314100138014.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314100237265.png)



##### 缓存雪崩

> 缓存雪崩是指在同一时段大量的缓存key同时失效或者Redis服务宕机，导致大量请求到达数据库，带来巨大压力。

> 解决方案：
>
> - 给不同的Key的TTL添加随机值
> - 利用Redis集群提高服务的可用性
> - 给缓存业务添加降级限流策略
> - 给业务添加多级缓存

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220313214413748.png)



##### 缓存击穿（热点Key问题）

> 缓存击穿问题也叫热点Key问题，就是一个被高并发访问并且缓存重建业务较复杂的key突然失效了，无数的请求访问会在瞬间给数据库带来巨大的冲击。

> 常见的解决方案有两种：
>
> - 互斥锁
> - 逻辑过期



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314125112627.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314101111444.png)



> 案例：基于互斥锁方式解决缓存击穿问题
> 需求：修改根据id查询商铺的业务，基于互斥锁方式来解决缓存击穿问题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314125635642.png)



```java
public <R, ID> R queryWithMutex(
        String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
    String key = keyPrefix + id;
    // 1.从redis查询商铺缓存
    String shopJson = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isNotBlank(shopJson)) {
        // 3.存在，直接返回
        return JSONUtil.toBean(shopJson, type);
    }
    // 判断命中的是否是空值
    if (shopJson != null) {
        // 返回一个错误信息
        return null;
    }

    // 4.实现缓存重建
    // 4.1.获取互斥锁
    String lockKey = LOCK_SHOP_KEY + id;
    R r = null;
    try {
        boolean isLock = tryLock(lockKey);
        // 4.2.判断是否获取成功
        if (!isLock) {
            // 4.3.获取锁失败，休眠并重试
            Thread.sleep(50);
            return queryWithMutex(keyPrefix, id, type, dbFallback, time, unit);
        }
        // 4.4.获取锁成功，根据id查询数据库
        r = dbFallback.apply(id);
        // 5.不存在，返回错误
        if (r == null) {
            // 将空值写入redis
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        // 6.存在，写入redis
        this.set(key, r, time, unit);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }finally {
        // 7.释放锁
        unlock(lockKey);
    }
    // 8.返回
    return r;
}
```



> 基于逻辑过期方式解决缓存击穿问题
> 需求：修改根据id查询商铺的业务，基于逻辑过期方式来解决缓存击穿问题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314125728923.png)



```JAVA
public <R, ID> R queryWithLogicalExpire(
        String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
    String key = keyPrefix + id;
    // 1.从redis查询商铺缓存
    String json = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isBlank(json)) {
        // 3.存在，直接返回
        return null;
    }
    // 4.命中，需要先把json反序列化为对象
    RedisData redisData = JSONUtil.toBean(json, RedisData.class);
    R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
    LocalDateTime expireTime = redisData.getExpireTime();
    // 5.判断是否过期
    if(expireTime.isAfter(LocalDateTime.now())) {
        // 5.1.未过期，直接返回店铺信息
        return r;
    }
    // 5.2.已过期，需要缓存重建
    // 6.缓存重建
    // 6.1.获取互斥锁
    String lockKey = LOCK_SHOP_KEY + id;
    boolean isLock = tryLock(lockKey);
    // 6.2.判断是否获取锁成功
    if (isLock){
        // 6.3.成功，开启独立线程，实现缓存重建
        CACHE_REBUILD_EXECUTOR.submit(() -> {
            try {
                // 查询数据库
                R newR = dbFallback.apply(id);
                // 重建缓存
                this.setWithLogicalExpire(key, newR, time, unit);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                // 释放锁
                unlock(lockKey);
            }
        });
    }
    // 6.4.返回过期的商铺信息
    return r;
}
```



> 向Redis写入数据，并设置逻辑过期时间。

```java
public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
    // 设置逻辑过期
    RedisData redisData = new RedisData();
    redisData.setData(value);
    redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
    // 写入Redis
    stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
}
```



```java
public <R, ID> R queryWithLogicalExpire(
        String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
    String key = keyPrefix + id;
    // 1.从redis查询商铺缓存
    String json = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isBlank(json)) {
        // 3.存在，直接返回
        return null;
    }
    // 4.命中，需要先把json反序列化为对象
    RedisData redisData = JSONUtil.toBean(json, RedisData.class);
    R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
    LocalDateTime expireTime = redisData.getExpireTime();
    // 5.判断是否过期
    if(expireTime.isAfter(LocalDateTime.now())) {
        // 5.1.未过期，直接返回店铺信息
        return r;
    }
    // 5.2.已过期，需要缓存重建
    // 6.缓存重建
    // 6.1.获取互斥锁
    String lockKey = LOCK_SHOP_KEY + id;
    boolean isLock = tryLock(lockKey);
    // 6.2.判断是否获取锁成功
    if (isLock){
        // 6.3.成功，开启独立线程，实现缓存重建
        CACHE_REBUILD_EXECUTOR.submit(() -> {
            try {
                // 查询数据库
                R newR = dbFallback.apply(id);
                // 重建缓存
                this.setWithLogicalExpire(key, newR, time, unit);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                // 释放锁
                unlock(lockKey);
            }
        });
    }
    // 6.4.返回过期的商铺信息
    return r;
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314213922711.png)



##### 缓存工具封装

> 基于StringRedisTemplate封装一个缓存工具类，满足下列需求：
>
> 方法1：将任意Java对象序列化为json并存储在string类型的key中，并且可以设置TTL过期时间
> 方法2：将任意Java对象序列化为json并存储在string类型的key中，并且可以设置逻辑过期时间，用于处理缓存击穿问题
> 方法3：根据指定的key查询缓存，并反序列化为指定类型，利用缓存空值的方式解决缓存穿透问题
> 方法4：根据指定的key查询缓存，并反序列化为指定类型，需要利用逻辑过期解决缓存击穿问题



```java
private final StringRedisTemplate stringRedisTemplate;

// 线程池
private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

/**
 * @description 基于构造函数进行注入
 * @author Lemonade
 * @param: stringRedisTemplate
 * @updateTime 2022/3/14 下午10:01
 */
public CacheClient(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
}

// ----------------------缓存工具封装练习----------------------------
public void set(String key, Object value, Long time, TimeUnit unit) {
    // JSONUtil.toJsonStr(value)序列化为Json字符串
    stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
}

public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
    // 设置逻辑过期
    RedisData redisData = new RedisData();
    redisData.setData(value);
    redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
    // 写入Redis
    stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
}

// 缓存穿透工具类
public <R,ID> R queryWithPassThrough(
        String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit){
    String key = keyPrefix + id;
    // 1.从redis查询商铺缓存
    String json = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isNotBlank(json)) {
        // 3.存在，直接返回
        return JSONUtil.toBean(json, type);
    }
    // 判断命中的是否是空值
    if (json != null) {
        // 返回一个错误信息
        return null;
    }

    // 4.不存在，根据id查询数据库
    R r = dbFallback.apply(id);
    // 5.不存在，返回错误
    if (r == null) {
        // 将空值写入redis
        stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
        // 返回错误信息
        return null;
    }
    // 6.存在，写入redis
    this.set(key, r, time, unit);
    return r;
}

// 缓存击穿工具类
public <R, ID> R queryWithLogicalExpire(
        String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
    String key = keyPrefix + id;
    // 1.从redis查询商铺缓存
    String json = stringRedisTemplate.opsForValue().get(key);
    // 2.判断是否存在
    if (StrUtil.isBlank(json)) {
        // 3.存在，直接返回
        return null;
    }
    // 4.命中，需要先把json反序列化为对象
    RedisData redisData = JSONUtil.toBean(json, RedisData.class);
    R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
    LocalDateTime expireTime = redisData.getExpireTime();
    // 5.判断是否过期
    if(expireTime.isAfter(LocalDateTime.now())) {
        // 5.1.未过期，直接返回店铺信息
        return r;
    }
    // 5.2.已过期，需要缓存重建
    // 6.缓存重建
    // 6.1.获取互斥锁
    String lockKey = LOCK_SHOP_KEY + id;
    boolean isLock = tryLock(lockKey);
    // 6.2.判断是否获取锁成功
    if (isLock){
        // 6.3.成功，开启独立线程，实现缓存重建
        CACHE_REBUILD_EXECUTOR.submit(() -> {
            try {
                // 查询数据库
                R newR = dbFallback.apply(id);
                // 重建缓存
                this.setWithLogicalExpire(key, newR, time, unit);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                // 释放锁
                unlock(lockKey);
            }
        });
    }
    // 6.4.返回过期的商铺信息
    return r;
}
// ----------------------缓存工具封装练习----------------------------
```



### 4.3 优惠券秒杀（含金量高）（计数器、lua脚本、分布式锁、3种消息队列）

#### 全局唯一ID

> 全局ID生成器，是一种在分布式系统下用来生成全局唯一ID的工具，一般要满足下列特性：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314224112520.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314224258442.png)



> 为了增加ID的安全性，我们可以不直接使用Redis自增的数值，而是拼接一些其它信息：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220314224525784.png)



```java
@Component
public class RedisIdWorker {
    /**
     * 开始时间戳
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;
    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        // 3.拼接并返回
        return timestamp << COUNT_BITS | count;
    }
}
```





> 全局唯一ID生成策略：
> - UUID
> - Redis自增
> - snowflake算法
> - 数据库自增
>
> Redis自增ID策略：
> - 每天一个key，方便统计订单量
> - ID构造是 时间戳 + 计数器



#### 实现优惠券秒杀下单

> 每个店铺都可以发布优惠券，分为平价券和特价券。平价券可以任意购买，而特价券需要秒杀抢购：
>
> 表关系：
> tb_voucher：优惠券的基本信息，优惠金额、使用规则等
> tb_seckill_voucher：优惠券的库存、开始抢购时间，结束抢购时间。特价优惠券才需要填写这些信息

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220315125753002.png)



> 在VoucherController中提供了一个接口，可以添加秒杀优惠券：

```java
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private IVoucherService voucherService;

    /**
     * 新增秒杀券
     * @param voucher 优惠券信息，包含秒杀信息
     * @return 优惠券id
     */
    @PostMapping("seckill")
    public Result addSeckillVoucher(@RequestBody Voucher voucher) {
        voucherService.addSeckillVoucher(voucher);
        return Result.ok(voucher.getId());
    }
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220315130339884.png)



> 下单时需要判断两点：
> - 秒杀是否开始或结束，如果尚未开始或已经结束则无法下单
> - 库存是否充足，不足则无法下单

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220315130710258.png)

```java
public Result seckillVoucher(Long voucherId) {
    // 1.查询优惠券
    SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
    // 2.判断秒杀是否开始
    if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
        // 尚未开始
        return Result.fail("秒杀尚未开始！");
    }
    // 3.判断秒杀是否已经结束
    if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
        // 尚未开始
        return Result.fail("秒杀已经结束！");
    }
    // 4.判断库存是否充足(反向判断)
    if (voucher.getStock() < 1) {
        // 库存不足
        return Result.fail("库存不足！");
    }

    return createVoucherOrder(voucherId);
}
```



> 此处未完成，待补充！P51



#### 超卖问题





#### 一人一单







#### 分布式锁

集群模式下，Synchronized 锁失效了 。Synchronized只能保证单个JVM内部的多个线程之间的互斥，而没有办法让集群环境下的多个JVM进程之间互斥。

要解决这个问题，必须使用分布式锁。

```bash
Synchronized其实就是利用JVM内部的锁监视器来控制线程，JVM内部只有一个锁监视器，所以只有一个线程获取锁，实现线程的互斥。
但是有多个JVM的时候，就有多个锁监视器，就有多个线程获取到锁。
我们必须让多个JVM使用同一个锁监视器，外部的。
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220505191138588.png)



分布式锁：满足分布式系统或集群模式下**多进程可见**并且**互斥**的锁。

<br>

分布式锁的核心是实现多进程之间互斥，而满足这一点的方式有很多，常见的有三种：

|        | **MySQL**                 | **Redis**                | **Zookeeper**                    |
| ------ | ------------------------- | ------------------------ | -------------------------------- |
| 互斥   | 利用mysql本身的互斥锁机制 | 利用setnx这样的互斥命令  | 利用节点的唯一性和有序性实现互斥 |
| 高可用 | 好                        | 好                       | 好                               |
| 高性能 | 一般                      | 好                       | 一般                             |
| 安全性 | 断开连接，自动释放锁      | 利用锁超时时间，到期释放 | 临时节点，断开连接自动释放       |



##### 基于Redis的分布式锁

```bash
# 实现分布式锁时需要实现的两个基本方法：
## 获取锁
互斥：确保只能有一个线程获取锁
添加锁，利用setnx的互斥特性
setnx lock thread1

## 释放锁
手动释放，删除即可
del key

超时释放：获取锁时添加一个超时时间（避免服务宕机引起的死锁）
expire lock 10

保证原子性操作 
set lock thread1 ex 10 nx

非阻塞：尝试一次，成功返回true，失败返回false
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220505193028173.png" style="zoom:50%;" />



基于Redis实现分布式锁初级版本

需求：定义一个类，实现下面接口，利用Redis实现分布式锁功能。





```bash
P61
释放锁时产生阻塞（Jvm垃圾回收full gc），如果阻塞时间足够长，就会触发超时释放锁，
这时其它线程就可以获取锁，开始执行业务，
这时如果线程1阻塞完成，恢复执行，开始释放锁，就把线程2 的锁给释放掉了（因为阻塞前已经判断过了，所以可以释放）
这就发生了误删

判断锁标识和释放是2个动作，之间产生阻塞，
所以，必须保证2个动作的原子性
```

<br>

```bash
# redis事务
可以保证原子性，但是无法保证一致性

推荐使用Redis的Lua脚本
Redis提供了Lua脚本功能，在一个脚本中编写多条Redis命令，确保多条命令执行时的原子性。Lua是一种编程语言，它的基本语法大家可以参考网站：https://www.runoob.com/lua/lua-tutorial.html

```



```bash
# 释放锁的业务流程是这样的：
获取锁中的线程标示
判断是否与指定的标示（当前线程标示）一致
如果一致则释放锁（删除）
如果不一致则什么都不做
如果用Lua脚本来表示则是这样的：


```

需求：基于Lua脚本实现分布式锁的释放锁逻辑

提示：RedisTemplate调用Lua脚本的API如下：





Redisson

Redisson是一个在Redis的基础上实现的Java驻内存数据网格（In-Memory Data Grid）。

它不仅提供了一系列的分布式的Java常用对象，还提供了许多分布式服务，其中就包含了各种分布式锁的实现。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220505203832316.png)



官网地址： https://redisson.orgGitHub

地址： https://github.com/redisson/redisson





#### Redis优化秒杀







#### Redis消息队列实现异步秒杀







### 4.4 达人探店（基于list的点赞列表、基于sortedset的点赞排行榜）





### 4.5 好友关注（基于set集合的关注、取关、共同关注、消息推送）







### 4.6 附近的商户（GeoHash）

### 4.7 用户签到（BitMap数据统计功能）

### 4.8 UV统计（HyperLogLog统计功能）















<hr>

# 高级篇







# 原理篇











































