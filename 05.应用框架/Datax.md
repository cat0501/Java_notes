数据同步工具





# 核心概念

框架的核心部分2块：配置（all in configuration，json串）、通过URLClassLoader实现插件的热加载



将 `job` 进行 `task` 拆分，然后将task合并到 `taskGroup` 进行运行。

- Job: Job是DataX用以描述从一个源头到一个目的端的同步作业，是DataX数据同步的最小业务单元。比如：从一张mysql的表同步到odps的一个表的特定分区。
- TaskGroup: 描述的是一组Task集合。在同一个TaskGroupContainer执行下的Task集合称之为TaskGroup。

# 启动过程

1. 解析配置：job.json（自己的json串）、core.json（datax自带配置信息）、plugin.json（读写插件的配置信息）三个配置、设置jobId到configuration当中

2. 启动 `Engine`，通过Engine.start()进入启动程序
3. 创建并启动 `JobContainer`，包括 **init**、prepare、**split**、**scheduler**、post。
   1. init：创建reader/writer的job对象，通过URLClassLoader实现类加载。
   2. split：根据限流配置计算channel的个数，进而计算task的个数。writer的切分结果要参照reader的切分结果，达到切分后数目相等，才能满足1：1的通道模型。（channel的计数主要是根据byte和record的限速来实现的，在split()的函数中第一步就是计算channel的大小）
   3. scheduler：把上一步reader和writer split的结果整合到具体taskGroupContainer中。同时不同的执行模式调用不同的调度策略，将所有任务调度起来。







## 源码分析

main函数：解析job相关配置生成configuration、依据配置启动Engine

```java
Configuration configuration = ConfigParser.parse(jobPath);

// 加载指定的插件的配置信息，并且和全局的配置文件进行合并
configuration.merge(parsePluginConfig(new ArrayList<String>(pluginList)), false);


```





# 数据传输

Reader插件和Writer插件之间也是通过channel来实现数据的传输的。

插件通过RecordSender往channel写入数据，通过RecordReceiver从channel读取数据。

channel中的一条数据为一个Record的对象，Record中可以放多个Column对象，这可以简单理解为数据库中的记录和列。



```sh
2024-01-08 10:04:45.855 [job-0] INFO  StandAloneJobContainerCommunicator - Total 5636608 records, 1135806841 bytes | Speed 3.24MB/s, 16963 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 116.061s |  All Task WaitReaderTime 193.444s | Percentage 0.00%
2024-01-08 10:04:55.908 [job-0] INFO  StandAloneJobContainerCommunicator - Total 5818976 records, 1172218334 bytes | Speed 3.47MB/s, 18236 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 120.122s |  All Task WaitReaderTime 199.688s | Percentage 0.00%



2024-01-08 10:19:00.501 [0-0-0-reader] INFO  CommonRdbmsReader$Task - Finished read record by Sql: [SELECT * FROM bitnei_ods.ods_dcsy_nation_screen_t_veh_product_info_statistic
] jdbcUrl:[jdbc:hive2://cnbjfcysjztzk01:2181,cnbjfcysjztzk02:2181,cnbjfcysjztzk03:2181/default;password=root;serviceDiscoveryMode=zooKeeper;user=root;zooKeeperNamespace=hiveserver2&principal=hive/_HOST@SJZT.COM].
2024-01-08 10:19:01.760 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[1176142]ms
2024-01-08 10:19:01.761 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2024-01-08 10:19:06.092 [job-0] INFO  StandAloneJobContainerCommunicator - Total 21614558 records, 4540357942 bytes | Speed 3.48MB/s, 17459 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 446.948s |  All Task WaitReaderTime 718.978s | Percentage 100.00%



2024-01-08 10:19:06.098 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2024-01-08 09:59:23
任务结束时刻                    : 2024-01-08 10:19:06
任务总计耗时                    :               1182s
任务平均流量                    :            3.67MB/s
记录写入速度                    :          18317rec/s
读出记录总数                    :            21614558
读写失败总数                    :                   0
```





![](.\img\datax_chuanshu.png)







# 参考

执行流程分析：https://www.cnblogs.com/davidwang456/articles/14156680.html