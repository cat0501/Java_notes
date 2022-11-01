# 

## 1. 初识 Docker

## 2. Docker 命令

### 2.1 安装

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
```



### 2.2 进程相关

```bash
### 启动docker服务
systemctl start docker
### 停止docker服务
systemctl stop docker
### 重启docker服务
systemctl restart docker

### 查看docker服务状态
systemctl status docker
### 开机启动docker服务
systemctl enable docker
```





### 2.3 镜像相关

```bash
### 查看本地所有镜像
docker images
### 删除镜像
docker rmi 镜像id
### 拉取镜像
docker pull 镜像名称
### 搜索镜像
docker search 镜像名称
```





### 2.4 容器相关

```bash
### 创建并启动容器
docker run 参数
-d：以守护（后台）模式运行容器。创建一个容器在后台运行，需要使用docker exec 进入容器。退出后，容器不会关闭。
-p：指定端口映射，格式：主机端口：容器端口
-e MYSQL_ROOT_PASSWORD=123456：设置环境变量
-v：目录影射，容器目录挂载到宿主机目录。格式：宿主机目录：容器目录
--name：为创建的容器命名

### 进入容器
docker exec 参数
docker exec -it db1 /bin/bash
```



```bash
docker run -d -p 3316:3306 -e MYSQL_ROOT_PASSWORD=123456 --name db1 -v /home/mysql/data:/var/lib/mysql -v /root/db1/my.cnf:/etc/mysql/my.cnf -v /home/mysql/mysql-files:/var/lib/mysql-files/ mysql:8.0.27

docker run -d -p 3326:3306 -e MYSQL_ROOT_PASSWORD=123456 --name db2 -v /home/mysql/data:/var/lib/mysql -v /root/db2/my.cnf:/etc/mysql/my.cnf -v /home/mysql/mysql-files:/var/lib/mysql-files/ mysql:8.0.27
```







```bash
### 查看所有容器
docker ps -a
### 删除容器
docker rm 9ce0770ca222
```







## 3. Docker 容器数据卷

## Dockerfile

## Docker 应用部署

## Docker 服务编排

## Docker 私有仓库

## Docker相关概念