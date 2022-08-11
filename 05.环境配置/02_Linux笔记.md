# 

# Linux

## 1 常用命令

```sh
# 查看端口占用情况（用kill pid来杀掉进程）
$ lsof -i tcp:port
$ netstat -tunlp|grep 3306

# 创建空文件夹
$ mkdir test
# 创建一个文件
touch .gitattrbutes

# 压缩文件与解压缩
$ tar -czvf dist.tar.gz dist
$ tar -xzvf dist.tar.gz


# 运行java应用
$ nohup java -jar demo.jar &
# 正在运行的java应用（进程）
$ ps -aux | grep java


# linux批量替换文件夹及子文件夹中文件的内容
# 上述命令把当前目录以及子目录中的所有文件的 old 替换为 new 
find -type f -name "*.py" | xargs sed 's#old#new#g' -i

# 查找并删除文件（面试题）


# linux将多个文件的内容合并
cat b1.sql b2.sql b3.sql > b_all.sql
cat *.sql > merge.sql


mv 

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



## 2 简介与安装Linux环境（含虚拟机配置）

- Linux 的发行版：简单来说，就是将 Linux 内核与应用软件做一个打包。

- 目前市面上较知名的发行版有
  - Ubuntu、RedHat、CentOS
  - Debian、Fedora、SuSE、OpenSUSE、Arch Linux、SolusOS 等

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20211208113912602.png)



- Linux 的安装步骤比较繁琐，现在其实云服务器挺普遍的，价格也便宜。
  - 如果自己不想搭建，也可以直接买一台学习用用，参考[各大云服务器比较](https://www.runoob.com/linux/linux-cloud-server.html)。

- 虚拟机安装后占用空间，也会有些卡顿，我们作为程序员其实可以选择购买一台自己的服务器，这样的话更加接近真实线上工作。



### 2.1 安装VMware虚拟机来安装Linux系统

- 安装 VMware 虚拟机软件，然后使用镜像，配置网卡！
- https://www.runoob.com/w3cnote/vmware-install-centos7.html



#### 虚拟机配置（重要！）

- VMware Fusion 12.1.2
- Centos 7.6


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



### 2.2 购买云服务器(Elastic Compute Service, ECS)

- 阿里云
- 腾讯云

```sh
1、阿里云购买服务器：https://www.aliyun.com/minisite/goods?userCode=0phtycgr
2、购买完毕后，获取服务器的ip地址，重置服务器密码，就可以远程登录了
3、下载 xShell 工具，进行远程连接使用！
注意事项：
如果要打开端口，需要在阿里云的安全组面板中开启对应的出入规则，不然的话会被阿里拦截！
```



### 2.3 关机

在 linux 领域内大多用在服务器上，很少遇到关机的操作。毕竟服务器上跑一个服务是永无止境的，除非特殊情况下，不得已才会关机。

正确的关机流程为：sync > shutdown > reboot > halt

最后总结一下，不管是重启系统还是关闭系统，首先要运行 **sync** 命令，把内存中的数据写到磁盘中。

```bash
# 将数据由内存同步到硬盘中
sync

# 关机指令
shutdown
```



## 3 Linux系统目录结构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211207142241892-20220212171125315.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220212171804813.png)



```sh
# 在 Linux 系统中，有几个目录是比较重要的，平时需要注意不要误删除或者随意更改内部文件。
/etc：上边也提到了，这个是系统中的配置文件，如果你更改了该目录下的某个文件可能会导致系统不能启动。
/bin, /sbin, /usr/bin, /usr/sbin: 这是系统预设的执行文件的放置目录，比如 ls 就是在 /bin/ls 目录下的。
值得提出的是，/bin, /usr/bin 是给系统用户使用的指令（除root外的通用户），而/sbin, /usr/sbin 则是给 root 使用的指令。

/var： 各个程序产生的日志被记录到这个目录下，具体在 /var/log 目录下，另外 mail 的预设放置也是在这里。

# 在Linux文件系统中有两个特殊的目录
一个用户所在的工作目录，也叫当前目录，可以使用一个点 . 来表示；
另一个是当前目录的上一级目录，也叫父目录，可以使用两个点 .. 来表示。
```





```sh
# binary缩写，存放着最经常使用的命令。
/bin

/boot: 启动Linux时使用的一些核心文件，包括一些连接文件以及镜像文件。
/dev: Device（设备）的缩写，存放的是Linux的外部设备。在Linux中访问设备的方式和访问文件的方式是相同的。

# 所有的系统管理所需要的配置文件和子目录。
/etc
# 用户的主目录。Linux中每个用户都有一个自己的目录，一般该目录名是以用户的账号命名的。
/home

/lib: 存放系统最基本的动态连接共享库，作用类似于windows的DLL文件。
/lost+found: 一般为空，系统非法关机后存放一些文件。
/media: Linux系统会自动识别一些设备，例如U盘、光驱等等。识别后Linux会把识别的设备挂载到这个目录下。
# 系统提供该目录是为了让用户临时挂载别的文件系统的。
# 我们可以将光驱挂载在 /mnt/ 上，然后进入该目录就可以查看光驱里的内容了。
/mnt

# 给主机额外安装软件的目录。比如你安装一个Oracle数据库就可以放到这个目录下，默认为空。
/opt

/proc: 存储的是当前内核运行状态的一系列特殊文件

# 系统管理员，也称作超级权限者的用户主目录。
/root

/sbin: 存放的是系统管理员使用的系统管理程序。
/srv: 存放一些服务启动之后需要提取的数据。
/sys: 该目录下安装了 2.6 内核中新出现的一个文件系统 sysfs 。
/tmp: 存放一些临时文件。

# 这是一个非常重要的目录，用户的很多应用程序和文件都放在这个目录下，类似于windows下的program files目录。
/usr

/usr/src: 内核源代码默认的放置目录。
/var: 这个目录中存放着在不断扩充着的东西，我们习惯将那些经常被修改的目录放在这个目录下。包括各种日志文件。
```





## 4 Linux远程登录（工具）

> Linux 系统中是通过 ssh 服务实现的远程登录功能， ssh 服务默认端口号为 22。

- Windows 
  -  SecureCRT, Putty, SSH Secure Shell 等

- mac
  - electerm（推荐）

## 5 Linux文件

### 基本属性

> Linux 系统是一种典型的多用户系统，不同的用户处于不同的地位，拥有不同的权限。为了保护系统的安全性，Linux 系统对不同的用户访问同一文件（包括目录文件）的权限做了不同的规定。

```sh
# 显示一个文件的属性以及文件所属的用户和组
$ ls -l
```



> 第一个字符代表这个文件是目录、文件或链接文件等等。当为 **d** 则是目录；当为 **-** 则是文件；若是 **l** 则表示为链接文档(link file)。

> 接下来的字符以三个为一组，且均为 **rwx** 的组合。其中， **r** 代表可读(read)、 **w** 代表可写(write)、 **x** 代表可执行(execute)。 要注意的是，这三个权限的位置不会改变，如果没有权限，就会出现减号 **-** 而已。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220212180836310.png)



#### Linux文件属主和属组

Linux系统按文件所有者、文件所有者同组用户和其他用户来规定了不同的文件访问权限。

对于 root 用户来说，一般情况下，文件的权限对其不起作用。

#### 更改文件属性

```sh
# 当权限为-rwxrwx---
chmod 777 文件或目录
chmod [-R] 777 文件或目录
```



### 文件和目录管理

```sh
# 列出目录（可能是最常被运行的）
ls -a
# 切换目录
cd [相对路径或绝对路径]
# 显示目前所在的目录
pwd

# 创建新目录
mkdir test
# 删除空的目录
rmdir test
rmdir -p test1/test2/test3/test4
# 你可以使用 rm 命令来删除非空目录
rm [-fir] 文件或目录
    -f ：就是 force 的意思，忽略不存在的文件，不会出现警告信息；
    -i ：互动模式，在删除前会询问使用者是否动作
    -r ：递归删除啊！最常用在目录的删除了！这是非常危险的选项！！！

# 移动文件与目录，或修改名称
mv [-fiu] source destination
    -f ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
    -i ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
    -u ：若目标文件已经存在，且 source 比较新，才会升级 (update)

# 文件内容查看
cat -b 文件名

```



## 6 Linux用户（组）管理

### 添加用户

```sh
useradd 选项 用户名
```



## 7 Linux磁盘管理

> Linux 磁盘管理常用三个命令为 **df**、**du** 和 **fdisk**。
>
> - **df**（英文全称：disk full）：列出文件系统的整体磁盘使用量
> - **du**（英文全称：disk used）：检查磁盘空间使用量
> - **fdisk**：用于磁盘分区



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



## 8 Linux yum/apt 命令













<hr>


## Linux服务器上更新服务

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













## 常用基本命令

#### 目录管理

> **ls列出目录**

**ls -al**

**-a ：全部的文件，连同隐藏文件( 开头为 . 的文件) 一起列出来(常用)**
**-l ：长数据串列出，包含文件的属性与权限等等数据；(常用)**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211207145103938.png)



> **cd （切换目录）**

```bash
# 回到根目录
[root@pt-dev pt]# cd /
[root@pt-dev /]# ls
bin   data  etc   jitlog  lib64  mnt  oracle19  root  sbin  ssd  tmp    usr  www
boot  dev   home  lib     media  opt  proc      run   srv   sys  Users  var
[root@pt-dev /]# 

# 回到家目录，即/root这个目录
[root@pt-dev /]# cd ~
[root@pt-dev ~]# ls
0910BJ_ELEMENT.dmp  0910NEW_LOGGER.dmp    ApiManager-0.0.1-SNAPSHOT.jar    element.dmp     logs              NEW_WORKFLOW.dmp
0910BJ_FRAME.dmp    0910NEW_PORTALET.dmp  billType.dmp                     hn_element.dmp  my-yapi           yapi-1.10.1.zip
0910NEW_FRS.dmp     anaconda-ks.cfg       dm8_20210712_x86_rh6_64_ent.zip  hn_frame.dmp    NEW_BILLTYPE.dmp  yapi-docker

# 回到上一级
[root@pt-dev ~]# cd ..
[root@pt-dev /]# 

# 进入某个目录，如opt目录
[root@pt-dev /]# cd opt
[root@pt-dev opt]# ls
newPt  openoffice4  pt
[root@pt-dev opt]# 
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211207145644741.png)

#### 基本属性（权限）

##### 看懂文件属性

> Linux系统是一种**典型的多用户系统**，不同的用户处于不同的地位，拥有不同的权限。为了保护系统的安全性，Linux系统对不同的用户访问同一文件（包括目录文件）的权限做了不同的规定。
>
> 对于文件来说，它都有一个特定的所有者，也就是对该文件具有所有权的用户。
> 同时，在Linux系统中，用户是按组分类的，一个用户属于一个或多个组。
> 文件所有者以外的用户又可以分为文件所有者的同组用户和其他用户。
> 因此，Linux系统按**文件所有者**、**文件所有者同组用户**和**其他用户**来规定了不同的文件访问权限。
> 在以上实例中，boot 文件是一个目录文件，属主和属组都为 root。

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



<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208111432441.png" alt="image-20211208111432441" style="zoom: 33%;" />

![image-20211208111121039](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208111121039.png)



##### 修改文件属性

Linux文件的基本权限有九个，分别是owner/group/others三种身份各有自己的read/write/execute权限。

我们可以使用数字来代表各个权限，各权限的分数对照表如下：

```bash
r:4 w:2 x:1
```

```bash
每种身份(owner/group/others)各自的三个权限(r/w/x)分数是需要累加的，例如当权限为： [-rwxrwx--
-] 分数则是：
owner = rwx = 4+2+1 = 7
group = rwx = 4+2+1 = 7
others= --- = 0+0+0 = 0
```

```bash
chmod 770 filename
```



**1、chgrp：更改文件属组**

```bash
chgrp [-R] 属组名 文件名
```

-R：递归更改文件属组，就是在更改某个目录文件的属组时，如果加上-R的参数，那么该目录下的所有
文件的属组都会更改。
**2、chown：更改文件属主，也可以同时更改文件属组**

```bash
chown [–R] 属主名 文件名 
chown [-R] 属主名：属组名 文件名
```

**3、chmod：更改文件9个属性**

```bash
chmod [-R] xyz 文件或目录
```









#### 文件内容查看

> **cat**

```bash
# 查看文件内容，列出行号
[root@pt-dev 4a-server]# cat -b application.yml
```



![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211208093224787.png)



#### Vim文件编辑器

> **vim**

```bash
# 进入编辑文件模式
[root@pt-dev 4a-server]# vim application.yml 

# 按下i进入输入模式
# 编辑......
# esc，输入:wq后退出
```



#### 进程管理

>**ps 查看进程**

**-a：显示当前终端的所有进程信息**
**-u：以用户的形式显示进程信息**
**-x：显示后台进程运行的参数**

```bash
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



![image-20211208104744502](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208104744502.png)



![image-20211208110021468](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211208110021468.png)







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



dist文件夹压缩成.tar文件

```
tar -czvf dist.tar.gz dist
```





## 参考与推荐

https://www.runoob.com/linux/linux-tutorial.html





















































