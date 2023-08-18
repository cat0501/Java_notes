



# 安装环境

## 官网下载



## 配置环境变量

## 配置本地仓库 localRepository

```xml
<localRepository>/Users/cat/environment/repo</localRepository>
```



## 配置远程仓库

- 阿里云



安装Oracle驱动 ojdbc8 到本地maven仓库

```xml
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc</artifactId>
    <version>8</version>
</dependency>
```



```sh
mvn install:install-file -Dfile=D:\work\ojdbc-8.jar -DgroupId=com.oracle -DartifactId=ojdbc -Dversion=8 -Dpackaging=jar -DgeneratePom=true
```









# 参考









