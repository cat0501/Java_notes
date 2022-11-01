

# 命令

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220327224143107.png)



## 初始化

- 现有目录初始化（将创建一个名为 `.git`  的子目录）

```sh
git init
```

- 添加远程库（本地库关联远程库）

```sh
git remote add gitee https://gitee.com/Lemonade19/Java_notes.git
git remote add github https://github.com/cat0501/Java_notes_code.git

# 远程仓库的重命名、移除
git remote rename test test1
git remote rm test1
```

- 提交信息

```sh
# 查看提交历史
git log
## 只看某个人的提交记录
git log --author=bob

# 用户名和邮箱配置（项目级别）
git config user.name Lemonade19
git config user.email 17782975312@163.com
```



- 从服务器克隆已有Git 仓库：`git clone [url]` 



## 提交更新

- 提交（本地库同步到 Github、Gitee等）

```sh
# 检测当前文件状态
git status

# 添加到本地（暂存区）
git add .

# 提交更新到 版本库
git commit -m "clean";

# 推送改动到 远程仓库
git push github master
git push origin master
```

- 修改注释信息

```sh
# 修改最后一次 commit 的注释信息
git commit --amend 
# 修改某几次 commit 的信息
git rebase -i HEAD~2
// 或者
git rebase -i {commitID} // 例如 git rebase -i d95ddfb
```

- 配置大小写敏感、乱码问题

```sh
# git配置大小写敏感
$ git config core.ignorecase
true
# 下面设置大小写敏感为敏感
$ git config core.ignorecase false

# cmd git页面中文显示为数字,不是乱码
$ git config --global core.quotepath false
```



## 分支

- 查看

```sh
# 列出本地分支
$ git branch

# 列出远程分支
$ git branch -r
```

- 创建、切换

```bash
# 新建一个本地分支 test
$ git branch test
# 切换当前分支到 test
$ git checkout test

# 创建分支并切换过去(上面两条命令的合写)
$ git checkout -b feature_x

# 切换到主分支
git checkout master
```

- 合并

```sh
# 合并分支(可能会有冲突)
git merge test
```



## 其它

1. 查看提交历史

   ```sh
   git log
   ```

2. 撤销指定的提交

   ```sh
   git revert <commit>
   ```



# Tools

1. 命令行

   cmd、IDEA 的终端

2. IDEA Git 插件

IDEA Git 插件越来越强大，很多时候，我们日常使用 Git ，更多使用它。具体的教程，可以看看 [《IntelliJ IDEA 下的使用 git》](https://blog.csdn.net/huangfan322/article/details/53220060) 。

3. SourceTree

   一个图形化的 Git 增强工具，其最好用的功能就在于它集成了 GitFlow ，让开发者可以更简单、更规范的去做一些 Git 操作；

   另外它还提供了更友好的 merge 界面，但是操作起来不是很顺手，因为它只支持整行删除。

4. 其它

- [SmartGit](https://www.syntevo.com/smartgit/)
- [Tower](https://www.git-tower.com/mac)
- Atom



# Introduction

## Git 相比 SVN 的优缺点？

**Git 是分布式版本控制系统，SVN 是集中式版本控制系统。**



1）🦅SVN 的优缺点

- 优点
  - 1、管理方便，逻辑明确，符合一般人思维习惯。
  - 2、易于管理，集中式服务器更能保证安全性。
  - 3、代码一致性非常高。
  - 4、适合开发人数不多的项目开发。
- 缺点
  - 1、服务器压力太大，数据库容量暴增。
  - 2、如果不能连接到服务器上，基本上不可以工作，因为 SVN 是集中式服务器，如果服务器不能连接上，就不能提交，还原，对比等等。
  - 3、不适合开源开发（开发人数非常非常多，但是 Google App Engine 就是用 SVN 的）。但是一般集中式管理的有非常明确的权限管理机制（例如分支访问限制），可以实现分层管理，从而很好的解决开发人数众多的问题。



2）🦅Git 优缺点

- 优点
  - 1、适合分布式开发，强调个体。
  - 2、公共服务器压力和数据量都不会太大。
  - 3、速度快、灵活。
  - 4、任意两个开发者之间可以很容易的解决冲突。
  - 5、离线工作。
- 缺点
  - 1、学习周期相对而言比较长。
  - 2、不符合常规思维。
  - 3、代码保密性差，一旦开发者把整个库克隆下来就可以完全公开所有代码和版本信息。

所以，很多公司的开发团队使用 Git 作为版本管理工具，而产品团队使用 SVN 。



## Git 服务器

- 公有服务方案
  - Github
  - Gitee
- 私有化部署方案
  - GitLab
  - Gogs
  - Bitbucket

> 注意，Gitlab 和 Bitbucket 也提供公有服务的方案。

- 一般情况下，大多数公司使用 GitLab 作为 Git 服务器。
  - GitLab是一个利用 [Ruby on Rails](http://www.oschina.net/p/ruby+on+rails) 开发的开源应用程序，实现一个自托管的 [Git](http://www.oschina.net/p/git) 项目仓库，可通过Web界面进行访问公开的或者私人的项目。
  - 因为 GitLb 使用 Ruby on Rails 实现，所以占用的系统资源会比较多。
  - 它拥有与 [Github](http://www.oschina.net/p/github) 类似的功能，能够浏览源代码，管理缺陷和注释。可以管理团队对仓库的访问，它非常易于浏览提交过的版本并提供一个文件历史库。它还提供一个代码片段收集功能可以轻松实现代码复用，便于日后有需要的时候进行查找。



## 安装环境

- 官网下载：https://git-scm.com/download
- 直接 Next 安装即可
- 参考：https://blog.csdn.net/mukes/article/details/115693833



# Reference

- [《Git 面試題》](https://gitbook.tw/interview)

  > 读者写的非常棒，即使不准备面试，也可以看看，作为平时使用 Git 一些场景下的解决方案。

- [《面试当中的 Git 问题》](https://blog.csdn.net/qq_26768741/article/details/66975516)

- [《Git 的常见问题以及面试题汇总》](https://www.breakyizhan.com/git/5035.html)

- [《泪流满面的 11 个 Git 面试题》](http://blog.jobbole.com/114297/)

- [《面试中的那些 Git 问题 - 基础部分》](http://www.cocoachina.com/ios/20171023/20873.html)

- 关于分支：https://blog.csdn.net/guo_qiangqiang/article/details/88020656

- git配置多个账号：https://www.cnblogs.com/50how/p/15735682.html

- 如何将本地仓库同时同步至多个Git远程仓库
  - https://blog.csdn.net/COCO56/article/details/103818543
  - https://blog.csdn.net/zixiao217/article/details/82939654
  - https://blog.csdn.net/xiecheng1995/article/details/106570059





# 	Q&A

## 一个本地库能不能既关联 GitHub，又关联 Gitee 呢？

> 答案是肯定的，因为 git 本身是分布式版本控制系统，可以同步到另外一个远程库，当然也可以同步到另外两个远程库。
>
> 使用多个远程库时，我们要注意，git 给远程库起的默认名称是 `origin`，如果有多个远程库，我们需要用不同的名称来标识不同的远程库。

1. 取消全局设置

```bash
# 查看
git config user.name
git config user.email

# 取消
git config --global --unset user.name
git config --global --unset user.email
```



2. 配置秘钥

由于你的本地 Git 仓库和 GitHub 仓库之间的传输是通过 `SSH` 加密的，所以我们需要配置验证信息。

- 使用以下命令生成 SSH Key：

```sh
$ ssh-keygen -t rsa -C "youremail@example.com"
```



```bash
# 后面的 your_email@youremail.com 改为你在 Github 或Gitee上注册的邮箱,
# 之后会要求确认路径和输入密码，我们这使用默认的一路回车就行。

ssh-keygen -t rsa -C "17782975312@163.com"
ssh-keygen -t rsa -C "pxzjl123@icloud.com"
```

- 成功的话会在 ~/ 下生成 `.ssh` 文件夹，进去打开 `id_rsa.pub`，复制里面的 `key`。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20211230214938954.png)



> 注：根据runoob官网，只用在本地~/下.ssh 文件夹中生成一个秘钥就好，但是我根据[思否博主建议](https://segmentfault.com/a/1190000016269686)对每个账户（GitHub、Gitee）生成了一个秘钥然后改名并分别配置到了GitHub和Gitee中，秘钥文件如上图。
>
> 其中，id_rsa 是私钥 ， id_rsa.pub 是公钥。



3. 关联 GitHub 和 Gitee

- 关联 GitHub 远程库

```bash
$ git remote add github git@github.com:tianqixin/runoob-git-test.git
```

> 注意，远程库的名称叫 github，不叫 origin 了。

- 关联 Gitee 远程库

```bash
$ git remote add gitee git@gitee.com:imnoob/runoob-test.git
```

- 查看远程库信息

```bash
$ git remote -v

gitee    git@gitee.com:imnoob/runoob-test.git (fetch)
gitee    git@gitee.com:imnoob/runoob-test.git (push)
github    git@github.com:tianqixin/runoob.git (fetch)
github    git@github.com:tianqixin/runoob.git (push)
```

4. 推送改动

```bash
# 添加和提交
git add .
git commit -m "xxx"

# 推送到 GitHub
git push github master
# 推送到 Gitee
git push gitee master
```



这样一来，我们的本地库就可以同时与多个远程库互相同步：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/gitee8.png)

如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222742330.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222850270.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222954724.png)



## Github 如何设置 master 为默认分支？

- 需要在当前项目的 `setting` 里面修改默认分支，账户里面修改无效。

## git push -u 的含义和用法

- 第一次加了参数 `-u` 后，以后即可直接用 `git push` 代替 `git push origin master`

- https://blog.csdn.net/chenzz444/article/details/104408607



## 测试一下本地私钥和网站公钥配置是否成功？

在config文件中，给GitHub网站配置的别名就是github，所以直接使用别名，就是

```bash
ssh -T git@github
```



- 遇到问题：

```bash
The authenticity of host 'github.com (20.205.243.166)' can't be established.
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230195845819.png" alt="image-20211230195845819" style="zoom:33%;" />

- 解决：Google之后明白，少了一个 known_hosts 文件，本来密钥文件应该是三个，现在是两个，便报了这样的错误，此时选择yes回车之后，便可同时生成了缺少了的 known_hosts文件。

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230200047967.png" alt="image-20211230200047967" style="zoom:33%;" />



秘钥

https://www.jianshu.com/p/be58fa27a704

Git配置多个用户

https://blog.csdn.net/Jumping_code/article/details/108775927



## 终端配置

更新完 zsh 说我目录权限问题的解决   https://www.jianshu.com/p/f2545c1feaec

报错内容如下：
```bash
[oh-my-zsh] For safety, we will not load completions from these directories until
[oh-my-zsh] you fix their permissions and ownership and restart zsh.
[oh-my-zsh] See the above list for directories with group or other writability.

[oh-my-zsh] To fix your permissions you can do so by disabling
[oh-my-zsh] the write permission of "group" and "others" and making sure that the
[oh-my-zsh] owner of these directories is either root or your current user.
[oh-my-zsh] The following command may help:
[oh-my-zsh] compaudit | xargs chmod g-w,o-w

[oh-my-zsh] If the above didn't help or you want to skip the verification of
[oh-my-zsh] insecure directories you can set the variable ZSH_DISABLE_COMPFIX to
[oh-my-zsh] "true" before oh-my-zsh is sourced in your zshrc file.
```
解决方案
```bash
chmod 755 /usr/local/share/zsh
chmod 755 /usr/local/share/zsh/site-functions
```
