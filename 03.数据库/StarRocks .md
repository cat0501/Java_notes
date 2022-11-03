



# 产品简介

## 是什么

**新一代极速全场景 MPP (Massively Parallel Processing) 数据库**。StarRocks 的愿景是能够让用户的**数据分析变得更加简单和敏捷**。用户无需经过复杂的预处理，就可以用 StarRocks 来支持多种数据分析场景的极速分析。

能很好地支持实时数据分析，并能实现对实时更新数据的高效查询。StarRocks 还支持现代化物化视图，进一步加速查询。

兼容 **MySQL** 协议，支持标准 SQL 语法，易于对接使用，全系统无外部依赖，高可用，易于运维管理。



## 使用场景

- OLAP 多维分析
- 实时数据仓库
- 高并发查询
- 统一分析



## 架构



![system_architecture](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.2-1.png)

### FE（前端节点：管理）

FE 是 StarRocks 的前端节点，负责管理元数据，管理客户端连接，进行查询规划，查询调度等工作。

两种角色：Follower 和 Observer。Follower 会通过类 Paxos 的 BDBJE 协议选主出一个 Leader。三者区别如下：

- Leader
  - 只有 Leader 节点会对元数据进行写操作，Follower 和 Observer 只有读取权限。
  - Leader 从 Follower 中选举。如果 Leader 节点失败，Follower 会发起新一轮选主。
- Follower
  - 只有元数据读取权限，无写入权限。
- Observer
  - 主要用于扩展集群的查询并发能力，可选部署。
  - 不参与选主，不会增加集群选主压力。



### BE（后端节点：数据存储、SQL执行）

BE 是 StarRocks 的后端节点，负责数据存储、SQL执行等工作。







## 特性







# 快速开始

## 部署

### 前提

环境要求如下：

|          |                                                              |                                                              |
| :------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| 分类     | 描述                                                         | 说明                                                         |
| 硬件要求 | 集群至少拥有两台物理或虚拟节点。BE 节点 CPU 需支持 AVX2 指令集。各节点间需要通过万兆网卡及万兆交换机连接。 | FE 节点建议配置 8 核 或以上 CPU，16GB 或以上内存。BE 节点建议配置 16 核 或以上 CPU，64GB 或以上内存。通过运行 `cat /proc/cpuinfo |grep avx2` 命令查看节点 CPU 支持的指令集，若有结果返回则表明 CPU 支持 AVX2 指令集。 |
| 操作系统 | 所有节点操作系统需为 CentOS（7 或以上）。                    |                                                              |
| 软件要求 | 所有节点需安装 Java Development Kit（1.8 或以上，推荐使用1.8）。客户端节点需安装 MySQL 客户端（5.5 或以上）。 |                                                              |
| 系统环境 | 集群时钟需保持同步。用户需要有设置 `ulimit -n` 权限。        |                                                              |





### 规划

| FE          | BE                                    |
| :---------- | :------------------------------------ |
| 10.11.14.15 | 10.11.14.13、10.11.14.15、10.11.14.16 |





### 部署 FE 节点





### 部署 BE 节点

#### （1）下载并解压





#### （2）配置 BE 节点

```bash
进入 StarRocks-x.x.x/be 路径。
```



#### （3）创建数据路径





#### （4）添加 BE 节点

```sql
ALTER SYSTEM ADD BACKEND "10.11.14.15:9050";
```



#### （5）启动 BE 节点





#### （6）确认 BE 启动成功

```sql
SHOW PROC '/backends'\G

```



> 当 **`Alive`** 为 **`true`** 时，当前 **BE** 节点正常接入集群。
>
> 如果 BE 节点没有正常接入集群，可以通过查看 log/be.WARNING 日志文件排查问题。



#### （7）停止 BE 节点

```bash
./bin/stop_be.sh --daemon
```







## 建表

## 导入和查询数据







# 数据导入

## 导入总览

| 导入方式           | 协议  | 业务场景                                                     | 数据量（单作业）     | 数据源                                      | 数据格式      | 同步模式 |
| :----------------- | :---- | :----------------------------------------------------------- | :------------------- | :------------------------------------------ | :------------ | :------- |
| Stream Load        | HTTP  | 通过 HTTP 协议导入本地文件、或通过程序导入数据流。           | 10 GB 以内           | 本地文件流式数据                            | CSVJSON       | 同步     |
| Broker Load        | MySQL | 从 HDFS 或外部云存储系统导入数据。                           | 数十到数百 GB        | HDFSAmazon S3Google GCS阿里云 OSS腾讯云 COS | CSVParquetORC | 异步     |
| Routine Load       | MySQL | 从 Apache Kafka® 实时地导入数据流。                          | 微批导入 MB 到 GB 级 | Kafka                                       | CSVJSON       | 异步     |
| Spark Load         | MySQL | 通过 Apache Spark™ 集群初次从 HDFS 或 Hive 迁移导入大量数据。需要做全局数据字典来精确去重。 | 数十 GB 到 TB级别    | HDFSHive                                    | CSVParquet    | 异步     |
| INSERT INTO SELECT | MySQL | 外表导入。StarRocks 数据表之间的数据导入。                   | 跟内存相关           | StarRocks 表外部表                          | StarRocks 表  | 同步     |
| INSERT INTO VALUES | MySQL | 单条批量小数据量插入。通过 JDBC 等接口导入。                 | 简单测试用           | 程序ETL 工具                                | SQL           | 同步     |





## 从 HDFS 或外部云存储系统导入数据

基于 `MySQL` 协议的 `Broker Load` 导入方式，可从 HDFS 或外部云存储系统导入几十到数百 GB 的数据量。

### 从 HDFS 导入

- 前提：StarRocks 集群中已部署 Broker。

```sh
# 查看
SHOW PROC "/brokers";

# broker 添加
ALTER SYSTEM ADD BROKER mybroker "10.11.14.16:8000";

# broker 删除
ALTER SYSTEM DROP BROKER mybroker "10.11.14.16:8000";
```



![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20221102152048621.png)



- 库表信息

```sql
mysql -h 10.11.14.15 -P9030 -uroot

MySQL [example_db]> desc table4;
+-------+----------------+------+-------+---------+-------+
| Field | Type           | Null | Key   | Default | Extra |
+-------+----------------+------+-------+---------+-------+
| id    | INT            | No   | true  | NULL    |       |
| city  | VARCHAR(65533) | Yes  | false |         |       |
+-------+----------------+------+-------+---------+-------+
2 rows in set (0.01 sec)

MySQL [example_db]> 
```



- 语法

```sql
LOAD LABEL example_db.student12
(
    DATA INFILE("hdfs://10.11.14.15:8020/user/test/file2.csv")
    INTO TABLE table4
    COLUMNS TERMINATED BY ","
    (id, city)
)
WITH BROKER "mybroker"
(
    "username" = "admin",
    "password" = "admin"
)
PROPERTIES
(
    "timeout" = "3600"
);
```

- 说明

  - HDFS 集群  `/user/test/`   路径下的 CSV 文件 `file2.csv`

  ```sh
  [root@cnbjzxsshnn01 opt]# hdfs dfs -ls /user/test
  Found 1 items
  -rw-r--r--   3 root supergroup          8 2022-11-02 14:04 /user/test/file2.csv
  [root@cnbjzxsshnn01 opt]# 
  [root@cnbjzxsshnn01 opt]# hadoop fs -cat /user/test/file2.csv
  200,12
  [root@cnbjzxsshnn01 opt]#
  ```

  

### 查询数据

```sql
MySQL [example_db]> SELECT * FROM table4;
+------+------+
| id   | city |
+------+------+
  |200 | 12
+------+------+
1 row in set (0.02 sec)

MySQL [example_db]> 
```



### 查看导入作业

```sh
SHOW LOAD\G;
```







# 参考

官方文档：https://docs.starrocks.io/zh-cn/2.3/quick_start/Deploy
