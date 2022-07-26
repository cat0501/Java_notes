# 精进Git面试题



## 日常使用





```bash
# 本地库同步Github

# 初始化
git init;

# 添加远程库
git remote add github https://github.com/cat0501/Java_notes_code.git

# 添加到本地
git add .
# 提交到暂存区
git commit -m "github";
# 推送
git push github master
```





```bash
# 列出本地分支
git branch

# 新建一个本地分支
git branch (branchname) 

```





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220327224143107.png)





<hr>

## Git 的一些常用命令？

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220327231015557.png)



- `git init`：创建 Git 库。

- `git status` ：查看当前仓库的状态。

- `git show` ：# 显示某次提交的内容 git show $id

- `git diff` ：查看本次修改与上次修改的内容的区别。

- `git add <file>` ：把现在所要添加的文件放到暂存区中。

  - `git log -p <file>` ：查看每次详细修改内容的 diff 。

  - `git rm <file>` ：从版本库中删除文件。

  - `git reset <file>` ：从暂存区恢复到工作文件。

  - `git reset HEAD^` ：恢复最近一次提交过的状态，即放弃上次提交后的所有本次修改` 。

    > HEAD 本身是一個游标，它通常會指向某一个本地端分支或是其它 commit，所以你也可以把 HEAD 当做是目前所在的分支（current branch）。 可参见 [《Git 中 HEAD 是什么东西》](https://juejin.im/entry/59a38c5d6fb9a0248e5cc884) 。

- `git commit` ：把 Git add 到暂存区的内容提交到代码区中。

- `git clone` ：从远程仓库拷贝代码到本地。

- `git branch`：查看当前的分支名称。

  - `git branch -r` ：查看远程分支。

- `git checkout` ：切换分支。

- `git merge <branch>` ：将 branch 分支合并到当前分支。

- `git stash`：暂存。

  - `git stash pop` ：恢复最近一次的暂存。

- `git pull`：抓取远程仓库所有分支更新并合并到本地。

  - `git push origin master` ：将本地主分支推到远程主分支。





## 平时使用什么 Git 工具？

**1）命令行**

只能说十个里面九个菜，还有一个是大神，虽然命令行提供了全部的功能，但是很多用 GUI 工具可以很便捷解决的问题，命令行做起来都比较麻烦。

当然并不是让大家不要去命令行，通过命令行可以对 git 的功能和原理有一个更深入的了解。

**2）IDEA Git 插件**

IDEA Git 插件越来越强大，很多时候，我们日常使用 Git ，更多使用它。具体的教程，可以看看 [《IntelliJ IDEA 下的使用 git》](https://blog.csdn.net/huangfan322/article/details/53220060) 。

**3）SourceTree**

> 可以说是最好用的 Git 工具，没有之一。

日常使用的一个图形化的 Git 增强工具，而最好用的功能就在于它集成了 GitFlow ，让开发者可以更简单、更规范的去做一些 Git 操作；

另外它还提供了更友好的 merge 界面，但是操作起来不是很顺手，因为它只支持整行删除。

**4）其它**

- [SmartGit](https://www.syntevo.com/smartgit/)
- [Tower](https://www.git-tower.com/mac)
- Atom



## Git 和 SVN 的优缺点？

Git 是分布式版本控制系统，SVN 是集中式版本控制系统。

1）SVN 的优缺点

- 优点
  - 1、管理方便，逻辑明确，符合一般人思维习惯。
  - 2、易于管理，集中式服务器更能保证安全性。
  - 3、代码一致性非常高。
  - 4、适合开发人数不多的项目开发。
- 缺点
  - 1、服务器压力太大，数据库容量暴增。
  - 2、如果不能连接到服务器上，基本上不可以工作，因为 SVN 是集中式服务器，如果服务器不能连接上，就不能提交，还原，对比等等。
  - 3、不适合开源开发（开发人数非常非常多，但是 Google App Engine 就是用 SVN 的）。但是一般集中式管理的有非常明确的权限管理机制（例如分支访问限制），可以实现分层管理，从而很好的解决开发人数众多的问题。



2）Git 优缺点

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

所以，很多公司的开发团队使用 Git 作为版本管理，而产品团队使用 SVN 。



## 说说创建分支的步骤？

- 1、`git branch xxx_dev` ：创建名字为 `xxx_dev` 的分支。
- 2、`git checkout xxx_dev` ：切换到名字为 `xxx_dev` 的分支。
- 3、`git push origin xxx_dev` ：执行推送的操作，完成本地分支向远程分支的同步。

更详细的，可以看看 [《Github 创建新分支》](https://blog.csdn.net/top_code/article/details/51931916) 文章。



🦅 **tag 是什么？**

tag ，指向一次 commit 的 id ，通常用来给分支做一个标记。

> 大多数情况下，我们会将每个 Release 版本打一个分支。例如 SkyWalking 的 Tag 是 https://github.com/apache/incubator-skywalking/tags 。

- 打标签 ：`git tag -a v1.01 -m "Release version 1.01"` 。
- 提交标签到远程仓库 ：`git push origin --tags` 。
- 查看标签 ：`git tag` 。
- 查看某两次 tag 之间的 commit ：`git log --pretty=oneline tagA..tagB` 。
- 查看某次 tag 之后的 commit ：`git log --pretty=oneline tagA..` 。



🦅 **Git 提交代码时候写错 commit 信息后，如何重新设置 commit 信息？**

可以通过 `git commit --amend` 来对本次 commit 进行修改。

🦅 **删除已经合并过的分支会发生什么事？**

分支本身就像是指标或贴纸一样的东西，它指着或贴在某个 commit 上面，分支并不是目录或档桉的复制品（但在有些版控系统的确是）。

在 Git 裡，删除分支就像是你把包装盒上的贴纸撕下来，贴纸撕掉了，盒子并不会就这样跟着消失。所以，当你删除合并过的分支不会发生什么事，也不会造成档桉或目录跟着被删除的状况。





## Git 服务器

Git 服务器的选择，实际上是比较多的。

- 公有服务方案
  - Github
  - Gitee
- 私有化部署方案
  - GitLab
  - Gogs
  - Bitbucket

> 注意，Gitlab 和 Bitbucket 也提供公有服务的方案。



一般情况下，大多数公司使用 GitLab 作为 Git 服务器。

> GitLab是一个利用 [Ruby on Rails](http://www.oschina.net/p/ruby+on+rails) 开发的开源应用程序，实现一个自托管的[Git](http://www.oschina.net/p/git)项目仓库，可通过Web界面进行访问公开的或者私人项目。
>
> 它拥有与[Github](http://www.oschina.net/p/github)类似的功能，能够浏览源代码，管理缺陷和注释。可以管理团队对仓库的访问，它非常易于浏览提交过的版本并提供一个文件历史库。它还提供一个代码片段收集功能可以轻松实现代码复用，便于日后有需要的时候进行查找。



- 不过因为 GitLb 使用 Ruby on Rails 实现，所以占用的系统资源会比较多。









<hr>

## 最后

参考与推荐如下文章：

- [《Git 面試題》](https://gitbook.tw/interview)

  > 读者写的非常棒，即使不准备面试，也可以看看，作为平时使用 Git 一些场景下的解决方案。

- [《面试当中的 Git 问题》](https://blog.csdn.net/qq_26768741/article/details/66975516)

- [《Git 的常见问题以及面试题汇总》](https://www.breakyizhan.com/git/5035.html)

- [《泪流满面的 11 个 Git 面试题》](http://blog.jobbole.com/114297/)

- [《面试中的那些 Git 问题 - 基础部分》](http://www.cocoachina.com/ios/20171023/20873.html)





<hr>

## 一个本地库能不能既关联 GitHub，又关联 Gitee 呢？

> 答案是肯定的，因为 git 本身是分布式版本控制系统，可以同步到另外一个远程库，当然也可以同步到另外两个远程库。
> 使用多个远程库时，我们要注意，git 给远程库起的默认名称是 `origin`，如果有多个远程库，我们需要用不同的名称来标识不同的远程库。

## 配置秘钥

由于你的本地 Git 仓库和 GitHub 仓库之间的传输是通过`SSH`加密的，所以我们需要配置验证信息：

使用以下命令生成 SSH Key：

```
$ ssh-keygen -t rsa -C "youremail@example.com"

ssh-keygen -t rsa -C "17782975312@163.com"
ssh-keygen -t rsa -C "pxzjl123@icloud.com"
```



后面的 your_email@youremail.com 改为你在 Github 或Gitee上注册的邮箱，之后会要求确认路径和输入密码，我们这使用默认的一路回车就行。
成功的话会在 ~/ 下生成 .ssh 文件夹，进去，打开 id_rsa.pub，复制里面的 key。

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230214938954.png" alt="image-20211230214938954" style="zoom:33%;" />

> 注：根据runoob官网，只用在本地~/下.ssh 文件夹中生成一个秘钥就好，但是我根据[思否博主建议](https://segmentfault.com/a/1190000016269686)对每个账户（GitHub、Gitee）生成了一个秘钥然后改名并分别配置到了GitHub和Gitee中，秘钥文件如上图。
>
> id_rsa是私钥 id_rsa.pub是公钥





## 关联GitHub和Gitee

先关联 GitHub 的远程库：

```bash
git remote add github git@github.com:tianqixin/runoob-git-test.git
```

注意，远程库的名称叫 github，不叫 origin 了。

接着，再关联 Gitee 的远程库：

```bash
git remote add gitee git@gitee.com:imnoob/runoob-test.git
```

同样注意，远程库的名称叫 gitee，不叫 origin。

现在，我们用 git remote -v 查看远程库信息，可以看到两个远程库：

```bash
git remote -v
gitee    git@gitee.com:imnoob/runoob-test.git (fetch)
gitee    git@gitee.com:imnoob/runoob-test.git (push)
github    git@github.com:tianqixin/runoob.git (fetch)
github    git@github.com:tianqixin/runoob.git (push)
```

## 推送

先添加和提交：

```bash
git add .
git commit -m "xxx"
```



如果要推送到 GitHub，使用命令：

```bash
git push github master
```

如果要推送到 Gitee，使用命令：

```bash
git push gitee master
```

这样一来，我们的本地库就可以同时与多个远程库互相同步：

![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/gitee8.png)



## 最终效果

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222742330.png" alt="image-20211230222742330" style="zoom:33%;" />



![image-20211230222850270](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222850270.png)



![image-20211230222954724](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222954724.png)



## 其它问题



### Github 如何设置 master 为默认分支？

需要在当前项目的setting里面修改默认分支，账户里面修改无效。



### 测试一下本地私钥和网站公钥配置是否成功？

在config文件中，给GitHub网站配置的别名就是github，所以直接使用别名，就是

```bash
ssh -T git@github
```



遇到问题：

```bash
The authenticity of host 'github.com (20.205.243.166)' can't be established.
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230195845819.png" alt="image-20211230195845819" style="zoom:33%;" />

解决：

Google之后明白，少了一个known_hosts文件，本来密钥文件应该是三个，现在是两个，便报了这样的错误，此时选择yes回车之后，便可同时生成了缺少了的known_hosts文件。

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230200047967.png" alt="image-20211230200047967" style="zoom:33%;" />



### push：'Github' does not appear to be a git repository

遇到问题

```bash
fatal: 'Github' does not appear to be a git repository
```

解决

```bash
https://blog.csdn.net/chaorwin/article/details/51921294
```



### 万分感谢！！！第一次往github上的Java_notes仓库push大量文件！

https://www.runoob.com/git/git-remote-repo.html

```bash
git add .
git commit -m "添加 README.md 文件"
git remote add origin git@github.com:cat0501/Java_notes.git
git push -u origin master
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230210913202.png" alt="image-20211230210913202" style="zoom: 33%;" />



### git中提示 please tell me who you are

需要你登录一下，确认你的身份。

先输入命令`git config user.name "username"`，换行输入`git config user.email "email"`

输入正确的之后就能使用 git add 、commit等命令进行版本控制了。



---



先查看现有远程库的情况：

```bash
git remote --verbose
```



```bash
(base) cat@zhangjilindembp Java_notes % git remote --verbose

origin	https://gitee.com/code0002/Java_notes.git (fetch)
origin	https://gitee.com/code0002/Java_notes.git (push)
(base) cat@zhangjilindembp Java_notes % 
```

可以看到，目前仅有`https://gitee.com/code0002/Java_notes.git`这个远程库地址



git remote add GitHub git@github.com:xcalan/learn_git.git
git remote add Gitee git@gitee.com:xcalan/learn_git.git



分支：

```bash
# 查看本地分支
(base) cat@zhangjilindembp Java_notes % git branch
* master
# 查看远程分支
(base) cat@zhangjilindembp Java_notes % git branch -r
 Gitee/master
```



```bash
git push <远程主机名> <本地分支名>:<远程分支名>
git push Github master:main
```



```
git remote add gitee git@gitee.com:code0002/Java_notes.git
```



秘钥

https://www.jianshu.com/p/be58fa27a704

Git配置多个用户

https://blog.csdn.net/Jumping_code/article/details/108775927

https://blog.csdn.net/weixin_33725515/article/details/86250140?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link&utm_relevant_index=9

https://blog.csdn.net/cicibi6696/article/details/100686299

https://blog.csdn.net/weixin_47518343/article/details/109047689?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-2.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-2.no_search_link&utm_relevant_index=5

https://blog.csdn.net/weixin_47518343/article/details/109047689?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-5.no_search_link&spm=1001.2101.3001.4242.4&utm_relevant_index=8

https://blog.csdn.net/zixiao217/article/details/82939654?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.no_search_link&spm=1001.2101.3001.4242.1&utm_relevant_index=3



https://blog.csdn.net/xiecheng1995/article/details/106570059?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-1.no_search_link&spm=1001.2101.3001.4242.2&utm_relevant_index=4

https://blog.csdn.net/COCO56/article/details/103818543?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-5.no_search_link&spm=1001.2101.3001.4242.4&utm_relevant_index=8



# git命令



分支

https://blog.csdn.net/guo_qiangqiang/article/details/88020656

# 终端配置
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
