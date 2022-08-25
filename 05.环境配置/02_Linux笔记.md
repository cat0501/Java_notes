# 

# 1 Linux 常用命令

## 用户 (组) 相关

```sh
# 切换用户
sudo su

# 添加用户
useradd 选项 用户名
```





## 目录相关

```sh
# 列出目录（可能是最常被运行的）
## -a 全部文件（含隐藏）、-l 详细信息（含权限信息）
ll [-a]
ls [-a、l]

# 切换目录
cd [相对路径或绝对路径]

# 显示目前所在的目录
pwd

# 创建目录
mkdir test
# 删除/递归删除
rmdir test
rm -rf 文件或目录

# 压缩文件与解压缩
tar -czvf dist.tar.gz dist
tar -xzvf dist.tar.gz

# 移动文件与目录，或修改名称
mv source destination
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211207145644741.png)





## 文件相关

```sh
# 文件内容查看
cat -b 文件名

# 创建文件
touch .gitattrbutes

# 查找并删除文件（面试题）
```



## 进程相关

```sh
# 查看端口占用情况（用kill pid来杀掉进程）
$ lsof -i tcp:port
$ netstat -tunlp|grep 3306

# 运行java应用
$ nohup java -jar demo.jar &
# 正在运行的java应用（进程）
$ ps -aux | grep java

```

- ps [-aux]
  - -a：显示当前终端的所有进程信息
  - -u：以用户的形式显示进程信息
  - -x：显示后台进程运行的参数

```sh
# 查看某个服务的进程
# grep 命令用于查找文件里符合条件的字符串
# 命令格式：命令A|命令B，即命令A的正确输出作为命令B的操作对象

[root@pt-dev /]# ps -aux|grep 4A-server-2.2.5-SNAPSHOT.jar
root     18929  7.9  2.4 8546904 795772 pts/1  Sl   09:31   5:58 java -jar -Xmx256m -Xmx256m -Xmn256m 4A-server-2.2.5-SNAPSHOT.jar
root     31087  0.0  0.0 112948  1248 pts/0    S+   10:46   0:00 grep --color=auto 4A-server-2.2.5-SNAPSHOT.jar


# 具体信息解释
[root@pt-dev /]# ps aux
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.0  0.0 193828  4132 ?        Ss   Nov23   1:29 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root     18929  7.1  2.4 8546904 795388 pts/1  Sl   09:31   6:00 java -jar -Xmx256m -Xmx256m -Xmn256m 4A-server-2.2.5-SNAPSHOT.jar

• USER：该 process 属于那个使用者账号的？
• PID ：该 process 的号码。
• %CPU：该 process 使用掉的 CPU 资源百分比；
• %MEM：该 process 所占用的物理内存百分比；
• VSZ ：该 process 使用掉的虚拟内存量 (Kbytes)
• RSS ：该 process 占用的固定的内存量 (Kbytes)
• TTY ：该 process 是在那个终端机上面运作，若与终端机无关，则显示 ?，另外， tty1-tty6 是本机上面的登入者程序，若为 pts/0 等等的，则表示为由网络连接进主机的程序。
• STAT：该程序目前的状态，主要的状态有：
o R ：该程序目前正在运作，或者是可被运作；
o S ：该程序目前正在睡眠当中 (可说是 idle 状态啦！)，但可被某些讯号 (signal) 唤醒。
o T ：该程序目前正在侦测或者是停止了；
o Z ：该程序应该已经终止，但是其父程序却无法正常的终止他，造成 zombie (疆尸) 程序的状态
• START：该 process 被触发启动的时间；
• TIME ：该 process 实际使用 CPU 运作的时间。
• COMMAND：该程序的实际指令为何？
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208104744502.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208110021468.png)



## 磁盘相关

Linux 磁盘管理常用三个命令为 **df**、**du** 和 **fdisk**。

- **df**（英文全称：disk full）：列出文件系统的整体磁盘使用量
- **du**（英文全称：disk used）：检查磁盘空间使用量
- **fdisk**：用于磁盘分区

```sh
# 对文件和目录磁盘使用的空间的查看
du [-ahskm] 文件或目录名称
    -a ：列出所有的文件与目录容量，因为默认仅统计目录底下的文件量而已。
    -h ：以人们较易读的容量格式 (G/M) 显示；
    -s ：列出总量而已，而不列出每个各别的目录占用容量；
    -S ：不包括子目录下的总计，与 -s 有点差别。
    -k ：以 KBytes 列出容量显示；
    -m ：以 MBytes 列出容量显示；
```





## 其它

```sh
# linux批量替换文件夹及子文件夹中文件的内容
# 上述命令把当前目录以及子目录中的所有文件的 old 替换为 new 
find -type f -name "*.py" | xargs sed 's#old#new#g' -i

# linux将多个文件的内容合并
cat b1.sql b2.sql b3.sql > b_all.sql
cat *.sql > merge.sql

# linux下常用的故障排查命令
生产环境服务器变慢，诊断思路和性能评估谈谈
整机：top——看CPU和内存
内存：free
```

- 如何查看 `Jdk` 安装路径

```bash
# 在打印文本的最后两行可以看到jdk的路径
$ java -verbose

# 前提是配置了环境变量$JAVA_HOME
$ echo $JAVA_HOME

# which java
$ which java
$ ls -lrt /usr/bin/java (获取新的指向路径,循环多次)
```



# 2 概述

- Linux 的发行版：简单来说，就是将 Linux 内核与应用软件做一个打包。

- 目前市面上较知名的发行版有
  - Ubuntu、RedHat、CentOS
  - Debian、Fedora、SuSE、OpenSUSE、Arch Linux、SolusOS 等

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208113912602-20220825214701647.png)



- Linux 的安装步骤比较繁琐，其实现在云服务器挺普遍的，价格也便宜。如果自己不想搭建，也可以直接买一台学习用用，参考[各大云服务器比较](https://www.runoob.com/linux/linux-cloud-server.html)。

- 虚拟机安装后占用空间，也会有些卡顿，可以考虑购买一台自己的服务器，这样的话更加接近真实线上工作。



## 目录结构



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211207142241892-20220212171125315.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220212171804813.png)



```bash
# 在Linux文件系统中有两个特殊的目录
一个用户所在的工作目录，也叫当前目录，可以使用一个点 . 来表示
另一个是当前目录的上一级目录，也叫父目录，可以使用两个点 .. 来表示
```





| 目录              | 内容                                     | 说明                               |
| ----------------- | ---------------------------------------- | ---------------------------------- |
| 🎈/etc             | 系统中的配置文件                         |                                    |
| 🎈/bin, /usr/bin   | 给系统用户使用的指令（除root外的通用户） | 比如 ls 就是在 /bin/ls 目录下的    |
| 🎈/sbin, /usr/sbin | 给 root 用户使用的指令                   |                                    |
| 🎈/var             | 各个程序产生的日志被记录到这个目录下     | 具体在 /var/log 目录下             |
| /bin              |                                          | binary缩写，存放着最经常使用的命令 |
| /home             | 用户的主目录                             | Linux中每个用户都有一个自己的目录  |
| /opt              | 安装软件的目录                           | 默认为空                           |
| /root             | 系统管理员的用户主目录                   | 1                                  |
| /usr              | 存放用户的很多应用程序和文件             | 类似于windows下的program files目录 |
| /tmp              | 存放一些临时文件                         |                                    |



```sh
/boot: 启动Linux时使用的一些核心文件，包括一些连接文件以及镜像文件。
/dev: Device（设备）的缩写，存放的是Linux的外部设备。在Linux中访问设备的方式和访问文件的方式是相同的。

/lib: 存放系统最基本的动态连接共享库，作用类似于windows的DLL文件。
/lost+found: 一般为空，系统非法关机后存放一些文件。
/media: Linux系统会自动识别一些设备，例如U盘、光驱等等。识别后Linux会把识别的设备挂载到这个目录下。
# 系统提供该目录是为了让用户临时挂载别的文件系统的。
# 我们可以将光驱挂载在 /mnt/ 上，然后进入该目录就可以查看光驱里的内容了。
/mnt

/proc: 存储的是当前内核运行状态的一系列特殊文件

/srv: 存放一些服务启动之后需要提取的数据。
/sys: 该目录下安装了 2.6 内核中新出现的一个文件系统 sysfs 。

/usr/src: 内核源代码默认的放置目录。
/var: 这个目录中存放着在不断扩充着的东西，我们习惯将那些经常被修改的目录放在这个目录下。包括各种日志文件。
```

## 远程登录（工具）

> 对于Linux 系统，通过 ssh 服务实现远程登录功能， ssh 服务默认端口号为 22。

- Windows 
  -  SecureCRT, Putty, SSH Secure Shell 等工具

- mac
  - electerm（推荐）



## 文件属性

- Linux 系统是一种典型的多用户系统，不同的用户处于不同的地位，拥有不同的权限。
  - 为了保护系统的安全性，Linux 系统对不同的用户访问同一文件（包括目录文件）的权限做了不同的规定。

```sh
# 显示一个文件的属性以及文件所属的用户和组
$ ls -l
```

- Linux 系统按 **文件所有者（属主）、文件所有者同组用户（属组） 和 其他用户**来规定了不同的文件访问权限。
  - 对于 `root` 用户来说，一般情况下，文件的权限对其不起作用。
- 10 个字符确定每一个文件的属性

> 第一个字符代表这个文件是目录、文件或链接文件等等。当为 **d** 则是目录；当为 **-** 则是文件；若是 **l** 则表示为链接文档(link file)。

> 接下来的字符以三个为一组，且均为 **rwx** 的组合。其中， **r** 代表可读(read)、 **w** 代表可写(write)、 **x** 代表可执行(execute)。 要注意的是，这三个权限的位置不会改变，如果没有权限，就会出现减号 **-** 而已。



```bash
第一个字符代表这个文件是目录、文件或链接文件等等：
    当为[ d ]则是目录
    当为[ - ]则是文件；
    若是[ l ]则表示为链接文档 ( link file )；
    若是[ b ]则表示为装置文件里面的可供储存的接口设备 ( 可随机存取装置 )；
    若是[ c ]则表示为装置文件里面的串行端口设备，例如键盘、鼠标 ( 一次性读取装置 )。
接下来的字符中，三个为一组，且均为『rwx』 的三个参数的组合。
		其中，[ r ]代表可读(read)、[ w ]代表可写(write)、[ x ]代表可执行(execute)。如果没有权限，就会出现减号[ - ]而已。
		
每个文件的属性由左边第一部分的10个字符来确定（如下图）：
```





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220212180836310.png)



- 更改文件属性

  - Linux 文件的基本权限有九个，分别是 owner/group/others 三种身份各有自己的 read/write/execute 权限。我们可以使用数字来代表各个权限，各权限的分数对照表如下：

  ```sh
  r:4 w:2 x:1
  ```

  ```sh
  每种身份(owner/group/others)各自的三个权限(r/w/x)分数是需要累加的，例如当权限为： [-rwxrwx--
  -] 分数则是：
  owner = rwx = 4+2+1 = 7
  group = rwx = 4+2+1 = 7
  others= --- = 0+0+0 = 0
  ```

  - chmod：更改文件9个属性

  ```sh
  # 当权限为-rwxrwx---
  chmod 777 文件或目录
  chmod [-R] 777 文件或目录
  ```

  - chgrp：更改文件属组

  ```sh
  # -R：递归更改文件属组，该目录下的所有子目录/文件的属组都会更改
  chgrp [-R] 属组名 文件名
  ```

  - chown：更改文件属主，也可以同时更改文件属组

  ```sh
  chown [–R] 属主名 文件名 
  chown [-R] 属主名：属组名 文件名
  ```





## Vim 文件编辑器

- **vim fileName**
- `i` 进入输入模式
- `esc`，`:wq` 退出



## yum/apt 命令



# 3 安装Linux环境（含虚拟机配置）

## 通过 VMware 虚拟机

- 流程

  - 安装 VMware 虚拟机软件（VMware Fusion 12.1.2）

  - 使用镜像（Centos 7.6）

  - 配置网卡



```bash
# 宿主机IP
ifconfig
192.168.1.10

# 虚拟机1
网络适配器——桥接模式——wifi
[cat@172 ~]$ su root
Password: 
[root@localhost cat]#
## 分配IP
[root@localhost cat]# dhclient
[root@localhost cat]# ifconfig
192.168.1.6

## IP固定下来，重启网卡
vim /etc/sysconfig/network-scripts/ifcfg-ens33
systemctl restart network.service

# 虚拟机2
dhclient(2926) is already running - exiting.
## 查找dhclient进程
ps -ef | grep dhclient
kill -9 xxx

192.168.1.7
```

- 可参考：[菜鸟教程](https://www.runoob.com/w3cnote/vmware-install-centos7.html) 

- 关机
  - 正确的关机流程为：sync（将数据由内存同步到硬盘中） > shutdown > reboot > halt



## 通过购买云服务器 ECS

Elastic Compute Service, ECS

- 阿里云
- 腾讯云







# Linux 服务器上更新服务

> 脚本启停服务
>
> `cat -b 文件名`

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220106091646040.png)



```sh
[root@pt-dev framework-server2]# cat -b shutdown.sh
     1  #!/bin/bash
     2  uwpid=`ps -ef|grep framework-server*.jar | grep -v grep | awk '{print $2}'`
     3  if [ -n "$uwpid" ]
     4  then
     5  echo $uwpid
     6  kill $uwpid
     7  else
     8   echo "application is already stopped"
     9  fi
```



```sh
[root@pt-dev framework-server2]# cat -b startup.sh
     1  #!/bin/bash




     2  nohup java -jar    -Xms256m -Xmx512m -Xmn200m framework-server*.jar  > nohup.out 2>&1 &
```





安装nginx并且进行相关配置

```sh
# 安装nginx,在make编译时报错：make: *** No rule to make target `build', needed by `default'. Stop.
# 解决：

# 更新yum
yum update
# 安装前置库 
yum install -y gcc pcre pcre-devel openssl openssl-devel gd gd-devel
# 进入nginx目录重新编译一次参数：
./configure \

--prefix=/usr/local/nginx \

--pid-path=/usr/local/nginx/run \

--user=nginx \

--group=nginx \

--with-http_ssl_module \

--with-http_flv_module \

--with-http_stub_status_module \

--with-http_gzip_static_module \

--with-pcre \

--with-http_image_filter_module \

--with-debug \

# 再次 
make && make install 
```



# 参考与推荐

https://www.runoob.com/linux/linux-tutorial.html



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208093224787.png)

















































