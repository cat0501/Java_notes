# 第一次尝试

搭建博客：
http://101.42.229.218/

修改文件遇到权限问题这样处理：
https://www.94ip.com/post/799.html



参考的大佬：

https://www.bilibili.com/video/BV1cp4y1i7C7?p=5&spm_id_from=pageDriver

http://fuchenchenle.cn/



https://blog.csdn.net/cungudafa/category_9714183.html

https://cungudafa.blog.csdn.net/article/details/104295156

https://blog.csdn.net/victoryxa/article/details/105841440?spm=1001.2014.3001.5501

https://hexo.io/zh-cn/docs/

https://www.94ip.com/post/799.html



可参考：

https://www.iamys.club/



## 一、Hexo安装过程（mac上）

https://fuchenchenle.cn/2020/08/17/%E8%85%BE%E8%AE%AF%E4%BA%91%EF%BC%8Bhexo-%E6%90%AD%E5%BB%BA%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/
### 准备任务
1.安装node.js和git

```bash
# 验证已安装
(base) cat@zhangjianlindeMacBook-Pro ~ % node -v
v16.10.0
(base) cat@zhangjianlindeMacBook-Pro ~ % npm -v
7.24.0

(base) cat@zhangjianlindeMacBook-Pro ~ % git --version
git version 2.30.1 (Apple Git-130)
(base) cat@zhangjianlindeMacBook-Pro ~ % which git
/usr/bin/git
# 验证node环境配置
https://www.cnblogs.com/richerdyoung/p/7265786.html
```

### 安装
hexo官网    https://hexo.io/zh-cn/docs/
1.安装Hexo

```bash
npm install -g hexo-cli
```

2.生成Hexo

我们执行以下命令，生成一个博客
```bash
hexo init blog
```
> 安装过程中，他会自动生成一个文件夹，这个文件夹就是Hexo的配置文件
> “blog”是你要生成博客的文件夹名称，可以根据自己的喜好来取名
> 等待安装完成

安装完成后，我们进入生成的文件夹，用hexo s 这个命令来启动Hexo

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230004124430.png" alt="image-20211230004124430" style="zoom: 33%;" />

输入它提示的网站 127.0.0.1:4000 进去之后就可以看到

![image-20211230003958902](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230003958902.png)



## 二、部署到腾讯云
http://fuchenchenle.cn/2020/08/18/hexo%E9%83%A8%E7%BD%B2%E8%85%BE%E8%AE%AF%E4%BA%91/

### 云服务器配置Git
1.安装依赖库
```bash
yum install curl-devel expat-devel gettext-devel openssl-devel zlib-devel 
```

2.安装编译工具
```bash
yum install gcc perl-ExtUtils-MakeMaker package
```
3.查看git的版本y
```bash
git version
```
4.删除git
```bash
yum remove git -y
```

5.下载解压最新版
```bash
cd /usr/local/src #下载的目录 wget https://www.kernel.org/pub/software/scm/git/git-2.28.0.tar.gz #下载最新版 tar -zxvf git-2.28.0.tar.gz #解压到当前文件夹
```

6.编辑并安装
```bash
cd git-2.28.0    #进入文件夹
make prefix=/usr/local/git all    #编译源码
make prefix=/usr/local/git install    #安装路径
```

7.配置git的环境变量
```bash
echo 'export PATH=$PATH:/usr/local/git/bin' >> /etc/bashrc
```

8.刷新环境变量
```bash
source /etc/bashrc
```
9.查看版本号
```bash
git --version
```

10.创建git用户并且修改权限

```bash
adduser admin
passwd admin
chmod 740 /etc/sudoers 
vim /etc/sudoers 

root ALL=(ALL) ALL 
fuchen ALL=(ALL) ALL
```

> 创建git用户并且修改权限遇到的问题
Linux修改用户：useradd: cannot open /etc/passwd
https://blog.csdn.net/w5wangdeqing/article/details/90258142


11.本地mac使用Gitbash创建密钥
```bash
ssh-keygen -t rsa
```

12.将本地创建id_rsa.pub中文件复制
```bash
su fuchen
mkdir ~/.ssh
vim ~/.ssh/authorized_keys
```

13.本地测试
```bash
ssh -v admin@101.42.229.218
```






### 云服务器网站配置
1.创建网站目录并且设置权限
```bash
su root
mkdir /home/hexo
chown admin:admin -R /home/hexo
```

2.安装Nginx
```bash
yum install -y nginx
systemctl start nginx.service   #启动服务
```

3.修改Nginx配置文件
```bash
vim /etc/nginx/nginx.conf
```
```bash
    server {
        listen       80;
        listen       [::]:80;
        server_name  101.42.229.218;
        root         /home/hexo;
```

4.重启服务器
```bash
systemctl restart nginx.service
```

5.建立git仓库
```bash
su root
cd /home/admin
git init --bare blog.git
chown admin:admin -R blog.git
```

6.同步网站根目录
```bash
# /home/admin目录下执行下面的命令
vim blog.git/hooks/post-receive

# 添加如下内容
git --work-tree=/home/hexo --git-dir=/home/admin/blog.git checkout -f
```

7.修改权限
```bash
chmod +x /home/fuchen/blog.git/hooks/post-receive
```

8.在windows10本地hexo目录修改_config.yml文件（mac终端）
```bash
deploy:
  type: git
  repository: admin@101.42.229.218:/home/admin/blog.git    #用户名@服务器Ip:git仓库位置
  branch: master
```

9.在本机gitbash部署（mac终端）
```bash
# 清理下缓存
hexo clean
# 运行
hexo g -d
```

运行时出现如下问题并解决：ERROR Deployer not found: git
```bash
# 执行这一句就可以
npm install --save hexo-deployer-git
```
> 感谢下面的博主
https://blog.csdn.net/weixin_36401046/article/details/52940313




## 三、美化
### hexo主题：matery
https://gitee.com/ryanjiena/hexo-theme-matery

下载主题到thems目录下：
git clone https://github.com/blinkfox/hexo-theme-matery.git
修改 Hexo 根目录下的 _config.yml 的 theme 的值：theme: hexo-theme-matery



大佬1
https://blog.csdn.net/cungudafa/category_9714183.html

大佬2
增加樱花🌸飘落效果
https://blog.csdn.net/victoryxa/article/details/105841440?spm=1001.2014.3001.5501



# 第二次部署

主要参考

https://mintimate.cn/2020/03/19/hexo/#%E5%89%8D%E6%8F%90



问题：

部署Hexo到腾讯云静态托管后主页主题不生效

解决：

时间问题，刷新一会就好。







































