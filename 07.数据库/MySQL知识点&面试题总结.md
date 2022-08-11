





## MySQL基础

### 关系型数据库介绍

顾名思义，关系型数据库就是一种建立在关系模型的基础上的数据库。关系模型表明了数据库中所存储的数据之间的联系（一对一、一对多、多对多）。关系型数据库中，我们的数据都被存放在了各种表中（比如用户表），表中的每一行就存放着一条数据（比如一个用户的信息）。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/5e3c1a71724a38245aa43b02_99bf70d46cc247be878de9d3a88f0c44.png)



大部分关系型数据库都使用 SQL 来操作数据库中的数据。并且，大部分关系型数据库都支持事务的四大特性(ACID)。

**有哪些常见的关系型数据库呢？**

MySQL、PostgreSQL、Oracle、SQL Server、SQLite（微信本地的聊天记录的存储就是用的 SQLite） ......。



### MySQL介绍

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/20210327143351823.png)

**MySQL 是一种关系型数据库，主要用于持久化存储我们的系统中的一些数据比如用户信息。**

由于 MySQL 是开源免费并且比较成熟的数据库，因此，MySQL 被大量使用在各种系统中。任何人都可以在 GPL(General Public License) 的许可下下载并根据个性化的需要对其进行修改。MySQL 的默认端口号是**3306**。



## 存储引擎
### 存储引擎相关的命令

**查看 MySQL 提供的所有存储引擎**

```sql
mysql> show engines;
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220310173100074.png)



> 从上图我们可以查看出 MySQL 当前默认的存储引擎是 InnoDB，并且在 5.7 版本所有的存储引擎中只有 InnoDB 是事务性存储引擎，也就是说只有 InnoDB 支持事务。



**查看 MySQL 当前默认的存储引擎**

```sql
mysql> show variables like '%storage_engine%';
```

**查看表的存储引擎**

```sql
show table status like "table_name" ;
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E6%9F%A5%E7%9C%8B%E8%A1%A8%E7%9A%84%E5%AD%98%E5%82%A8%E5%BC%95%E6%93%8E.png)



### MyISAM和InnoDB的区别

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0MzM3Mjcy,size_16,color_FFFFFF,t_70.png)













### 关于MyISAM和InnoDB的选择问题







## 锁机制和InnoDB锁算法
## 查询缓存
## 事务
### 何为事务？
### 何为数据库事务？
### 何为ACID特性？
### 并发事务带来哪些问题？
### 事务隔离级别有哪些？
### MySQL的默认隔离级别是什么？











































































