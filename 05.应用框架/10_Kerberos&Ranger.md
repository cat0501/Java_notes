Kerberos 和 keytab 

`Keytab` 和 `Kerberos` 是与身份验证和安全领域相关的概念。

1. Keytab（密钥表）：Keytab是用于存储密钥和凭证的文件格式。在Kerberos身份验证中，Keytab文件包含了用于密钥交换和生成票据（Ticket）的密钥。Keytab文件通常与Kerberos协议一起使用，以便在客户端和服务端之间进行安全的身份验证和通信。Keytab文件通常由网络管理员在Kerberos环境中配置和分发给客户端和服务端。

2. Kerberos：Kerberos是一个网络身份验证协议，用于安全地验证用户、服务和主机实体的身份。它提供了一种强大的身份验证机制，可以在不安全的网络上进行安全通信。Kerberos的工作原理是客户端和服务端通过票据（Tickets）进行互相认证，票据是使用Keytab文件中的密钥生成的，以确保通信的机密性和完整性。Kerberos还提供了单点登录（Single Sign-On）功能，使用户只需一次验证即可访问多个服务。

综上所述，Keytab是一种文件格式，用于存储密钥和凭证，而Kerberos是一种身份验证协议，用于安全地验证网络实体的身份。Keytab文件通常与Kerberos一起使用，以实现安全的身份验证和通信。



命令

`kinit` 命令用于在 Kerberos 环境中获取用户的票据（Ticket）。有两种方式可以使用 `kinit` 命令进行身份认证：

1. `kinit -kt /home/liyakun/liyakun.keytab liyakun`：该命令使用指定的Keytab文件`/home/liyakun/liyakun.keytab`和主体名称`liyakun`进行身份认证。Keytab文件包含了用于生成票据的密钥。通过这个命令，`kinit`将使用Keytab文件中的密钥来获取`liyakun`用户的票据。这是在非交互模式下使用Keytab的常见方式。

2. `kinit liyakun`：该命令仅提供了主体名称`liyakun`，它将提示用户输入密码以完成身份验证。这是在交互模式下使用用户名和密码进行身份验证的方式。

无论是使用Keytab文件还是用户名和密码，`kinit`命令都是用于在Kerberos环境中获取用户票据的常见方式。具体使用哪种方式，取决于系统管理员的配置和用户的身份验证需求。









连接串

```sh
hive连接配置
jdbc:hive2://cnbjsjztpnn01:10000/default;principal=hive/_HOST@SJZT.COM

impala连接配置
jdbc:impala://cnbjsjztpdn03:21050/default;AuthMech=1;KrbRealm=SJZT.COM;KrbHostFQDN=_HOST;KrbServiceName=impala;KrbAuthType=2

beeline jdbc访问 impala
beeline -d "com.cloudera.impala.jdbc41.Driver" -u "jdbc:impala://xcdp1:21050/default;AuthMech=1;KrbRealm=SJZT.COM;KrbHostFQDN=_HOST;KrbServiceName=impala;KrbAuthType=2"

访问带认证的WebUI
https://www.jianshu.com/p/ce44967613a7
```

KrbHostFQDN需要指定连接哪台服务器的impalad，需要使用服务器的别名。



```sh
我们的代码在kerberos模式下，连接hive/impala数据库时，需要正确的组装连接串，才能确保jdbc正确的建立。我们组装的连接串可能有下列几种形式。
1) jdbc:hive2://10.11.14.30:10000/bitnei_ods;principal=hive/10.11.14.30@SJZT.COM
2) jdbc:hive2://10.11.14.30:10000/bitnei_ods;principal=hive/_HOST@SJZT.COM
3) jdbc:hive2://10.11.14.30:10000/bitnei_ods;principal=hive/cnbjsjztpdn01@SJZT.COM
4) jdbc:hive2://cnbjsjztpdn01:10000/bitnei_ods;principal=hive/_HOST@SJZT.COM

目前我这梳理源码，梳理出了3个转换（可能存在遗漏，大家发现了可以给补充一下）
1、构建HiveConnection对象时，该类的构造函数会调用getCanonicalHostName方法，该方法会把ip地址转换为域名，所以如果jdbc的url中的ip地址可以转换为多个域名，要小心，它转换为读到的第一个域名 （即使url中使用的不是ip地址，是机器的名称，也会做该转换）。

2、HadoopThriftAuthBridge 的createClientTransport方法调用SecurityUtil.getServerPrincipal方法尝试做_HOST转换，如果url中用了_HOST，是在这里发生替换的。没有_HOST,这里不做什么动作。

3、GssKrb5Client 在构造过程中，会在一次尝试把principal部分的名称做转换，在这里如果nameservice可以找到一个更完整域名，则做一次替换，比如我们指定了hive/cnbjsjztpnn01@SJZT.COM，但是servicename能找到一个 cnbjsjztpnn01.sjzt.com， 这里会发生一次替换（servicename目前看优先找到的域名是host中的第一个域名，咱们目前方案是hosts，如果后续使用内网dns处理，需要在研究一下）。

首先1）任何场景都是错的因为 hive/10.11.14.30@SJZT.COM 我们的kerberos库中不会存在这样的用户，java运行程序目前看没有哪个地方给这个东西做转换。
对于2）当我们的hosts文件可以解析出正确的域名时，这个链接是正确的
对于3）其实是 2）的具体版，但是不如2）更能适应场景，所以大多情况正确，除非 nameservice在机器上找到一个kerberos库中不存在用户的域名，且该域名为cnbjsjztpdn01.xxx
对于4) 其实是2）的加强版， 所以大多情况正确，也存在3）中存在的风险
```













# 参考

- [window 环境下jdbc访问启用kerberos的impala](https://www.cnblogs.com/lovegmail/p/6427133.html)













