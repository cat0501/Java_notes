![StarRocks开源——携手未来，星辰大海！](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/v2-9dbd6f97b6dc15b328ef9ad1d1827fc4_720w.jpg)



# 产品简介

## 是什么

StarRocks 是下一代亚秒级 MPP 数据库，适用于完整的分析场景，包括多维分析、实时分析和 ad-hoc 查询。



**新一代极速全场景 MPP (Massively Parallel Processing，大规模并行处理) 数据库**。StarRocks 的愿景是能够让用户的**数据分析变得更加简单和敏捷**。用户无需经过复杂的预处理，就可以用 StarRocks 来支持多种数据分析场景的极速分析。

**架构简洁**，采用了全面向量化引擎，并配备全新设计的 CBO (Cost Based Optimizer) 优化器，**查询速度（尤其是多表关联查询）远超同类产品**。

能很好地支持实时数据分析，并**能实现对实时更新数据的高效查询**。StarRocks 还支持现代化物化视图，进一步加速查询。

兼容 **MySQL** 协议，**支持标准 SQL 语法**，易于对接使用，全系统无外部依赖，高可用，易于运维管理。

 

> 一个官方视频介绍：
> #<video src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/StarRocks%20Intro.mp4"></video>



- 背景
  - Doris ：百度统计报表的专用系统
  - **Apache Doris**：2018年百度贡献给Apache 的
  - DorisDB：原百度 Doris 团队的个别人员离职创业的商业化闭源产品
  - **StarRocks**：版权的问题，将 DorisDB 改名为 StarRocks
- 官方文档
  - Apache Doris 官网文档：https://doris.apache.org/zh-CN/learning
  - StarRocks 官方文档：https://docs.starrocks.io/zh-cn/main/introduction/StarRocks_intro

- 出现：如何有效地分析这些海量的数据，真正有效地利用数据为业务创造价值？
  - **数据分析性能不达标**：业务提出了更多的分析需求，比如多维分析，实时分析，高并发查询。在很多分析需求场景下，当前系统性能表现不佳，无法提供极速分析体验。
  - **数据分析的灵活性不足**：比如自助化 BI 这样灵活的场景下，星型模型和雪花模型的价值不可替代。现有的系统难以同时高性能支持这些建模手段。
  - **数据架构复杂度太高**：目前同时构建离线数据链路和实时数据链路，存在数据同步、数据一致性、计算逻辑同步、异常数据处理、多系统运维等问题。
  - **数据分析能力弹性不足**：数据规模越来越大，对应的数据分析系统需要不断地扩容；不同的业务线有不同的数据分析访问量。如何保证既能支持好业务，又能节省成本？**就需要一套全新的“极速统一”的数据架构。“极速”，意味着全面提升数据处理和分析的性能；“统一”意味着将复杂分散的数据架构融合为简单统一的架构。**

> 复杂的企业数据分析架构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/v2-38fb3beddd6e5d7ba07d42b9549f622f_1440w.jpg)









![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/v2-d78042fa3bd19decf248c6de5e8405c1_1440w.webp)

- StarRocks 实现了极速统一分析
  - StarRocks 可以同时高效支持 OLAP 多维分析、实时数据分析、高并发查询、AdHoc 查询等多场景，并且比上一代同类型产品的分析能力快3～5倍以上。
  - 全新的 OLAP多维分析体验，打破“只能做大宽表”的局限性，让多种数据建模模式：预计算、大宽表、星型模型和雪花模型等都具备极速分析体验。
  - 全新的实时数据分析体验，支持数据实时更新和删除，并能保证极速查询性能。
  - 全新的高并发查询体验，支持数千人同时访问。
  - 全新极简统一的OLAP架构，大大降低了使用和运维管理复杂度，提升了开发和使用效率。



- OLAP

> - OLAP(Online Analytical Processing)：分析型数据库，是指一类支持对大规模数据进行较为复杂的联机分析处理的数据库，更关注复杂查询和聚集分析。
>   - 通常并发不高，每个查询会运行较长时间，操作的数据量巨大。
>   - 分析中的查询，大多只需读取数据，不会对历史数据轻易修改。
>   - 分析中的关系代数操作，会包含非常复杂的交运算，中间结果可能种类繁多数量庞大，但 最终返回给用户的结果可能较小较容易理解。
> - 分布式 OLAP 数据库：业界代表包括 TeraData、Greenplum、GaussDB(DWS)、AnalyticDB、 Bigquery、 Clickhouse等。
> - 常见OLAP引擎对比

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRf5b-D5aaC5q2i5rC0,size_20,color_FFFFFF,t_70,g_se,x_16-20221106205516039.png)



- 特性
  - Native vectorized SQL engine（原生矢量化SQL引擎）
  - Standard SQL（标准SQL）：与MySQL协议兼容。可以使用各种客户端和BI软件来访问StarRock。
  - Smart query optimization（智能查询优化）：CBO(基于成本的优化器)可以优化复杂的查询。
  - Real-time update（实时更新）：可以根据主键执行 upsert/DELETE 操作，在并发更新时实现高效的查询。
  - Intelligent materialized view（智能实化视图）：可以在数据导入过程中自动更新，并在查询执行时自动选择。
  - Querying data in data lakes directly（直接查询数据湖中的数据）：直接访问，无需导入。
  - Resource management（资源管理）：允许StarRock限制查询的资源消耗。
  - Easy to maintain（易于维护）：简单的架构使其易于部署、维护和扩展。在集群扩展或扩展时平衡资源，并在节点发生故障时自动恢复数据副本。



- 在大数据生态中的地位：StarRocks 不依赖于某⼀种技术栈，而又能够**兼容大数据平台的绝大部分技术栈**。
  - 在数据导入层面上，StarRock 可以拉取 HDFS、S3、OSS 中的数据，也可以导⼊平面文件，或者是消费 Kafka 中的增量数据；
  - 对于像 MySQL 或者 Oceanbase 这样的 TP 业务库，全量数据我们可以通过 dataX， sqoop 等工具进⾏同步，增量数据我们可以通过 canal 这样的 CDC 工具实时同步；
  - 如果在同步的过程中，我们需要进⾏⼀些清洗或者数据转换操作，可以使⽤ Flink 或 者 Spark；
  - 此外 StarRocks 还支持外表联邦查询，可以拉取 Hive、MySQL、ES 以及 Iceberg 中的 数据，与 StarRocks 中的表进⾏关联，避免数据孤岛的存在；
  - 从顶层协议来看，StarRocks 兼容了 `MySQL` 协议，可以轻松平稳的对接多种开源或者 商业 `BI` 工具，⽐如说 Tableau，FineBI，SmartBI，Superset 等。

> 数据运营层 ODS
>
> 数据细节层 DWD
>
> 数据服务层 DWS
>
> 数据应用层 ADS

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/v2-8239f4bc13b21a6050ee4c198d618e5c_1440w.webp)



![](https://img-blog.csdnimg.cn/230e8277a75a47c4aff4c520e884e822.png)



## 使用场景

1. OLAP **多维**分析

   利用 StarRocks 的 MPP 框架和向量化执行引擎，用户可以灵活的选择雪花模型，星型模型，宽表模型或者预聚合模型。

2. **实时**数据仓库

   实现了 Primary-Key 模型，能够实时更新数据并极速查询，可以秒级同步 **TP** (Transaction Processing) 数据库的变化，构建实时数仓。

   - 电商大促数据分析
   - 物流行业的运单分析
   - 直播质量分析

3. **高并发**查询

   良好的数据分布特性，灵活的索引以及物化视图等特性

   - SaaS 行业面向用户分析报表
   - Dashboard 多页面分析

4. **统一**分析

   通过使用一套系统解决多维分析、高并发查询、预计算、实时分析查询等场景；

   使用 StarRocks 统一管理数据湖和数据仓库，将高并发和实时性要求很高的业务放在 StarRocks 中分析，也可以使用 StarRocks 外表查询功能进行数据湖上的分析。



## 架构

架构简洁，整个系统的核心**只有 FE（Frontend）、BE（Backend）两类进程**，不依赖任何外部组件，方便部署与维护。

FE 和 BE 模块都**可以在线水平扩展**，元数据和业务数据都**有副本机制**，确保整个系统无单点。

提供 MySQL 协议接口，**支持标准 SQL 语法**。用户可通过 MySQL 客户端方便地查询和分析 StarRocks 中的数据。

![system_architecture](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.2-1.png)

### FE（前端节点：管理）

FE 是 StarRocks 的前端节点，负责管理元数据，管理客户端连接，进行查询规划，查询调度等工作。使用 Java 语言。

> FE 的主要作用有：
>
> - **缓存外部表元数据**。其中，元数据信息分为两类：
>   - 数据表的基本元信息，如表结构 Schema、存储位置、分区信息、不同类别的统计信息等；
>   - 数据表下的数据文件信息，如某个分区下的文件列表、大小、压缩格式等。
> - **查询规划**。包括解析 SQL 文本，生成逻辑执行计划和物理执行计划，对执行计划进行优化，如分区/列裁剪，Join Reorder 等。
> - **查询调度**。使用上述的外部表元数据，根据数据的分布情况分配可用的 BE 节点资源等信息，规划每个 BE 需要执行的具体计算任务，并使用算法保证数据本地性的同时，尽可能让每个 BE 节点并行地读取大致相同的数据量以提高执行效率。



根据配置有**两种角色：Follower 和 Observer**。Follower 会通过类 Paxos 的 BDBJE 协议选主出一个 Leader。三者区别如下：
- Observer
  - 主要用于**扩展集群的查询并发能力，可选**部署。
  - **不参与选主**，不会增加集群选主压力。
  - 通过回放 Leader 的元数据日志来异步同步数据。
- Follower
  - **只有元数据读取权限**，无写入权限。通过回放 Leader 的元数据日志来异步同步数据。
  - **参与 Leader 选举**，必须有半数以上的 Follower 节点存活才能进行选主。
- Leader
  - 只有 Leader 节点会**对元数据进行写操作**，Follower 和 Observer 只有读取权限。
  - Leader 从 Follower 中选举。如果 Leader 节点失败，Follower 会发起新一轮选主。



### BE（后端节点：数据存储、SQL执行）

BE 是 StarRocks 的后端节点，负责数据存储、SQL执行等工作。

- 数据存储：BE 节点是完全对等的，FE 按照一定策略将数据分配到对应的 BE 节点。BE 负责将导入数据写成对应的格式存储下来，并生成相关索引。

- 执行 SQL 计算
  - 一条 SQL 语句首先会按照具体的语义规划成**逻辑执行单元**，然后再按照数据的分布情况拆分成**具体的物理执行单元**。
  - 物理执行单元会在对应的数据存储节点上执行，这样可以实现**本地计算**，避免数据的传输与拷贝，从而能够得到极致的查询性能。



> BE 主要作用有：
>
> 1. 执行 FE 分配的计算任务。如 Scan、Join、Shuffle、Aggregate 等。
>
> 2. 向 FE 汇报执行状态，传输执行结果。



### 数据管理

- 使用**列式存储**，采用**分区分桶**机制进行数据管理。
  - （1）分区：如将一张表按照时间来进行分区，粒度可以是一天，或者一周等。
  - （2）分桶：一个分区内的数据可以**根据一列或者多列**进行分桶，将数据切分成多个 **Tablet**。（Tablet 是 StarRocks 中最小的数据管理单元。每个 Tablet 都会以多副本(replica) 的形式存储在不同的 BE 节点中。您可以自行指定 Tablet 的个数和大小。）

- 如下，展示了 StarRocks 的数据划分以及 Tablet 多副本机制。

图中，表按照日期划分为 4 个分区，第一个分区进一步切分成 4 个 Tablet。每个 Tablet 使用 3 副本进行备份，分布在 3 个不同的 BE 节点上。

![data_management](https://docs.starrocks.io/static/54199a0102ece692ce2840ed10ec06c6/c1b63/1.2-2.png)



- 支持**高并发**的能力
  - StarRocks 在执行 SQL 语句时，可以对所有 Tablet 实现并发处理，从而充分的利用多机、多核提供的计算能力。
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



### CBO 优化器 ⭐

![CBO](https://docs.starrocks.io/static/c4f97bfd8a587af0e8c9c24ba2623768/c1b63/1.2-5.png)

- 背景
  - 在多表关联查询场景下，仅靠优秀的执行引擎没有办法获得最极致的执行性能。因为这类场景下，不同执行计划的复杂度可能会相差几个数量级。
  - 查询中关联表的数目越大，可能的执行计划就越多，在众多的可能中选择一个最优的计划，这是一个NP-Hard的问题。只有优秀的查询优化器，才能选择出相对最优的查询计划，从而实现极致的多表分析性能。

- 设计
  - **StarRocks从零设计并实现了一款全新的，基于代价的优化器CBO（Cost Based Optimizer）**。

- 优势
  - 由于全新CBO的支持，StarRocks 能比同类产品**更好地支持多表关联查询，特别是复杂的多表关联查询**，让全面向量化引擎能够发挥极致的性能。



### 可实时更新的列式存储引擎

StarRocks能够支持秒级的导入延迟，提供**准实时的服务能力**。



![columnar_storage_engine](https://docs.starrocks.io/static/41a1d6766686228b46feec61f91338b2/c1b63/1.2-6.png)



### 智能的物化视图

- StarRocks 支持用户使用物化视图**进行查询加速**。（FROM 官方文档）
  - 可以自动根据原始表更新数据；而且物化视图的选择也是自动进行的。

- 物化视图包含了两个维度的内容，**一个维度是物化，一个维度是视图**。（FROM 官方知乎社区）
  - 物化这个维度指的是物化视图要将数据进行物理化存储，这样后续应用就能够直接使用，起到查询加速的效果。
  - 视图是逻辑层次的概念，表达的是一个查询的结果集，视图可以直接被用来指定进行查询。用户使用视图更多的是想做一个逻辑的抽象，用来简化 SQL。
  - 所以物化视图是两者的融合，一方面能够通过物理层的存储来加速查询，另一方面提供了逻辑层的抽象，用来简化用户的 SQL 表达。





### 数据湖分析

StarRocks**不仅能高效的分析本地存储的数据，也可以作为计算引擎直接分析数据湖中的数据**。

支持包括 **`Apache Hive`**、**`Apache Iceberg`**、**`Apache Hudi`** （Uber 开源的Data Lakes解决方案）等数据组织结构，支持 Parquet、ORC、CSV 等文件格式，也支持 HDFS、S3、OSS 等存储方式。

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

| FE          | BE                                    | Broker                                |
| :---------- | :------------------------------------ | ------------------------------------- |
| 10.11.14.15 | 10.11.14.13、10.11.14.15、10.11.14.16 | 10.11.14.13、10.11.14.15、10.11.14.16 |







### 部署 FE 节点

#### （1）下载并解压二进制安装包

```bash
tar -xzvf StarRocks-x.x.x.tar.gz

[root@cnbjzxsshnn01 StarRocks-2.3.3]# ls
apache_hdfs_broker  be  fe  LICENSE.txt  meta  NOTICE.txt  udf
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

web UI：http://10.11.14.15:8030/system

> 用户名：root
>
> 密码：空



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



### 部署 Broker 节点

通过部署的 Broker，StarRocks 可**读取对应数据源（如HDFS、S3）上的数据**，利用自身的计算资源**对数据进行预处理和导入**。

除此之外，Broker 也被应用于**数据导出，备份恢复**等功能。

```sh
cd $STARROCKS_HOME/apache_hdfs_broker

# 查看
SHOW PROC "/brokers";

# broker 添加
ALTER SYSTEM ADD BROKER mybroker "10.11.14.16:8000";

# broker 删除
ALTER SYSTEM DROP BROKER mybroker "10.11.14.16:8000";
```

Broker 在系统架构中的位置如下：

```lua
+----+   +----+
| FE |   | BE |
+-^--+   +--^-+
  |         |
  |         |
+-v---------v-+
|   Broker    |
+------^------+
       |
       |
+------v------+
|HDFS/BOS/AFS |
+-------------+
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
-- 建库
CREATE DATABASE example_db;
-- 查看当前 StarRocks 集群中所有数据库
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
-- 增加列
例如，在以上创建的表中，与 ispass 列后新增一列 uv，类型为 BIGINT，默认值为 0。
ALTER TABLE detailDemo ADD COLUMN uv BIGINT DEFAULT '0' after ispass;

-- 删除列（删除以上步骤新增的列）
ALTER TABLE detailDemo DROP COLUMN uv;

-- 查看修改表结构作业状态（修改表结构为异步操作。提交成功后，您可以通过以下命令查看作业状态）
SHOW ALTER TABLE COLUMN\G;

-- 取消修改表结构（取消当前正在执行的作业）
CANCEL ALTER TABLE COLUMN FROM table_name\G;
```



### 创建用户并授权

```sql
-- 使用 root 账户创建 test 账户，并授予其 example_db 的读写权限 。
CREATE USER 'test' IDENTIFIED by '123456';
GRANT ALL on example_db to test;

-- 通过登录被授权的 test 账户，就可以操作 example_db 数据库。
mysql -h 127.0.0.1 -P9030 -utest -p123456
```







## 导入和查询数据





# 表设计

建表时，您需要指定数据模型 (Data Model)，这样数据导入至数据模型时，StarRocks 会**按照排序键对数据进行排序、处理和存储**。



- 数据模型

StarRocks 支持四种数据模型，分别是明细模型 (Duplicate Key Model)、聚合模型 (Aggregate Key Model)、更新模型 (Unique Key Model) 和主键模型 (Primary Key Model)。

这四种数据模型能够支持多种数据分析场景，例如日志分析、数据汇总分析、实时分析等。

- 排序键：数据导入至数据模型，按照建表时指定的一列或多列**排序和存储**，这部分用于排序的列就称为排序键。
  - 明细模型中，排序键就是用于排序的列，即 `DUPLICATE KEY` 指定的列。 
  - 聚合模型中，排序键就是用于聚合的列，即 `AGGREGATE KEY` 指定的列。
  - 主键模型和更新模型中，排序键就是满足唯一性约束的列，分别由 `PRIMARY KEY` 和 `UNIQUE KEY` 指定。



## 汇总

- 明细：只能追加数据，不能修改历史数据。适合具有非常强的时序性的日志分析类场景。在日志分析的场景中，一般设置的排序键为事件类型和事件时间，这样分析某时间范围的某一类事件时，性能将会得到比较大的提升。
- 聚合：对于汇总分析统计的场景
- 更新：聚合模型的一种特殊形式，聚合函数为Replace，即返回具有相同主键的一组数据的最新记录。更新模型依然是追加模式，由于有多个版本，查询时会有比较多的开销，性能上不是太优。
- 主键：是在1.19版本后增加的，与更新模型类似，但解决了更新模型中最核心的真正更新的逻辑问题。在主键模型中，采取的则是真正的更新模式，非常适合实时和频繁更新的场景，由于不会像更新模型保留多个版本的信息，查询效率也得到很大提升，理论上可以达到3~10倍的提升，具体要看SQL的复杂度。
  - 主键模型对于频繁增删改查的交易业务场景比较适合，特别适合大数据场景中的实时的增量数据更新场景
  - 用硬件换性能也是大数据优化的一种常见模式，所以在设计主键模型时，最好有冷热数据的考虑，比如考虑按时间分区，同时主键模型的表的量级不能太大，一般不要超过亿级，否则对内存的消耗会非常大
  - 最后主键模型采取的Update模式（也是Delete-Insert模式），基于表的主键进行数据的更新操作（主键具备唯一性）
- 最后，明细模型应用于更新比较少的批量的大数据离线场景，主键模型应用于更新频繁流式的大数据实时场景，离线和实时都得到比较好的支撑。



## 明细模型 (Duplicate Key Model)

- 概念：创建表时，支持定义**排序键**。
- 适用场景
  - 适用于**分析日志数据等**，支持追加新数据，不支持修改历史数据。
  - 导入日志数据或者时序数据，主要特点是旧数据不会更新，只会追加新的数据。
- 举例
  - 例如，需要分析某时间范围的某一类事件的数据，则可以将事件时间（`event_time`）和事件类型（`event_type`）作为排序键。在该业务场景下，建表语句如下：

```sql
CREATE TABLE IF NOT EXISTS detail (
    event_time DATETIME NOT NULL COMMENT "datetime of event",
    event_type INT NOT NULL COMMENT "type of event",
    user_id INT COMMENT "id of user",
    device_code INT COMMENT "device code",
    channel INT COMMENT ""
)
DUPLICATE KEY(event_time, event_type)
DISTRIBUTED BY HASH(user_id) BUCKETS 8;
```

> 建表时必须使用 `DISTRIBUTED BY HASH` 子句指定分桶键。分桶键的更多说明，请参见[分桶](https://docs.starrocks.io/zh-cn/latest/table_design/Data_distribution/#分桶)。



## 聚合模型 (Aggregate Key Model)

- 概念：建表时，支持定义**排序键和指标列**，**并为指标列指定聚合函数**。当多条数据具有相同的排序键时，指标列会进行聚合。在分析统计和汇总数据时，聚合模型能够减少查询时所需要处理的数据，提升查询效率。
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
- 原理：可以视为聚合模型的特殊情况，指标列指定的聚合函数为 **REPLACE**，返回具有相同主键的一组数据中的最新数据。
  - 例如下表中，`ID` 是主键，`value` 是指标列，`_version` 是 StarRocks 内部的版本号
  
  | ID   | value | _version |
  | :--- | :---- | :------- |
  | 1    | 100   | 1        |
  | 1    | 101   | 2        |
  | 2    | 100   | 3        |
  | 2    | 101   | 4        |
  | 2    | 102   | 5        |
  
  - 最终查询结果如下
  | ID   | value |
  | :--- | :---- |
  | 1    | 101   |
  | 2    | 102   |



## 主键模型 (Primary Key Model)

- 概念：建表时，支持定义**主键和指标列**，查询时**返回主键相同的一组数据中的最新数据**。StarRocks 1.19 版本推出。

  - 相对于更新模型：主键模型在查询时不需要执行聚合操作，并且支持谓词和索引下推，能够在支持**实时和频繁更新**等场景的同时，提供高效查询。

- 适用场景
  - 适用于**实时和频繁更新的场景**。
  - 相对于其他模型，主键模型对内存的要求比较高。目前主键模型中，主键编码后，占用内存空间上限为 127 字节。
  - 如下两个场景中，主键占用空间相对可控：
    - （1）数据有冷热特征，即最近几天的热数据才经常被修改，老的冷数据很少被修改。例如，MySQL订单表实时同步到 StarRocks 中提供分析查询。
    
    ![主键1](https://docs.starrocks.io/static/a93e1d5136d7d58c9395f9da6569a62c/c1724/3.2-1.png)
    
    - （2）大宽表（数百到数千列）。主键只占整个数据的很小一部分，其内存开销比较低。比如用户状态和画像表，虽然列非常多，但总的用户数不大（千万至亿级别），主键索引内存占用相对可控。
    
    ![主键2](https://docs.starrocks.io/static/248eb27b2bdaedb46664684792b0c75d/2a195/3.2-2.png)
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



### 从腾讯云 COS

把腾讯云 COS 存储空间 `bucket_cos` 里 `/input/` 文件夹内的 CSV 文件 `file1.csv` 和 `file2.csv` 分别导入到 StarRocks 表 `table1` 和 `table2` 中

```sql
LOAD LABEL test_db.label5
(
    DATA INFILE("cosn://bucket_cos/input/file1.csv")
    INTO TABLE table1
    (id, name)
    
    DATA INFILE("cosn://bucket_cos/input/file2.csv")
    INTO TABLE table2
    (id, name, score)
)
WITH BROKER "mybroker"
(
    "fs.cosn.userinfo.secretId" = "xxxxxxxxxxxxxxxxx",
    "fs.cosn.userinfo.secretKey" = "yyyyyyyyyyyyyyyy",
    "fs.cosn.bucket.endpoint_suffix" = "cos.ap-beijing.myqcloud.com"
)
```



## 使用 INSERT INTO 语句导入

### 准备库表

在 StarRocks 中创建数据库 `load_test`，并在其中创建聚合模型表 `insert_wiki_edit` 以及数据源表 `source_wiki_edit`。

```sql
CREATE DATABASE IF NOT EXISTS load_test;
USE load_test;
CREATE TABLE insert_wiki_edit
(
    event_time DATETIME,
    channel VARCHAR(32) DEFAULT '',
    user VARCHAR(128) DEFAULT '',
    is_anonymous TINYINT DEFAULT '0',
    is_minor TINYINT DEFAULT '0',
    is_new TINYINT DEFAULT '0',
    is_robot TINYINT DEFAULT '0',
    is_unpatrolled TINYINT DEFAULT '0',
    delta INT SUM DEFAULT '0',
    added INT SUM DEFAULT '0',
    deleted INT SUM DEFAULT '0'
)
AGGREGATE KEY(event_time, channel, user, is_anonymous, is_minor, is_new, is_robot, is_unpatrolled)
PARTITION BY RANGE(event_time)
(
    PARTITION p06 VALUES LESS THAN ('2015-09-12 06:00:00'),
    PARTITION p12 VALUES LESS THAN ('2015-09-12 12:00:00'),
    PARTITION p18 VALUES LESS THAN ('2015-09-12 18:00:00'),
    PARTITION p24 VALUES LESS THAN ('2015-09-13 00:00:00')
)
DISTRIBUTED BY HASH(user) BUCKETS 3;


CREATE TABLE source_wiki_edit
(
    event_time DATETIME,
    channel VARCHAR(32) DEFAULT '',
    user VARCHAR(128) DEFAULT '',
    is_anonymous TINYINT DEFAULT '0',
    is_minor TINYINT DEFAULT '0',
    is_new TINYINT DEFAULT '0',
    is_robot TINYINT DEFAULT '0',
    is_unpatrolled TINYINT DEFAULT '0',
    delta INT SUM DEFAULT '0',
    added INT SUM DEFAULT '0',
    deleted INT SUM DEFAULT '0'
)
AGGREGATE KEY(event_time, channel, user, is_anonymous, is_minor, is_new, is_robot, is_unpatrolled)
PARTITION BY RANGE(event_time)
(
    PARTITION p06 VALUES LESS THAN ('2015-09-12 06:00:00'),
    PARTITION p12 VALUES LESS THAN ('2015-09-12 12:00:00'),
    PARTITION p18 VALUES LESS THAN ('2015-09-12 18:00:00'),
    PARTITION p24 VALUES LESS THAN ('2015-09-13 00:00:00')
)
DISTRIBUTED BY HASH(user) BUCKETS 3;
```



### 通过 INSERT INTO VALUES 语句导入数据

> 注意
>
> INSERT INTO VALUES 语句导入方式仅适用于导入少量数据作为验证 DEMO 用途，不适用于大规模测试或生产环境。如需大规模导入数据，请选择其他导入方式。



以下示例以 `insert_load_wikipedia` 为 Label 向源表 `source_wiki_edit` 中导入两条数据。
```sql
INSERT INTO source_wiki_edit
WITH LABEL insert_load_wikipedia
VALUES
    ("2015-09-12 00:00:00","#en.wikipedia","AustinFF",0,0,0,0,0,21,5,0),
    ("2015-09-12 00:00:00","#ca.wikipedia","helloSR",0,1,0,1,0,3,23,0);
```



### 通过 INSERT INTO SELECT 语句导入数据

将源表中的数据导入至目标表中。详细使用方式，参考 [INSERT](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-manipulation/insert)。详细参数信息，参考 [INSERT 参数](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-manipulation/insert#参数说明)。

- 以下示例以 `insert_load_wikipedia_1` 为 Label 将源表中的数据导入至目标表中。

```sql
INSERT INTO insert_wiki_edit
WITH LABEL insert_load_wikipedia_1
SELECT * FROM source_wiki_edit;
```

- 以下示例以 `insert_load_wikipedia_2` 为 Label 将源表中的数据导入至目标表的 `p06` 和 `p12` 分区中。

```sql
INSERT INTO insert_wiki_edit PARTITION(p06, p12)
WITH LABEL insert_load_wikipedia_2
SELECT * FROM source_wiki_edit;
```

- 以下示例将源表中指定列的数据导入至目标表中。

```sql
INSERT INTO insert_wiki_edit
    WITH LABEL insert_load_wikipedia_3 (event_time, channel)
    SELECT event_time, channel FROM routine_wiki_edit;
```



## 通过 HTTP PUT 从本地文件系统或流式数据源导入数据

### 概念

- HTTP 协议、Stream Load 导入方式
  - StarRocks 提供基于 **HTTP** 协议的 **Stream Load** 导入方式，帮助您从**本地文件系统或流式数据源**导入数据。
- 同步导入方式
  - 提交导入作业以后，StarRocks 会同步地执行导入作业，并返回导入作业的结果信息。可以通过返回的结果信息来判断导入作业是否成功。
- 适用业务场景
  - 导入本地数据文件：采用 curl 命令直接提交一个导入作业，将本地数据文件的数据导入到 StarRocks 中。
  - 导入实时产生的数据流：一般可采用 Apache Flink® 等程序提交一个导入作业，持续生成一系列导入任务，将实时产生的数据流持续不断地导入到 StarRocks 中。
- 支持的数据文件格式
  - CSV
  - JSON
- 基本原理
  - Stream Load 需要您在客户端上通过 HTTP 发送导入作业请求给 FE 节点，FE 节点会通过 HTTP 重定向 (Redirect) 指令将请求转发给某一个 BE 节点。

![4.2-1](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/4.2-1.png)

### 导入本地文件

#### 创建导入作业

有关创建导入作业的详细语法和参数说明，请参见 [STREAM LOAD](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-manipulation/STREAM LOAD)。

##### 导入 CSV 格式的数据
- 建表
```sql
MySQL [test_db]> CREATE TABLE `table1`
(
    `id` int(11) NOT NULL COMMENT "用户 ID",
    `name` varchar(65533) NULL COMMENT "用户姓名",
    `score` int(11) NOT NULL COMMENT "用户得分"
)
ENGINE=OLAP
PRIMARY KEY(`id`)
DISTRIBUTED BY HASH(`id`) BUCKETS 10;
```
- 准备数据
```bash
1,Lily,23
2,Rose,23
3,Alice,24
4,Julia,25
```
- 执行导入
```bash
curl --location-trusted -u root: -H "label:123" \
    -H "column_separator:," \
    -H "columns: id, name, score" \
    -T example1.csv -XPUT \
    http://<fe_host>:<fe_http_port>/api/test_db/table1/_stream_load
```
- 查询
```sql
MySQL [test_db]> SELECT * FROM table1;

+------+-------+-------+
| id   | name  | score |
+------+-------+-------+
|    1 | Lily  |    23 |
|    2 | Rose  |    23 |
|    3 | Alice |    24 |
|    4 | Julia |    25 |
+------+-------+-------+

4 rows in set (0.00 sec)
```

##### 导入 JSON 格式的数据

- 建表（主键模型表）

```sql
MySQL [test_db]> CREATE TABLE `table2`
(
    `id` int(11) NOT NULL COMMENT "城市 ID",
    `city` varchar(65533) NULL COMMENT "城市名称"
)
ENGINE=OLAP
PRIMARY KEY(`id`)
DISTRIBUTED BY HASH(`id`) BUCKETS 10;
```

- 准备数据

```json
{"name": "北京", "code": 2}
```

- 执行导入

```bash
curl -v --location-trusted -u root: -H "strict_mode: true" \
    -H "format: json" -H "jsonpaths: [\"$.name\", \"$.code\"]" \
    -H "columns: city,tmp_id, id = tmp_id * 100" \
    -T example2.json -XPUT \
    http://<fe_host>:<fe_http_port>/api/test_db/table2/_stream_load
```



![4.2-2](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/4.2-2.png)


> 说明：上述示例中，在导入过程中先将 `example2.json` 文件中 `code` 字段对应的值乘以 100，然后再落入到 `table2` 表的 `id` 中。

- 查询

#### 查看导入作业
因为是同步导入，所以导入作业结束后，StarRocks 会以 JSON 格式返回本次导入作业的结果信息。

#### 取消导入作业
不支持手动取消导入作业。如果导入作业发生超时或者导入错误，StarRocks 会自动取消该作业。



### 导入数据流

Stream Load 支持通过程序导入数据流，具体操作方法，请参见如下文档：
- Flink 集成 Stream Load，请参见[使用 flink-connector-starrocks 导入至 StarRocks](https://docs.starrocks.io/zh-cn/latest/loading/Flink-connector-starrocks)。
- Java 集成 Stream Load，请参见 [https://github.com/StarRocks/demo/MiscDemo/stream_load](https://github.com/StarRocks/demo/tree/master/MiscDemo/stream_load)。
- Apache Spark™ 集成 Stream Load，请参见 [01_sparkStreaming2StarRocks](https://github.com/StarRocks/demo/blob/master/docs/01_sparkStreaming2StarRocks.md)。



### 参数配置

- `streaming_load_max_mb`：单个待导入数据文件的大小上限。默认文件大小上限为 10 GB。具体请参见 [BE 配置项](https://docs.starrocks.io/zh-cn/latest/administration/Configuration#be-配置项)。
- `stream_load_default_timeout_second`：导入作业的超时时间。默认超时时间为 600 秒。具体请参见 [FE 动态参数](https://docs.starrocks.io/zh-cn/latest/administration/Configuration#配置-fe-动态参数)。



### 使用说明

- 如果待导入数据文件中某行数据的某个字段缺失、并且 StarRocks 表中跟该字段对应的列定义为 `NOT NULL`，StarRocks 会在导入该行数据时自动往 StarRocks 表中对应的列补充 `NULL`。您也可以通过 `ifnull()` 函数指定要补充的默认值。
- 例如，如果上述 `example2.json` 文件中代表城市 ID 的列缺失，您希望 StarRocks 在导入数据时往 StarRocks 表中对应的列中补充 `x`，可以指定 `"columns: city, tmp_id, id = ifnull(tmp_id, 'x')"`。



## 从 Apache Kafka® 持续导入

向StarRocks 提交一个 Routine Load 导入作业 `example_tbl1_ordertest1`，持续消费 Kafka 集群中 Topic `ordertest1` 的消息，并导入至数据库 `example_db` 的表 `example_tbl1` 中。并且导入作业会从该 Topic 所指定分区的最早位点 (Offset) 开始消费。

- 建表

```sql
CREATE TABLE example_db.example_tbl1 ( 
    `order_id` bigint NOT NULL COMMENT "订单编号",
    `incr` bigint NOT NULL COMMENT "日增长", 
    `price` bigint NOT NULL COMMENT "单价", 
    `customer_name1` varchar(26) NULL COMMENT "顾客姓名1", 
    `customer_name2` varchar(26) NULL COMMENT "顾客姓名2"
) 
ENGINE=OLAP 
PRIMARY KEY (order_id) 
DISTRIBUTED BY HASH(`order_id`) BUCKETS 5; 
```

- topic

```sh
./kafka-topics --zookeeper 10.11.14.17: 2181/kafka --create --topic topic-demo --replication-factor 3 --partitions 4

./kafka-topics --describe --zookeeper 10.11.14.17:2181 --topic topic-demo2

./kafka-console-producer --broker-list 10.11.14.19:9092,10.11.14.20:9092,10.11.14.21:9092 --topic topic-demo22 < ./b.csv
```

- 查看

```sql
SHOW ROUTINE LOAD\G;

```







```sql
CREATE ROUTINE LOAD example_db.example_tbl1_ordertest1 ON example_tbl1
COLUMNS TERMINATED BY ",",
COLUMNS (order_id, pay_dt, customer_name, nationality, temp_gender, price)
PROPERTIES
(
    "desired_concurrent_number" = "5"
)
FROM KAFKA
(
    "kafka_broker_list" ="<kafka_broker1_ip>:<kafka_broker1_port>,<kafka_broker2_ip>:<kafka_broker2_port>",
    "kafka_topic" = "ordertest1",
    "kafka_partitions" ="0,1,2,3,4",
    "property.kafka_default_offsets" = "OFFSET_BEGINNING"
);
```



```sh
CREATE ROUTINE LOAD example_db.example_tbl2_ordertest1 ON example_tbl1
COLUMNS TERMINATED BY ",",
COLUMNS (order_id, incr, price, customer_name1, customer_name2)
PROPERTIES
(
    "desired_concurrent_number" = "3"
)
FROM KAFKA
(
    "kafka_broker_list" ="10.11.14.19:9092,10.11.14.20:9092,10.11.14.21:9092",
    "kafka_topic" = " topic-demo",
    "kafka_partitions" ="0,1,2,3",
    "property.kafka_default_offsets" = "OFFSET_BEGINNING"
);
```



查看导入作业执行情况：SHOW ROUTINE LOAD







## 使用 Apache Spark™ 批量导入

> ![Spark与Flink究竟哪家强？](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/v2-f0610f026422d3ab5bc784b60f3b2e38_720w.jpg)
>
> Apache Spark 和 Apache Flink是两个最流行的数据处理框架。Spark 与 Flink都支持大规模分布式数据处理，并提供对前几代框架的改进。从成熟度来说 Spark 生态更完善，Flink 比较新，而且包含 Spark不具备的功能。
> 更多内容：111





## 从 Apache Flink® 持续导入

- 功能简介
  - StarRocks 提供 `flink-connector-starrocks`，导入数据至 StarRocks，相比于 `Flink` 官方提供的 `flink-connector-jdbc`，导入性能更佳。 flink-connector-starrocks 的内部实现是通过缓存并批量由 `Stream Load` 导入。
- 支持的数据源
  - CSV
  - JSON

- 源码地址：https://github.com/StarRocks/starrocks-connector-for-apache-flink

- 实践









# 数据导出

可以使用该功能将指定表或分区上的数据，以 **CSV** 的格式，通过 **Broker** 程序导出到外部云存储系统，如 HDFS、阿里云 OSS、AWS S3、或其他兼容 S3 协议的对象存储服务。

- 前提：部署 Broker 节点
- 注意事项
  - 建议不要一次性导出大量数据。一个导出作业建议的导出数据量最大为几十 GB。一次性导出过量数据可能会导致导出失败，重试的成本也会增加。
  - 如果表数据量过大，建议按照分区导出。
- 流程

![](https://docs.starrocks.io/static/bc95e13b8351e1109a1785ade5d0143b/c1b63/5.1-2.png)

导出作业的总体处理流程主要包括以下三个步骤：

1. 用户**提交**一个导出作业到 Leader FE。
2. Leader FE 会先向集群中所有的 BE 发送 `snapshot` 命令，对所有涉及到的 Tablet 做一个**快照**，以保持导出数据的一致性，**并生成多个导出子任务**。每个子任务即为一个查询计划，每个查询计划会负责处理一部分 Tablet。
3. Leader FE 会把**一个个导出子任务发送**给 BE 执行。

- 基本原理
- 相关配置
  - FE 参数配置
  - Broker 参数配置

- 实际操作 `EXPORT`
  - 把 `db1` 数据库中 `tbl1` 表在 `p1` 和 `p2` 分区上 `col1` 和 `col3` 两列的数据导出到 HDFS 存储上的 `export` 目录中

```sql
EXPORT TABLE db1.tbl1 

PARTITION (p1,p2)

(col1, col3)

TO "hdfs://HDFS_IP:HDFS_Port/export/lineorder_" 

PROPERTIES

(
    "column_separator"=",",
    "load_mem_limit"="2147483648",
    "timeout" = "3600"
)

WITH BROKER "broker1"

(
    "username" = "user",
    "password" = "passwd"
);
```



- 获取导出作业的查询 ID、查看导出作业的状态

```sql
SELECT LAST_QUERY_ID() 
SHOW EXPORT WHERE queryid = "edee47f0-abe1-11ec-b9d1-00163e1e238f";

-- 取消
CANCEL EXPORT WHERE queryid = "921d8f80-7c9d-11eb-9342-acde48001122";
```

- 最佳实践



# 参考手册

### SQL 参考

#### DDL

#### DML

##### Broker Load

```sql
LOAD LABEL [<database_name>.]<label_name>
(
    data_desc[, data_desc ...]
)
WITH BROKER "<broker_name>"
[broker_properties]
[opt_properties];
```

- LABEL：指定导入作业的标签。
  - 每个导入作业都对应一个在该数据库内唯一的标签。通过标签，可以查看对应导入作业的执行情况，并防止导入相同的数据。导入作业的状态为 **FINISHED** 时，其标签不可再复用给其他导入作业。导入作业的状态为 **CANCELLED** 时，其标签可以复用给其他导入作业。
  - 命名规范，请参见[系统限制](https://docs.starrocks.io/zh-cn/2.3/reference/System_limit)。
- data_desc：用于描述一批次待导入的数据。声明了本批次待导入数据所属的数据源地址、ETL 函数、StarRocks 表和分区等信息。
  - Broker Load 支持一次导入多个数据文件。还支持保证单次导入事务的原子性。
  - data_desc 中的必选参数如下：file_path（待导入数据文件所在的路径）、INTO TABLE（指定目标 StarRocks 表的名称）
  - data_desc 中的可选参数如下：NEGATIVE（撤销某一批已经成功导入的数据）、PARTITION（指定要把数据导入哪些分区，默认所有分区）、FORMAT AS（指定待导入数据文件的格式。取值包括 `CSV`、`Parquet` 和 `ORC`）、COLUMNS TERMINATED BY（指定待导入数据文件中的列分隔符）、column_list（指定待导入数据文件和 StarRocks 表之间的列对应关系）、COLUMNS FROM PATH AS（指定的文件路径中提取一个或多个分区字段的信息）、SET（将待导入数据文件的某一列按照指定的函数进行转化，然后将转化后的结果落入 StarRocks 表中）、WHERE（指定过滤条件，对做完转换的数据进行过滤）
- WITH BROKER：用于指定 Broker 的名称
- broker_properties：提供通过 Broker 访问的数据源的信息
- opt_properties：指定一些导入相关的可选参数
  - timeout（导入作业的超时时间）、max_filter_ratio（导入作业的最大容忍率）、load_mem_limit（导入作业的内存限制）、strict_mode（是否开启严格模式）、timezone（导入作业所使用的时区）







##### INSERT

```sql
INSERT INTO table_name
[ PARTITION (p1, ...) ]
[ WITH LABEL label]
[ (column [, ...]) ]
{ VALUES ( { expression | DEFAULT } [, ...] ) [, ...] | query }
```



| 参数        | 说明                                                         |
| :---------- | :----------------------------------------------------------- |
| table_name  | 导入数据的目标表。可以为 `db_name.table_name` 形式。         |
| partitions  | 导入的目标分区。此参数必须是目标表中存在的分区，多个分区名称用逗号（,）分隔。如果指定该参数，数据只会被导入相应分区内。如果未指定，则默认将数据导入至目标表的所有分区。 |
| label       | 导入作业的标识，数据库内唯一。如果未指定，StarRocks 会自动为作业生成一个 Label。建议您指定 Label。否则，如果当前导入作业因网络错误无法返回结果，您将无法得知该导入操作是否成功。如果指定了 Label，可以通过 SQL 命令 `SHOW LOAD WHERE label="label"` 查看任务结果。关于 Label 命名限制，参考[系统限制](https://docs.starrocks.io/zh-cn/2.3/reference/System_limit) |
| column_name | 导入的目标列，必须是目标表中存在的列。该参数的对应关系与列名无关，但与其顺序一一对应。如果不指定目标列，默认为目标表中的所有列。如果源表中的某个列在目标列不存在，则写入默认值。如果当前列没有默认值，导入作业会失败。如果查询语句的结果列类型与目标列的类型不一致，会进行隐式转化，如果不能进行转化，那么 INSERT INTO 语句会报语法解析错误。 |
| expression  | 表达式，用以为对应列赋值。                                   |
| DEFAULT     | 为对应列赋予默认值。                                         |
| query       | 查询语句，查询的结果会导入至目标表中。查询语句支持任意 StarRocks 支持的 SQL 查询语法。 |



##### SHOW LOAD

查看数据库中指定导入作业的相关信息，包括 Broker Load、Spark Load 和 INSERT。

Stream Load 是同步操作，会直接返回结果，不会通过 SHOW LOAD 展示。

Routine Load 可通过 [SHOW ROUTINE LOAD](https://docs.starrocks.io/zh-cn/2.3/sql-reference/sql-statements/data-manipulation/SHOW ROUTINE LOAD) 查看导入作业的相关信息。







### 函数参考

#### 窗口函数

#### 日期函数

#### 加密函数



## 系统限制

- StarRocks 采用 MySQL 协议进行通信，用户可通过 MySQL Client 或者 JDBC 连接到 StarRocks 集群。
- 建表时，Key列不能使用 FLOAT 或者 DOUBLE 类型，可用 DECIMAL 类型表示小数。
- StarRocks 不支持修改表中的列名。
- StarRocks 仅支持 UTF8 编码，不支持 GB 等编码。







# Q&A

- Insert 方式 label 作用
  - 建议您指定 Label。**否则，如果当前导入作业因网络错误无法返回结果，您将无法得知该导入操作是否成功。**如果指定了 Label，可以通过 SQL 命令 `SHOW LOAD WHERE label="label"` 查看任务结果。

- Kafka 指定部分列
  - 根据 CSV 数据中需要导入的几列（例如除第五列性别外的其余五列需要导入至 StarRocks）， 在 StarRocks 集群的目标数据库 `example_db` 中创建表 `example_tbl1`。
  - [导入过程中实现数据转换 @ Etl_in_loading @ StarRocks Docs](https://docs.starrocks.io/zh-cn/2.3/loading/Etl_in_loading)
    - 跳过不需要导入的列：一方面，该功能使您可以**跳过不需要导入的列**；另一方面，当 StarRocks 表与待导入数据文件的列顺序不一致时，您可以通过该功能建立两者之间的**列映射关系**。
    - 过滤掉不需要导入的列：在导入时，您可以通过指定过滤条件，跳过不需要导入的行，只导入必要的行。
    - 生成衍生列：将计算后产生的新列落入到 StarRocks 表中。

- 通常情况下，CSV 文件中待导入数据文件中的列，是没有命名的。有些 CSV 文件中，会在首行给出列名，但其实 StarRocks 仍然是不感知的，会当做普通数据处理。因此，在导入 CSV 格式的数据时，您需要在导入命令或者语句中对待导入数据文件中的列**按顺序**依次临时命名。这些临时命名的列，会和 StarRocks 表中的列**按名称**进行对应。这里需要注意以下几点：

  - 待导入数据文件中与 StarRocks 表中都存在、且命名相同的列，其数据会直接导入。

  - 待导入数据文件中存在、但是 StarRocks 表中不存在的列，其数据会在导入过程中忽略掉。

  - 如果有 StarRocks 表中存在、但是未声明的列，会报错。





## 1130 版权协议问题

- 相关资料

对 StarRocks 真假开源之争的灵魂九问：https://zhuanlan.zhihu.com/p/408727834

StarRocks 昨日回应：关于 StarRocks 相关疑问的解答：https://developer.aliyun.com/article/805832

- 内容整理

一问：DorisDB 品牌名称是否侵犯 Apache 软件基金会权益，更名 StarRocks 背后的真实原因？

客观事实：StarRocks 公众号注册于 2020 年 07 月 29 日，注册名为 DorisDB，并在 2021-09-08 更名并认证为 StarRocks。

Apache Doris态度：曾多次直接或间接劝阻鼎石公司、联系其投资人加以劝阻无果。



二问：StarRocks 是 Fork Apache Doris 的 “山寨 ”项目吗？

项目声明中也明确表示了其项目源自于 Apache Doris。



三问：鼎石公司对 Apache Doris 的贡献到底有几成？

鼎石公司已经完全脱离了对上游项目的贡献，只维护其 Fork 的版本和商业私有化版本。



四问：让公众误会其为 Apache Doris 捐赠方或核心贡献者，是否存在刻意误导？

观点：从来不批判商业软件，不从道德层面批判你是否参与、贡献开源，也并不反对任何公司基于基金会的任何开源项目去做商业化，批判的是**在这个过程中故意模糊概念、不实事求是、违背开源精神的行为**。



五问：真真假假，StarRocks 开源了吗？

**StarRocks 目前所谓的“开源”采用了 Elastic License 2.0**。但问题是，这并非 OSI 认证的开源 License。

StarRocks 表示，Elastic 等开源软件公司会更倾向于和那些为开源社区做出贡献的公司合作。他们采用 Elastic License 2.0 作为开源许可证，可以确保用户可以继续免费使用、修改和分发 StarRocks 的源代码，同时保护 StarRocks 的版权和商标。



六问：高调更名，是碰瓷营销，还是对开源文化和知识产权的践踏？

Apache Doris：或低调更名，或诚意道歉，这才是 StarRocks 该有的态度。

StarRocks：已完成



七问：捆绑“大佬”站台营销，真实情况大佬们知道吗？

包括**阿里云计算平台负责人贾扬清、腾讯云大数据产品总经理聂晶**等大佬在内，来自阿里巴巴、腾讯、京东、顺丰、小米、携程、小红书、58 同城等公司的多位工程师在文内通过视频的形式为 StarRocks 站台背书，并表示愿意加入 StarRocks 社区进行贡献。

> - DorisDB 的各种侵权行为，大佬们知情吗？
> - DorisDB 的相关负责人有否说明实情，还是模糊了事实？
> - 如果事态持续发展会不会影响到大佬们的个人信誉？
> - 在找好友帮忙，还是在为好友挖坑？



八问：性能是会否成为数据库商业公司的核心优势？

如果在竞争中，性能是你唯一的杀手锏，最终你一定会被对方干掉，因为**所有的性能、指标都是对方可以通过更好的创新去超越的，它并不会带来碾压级的优势。**



九问：to VC？什么样的商业软件公司值得被看好？

合法合规、知识产权清晰、有客户愿意买单。至于开源还是闭源，其实并不重要。



官方回复：

肯定关联：在这13年里，StarRocks的小伙伴也曾呕心沥血，为Apache Doris贡献了数不清的时间和精力。如果没有Apache Doris项目，就没有今天的StarRocks。同样，没有这些小伙伴，也不会有今天的Apache Doris。

已兑现承诺：2021二季度，Apache基金会的朋友正式和我们讨论此事，虽然我们所有的行为均合法合规，但是为了不影响Apache Doris项目，我们承诺今年三季度会将问题解决。如今，我们已经兑现了承诺。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221201165555611.png)



众所周知，一个分析型数据库有三大件：优化器，执行器，存储引擎。当前我们已经从零开始，重新打造了两个大的组件：优化器和执行器，并完全抛弃了原有的按行做计算的内核。我们最近将发布新版本的实时列存引擎，对原有的列存引擎也进行了重大重构。

**所以，StarRocks虽然使用了 Apache Doris 的框架，但是其内核已经是完全独立自主研发的了。**当前项目中原有的Apache Doris 代码保留了原有的 Apache Licence 2。只有完全由我们独立开发的代码，采用了Elastic License 2。这也是行业的常规做法。



所以，**您完全可以像使用ElasticSearch那样放心使用我们的产品！**



今天，StarRocks的目标已经升级为”新一代流批融合的极速湖仓(Lakehouse)“。这和Apache Doris的项目的目标并不一致。这也是我们为什么没有把代码合并回Apache Doris项目的原因之一。时代像风，数据如海。在这样一个伟大的时代，StarRocks希望能和各方携手，专注于客户的需求，一起打造一个数据分析的新时代！



- 得出结论





##  1130 Spark来快速读取StarRocks中存储的数据

https://blog.csdn.net/ult_me/article/details/123213217

通过 Spark Connector，我们可以使用 Spark 来快速读取StarRocks中存储的数据。Spark Connector在使用上和咱们之前介绍的Flink Connector Source类似，二者都具备**并行读取StarRocks的BE节点数据的能力，相对于jdbc的方式大大提高了数据读取效率**。











# 性能测试

## 环境准备

- 集群如下

|        | StarRocks                             | Clickhouse          |
| ------ | ------------------------------------- | ------------------- |
| 服务器 | 10.11.14.15、10.11.14.16、10.11.14.13 | 10.11.14.16、17、18 |
| 配置   | 8/32、4/16、8/32                      | 4/16、8/32、8/32    |



## 数据准备

- 数据来源

  - 10.11.14.10   root  smc@z9w6
  - carbon_mileage_single
  - 数据量：2601948条、429MB

- 数据导入

  - 原有数据转 csv 格式
  - 导入 StarRocks（已完成）

  ```sql
  CREATE TABLE `carbon_mileage_single` (
    `id` int(11) NOT NULL COMMENT "自增主键",
    `vin` varchar(60) NULL COMMENT "车架号",
    `province` varchar(60) NULL COMMENT "注册省份",
    `city` varchar(60) NULL COMMENT "注册地区",
    `data_time` varchar(60) NULL COMMENT "日期",
    `veh_category_2` varchar(60) NULL COMMENT "车辆细分用途",
    `unit_name` varchar(60) NULL COMMENT "生产厂家",
    `un_name` varchar(60) NULL COMMENT "运营单位",
    `drivemode` varchar(60) NULL COMMENT "传动模式",
    `kmday` double NULL COMMENT "日行驶里程Km",
    `onlinekmsum` double NULL COMMENT "上线至今行驶里程Km",
    `carbonsumday` double NULL COMMENT "日新增碳减排Kg",
    `carbonday` double NULL COMMENT "日新增碳排放Kg",
    `onlinecarbonsum` double NULL COMMENT "上线至今碳减排Kg",
    `onlinecarbon` double NULL COMMENT "上线至今碳排放Kg"
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
  curl --location-trusted -u root: -H "label:20221125" \
      -H "column_separator:," \
      -H "columns: id, vin, province, city, data_time, veh_category_2, unit_name, un_name, drivemode, kmday, onlinekmsum,carbonsumday, carbonday, onlinecarbonsum, onlinecarbon" \
      -T carbon_mileage_single.csv -XPUT \
      http://10.11.14.15:8030/api/example_db/carbon_mileage_single/_stream_load;
  ```

  

  - 导入 Clickhouse（已完成）

  ```sql
  -- 建库
  CREATE DATABASE if not exists clickhouse_test ENGINE = Atomic;
  
  -- 建表
  create table carbon_mileage_single
      (
      id              int  comment '自增主键',
      vin             varchar(60)                 comment '车架号',
      province        varchar(60)                 comment '注册省份',
      city            varchar(60)                 comment '注册地区',
      data_time       varchar(60)                 comment '日期',
      veh_category_2  varchar(60)                 comment '车辆细分用途',
      unit_name       varchar(60)                 comment '生产厂家',
      un_name         varchar(60)                 comment '运营单位',
      drivemode       varchar(60)                 comment '传动模式',
      kmday           double              comment '日行驶里程Km',
      onlinekmsum     double default 0.00  comment '上线至今行驶里程Km',
      carbonsumday    double               comment '日新增碳减排Kg',
      carbonday       double               comment '日新增碳排放Kg',
      onlinecarbonsum double               comment '上线至今碳减排Kg',
      onlinecarbon    double               comment '上线至今碳排放Kg'
      )
      engine = MergeTree
          order by id;
          
  -- 导入数据
  DataGrip
  ```

  查看当前节点所属集群的相关信息：`select * from system.clusters;`

  

  ![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20221122141441819.png)



## SQL 场景 15个

1. 单表 根据主键 id 查询

- StarRocks 主键模型：17ms、Clickhouse：107ms
- StarRocks 更新模型：34

```sql
SELECT id, vin, province, city, data_time, veh_category_2, unit_name, un_name, drivemode, kmday, onlinekmsum, carbonsumday, carbonday, onlinecarbonsum,
onlinecarbon 
FROM carbon_mileage_single
WHERE id = 150;
```




2. 单表

- 42、189
- 89

```sql
SELECT sum(carbonsumday * kmday) AS carbonsum
FROM carbon_mileage_single
WHERE onlinecarbonsum >= 100 AND onlinecarbon <= 1000 AND kmday < 100;
```


3. 单表

- 40、658
- 103

```sql
SELECT kmday, onlinekmsum, carbonsumday, carbonday, onlinecarbonsum, onlinecarbon
FROM carbon_mileage_single
WHERE onlinecarbonsum >= 100 AND onlinecarbon <= 1000 AND kmday BETWEEN 12 AND 100;
```


4. 单表

- 33、259
- 71

```sql
SELECT sum(carbonsumday * kmday) AS carbonsum, drivemode, year(data_time) AS year
FROM carbon_mileage_single 
WHERE veh_category_2 = '出租乘用车' AND unit_name = '东风汽车公司'
GROUP BY year,drivemode
ORDER BY year,drivemode;
```


5. 单表

- 50、448
- 73

```sql
SELECT unit_name, un_name
FROM carbon_mileage_single
WHERE data_time >= '20221026' AND data_time<= '20221027'
GROUP BY unit_name, un_name;
```


6. 单表

- 33、171
- 69

```sql
SELECT unit_name, un_name
FROM carbon_mileage_single
WHERE unit_name in ('一汽-大众汽车有限公司', '上汽大众汽车有限公司')
AND data_time >= '20221026' AND data_time<= '20221031'
GROUP BY unit_name, un_name
;

SELECT year(data_time) AS year, unit_name, un_name 
FROM carbon_mileage_single
WHERE unit_name in ('一汽-大众汽车有限公司', '上汽大众汽车有限公司')
AND data_time >= '20221026' AND data_time<= '20221031'
GROUP BY unit_name, un_name, year
ORDER BY year DESC
;
```


7. 单表

- 37、161
- 76

```sql
SELECT year(data_time) AS year, unit_name, un_name, SUM(onlinekmsum - onlinecarbon) AS profit
FROM carbon_mileage_single
WHERE unit_name in ('一汽-大众汽车有限公司', '上汽大众汽车有限公司')
AND data_time >= '20221026' AND data_time<= '20221031'
GROUP BY unit_name, un_name, year
ORDER BY year ASC, unit_name ASC, un_name ASC
;
```


8. 单表

- 32,29,155,45、144,145,130,146
- 28，31，46，52

```sql
select count(*),data_time from carbon_mileage_single group by data_time;

select count(DISTINCT data_time) from carbon_mileage_single;

select count(*),data_time, city from carbon_mileage_single group by data_time, city;

select count(*) from carbon_mileage_single group by data_time, city;
```


9. 单表

- 90、221
- 67

```sql
select
  province,
  city,
  sum(kmday) as sum_kmday,
  sum(onlinekmsum) as sum_onlinekmsum,
  avg(carbonsumday) as carbonsumday,
  avg(carbonday) as carbonday,
  count(*) as count
from carbon_mileage_single
where  data_time <= '20221201'
group by  province,  city
order by  province,  city;
```



10. 多表

- 49、264
- 53

```sql
select vin, province,  city,  data_time,  veh_category_2, unit_name,
       idnumber,  diastolicPressure,  pluseVal
from  carbon_mileage_single AS A JOIN healthinforecords AS B
on A.id=B.id
where
  province = '山东省'
  and unit_name like '%比亚迪%'
order by  data_time desc, unit_name
LIMIT 0,10;

select vin, province,  city,  data_time,  veh_category_2, unit_name,
       C2,  C3,  C5
from  carbon_mileage_single AS A JOIN healthinforecords AS B
on A.id=B.C1
where
  province = '山东省'
  and unit_name like '%比亚迪%'
order by  data_time desc, unit_name
LIMIT 0,10;
```



11. 多表（子查询）

- 62ms、2452ms
- 61

```sql
select
  province, sum(onlinekmsum * carbonsumday) as revenue, veh_category_2
from
  carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.id
where
  un_name = '私人'
  and data_time <  '20221026'
  and kmday < (select diastolicPressure from healthinforecords where id = 441)
group by province, veh_category_2
order by revenue desc, province;


select
  province, sum(onlinekmsum * carbonsumday) as revenue, veh_category_2
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.C1
where
  un_name = '私人'
  and data_time <  '20221026'
  and kmday < (select C3 from healthinforecords where C1 = 441)
group by province, veh_category_2
order by revenue desc, province;
```



12. 多表（子查询）

- 85ms、264ms
- 68

```sql
select
  province, sum(onlinekmsum * carbonsumday) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.id
where
  un_name like '私人%'
  and data_time <  '20221026'
  and kmday < (select diastolicPressure from healthinforecords where id = 441)
group by province, veh_category_2
order by revenue desc, province;


select
  province, sum(onlinekmsum * carbonsumday) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.C1
where
  un_name like '私人%'
  and data_time <  '20221026'
  and kmday < (select C3 from healthinforecords where C1 = 441)
group by province, veh_category_2
order by revenue desc, province;
```

13. 多表

- 51、225
- 46

```sql
select
  province, sum(onlinekmsum) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.id
where
  kmday in (select diastolicPressure from healthinforecords group by diastolicPressure having sum(systolicPressure) > 150)
  and data_time <  '20221026'
group by province, veh_category_2
order by revenue desc, province;

select
  province, sum(onlinekmsum) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.C1
where
  kmday in (select C3 from healthinforecords group by C3 having sum(C4) > 150)
  and data_time <  '20221026'
group by province, veh_category_2
order by revenue desc, province;
```



14. 多表

- 44、258
- 46

```sql
select
  province, veh_category_2
from
    carbon_mileage_single JOIN healthinforecords
	on carbon_mileage_single.id=healthinforecords.id
where
  kmday in (
        select (temperatureVal-breathingRate) FROM healthinforecords where id <= 1000
  )
  and drivemode = '纯电动汽车(BEV)'
order by province;


select
  province, veh_category_2
from
    carbon_mileage_single JOIN healthinforecords
	on carbon_mileage_single.id=healthinforecords.C1
where
  kmday in (
        select (C6-C7) FROM healthinforecords where C1 <= 1000
  )
  and drivemode = '纯电动汽车(BEV)'
order by province;
```



15. 多表

- 50、229
- 45

```sql
select
  province, sum(onlinekmsum) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.id
where
  kmday in (select
                diastolicPressure from healthinforecords
                   where id > 4000
                   group by diastolicPressure
                   having sum(systolicPressure) > 150)
  and data_time <  '20221026'
group by
    province, veh_category_2
order by
    revenue desc, province;
    
    
select
  province, sum(onlinekmsum) as revenue, veh_category_2, count(*) as custdist
from
    carbon_mileage_single AS A JOIN healthinforecords AS B
	on A.id=B.C1
where
  kmday in (select
                C3 from healthinforecords
                   where C1 > 4000
                   group by C3
                   having sum(C4) > 150)
  and data_time <  '20221026'
group by
    province, veh_category_2
order by
    revenue desc, province;
```



更新模型建表 SQL

```sql
CREATE TABLE `carbon_mileage_single` (
  `id` int(11) NOT NULL COMMENT "自增主键",
  `vin` varchar(60) NULL COMMENT "车架号",
  `province` varchar(60) NULL COMMENT "注册省份",
  `city` varchar(60) NULL COMMENT "注册地区",
  `data_time` varchar(60) NULL COMMENT "日期",
  `veh_category_2` varchar(60) NULL COMMENT "车辆细分用途",
  `unit_name` varchar(60) NULL COMMENT "生产厂家",
  `un_name` varchar(60) NULL COMMENT "运营单位",
  `drivemode` varchar(60) NULL COMMENT "传动模式",
  `kmday` double NULL COMMENT "日行驶里程Km",
  `onlinekmsum` double NULL COMMENT "上线至今行驶里程Km",
  `carbonsumday` double NULL COMMENT "日新增碳减排Kg",
  `carbonday` double NULL COMMENT "日新增碳排放Kg",
  `onlinecarbonsum` double NULL COMMENT "上线至今碳减排Kg",
  `onlinecarbon` double NULL COMMENT "上线至今碳排放Kg"
) ENGINE=OLAP 
UNIQUE KEY(id)
COMMENT "OLAP"
DISTRIBUTED BY HASH(`id`) BUCKETS 10 
PROPERTIES (
"replication_num" = "3",
"in_memory" = "false",
"storage_format" = "DEFAULT",
"enable_persistent_index" = "false"
);
```



## 测试结果

| 序号 | SQL关键字                                                    | StarRocks PRIMARY KEY（ms） | Clickhouse（ms）   | Clickhouse/StarRocks PRIMARY KEY | StarRocks UNIQUE KEY（ms） |
| ---- | ------------------------------------------------------------ | --------------------------- | ------------------ | -------------------------------- | -------------------------- |
| 1    | WHERE                                                        | 17                          | 107                | 6.3                              | 34                         |
| 2    | sum()、 >=                                                   | 42                          | 189                | 4.5                              | 89                         |
| 3    | BETWEEN                                                      | 40                          | 658                | 16.4                             | 103                        |
| 4    | GROUP BY                                                     | 33                          | 259                | 7.8                              | 71                         |
| 5    | >=、GROUP BY                                                 | 50                          | 448                | 8.9                              | 73                         |
| 6    | >=、GROUP BY                                                 | 33                          | 171                | 5.1                              | 69                         |
| 7    | in、GROUP BY、ORDER BY                                       | 37                          | 161                | 4.3                              | 76                         |
| 8    | count(*)、GROUP BY                                           | 32、29、155、45             | 144、145、130、146 | 565/325=1.7                      | 28、31、46、52             |
| 9    | sum()、avg()、count(*)、group by                             | 90                          | 221                | 2.4                              | 67                         |
| 10   | JOIN on                                                      | 49                          | 264                | 2.4                              | 53                         |
| .... |                                                              |                             |                    |                                  |                            |
| 11   | JOIN on、select 子查询、group by、order by                   | 62                          | 2452               | 39                               | 61                         |
| 12   | JOIN on、select 子查询、group by、order by、count(*)         | 85                          | 264                | 3.1                              | 68                         |
| 13   | JOIN on、select 子查询、group by、order by、count(*)、having、sum | 51                          | 225                | 4.4                              | 46                         |
| 14   | JOIN on、in、select 子查询、order by                         | 44                          | 258                | 5.8                              | 46                         |
| 15   | sum、count(*)、select 子查询、group by、having、order by     | 50                          | 229                | 4.6                              | 45                         |





ClickHouse 与 StarRocks 都是很优秀的关系型 OLAP 数据库。两者有着很多的相似之处，对于分析类查询都提供了极致的性能，都不依赖于 Hadoop 生态圈。

在一些场景下，StarRocks 相较于 ClickHouse 有更好的表现。一般来说，ClickHouse 适合于维度变化较少的拼宽表的场景，StarRocks 不仅在单表的测试中有着更出色的表现，在多表关联的场景具有更大的优势。





- ⽀持多并发查询，部分场景可以达到 1 万以上 QPS；
- ⽀持 Shuffle Join，Colocate Join 等多种分布式 Join ⽅式，多表关联性能更优；
- ⽀持事务性的 DDL 与 DML 操作，兼容 MySQL 协议；
- FE、BE 架构简单，不依赖外部组件，运维更加简单；
- 数据⾃动均衡，集群随业务增⻓⽔平扩展⽅便。



## 需要完善



[ClickHouse vs StarRocks 选型对比_dan20211的博客-CSDN博客](https://blog.csdn.net/dan20211/article/details/121711042)



# 参考

Github 上：https://github.com/StarRocks/starrocks

StarRocks 官方文档：https://docs.starrocks.io/zh-cn/2.3/quick_start/Deploy

Doris 官方文档：https://doris.apache.org/zh-CN/docs/dev/summary/basic-summary

Spark 与 Flink 究竟哪家强？：https://zhuanlan.zhihu.com/p/549490227

10分钟带你全面了解StarRocks！：https://zhuanlan.zhihu.com/p/532302941

OLAP数据库：https://www.modb.pro/wiki/2279

大数据Hadoop之——DorisDB介绍与环境部署（StarRocks）：https://blog.csdn.net/qq_35745940/article/details/125580804

StarRocks开源——携手未来，星辰大海！：https://zhuanlan.zhihu.com/p/407955287

StarRocks内部实时更新技术的实现方案：https://zhuanlan.zhihu.com/p/566219916

三大数据模型：星型模型、雪花模型、星座模型：https://www.modb.pro/db/134542

超详细的Kafka架构原理图解：https://blog.csdn.net/SQY0809/article/details/117197036

CSV, JSON, AVRO,Parquet, and ORC：https://www.jianshu.com/p/9009d652ed64

ClickHouse vs StarRocks 选型对比：https://blog.csdn.net/dan20211/article/details/121711042

一起聊聊数仓大宽表：https://zhuanlan.zhihu.com/p/454600683

StarRocks4种数据模型如何在不同场景中实践：https://www.jianshu.com/p/d0cd3c67002b











