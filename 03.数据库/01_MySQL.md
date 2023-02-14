​	





# 写在前面



## 推荐

### 视频



[黑马程序员220218](https://www.bilibili.com/video/BV1Kr4y1i7ru?p=168&spm_id_from=pageDriver)

[尚硅谷宋红康MySQL数据库教程天花板211117](https://www.bilibili.com/video/BV1iq4y1u7vj/?spm_id_from=autoNext)

[黑马程序员200918（MyCat性能最好的开源数据库中间件）](https://www.bilibili.com/video/BV17f4y1D7pm?p=98)

### 书籍





### 博客



[MySQL 实战 45 讲（极客时间专栏）](https://time.geekbang.org/column/article/68319)




MySQL 是一款非常流行的数据库管理系统

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330133909080.png?w=600)







## 面试题

### 黑马

```bash
-- 基础篇
什么是事务,以及事务的四大特性? 
事务的隔离级别有哪些,MySQL默认是哪个? 
内连接与左外连接的区别是什么? 

-- 进阶篇
常用的存储引擎？InnoDB与MyISAM的区别？
MySQL默认InnoDB引擎的索引是什么数据结构? 
如何查看MySQL的执行计划? 
索引失效的情况有哪些? 
什么是回表查询? 
什么是MVCC?

-- 运维篇
MySQL主从复制的原理是什么? 
主从复制之后的读写分离如何实现? 
数据库的分库分表如何实现?
```

### 悟空架构面试必备👍🏻

[原文](http://www.passjava.cn/#/88.Interview/03.Database/MySQL1)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/r0GNNmUoQput.png)

#### 一、说下 MySQL 的 redo log 和 binlog？



```bash
（1）MySQL 分两层：Server层和引擎层。区别如下：
- Server层：主要做的是 MySQL 功能层面的事情。Server 层也有自己的日志，称为 binlog（归档日志）
- 引擎层：负责存储相关的具体事宜。redo log 是 InnoDB 引擎特有的日志。

（2）redo log 是物理日志，记录的是“在某个数据页上做了什么修改”；
binlog 是逻辑日志，记录的是这个语句的原始逻辑，比如“给 ID=2 这一行的 c 字段加 1 ”。

（3）redo log 是循环写的，空间固定会用完；

（4）binlog 是可以追加写入的。“追加写”是指 binlog 文件写到一定大小后会切换到下一个，并不会覆盖以前的日志。
```



#### 二、说说建立索引的优势、负面影响和原则？

```bash
# （1）索引的优势？ 
检索速度：快速访问数据表中的特定信息，提高检索速度。 
唯一性：创建唯一性索引，保证数据库表中每一行数据的唯一性。 
加速连接：加速表和表之间的连接。 
减少分组和排序的时间：使用分组和排序进行数据检索时，可以显著减少查询中分组和排序的时间。

# （2）索引的负面影响？ 
耗时：创建索引和维护索引需要耗费时间，这个时间随着数据量的增加而增加。 
占空间：索引需要占用物理空间，不光是表需要占用数据空间，每个索引也需要占用物理空间。 
维护速度：当对表进行增、删、改、的时候索引也要动态维护，这样就降低了数据的维护速度。

# （3）为数据表建立索引的原则有哪些？ 
在最频繁使用的、用以缩小查询范围的字段上建立索引。 在频繁使用的、需要排序的字段上建立索引。

# （4）什么情况下不适合建立索引？ 
对于查询中很少涉及的列或者重复值比较多的列，不宜建立索引。 
对于一些特殊的数据类型，不宜建立索引，比如文本字段（text）。
```



#### 三、说下MySQL中的索引有哪些分类？

```bash
MySQL的所有列类型都可以被索引。
MyISASM和InnoDB类型的表默认创建的都是BTREE索引。

MySQL中的索引是在存储引擎层中实现的，而不是在服务器层实现的。
所以每种存储引擎的索引都不一定完全相同，也不是所有的存储引擎都支持所有的索引类型。

# MySQL目前提供了以下几种索引。

1）BTREE索引：最常见的索引类型，大部分引擎都支持BTREE索引，例如MyISASM、InnoDB、MEMORY等。

2）HASH索引：只有MEMORY和NDB引擎支持，适用于简单场景。

3）RTREE索引（空间索引）：空间索引是MylSAM的一个特殊索引类型，主要用于地理空间数据类型，通常使用较少。

4）FULLTEXT（全文索引）：全文索引也是MylSAM的一个特殊索引类型，主要用于全文索引。
InnoDB从MySQL 5.6版本开始提供对全文索引的支持。
```



#### 四、说下使用索引的推荐原则有哪些？

```bash
# 查询条件的列
（1）最适合索引的列是出现在WHERE子句中的列，或连接子句中指定的列，而不是出现在SELECT关键字后的选择列表中的列。 
（2）使用唯一索引。唯一性索引的值是唯一的，可以更快速的通过该索引来确定某条记录。 
（3）不要过度索引。因为每个索引都要占用额外的磁盘空间，并降低写操作的性能，增加维护成本。
在修改表的内容时，索引必须进行更新，有时也可能需要重构，因此，索引越多，维护索引所花的时间也就越长。 
# 排序、分组和联合操作的字段
（4）为经常需要排序、分组和联合操作的字段建立索引。 
（5）删除不再使用或者很少使用的索引。 
# 最左前缀法则
（6）利用最左原则。mysql建立多列索引（联合索引）有最左前缀的原则，即最左优先。
如：如果有一个2列的索引(col1,col2),则已经对(col1)、(col1,col2)上建立了索引； 如果有一个3列索引(col1,col2,col3)，则已经对(col1)、(col1,col2)、(col1,col2,col3)上建立了索引。 
# 短（索引长度）
（7）使用短索引。
```



#### 五、说下MySQL覆盖索引

```bash
# 概念
如果一个索引包含（或者说覆盖了）所有满足查询所需要的数据，那么就称这类索引为覆盖索引（Covering Index）。

在 MySQL 中，可以通过使用 explain 命令输出的 Extra 列来判断是否使用了索引覆盖查询。
若使用了索引覆盖查询，则 Extra 列包含“Using index””字符串。

大白话解释： select 的数据列只用从索引中就能够取得，不必从数据表中读取，换句话说查询列要被所使用的索引覆盖。

# 优点： 
1）覆盖索引能有效地提高查询性能，因为覆盖索引只需要读取索引而不需要再回表读取数据。
MySQL查询优化器在执行查询前会判断是否有一个索引能执行覆盖查询。 
2）索引项通常比记录要小，所以MySQL会访问更少的数据。

# 补充
不是所有类型的索引都可以成为覆盖索引。
覆盖索引必须要存储索引的列，而哈希索引、空间索引和全文索引等都不存储索引列的值，所以MySQL只能使用B-Tree索引做覆盖索引。
```



#### 六、说下联合索引的各种匹配规则





#### 七、说下MySQL回表





#### 八、说下 MySQL 最左匹配规则







#### 九、说下你在使用索引上遇到的一些问题











## 课程规划

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330134008703.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330133948392.png)





# 基础篇



## 1. 概述（介绍、安装）

### 介绍

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330225654238.png)



**主流的关系型数据库管理系统**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330230208197.png)



### 安装

安装见飞书云文档：https://f6q9xnlo2k.feishu.cn/docx/G544dueVxo0ILRxZIbTc1jqHn7d





**关系模型**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330232509495.png)



## 2. SQL（操作MySQL的语言）（核心部分）

关键字建议使用大写

SQL分类

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330233114823.png)



### DDL（库表字段定义）

- 库操作

```sql
-- 查询所有数据库
show databases;
-- 使用
use 数据库名;

-- 查询当前数据库
select database();
-- 创建
create database [if not exists] 数据库名 [default charset 字符集] [collate 排序规则]; 
-- 删除
drop database [if exists] 数据库名;
```



- 表操作

```sql
-- 查询当前数据库所有表
show tables;
-- 查询表结构
desc 表名;
-- 查询指定表的建表语句
show create table 表名;

-- 创建表
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                       null comment '用户名',
    userAccount  varchar(256)                       null comment '登录账号',
    avatarUrl    varchar(256)                       null comment '头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(128)                       null comment '邮箱',
    userStatus   int      default 1                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted    tinyint  default 0                 not null comment '是否逻辑删除',
    userRole     int      default 0                 null comment '角色 0-普通用户 1-管理员'
)
    comment '用户表';

-- 添加字段
alter table 表名 add 字段名 类型（长度）[comment 注释][约束]
// 为emp表增加一个新的字段”昵称”为nickname，类型为varchar(20)
alter table emp add nickname varchar(20) comment '昵称';

-- 修改数据类型
alter table 表名 modify 字段名 新数据类型(长度);
-- 修改字段名和字段类型
alter table 表名 change 旧字段名 新字段名 类型(长度)[comment 注释][约束];
// 将emp表的nickname字段修改为username，类型为varchar(30)
alter table emp change nickname username varchar(30) comment '昵称';

-- 删除字段
alter table 表名 drop 字段名;
-- 修改表名
alter table 表名 rename to 新表名;
-- 删除表
drop table [if exists] 表名;
-- 删除指定表，并重新创建该表
truncate table 表名;
// 注意：在删除表时，表中的全部数据也会被删除。
```



MySQL中的数据类型有很多，主要分为三类：数值类型、字符串类型、日期时间类型。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110917846.png?w=600)



![image-20220331110934767](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110934767.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110956441.png)



```sh
根据需求创建表(设计合理的数据类型、长度)	

设计一张员工信息表，要求如下：
1. 编号（纯数字）
2. 员工工号 (字符串类型，长度不超过10位)
3. 员工姓名（字符串类型，长度不超过10位）
4. 性别（男/女，存储一个汉字）
5. 年龄（正常人年龄，不可能存储负数）
6. 身份证号（二代身份证号均为18位，身份证中有X这样的字符）
7. 入职时间（取值年月日即可）
```



**MySQL图形化界面**

![image-20220331112151427](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331112151427.png)



### DML（表的增删改）

- 添加数据（INSERT） 
- 修改数据（UPDATE） 
- 删除数据（DELETE）



```sql
insert into 表名 (字段名1,字段名2,...) values (值1,值2,...);
insert into 表名 values (值1,值2,...);

-- 批量添加
insert into 表名 (字段名1,字段名2,...) values (值1,值2,...),(值1,值2,...),(值1,值2,...);
insert into 表名 values (值1,值2,...),(值1,值2,...),(值1,值2,...);
// 字符串和日期型数据应该包含在引号中。

-- 修改数据
update 表名 set 字段名1=值1,字段名2=值2,...[where 条件];
// 注意：修改语句的条件可以有，也可以没有，如果没有条件，则会修改整张表的所有数据。

-- 删除数据
delete from 表名 [where 条件];
// 注意：DELETE 语句的条件可以有，也可以没有，如果没有条件，则会删除整张表的所有数据。
```



### DQL（表的查询）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331113108224.png)



```sql
-- 基本查询
select 字段1 [as 别名1],字段2 [as 别名2],字段3 [as 别名3],... from 表名;
select * from 表名;
select distinct 字段列表 from 表名;
// 注意 : * 号代表查询所有字段，在实际开发中尽量少用（不直观、影响效率）。

-- 条件查询
select 字段列表 from 表名 where 条件列表;
```

![image-20220331113523084](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331113523084.png)



```sql
-- 聚合函数：将一列数据作为一个整体，进行纵向计算 。
count: 统计数量
max: 最大值
min: 最小值
avg: 平均值
sum: 求和

select 聚合函数 (字段列表) from 表名;
// 注意 : null值不参与所有聚合函数运算。

-- 分组查询 group by
select 字段列表 from 表名 [where 条件] group by 分组字段名 [having 分组后过滤条件];	
// where与having区别
// 执行时机不同：where是分组之前进行过滤，不满足where条件，不参与分组；而having是分组之后对结果进行过滤。
// 判断条件不同：where不能对聚合函数进行判断，而having可以。
// 执行顺序: where > 聚合函数 > having 。
// 分组之后，查询的字段一般为聚合函数和分组字段，查询其他字段无任何意义。
```



```sql
-- 排序查询 order by
select 字段列表 from 表名 order by 字段1 排序方式1, 字段2 排序方式2;
// ASC：升序 （默认值）
// DESC：降序
// 注意：如果是多字段排序，当第一个字段值相同时，才会根据第二个字段进行排序。
-- 分页查询 limit
select 字段列表 from 表名 limit 起始索引 查询记录数;
// 起始索引从0开始，起始索引 = （查询页码 - 1）* 每页显示记录数。
// 分页查询是数据库的方言，不同的数据库有不同的实现，MySQL中是LIMIT。
// 如果查询的是第一页数据，起始索引可以省略，直接简写为 limit 10。
```



```sql
// 练习
1. 查询年龄为20,21,22,23岁的员工信息。
2. 查询性别为 男 ，并且年龄在 20-40 岁(含)以内的姓名为三个字的员工。
3. 统计员工表中, 年龄小于60岁的 , 男性员工和女性员工的人数。
4. 查询所有年龄小于等于35岁员工的姓名和年龄，并对查询结果按年龄升序排序，如果年龄相同按入职时间降序排序。
5. 查询性别为男，且年龄在20-40 岁(含)以内的前5个员工信息，对查询的结果按年龄升序排序，年龄相同按入职时间升序排序。
```



执行顺序

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331114556898.png)





### DCL（访问权限控制）

#### 用户管理

```sql
-- 查询用户
use mysql;
select * from user;

-- 创建用户
create user '用户名'@'主机名' identified by '密码';

-- 修改用户密码
alter user '用户名'@'主机名' identified with mysql_native_password by '新密码'; 

-- 删除用户
drop user '用户名'@'主机名'
// 主机名可以使用 % 通配。
// 这类SQL开发人员操作的比较少，主要是DBA（ Database Administrator 数据库管理员）使用。
```



#### 权限控制

MySQL中定义了很多种权限，但是常用的就以下几种：

| 权限                | 说明               |
| ------------------- | ------------------ |
| ALL, ALL PRIVILEGES | 所有权限           |
| SELECT              | 查询数据           |
| INSERT              | 插入数据           |
| UPDATE              | 修改数据           |
| DELETE              | 删除数据           |
| ALTER               | 修改表             |
| DROP                | 删除数据库/表/视图 |
| CREATE              | 创建数据库/表      |



```sql
-- 查询权限
show grants for '用户名'@'主机名';
-- 授予权限
grant 权限列表 on 数据库名.表名 to '用户名'@'主机名';
-- 撤销权限
revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
// 多个权限之间，使用逗号分隔
// 授权时， 数据库名和表名可以使用 * 进行通配，代表所有。
```





## 3. 函数（功能强大的内置函数、其应用场景）

是指一段可以直接被另一段程序调用的程序或代码。

### 字符串函数

| 函数                       | 功能                                                         |
| -------------------------- | ------------------------------------------------------------ |
| concat (S1,S2,...Sn)       | 字符串拼接，将 S1，S2 ，...Sn 拼接成一个字符串               |
| lower(str)                 | 将字符串 str 全部转为小写                                    |
| upder(str)                 | 将字符串 str 全部转为大写                                    |
| lpad(str, n ,pad)          | 左填充，用字符串 pad 对 str 的左边进行填充，达到 n 个字符串长度 |
| Rpad(str, n ,pad)          | 左填充，用字符串 pad 对 str 的右边进行填充，达到 n 个字符串长度 |
| trim(str)                  | 去掉字符串头部和尾部的空格                                   |
| substring(str, start, len) | 返回字符串 str  从 start 位置起的 len 个长度的字符串         |



```sql
select 函数(参数);

// 练习：由于业务需求变更，企业员工的工号，统一为5位数，目前不足5位数的全部在前面补0。比如： 1号员
工的工号应该为00001。
```



### 数值函数

| 函数       | 功能                                   |
| ---------- | -------------------------------------- |
| ceil(x)    | 向上取整                               |
| floor(x)   | 向下取整                               |
| mod(x/y)   | 返回 x/y 的模                          |
| rand()     | 返回 0~1 内的随机数                    |
| rand(x, y) | 求参数 x 的四舍五入的值，保留 y 位小数 |



```sql
//通过数据库的函数，生成一个六位数的随机验证码。

```





### 日期函数

| 函数                               | 功能                                                |
| ---------------------------------- | --------------------------------------------------- |
| curdate()                          | 返回当前日期                                        |
| curtime()                          | 返回当前时间                                        |
| now()                              | 返回当前日期和时间                                  |
| yesr(date)                         | 获取指定 date 的年份                                |
| month(date)                        | 获取指定 date 的月份                                |
| day(date)                          | 获取指定 date 的日期                                |
| date_add(date, interval expr type) | 返回一个日期/时间值加上一个时间间隔 expr 后的时间值 |
| datediff(date1, date1)             | 返回起始时间 date1 和 结束时间 date2 之间的天数     |



```sql
// 查询所有员工的入职天数，并根据入职天数倒序排序。	
```





### 流程函数

流程函数也是很常用的一类函数，可以在SQL语句中实现条件筛选，从而提高语句的效率。

| 函数                                                      | 功能                                                         |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| if(value, t, f)                                           | 如果 value 为 true，则返回t ，否则返回f                      |
| ifnull(value1, value2)                                    | 如果 value 不为空，返回value1 ，否则返回value2               |
| case when [val1] then [res1] ... else [default] end       | 如果 val1 为true ，返回res1 ， 否则返回 default 默认值       |
| case [expr] when [val1] then [res1]... else [default] end | 如果 expr 的值等于val1 ，返回res1 ， 否则返回 default 默认值 |



```sql
统计班级各个学员的成绩，展示的规则如下：
• >= 85，展示优秀
• >= 60，展示及格
• 否则，展示不及格
```



```sql
1. 字符串函数
concat lower upper lpad rpad trim sunstring
2. 数值函数
ceil floor mod rand round
3. 日期函数
curdate curtime now year month day date_add adtediff 
4. 流程函数
if ifnull case[...]when...then...else...end
```





## 4. 约束（保证数据的完整性和正确性）

### 概述

1. 概念：约束是作用于表中字段上的规则，用于限制存储在表中的数据。

2. 目的：保证数据库中数据的正确、有效性和完整性。

3. 分类：

| 约束                       | 描述                                                     | 关键字      | 例如                      |
| -------------------------- | -------------------------------------------------------- | ----------- | ------------------------- |
| 非空约束                   | 限制该字段的数据不能为null                               | not null    | 不为空，并且唯一          |
| 唯一约束                   | 保证该字段的所有数据都是唯一、不重复的                   | unique      | 不为空，并且唯一          |
| 主键约束                   | 主键是一行数据的唯一标识，要求非空且唯一                 | primary key | 主键，并且自动增长        |
| 默认约束                   | 保存数据时，如果未指定该字段的值，则采用默认值           | default     | 如果没有指定该值，默认为1 |
| 检查约束（8.0.16版本之后） | 保证字段值满足某一个条件                                 | check       | 大于0并且小于等于120      |
| 外键约束                   | 用来让两张表的数据之间建立连接，保证数据的一致性和完整性 | foreign key |                           |

注意：约束是作用于表中字段上的，可以在创建表/修改表的时候添加约束。



> 案例

| 字段名 | 字段含义   | 字段类型    | 约束条件                  |
| ------ | ---------- | ----------- | ------------------------- |
| id     | ID唯一标识 | int         | 主键，并且自动增长        |
| name   | 姓名       | varchar(10) | 不为空，并且唯一          |
| age    | 年龄       | int         | 大于0并且小于等于120      |
| status | 状态       | char        | 如果没有指定该值，默认为1 |
| gender | 性别       | char        | 无                        |



```sql
create table tb_user(
  id int auto_increment primary key comment 'ID唯一标识',
  name varchar(10) not null unique comment '姓名',
  age int check(age>0&&age<=120) comment '年龄',
  status char(1) default('1') comment '状态',
  gender char(1) comment '性别'
);
```



### 外键约束

外键用来让两张表的数据之间建立连接，从而保证数据的一致性和完整性。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331131724487.png)

注意：目前上述的两张表，在数据库层面，并未建立外键关联，所以是无法保证数据的一致性和完整性的。



```sql
-- 添加外键
create table 表名(
    字段名  数据类型
  ...
  
  [constraint][外键名称] foreign key (外键字段名) references 主表(主表列名);
)
alter table 表名 add constraint 外键名称 foreign key(外键字段名) references 主表(主表列名);

-- 删除外键
alter table 表名 drop foreign key 外键名称;
```



- 删除/更新行为

| 行为        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| no action   | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有则不允许删除/更新。 (与restrict 一致) |
| restrict    | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有则不允许删除/更新。 (与 no action一致) |
| cascade     | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有，则也删除/更新外键在子表中的记录。 |
| set null    | 当在父表中删除对应记录时，首先检查该记录是否有对应外键，如果有则设置子表中该外键值为null（这就要求该外键允许取null）。 |
| set default | 父表有变更时，子表将外键列设置成一个默认的值 (Innodb不支持)  |



```sql
alter table 表名 add constraint 外键名称 foreign key（外键字段） references 主表名( 主表字段名)  on update cascade on default cascade;
```





## 5. 多表查询（结合具体的案例）

### 多表关系

在数据库表结构设计时，会根据业务需求及业务模块之间的关系，分析并设计表结构，由于业务之间相互关联，所以各个表结构之间也存在着各种联系，基本上分为三种：

- 一对多（多对一）
  - 案例：部门 与 员工的关系
  - 关系：一个部门对应多个员工，一个员工对应一个部门
  - 实现：在多的一方建立外键，指向一的一方的主键


![image-20220331134259260](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134259260.png)



- 多对多
  - 案例：学生 与 课程的关系
  - 关系：一个学生可以选修多门课程，一门课程也可以供多个学生选择
  - 实现：建立第三张中间表，中间表至少包含两个外键，分别关联两方主键


![image-20220331134402725](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134402725.png)



- 一对一
  - 案例：用户 与 用户详情的关系
  - 关系：一对一关系，多用于单表拆分，将一张表的基础字段放在一张表中，其他详情字段放在另一张表中，以提升操作效率
  - 实现：在任意一方加入外键，关联另外一方的主键，并且设置外键为唯一的(UNIQUE)


![image-20220331134527199](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134527199.png)



### 多表查询概述

- 概述: 指从多张表中查询数据

- 笛卡尔积: 笛卡尔乘积是指在数学中，两个集合A集合 和 B集合的所有组合情况。(在多表查询时，需要消除无效的笛卡尔积)


#### 连接查询

- 内连接：相当于查询A、B交集部分数据

- 左外连接：查询左表所有数据，以及两张表交集部分数据

- 右外连接：查询右表所有数据，以及两张表交集部分数据

- 自连接：当前表与自身的连接查询，自连接必须使用表别名


#### 子查询

交集

### 内连接

```sql
-- 隐式内连接
  select 字段列表 from 表1,表2 where 条件...;
  -- 隐式内连接
  select 字段列表 from 表1 [inner] join 表2 on 连接条件...;
// 内连接查询的是两张表交集的部分
```



### 外连接（重点）

```sql
-- 左外连接: 相当于查询表1(左表)的所有数据 包含 表1和表2交集部分的数据
select 字段列表 from 表1 left[outer] join 表2 on 条件...;
-- 左外连接
select 字段列表 from 表1 right[outer] join 表2 on 条件...;
```



### 自连接

```sql
-- 自连接查询，可以是内连接查询，也可以是外连接查询。
select 字段列表 from 表A 别名A join 表B 别名B on 条件...;

-- 联合查询-union , union all：对于union查询，就是把多次查询的结果合并起来，形成一个新的查询结果集。
select 字段列表 from 表A...
union[all]
select 字段列表 from 表B...
// 对于联合查询的多张表的列数必须保持一致，字段类型也需要保持一致。
// union all 会将全部的数据直接合并在一起，union 会对合并之后的数据去重。
```



### 子查询

概念：SQL语句中嵌套SELECT语句，称为嵌套查询，又称子查询。

```sql
select * from t1 where column1 = (select column1 from t2);
// 子查询外部的语句可以是INSERT / UPDATE / DELETE / SELECT 的任何一个。
```

根据子查询结果不同，分为：

- 标量子查询（子查询结果为单个值：数字、字符串、日期等）（ 常用的操作符：= <> > >= < <= 	）
- 列子查询(子查询结果为一列) （常用的操作符：IN 、NOT IN 、 ANY 、SOME 、 ALL）
- 行子查询(子查询结果为一行) （常用的操作符：= 、<> 、IN 、NOT IN）
- 表子查询(子查询结果为多行多列)）（常用的操作符：IN）



根据子查询位置，分为：WHERE之后 、FROM之后、SELECT 之后。



### 多表查询案例

```bash
1. 查询员工的姓名、年龄、职位、部门信息。
2. 查询年龄小于30岁的员工姓名、年龄、职位、部门信息。
3. 查询拥有员工的部门ID、部门名称。
4. 查询所有年龄大于40岁的员工, 及其归属的部门名称; 如果员工没有分配部门, 也需要展示出来。
5. 查询所有员工的工资等级。
6. 查询 "研发部" 所有员工的信息及工资等级。
7. 查询 "研发部" 员工的平均工资。
8. 查询工资比 "灭绝" 高的员工信息。
9. 查询比平均薪资高的员工信息。
10. 查询低于本部门平均工资的员工信息。
11. 查询所有的部门信息, 并统计部门的员工人数。
12. 查询所有学生的选课情况, 展示出学生名称, 学号, 课程名称
```



### 总结

多表关系

- 一对多：在多的一方设置外键，关联一的一方的主键

- 多对多：建立中间表，中间表包含两个外键，关联两张表的主键

- 一对一：用于表结构拆分，在其中任何一方设置外键unique ，关联另一方的主键



多表查询

- 内连接

```sql
select 字段列表 from 表1,表2 where 条件...;

select 字段列表 from 表1 [inner] join 表2 on 连接条件...;
```

- 外连接

```sql
select 字段列表 from 表1 left[outer] join 表2 on 条件...;
```

- 自连接

```sql
select 字段列表 from 表A 别名A join 表B 别名B on 条件...;
```

- 子查询

```sql
标量子查询、列子查询、行子查询、表子查询
```





## 6. 事务（保证数据的安全性）

### 事务简介

事务 是一组操作的集合，它是一个不可分割的工作单位，事务会把所有的操作作为一个整体一起向系统提交或撤销操作

请求，即这些操作要么同时成功，要么同时失败。

**默认MySQL的事务是自动提交的，也就是说，当执行一条DML语句，MySQL会立即隐式的提交事务。**



### 事务操作

```sql
-- 查看/设置事务提交方式
select @@autocommit;
set @@autocommit=0;

-- 开启事务
start transaction 或 begin;
-- 提交事务
commit;
-- 回滚事务
rollback;
```



### 事务四大特性AIDC

- 原子性（Atomicity）：事务是不可分割的最小操作单元，要么全部成功，要么全部失败。
- 一致性（Consistency）：执⾏事务前后，数据保持⼀致，多个事务对同⼀个数据读取的结果是相同的。
- 隔离性（Isolation）：数据库系统提供的隔离机制，保证事务在不受外部并发操作影响的独立环境下运行。
-  持久性（Durability）：事务一旦提交或回滚，它对数据库中的数据的改变就是永久的。



### 并发事务问题

| 问题       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| 脏读       | 一个事务读到另外一个事务还没有提交的数据。                   |
| 不可重复读 | 一个事务先后读取同一条记录，但两次读取的数据不同，称之为不可重复读。 |
| 幻读       | 一个事务按照条件查询数据时，没有对应的数据行，但是在插入数据时，又发现这行数据已经存在，好像出现了 |





### 事务隔离级别

| 隔离级别                | 脏读 | 不可重复读 | 幻读 |
| ----------------------- | ---- | ---------- | ---- |
| Read uncommitted        | √    | √          | √    |
| Read committed          | ×    | √          | √    |
| Repeatable Read（默认） | ×    | ×          | √    |
| Serializable            | ×    | ×          | ×    |



```sql
-- 查看事务隔离级别
select @@transaction_isolation;

-- 设置事务隔离级别
set [session | global] transaction isolation level [Read uncommitted | Read committed | Repeatable Read | Serializable]
```



**注意：事务隔离级别越高，数据越安全，但是性能越低。**





### 总结

1. 事务简介

事务是一组操作的集合，这组操作，要么全部执行成功，要么全部执行失败。

2. 事务操作

```sql
-- 开启事务
start transaction 或 begin;
-- 提交事务
commit;
-- 回滚事务
rollback;
```



3. 事务四大特性

原子性 Atomicity 、一致性 Consistency 、隔离性 Isolation 、持久性 Durability 

4. 并发事务问题

脏读、不可重复读、幻读

5. 事务隔离级别

Read uncommitted | Read committed | Repeatable Read | Serializable







# 进阶篇

## 7. 存储引擎

### MySQL体系结构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331144414498.png)



```bash
-- 连接层
最上层是一些客户端和链接服务，主要完成一些类似于连接处理、授权认证、及相关的安全方案。服务器也会为安全接入的每个客户
端验证它所具有的操作权限。

-- 服务层
第二层架构主要完成大多数的核心服务功能，如SQL接口，并完成缓存的查询，SQL的分析和优化，部分内置函数的执行。所有跨存
储引擎的功能也在这一层实现，如 过程、函数等。

-- 引擎层
存储引擎真正的负责了MySQL中数据的存储和提取，服务器通过API和存储引擎进行通信。不同的存储引擎具有不同的功能，这样我
们可以根据自己的需要，来选取合适的存储引擎。

-- 存储层
主要是将数据存储在文件系统之上，并完成与存储引擎的交互。

```



### 存储引擎简介

存储引擎就是存储数据、建立索引、更新/查询数据等技术的实现方式 。存储引擎是基于表的，而不是基于库的，所以存储引擎也可被

称为表类型。



1. 在创建表时，指定存储引擎

```sql
create table 表名(
		字段1  字段1类型 [comment 字段1注释],
  	...
		字段n  字段n类型 [comment 字段n注释]
) engine=innodb [comment 表注释];
```



2. 查看当前数据库支持的存储引擎

```sql
show engines;
```



### 存储引擎特点（事务、外键、行级锁）

#### InnoDB

介绍：InnoDB是一种兼顾高可靠性和高性能的通用存储引擎，在 MySQL 5.5 之后，InnoDB是默认的 MySQL 存储引擎。

特点：

- DML操作遵循ACID模型，支持事务 ；

- 行级锁 ，提高并发访问性能；

- 支持外键 FOREIGN KEY约束，保证数据的完整性和正确性；

文件：

xxx.ibd：xxx代表的是表名，innoDB引擎的每张表都会对应这样一个表空间文件，存储该表的表结构（frm、sdi）、数据和索引。

参数：innodb_file_per_table

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331145439040.png)





#### MyISAM

介绍：MyISAM是MySQL早期的默认存储引擎。

特点：

- 不支持事务，不支持外键

- 支持表锁，不支持行锁

- 访问速度快

文件：

xxx.sdi：存储表结构信息

xxx.MYD: 存储数据

xxx.MYI: 存储索引



#### Memory

介绍：Memory引擎的表数据是存储在内存中的，由于受到硬件问题、或断电问题的影响，只能将这些表作为临时表或缓存使用。

特点：

- 内存存放

- hash索引（默认）

文件

xxx.sdi：存储表结构信息



![image-20220331145726518](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331145726518.png)



### 存储引擎选择

在选择存储引擎时，应该根据应用系统的特点选择合适的存储引擎。对于复杂的应用系统，还可以根据实际情况选择多种存储引擎进行组合。



➢ InnoDB : 是Mysql的默认存储引擎，支持事务、外键。如果应用对事务的完整性有比较高的要求，在并发条件下要求数据的一致

性，数据操作除了插入和查询之外，还包含很多的更新、删除操作，那么InnoDB存储引擎是比较合适的选择。

➢ MyISAM ： 如果应用是以读操作和插入操作为主，只有很少的更新和删除操作，并且对事务的完整性、并发性要求不是很高，那

么选择这个存储引擎是非常合适的。

➢ MEMORY：将所有数据保存在内存中，访问速度快，通常用于临时表及缓存。MEMORY的缺陷就是对表的大小有限制，太大的表

无法缓存在内存中，而且无法保障数据的安全性。



## 8. 索引index ⭐️

### 索引概述、优缺点

索引`index`是帮助MySQL高效获取数据的数据结构（有序）。在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这

些数据结构以某种方式引用（指向）数据， 这样就可以在这些数据结构上实现高级查找算法，这种数据结构就是索引。



最典型的例子就是查新华字典，通过查找目录快速定位到查找的字。

> B + Tree
>
> 红黑树
>
> 二叉树
>
> B - Tree



![image-20220331150549880](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331150549880.png)

备注：上述二叉树索引结构的只是一个示意图，并不是真实的索引结构。



优点：

- 提高数据检索的效率，降低数据库的IO成本。

- 通过索引列对数据进行排序，降低数据排序的成本，降低CPU的消耗。

缺点：

- 索引列也是要占用空间的。
- 索引大大提高了查询效率，同时却也降低更新表的速度，如对表进行INSERT、UPDATE、DELETE时，效率降低。



### 索引结构

我们平常所说的索引，如果没有特别指明，都是指B+树结构组织的索引。

| 索引          | InnoDB          | MyISAM | Memory |
| ------------- | --------------- | ------ | ------ |
| B+Tree索引    | 支持            | 支持   | 支持   |
| Hash索引      | 不支持          | 不支持 | 支持   |
| R-tree索引    | 不支持          | 支持   | 不支持 |
| Full-text索引 | 5.6版本之后支持 | 支持   | 不支持 |



#### 二叉树

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151215402.png)

二叉树缺点：顺序插入时，会形成一个链表，查询性能大大降低。 大数据量情况下，层级较深，检索速度慢。

红黑树：大数据量情况下，层级较深，检索速度慢。



#### B-Tree（多路平衡查找树）

以一颗最大度数（max-degree）为5(5阶)的b-tree为例(每个节点最多存储4个key，5个指针)：

![image-20220331151317568](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151317568.png)

> 知识小贴士: 树的度数指的是一个节点的子节点个数。



插入 100 65 169 368 900 556 780 35 215 1200 234 888 158 90 1000 88 120 268 250 数据为例。

![image-20220331151432119](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151432119.png)



> 具体动态变化的过程可以参考网站: https://www.cs.usfca.edu/~galles/visualization/BTree.html





#### B+Tree

以一颗最大度数（max-degree）为4（4阶）的b+tree为例：

![image-20220331151621423](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151621423.png)



插入 100 65 169 368 900 556 780 35 215 1200 234 888 158 90 1000 88 120 268 250 数据为例。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151738181.png)

相对于B-Tree区别: 

①. 所有的数据都会出现在叶子节点

②. 叶子节点形成一个单向链表



MySQL索引数据结构对经典的B+Tree进行了优化。在原B+Tree的基础上，增加一个指向相邻叶子节点的链表指针，就形成了带有顺

序指针的B+Tree，提高区间访问的性能。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151850397.png)



#### Hash

哈希索引就是采用一定的hash算法，将键值换算成新的hash值，映射到对应的槽位上，然后存储在hash表中。

如果两个(或多个)键值，映射到一个相同的槽位上，他们就产生了hash冲突（也称为hash碰撞），可以通过链表来解决。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331152030663.png?w=600)

Hash索引特点：

- Hash索引只能用于对等比较(=，in)，不支持范围查询（between，>，< ，...）
- 无法利用索引完成排序操作
- 查询效率高，通常只需要一次检索就可以了，效率通常要高于B+tree索引



<hr>

存储引擎支持：

在MySQL中，支持hash索引的是Memory引擎，而InnoDB中具有自适应hash功能，hash索引是存储引擎根据B+Tree索引在指定条件

下自动构建的。



**为什么InnoDB存储引擎选择使用B+tree索引结构?（即和另外3种进行对比）** 

- 相对于二叉树，层级更少，搜索效率高；
- 对于B-tree，无论是叶子节点还是非叶子节点，都会保存数据，这样导致一页中存储的键值减少，指针跟着减少，要同样保存大量数据，只能增加树的高度，导致性能降低；
- 相对Hash索引，B+tree支持范围匹配及排序操作；



<hr>

### 索引分类

| 分类     | 含义                                                 | 特点                     | 关键字   |
| -------- | ---------------------------------------------------- | ------------------------ | -------- |
| 主键索引 | 针对于表中主键创建的索引                             | 默认自动创建，只能有一个 | primary  |
| 唯一索引 | 避免同一个表中某数据列中的值重复                     | 可以有多个               | unique   |
| 常规索引 | 快速定位特定数据                                     | 可以有多个               |          |
| 全文索引 | 全文索引查找的是文本中的关键词，而不是比较索引中的值 | 可以有多个               | fulltext |

#### （1）主键索引

我们建表的时候，例如下面这个建表语句

```sql
CREATE TABLE `t_blog_sort` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类内容',
  `content` varchar(255) DEFAULT NULL COMMENT '分类简介',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `sort` int(11) DEFAULT '0' COMMENT '排序字段，越大越靠前',
  `click_count` int(11) DEFAULT '0' COMMENT '点击数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客分类表';
```

这里面有使用到 PRIMARY KEY (`uid`)，这就是主键索引

#### （2）唯一索引

唯一索引 类似于普通索引，索引列的值必须唯一

唯一索引和主键索引的区别就是，唯一索引允许出现空值，而主键索引不能为空

```sql
create unique index index_name on table(column)
```

或者创建表时指定

```sql
unique index_name column
```

#### （3） 普通索引

当我们需要建立索引的字段，既不是主键索引，也不是唯一索引

那么就可以创建一个普通索引

```sql
create index  index_name on table(column)
```

或者创建表时指定

```sql
create table(..., index index_name column)
```

#### （4）全文索引

lunce、solr和ElasticSearch就是做全文检索的，里面涉及到了**倒排索引**的概念，**mysql很少使用**全文索引。

要用来查找文本中的关键字，不是直接与索引中的值相比较，像是一个搜索引擎，配合 match against 使用，现在只有char，varchar，text上可以创建索引，在数据量比较大时，先将数据放在一个没有全文索引的表里，然后在利用create index创建全文索引，比先生成全文索引在插入数据快很多。

#### （5）组合索引

目前，在业务不是特别复杂的时候，可能使用一个列作为索引，或者直接采用主键索引即可，但是如果业务变得复杂的时候，就需要用到组合索引，通过对多个列建立索引。

组合索引的用处，假设我现在表有个多个字段：id、name、age、gender，然后我经常使用以下的查询条件

```sql
select * from user where name = 'xx' and age = xx
```

这个时候，我们就可以通过组合 name 和 age 来建立一个组合索引，加快查询效率，建立成组合索引后，我的索引将包含两个key值

在多个字段上创建索引，遵循**最左匹配**原则

```sql
alter table t add index index_name(a,b,c);
```



**在InnoDB存储引擎中，根据索引的存储形式，又可以分为以下两种：**

| 分类                      | 含义                                                       | 特点                    |
| ------------------------- | ---------------------------------------------------------- | ----------------------- |
| 聚集索引(Clustered Index) | 将数据存储与索引放到了一块，索引结构的叶子节点保存了行数据 | **必须有,而且只有一个** |
| 二级索引(Secondary Index) | 将数据与索引分开存储，索引结构的叶子节点关联的是对应的主键 | 可以存在多个            |



聚集索引选取规则: 

- 如果存在主键，主键索引就是聚集索引。
- 如果不存在主键，将使用第一个唯一（UNIQUE）索引作为聚集索引。
- 如果表没有主键，或没有合适的唯一索引，则InnoDB会自动生成一个rowid作为隐藏的聚集索引。







主键构建的主键索引就是聚集索引；叶子结点挂的数据就是这一行的数据。

如果要针对name字段再建立一个索引，因为聚集索引只会有一个，所以name字段建的索引称为二级索引。叶子结点下面挂的是name

值对应的这一行的的主键值

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331154544445.png)

> 第七分钟



```sql
select * from user where name = 'arm';
-- 不会走聚集索引，会走二级索引
-- A比L小，到Geek，再到Arm，拿到10.
-- 要获取所有数据，再根据聚集索引查找。这一过程称为回表查询：先走二级索引找到对应的主键值，再根据主键值到聚集索引中拿到这一行的行数据。
```





聚集索引和二级索引的结构一定要理解，这个在SQL优化中是非常重要的。很多SQL优化策略的底层原理。

看2道思考题：

1. 一下SQL语句，哪个执行效率高？为什么？（1）

```sql
-- id为主键，name字段创建的有索引
select * from user where id =10;
select * from user where name = 'Arm';
```

2. InnoDB主键索引的B+tree高度为多高呢?

假设: 一行数据大小为1k，一页中可以存储16行这样的数据。InnoDB的指针占用6个字节的空间，主键即使为bigint，占用字节数为8。

```bash
高度为2：
n * 8 + (n + 1) * 6 = 16*1024 , 算出n约为 1170 
1171* 16 = 18736
高度为3：
1171 * 1171 * 16 = 21939856

2千多万条记录，B+树高度也才为3。效率很高。
```





<hr>

### 索引语法

```sql
-- 创建索引
create [unique | fulltext] index index_name on table_name(index_col_name,...);

-- 查看索引
show index from table_name;

-- 删除索引
drop index index_name on table_name;
```



案例：按照下列的需求，完成索引的创建

```sql
-- 1. name字段为姓名字段，该字段的值可能会重复，为该字段创建索引。
-- 2. phone手机号字段的值，是非空，且唯一的，为该字段创建唯一索引。
-- 3. 为profession、age、status创建联合索引。
-- 4. 为email建立合适的索引来提升查询效率。

create index idx_user_name on tb_user(name);
create unique index idx_user_phone on tb_user(phone);
create index idx_user_profession_age_status on tb_user(profession,age,status);
create index idx_user_email on tb_user(email);

-- 删除索引
drop index idx_user_email on tb_user;
```





### SQL性能分析

#### SQL执行频率（查询为主还是增删改为主？）

MySQL 客户端连接成功后，通过 `show [session|global] status` 命令可以提供服务器状态信息。通过如下指令，可以查看当前

数据库的INSERT、UPDATE、DELETE、SELECT的访问频次：

```sql
show global status like 'Com_______';
```



#### 慢查询日志（优化哪些select语句）（定位执行效率低的SQL）

慢查询日志记录了所有执行时间超过指定参数（long_query_time，单位：秒，默认10秒）的所有SQL语句的日志。

MySQL的慢查询日志默认没有开启，需要在MySQL的配置文件（/etc/my.cnf）中配置如下信息：

```properties
# 查看当前慢查询日志开关
show variables like 'slow_query_log';
# 开启MySQL慢查询日志开关
slow_query_log=1
# 设置慢日志的时间为 2秒， 语句执行时间超过 2秒，就会视为慢查询，记录慢查询日志
long_query_time=2
```

配置完毕之后，通过以下指令重新启动MySQL服务器进行测试，查看慢日志文件中记录的信息 /var/lib/mysql/localhost-slow.log。

> https://blog.csdn.net/weixin_40675010/article/details/104256171



```sql
# mac开启慢查询日志: https://www.cnblogs.com/woods1815/p/11829659.html
mysql> set global slow_query_log='on';
SET GLOBAL long_query_time = 3;  # 这里需要注意下，long_query_time参数设置后需要下次会话后才生效，当前会话查询还是原来的数值
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331184122293.png)



#### profile详情（每一条sql的耗时，以及耗时在哪一个阶段）

show profiles 能够在做SQL优化时帮助我们了解时间都耗费到哪里去了。通过have_profiling参数，能够看到当前MySQL是否支持

profile操作：

```sql
select @@have_profiling;
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331184659785.png)





默认profiling是关闭的，可以通过set语句在session/global级别（会话/全局级别）开启profiling：

```sql
set profiling=1;
```



执行一系列的业务SQL的操作，然后通过如下指令查看指令的执行耗时：

```sql
-- 查看每一条 SQL 的耗时基本情况
show profiles;
-- 查看指定query_id的 SQL语句各个阶段的耗时情况
show profile for query query_id;
-- 查看指定query_id的 SQL语句cpu的使用情况
show profile cpu for query query_id;
```



以上三种方式从执行时间的角度出发。并不能真正评判一条sql的性能。

<hr>



#### explain执行计划（重要）

EXPLAIN 或者 DESC命令获取 MySQL 如何执行 SELECT 语句的信息，包括在 SELECT 语句执行过程中表如何连接和连接的顺序。

```sql
-- 直接在select语句之前加上关键字explain / desc
explain select 字段列表 from 表名 where 条件;
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331185441717.png?w=600)

 

**EXPLAIN 执行计划各字段含义：（重点字段是type、possible_key、key、key_len）**

- Id：select查询的序列号，表示查询中执行select子句或者是操作表的顺序(id相同，执行顺序从上到下；id不同，值越大，越先执行)。 

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331190207655.png?w=600)

- select_type：表示 SELECT 的类型，常见的取值有 SIMPLE（简单表，即不使用表连接或者子查询）、PRIMARY（主查询，即外

  层的查询）、UNION（UNION 中的第二个或者后面的查询语句）、SUBQUERY（SELECT/WHERE之后包含了子查询）等

- **type**：表示连接类型，性能由好到差的连接类型为NULL、system、const（主键或唯一索引）、eq_ref、ref（非唯一性索引）、range、 index、all （全表扫描，性能低）。 （尽量往前优化！）

- **possible_key**：显示可能应用在这张表上的索引，一个或多个。

- **Key**：实际使用的索引，如果为NULL，则没有使用索引。

- **Key_len**：表示索引中使用的字节数， 该值为索引字段最大可能长度，并非实际使用长度，在不损失精确性的前提下， 长度越短越好 。
- rows：MySQL认为必须要执行查询的行数，在innodb引擎的表中，是一个估计值，可能并不总是准确的。
- filtered：表示返回结果的行数占需读取行数的百分比， filtered 的值越大越好。



### 索引使用（如何正确使用、使用与否）

#### 使用索引

MySQL每次只使用一个索引，与其说 数据库查询只能用一个索引，倒不如说，和全表扫描比起来，去分析两个索引 B+树更耗费时间，所以where A=a and B=b 这种查询使用（A，B）的组合索引最佳，B+树根据（A，B）来排序。

- 主键，unique字段
- 和其他表做连接的字段需要加索引
- 在where 里使用 >, >=, = , <, <=, is null 和 between等字段。
- 使用不以通配符开始的like，where A like ‘China%’
- 聚合函数里面的 MIN()， MAX()的字段
- order by 和 group by字段



#### 何时不使用索引

- 表记录太少
- 数据重复且分布平均的字段（只有很少数据的列）
- 经常插入、删除、修改的表要减少索引
- text，image 等类型不应该建立索引，这些列的数据量大（加入text的前10个字符唯一，也可以对text前10个字符建立索引）
- MySQL能估计出全表扫描比使用索引更快的时候，不使用索引



#### 索引何时失效

- 组合索引为使用最左前缀，例如组合索引（A，B），where B = b 不会使用索引
- like未使用最左前缀，where A like "%China"
- 搜索一个索引而在另一个索引上做 order by， where A = a order by B，只会使用A上的索引，因为查询只使用一个索引。
- or会使索引失效。如果查询字段相同，也可以使用索引。例如 where A = a1 or A = a2（生效），where A=a or B = b （失效）
- 在索引列上的操作，函数upper()等，or、！ = （<>）,not in 等



#### 验证索引效率（索引对查询效率提升极大）

```sql
-- 在未建立索引之前，执行如下SQL语句，查看SQL的耗时。-------------------21s
select * from tb_sku where id = 1;
select * from tb_sku where id = 1\G;
select * from tb_sku where sn = '100000003145001';
-- 针对字段创建索引(1千万数据构建B+树，耗时1分钟11秒)
create index idx_sku_sn on tb_sku(sn);

-- 然后再次执行相同的SQL语句，再次查看SQL的耗时。-------------------0.01s
select * from tb_sku where sn = '100000003145001';
```



> 想到面试题：sql优化之正确使用索引

#### 1 最左前缀法则（针对联合索引）（索引失效或部分失效）

如果索引了多列（联合索引），要遵守最左前缀法则（查询从索引的最左列开始，并且不跳过索引中的列）

如果跳跃某一列，索引将部分失效(后面的字段索引失效)。

最左边的索引要存在，存在位置没有关系。

```sql
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';（符合最左前缀法则）
explain select * from tb_user where profession = '软件工程' and age = 31;（符合最左前缀法则）
explain select * from tb_user where profession = '软件工程';（符合最左前缀法则）
explain select * from tb_user where age = 31 and status = '0';（跳过了左边的列profession，不符合，索引失效，全表扫描）
explain select * from tb_user where status = '0';（同上）
explain select * from tb_user where profession = '软件工程' and status = '0';（索引status部分失效）
```



#### 范围查询（业务允许的话，尽量使用>=）

联合索引中，出现范围查询(>,<)，范围查询右侧的列索引失效

```sql
explain select * from tb_user where profession = '软件工程' and age > 31 and status = '0';（status部分索引失效）
explain select * from tb_user where profession = '软件工程' and age >= 31 and status = '0';（索引未失效）
```



#### 索引列运算

不要在索引列上进行运算操作， 索引将失效。

```sql
-- 查询手机号最后2位是15
explain select * from tb_user where substring(phone,10,2) = '15';（索引失效，type=ALL，全表扫描）
```



#### 字符串不加引号

字符串类型字段使用时，不加引号， 索引将失效。

```sql
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select * from tb_user where phone = 17799990015;
```



#### 模糊查询

如果仅仅是尾部模糊匹配，索引不会失效。如果是头部模糊匹配，索引失效。

```sql
explain select * from tb_user where profession like '软件%';
explain select * from tb_user where profession like '%工程';
explain select * from tb_user where profession like '%工';
```



#### or连接的条件

用or分割开的条件， 如果or前的条件中的列有索引，而后面的列中没有索引，那么涉及的索引都不会被用到。

```sql
explain select * from tb_user where id = 10 or age = 23;
explain select * from tb_user where phone = '17799990015' or age = 23;
```



由于age没有索引，所以即使id、phone有索引，索引也会失效。所以需要针对于age也要建立索引。



#### 数据分布影响（is null和is not null是否走索引取决于表中数据的分布）

如果MySQL评估使用索引比全表更慢，则不使用索引。

```sql
select * from tb_user where phone >= '17799990015';
select * from tb_user where phone >= '17799990015';
```



#### SQL提示（告诉MySQL用哪个索引）

SQL提示，是优化数据库的一个重要手段，简单来说，就是在SQL语句中加入一些人为的提示来达到优化操作的目的。



```sql
-- use index：
explain select * from tb_user use index(idx_user_pro) where profession = '软件工程'; 
-- ignore index：
explain select * from tb_user ignore index(idx_user_pro) where profession = '软件工程'; 
-- force index：
explain select * from tb_user force index(idx_user_pro) where profession = '软件工程'; 
```



#### 覆盖索引

尽量使用覆盖索引（查询使用了索引，并且需要返回的列，在该索引中已经全部能够找到），减少` select * `。（因为 * 很容易出现回表扫描）

```sql
explain select id,profession from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select id,profession,age,status from tb_user where profession = '软件工程' and age = 31 and status = '0';

explain select id,profession,age,status,name from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';
```

知识小贴士：

- using index condition ：查找使用了索引，但是需要回表查询数据

- using where; using index ：查找使用了索引，但是需要的数据都在索引列中能找到，所以不需要回表查询数据





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331203949131.png)



```sql
-- 面试题思考
-- 一张表, 有四个字段(id, username, password, status), 由于数据量大, 需要对以下SQL语句进行优化, 该如何进行才是最优方案:
select id, username, password from tb_user where username = 'itcast';
-- 建立username, password的联合索引
```





#### 前缀索引（降低索引的体积，只针对前缀建立索引）

当字段类型为字符串（varchar，text等）时，有时候需要索引很长的字符串，这会让索引变得很大，查询时，浪费大量的磁盘IO， 影

响查询效率。此时可以只将字符串的一部分前缀，建立索引，这样可以大大节约索引空间，从而提高索引效率。



```sql
create index idx_xxx on table_name(column(n));
-- 前缀长度: 可以根据索引的选择性来决定，而选择性是指不重复的索引值（基数）和数据表的记录总数的比值，索引选择性越高则查询效率越高， 唯一索引的选择性是1，这是最好的索引选择性，性能也是最好的。
select count(distinct email)/count(*) from tb_user;
select count(distinct substring(email,1,5)/count(*) from tb_user);
```



#### 单列索引与联合索引

单列索引：即一个索引只包含单个列。

联合索引：即一个索引包含了多个列。

在业务场景中，如果存在多个查询条件，考虑针对于查询字段建立索引时，建议建立联合索引，而非单列索引。



```sql
explain select id, phone, name from tb_user where phone = '17799990010' and name = '韩信';
-- 必然涉及覆盖索引的问题，会回表查询，因为phone的索引当中必然不包含name字段的值。
-- 所以，推荐创建联合索引。
```



多条件联合查询时， MySQL优化器会评估哪个字段的索引效率更高，会选择该索引完成本次查询。



联合索引情况：（最终构建的B+树如下，节点存储phone和name的值）（这一个索引结构已经获取到了想要的数据，覆盖索引，不会回表查询）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331211509552.png?w=600)





<hr>

### 索引设计原则

1. 针对于数据量较大（超过100w），且**查询比较频繁的表建立索引**。

2. 针对于**常作为查询条件（where）、排序（order by）、分组（group by）操作的字段建立索引**。

3. 尽量**选择区分度高（身份证号，反例是性别）的列**作为索引，尽量建立唯一索引，区分度越高，使用索引的效率越高。

4. 如果是字符串类型的字段，字段的长度较长，可以针对于字段的特点，**建立前缀索引**。

5. 尽量使用联合索引，减少单列索引，查询时，**联合索引很多时候可以覆盖索引，节省存储空间，避免回表，提高查询效率。**

6. 要控制索引的数量，索引并不是多多益善，索引越多，维护索引结构的代价也就越大，**会影响增删改的效率。**

7. 如果索引列不能存储NULL值，请在创建表时使用**NOT NULL约束**它。当优化器知道每列是否包含NULL值时，它可以更好地确定哪个索引最有效地用于查询。



### 索引总结

```sql
-- 1.概述
索引是高效获取数据的数据结构 ;
-- 2.索引结构
B+ Tree: 所有的数据出现在叶子结点，而且叶子结点形成一个双向链表。
Hash
-- 3.索引分类
主键索引（主键默认会创建的）、唯一索引、常规索引、全文索引
聚集索引（必须存在，只能有一个。叶子结点挂的是一行row的数据。默认主键索引就是聚集索引。）、二级索引（叶子结点挂的是对应行的主键）
-- 4.索引语法（创建、查看、删除）
create [unique] index xxx on xxx(xxx);
show index from xxx;
drop index xxx from xxx;
-- 5.SQL性能分析
执行频次（查询为主还是增删改为主）、慢查询日志（定位执行比较耗时的sql语句）、profile（监控每一条sql语句的耗时以及具体的时间耗费在哪一个阶段）、explain（使用最多，查看sql语句的执行计划评判sql语句的性能）
-- 6.索引使用
联合索引（最重要的是遵循最左前缀法则，防止失效）
索引失效（索引列上运算、字符串不加引号、前面模糊匹配、or连接一侧没有索引、全表扫描更快）
SQL提示（指定使用哪个索引）
覆盖索引（涉及回表查询的问题。所谓覆盖索引是指查询返回的列在索引结构中都包含了。所谓回表查询是指先走二级索引再到聚集索引）
前缀索引（缩小索引的体积，提高查询的效率）
单列/联合索引（推荐使用联合索引。理由：性能较高；如果使用得当可以避免回表查询。）
-- 7.索引性能分析
表（数据量大，查询频率高）
字段（查询条件（where）、排序（order by）、分组（group by）后的字段）
建立设么索引（尽量建立唯一索引，区分度高）（尽量使用联合索引）（尽量使用前缀索引）
```







## 9. SQL优化（对索引进行优化）

### 9.1 插入数据



- 批量插入
- 手动提交事务
- 主键顺序插入



- 大批量插入数据

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331220058636.png)

> 使用load指令将100w数据加载进表结构当中，16s。使用insert的话，需要十多分钟。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331220122365.png)

主键顺序插入性能高于乱序插入。



### 9.2 主键优化（主键的设计原则）

> 所有的数据都在叶子结点，非叶子结点仅仅起到索引数据的作用，
>
> 非叶子结点的索引和叶子节点的数据最终都是存放在一个逻辑结构——页page当中的。



#### 页分裂

页可以为空，也可以填充一半，也可以填充100%。每个页包含了2-N行数据(如果一行数据多大，会行溢出)，根据主键排列。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331221432582.png)



主键乱序插入可能出现页分裂的情况。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331221455047.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331221554607.png)

#### 页合并

当删除一行记录时，实际上记录并没有被物理删除，只是记录被标记（flaged）为删除并且它的空间变得允许被其他记录声明使用。

当页中删除的记录达到 MERGE_THRESHOLD（默认为页的50%），InnoDB会开始寻找最靠近的页（前或后）看看是否可以将两个页

合并以优化空间使用。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331221837341.png)

知识小贴士：

MERGE_THRESHOLD：合并页的阈值，可以自己设置，在创建表或者创建索引时指定。

#### 主键设计原则

➢ 满足业务需求的情况下，尽量降低主键的长度。（主键索引只有一个，二级索引可以有很多）

➢ 插入数据时，尽量选择顺序插入（否则可能会出现页分裂），选择使用AUTO_INCREMENT自增主键。

➢ 尽量不要使用UUID做主键（无序的，可能会乱序插入。而且长度也较长。）或者是其他自然主键，如身份证号。

➢ 业务操作时，避免对主键的修改。（修改主键还会去动对应的索引结构，代价是比较大的）



### 9.3 order by 优化

① Using filesort：通过表的索引或全表扫描，读取满足条件的数据行，然后在排序缓冲区 sort buffer中完成排序操作，所有不是通过

索引直接返回排序结果的排序都叫 Filesort排序。

② Using index：通过有序索引顺序扫描直接返回有序数据，这种情况即为Using index ，不需要额外排序，操作效率高。（相比上

面，性能更高。）（优化order by语句的时候尽量优化为这种。）



```sql
-- 没有创建索引时，根据age,phone进行排序
explain select id, age, phone from tb_user order by age, phone;
-- 创建索引
create index idx_user_age_phone_aa on tb_user(age, phone); 
-- 创建索引后，根据age,phone进行升序排序(优化为Using index)
explain select id, age, phone from tb_user order by age, phone;
-- 创建索引后，根据age,phone进行降序排序(优化为Using index)(反向扫描索引)
explain select id, age, phone from tb_user order by age desc, phone desc;

-- 根据age, phone一个升序，一个降序
explain select id, age, phone from tb_user order by age asc, phone desc;(Using filesort, Using index)
-- 创建索引
create index idx_user_age_phone_ad on tb_user(age acs, phone desc); 
-- 再次测试，发现已经优化为Using index
```



![image-20220401094251904](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401094251904.png)



➢ 根据排序字段建立合适的索引，多字段排序时，也遵循最左前缀法则。

➢ 尽量使用覆盖索引。

➢ 多字段排序 一个升序一个降序，此时需要注意联合索引在创建时的规则（asc / desc ）。

➢ 如果不可避免的出现 ，大数据量排序时，可以适当增大排序缓冲区大小 sort_buffer_size （默认256k ）





### 9.4 group by （分组）优化



```sql
-- 删除目前的联合索引
drop index idx_user_pro_age_sta on tb_user;
-- 执行分组操作，根据profession字段分组
explain select profession, count(*) from tb_user group by profession;(用到了临时表，性能较低)
-- 创建联合索引
create index idx_user_pro_age_sta on tb_user(profession, age, status);
-- 执行分组操作，根据profession字段分组
explain select profession, count(*) from tb_user group by profession;（优化为了Using index）
-- 执行分组操作，根据profession字段分组
explain select profession, count(*) from tb_user group by profession, age;
```



➢  在分组操作时，可以通过索引来提高效率。

➢ 分组操作时，索引的使用也是满足最左前缀法则的。



### 9.5 limit（分页查询）优化

一个常见又非常头疼的问题就是 limit 2000000,10 ，此时需要MySQL排序前2000010 记录，仅仅返回2000000 - 2000010 

的记录，其他记录丢弃，查询排序的代价非常大 。

```sql
-- 第1页（速度很快）
select * from tb_sku limit 0,10;
-- 第2页（速度很快）
select * from tb_sku limit 10,10;
-- 1000000的前10条（慢）
select * from tb_sku limit 1000000,10;

-- 官方：覆盖索引+子查询实现
select s.* from tb_sku s, (select id from tb_sku order by id limit 9000000, 10) a where s.id = a.id;
```



优化思路: 一般分页查询时，通过创建 覆盖索引 能够比较好地提高性能，可以通过覆盖索引加子查询形式进行优化。



### 9.6 count（聚合函数）优化

```sql
explain select count(*) from tb_sku;
```

- MyISAM 引擎把一个表的总行数存在了磁盘上，因此执行 count(*) 的时候会直接返回这个数，效率很高；*
- InnoDB 引擎就麻烦了，它执行 count(*) 的时候，需要把数据一行一行地从引擎里面读出来，然后累积计数。



优化思路：自己计数



count() 是一个聚合函数，对于返回的结果集，一行行地判断，如果 count 函数的参数不是 NULL，累计值就加 1，否则不加，最

后返回累计值。



<hr>

**用法：count（*）、count（主键）、count（字段）、count（1）**

- count（主键）：InnoDB 引擎会遍历整张表，把每一行的 主键id 值都取出来，返回给服务层。服务层拿到主键后，直接按行进行

  累加(主键不可能为null)

- count（字段）：没有not null 约束 : InnoDB 引擎会遍历整张表把每一行的字段值都取出来，返回给服务层，服务层判断是否为

  null，不为null，计数累加。有not null 约束：InnoDB 引擎会遍历整张表把每一行的字段值都取出来，返回给服务层，直接按行进

  行累加。

- count（1）：InnoDB 引擎遍历整张表，但不取值。服务层对于返回的每一行，放一个数字“1”进去，直接按行进行累加。

- count（*）：InnoDB引擎并不会把全部字段取出来，而是专门做了优化，不取值，服务层直接按行进行累加。



**按照效率排序的话，count(字段) < count(主键 id) < count(1) ≈ count(*)，所以尽量使用 count(*)（数据库专门做了优化）。**



### 9.7 update（更新数据时）优化

执行update语句的时候，一定要根据索引字段进行更新，否则行锁升级为表锁。

InnoDB的行锁是针对索引加的锁，不是针对记录加的锁 ,并且该索引不能失效，否则会从行锁升级为表锁 。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401102524185.png)





## 10. 视图/存储过程/触发器（存储对象）

### 10.1 视图

#### 介绍

视图（View）是一种虚拟存在的表。视图中的数据并不在数据库中实际存在，行和列数据来自定义视图的查询中使用的表，并且是在 

使用视图时动态生成的。

通俗的讲，视图只保存了查询的SQL逻辑，不保存查询结果。所以我们在创建视图的时候，主要的工作就落在创建这条SQL查询语句上。



```sql
-- 创建
create [or replace] view 视图名称[列名列表] as select 语句 [with [cascaded | local] check option]
create or replace view stu_v_1 as select id, name from student where id <=10;

-- 查询
-- 查询创建视图语句  show create view 视图名称;
-- 查询视图数据  select * from 视图名称......;
show create view stu_v_1;
select * from stu_v_1;
select * from stu_v_1 where id < 3;

-- 修改
方式1：create [or replace] view 视图名称[列名列表] as select 语句 [with [cascaded | local] check option]
方式2：alter view 视图名称[列名列表] as select 语句 [with [cascaded | local] check option]
create or replace view stu_v_1 as select id, name, no from student where id <=10;
alter view stu_v_1 as select id, name from student where id <=10;

-- 删除
drop view [if exists] 视图名称;
drop view if exists stu_v_1;
```



#### 检查选项

当使用WITH CHECK OPTION子句创建视图时，MySQL会通过视图检查正在更改的每个行，例如 插入，更新，删除，以使其符合视

图的定义。 MySQL允许基于另一个视图创建视图，它还会检查依赖视图中的规则以保持一致性。为了确定检查的范围，mysql提供了

两个选项：CASCADED 和 LOCAL ，默认值为 CASCADED 。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401105746671.png)

> insert into stu_v_2 values(15, 'Tom'); 就会成功，因为满足2个约束。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401110749356.png)

（1）当我们操作当前视图时，cascaded检查选项是，如果当前视图有检查选项，则插入数据要满足包括当前视图条件以及满足当前

视图所依赖的视图的条件。如果当前视图没有检查选项，则插入数据要满足当时视图所依赖视图有检查选项及其依赖的视图的条件。

（2）当我们在操作当前视图时，local检查选项是递归的查找当前视图所依赖的视图是否有检查选项，如果有，则检查；如果没有，就

不做检查。

https://blog.csdn.net/weixin_44300779/article/details/122805156



#### 更新

要使视图可更新，视图中的行与基础表中的行之间必须存在一对一的关系。如果视图包含以下任何一项，则该视图不可更新：

\1. 聚合函数或窗口函数（SUM()、 MIN()、 MAX()、 COUNT()等）

\2. DISTINCT

\3. GROUP BY

\4. HAVING

\5. UNION 或者 UNION ALL



#### 作用

简单：视图不仅可以简化用户对数据的理解，也可以简化他们的操作。那些被经常使用的查询可以被定义为视图，从而使得用户不必

为以后的操作每次指定全部的条件。

安全：数据库可以授权，但不能授权到数据库特定行和特定的列上。通过视图用户只能查询和修改他们所能见到的数据（保证了敏感数据的安全性）

数据独立：视图可帮助用户屏蔽真实表结构变化带来的影响。（屏蔽基表变化对业务的影响）

#### 案例

```sql
-- 根据如下需求，定义视图
1. 为了保证数据库表的安全性，开发人员在操作tb_user表时，只能看到的用户的基本字段，屏蔽手机号和邮箱两个字段。
create view tb_user_view as select id, name, profession, age, gender, status, createtime from tb_user;
select * from tb_user_view;
2. 查询每个学生所选修的课程（三张表联查），这个功能在很多的业务中都有使用到，为了简化操作，定义一个视图。
select s.name, s.no, c.name from student s, student_course sc, course c where s.id = sc.studentid and sc.courseid = c.id;
-- 多表联查的sql封装到视图中
create view tb_stu_course_view as select s.name student_name, s.no student_no, c.name course_name from student s, student_course sc, course c where s.id = sc.studentid and sc.courseid = c.id;
-- 后续直接查询视图就可以
select * from tb_stu_course_view;
```





### 10.2 存储过程

#### 介绍与特点

**介绍**

存储过程是事先经过编译并存储在数据库中的一段 SQL 语句的集合，调用存储过程可以简化应用开发人员的很多工作，减少数据在数

据库和应用服务器之间的传输，对于提高数据处理的效率是有好处的。

存储过程思想上很简单，就是数据库 SQL 语言层面的代码封装与重用。



**特点**

- 封装，复用
- 可以接收参数，也可以返回数据
- 减少网络交互，效率提升



```sql
-- 创建
create procedure 存储过程名称([参数列表])
begin
	-- sqlyuju 
end;	

create procedure p1()
begin
	select count(*) from student;
end;	

-- 调用
call 名称([参数])
call p1();

-- 查看
select * from information_schema.ROUTINES where ROUTINE_SCHEMA = 'itcast';    -- 查询指定数据库的存储过程及状态信息
show create procedure p1;

-- 删除
drop procedure if exists p1;
```



注意: 在命令行中，执行创建存储过程的SQL时，需要通过关键字 delimiter 指定SQL语句的结束符。



### 10.3 存储函数

存储函数是有返回值的存储过程，存储函数的参数只能是IN类型的。具体语法如下：





```sql
-- 1. 计算从1累加到n的值，n为传入的参数值。
```





### 10.4 触发器

触发器是与表有关的数据库对象，指在 insert/update/delete 之前或之后，触发并执行触发器中定义的SQL语句集合。

触发器的这种特性可以协助应用在数据库端确保数据的完整性 , 日志记录 , 数据校验等操作 。

使用别名 OLD 和 NEW 来引用触发器中发生变化的记录内容，这与其他的数据库是相似的。现在触发器还只支持行级触发（如update

影响5行，触发5次），不支持语句级触发。



```sql
-- 创建
create trigger trigger_name
before/after insert/update/delete
on tbl_name for each row  -- 行级触发器
begin
	trigger_stmt;
end;

-- 查看
show triggers;

-- 删除
drop trigger [schema_name] trigger_name;    -- 如果没有指定schema_name，默认当前数据库
```









```sql
-- 案例：定义触发器，完成如下需求
通过触发器记录 tb_user 表的数据变更日志，将变更日志插入到日志表user_logs中, 包含增加, 修改 , 删除 ;

-- 准备工作，日志表user_log
create table user_logs (
	id int(11) not null auto_increment,
  operation varchar(20) not null comment '操作类型, insert/update/delete',
  operation_time datetime not null comment '操作时间',
  operate_id int(11) not null comment'操作ID',
  operate_params varchar(500) comment'操作参数',
  primary key('id')
) engine=innodb default charset=utf8;

-- 插入数据触发器
create trigger tb_user_insert_trigger
		after insert on tb_user for each row
begin
		insert into user_log(id, operation, operation_time, operation_id, operation_params) values
		(null, 'insert', now(), new.id, concat('插入的数据内容为：id=', new.id' ,',name=', new.name,',phone=', new.phone,',email=', new.email,',',profession=', new.profession));
end;

-- 更新数据触发器
create trigger tb_user_update_trigger
		after update on tb_user for each row
begin
		insert into user_log(id, operation, operation_time, operation_id, operation_params) values
		(null, 'update', now(), new.id, 
     		concat('更新之前的数据内容为：id=', old.id' ,',name=', old.name,',phone=', old.phone,',email=', old.email,',',profession=', old.profession,
               '更新之后的数据内容为：id=', new.id' ,',name=', new.name,',phone=', new.phone,',email=', new.email,',',profession=', new.profession
              ));
end;

-- 删除数据触发器
create trigger tb_user_delete_trigger
		after delete on tb_user for each row
begin
		insert into user_log(id, operation, operation_time, operation_id, operation_params) values
		(null, 'delete', now(), old.id, 
     		concat('删除之前的数据内容为：id=', old.id' ,',name=', old.name,',phone=', old.phone,',email=', old.email,',',profession=', old.profession	));
end;
```



<hr>

```bash
# 小结
# 视图 view 
虚拟存在的表，不保存查询结果，只保存查询的SQL逻辑
简单、安全、数据独立

# 存储过程 procedure
事先定义并存储在数据库中的一段SQL语句的集合。
减少网络交互，提高性能、封装重用
变量、if、case、参数(in/out/inout)、while、repeat、loop、cursor、handler

# 存储函数 function
存储函数是有返回值的存储过程，参数类型只能为IN类型
存储函数可以被存储过程替代

# 触发器 trigger
可以在表数据进行INSERT、UPDATE、DELETE之前或之后触发
保证数据完整性、日志记录、数据校验
```





## 11. 锁🔐

### 11.1 概述

锁是计算机协调多个进程或线程并发访问某一资源的机制。

在数据库中，除传统的计算资源（CPU、RAM、I/O）的争用以外，数据也是一种供许多用户共享的资源。

如何保证数据并发访问的一致性、有效性是所有数据库必须解决的一个问题，锁冲突也是影响数据库并发访问性能的一个重要因素。

从这个角度来说，锁对数据库而言显得尤其重要，也更加复杂。



**MySQL中的锁，按照锁的粒度分，分为以下三类：**

1. 全局锁：锁定数据库中的所有表。

2. 表级锁：每次操作锁住整张表。

3. 行级锁：每次操作锁住对应的行数据。



<hr>

### 11.2 全局锁

全局锁就是对整个数据库实例加锁，加锁后整个实例就处于只读状态，后续的DML的写语句，DDL语句，已经更新操作的事务提交语

句都将被阻塞。



其典型的使用场景是**做全库的逻辑备份**，对所有的表进行锁定，从而获取一致性视图，保证数据的完整性。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401155803659.png)



如下，加了全局锁后，只可读不可写。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401171353342.png)





```sql
-- 加上全局锁
flush tables with read lock;

-- 备份库
mysqldump -h 192.168.200.202 -uroot -p123456 db01 > D:/db01.sql

-- 释放锁
unlock tables;
```



数据库中加全局锁，是一个比较重的操作，存在以下问题：

1. 如果在主库上备份，那么在备份期间都不能执行更新，业务基本上就得停摆。

2. 如果在从库上备份，那么在备份期间从库不能执行主库同步过来的二进制日志（binlog），会导致主从延迟。



在InnoDB引擎中，我们可以在备份时加上参数 --single-transaction 参数来完成不加锁的一致性数据备份。（快照读的方式）

```sql
mysqldump --single-transaction -uroot -p123456 db01 > db01.sql
```







### 11.3 表级锁

表级锁，每次操作锁住整张表。锁定粒度大，发生锁冲突的概率最高，并发度最低。应用在MyISAM、InnoDB、BDB等存储引擎中。

对于表级锁，主要分为以下三类：

#### 表锁

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401173346623.png)

读锁不会阻塞其他客户端的读，但是会阻塞写。写锁既会阻塞其他客户端的读，又会阻塞其他客户端的写。



#### 元数据锁（meta data lock，MDL）

MDL加锁过程是系统自动控制，无需显式使用，在访问一张表的时候会自动加上。MDL锁主要作用是维护表元数据的数据一致性，在

表上有活动事务的时候，不可以对元数据进行写入操作。为了避免DML与DDL冲突，保证读写的正确性。

在MySQL5.5中引入了MDL，当对一张表进行增删改查的时候，加MDL读锁(共享)；当对表结构进行变更操作的时候，加MDL写锁(排

他)。

| 对应SQL                                        | 锁类型                                  | 说明                                             |
| ---------------------------------------------- | --------------------------------------- | ------------------------------------------------ |
| lock tables xxx read / write                   | SHARED_READ_ONLY / SHARED_NO_READ_WRITE |                                                  |
| select 、select ... lock in share mode         | SHARED_READ                             | 与SHARED_READ、SHARED_WRITE兼容，与EXCLUSIVE互斥 |
| insert 、update、delete、select ... for update | SHARED_WRITE                            | 与SHARED_READ、SHARED_WRITE兼容，与EXCLUSIVE互斥 |
| alter table ...                                | EXCLUSIVE                               | 与其他的MDL都互斥                                |



查看元数据锁：

```sql
select object_type, object_schema, object_name, lock_type, lock_duration from performance_schema.metadata_locks;
```



#### 意向锁（解决行锁和表锁的冲突问题）

为了避免DML在执行时，加的行锁与表锁的冲突，在InnoDB中引入了意向锁，使得表锁不用检查每行数据是否加锁，使用意向锁来减

少表锁的检查。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401175409044.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401175558066.png)

##### 意向共享锁（IS）

由语句 select ... lock in share mode添加。

与表锁共享锁（read）兼容，与表锁排它锁（write）互斥。

##### 意向排他锁（IX）

由insert、update、delete、select ... for update 添加。

与表锁共享锁（read）及排它锁（write）都互斥。意向锁之间不会互斥。



可以通过以下SQL，查看意向锁及行锁的加锁情况：

```sql
select object_schema, object_name, index_name, lock_type, lock_mode, lock_data from performance_schema.data_locks;
```





### 11.4 行级锁

行级锁，每次操作锁住对应的行数据。锁定粒度最小，发生锁冲突的概率最低，并发度最高。应用在InnoDB存储引擎中。



InnoDB的数据是基于索引组织的，行锁是通过对索引上的索引项加锁来实现的，而不是对记录加的锁。对于行级锁，主要分为以下三

类：

- 行锁（Record Lock）：锁定单个行记录的锁，防止其他事务对此行进行update和delete。在RC、RR隔离级别下都支持。

- 间隙锁（Gap Lock）：锁定索引记录间隙（不含该记录），确保索引记录间隙不变，防止其他事务在这个间隙进行insert，产生幻

  读。在RR隔离级别下都支持。

- 临键锁（Next-Key Lock）：行锁和间隙锁组合，同时锁住数据，并锁住数据前面的间隙Gap。在RR隔离级别下支持。



![image-20220401181158018](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401181158018.png)



InnoDB实现了以下两种类型的行锁：

- 共享锁（S）：允许一个事务去读一行，阻止其他事务获得相同数据集的排它锁。

- 排他锁（X）：允许获取排他锁的事务更新数据，阻止其他事务获得相同数据集的共享锁和排他锁。



| 当前锁类型/请求锁类型 | S（共享锁） | X（排它锁） |
| --------------------- | ----------- | ----------- |
| S（共享锁）           | 兼容        | 冲突        |
| X（排它锁）           | 冲突        | 冲突        |



| SQL                           | 行锁类型    | 说明                                     |
| ----------------------------- | ----------- | ---------------------------------------- |
| insert...                     | 排它锁      | 自动加锁                                 |
| update...                     | 排它锁      | 自动加锁                                 |
| delete...                     | 排它锁      | 自动加锁                                 |
| select（正常）                | 不加任何锁🔐 |                                          |
| select ... lock in share mode | 共享锁      | 需要手动在select之后加lock in share mode |
| select ... for update         | 排它锁      | 需要手动在select之后加for update         |





**行锁演示：**

默认情况下，InnoDB在 REPEATABLE READ事务隔离级别运行，InnoDB使用 next-key 锁进行搜索和索引扫描，以防止幻读。

- 针对唯一索引进行检索时，对已存在的记录进行等值匹配时，将会自动优化为行锁。

- InnoDB的行锁是针对于索引加的锁，**不通过索引条件检索数据，那么InnoDB将对表中的所有记录加锁**，此时 **就会升级为表锁**。

可以通过以下SQL，查看意向锁及行锁的加锁情况：

```sql
select object_schema, object_name, index_name, lock_type, lock_mode, lock_data from performance_schema.data_locks;
```



**间隙锁/临键锁-演示：**

默认情况下，InnoDB在 REPEATABLE READ事务隔离级别运行，InnoDB使用 next-key 锁进行搜索和索引扫描，以防止幻读。



- 索引上的等值查询(唯一索引)，给不存在的记录加锁时, 优化为间隙锁 。
- 索引上的等值查询(普通索引)，向右遍历时最后一个值不满足查询需求时，next-key lock 退化为间隙锁。
- 索引上的范围查询(唯一索引)--会访问到不满足条件的第一个值为止。



注意：间隙锁唯一目的是防止其他事务插入间隙。间隙锁可以共存，一个事务采用的间隙锁不会阻止另一个事务在同一间隙上采用间

隙锁。

<hr>

```bash
# 概述
在并发访问时，解决数据访问的一致性、有效性问题
全局锁、表级锁、行级锁

# 全局锁
对整个数据库实例加锁，加锁后整个实例就处于只读状态
性能较差，数据逻辑备份时使用

# 表级锁
操作锁住整张表，锁定粒度大，发生锁冲突的概率高
表锁、元数据锁、意向锁

# 行级锁
操作锁住对应的行数据，锁定粒度最小，发生锁冲突的概率最低
行锁、间隙锁（记录间的间隙）（主要是为了避免多个事务操作时出现幻读现象）、临键锁（记录和间隙）
```







## 12. InnoDB引擎（理解为主）

### 12.1 逻辑存储结构

![image-20220401201311779](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401201311779.png)





### 12.2 架构（内存架构和磁盘架构）

MySQL5.5 版本开始，默认使用InnoDB存储引擎，它擅长事务处理，具有崩溃恢复特性，在日常开发中使用非常广泛。下面是InnoDB

架构图，左侧为内存结构，右侧为磁盘结构。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401201620483.png)

#### 内存架构

##### （1）缓冲池 Buffer Pool

Buffer Pool：缓冲池是主内存中的一个区域，里面可以缓存磁盘上经常操作的真实数据，在执行增删改查操作时，先操作缓冲池中的

数据（若缓冲池没有数据，则从磁盘加载并缓存），然后再以一定频率刷新到磁盘，从而减少磁盘IO，加快处理速度。



缓冲池以Page页为单位，底层采用链表数据结构管理Page。根据状态，将Page分为三种类型：

-  free page：空闲page，未被使用。

- clean page：被使用page，数据没有被修改过。

- dirty page：脏页，被使用page，数据被修改过，也中数据与磁盘的数据产生了不一致。



##### （2）更改缓冲区 Change Buffer

Change Buffer：更改缓冲区（针对于非唯一二级索引页），在执行DML语句时，如果这些数据Page没有在Buffer Pool中，不会直接

操作磁盘，而会将数据变更存在更改缓冲区 Change Buffer 中，在未来数据被读取时，再将数据合并恢复到Buffer Pool中，再将合并

后的数据刷新到磁盘中。

**Change Buffer的意义是什么?** 

与聚集索引不同，二级索引通常是非唯一的，并且以相对随机的顺序插入二级索引。同样，删除和更新可能会影响索引树中不相邻的

二级索引页，如果每一次都操作磁盘，会造成大量的磁盘IO。有了ChangeBuffer之后，我们可以在缓冲池中进行合并处理，减少磁盘

IO。



##### （3）自适应Hash

Adaptive Hash Index：自适应hash索引，用于优化对Buffer Pool数据的查询。InnoDB存储引擎会监控对表上各索引页的查询，如果观

察到hash索引可以提升速度，则建立hash索引，称之为自适应hash索引。



自适应哈希索引，无需人工干预，是系统根据情况自动完成。

参数： adaptive_hash_index





##### （4）日志缓冲区Log Buffer

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401203021589.png)





<hr>

#### 磁盘结构



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401203753042.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401203815305.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401203833458.png)





#### 后台线程（内存中的数据如何刷新到磁盘中）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401215350625.png)



**1. Master Thread**

核心后台线程，负责调度其他线程，还负责将缓冲池中的数据异步刷新到磁盘中, 保持数据的一致性，还包括脏页的刷新、合并插入缓

存、undo页的回收 。

**2. IO Thread**

在InnoDB存储引擎中大量使用了AIO来处理IO请求, 这样可以极大地提高数据库的性能，而IO Thread主要负责这些IO请求的回调。

| 线程类型             | 默认个数 | 职责                         |
| -------------------- | -------- | ---------------------------- |
| Read thread          | 4        | 负责读操作                   |
| Write thread         | 4        | 负责写操作                   |
| Log thread           | 1        | 负责将日志缓冲区刷新到磁盘   |
| Insert buffer thread | 1        | 负责将写缓冲区内容刷新到磁盘 |



**3. Purge Thread**

主要用于回收事务已经提交了的undo log，在事务提交之后，undo log可能不用了，就用它来回收。

**4. Page Cleaner Thread**

协助 Master Thread 刷新脏页到磁盘的线程，它可以减轻 Master Thread 的工作压力，减少阻塞。



### 12.3 事务管理（作为默认存储引擎，很大一部分原因在于支持事务）

事务 是一组操作的集合，它是一个不可分割的工作单位，事务会把所有的操作作为一个整体一起向系统提交或撤销操作请求，即这些

操作要么同时成功，要么同时失败。

- 原子性（Atomicity）：事务是不可分割的最小操作单元，要么全部成功，要么全部失败。
- 一致性（Consistency）：事务完成时，必须使所有的数据都保持一致状态。
- 隔离性（Isolation）：数据库系统提供的隔离机制，保证事务在不受外部并发操作影响的独立环境下运行。
- 持久性（Durability）：事务一旦提交或回滚，它对数据库中的数据的改变就是永久的。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401215844958.png)



#### redo log（持久性）

重做日志，记录的是事务提交时数据页的物理修改，是用来实现事务的持久性。

该日志文件由两部分组成：重做日志缓冲（redo log buffer）以及重做日志文件（redo log file）,前者是在内存中，后者在磁盘中。当

事务提交之后会把所有修改信息都存到该日志文件中, 用于在刷新脏页到磁盘,发生错误时, 进行数据恢复使用。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401220540789.png)





#### undo log（原子性）

回滚日志，用于记录数据被修改前的信息 , 作用包含两个 : **提供回滚** 和 **MVCC(多版本并发控制)** 。

undo log和redo log记录物理日志不一样，它是逻辑日志。可以认为当delete一条记录时，undo log中会记录一条对应的insert记录，反

之亦然，当update一条记录时，它记录一条对应相反的update记录。当执行rollback时，就可以从undo log中的逻辑记录读取到相应的

内容并进行回滚。



Undo log销毁：undo log在事务执行时产生，事务提交时，并不会立即删除undo log，因为这些日志可能还用于MVCC。

Undo log存储：undo log采用段的方式进行管理和记录，存放在前面介绍的 rollback segment 回滚段中，内部包含1024个undo log 

segment。





### 12.4 MVCC（事务相关）（多版本并发控制）（高频面试题）

#### 基本概念

- 当前读：读取的是记录的最新版本，读取时还要保证其他并发事务不能修改当前记录，会对读取的记录进行加锁。对于我们日常

  的操作，如：select ... lock in share mode(共享锁)，select ... for update、update、insert、delete(排他锁)都是一种当前读。

- 快照读：简单的select（不加锁）就是快照读，快照读，读取的是记录数据的可见版本，有可能是历史数据，不加锁，是非阻塞

  读。

  - Read Committed：每次select，都生成一个快照读。

  - Repeatable Read：开启事务后第一个select语句才是快照读的地方。
  - Serializable：快照读会退化为当前读。

- MVCC：全称 Multi-Version Concurrency Control，多版本并发控制。指维护一个数据的多个版本，使得读写操作没有冲突，快照

  读为MySQL实现MVCC提供了一个非阻塞读功能。MVCC的具体实现，还需要依赖于数据库记录中的三个隐式字段、undo log日

  志、readView。



#### 实现原理

##### 记录中的隐藏字段

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401221925563.png)



| 隐藏字段    | 含义                                                         |
| ----------- | ------------------------------------------------------------ |
| DB_TRX_ID   | 最近修改事务ID，记录插入这条记录或最后一次修改该记录的事务ID。 |
| DB_ROLL_PTR | 回滚指针，指向这条记录的上一个版本，用于配合undo log，指向上一个版本。 |
| DB_ROW_ID   | 隐藏主键，如果表结构没有指定主键，将会生成该隐藏字段。       |



##### undo log

回滚日志，在insert、update、delete的时候产生的便于数据回滚的日志。

- 当insert的时候，产生的undo log日志只在回滚时需要，在事务提交后，可被立即删除。

- 而update、delete的时候，产生的undo log日志不仅在回滚时需要，在快照读时也需要，不会立即被删除。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401223007731.png)



不同事务或相同事务对同一条记录进行修改，会导致该记录的undolog生成一条记录版本链表，链表的头部是最新的旧记录，链表尾部

是最早的旧记录。



##### readView

ReadView（读视图）是 快照读 SQL执行时MVCC提取数据的依据，记录并维护系统当前活跃的事务（未提交的）id。



ReadView中包含了四个核心字段：

| 字段           | 含义                                                 |
| -------------- | ---------------------------------------------------- |
| m_ids          | 当前活跃的事务ID集合                                 |
| min_trx_id     | 最小活跃事务ID                                       |
| max_trx_id     | 预分配事务ID，当前最大事务ID+1（因为事务ID是自增的） |
| creator_trx_id | ReadView创建者的事务ID                               |



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401223807981.png)



不同的隔离级别，生成ReadView的时机不同：

- READ COMMITTED ：在事务中每一次执行快照读时生成ReadView。 
- REPEATABLE READ：仅在事务中第一次执行快照读时生成ReadView，后续复用该ReadView。



###### RC隔离级别下快照读的时候，数据版本提取时的底层原理

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401224552274.png)



###### RR隔离级别下快照读的时候，数据版本提取时的底层原理

RR隔离级别下，仅在事务中第一次执行快照读时生成ReadView，后续复用该ReadView。

> P146：可重复读，同一个事务当中读取两条相同的数据应该是一样的，两个ReadView都一样其匹配规则也一样，在版本链查找出来的数据也应该是一样的。这就保证了可重复读。



MVCC主要作用就是：在快照读的时候，决定提取的到底是哪一个记录的版本。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401225400664.png)



<hr>



```bash
# 逻辑存储结构
表空间、段、区1M、页16K、行
# 架构
内存结构（主要是缓冲区）
磁盘结构
# 事务原理（四大特性底层是如何实现的）
原子性 – undo log
持久性 – redo log
一致性 – undo log + redo log
隔离性 – 锁 + MVCC
# MVCC
记录隐藏字段、undo log版本链、readView
```



## 13. MySQL管理

### 13.1 系统数据库

Mysql数据库安装完成后，自带了一下四个数据库，具体作用如下：

| 数据库              | 含义                                                         |
| ------------------- | ------------------------------------------------------------ |
| mysql               | 存储MySQL服务器正常运行所需要的各种信息（时区、主从、用户、权限等） |
| informastion_schema | 提供了访问数据库元数据的各种表和视图，包括数据库、表、字段类型及访问全新等 |
| performance_schema  | 为MySQL服务器运行时状态提供了一个底层监控功能，主要用于收集数据库服务器性能参数 |
| sys                 | 包含了一系列方便DBA和开发人员利用performance_schema性能数据库进行性能调优和诊断的视图 |



### 13.2 常用工具

#### （1）mysql（MySQL客户端工具，-e执行SQL并退出）

该mysql不是指mysql服务，而是指mysql的客户端工具。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401231522101.png)



**-e **选项可以在Mysql客户端执行SQL语句，而不用连接到MySQL数据库再执行，对于一些批处理脚本，这种方式尤其方便。

```sql
mysql -h192.168.200.202 -P3306 -uroot -p123456 itcast -e "select * from stu";
```



#### （2）mysqladmin（MySQL管理工具）

mysqladmin 是一个执行管理操作的客户端程序。可以用它来检查服务器的配置和当前状态、创建并删除数据库等。

通过帮助文档查看选项：`mysqladmin --help`

```sql
mysqladmin -uroot -p123456 version;
mysqladmin -uroot -p123456 variables;
```



#### （3）mysqbinlog（二进制日志查看工具）

由于服务器生成的二进制日志文件以二进制格式保存，所以如果想要检查这些文本的文本格式，就会使用到mysqlbinlog 日志管理

工具。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401232422653.png)



#### （4）mysqlshow（查看数据库、表、字段的统计信息）

mysqlshow 客户端对象查找工具，用来很快地查找存在哪些数据库、数据库中的表、表中的列或者索引。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401232657606.png)



#### （5）mysqldump（数据备份工具）

mysqldump 客户端工具用来备份数据库或在不同数据库之间进行数据迁移。备份内容包含创建表，及插入表的SQL语句。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220401232733109.png)





#### （6）mysqlimport/source（数据导入工具）

mysqlimport 是客户端数据导入工具，用来导入mysqldump 加 -T 参数后导出的文本文件。

```sql
mysqlimport [options] db_name textfile1 [textfile2...]
mysqlimport -uroot -p123456 test /tmp/city.txt
```



如果需要导入sql文件,可以使用mysql中的source 指令 :

```sql
source /root/xxx.sql
```





<hr>

# 运维篇

## 14. 日志（各种日志以及日志的作用）

### 14.1 错误日志

错误日志是 MySQL 中最重要的日志之一，它记录了当 mysqld 启动和停止时，以及服务器在运行过程中发生任何严重错误时的相关信

息。当数据库出现任何故障导致无法正常使用时，建议首先查看此日志。



该日志是默认开启的，默认存放目录 /var/log/，默认的日志文件名为 mysqld.log 。查看日志位置：

```sql
show variables like %log_error%;
```





### 14.2 二进制日志DDL和DML

#### 介绍

二进制日志（BINLOG）记录了所有的 DDL（数据定义语言）语句和 DML（数据操纵语言）语句，**但不包括数据查询（SELECT、**

**SHOW）语句。**

作用：①. 灾难时的数据恢复；②. MySQL的主从复制。在MySQL8版本中，默认二进制日志是开启着的，涉及到的参数如下：

```sql
show variables like '%log_bin%';
```



`/var/lib/mysql/binlog.index`记录了当前关联的日志文件有哪些。



#### 日志格式

MySQL服务器中提供了多种格式来记录二进制日志，具体格式及特点如下：

| 日志格式  | 含义                                                         |
| --------- | ------------------------------------------------------------ |
| statement | 基于SQL语句的日志记录，记录的是SQL语句，对数据进行修改的SQL都会记录在日志文件中。 |
| row       | 基于行的日志记录，记录的是每一行的数据变更。（默认）         |
| mixed     | 混合了STATEMENT和ROW两种格式，默认采用STATEMENT，在某些特殊情况下会自动切换为ROW进行记录。 |



```sql
show variables like '%binlog_format%';
```



row：如果使用update更新了3行，那么会记录这三行数据变化前后的样子，使用如下命令查看

```bash
mysqlbinlog -v binlog.000002
```

statement：记录的是sql语句，使用如下命令查看

```bash
mysqlbinlog binlog.000003
```







#### 日志查看

由于日志是以二进制方式存储的，不能直接读取，需要通过二进制日志查询工具 mysqlbinlog 来查看，具体语法：

```bash
mysqlbinlog [参数选项] logfilename

参数选项：
		-d		指定数据库名称，只列出指定的数据库相关操作。
		-o		忽略掉日志中的前 行命令。
		-v		将行事件（数据变更）重构为sql语句
		-w		将行事件（数据变更）重构为sql语句，并输出注释信息
```





#### 日志删除

对于比较繁忙的业务系统，每天生成的binlog数据巨大，如果长时间不清除，将会占用大量磁盘空间。可以通过以下几种方式清理日

志：

| 指令                                             | 含义                                                         |
| ------------------------------------------------ | ------------------------------------------------------------ |
| reset master                                     | 删除全部 binlog 日志，删除之后，日志编号，将从 binlog.000001重新开始 |
| purge master logs to 'binlog.000002'             | 删除 000002 编号之前的所有日志                               |
| purge master logs before 'yyyy-mm-dd hh24:mi:ss' | 删除日志为 "yyyy-mm-dd hh24:mi:ss" 之前产生的所有日志        |



也可以在mysql的配置文件中配置二进制日志的过期时间，设置了之后，二进制日志过期会自动删除。默认存放30天。

```bash
show variables like '%binlog_expire_logs_seconds%';
```





### 14.3 查询日志（记录所有sql语句，默认不开启）

查询日志中记录了客户端的所有操作语句，而二进制日志不包含查询数据的SQL语句。默认情况下， 查询日志是未开启

的。如果需要开启查询日志，可以设置以下配置 ：

```sql
show variables like '%general%';

-- 修改MySQL的配置文件/etc/my.cnf文件，添加如下内容：
# 开启查询日志，0开启 1关闭
general_log=1
# 设置日志的文件名，若未指定，默认文件名为host_name.log
general_log_file=mysql_query.log
```





### 14.4 慢查询日志

慢查询日志记录了所有执行时间超过参数 `long_query_time` 设置值并且扫描记录数不小于 min_examined_row_limit

的所有的SQL语句的日志，默认未开启。long_query_time 默认为 10 秒，最小为 0， 精度可以到微秒。

```bash
# 开启慢查询日志
slow_query_log=1
# 执行时间参数
long_query_time=2
```





默认情况下，不会记录管理语句，也不会记录不使用索引进行查找的查询。可以使用log_slow_admin_statements和

log_queries_not_using_indexes更改此行为，如下所述。

```sql
-- 记录执行较慢的管理语句
log_slow_admin_statements=1
-- 记录执行较慢的未使用索引的语句
log_queries_not_using_indexes=1
```







## 15. 主从复制（概念、原理、搭建集群）

### 15.1 概述



主从复制是指将主数据库的DDL 和 DML 操作通过二进制日志传到从库服务器中，然后在从库上对这些日志重新执行（也叫重做），

从而使得从库和主库的数据保持同步。



MySQL支持一台主库同时向多台从库进行复制， 从库同时也可以作为其他从服务器的主库，实现链状复制。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402095858761.png)



### 15.2 原理

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402095958529.png)





从上图来看，复制分成三步：

1. Master 主库在事务提交时，会把数据变更记录在二进制日志文件 Binlog 中。

2. 从库读取主库的二进制日志文件 Binlog ，写入到从库的中继日志 Relay Log 。

3. slave重做中继日志中的事件，将改变反映它自己的数据。



### 15.3 搭建

#### 准备服务器

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402145457925.png)

准备好两台服务器之后，在上述的两台服务器中分别安装好MySQL，并完成基础的初始化准备工作。



#### 配置主库



- 修改配置文件 /etc/my.cnf

```bash
# mysql服务ID，保证整个集群环境中唯一，取值范围1~2^32-1，默认为1
server-id=1
# 是否只读：1只读  0读写
read-only=0
# 指定不需要同步的数据库
#binlog-ignore-db=mysql
# 指定同步的数据库
#binlog-do-db=db01
```

- 重启mysql服务器

```bash
systemctl restart mysqld
```



- 登录mysql，创建远程连接的账号，并授予主从复制权限

```bash
# 创建itcast用户，并设置密码，该用户可在任意主机连接该MySQL服务
create user 'itcast'@'%' identified with mysql_native_password by 'Root@123456';
# 为'itcast'@'%'用户分配主从复制权限
grant replication slave on *.* to 'itcast'@'%';
```



- 通过指令，查看二进制日志坐标

```bash
show master status;
```



**字段含义说明：**

file : 从哪个日志文件开始推送日志文件

position ： 从哪个位置开始推送日志

binlog_ignore_db : 指定不需要同步的数据库



#### 配置从库



- 修改配置文件 /etc/my.cnf

```bash
# mysql服务ID，保证整个集群环境中唯一，取值范围1~2^32-1，默认为1
server-id=2
# 是否只读：1只读  0读写
read-only=1
```

- 重启mysql服务器

```bash
systemctl restart mysqld
```

- 登录mysql，设置主库配置

```sql
change replication source to source_host 'xxx.xxx', source_user='xxx', source_password='xxx', source_log_file='xxx', source_log_pos='xxx';
```



上述是8.0.23中的语法。如果是之前版本，执行如下sql

```sql
change master to master_host 'xxx.xxx', master_user='xxx', master_password='xxx', master_log_file='xxx', master_log_pos='xxx';
```



| 参数名          | 含义               | 8.0.23之前      |
| --------------- | ------------------ | --------------- |
| source_host     | 主库IP地址         | master_host     |
| source_user     | 连接主库的用户名   | master_user     |
| source_password | 连接主库的密码     | master_password |
| source_log_file | binlog日志文件名   | master_log_file |
| source_log_pos  | binlog日志文件位置 | master_log_pos  |



- 开启同步操作

```bash
start replica;  # 8.0.22之后
start slave;  # 8.0.22之前
```





-  查看主从同步状态

```bash
show replica status;  # 8.0.22之后
show slave status;  # 8.0.22之前
```





#### 测试主从复制

- 在主库上创建数据库、表，并插入数据

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402151716973.png)



- 在从库中查询数据，验证主从是否同步





<hr>

```bash
# 概述
将主库的数据变更同步到从库，从而保证主库和从库数据一致。
数据备份、失败迁移，读写分离，降低单库读写压力。
# 原理
① 主库会把数据变更记录在二进制日志文件binlog中。
② 从库连接主库，读取binlog日志，并写入自身中继日志 relaylog。 
③ slave重做中继日志，将改变反映它自己的数据。
# 搭建
① 准备服务器
② 配置主库
③ 配置从库
④ 测试主从复制

# 思考🤔
Master主动向Slave发送binlog?还是Slave主动向Master要binlog?



https://blog.csdn.net/crty2245/article/details/100238847
```





## 16. 分库分表（重要内容）

### 介绍



随着互联网及移动互联网的发展，应用系统的数据量也是成指数式增长，若采用单数据库进行数据存储，存在以下性能瓶颈：

1. IO瓶颈：热点数据太多，数据库缓存不足，产生大量磁盘IO，效率较低。 请求数据太多，带宽不够，网络IO瓶颈。

2. CPU瓶颈：排序、分组、连接查询、聚合统计等SQL会耗费大量的CPU资源，请求数太多，CPU出现瓶颈。



分库分表的中心思想都是将数据分散存储，使得单一数据库/表的数据量变小来缓解单一数据库的性能问题，从而达到提升数据库性能

的目的。



#### 拆分策略之垂直拆分

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402152153389.png)



#### 拆分策略之水平拆分

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402152214334.png)



#### 实现技术

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220402152342731.png)



- shardingJDBC：基于AOP原理，在应用程序中对本地执行的SQL进行拦截，解析、改写、路由处理。需要自行编码配置实现，只

  支持java语言，性能较高。

- MyCat：数据库分库分表中间件，不用调整代码即可实现分库分表，支持多种语言，性能不及前者。



<hr>

### Mycat概述

Mycat是开源的、活跃的、基于Java语言编写的MySQL**数据库中间件**。**可以像使用mysql一样来使用mycat**，对于开发人员来说根

本感觉不到mycat的存在。



优势：

- 性能可靠稳定
- 强大的技术团队
- 体系完善
- 社区活跃



#### 下载及安装

Mycat是采用java语言开发的开源的数据库中间件，支持Windows和Linux运行环境，下面介绍MyCat的Linux中的环境搭建。我们需要

在准备好的服务器中安装如下软件。

- MySQL

- Jdk
- MyCat



| 服务器         | 安装软件          | 说明                          |
| -------------- | ----------------- | ----------------------------- |
| 101.42.229.218 | Jdk、MySQL、MyCat | MyCat中间件服务器、分片服务器 |
| 39.101.189.62  | MySQL             | 分片服务器                    |





```bash
http://www.mycat.org.cn/mycat1.html
http://dl.mycat.org.cn/1.6.7.4/Mycat-server-1.6.7.4-release/

tar -zxvf Mycat-server-1.6.7.3-release-20210913163959-linux.tar.gz -C /usr/local/
```



#### 目录结构

- bin : 存放可执行文件，用于启动停止mycat
- conf：存放mycat的配置文件
- lib：存放mycat的项目依赖包（jar）
- logs：存放mycat的日志文件



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220403143705825.png)



### Mycat入门

由于 tb_order 表中数据量很大，磁盘IO及容量都到达了瓶颈，现在需要对 tb_order表进行数据分片，分为三个数据节点，每一个节

点主机位于不同的服务器上, 具体的结构，参考下图：



```bash
# UltraEdit连接远端Linux进行文件编辑
https://blog.csdn.net/zhangjunfei12103323/article/details/72654308
```



#### 分片配置（schema.xml）

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
	
	<schema name="DB01" checkSQLschema="true" sqlMaxLimit="100">
		<!-- auto sharding by id (long) -->
		<table name="TB_ORDER" dataNode="dn1,dn2" rule="auto-sharding-long" />
	</schema>

	<dataNode name="dn1" dataHost="dhost1" database="db01" />
	<dataNode name="dn2" dataHost="dhost2" database="db01" />

	<dataHost name="dhost1" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="jdbc" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>

		<writeHost host="master" url="jdbc:mysql://101.42.229.218:3306?useSSL=false&amp;serverTimezone=Asia/Shanghai&amp;characterEncoding=utf8" user="root" password="123456" />
	</dataHost>
	
		<dataHost name="dhost2" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="jdbc" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>

		<writeHost host="master" url="jdbc:mysql://39.101.189.62:3306?useSSL=false&amp;serverTimezone=Asia/Shanghai&amp;characterEncoding=utf8" user="root" password="123456" />
	</dataHost>
</mycat:schema>
```



#### 分片配置（server.xml）

配置mycat的用户及用户的权限信息:

```xml
	<user name="root" defaultAccount="true">
		<property name="password">123456</property>
		<property name="schemas">DB01</property>
		
		<!-- 表级 DML 权限设置 -->
		<!-- 		
		<privileges check="false">
			<schema name="TESTDB" dml="0110" >
				<table name="tb01" dml="0000"></table>
				<table name="tb02" dml="1111"></table>
			</schema>
		</privileges>		
		 -->
	</user>

	<user name="user">
		<property name="password">123456</property>
		<property name="schemas">DB01</property>
		<property name="readOnly">true</property>
	</user>
```



#### 启动服务

切换到Mycat的安装目录，执行如下指令，启动Mycat：

```bash
# 启动，占用端口号 8066。
bin /mycat start
[root@VM-24-10-centos bin]# ./mycat start
# 停止
bin /mycat stop

# 启动完毕之后，查看日志是否启动成功
cat -b wrapper.log

# 连接并登陆MyCat
[root@192 ~]# mysql -h 39.101.189.62 -P 8066 -u root -p
```





```bash
# 几个坑
1. 使用阿里云等服务器，要开放8066端口。
2. MyCat建议使用1.6.7.4版本
3. schema 的值不要用小写
<schema name="DB01" checkSQLschema="true" sqlMaxLimit="100">


# 推荐参考：
## 官方强大的文档
https://github.com/MyCATApache/Mycat-Server/wiki/3.0-Mycat%E9%85%8D%E7%BD%AE%E5%85%A5%E9%97%A8
## 其它
https://blog.csdn.net/weixin_44649811/article/details/116503119
https://www.cnblogs.com/JennyYu/p/14394504.html
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220403171533361.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220403171548106.png)



```bash
# 阿里云
## docker
[root@192 ~]# docker -v
Docker version 20.10.14, build a224086


# 腾讯云
## CentOS8
CentOS8 Linux发行版止步在了CentOS8版本，并且将在2021年12月31日停止维护（原本EOL时间是2029年5月31日的），替代它的是CentOS Stream滚动发行版。
解决办法：https://blog.csdn.net/weixin_42131383/article/details/123160464
cd /etc/yum.repos.d/
rm *.repo #
wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo 
curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
yum makecache
重新运行 yum install -y yum-utils命令 安装依赖
## docker
[root@192 ~]# docker -v
Docker version 20.10.14, build a224086
```



```bash
# 安装docker

# 1、yum 包更新到最新 
yum update
# 2、安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的 
yum install -y yum-utils device-mapper-persistent-data lvm2
# 3、 设置yum源
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
# 4、 安装docker，出现输入的界面都按 y 
yum install -y docker-ce
# 5、 查看docker版本，验证是否验证成功
docker -v

# 启动docker
systemctl start docker
# 启动后，查看启动状态
systemctl status docker
# docker search mysql

# 拉取镜像
docker pull mysql:8.0.27
# 查看镜像
[root@VM-24-10-centos yum.repos.d]# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
mysql        8.0.27    3218b38490ce   3 months ago   516MB

# 
lsof -i tcp:3306
kill 3306;
https://www.cnblogs.com/Rui6/p/14599965.html
lsof不可用时使用
netstat -tunlp|grep 3306

# 
docker run --name db1 -p 3326:3306 -v /root/db2/my.cnf:/etc/mysql/my.cnf -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.27
docker run --name db2 -p 3326:3306 -v /root/db2/my.cnf:/etc/mysql/my.cnf -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.27
docker images;

```





### Mycat配置





### Mycat分片（核心）







### Mycat管理及监控







## 17. 读写分离

### 17.1 介绍







### 17.2 一主一从







### 17.3 一主一从读写分离







### 17.4 双主双从









### 17.5双主双从读写分离







# 写在后面

做个自律的人

































