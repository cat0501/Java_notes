# 

- Hadoop3.x





# 大数据概论

## 概念

大数据（Big Data）：指无法在一定时间范围内用常规软件工具进行捕捉、管理和处理的数据集合，是需要新处理模式才能具有更强的决策力、洞察发现力和流程优化能力的海量、高增长率和多样化的信息资产。

大数据主要解决，**海量**数据的**采集、存储和分析计算**问题。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220803215533752.png)



## 特点 4V

- 大量 Volume
  - 截至目前，人类生产的所有印刷材料的数据量是200PB，而历史上全人类总共说过的话的数据量大约是5EB。当前，典型个人计算机硬盘的容量为TB量级，而一些大企业的数据量已经接近EB量级。
- 高速 Velocity
  - 大数据区分于传统数据挖掘的最显著特征。海量数据面前，处理数据的效率就是企业的生命。
- 多样 Variety
  - 分为结构化数据和非结构化数据。
  - 非结构化数据越来越多，包括日志、音频、视频、图片、地理位置信息等，多类型的数据对数据的处理能力提出了更高要求。
- 低价值密度 Value
  - 价值密度的高低与数据总量的大小成反比。
  - 如何快速对有价值数据”提纯“成为目前大数据背景下待解决的难题。



## 应用场景

- 抖音：推荐你喜欢的视频
- 电商：广告推荐，给用户推荐可能喜欢的产品
- 零售：分析用户消费习惯，为用户购买商品提供方便，从而提升商品销量。经典案例，纸尿布 + 啤酒。
- 物流仓储：京东物流
- 保险：海量数据挖掘及风险预测，精准营销，提升精细化定价能力。
- 金融：帮助金融机构推荐优质客户
- 房产：精准推测与营销
- 人工智能 + 5G + 物联网 + 虚拟与现实

## 发展前景





## 大数据部门间业务流程分析

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804075651997.png)











## 大数据部门内组织架构





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220803221504164.png)









# Hadoop入门

- 课程升级内容
  - yarn
  - 生产调优手册
  - 源码
- 课程特色
  - 新 hadoop 3.1.3
  - 细 从搭建集群开始 每一个配置 每一行代码都有注释
  - 真 20+企业案例 30+企业调优 从百万代码中阅读源码
  - 全 全套资料
- 技术基础要求
  - JavaSE
  - maven
  - Idea
  - Linux常用命令



## 1 概述

### 1.1 是什么

- 分布式系统基础结构
- 主要解决，海量数据的存储和海量数据的分析计算问题
- 广义上讲，指一个更广泛的概念——Hadoop生态圈

### 1.2 发展历史（了解）

- 可以说Google是Hadoop的思想之源（Google在大数据方面的三篇论文)
  - GFS--->HDFS
  - Map-Reduce --->MR
  - BigTable--->HBase



### 1.3 发行版本（了解）

- Apache
- Cloudera
- Hortonworks

### 1.4 优势（4高）

- 高可靠性：底层维护多个数据副本
- 高扩展性：在集群间分配任务数据，可方便地扩展数以千计的结点

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804081554349.png)

- 高效性：并行工作的，以加快任务处理速度

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804081642809.png)

- 高容错性：能够自动将失败的任务重试





### 1.5 组成🎈（面试重点）

- `1.x`、`2.x`、`3.x`区别

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804081750498.png)



#### HDFS 架构概述（分布式文件系统）（存储）

- Hadoop Distributed File System
- 解决存储问题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220805223542512.png)



#### YARN（资源管理器）

- Yet Another Resource Negotiator
- Hadoop的资源管理器，主要管理CPU和内存

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804082125762.png)





#### MapReduce 架构概述（计算）

MapReduce将计算过程分为两个阶段：Map 和 Reduce

- 1）Map 阶段并行处理输入数据

- 2）Reduce 阶段对Map 结果进行汇总



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804082341454.png)



#### 三者关系



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804082424264.png)





### 1.6 大数据技术生态体系 🎈

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804082525280.png)



图中涉及的技术名词解释如下：🎈

1）Sqoop：Sqoo是一款开源的工具，主要用于在**Hadoop、Hive 与传统的数据库 (MySQL) 间进行数据的传递**，可以将一个关系型数据库（例如 : MySQL, Oracle 等）中的数据导进到 Hadoop 的 `HDFS`中，也可以将HDFS的数据导进到关系型数据库中。

2）Flume.：Flllm 是一个高可用的、高可靠的，分布式的**海量日志采集、聚合和传输的系统**，Flume 支持在日志系统中定制各类数据发送方，用于收集数据；

3）Kafka：Kafka是一种**高吞吐量的分布式发布订阅消息系统**；

4）Spark：Spark 是**当前最流行的开源大数据内存计算框架**。可以基于Hadoop上存储的大数据进行计算。

5）Flink：是**当前最流行的开源大数据内存计算框架**。用于**实时计算**的场景比较多。

6）Oozie：Oozie是一个管理 `Hadoop` 作业 (job) 的**工作流程调度管理系统**。

7）Hbase: HBase是一个**分布式的、面向列的开源数据库**。HBase不同于一般的关系数据库，它是一个**适合于非结构化数据存储**的数据库。

8）Hive：Hive 是基于 Hadoop 的一个数据仓库工具，**可以将结构化的数据文件映射为一张数据库表，并提供简单的SQL查询功能**，**可以将SQL语句转换为 MapReduce 任务进行运行**。其优点是学习成本低，可以通过类SQL语句快速实现简单的 MapReduce 统计，不必开发专门的 MapReduce 应用，十分**适合数据仓库的统计分析**。

9）ZooKeeper：它是一个**针对大型分布式系统的可靠协调系统**，提供的功能包括：配置维护、名字服务、分布式同步、组服务等。

### 1.7 推荐系统框架图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220804083336125.png)





## 2 运行环境搭建（开发重点）

- Linux：`CentOS-7.5-x86-1804`
- Jdk 8
- Hadoop 3.1.3



### 2.1 模板虚拟机环境准备

- 0）安装模板虚拟机，IP地址192.168.10.100、主机名称 hadoop100、内存4G、硬盘50G

- 1）hadoop100 虚拟机配置如下

  - yum 安装
  - 安装 `epel-release`

  ```bash
  [root@hadoop100 ~]# yum install -y epel-release
  ```

  > 注：Extra Packages for Enterprise Linux是为“红帽系”的操作系统提供额外的软件包，适用于RHEL、CentOS和Scientific Linux。相当于是一个软件仓库。

  - 安装 `net-tool`（工具包集合，包含 ifconfig 等命令）、`vim` 编辑器

  ```bash
  [root@hadoop100 ~]# yum install -y net-tools
  
  [root@hadoop100 ~]# yum install -y vim
  ```

  

- 2）关闭防火墙，关闭防火墙开机自启

```bash
[root@hadoop100 ~]# systemctl stop firewalld
[root@hadoop100 ~]# systemctl disable firewalld.service
```

> 注意：在企业开发时，通常单个服务器的防火墙时关闭的。公司整体对外会设置非常安全的防火墙。

- 3）创建 atguigu 用户，并修改 atguigu 用户的密码

```bash
[root@hadoop100 ~]# useradd atguigu
[root@hadoop100 ~]# passwd atguigu
```

- 4）配置atguigu用户具有 `root` 权限，方便后期加 sudo 执行 root 权限的命令

```bash
[root@hadoop100 ~]# vim /etc/sudoers
```

在 `%wheel` 这行下面添加一行，如下所示：

```bash
## Allow root to run any commands anywhere
root    ALL=(ALL)     ALL

## Allows people in group wheel to run all commands
%wheel  ALL=(ALL)       ALL
atguigu   ALL=(ALL)     NOPASSWD:ALL
```



- 5）在 `/opt` 目录下创建文件夹，并修改所属主和所属组

  - （1）在 `/opt` 目录下创建 module、software 文件夹

  ```bash
  [root@hadoop100 ~]# mkdir /opt/module
  [root@hadoop100 ~]# mkdir /opt/software
  ```

  - （2）修改 module、software 文件夹的所有者和所属组均为 atguigu 用户

  ```bash
  [root@hadoop100 ~]# chown atguigu:atguigu /opt/module 
  [root@hadoop100 ~]# chown atguigu:atguigu /opt/software
  ```

  - （3）查看 module、software 文件夹的所有者和所属组

  ```bash
  [root@hadoop100 ~]# cd /opt/
  [root@hadoop100 opt]# ll
  总用量 12
  drwxr-xr-x. 2 atguigu atguigu 4096 5月  28 17:18 module
  drwxr-xr-x. 2 root    root    4096 9月   7 2017 rh
  drwxr-xr-x. 2 atguigu atguigu 4096 5月  28 17:18 software
  ```

  

- 6）卸载虚拟机自带的 JDK

  ```bash
  # 查看
  [root@hadoop100 ~]# rpm -qa | grep -i java
  [root@hadoop100 ~]# rpm -qa | grep -i java | xargs -n1 rpm -e --nodeps
  ```

  - rpm -qa：查询所安装的所有 rpm 软件包
  - grep -i：忽略大小写
  - xargs -n1：表示每次只传递一个参数
  - rpm -e –nodeps：强制卸载软件

- 7）重启虚拟机

  ```bash
  [root@hadoop100 ~]# reboot
  ```

### 2.2 克隆虚拟机

- 1）利用模板机 `hadoop100`，克隆三台虚拟机：hadoop102、hadoop103、hadoop104

> 注意：克隆时，要先关闭hadoop100。

- 2）修改克隆机IP，以下以hadoop102举例说明

  - （1）修改克隆虚拟机的静态IP

  ```bash
  [root@hadoop100 ~]# vim /etc/sysconfig/network-scripts/ifcfg-ens33vim /etc/sysconfig/network-scripts/ifcfg-ens33
  修改为
  DEVICE=ens33
  TYPE=Ethernet
  ONBOOT=yes
  BOOTPROTO=static
  NAME="ens33"
  IPADDR=192.168.10.102
  PREFIX=24
  GATEWAY=192.168.10.2
  DNS1=192.168.10.2
  ```

  - （2）查看 `Linux` 虚拟机的虚拟网络编辑器，编辑->虚拟网络编辑器->VMnet8
  - （3）查看Windows系统适配器VMware Network Adapter VMnet8的IP地址
  - （4）保证 Linux 系统 ifcfg-ens33 文件中IP地址、虚拟网络编辑器地址和Windows系统 VM8 网络 IP 地址相同。

- 3）修改克隆机主机名，以下以hadoop102举例说明

  - （1）修改主机名称

  ```bash
  [root@hadoop100 ~]# vim /etc/hostname
  hadoop102
  ```

  - （2）配置Linux克隆机主机名称映射hosts文件，打开 `/etc/hosts`

  ```bash
  [root@hadoop100 ~]# vim /etc/hosts
  ```

  添加如下内容

  ```bash
  192.168.10.100 hadoop100
  192.168.10.101 hadoop101
  192.168.10.102 hadoop102
  192.168.10.103 hadoop103
  192.168.10.104 hadoop104
  192.168.10.105 hadoop105
  192.168.10.106 hadoop106
  192.168.10.107 hadoop107
  192.168.10.108 hadoop108
  ```

- 4）重启克隆机hadoop102

```bash
[root@hadoop100 ~]# reboot
```

- 5）修改 windows 的主机映射文件（hosts文件）

  - （1）如果操作系统是 window7，可以直接修改

  - （2）如果操作系统是 window10，先拷贝出来，修改保存以后，再覆盖即可

    - （a）进入 `C:\Windows\System32\drivers\etc` 路径
    - （b）拷贝 hosts 文件到桌面
    - （c）打开桌面 hosts 文件并添加如下内容

    ```bash
    192.168.10.100 hadoop100
    192.168.10.101 hadoop101
    192.168.10.102 hadoop102
    192.168.10.103 hadoop103
    192.168.10.104 hadoop104
    192.168.10.105 hadoop105
    192.168.10.106 hadoop106
    192.168.10.107 hadoop107
    192.168.10.108 hadoop108
    ```

    - （d）将桌面 hosts 文件覆盖 `C:\Windows\System32\drivers\etc` 路径 `hosts `文件

### 2.3 在 hadoop102 安装 JDK

- 1）卸载现有 JDK

> 注意：安装 JDK 前，一定确保提前删除了虚拟机自带的 JDK。

- 2）用 XShell 等传输工具将 JDK 导入到 `/opt/software/` 目录下，并查看

  ```bash
  [atguigu@hadoop102 ~]$ ls /opt/software/
  ```

- 3）解压 JDK 到 `/opt/module` 目录下

  ```bash
  [atguigu@hadoop102 software]$ tar -zxvf jdk-8u212-linux-x64.tar.gz -C /opt/module/
  ```

- 4）配置 JDK 环境变量

```bash
（1）新建/etc/profile.d/my_env.sh文件
[atguigu@hadoop102 ~]$ sudo vim /etc/profile.d/my_env.sh
添加如下内容

#JAVA_HOME
export JAVA_HOME=/opt/module/jdk1.8.0_212
export PATH=$PATH:$JAVA_HOME/bin

（2）保存后退出
:wq

（3）source一下/etc/profile文件，让新的环境变量 PATH 生效
[atguigu@hadoop102 ~]$ source /etc/profile
```

- 6）测试 JDK 是否安装成功

```bash
[atguigu@hadoop102 ~]$ java -version

java version "1.8.0_212"
```



### 2.4 在 hadoop102 安装Hadoop

Hadoop下载地址：[https://archive.apache.org/dist/hadoop/common/hadoop-3.1.3/](https://archive.apache.org/dist/hadoop/common/hadoop-2.7.2/)

- 1）用 XShell 等文件传输工具将 hadoop-3.1.3.tar.gz 导入到  `/opt/software/` 目录下

- 2）进入到 Hadoop 安装包路径下

  ```bash
  [atguigu@hadoop102 ~]$ cd /opt/software/
  ```

- 3）解压安装文件到 /opt/module 下面，并查看

  ```bash
  [atguigu@hadoop102 software]$ tar -zxvf hadoop-3.1.3.tar.gz -C /opt/module/
  
  [atguigu@hadoop102 software]$ ls /opt/module/
  hadoop-3.1.3
  ```

- 4）将Hadoop添加到环境变量

  - （1）获取Hadoop安装路径

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ pwd
  /opt/module/hadoop-3.1.3
  ```

  - （2）打开 /etc/profile.d/my_env.sh 文件

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ sudo vim /etc/profile.d/my_env.sh
  
  在my_env.sh文件末尾添加如下内容：（shift+g）
  
  #HADOOP_HOME
  export HADOOP_HOME=/opt/module/hadoop-3.1.3
  export PATH=$PATH:$HADOOP_HOME/bin
  export PATH=$PATH:$HADOOP_HOME/sbin
  
  保存并退出： :wq
  ```

  - （3）让修改后的文件生效

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ source /etc/profile
  ```

- 5）测试是否安装成功

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop version
Hadoop 3.1.3
```

- 6）重启（如果Hadoop命令不能用再重启虚拟机）

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ sudo reboot
```



### 2.5 Hadoop 目录结构

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ ll
总用量 52
drwxr-xr-x. 2 atguigu atguigu  4096 5月  22 2017 bin
drwxr-xr-x. 3 atguigu atguigu  4096 5月  22 2017 etc
drwxr-xr-x. 2 atguigu atguigu  4096 5月  22 2017 include
drwxr-xr-x. 3 atguigu atguigu  4096 5月  22 2017 lib
drwxr-xr-x. 2 atguigu atguigu  4096 5月  22 2017 libexec
-rw-r--r--. 1 atguigu atguigu 15429 5月  22 2017 LICENSE.txt
-rw-r--r--. 1 atguigu atguigu   101 5月  22 2017 NOTICE.txt
-rw-r--r--. 1 atguigu atguigu  1366 5月  22 2017 README.txt
drwxr-xr-x. 2 atguigu atguigu  4096 5月  22 2017 sbin
drwxr-xr-x. 4 atguigu atguigu  4096 5月  22 2017 share
```

- 重要目录
  - （1）**`bin`** 目录：存放对 Hadoop 相关服务（`hdfs`，`yarn`，`mapred`）进行操作的脚本
  - （2）**`etc` ** 目录：Hadoop的配置文件目录，存放Hadoop的配置文件
  - （3）lib 目录：存放Hadoop的本地库（对数据进行压缩解压缩功能）
  - （4）**`sbin `** 目录：存放启动或停止Hadoop相关服务的脚本
  - （5）share 目录：存放Hadoop的依赖 jar 包、文档、和官方案例



## 3 运行模式

- Hadoop官方网站：http://hadoop.apache.org/

- Hadoop运行模式包括：本地模式、伪分布式模式以及完全分布式模式。
  - 本地模式：单机运行，只是用来演示一下官方案例。生产环境不用。
  - 伪分布式模式：也是单机运行，但是具备Hadoop集群的所有功能，一台服务器模拟一个分布式的环境。个别缺钱的公司用来测试，生产环境不用。
  - 完全分布式模式：多台服务器组成分布式环境。**生产环境使用。**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220809194929194.png)



### 3.1 本地运行模式（官方WordCount）

- 在 `hadoop-3.1.3` 文件下面创建一个 `wcinput` 文件夹

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ mkdir wcinput
```

- 在 wcinput 文件下创建一个 `word.txt` 文件

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ cd wcinput
```

- 编辑`word.txt` 文件

```bash
[atguigu@hadoop102 wcinput]$ vim word.txt

# 在文件中输入如下内容
hadoop yarn
hadoop mapreduce
atguigu
atguigu
```

- 回到 Hadoop目录 ` /opt/module/hadoop-3.1.3`，执行程序

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount wcinput wcoutput
```

- 查看结果

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ cat wcoutput/part-r-00000

# 结果如下
atguigu 2
hadoop  2
mapreduce       1
yarn    1
```



### 3.2 完全分布式运行模式🎈（开发重点）

- 分析：

  - 1）准备3台客户机（关闭防火墙、静态IP、主机名称）
  - 2）安装JDK、配置环境变量
  - 3）安装Hadoop、配置环境变量
  - 4）配置集群
  - 5）单点启动
  - 6）配置ssh
  - 7）群起并测试集群



#### 3.2.0 总结一下

|      | hadoop102         | hadoop103                   | hadoop104                  |
| ---- | ----------------- | --------------------------- | -------------------------- |
| HDFS | NameNode DataNode | DataNode                    | SecondaryNameNode DataNode |
| YARN | NodeManager       | ResourceManager NodeManager | NodeManager                |

- 虚拟机准备（详见 2.1 ~ 2.4 ）
  - hadoop102 安装 JDK、Hadoop
  - 关闭锁屏

- 集群分发脚本
  - `scp` 命令拷贝 
  - `rsync` 远程同步（同步 hadoop102 上的 JDK、Hadoop、环境变量配置到 hadoop103、hadoop104 ）
  - 实现 `xsync` 集群分发脚本

- ssh 无密登陆配置（三台机器同样配置）

```bash
$ ssh-keygen -t rsa
$ ssh-copy-id hadoop102
$ ssh-copy-id hadoop103
$ ssh-copy-id hadoop104
```

- 修改 hadoop102 配置文件并分发
  - 核心配置文件：`core-site.xml`
  - HDFS 配置文件：配置 `hdfs-site.xml`
  - YARN 配置文件：配置 `yarn-site.xml`
  - MapReduce 配置文件：配置 `mapred-site.xml`

- 启动集群

  - hadoop102 配置 `workers` 并分发
  - hadoop102 格式化 `NameNode`
  - hadoop102 启动 `hdfs`

  ```bash
  # 启动 hdfs
  [atguigu@hadoop102 hadoop-3.1.3]$ sbin/start-dfs.sh
  ```

  - hadoop103 启动 `yarn`

  ```bash
  [atguigu@hadoop103 hadoop-3.1.3]$ sbin/start-yarn.sh
  ```

  - web 页面查看：http://hadoop102:9870、 http://hadoop103:8088

- 配置历史服务器
- 配置日志 





#### 3.2.1 虚拟机准备

- 详见 2.1 ~ 2.4 
- 虚拟机关闭锁屏（一直输密码进系统很麻烦！）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220824220241066.png)



#### 3.2.2 编写集群分发脚本xsync ⭐️

> - 拷贝
> - 同步
> - 脚本



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220824223022082.png)



- scp（secure copy）安全拷贝

  - 定义：scp可以实现服务器与服务器之间的数据拷贝。（from server1 to server2）

  - 语法

    ```bash
    scp  -r     $pdir/$fname       $user@$host:$pdir/$fname
    
    命令  递归   要拷贝的文件路径/名称  目的地用户@主机:目的地路径/名称
    ```

    

  - 案例实操

  ```bash
  # 前提：在hadoop102、hadoop103、hadoop104都已经创建好/opt/module、/opt/software两个目录，
  # 并且已经把这两个目录修改为atguigu:atguigu
  [atguigu@hadoop102 ~]$ sudo chown atguigu:atguigu -R /opt/module
  
  # 将hadoop102中/opt/module/jdk1.8.0_212目录拷贝到hadoop103上
  [atguigu@hadoop102 ~]$ scp -r /opt/module/jdk1.8.0_212  atguigu@hadoop103:/opt/module
  
  # 将hadoop102中/opt/module/hadoop-3.1.3目录拷贝到hadoop103上
  [atguigu@hadoop103 ~]$ scp -r atguigu@hadoop102:/opt/module/hadoop-3.1.3 /opt/module/
  
  # 将hadoop102中/opt/module目录下所有目录拷贝到hadoop104上
  [atguigu@hadoop103 opt]$ scp -r atguigu@hadoop102:/opt/module/* atguigu@hadoop104:/opt/module
  ```

  

- rsync 远程同步工具
  - rsync 主要用于备份和镜像。具有速度快、避免复制相同内容和支持符号链接的优点。
  
  - rsync 和 scp 区别：用 rsync 做文件的复制要比 scp 的速度快，rsync **只对差异文件做更新**，scp 是**把所有文件都复制过去**。
  
  - 语法
  
    ```bash
    rsync   -av    $pdir/$fname       $user@$host:$pdir/$fname
    
    命令  选项参数  要拷贝的文件路径/名称  目的地用户@主机:目的地路径/名称
    ```
  
     选项参数说明
  
    | 选项 | 功能         |
    | ---- | ------------ |
    | -a   | 归档拷贝     |
    | -v   | 显示复制过程 |
  
  - 案例实操
  
  ```bash
  # 删除hadoop103中/opt/module/hadoop-3.1.3/wcinput
  [atguigu@hadoop103 hadoop-3.1.3]$ rm -rf wcinput/
  
  # 同步hadoop102中的/opt/module/hadoop-3.1.3到hadoop103
  [atguigu@hadoop102 module]$ rsync -av hadoop-3.1.3/ atguigu@hadoop103:/opt/module/hadoop-3.1.3/
  ```
  
  
  
- `xsync` 集群分发脚本

  - 需求：循环复制文件到所有节点的相同目录下

  - 分析

    - `rsync` 命令原始拷贝

    ```bash
    rsync  -av     /opt/module  		 atguigu@hadoop103:/opt/
    ```

    - 期望脚本：xsync 要同步的文件名称
    - 期望脚本在任何路径都能使用（脚本放在声明了全局环境变量的路径）

    ```bash
    [atguigu@hadoop102 ~]$ echo $PATH
    /usr/local/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/home/atguigu/.local/bin:/home/atguigu/bin:/opt/module/jdk1.8.0_212/bin
    ```

    

  - 脚本实现

    - 在 /home/atguigu/bin 目录下创建 `xsync` 文件

    ```bash
    [atguigu@hadoop102 opt]$ cd /home/atguigu
    [atguigu@hadoop102 ~]$ mkdir bin
    [atguigu@hadoop102 ~]$ cd bin
    [atguigu@hadoop102 bin]$ vim xsync
    # 在该文件中编写如下代码
    ```
    
    
    
    ```bash
    #!/bin/bash
    
    #1. 判断参数个数
    if [ $# -lt 1 ]
    then
        echo Not Enough Arguement!
        exit;
    fi
    
    #2. 遍历集群所有机器
    for host in hadoop102 hadoop103 hadoop104
    do
        echo ====================  $host  ====================
        #3. 遍历所有目录，挨个发送
    
        for file in $@
        do
            #4. 判断文件是否存在
            if [ -e $file ]
                then
                    #5. 获取父目录
                    pdir=$(cd -P $(dirname $file); pwd)
    
                    #6. 获取当前文件的名称
                    fname=$(basename $file)
                    ssh $host "mkdir -p $pdir"
                    rsync -av $pdir/$fname $host:$pdir
                else
                    echo $file does not exists!
            fi
        done
    done
    ```
    
    - 修改脚本 xsync 具有执行权限

    ```bash
    [atguigu@hadoop102 bin]$ chmod +x xsync
    ```
    
    - 测试脚本

    ```bash
    [atguigu@hadoop102 ~]$ xsync /home/atguigu/bin
    ```
    
    - 将脚本复制到 /bin 中，以便全局调用

    ```bash
    [atguigu@hadoop102 bin]$ sudo cp xsync /bin/
    ```
    
    - 同步环境变量配置（root所有者）
    
    ```bash
    [atguigu@hadoop102 ~]$ sudo ./bin/xsync /etc/profile.d/my_env.sh
    
    # 让环境变量生效
    [atguigu@hadoop103 bin]$ source /etc/profile
    [atguigu@hadoop104 opt]$ source /etc/profile
    ```

#### 3.2.3 SSH 无密登录配置

- 配置 ssh

  - 语法：`ssh` 另一台电脑的IP地址
  - ssh 连接时出现 `Host key verification failed` 的解决方法

  ```bash
  [atguigu@hadoop102 ~]$ ssh hadoop103
  # 如果出现如下内容
  Are you sure you want to continue connecting (yes/no)? 
  # 输入yes，并回车
  # 退回到hadoop102
  [atguigu@hadoop103 ~]$ exit
  ```

  

- 无密钥配置

  - 免密登录原理
  - 生成公钥和私钥

  ```bash
  [atguigu@hadoop102 .ssh]$ pwd
  /home/atguigu/.ssh
  
  [atguigu@hadoop102 .ssh]$ ssh-keygen -t rsa
  # 然后敲（三个回车），就会生成两个文件id_rsa（私钥）、id_rsa.pub（公钥）
  # 将公钥拷贝到要免密登录的目标机器上
  [atguigu@hadoop102 .ssh]$ ssh-copy-id hadoop102
  [atguigu@hadoop102 .ssh]$ ssh-copy-id hadoop103
  [atguigu@hadoop102 .ssh]$ ssh-copy-id hadoop104
  
  # 注意：
  还需要在hadoop103上采用atguigu账号配置一下无密登录到hadoop102、hadoop103、hadoop104服务器上。
  还需要在hadoop104上采用atguigu账号配置一下无密登录到hadoop102、hadoop103、hadoop104服务器上。
  ```




- .ssh 文件夹下（~/.ssh）的文件功能解释

| 文件            | 功能                                    |
| --------------- | --------------------------------------- |
| known_hosts     | 记录ssh访问过计算机的公钥（public key） |
| id_rsa          | 生成的私钥                              |
| id_rsa.pub      | 生成的公钥                              |
| authorized_keys | 存放授权过的无密登录服务器公钥          |

#### 3.2.4 集群配置

- 集群部署规划
  - NameNode 和 SecondaryNameNode 不要安装在同一台服务器
  - ResourceManager 也很消耗内存，不要和 NameNode、SecondaryNameNode 配置在同一台机器上。

|      | hadoop102         | hadoop103                   | hadoop104                  |
| ---- | ----------------- | --------------------------- | -------------------------- |
| HDFS | NameNode DataNode | DataNode                    | SecondaryNameNode DataNode |
| YARN | NodeManager       | ResourceManager NodeManager | NodeManager                |

- 配置文件说明

  - Hadoop 配置文件分两类：默认配置文件和自定义配置文件，只有用户想修改某一默认配置值时，才需要修改自定义配置文件，更改相应属性值。
  - 默认配置文件

  | 要获取的默认文件     | 文件存放在Hadoop的jar包中的位置                           |
  | -------------------- | --------------------------------------------------------- |
  | [core-default.xml]   | hadoop-common-3.1.3.jar/core-default.xml                  |
  | [hdfs-default.xml]   | hadoop-hdfs-3.1.3.jar/hdfs-default.xml                    |
  | [yarn-default.xml]   | hadoop-yarn-common-3.1.3.jar/yarn-default.xml             |
  | [mapred-default.xml] | hadoop-mapreduce-client-core-3.1.3.jar/mapred-default.xml |

  - 自定义配置文件

  core-site.xml、hdfs-site.xml、yarn-site.xml、mapred-site.xml 四个配置文件存放在$HADOOP_HOME/etc/hadoop这个路径上，用户可以根据项目需求重新进行修改配置。

- 配置集群

  - 核心配置文件：配置 `core-site.xml`

  ```bash
  [atguigu@hadoop102 ~]$ cd $HADOOP_HOME/etc/hadoop
  [atguigu@hadoop102 hadoop]$ vim core-site.xml
  ```

  内容如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
  
  <configuration>
      <!-- 指定NameNode的地址 -->
      <property>
          <name>fs.defaultFS</name>
          <value>hdfs://hadoop102:8020</value>
      </property>
  
      <!-- 指定hadoop数据的存储目录 -->
      <property>
          <name>hadoop.tmp.dir</name>
          <value>/opt/module/hadoop-3.1.3/data</value>
      </property>
  
      <!-- 配置HDFS网页登录使用的静态用户为atguigu -->
      <property>
          <name>hadoop.http.staticuser.user</name>
          <value>atguigu</value>
      </property>
  </configuration>
  ```

  

  - HDFS 配置文件：配置 `hdfs-site.xml`

  ```bash
  [atguigu@hadoop102 hadoop]$ vim hdfs-site.xml
  ```

  内容如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
  
  <configuration>
  	<!-- nn web端访问地址-->
  	<property>
          <name>dfs.namenode.http-address</name>
          <value>hadoop102:9870</value>
      </property>
  	<!-- 2nn web端访问地址-->
      <property>
          <name>dfs.namenode.secondary.http-address</name>
          <value>hadoop104:9868</value>
      </property>
  </configuration>
  ```

  

  - YARN 配置文件：配置 `yarn-site.xml`

  ```bash
  [atguigu@hadoop102 hadoop]$ vim yarn-site.xml
  ```

  内容如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
  
  <configuration>
      <!-- 指定MR走shuffle -->
      <property>
          <name>yarn.nodemanager.aux-services</name>
          <value>mapreduce_shuffle</value>
      </property>
  
      <!-- 指定ResourceManager的地址-->
      <property>
          <name>yarn.resourcemanager.hostname</name>
          <value>hadoop103</value>
      </property>
  
      <!-- 环境变量的继承 -->
      <property>
          <name>yarn.nodemanager.env-whitelist</name>
          <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
      </property>
  </configuration>
  ```

  - MapReduce 配置文件：配置 `mapred-site.xml`

  ```bash
  [atguigu@hadoop102 hadoop]$ vim mapred-site.xml
  ```

  内容如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
  
  <configuration>
  	<!-- 指定MapReduce程序运行在Yarn上 -->
      <property>
          <name>mapreduce.framework.name</name>
          <value>yarn</value>
      </property>
  </configuration>
  ```

  

- 在集群上分发配置好的 Hadoop 配置文件

```bash
[atguigu@hadoop102 hadoop]$ xsync /opt/module/hadoop-3.1.3/etc/hadoop/
```



- 去103和104上查看文件分发情况

```bash
[atguigu@hadoop103 ~]$ cat /opt/module/hadoop-3.1.3/etc/hadoop/core-site.xml
[atguigu@hadoop104 ~]$ cat /opt/module/hadoop-3.1.3/etc/hadoop/core-site.xml
```



#### 3.2.5 群起集群

- 配置 workers

```bash
[atguigu@hadoop102 hadoop]$ vim /opt/module/hadoop-3.1.3/etc/hadoop/workers

# 在该文件中增加如下内容：
hadoop102
hadoop103
hadoop104

# 同步所有节点配置文件
[atguigu@hadoop102 hadoop]$ xsync /opt/module/hadoop-3.1.3/etc
```

- 启动集群

  - **如果集群是第一次启动**，需要在 hadoop102 节点格式化 `NameNode`

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ hdfs namenode -format
  ```

  - 启动 HDFS

  > 参考：https://blog.csdn.net/weixin_43848614/article/details/112596493

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ sbin/start-dfs.sh
  ```

  

  ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220825002900933.png)

  

  - 在配置了 ResourceManager 的节点（hadoop103）启动YARN

  ```bash
  [atguigu@hadoop103 hadoop-3.1.3]$ sbin/start-yarn.sh
  ```

  - Web 端查看 HDFS 的 NameNode
    - 浏览器中输入：http://hadoop102:9870
    - 查看 HDFS 上存储的数据信息

  ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220825003952217.png)

  

  - Web 端查看 YARN 的 ResourceManager
    - 浏览器中输入：http://hadoop103:8088
    - 查看 YARN 上运行的 Job 信息

  ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220825004009021.png)

- 集群基本测试

  - 上传文件到集群

  ```bash
  # 上传小文件
  [atguigu@hadoop102 ~]$ hadoop fs -mkdir /input
  [atguigu@hadoop102 ~]$ hadoop fs -put $HADOOP_HOME/wcinput/word.txt /input
  
  # 上传大文件
  [atguigu@hadoop102 ~]$ hadoop fs -put  /opt/software/jdk-8u212-linux-x64.tar.gz  /
  ```

  - 上传文件后查看文件存放在什么位置

  ```bash
  # 查看HDFS文件存储路径
  [atguigu@hadoop102 subdir0]$ pwd
  /opt/module/hadoop-3.1.3/data/dfs/data/current/BP-1436128598-192.168.10.102-1610603650062/current/finalized/subdir0/subdir0
  
  # 查看HDFS在磁盘存储文件内容
  [atguigu@hadoop102 subdir0]$ cat blk_1073741825
  hadoop yarn
  hadoop mapreduce 
  atguigu
  atguigu
  ```

  - 拼接

  ```bash
  -rw-rw-r--. 1 atguigu atguigu 134217728 5月  23 16:01 blk_1073741836
  -rw-rw-r--. 1 atguigu atguigu   1048583 5月  23 16:01 blk_1073741836_1012.meta
  -rw-rw-r--. 1 atguigu atguigu  63439959 5月  23 16:01 blk_1073741837
  -rw-rw-r--. 1 atguigu atguigu    495635 5月  23 16:01 blk_1073741837_1013.meta
  [atguigu@hadoop102 subdir0]$ cat blk_1073741836>>tmp.tar.gz
  [atguigu@hadoop102 subdir0]$ cat blk_1073741837>>tmp.tar.gz
  [atguigu@hadoop102 subdir0]$ tar -zxvf tmp.tar.gz
  ```

  

  - 下载

  ```bash
  [atguigu@hadoop104 software]$ hadoop fs -get /jdk-8u212-linux-x64.tar.gz ./
  ```

  - 执行 `wordcount` 程序

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount /input /output
  ```

#### 3.2.6 配置历史服务器

为了查看程序的历史运行情况，需要配置一下历史服务器。

- 配置 `mapred-site.xml`

```bash
[atguigu@hadoop102 hadoop]$ vim mapred-site.xml
```

增加如下配置

```xml
<!-- 历史服务器端地址 -->
<property>
    <name>mapreduce.jobhistory.address</name>
    <value>hadoop102:10020</value>
</property>

<!-- 历史服务器web端地址 -->
<property>
    <name>mapreduce.jobhistory.webapp.address</name>
    <value>hadoop102:19888</value>
</property>
```



- 分发配置

```bash
[atguigu@hadoop102 hadoop]$ xsync $HADOOP_HOME/etc/hadoop/mapred-site.xml
```



- 在 hadoop102 启动历史服务器

```bash
[atguigu@hadoop102 hadoop]$ mapred --daemon start historyserver
```



- 查看历史服务器是否启动

```bash
[atguigu@hadoop102 hadoop]$ jps
```



- 查看 JobHistory：http://hadoop102:19888/jobhistory



#### 3.2.7 配置日志的聚集

- 日志聚集概念：应用运行完成以后，将程序运行日志信息上传到 HDFS 系统上。
  - 好处：可以方便的查看到程序运行详情，方便开发调试。
  - 注意：开启日志聚集功能，需要重新启动 NodeManager 、ResourceManager 和 HistoryServer。

- 开启

  - 配置 `yarn-site.xml`

  ```bash
  [atguigu@hadoop102 hadoop]$ vim yarn-site.xml
  ```

  增加如下配置

  ```xml
  <!-- 开启日志聚集功能 -->
  <property>
      <name>yarn.log-aggregation-enable</name>
      <value>true</value>
  </property>
  <!-- 设置日志聚集服务器地址 -->
  <property>  
      <name>yarn.log.server.url</name>  
      <value>http://hadoop102:19888/jobhistory/logs</value>
  </property>
  <!-- 设置日志保留时间为7天 -->
  <property>
      <name>yarn.log-aggregation.retain-seconds</name>
      <value>604800</value>
  </property>    
  ```

  - 分发配置

  ```bash
  [atguigu@hadoop102 hadoop]$ xsync $HADOOP_HOME/etc/hadoop/yarn-site.xml
  ```

  - 关闭NodeManager 、ResourceManager 和 HistoryServer

  ```bash
  [atguigu@hadoop103 hadoop-3.1.3]$ sbin/stop-yarn.sh
  [atguigu@hadoop103 hadoop-3.1.3]$ mapred --daemon stop historyserver
  ```

  - 启动NodeManager 、ResourceManage和HistoryServer

  ```bash
  [atguigu@hadoop103 ~]$ start-yarn.sh
  [atguigu@hadoop102 ~]$ mapred --daemon start historyserver
  ```

  - 删除HDFS上已经存在的输出文件

  ```bash
  [atguigu@hadoop102 ~]$ hadoop fs -rm -r /output
  ```

  - 执行WordCount程序

  ```bash
  [atguigu@hadoop102 hadoop-3.1.3]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount /input /output
  ```

  - 查看日志
    - 历史服务器地址：http://hadoop102:19888/jobhistory
    - 历史任务列表
    - 查看任务运行日志
    - 运行日志详情



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220825231327179.png)



#### 3.2.8 集群启动/停止方式总结

- 各个模块分开启动/停止（配置 ssh 是前提） 常用

```bash
# 整体启动/停止HDFS
start-dfs.sh/stop-dfs.sh

# 整体启动/停止YARN
start-yarn.sh/stop-yarn.sh
```

- 各个服务组件逐一启动/停止

```bash
# 分别启动/停止HDFS组件
hdfs --daemon start/stop namenode/datanode/secondarynamenode

# 启动/停止YARN
yarn --daemon start/stop  resourcemanager/nodemanager
```



#### 3.2.9 编写Hadoop集群常用脚本

- Hadoop集群启停脚本 `myhadoop.sh` （包含HDFS，Yarn，Historyserver）

```bash
[atguigu@hadoop102 ~]$ cd /home/atguigu/bin
[atguigu@hadoop102 bin]$ vim myhadoop.sh
```

输入如下内容：

```sh
#!/bin/bash

if [ $# -lt 1 ]
then
    echo "No Args Input..."
    exit ;
fi

case $1 in
"start")
        echo " =================== 启动 hadoop集群 ==================="

        echo " --------------- 启动 hdfs ---------------"
        ssh hadoop102 "/opt/module/hadoop-3.1.3/sbin/start-dfs.sh"
        echo " --------------- 启动 yarn ---------------"
        ssh hadoop103 "/opt/module/hadoop-3.1.3/sbin/start-yarn.sh"
        echo " --------------- 启动 historyserver ---------------"
        ssh hadoop102 "/opt/module/hadoop-3.1.3/bin/mapred --daemon start historyserver"
;;
"stop")
        echo " =================== 关闭 hadoop集群 ==================="

        echo " --------------- 关闭 historyserver ---------------"
        ssh hadoop102 "/opt/module/hadoop-3.1.3/bin/mapred --daemon stop historyserver"
        echo " --------------- 关闭 yarn ---------------"
        ssh hadoop103 "/opt/module/hadoop-3.1.3/sbin/stop-yarn.sh"
        echo " --------------- 关闭 hdfs ---------------"
        ssh hadoop102 "/opt/module/hadoop-3.1.3/sbin/stop-dfs.sh"
;;
*)
    echo "Input Args Error..."
;;
esac
```

保存后退出，然后赋予脚本执行权限

```bash
[atguigu@hadoop102 bin]$ chmod +x myhadoop.sh
```

- 查看三台服务器Java进程脚本：jpsall

```bash
[atguigu@hadoop102 ~]$ cd /home/atguigu/bin
[atguigu@hadoop102 bin]$ vim jpsall
```

输入如下内容：

```bash
#!/bin/bash

for host in hadoop102 hadoop103 hadoop104
do
        echo =============== $host ===============
        ssh $host jps 
done
```

保存后退出，然后赋予脚本执行权限

```bash
[atguigu@hadoop102 bin]$ chmod +x jpsall
```

- 分发 /home/atguigu/bin 目录，保证自定义脚本在三台机器上都可以使用

```bash
[atguigu@hadoop102 ~]$ xsync /home/atguigu/bin/
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220825231040057.png)



#### 3.2.10 常用端口号说明

| 端口名称                  | Hadoop2.x   | Hadoop3.x            |
| ------------------------- | ----------- | -------------------- |
| NameNode内部通信端口      | 8020 / 9000 | 8020 🎈 / 9000 / 9820 |
| NameNode HTTP UI          | 50070 🎈     | 9870 🎈               |
| MapReduce查看执行任务端口 | 8088        | 8088                 |
| 历史服务器通信端口        | 19888       | 19888                |



#### 3.2.11 集群时间同步

- **如果服务器在公网环境（能连接外网），可以不采用集群时间同步**，因为服务器会定期和公网时间进行校准；

- 如果服务器在内网环境，必须要配置集群时间同步，否则时间久了，会产生时间偏差，导致集群执行任务时间不同步。

- 需求
- 时间服务器配置（必须root用户）
- 其他机器配置（必须root用户）



## 4 常见错误及解决方案

- 1、防火墙没关闭、或者没有启动YARN
  - *INFO client.RMProxy: Connecting to ResourceManager at hadoop108/192.168.10.108:8032*

- 2、主机名称配置错误

- 3、IP 地址配置错误

- 4、ssh 没有配置好

- 5、root 用户和 atguigu 两个用户启动集群不统一

- 6、配置文件修改不细心

- 7、不识别主机名称

  ```bash
  java.net.UnknownHostException: hadoop102: hadoop102
          at java.net.InetAddress.getLocalHost(InetAddress.java:1475)
          at org.apache.hadoop.mapreduce.JobSubmitter.submitJobInternal(JobSubmitter.java:146)
          at org.apache.hadoop.mapreduce.Job$10.run(Job.java:1290)
          at org.apache.hadoop.mapreduce.Job$10.run(Job.java:1287)
          at java.security.AccessController.doPrivileged(Native Method)
  at javax.security.auth.Subject.doAs(Subject.java:415)
  ```

  - 解决办法：

    （1）在 `/etc/hosts` 文件中添加192.168.10.102 hadoop102

    （2）主机名称不要起 hadoop  hadoop000 等特殊名称

- 8、DataNode 和 NameNode进程同时只能工作一个。
- 9、执行命令不生效，粘贴Word中命令时，遇到-和长–没区分开。导致命令失效
  - 解决办法：尽量不要粘贴Word中代码。

- 10、jps 发现进程已经没有，但是重新启动集群，提示进程已经开启。
  
  - 原因是在 Linux 的根目录下 /tmp 目录中存在启动的进程临时文件，将集群相关进程删除掉，再重新启动集群。
  
- 11、jps 不生效 ✔
  
  - 原因：全局变量 `hadoop java` 没有生效。
  - 解决办法：需要 `source /etc/profile` 文件。
  
- 12、8088端口连接不上



# HDFS

## 5 HDFS 概述

### 5.1HDFS 产生背景、定义和使用场景

- 产生背景
  - 随着数据量越来越大，在一个操作系统存不下所有的数据；
  - 那么就分配到更多的操作系统管理的磁盘中，但是不方便管理和维护，迫切需要一种系统来管理多台机器上的文件，这就是分布式文件管理系统。
  - HDFS 只是**分布式文件管理系统**中的一种。

- 定义
  - HDFS（Hadoop Distributed File System），它是一个文件系统，用于存储文件，通过目录树来定位文件；
  - 其次，它是分布式的，由很多服务器联合起来实现其功能，集群中的服务器有各自的角色。

- 使用场景
  - **适合一次写入，多次读出的场景**。一个文件经过创建、写入和关闭之后就不需要改变。

### 5.2HDFS 优缺点

- 优点
  - 高容错性：数据自动保存多个副本。某一个副本丢失以后，可以自动恢复。
  - 适合处理大数据
    - 数据规模：能够处理数据规模达到GB、TB甚至**PB级别的数据**；
    - 文件规模：能够处理**百万**规模以上的**文件数量**，数量相当之大。
  - 可构建在廉价机器上，通过多副本机制，提高可靠性。
- 缺点
  - 不适合低延时数据访问（比如毫秒级的存储数据）
  - 无法高效地对大量小文件进行存储（寻址时间会超过读取时间）
  - 不支持并发写入（不允许多个文件同时写）、不支持文件随机修改（仅支持数据append追加）



### 5.3HDFS 组成架构





### 5.4HDFS 文件块大小（面试重点）

- HDFS 中的文件在物理上是分块存储（Block），块的大小可以通过配置参数来规定，默认大小在Hadoop2.x/3.x版本是128M。

- 不能设置太小，也不能设置太大？
  - 太小，会增加寻址时间
  - 太大，程序在处理这块数据时，会非常慢
  - HDFS块的大小设置主要取决于磁盘传输速率。



## 6 HDFS 的shell操作（开发重点）

### 6.1基本语法

- hadoop fs 具体命令
- hdfs dfs 具体命令



### 6.2命令大全

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ bin/hadoop fs
```



### 6.3常用命令实操

#### 准备工作

- 启动Hadoop集群（方便后续的测试）

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ sbin/start-dfs.sh
[atguigu@hadoop103 hadoop-3.1.3]$ sbin/start-yarn.sh
```



- -help：输出这个命令参数

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -help rm
```



- 创建/sanguo文件夹

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -mkdir /sanguo
```



#### 上传

- -moveFromLocal：从本地剪切粘贴到 HDFS

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ vim shuguo.txt
输入：
shuguo

[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs  -moveFromLocal  ./shuguo.txt  /sanguo
```

- -copyFromLocal：从本地文件系统中拷贝文件到 HDFS 路径去

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ vim weiguo.txt
输入：
weiguo

[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -copyFromLocal weiguo.txt /sanguo
```

- -put：等同于copyFromLocal，生产环境更习惯用put

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ vim wuguo.txt
输入：
wuguo

[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -put ./wuguo.txt /sanguo
```

- -appendToFile：追加一个文件到已经存在的文件末尾

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ vim liubei.txt
输入：
liubei

[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -appendToFile liubei.txt /sanguo/shuguo.txt
```



#### 下载

- -copyToLocal：从HDFS拷贝到本地

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -copyToLocal /sanguo/shuguo.txt ./
```

- -get：等同于copyToLocal，生产环境更习惯用get

```bash
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -get /sanguo/shuguo.txt ./shuguo2.txt
```

#### 直接操作

```bash
-ls: 显示目录信息
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -ls /sanguo

-cat：显示文件内容
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -cat /sanguo/shuguo.txt

-chgrp、-chmod、-chown：Linux文件系统中的用法一样，修改文件所属权限
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs  -chmod 666  /sanguo/shuguo.txt
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs  -chown  atguigu:atguigu   /sanguo/shuguo.txt

-mkdir：创建路径
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -mkdir /jinguo

-cp：从HDFS的一个路径拷贝到HDFS的另一个路径
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -cp /sanguo/shuguo.txt /jinguo

-mv：在HDFS目录中移动文件
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -mv /sanguo/wuguo.txt /jinguo
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -mv /sanguo/weiguo.txt /jinguo

-tail：显示一个文件的末尾1kb的数据
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -tail /jinguo/shuguo.txt

-rm：删除文件或文件夹
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -rm /sanguo/shuguo.txt

-du统计文件夹的大小信息
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -du -s -h /jinguo
27  81  /jinguo

[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -du  -h /jinguo
14  42  /jinguo/shuguo.txt
7   21   /jinguo/weiguo.txt
6   18   /jinguo/wuguo.tx
说明：27表示文件大小；81表示27*3个副本；/jinguo表示查看的目录

-setrep：设置HDFS中文件的副本数量
[atguigu@hadoop102 hadoop-3.1.3]$ hadoop fs -setrep 10 /jinguo/shuguo.txt

# 注：这里设置的副本数只是记录在NameNode的元数据中，是否真的会有这么多副本，还得看DataNode的数量。
# 因为目前只有3台设备，最多也就3个副本，只有节点数增加到10台时，副本数才能达到10。
```



## 7 HDFS 的API操作

### 7.1客户端环境准备

- 找到资料包路径下的 Windos 依赖文件夹，拷贝hadoop-3.1.0到非中文路径（比如d:\）。

- 配置 `HADOOP_HOME` 环境变量

- 配置Path环境变量

> 验证Hadoop环境变量是否正常。双击winutils.exe，如果报如下错误。说明缺少微软运行库（正版系统往往有这个问题）。在资料包里面有对应的微软运行库安装包双击安装即可。

- 在IDEA中创建一个Maven工程 HdfsClientDemo，并导入相应的依赖坐标+日志添加

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-client</artifactId>
        <version>3.1.3</version>
    </dependency>
    
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.30</version>
    </dependency>
</dependencies>
```

- 在项目的 `src/main/resources` 目录下，新建一个文件，命名为“log4j.properties”，在文件中填入

```properties
log4j.rootLogger=INFO, stdout  
log4j.appender.stdout=org.apache.log4j.ConsoleAppender  
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout  
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n  
log4j.appender.logfile=org.apache.log4j.FileAppender  
log4j.appender.logfile.File=target/spring.log  
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout  
log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
```

- 创建包名：com.atguigu.hdfs
- 创建 HdfsClient 类

```java
public class HdfsClient {

    @Test
    public void testMkdirs() throws IOException, URISyntaxException, InterruptedException {

        // 1 获取文件系统
        Configuration configuration = new Configuration();

        // FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration,"atguigu");

        // 2 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan/"));

        // 3 关闭资源
        fs.close();
    }
}
```

- 执行程序

客户端去操作HDFS时，是有一个用户身份的。默认情况下，HDFS客户端API会从采用Windows默认用户访问HDFS，会报权限异常错误。所以在访问HDFS时，一定要配置用户。

```bash
org.apache.hadoop.security.AccessControlException: Permission denied: user=56576, access=WRITE, inode="/xiyou/huaguoshan":atguigu:supergroup:drwxr-xr-x
```



### 7.2HDFS 的API案例操作

#### HDFS文件上传（测试参数优先级）

- 编写源代码

```java
@Test
public void testCopyFromLocalFile() throws IOException, InterruptedException, URISyntaxException {

    // 1 获取文件系统
    Configuration configuration = new Configuration();
    configuration.set("dfs.replication", "2");
    FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

    // 2 上传文件
    fs.copyFromLocalFile(new Path("d:/sunwukong.txt"), new Path("/xiyou/huaguoshan"));

    // 3 关闭资源
    fs.close();
}
```

- 将hdfs-site.xml拷贝到项目的resources资源目录下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<configuration>
	<property>
		<name>dfs.replication</name>
         <value>1</value>
	</property>
</configuration>
```

- 参数优先级

参数优先级排序：（1）客户端代码中设置的值 >（2）ClassPath下的用户自定义配置文件 >（3）然后是服务器的自定义配置（xxx-site.xml） >（4）服务器的默认配置（xxx-default.xml）



#### HDFS文件下载

```java
@Test
public void testCopyToLocalFile() throws IOException, InterruptedException, URISyntaxException{

    // 1 获取文件系统
    Configuration configuration = new Configuration();
    FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
    
    // 2 执行下载操作
    // boolean delSrc 指是否将原文件删除
    // Path src 指要下载的文件路径
    // Path dst 指将文件下载到的路径
    // boolean useRawLocalFileSystem 是否开启文件校验
    fs.copyToLocalFile(false, new Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("d:/sunwukong2.txt"), true);
    
    // 3 关闭资源
    fs.close();
}
```



#### HDFS文件更名和移动

```java
@Test
public void testRename() throws IOException, InterruptedException, URISyntaxException{

	// 1 获取文件系统
	Configuration configuration = new Configuration();
	FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu"); 
		
	// 2 修改文件名称
	fs.rename(new Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("/xiyou/huaguoshan/meihouwang.txt"));
		
	// 3 关闭资源
	fs.close();
}
```



#### HDFS删除文件和目录

```java
@Test
public void testDelete() throws IOException, InterruptedException, URISyntaxException{

	// 1 获取文件系统
	Configuration configuration = new Configuration();
	FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
		
	// 2 执行删除
	fs.delete(new Path("/xiyou"), true);
		
	// 3 关闭资源
	fs.close();
}
```



#### HDFS文件详情查看

查看文件名称、权限、长度、块信息

```java
@Test
public void testListFiles() throws IOException, InterruptedException, URISyntaxException {

	// 1获取文件系统
	Configuration configuration = new Configuration();
	FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

	// 2 获取文件详情
	RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

	while (listFiles.hasNext()) {
		LocatedFileStatus fileStatus = listFiles.next();

		System.out.println("========" + fileStatus.getPath() + "=========");
		System.out.println(fileStatus.getPermission());
		System.out.println(fileStatus.getOwner());
		System.out.println(fileStatus.getGroup());
		System.out.println(fileStatus.getLen());
		System.out.println(fileStatus.getModificationTime());
		System.out.println(fileStatus.getReplication());
		System.out.println(fileStatus.getBlockSize());
		System.out.println(fileStatus.getPath().getName());

		// 获取块信息
		BlockLocation[] blockLocations = fileStatus.getBlockLocations();
		System.out.println(Arrays.toString(blockLocations));
	}
	// 3 关闭资源
	fs.close();
}
```



#### 文件和文件夹判断

```java
@Test
public void testListStatus() throws IOException, InterruptedException, URISyntaxException{

    // 1 获取文件配置信息
    Configuration configuration = new Configuration();
    FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");

    // 2 判断是文件还是文件夹
    FileStatus[] listStatus = fs.listStatus(new Path("/"));

    for (FileStatus fileStatus : listStatus) {

        // 如果是文件
        if (fileStatus.isFile()) {
            System.out.println("f:"+fileStatus.getPath().getName());
        }else {
            System.out.println("d:"+fileStatus.getPath().getName());
        }
    }

    // 3 关闭资源
    fs.close();
}
```



## 8 读写流程（面试重点）

### 8.1HDFS 写数据流程

#### 刨析文件写入

- （1）客户端通过 Distributed FileSystem 模块向 NameNode 请求上传文件，NameNode 检查目标文件是否已存在，父目录是否存在。
- （2）NameNode 返回是否可以上传。
- （3）客户端请求第一个 Block 上传到哪几个 DataNode 服务器上。
- （4）NameNode 返回3个 DataNode节点，分别为 dn1、dn2、dn3。
- （5）客户端通过 FSDataOutputStream 模块请求 dn1上传数据，dn1 收到请求会继续调用 dn2，然后 dn2调用 dn3，将这个通信管道建立完成。
- （6）dn1、dn2、dn3逐级应答客户端。
- （7）客户端开始往dn1上传第一个 Block（先从磁盘读取数据放到一个本地内存缓存），以 Packet 为单位，dn1收到一个Packet就会传给dn2，dn2传给dn3；dn1每传一个packet会放入一个应答队列等待应答。
- （8）当一个 Block传输完成之后，客户端再次请求 NameNode 上传第二个 Block 的服务器。（重复执行3-7步）。





#### 网络拓扑-节点距离计算



#### 机架感知（副本存储节点选择）





### 8.2HDFS 读数据流程









## 9 NameNode和SecondaryNameNode

## 10 DataNode

### 10.1DataNode工作机制

- 一个数据块在 DataNode 上以文件形式存储在磁盘上，包括两个文件，一个是数据本身，一个是元数据包括数据块的长度，块数据的校验和，以及时间戳。

- DataNode 启动后向 NameNode 注册，通过后，周期性（6小时）的向NameNode上报所有的块信息。

  - DN向NN汇报当前解读信息的时间间隔，默认6小时

  ```xml
  <property>
  	<name>dfs.blockreport.intervalMsec</name>
  	<value>21600000</value>
  	<description>Determines block reporting interval in milliseconds.</description>
  </property>
  ```

  - DN扫描自己节点块信息列表的时间，默认6小时

  ```xml
  <property>
  	<name>dfs.datanode.directoryscan.interval</name>
  	<value>21600s</value>
  	<description>Interval in seconds for Datanode to scan data directories and reconcile the difference between blocks in memory and on the disk.
  	Support multiple time unit suffix(case insensitive), as described
  	in dfs.heartbeat.interval.
  	</description>
  </property>
  ```

  

- 心跳是每 3秒一次，心跳返回结果带有 NameNode给该 DataNode的命令如复制块数据到另一台机器，或删除某个数据块。如果超过 10分钟没有收到某个 DataNode的心跳，则认为该节点不可用。
- 集群运行中可以安全加入和退出一些机器。

### 10.2数据完整性

- DataNode 节点保证数据完整性的方法。

  - （1）当 DataNode 读取 Block 的时候，它会计算 CheckSum。

  - （2）如果计算后的CheckSum，与 Block创建时值不一样，说明 Block已经损坏。

  - （3）Client 读取其他 DataNode上的 Block。

  - （4）常见的校验算法 crc（32），md5（128），sha1（160）

  - （5）DataNode 在其文件创建后周期验证 CheckSum。



### 10.3掉线时限参数设置



# MapReduce

## 11 MapReduce 概述

### 11.1MapReduce 定义

- **分布式运算程序**的编程框架，是用户开发“基于Hadoop的数据分析应用”的核心框架。

- 核心功能是将**用户编写的业务逻辑代码**和**自带默认组件**整合成一个完整的分布式运算程序，并发运行在一个Hadoop集群上。

### 11.2MapReduce 优缺点

#### 优点👉

- 易于编程
  - 简单的实现一些接口，就可以完成一个分布式程序，这个分布式程序可以分布到大量廉价的PC机器上运行。
- 良好的扩展性
  - 可以通过简单的增加机器来扩展它的计算能力。
- 高容错性
  - 比如**其中一台机器挂了，它可以把上面的计算任务转移到另外一个节点上运行，不至于这个任务运行失败**，而且这个过程不需要人工参与，而完全是由Hadoop内部完成的。
- 适合 PB级以上海量数据的离线处理
  - 可以实现上千台服务器集群并发工作，提供数据处理能力。



#### 缺点

- 不擅长实时计算
  - 无法像MySQL一样，在毫秒或者秒级内返回结果。
- 不擅长流式计算
  - 流式计算的输入数据是动态的，而 MapReduce的输入数据集是静态的，不能动态变化。
  - 这是因为MapReduce自身的设计特点决定了数据源必须是静态的。
- 不擅长DAG计算



### 11.3MapReduce 核心思想



- 分布式的运算程序需要分成至少2个阶段。
- 第一阶段的 `MapTask` 并发实例，完全并行运行，互不相干。
- 第二阶段的 `ReduceTask` 并发实例互不相干，但是他们的数据依赖于上一个阶段的所有 MapTask并发实例的输出。
- MapReduce 编程模型只能包含一个 Map 阶段和一个 Reduce 阶段，如果用户的业务逻辑非常复杂，那就只能多个MapReduce程序，串行运行。
- 总结：分析 WordCount 数据流走向深入理解 MapReduce 核心思想。



### 11.4MapReduce 进程

- 一个完整的MapReduce程序在分布式运行时有三类实例进程
  - MrAppMaster：负责整个程序的过程调度及状态协调。
  - MapTask：负责 Map 阶段的整个数据处理流程。
  - ReduceTask：负责 Reduce 阶段的整个数据处理流程。



### 11.5官方WordCount源码

采用反编译工具反编译源码，发现 WordCount案例有 Map类、Reduce类和驱动类。且数据的类型是Hadoop自身封装的序列化类型。

### 11.6常用数据序列化类型

| **Java类型** | **Hadoop Writable类型** |
| ------------ | ----------------------- |
| Boolean      | BooleanWritable         |
| Byte         | ByteWritable            |
| Int          | IntWritable             |
| Float        | FloatWritable           |
| Long         | LongWritable            |
| Double       | DoubleWritable          |
| String       | Text                    |
| Map          | MapWritable             |
| Array        | ArrayWritable           |
| Null         | NullWritable            |

###  11.7MapReduce 编程规范

用户编写的程序分成三个部分：Mapper、Reducer和Driver。

#### Mapper

- 用户自定义的 Mapper要继承自己的父类
- Mapper的输入数据是 KV对的形式(KV的类型可自定义)
- Mapper中的业务逻辑写在 mapO方法中
- Mapper的输出数据是 KV对的形式(KV的类型可自定义)
- map0 方法(Map Taski进程)对每一个 <K,V> 调用一次



#### Reducer





#### Driver



### 11.8WordCount案例实操

- 需求描述

  - 在给定的文本文件中统计输出每一个单词出现的总次数

- 需求分析

  - 按照 MapReduce编程规范，分别编写 Mapper，Reducer，Driver。

- 环境准备

  - 创建maven工程，MapReduceDemo
  - 在pom.xml 文件中添加如下依赖

  ```xml
  <dependencies>
      <dependency>
          <groupId>org.apache.hadoop</groupId>
          <artifactId>hadoop-client</artifactId>
          <version>3.1.3</version>
      </dependency>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
      </dependency>
      <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-log4j12</artifactId>
          <version>1.7.30</version>
      </dependency>
  </dependencies>
  ```

  - 在项目的 src/main/resources 目录下，新建一个文件，命名为 “log4j.properties”，在文件中填入

  ```properties
  log4j.rootLogger=INFO, stdout  
  log4j.appender.stdout=org.apache.log4j.ConsoleAppender  
  log4j.appender.stdout.layout=org.apache.log4j.PatternLayout  
  log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n  
  log4j.appender.logfile=org.apache.log4j.FileAppender  
  log4j.appender.logfile.File=target/spring.log  
  log4j.appender.logfile.layout=org.apache.log4j.PatternLayout  
  log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
  ```

  - 创建包名：com.atguigu.mapreduce.wordcount

- 编写程序

  - 编写 Mapper 类

  ```java
  package com.atguigu.mapreduce.wordcount;
  import java.io.IOException;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.LongWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Mapper;
  
  public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
  	
  	Text k = new Text();
  	IntWritable v = new IntWritable(1);
  	
  	@Override
  	protected void map(LongWritable key, Text value, Context context)	throws IOException, InterruptedException {
  		
  		// 1 获取一行
  		String line = value.toString();
  		
  		// 2 切割
  		String[] words = line.split(" ");
  		
  		// 3 输出
  		for (String word : words) {
  			
  			k.set(word);
  			context.write(k, v);
  		}
  	}
  }
  ```

  - 编写 Reducer 类

  ```java
  package com.atguigu.mapreduce.wordcount;
  import java.io.IOException;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Reducer;
  
  public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
  
  int sum;
  IntWritable v = new IntWritable();
  
  	@Override
  	protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
  		
  		// 1 累加求和
  		sum = 0;
  		for (IntWritable count : values) {
  			sum += count.get();
  		}
  		
  		// 2 输出
           v.set(sum);
  		context.write(key,v);
  	}
  }
  ```

  - 编写 Driver 驱动类

  ```java
  package com.atguigu.mapreduce.wordcount;
  import java.io.IOException;
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  
  public class WordCountDriver {
  
  	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
  
  		// 1 获取配置信息以及获取job对象
  		Configuration conf = new Configuration();
  		Job job = Job.getInstance(conf);
  
  		// 2 关联本Driver程序的jar
  		job.setJarByClass(WordCountDriver.class);
  
  		// 3 关联Mapper和Reducer的jar
  		job.setMapperClass(WordCountMapper.class);
  		job.setReducerClass(WordCountReducer.class);
  
  		// 4 设置Mapper输出的kv类型
  		job.setMapOutputKeyClass(Text.class);
  		job.setMapOutputValueClass(IntWritable.class);
  
  		// 5 设置最终输出kv类型
  		job.setOutputKeyClass(Text.class);
  		job.setOutputValueClass(IntWritable.class);
  		
  		// 6 设置输入和输出路径
  		FileInputFormat.setInputPaths(job, new Path(args[0]));
  		FileOutputFormat.setOutputPath(job, new Path(args[1]));
  
  		// 7 提交job
  		boolean result = job.waitForCompletion(true);
  		System.exit(result ? 0 : 1);
  	}
  }
  ```

  

- 本地测试

  - 需要首先配置好 HADOOP_HOME 变量以及 Windows 运行依赖
  - 在 IDEA/Eclipse 上运行程序



## 12 Hadoop 序列化

### 12.1序列化概述

- 什么是序列化
  - 序列化就是**把内存中的对象，转换成字节序列**（或其他数据传输协议）以便于存储到磁盘（持久化）和网络传输。
  - 反序列化就是将收到字节序列（或其他数据传输协议）或者是**磁盘的持久化数据，转换成内存中的对象**。

- 为什么要序列化
  - 一般来说，“活的”对象只生存在内存里，关机断电就没有了。而且“活的”对象只能由本地的进程使用，不能被发送到网络上的另外一台计算机。
  - **然而序列化可以存储“活的”对象，可以将“活的”对象发送到远程计算机**。
- 为什么不用 Java 的序列化
  - `Java` 的序列化是一个**重量级序列化框架**（Serializable），一个对象被序列化后，会附带很多额外的信息（各种校验信息，Header，继承体系等），不便于在网络中高效传输。
  - 所以，Hadoop自己开发了一套序列化机制（Writable）。
- Hadoop 序列化特点
  - 紧凑：高效使用存储空间
  - 快速：读写数据的额外开销小
  - 互操作：支持多语言的交互



### 12.2自定义bean对象实现序列化接口（Writable）

- 在企业开发中往往常用的基本序列化类型不能满足所有需求，比如在Hadoop框架内部传递一个 `bean` 对象，那么该对象就需要实现序列化接口。

- 具体实现 bean 对象序列化步骤如下7步。

  - （1）必须实现 `Writable` 接口
  - （2）反序列化时，需要反射调用空参构造函数，所以必须有空参构造

  ```java
  public FlowBean() {
  	super();
  }
  ```

  - （3）重写序列化方法

  ```java
  @Override
  public void write(DataOutput out) throws IOException {
  	out.writeLong(upFlow);
  	out.writeLong(downFlow);
  	out.writeLong(sumFlow);
  }
  ```

  - （4）重写反序列化方法

  ```java
  @Override
  public void readFields(DataInput in) throws IOException {
  	upFlow = in.readLong();
  	downFlow = in.readLong();
  	sumFlow = in.readLong();
  }
  ```

  - （5）注意反序列化的顺序和序列化的顺序完全一致
  - （6）要想把结果显示在文件中，需要重写 `toString()` ，可用 "\t" 分开，方便后续用。
  - （7）如果需要将自定义的 bean 放在 key 中传输，则还需要实现 `Comparable` 接口，因为MapReduce框中的 Shuffle 过程要求对 key 必须能排序。详见后面排序案例。

### 12.3序列化案例实操

- 需求
- 需求分析
- 编写 `MapReduce` 程序

  - 编写流量统计的 Bean对象

  ```java
  package com.atguigu.mapreduce.writable;
  
  import org.apache.hadoop.io.Writable;
  import java.io.DataInput;
  import java.io.DataOutput;
  import java.io.IOException;
  
  //1 继承Writable接口
  public class FlowBean implements Writable {
  
      private long upFlow; //上行流量
      private long downFlow; //下行流量
      private long sumFlow; //总流量
  
      //2 提供无参构造
      public FlowBean() {
      }
  
      //3 提供三个参数的getter和setter方法
      public long getUpFlow() {
          return upFlow;
      }
      public void setUpFlow(long upFlow) {
          this.upFlow = upFlow;
      }
      
      public long getDownFlow() {
          return downFlow;
      }
      public void setDownFlow(long downFlow) {
          this.downFlow = downFlow;
      }
      
      public long getSumFlow() {
          return sumFlow;
      }
      public void setSumFlow(long sumFlow) {
          this.sumFlow = sumFlow;
      }
  
      public void setSumFlow() {
          this.sumFlow = this.upFlow + this.downFlow;
      }
  
      //4 实现序列化和反序列化方法,注意顺序一定要保持一致
      @Override
      public void write(DataOutput dataOutput) throws IOException {
          dataOutput.writeLong(upFlow);
          dataOutput.writeLong(downFlow);
          dataOutput.writeLong(sumFlow);
      }
  
      @Override
      public void readFields(DataInput dataInput) throws IOException {
          this.upFlow = dataInput.readLong();
          this.downFlow = dataInput.readLong();
          this.sumFlow = dataInput.readLong();
      }
  
      //5 重写ToString
      @Override
      public String toString() {
          return upFlow + "\t" + downFlow + "\t" + sumFlow;
      }
  }
  ```

  - 编写Mapper类

  ```java
  package com.atguigu.mapreduce.writable;
  
  import org.apache.hadoop.io.LongWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Mapper;
  import java.io.IOException;
  
  public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
      private Text outK = new Text();
      private FlowBean outV = new FlowBean();
  
      @Override
      protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
  
          //1 获取一行数据,转成字符串
          String line = value.toString();
  
          //2 切割数据
          String[] split = line.split("\t");
  
          //3 抓取我们需要的数据:手机号,上行流量,下行流量
          String phone = split[1];
          String up = split[split.length - 3];
          String down = split[split.length - 2];
  
          //4 封装outK outV
          outK.set(phone);
          outV.setUpFlow(Long.parseLong(up));
          outV.setDownFlow(Long.parseLong(down));
          outV.setSumFlow();
  
          //5 写出outK outV
          context.write(outK, outV);
      }
  }
  ```

  - 编写 Reducer类

  ```java
  package com.atguigu.mapreduce.writable;
  
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Reducer;
  import java.io.IOException;
  
  public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
      private FlowBean outV = new FlowBean();
      @Override
      protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
  
          long totalUp = 0;
          long totalDown = 0;
  
          //1 遍历values,将其中的上行流量,下行流量分别累加
          for (FlowBean flowBean : values) {
              totalUp += flowBean.getUpFlow();
              totalDown += flowBean.getDownFlow();
          }
  
          //2 封装outKV
          outV.setUpFlow(totalUp);
          outV.setDownFlow(totalDown);
          outV.setSumFlow();
  
          //3 写出outK outV
          context.write(key,outV);
      }
  }
  ```

  - 编写Driver驱动类

  ```java
  package com.atguigu.mapreduce.writable;
  
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  import java.io.IOException;
  
  public class FlowDriver {
      public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
  
          //1 获取 job对象
          Configuration conf = new Configuration();
          Job job = Job.getInstance(conf);
  
          //2 关联本 Driver类
          job.setJarByClass(FlowDriver.class);
  
          //3 关联 Mapper和Reducer
          job.setMapperClass(FlowMapper.class);
          job.setReducerClass(FlowReducer.class);
          
  		//4 设置Map端输出KV类型
          job.setMapOutputKeyClass(Text.class);
          job.setMapOutputValueClass(FlowBean.class);
          
  		//5 设置程序最终输出的KV类型
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(FlowBean.class);
          
  		//6 设置程序的输入输出路径
          FileInputFormat.setInputPaths(job, new Path("D:\\inputflow"));
          FileOutputFormat.setOutputPath(job, new Path("D:\\flowoutput"));
          
  		//7 提交Job
          boolean b = job.waitForCompletion(true);
          System.exit(b ? 0 : 1);
      }
  }
  ```

  

## 13 MapReduce框架原理

### 13.1 InputFormat数据输入

### 13.2 工作流程

### 13.3 Shuffle机制

### 13.4 OutputFormat数据输出

### 13.5 MapReduce内核源码解析

### 13.6 Join应用

### 13.7 数据清洗（ETL）

### 13.8 MapReduce开发总结





## 14 Hadoop数据压缩

### 14.1 概述

### 14.2 MR支持的压缩编码

- 压缩算法对比介绍

| 压缩格式 | Hadoop自带？ | 算法    | 文件扩展名 | 是否可切片 | 换成压缩格式后，原来的程序是否需要修改 |
| -------- | ------------ | ------- | ---------- | ---------- | -------------------------------------- |
| DEFLATE  | 是，直接使用 | DEFLATE | .deflate   | 否         | 和文本处理一样，不需要修改             |
| Gzip     | 是，直接使用 | DEFLATE | .gz        | 否         | 和文本处理一样，不需要修改             |
| bzip2    | 是，直接使用 | bzip2   | .bz2       | 是         | 和文本处理一样，不需要修改             |
| LZO      | 否，需要安装 | LZO     | .lzo       | 是         | 需要建索引，还需要指定输入格式         |
| Snappy   | 是，直接使用 | Snappy  | .snappy    | 否         | 和文本处理一样，不需要修改             |

- 压缩性能的比较

| 压缩算法 | 原始文件大小 | 压缩文件大小 | 压缩速度 | 解压速度 |
| -------- | ------------ | ------------ | -------- | -------- |
| gzip     | 8.3GB        | 1.8GB        | 17.5MB/s | 58MB/s   |
| bzip2    | 8.3GB        | 1.1GB        | 2.4MB/s  | 9.5MB/s  |
| LZO      | 8.3GB        | 2.9GB        | 49.3MB/s | 74.6MB/s |

### 14.3 压缩方式选择

压缩方式选择时重点考虑：**压缩 / 解压缩速度、压缩率（压缩后存储大小）、压缩后是否可以支持切片**。



### 14.4 压缩参数配置

- 为了支持多种压缩/解压缩算法，Hadoop引入了编码/解码器

| 压缩格式 | 对应的编码/解码器                          |
| -------- | ------------------------------------------ |
| DEFLATE  | org.apache.hadoop.io.compress.DefaultCodec |
| gzip     | org.apache.hadoop.io.compress.GzipCodec    |
| bzip2    | org.apache.hadoop.io.compress.BZip2Codec   |
| LZO      | com.hadoop.compression.lzo.LzopCodec       |
| Snappy   | org.apache.hadoop.io.compress.SnappyCodec  |

- 要在Hadoop中启用压缩，可以配置如下参数

| 参数                                                         | 默认值                                              | 阶段        | 建议                                          |
| ------------------------------------------------------------ | --------------------------------------------------- | ----------- | --------------------------------------------- |
| io.compression.codecs<br/>  （在core-site.xml中配置）        | 无，这个需要在命令行输入<br/>hadoop checknative查看 | 输入压缩    | Hadoop使用文件扩展名判断是否支持某种编解码器  |
| mapreduce.map.output.compress<br>（在mapred-site.xml中配置） | false                                               | mapper输出  | 这个参数设为true启用压缩                      |
| mapreduce.map.output.compress.codec<br/>（在mapred-site.xml中配置） | org.apache.hadoop.io<br/>.compress.DefaultCodec     | mapper输出  | 企业多使用LZO或Snappy编解码器在此阶段压缩数据 |
| mapreduce.output.fileoutputformat<br/>.compress（在mapred-site.xml中配置） | false                                               | reducer输出 | 这个参数设为true启用压缩                      |
| mapreduce.output.fileoutputformat<br/>.compress.codec（在mapred-site.xml中配置） | org.apache.hadoop.io<br/>.compress.DefaultCodec     | reducer输出 | 使用标准工具或者编解码器，如gzip和bzip2       |



### 14.5 压缩实操案例



## 15 常见错误及解决方案



# yarn

## 16 Yarn资源调度器

### 16.1基础架构





### 16.2工作机制





### 16.3作业提交全过程





### 16.4调度器和调度算法



### 16.5常用命令





### 16.6生产环境核心参数





## 17 Yarn案例实操

> 注：调整下列参数之前尽量拍摄Linux快照，否则后续的案例，还需要重写准备集群。





# 源码解析













# 生产调优手册





# 推荐

- [尚硅谷大数据Hadoop教程（Hadoop 3.x安装搭建到集群调优）](https://www.bilibili.com/video/BV1Qp4y1n7EN)

```bash
# 178P，27.5小时

# 进行中
```











































































![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220730085217510.png)





