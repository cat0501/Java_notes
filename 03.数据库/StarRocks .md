



# 产品简介

## 是什么

**新一代极速全场景 MPP (Massively Parallel Processing，大规模并行处理) 数据库**。StarRocks 的愿景是能够让用户的**数据分析变得更加简单和敏捷**。用户无需经过复杂的预处理，就可以用 StarRocks 来支持多种数据分析场景的极速分析。

**架构简洁**，采用了全面向量化引擎，并配备全新设计的 CBO (Cost Based Optimizer) 优化器，**查询速度（尤其是多表关联查询）远超同类产品**。

能很好地支持实时数据分析，并能实现**对实时更新数据的高效查询**。StarRocks 还支持现代化物化视图，进一步加速查询。

兼容 **MySQL** 协议，支持标准 SQL 语法，易于对接使用，全系统无外部依赖，高可用，易于运维管理。



## 使用场景

1. OLAP 多维分析

   利用 StarRocks 的 MPP 框架和向量化执行引擎，用户可以灵活的选择雪花模型，星型模型，宽表模型或者预聚合模型。

2. 实时数据仓库

   实现了 Primary-Key 模型，能够实时更新数据并极速查询，可以秒级同步 **TP** (Transaction Processing) 数据库的变化，构建实时数仓。

   - 电商大促数据分析
   - 物流行业的运单分析
   - 直播质量分析

3. 高并发查询

   良好的数据分布特性，灵活的索引以及物化视图等特性

   - SaaS 行业面向用户分析报表
   - Dashboard 多页面分析

4. 统一分析

   通过使用一套系统解决多维分析、高并发查询、预计算、实时分析查询等场景；

   使用 StarRocks 统一管理数据湖和数据仓库，将高并发和实时性要求很高的业务放在 StarRocks 中分析，也可以使用 StarRocks 外表查询功能进行数据湖上的分析。



## 架构

架构简洁，整个系统的核心**只有 FE（Frontend）、BE（Backend）两类进程**，不依赖任何外部组件，方便部署与维护。

FE 和 BE 模块都**可以在线水平扩展**，元数据和业务数据都**有副本机制**，确保整个系统无单点。

提供 MySQL 协议接口，**支持标准 SQL 语法**。用户可通过 MySQL 客户端方便地查询和分析 StarRocks 中的数据。

![system_architecture](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.2-1.png)

### FE（前端节点：管理）

FE 是 StarRocks 的前端节点，负责管理元数据，管理客户端连接，进行查询规划，查询调度等工作。



根据配置有**两种角色：Follower 和 Observer**。Follower 会通过类 Paxos 的 BDBJE 协议选主出一个 Leader。三者区别如下：
- Observer
  - 主要用于扩展集群的查询并发能力，可选部署。
  - **不参与选主**，不会增加集群选主压力。
  - 通过回放 Leader 的元数据日志来异步同步数据。
- Follower
  - **只有元数据读取权限**，无写入权限。通过回放 Leader 的元数据日志来异步同步数据。
  - **参与 Leader 选举**，必须有半数以上的 Follower 节点存活才能进行选主。
- Leader
  - 只有 Leader 节点会对元数据进行写操作，Follower 和 Observer 只有读取权限。
  - Leader 从 Follower 中选举。如果 Leader 节点失败，Follower 会发起新一轮选主。



### BE（后端节点：数据存储、SQL执行）

BE 是 StarRocks 的后端节点，负责数据存储、SQL执行等工作。

- 数据存储：BE 节点是完全对等的，FE 按照一定策略将数据分配到对应的 BE 节点。BE 负责将导入数据写成对应的格式存储下来，并生成相关索引。

- 执行 SQL 计算
  - 一条 SQL 语句首先会按照具体的语义规划成**逻辑执行单元**，然后再按照数据的分布情况拆分成**具体的物理执行单元**。
  - 物理执行单元会在对应的数据存储节点上执行，这样可以实现**本地计算**，避免数据的传输与拷贝，从而能够得到极致的查询性能。



### 数据管理

- 使用**列式存储**，采用**分区分桶**机制进行数据管理。
  - （1）分区：如将一张表按照时间来进行分区，粒度可以是一天，或者一周等。
  - （2）分桶：一个分区内的数据可以**根据一列或者多列**进行分桶，将数据切分成多个 **Tablet**。（Tablet 是 StarRocks 中最小的数据管理单元。每个 Tablet 都会以多副本(replica) 的形式存储在不同的 BE 节点中。您可以自行指定 Tablet 的个数和大小。）

- 如下，展示了 StarRocks 的数据划分以及 Tablet 多副本机制。

图中，表按照日期划分为 4 个分区，第一个分区进一步切分成 4 个 Tablet。每个 Tablet 使用 3 副本进行备份，分布在 3 个不同的 BE 节点上。

![data_management](https://docs.starrocks.io/static/54199a0102ece692ce2840ed10ec06c6/c1b63/1.2-2.png)



- 支持**高并发**的能力
  - StarRocks 在执行 SQL 语句时，可以对所有 Tablet实现并发处理，从而充分的利用多机、多核提供的计算能力。
  - 用户也可以利用 StarRocks 数据的切分方式，将高并发请求压力分摊到多个物理节点，从而可以通过增加物理节点的方式来扩展系统支持高并发的能力。
- 支持 **Tablet 多副本存储**
  - 默认副本数为三个。**多副本能够保证数据存储的高可靠以及服务的高可用。**





## 特性

### MPP分布式执行框架

在 MPP 执行框架中，一条查询请求会被拆分成多个物理计算单元，在**多机并行执行**。每个执行节点拥有独享的资源（CPU、内存）。单个查询请求可以充分利用所有执行节点的资源，所以单个查询的性能可以随着集群的水平扩展而不断提升。

![MPP1](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.2-3.png)



如上，

1. 将**一个查询**在逻辑上切分为**多个逻辑执行单元**（Query Fragment）。
2. 按照每个逻辑执行单元需要处理的计算量，**每个逻辑执行单元**会由**一个或者多个物理执行单元**来具体实现。即，**不同逻辑执行单元可以由不同数目的物理执行单元来具体执行，以提高资源使用率，提升查询速度。**
3. 物理执行单元是最小的调度单位。一个物理执行单元会被调度到集群**某个BE上**执行。

![MPP1](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.2-4.png)



### 全面向量化执行引擎

充分发挥了 CPU 的处理能力。

**按照列式的方式**组织和处理数据。StarRocks 的数据存储、内存中数据的组织方式，以及 SQL 算子的计算方式，都是列式实现的。

按列的数据组织也会更加充分的利用 CPU 的 Cache，按列计算会有更少的虚函数调用以及更少的分支判断从而获得更加充分的CPU指令流水。



### CBO 优化器

![CBO](https://docs.starrocks.io/static/c4f97bfd8a587af0e8c9c24ba2623768/c1b63/1.2-5.png)

- 背景
  - 在多表关联查询场景下，仅靠优秀的执行引擎没有办法获得最极致的执行性能。因为这类场景下，不同执行计划的复杂度可能会相差几个数量级。
  - 查询中关联表的数目越大，可能的执行计划就越多，在众多的可能中选择一个最优的计划，这是一个NP-Hard的问题。只有优秀的查询优化器，才能选择出相对最优的查询计划，从而实现极致的多表分析性能。

- 设计
  - StarRocks从零设计并实现了一款全新的，基于代价的优化器CBO（Cost Based Optimizer）。

- 优势
  - 由于全新CBO的支持，StarRocks能比同类产品更好地支持多表关联查询，特别是复杂的多表关联查询，让全面向量化引擎能够发挥极致的性能。



### 可实时更新的列式存储引擎

StarRocks能够支持秒级的导入延迟，提供准实时的服务能力。



![columnar_storage_engine](https://docs.starrocks.io/static/41a1d6766686228b46feec61f91338b2/c1b63/1.2-6.png)



### 智能的物化视图

### 数据湖分析

StarRocks不仅能高效的分析本地存储的数据，也可以作为计算引擎直接分析数据湖中的数据。

支持包括Apache Hive、Apache Iceberg、Apache Hudi等数据组织结构，支持 Parquet、ORC、CSV 等文件格式，也支持 HDFS、S3、OSS 等存储方式。

在数据湖分析的场景中，StarRocks 主要负责数据的**计算分析**，而数据湖则主要负责数据的**存储、组织和维护**。

![datalake_analytics](https://docs.starrocks.io/static/93bb2ad5c74e2f33498a31dfb581aafe/a242d/1.2-8.png)





# 快速开始

## 部署

### 前提

环境要求如下：

| 分类     | 描述                                                         | 说明                                                         |
| :------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| 硬件要求 | 集群至少拥有两台物理或虚拟节点。<br/>BE 节点 CPU 需支持 AVX2 指令集。<br/>各节点间需要通过万兆网卡及万兆交换机连接。 | FE 节点建议配置 8 核 或以上 CPU，16GB 或以上内存。<br/>BE 节点建议配置 16 核 或以上 CPU，64GB 或以上内存。<br/>通过运行 `cat /proc/cpuinfo |grep avx2` 命令查看节点 CPU 支持的指令集，若有结果返回则表明 CPU 支持 AVX2 指令集。 |
| 操作系统 | 所有节点操作系统需为 CentOS（7 或以上）。                    |                                                              |
| 软件要求 | 所有节点需安装 Java Development Kit（1.8 或以上，推荐使用1.8）。<br/>客户端节点需安装 MySQL 客户端（5.5 或以上）。 |                                                              |
| 系统环境 | 集群时钟需保持同步。<br/>用户需要有设置 `ulimit -n` 权限。   |                                                              |



### 规划

| FE          | BE                                    |
| :---------- | :------------------------------------ |
| 10.11.14.15 | 10.11.14.13、10.11.14.15、10.11.14.16 |



### 部署 FE 节点

#### （1）下载并解压二进制安装包

```bash
tar -xzvf StarRocks-x.x.x.tar.gz
```

#### （2）配置 FE 节点

```sh
# 进入 StarRocks-x.x.x/fe 路径
cd StarRocks-x.x.x/fe
```

修改 FE 配置文件 **conf/fe.conf**。

```sh
# 添加元数据目录配置项。
meta_dir = ${STARROCKS_HOME}/meta

# 添加 Java 目录配置项。
JAVA_HOME = /path/to/your/java
```

#### （3）创建元数据路径

```bash
mkdir -p meta
```

> 注意：该路径需要与 **conf/fe.conf** 文件中配置路径保持一致。



#### （4）启动 FE 节点

```bash
bin/start_fe.sh --daemon
```

#### （5）确认 FE 启动成功

- 通过查看日志 **log/fe.log** 确认 FE 是否启动成功。
```sh
2020-03-16 20:32:14,686 INFO 1 [FeServer.start():46] thrift server started.  // FE 节点启动成功。
2020-03-16 20:32:14,696 INFO 1 [NMysqlServer.start():71] Open mysql server success on 9030  // 可以使用 MySQL 客户端通过 `9030` 端口连接 FE。
2020-03-16 20:32:14,696 INFO 1 [QeService.start():60] QE service start.
2020-03-16 20:32:14,825 INFO 76 [HttpServer$HttpServerThread.run():210] HttpServer started with port 8030
...
```
- 通过运行 `jps` 命令查看 Java 进程，确认 **StarRocksFE** 进程是否存在。
- 通过在浏览器访问 `FE ip:http_port`（默认 `http_port` 为 `8030`），进入 StarRocks 的 WebUI，用户名为 `root`，密码为空。



#### （6）添加 FE 节点

通过 MySQL 客户端连接 StarRocks 以添加 FE 节点。

```bash
mysql -h 127.0.0.1 -P9030 -uroot

# 查看 FE 状态
SHOW PROC '/frontends'\G
```

#### （7）部署 FE 节点的高可用集群

StarRocks 的 FE 节点支持 HA 模型部署，以保证集群的高可用。详细设置方式参考 [FE 高可用集群部署](https://docs.starrocks.io/zh-cn/2.3/administration/Deployment)。

#### （8）停止 FE 节点

```bash
./bin/stop_fe.sh --daemon
```



### 部署 BE 节点

#### （1）下载并解压二进制安装包

```sh
tar -xzvf StarRocks-x.x.x.tar.gz

[root@cnbjzxsshnn01 StarRocks-2.3.3]# ls
apache_hdfs_broker  be  fe  LICENSE.txt  meta  NOTICE.txt  udf
```

#### （2）配置 BE 节点

```bash
# 进入 StarRocks-x.x.x/be 路径
cd StarRocks-x.x.x/be
```

#### （3）创建数据路径

```sh
mkdir -p storage

[root@cnbjzxsshnn01 be]# ls
bin  conf  lib  log  storage  www
```

#### （4）添加 BE 节点

通过 MySQL 客户端将 BE 节点添加至 StarRocks 集群。

```sql
mysql> ALTER SYSTEM ADD BACKEND "10.11.14.15:9050";

# 移除节点
mysql> ALTER SYSTEM decommission BACKEND "10.11.14.15:9050";
```



#### （5）启动 BE 节点

```sh
bin/start_be.sh --daemon
```

#### （6）确认 BE 启动成功

```sql
mysql> SHOW PROC '/backends'\G
```

> 当 **`Alive`** 为 **`true`** 时，当前 **BE** 节点正常接入集群。
>
> 如果 BE 节点没有正常接入集群，可以通过查看 log/be.WARNING 日志文件排查问题。

#### （7）停止 BE 节点

```bash
./bin/stop_be.sh --daemon
```







## 建表

### 连接 StarRocks

可以通过 MySQL 客户端连接任意一个 FE 节点的 `query_port`（默认为 `9030`）以连接 StarRocks。StarRocks 内置 `root` 用户，密码默认为空。

```shell
mysql -h <fe_host> -P9030 -u root
mysql -h 10.11.14.15 -P9030 -u root
```

### 建库建表

```sql
# 建库
CREATE DATABASE example_db;
# 查看当前 StarRocks 集群中所有数据库
MySQL [(none)]> SHOW DATABASES;
```

StarRocks 支持 [多种数据模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model)，以适用不同的应用场景。以下示例基于 [明细表模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model#明细模型) 编写建表语句。

```sql
use example_db;
CREATE TABLE IF NOT EXISTS `detailDemo` (
    `recruit_date`  DATE           NOT NULL COMMENT "YYYY-MM-DD",
    `region_num`    TINYINT        COMMENT "range [-128, 127]",
    `num_plate`     SMALLINT       COMMENT "range [-32768, 32767] ",
    `tel`           INT            COMMENT "range [-2147483648, 2147483647]",
    `id`            BIGINT         COMMENT "range [-2^63 + 1 ~ 2^63 - 1]",
    `password`      LARGEINT       COMMENT "range [-2^127 + 1 ~ 2^127 - 1]",
    `name`          CHAR(20)       NOT NULL COMMENT "range char(m),m in (1-255)",
    `profile`       VARCHAR(500)   NOT NULL COMMENT "upper limit value 1048576 bytes",
    `hobby`         STRING         NOT NULL COMMENT "upper limit value 65533 bytes",
    `leave_time`    DATETIME       COMMENT "YYYY-MM-DD HH:MM:SS",
    `channel`       FLOAT          COMMENT "4 bytes",
    `income`        DOUBLE         COMMENT "8 bytes",
    `account`       DECIMAL(12,4)  COMMENT "",
    `ispass`        BOOLEAN        COMMENT "true/false"
) ENGINE=OLAP
DUPLICATE KEY(`recruit_date`, `region_num`)
PARTITION BY RANGE(`recruit_date`)
(
    PARTITION p20220311 VALUES [('2022-03-11'), ('2022-03-12')),
    PARTITION p20220312 VALUES [('2022-03-12'), ('2022-03-13')),
    PARTITION p20220313 VALUES [('2022-03-13'), ('2022-03-14')),
    PARTITION p20220314 VALUES [('2022-03-14'), ('2022-03-15')),
    PARTITION p20220315 VALUES [('2022-03-15'), ('2022-03-16'))
)
DISTRIBUTED BY HASH(`recruit_date`, `region_num`) BUCKETS 8
PROPERTIES (
    "replication_num" = "1" 
);
```

#### 排序键

- StarRocks 表内部组织存储数据时会按照指定列排序，这些列为排序列（Sort Key）。
- 明细模型中由 `DUPLICATE KEY` 指定排序列。

#### 字段类型

字段类型介绍详见 [数据类型章节](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-types/BIGINT)。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image2022-10-27_10-2-51.png)



#### 分区分桶

- `PARTITION` 关键字用于给表 [创建分区](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-definition/CREATE TABLE#partition_desc)。
  - 以上示例中使用 `recruit_date` 进行范围分区，从 11 日到 15 日每天创建一个分区。
- `DISTRIBUTED` 关键字用于给表 [创建分桶](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-definition/CREATE TABLE#distribution_desc)。
  - 以上示例中使用 `recruit_date` 以及 `region_num` 两个字段通过 Hash 算法创建 8 个桶。

#### 数据模型 ⭐

`DUPLICATE` 关键字表示当前表为明细模型，`KEY` 中的列表示当前表的排序列。StarRocks 支持多种数据模型，分别为 [明细模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model#明细模型)，[聚合模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model#聚合模型)，[更新模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model#更新模型)，[主键模型](https://docs.starrocks.io/zh-cn/2.3/table_design/Data_model#主键模型)。不同模型的适用于多种业务场景，合理选择可优化查询效率。



#### 索引

StarRocks 默认会给 Key 列创建稀疏索引加速查询，具体规则见 [排序键](https://docs.starrocks.io/zh-cn/2.3/table_design/Sort_key)。支持的索引类型有 [Bitmap 索引](https://docs.starrocks.io/zh-cn/2.3/table_design/Bitmap_index)，[Bloomfilter 索引](https://docs.starrocks.io/zh-cn/2.3/table_design/Bloomfilter_index) 等。

#### ENGINE 类型

默认 ENGINE 类型为 `OLAP`，对应 StarRocks 集群内部表。其他可选项包括 `mysql`，`elasticsearch`，`hive`，以及 `ICEBERG`，分别代表所创建的表为相应类型的 [外部表](https://docs.starrocks.io/zh-cn/2.3/data_source/External_table)。



### 查看表信息

通过 SQL 命令

```sql
# 查看当前数据库中所有的表
SHOW TABLES;

# 查看表的结构
DESC table_name;

# 查看建表语句
SHOW CREATE TABLE table_name;
```

### 修改表结构

StarRocks 支持多种 DDL 操作。可以通过 [ALTER TABLE](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-definition/ALTER TABLE) 命令可以修改表的 Schema，包括增加列，删除列，修改列类型（暂不支持修改列名称），改变列顺序。

```sql
# 增加列
例如，在以上创建的表中，与 ispass 列后新增一列 uv，类型为 BIGINT，默认值为 0。
ALTER TABLE detailDemo ADD COLUMN uv BIGINT DEFAULT '0' after ispass;

# 删除列（删除以上步骤新增的列）
ALTER TABLE detailDemo DROP COLUMN uv;

# 查看修改表结构作业状态（修改表结构为异步操作。提交成功后，您可以通过以下命令查看作业状态）
SHOW ALTER TABLE COLUMN\G;

# 取消修改表结构（取消当前正在执行的作业）
CANCEL ALTER TABLE COLUMN FROM table_name\G;
```

### 创建用户并授权

```sql
# 使用 root 账户创建 test 账户，并授予其 example_db 的读写权限 。
CREATE USER 'test' IDENTIFIED by '123456';
GRANT ALL on example_db to test;

# 通过登录被授权的 test 账户，就可以操作 example_db 数据库。
mysql -h 127.0.0.1 -P9030 -utest -p123456
```







## 导入和查询数据





# 表设计

- 数据模型

StarRocks 支持四种数据模型，分别是明细模型 (Duplicate Key Model)、聚合模型 (Aggregate Key Model)、更新模型 (Unique Key Model) 和主键模型 (Primary Key Model)。

这四种数据模型能够支持多种数据分析场景，例如日志分析、数据汇总分析、实时分析等。

- 排序键：按照建表时指定的一列或多列排序和存储，这部分用于排序的列就称为排序键。
  - 明细模型中，排序键就是用于排序的列，即 `DUPLICATE KEY` 指定的列。 
  - 聚合模型中，排序键就是用于聚合的列，即 `AGGREGATE KEY` 指定的列。
  - 主键模型和更新模型中，排序键就是满足唯一性约束的列，分别由 `PRIMARY KEY` 和 `UNIQUE KEY` 指定。



## 明细模型 (Duplicate Key Model)

- 概念：创建表时，支持定义**排序键**。
- 适用场景
  - 适用于**分析日志数据等**，支持追加新数据，不支持修改历史数据。



## 聚合模型 (Aggregate Key Model)

- 概念：建表时，支持定义**排序键和指标列**，并为指标列指定聚合函数。当多条数据具有相同的排序键时，指标列会进行聚合。在分析统计和汇总数据时，聚合模型能够减少查询时所需要处理的数据，提升查询效率。
- 适用场景：适用于**分析统计和汇总数据**。多为汇总类查询，比如 **SUM**、**COUNT**、**MAX** 等类型的查询。
  - 通过分析网站或 APP 的访问流量，统计用户的访问总时长、访问总次数。
  - 广告厂商为广告主提供的广告点击总量、展示总量、消费统计等。
  - 通过分析电商的全年交易数据，获得指定季度或者月份中，各类消费人群的爆款商品。
- 原理：从数据导入至数据查询阶段，聚合模型内部同一排序键的数据会多次聚合。

- 举例：例如，导入如下数据至聚合模型中：

| Date       | Country | PV   |
| :--------- | :------ | :--- |
| 2020.05.01 | CHN     | 1    |
| 2020.05.01 | CHN     | 2    |
| 2020.05.01 | USA     | 3    |
| 2020.05.01 | USA     | 4    |

在聚合模型中，以上四条数据会聚合为两条数据。这样在后续查询处理的时候，处理的数据量就会显著降低。

| Date       | Country | PV   |
| :--------- | :------ | :--- |
| 2020.05.01 | CHN     | 3    |
| 2020.05.01 | USA     | 7    |



## 更新模型 (Unique Key Model)

- 概念：建表时，支持定义**主键和指标列**，查询时**返回主键相同的一组数据中的最新数据**。相对于明细模型，更新模型**简化了数据导入流程**，能够更好地支撑实时和频繁更新的场景。
- 适用场景：**实时和频繁更新**的业务场景，例如分析电商订单。在电商场景中，订单的状态经常会发生变化，每天的订单更新量可突破上亿。



## 主键模型 (Primary Key Model)

- 概念：建表时，支持定义主键和指标列，查询时返回主键相同的一组数据中的最新数据。StarRocks 1.19 版本推出。
- 适用场景
  - 适用于**实时和频繁更新的场景**。
  - 相对于其他模型，主键模型对内存的要求比较高。目前主键模型中，主键编码后，占用内存空间上限为 127 字节。
  - 如下两个场景中，主键占用空间相对可控：
    - （1）数据有冷热特征，即最近几天的热数据才经常被修改，老的冷数据很少被修改。例如，MySQL订单表实时同步到 StarRocks 中提供分析查询。
    - （2）大宽表（数百到数千列）。主键只占整个数据的很小一部分，其内存开销比较低。比如用户状态和画像表，虽然列非常多，但总的用户数不大（千万至亿级别），主键索引内存占用相对可控。
- 原理
  - StarRocks 收到对某记录的更新操作时，会通过主键索引找到该条记录的位置，并对其标记为删除，再插入一条新的记录。**相当于把 Update 改写为 Delete+Insert**。
- 举例
  - 例如，需要**按天实时分析订单**，则可以将时间 `dt`、订单编号 `order_id` 作为主键，其余列为指标列。
  - 例如，需要**实时分析用户情况**，则可以将用户 ID `user_id` 作为主键，其余为指标列。



# 数据导入

## 导入总览

为适配不同的数据导入需求，StarRocks 系统提供了五种不同的导入方式，以支持从不同的数据源（如 HDFS、Kafka、本地文件等）或者方式（异步或同步）导入数据。

StarRocks 数据导入整体生态图如下。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/screenshot_1615530614737.png)





| 导入方式           | 协议  | 业务场景                                                     | 数据量（单作业）     | 数据源                                                       | 数据格式                | 同步模式 |
| :----------------- | :---- | :----------------------------------------------------------- | :------------------- | :----------------------------------------------------------- | :---------------------- | :------- |
| Stream Load        | HTTP  | 通过 HTTP 协议导入本地文件、或通过程序导入数据流。           | 10 GB 以内           | 本地文件<br>流式数据                                         | CSV<br/>JSON            | 同步     |
| Broker Load        | MySQL | 从 HDFS 或外部云存储系统导入数据。                           | 数十到数百 GB        | HDFS<br/>Amazon S3<br/>Google GCS<br/>阿里云OSS<br/>腾讯云 COS | CSV<br/>Parquet<br/>ORC | 异步     |
| Routine Load       | MySQL | 从 Apache Kafka® 实时地导入数据流。                          | 微批导入 MB 到 GB 级 | Kafka                                                        | CSV<br/>JSON            | 异步     |
| Spark Load         | MySQL | 通过 Apache Spark™ 集群初次从 HDFS 或 Hive 迁移导入大量数据。<br/>需要做全局数据字典来精确去重。 | 数十 GB 到 TB级别    | HDFS<br/>Hive                                                | CSV<br/>Parquet         | 异步     |
| INSERT INTO SELECT | MySQL | 外表导入。<br/>StarRocks 数据表之间的数据导入。              | 跟内存相关           | StarRocks 表<br/>外部表                                      | StarRocks 表            | 同步     |
| INSERT INTO VALUES | MySQL | 单条批量小数据量插入。通过 JDBC 等接口导入。                 | 简单测试用           | 程序ETL 工具                                                 | SQL                     | 同步     |





下图详细展示了在各种数据源场景下，应该选择哪一种导入方式。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/4.1.2.png)







## 从 HDFS 或外部云存储系统导入数据

基于 `MySQL` 协议的 `Broker Load` 导入方式，可从 HDFS 或外部云存储系统导入几十到数百 GB 的数据量。

### 概念

- 背景
  - Broker Load 需要借助 **Broker** 访问外部存储系统。
  - Broker 是一个独立的无状态服务，封装了文件系统接口。通过 Broker，StarRocks 能够访问和读取外部存储系统上的数据文件，并利用自身的计算资源对数据文件中的数据进行预处理和导入。

- 支持的数据文件格式
  - CSV
  - Parquet
  - ORC
- 支持的外部存储系统
  - HDFS
  - Amazon S3
  - Google GCS
  - 阿里云 OSS
  - 腾讯云 COS
- 前提条件
  - StarRocks 集群中已部署 Broker。
  - 可以通过 [SHOW BROKER](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/Administration/SHOW BROKER) 语句来查看集群中已经部署的 Broker。
- 基本原理

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/4.3-1.png)





### 从 HDFS 导入1

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



- 建表
  - 默认 ENGINE 类型为 `OLAP`，对应 StarRocks 集群内部表。
  - 明细模型中由 `DUPLICATE KEY` 指定排序列。
  - `DUPLICATE` 关键字表示当前表为明细模型，`KEY` 中的列表示当前表的排序列。
  - `PARTITION` 关键字用于给表 [创建分区](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-definition/CREATE TABLE#partition_desc)。`DISTRIBUTED` 关键字用于给表 [创建分桶](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-definition/CREATE TABLE#distribution_desc)。


```sql
mysql -h 10.11.14.15 -P9030 -uroot

CREATE TABLE `table4` (
  `id` int(11) NOT NULL COMMENT "城市 ID",
  `city` varchar(65533) NULL DEFAULT "" COMMENT "城市名称"
) ENGINE=OLAP 
PRIMARY KEY(`id`)
COMMENT "OLAP"
DISTRIBUTED BY HASH(`id`) BUCKETS 10 
PROPERTIES (
"replication_num" = "3",
"in_memory" = "false",
"storage_format" = "DEFAULT",
"enable_persistent_index" = "false"
);
```



```sql
MySQL [example_db]> desc table4;
+-------+----------------+------+-------+---------+-------+
| Field | Type           | Null | Key   | Default | Extra |
+-------+----------------+------+-------+---------+-------+
| id    | INT            | No   | true  | NULL    |       |
| city  | VARCHAR(65533) | Yes  | false |         |       |
+-------+----------------+------+-------+---------+-------+
2 rows in set (0.01 sec)
```



- 导入

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




### 从 HDFS 导入2

- 建表

```sql
create table healthinforecords(
    id                int         not null comment 'id',
    idnumber          varchar(18) null comment '身份证号',
    diastolicPressure int         null comment '舒张压',
    systolicPressure  int         null comment '收缩压',
    pluseVal          int         null comment '脉搏频率',
    temperatureVal    float       null comment '体温',
    breathingRate     int         null comment '呼吸频率',
    loadDate          date        null comment '记录时间'
) DUPLICATE KEY(id,idnumber)
DISTRIBUTED BY HASH(id) BUCKETS 10;
```

- 上传数据到 HDFS

```sh
hadoop fs -put ./healthinforecords.csv /user/test
```

- 执行导入

```sql
[root@cnbjzxsshnn01 opt]# mysql -h 10.11.14.15 -P9030 -uroot
MySQL [(none)]> use example_db;

LOAD LABEL example_db.healthinforecords
(
    DATA INFILE("hdfs://10.11.14.15:8020/user/test/healthinforecords.csv")
    INTO TABLE healthinforecords
    COLUMNS TERMINATED BY ","
    (id, 
    idnumber,
    diastolicPressure,
    systolicPressure,
    pluseVal,
    temperatureVal,
    breathingRate,
    loadDate)
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

- 查看导入作业执行情况、查看导入数据

```sh
SHOW LOAD\G;
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
