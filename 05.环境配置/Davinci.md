# 

# 1 概述

- 数据可视化工具，文档较齐全！

- 围绕 View（数据视图）与 Widget（可视化组件）两个核心概念设计
  - View 是数据的结构化形态
  - Widget 是数据的可视化形态

- 数据源
  - 支持多种 JDBC 数据源
  - 支持 CSV 数据文件上传



![overview_architecture](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/overview_architecture.jpg)



# 2 安装

- （1）下载并解压：将下载好的 Davinci 包（Release 包，不是 Source 包）解压到某个系统目录（地址：https://github.com/edp963/davinci/releases）
- （2）配置环境变量：解压后的目录配置到环境变量 DAVINCI3_HOME

```bash
export DAVINCI3_HOME=/data/services/davinci-service/davinci-assembly_0.3.1-0.3.1-SNAPSHOT-dist-rc
```

- （3）初始化数据库：修改 bin 目录下 initdb.sh 中的数据库信息为要初始化的数据库，如 davinci0.3

```bash
mysql -P 3306 -h localhost -u root -p123456 davinci0.3 < $DAVINCI3_HOME/bin/davinci.sql

sh bin/initdb.sh
```

- （4）初始化设置：`config`目录，将`application.yml.example`重命名为`application.yml`，开始配置
  - server 配置
  - datasource 配置
  - mail 配置：Davinci 使用注册–邮件激活添加用户，所以 mail 配置是必不可少的
  - 截图配置：用于定时任务功能发送邮件正文中对可视化应用进行截图
  - 使用统计配置：开启后将记录可视化应用的使用数据和登录终端信息
  - 日志配置：日志配置文件为 config/logback.xml，如当前的日志配置不能满足你的要求，可自定义配置日志模式
  - ......

```bash
cd config
mv application.yml.example application.yml
```



```yaml
[root@101 config]# cat -b application.yml 

    16  server:
    17    protocol: http
    18    address: 0.0.0.0
    19    port: 8002

    20    servlet:
    21      context-path: /
    


    22    # Used for mail and download services, can be empty, careful configuration
    23    # By default, 'server.address' and 'server.port' is used as the string value.
    24    access:
    25    #  address: 0.0.0.0
    26    #  port:


    27  ## jwt is one of the important configuration of the application
    28  ## jwt config cannot be null or empty
    29  jwtToken:
    30    secret: secret
    31    timeout: 1800000
    32    algorithm: HS512


    33  ## your datasource config
    34  source:
    35    initial-size: 1
    36    min-idle: 1
    37    max-wait: 30000
    38    max-active: 10
    39    break-after-acquire-failure: true
    40    connection-error-retry-attempts: 1
    41    time-between-eviction-runs-millis: 2000
    42    min-evictable-idle-time-millis: 600000
    43    max-evictable-idle-time-millis: 900000
    44    test-while-idle: true
    45    test-on-borrow: false
    46    test-on-return: false
    47    validation-query: select 1
    48    validation-query-timeout: 10
    49    keep-alive: false
    50    filters: stat

    51    enable-query-log: false
    52    result-limit: 1000000


    53  spring:
    54    mvc:
    55      async:
    56        request-timeout: 30s
    57    rest:
    58      proxy-host:
    59      proxy-port:
    60      proxy-ignore:


    61    ## davinci datasource config
    62    datasource:
    63      type: com.alibaba.druid.pool.DruidDataSource
    64      url: jdbc:mysql://localhost:3306/davinci0.3?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
    65      username: root
    66      password: 123456
    67      driver-class-name: com.mysql.jdbc.Driver
    68      initial-size: 2
    69      min-idle: 1
    70      max-wait: 60000
    71      max-active: 10
    72      break-after-acquire-failure: true
    73      connection-error-retry-attempts: 1
    74      time-between-eviction-runs-millis: 2000
    75      min-evictable-idle-time-millis: 600000
    76      max-evictable-idle-time-millis: 900000
    77      test-while-idle: true
    78      test-on-borrow: false
    79      test-on-return: false
    80      validation-query: select 1
    81      validation-query-timeout: 10
    82      keep-alive: false
    83      filters: stat

    84    ## redis config
    85    ## please choose either of the two ways
    86    redis:
    87      isEnable: false

    88    ## standalone config
    89      host: 0.0.0.0
    90      port: 6379

    91    ## cluster config
    92    #  cluster:
    93    #       nodes:

    94      password:
    95      database: 0
    96      timeout: 1000
    97      jedis:
    98        pool:
    99          max-active: 8
   100          max-wait: 1
   101          max-idle: 8
   102          min-idle: 0

   103    ## mail is one of the important configuration of the application
   104    ## mail config cannot be null or empty
   105    ## some mailboxes need to be set separately password for the SMTP service)
   106    mail:
   107      host: smtp.163.com
   108      port: 25
   109      username: 17782975312@163.com
   110      fromAddress:
   111      password: BWKEUOKCHCOCQAJI
   112      nickname: Davinci

   113      properties:
   114        smtp:
   115          starttls:
   116            enable: true
   117            required: true
   118          auth: true
   119        mail:
   120          smtp:
   121            ssl:
   122              enable: false

   123    ldap:
   124      urls:
   125      username:
   126      password:
   127      base:
   128      domainName:    # domainName 指 企业邮箱后缀，如企业邮箱为：xxx@example.com，这里值为 '@example.com'

   129    security:
   130      oauth2:
   131        enable: false
   132  #      client:
   133  #        registration:
   134  #          cas:
   135  #            provider: cas
   136  #            client-id: "xxxxx"
   137  #            client-name: "Sign in with XXX"
   138  #            client-secret: "xxxxx"
   139  #            authorization-grant-type: authorization_code
   140  #            client-authentication-method: post
   141  #            redirect-uri-template: "{baseUrl}/login/oauth2/code/{registrationId}"
   142  #            scope: userinfo
   143  #        provider:
   144  #          cas:
   145  #            authorization-uri: https://cas.xxxxx.cn/cas/oauth2.0/authorize
   146  #            token-uri: https://cas.xxxxx.cn/cas/oauth2.0/accessToken
   147  #            user-info-uri: https://cas.xxxxx.cn/cas/oauth2.0/profile
   148  #            user-name-attribute: id
   149  #            userMapping:
   150  #              email: "attributes.Email"
   151  #              name: "attributes.CnName1"
   152  #              avatar: "attributes.Avatar"

   153  screenshot:
   154    default_browser: CHROME
   155    timeout_second: 600
   156    chromedriver_path: /data/services/davinci-service/chromedriver
   157    remote_webdriver_url: 

   158  data-auth-center:
   159    channels:
   160      - name:
   161        base-url:
   162        auth-code:

   163  statistic:
   164    enable: true

   165    # You can use external elasticsearch storage [127.0.0.1:9300]
   166    elastic_urls:
   167    elastic_user:
   168    elastic_index_prefix:

   169    # You can also use external mysql storage
   170    mysql_url:
   171    mysql_username:
   172    mysql_password:

   173    # You can also use external kafka
   174    kafka.bootstrap.servers:
   175    kafka.topic:
   176    java.security.krb5.conf:
   177    java.security.keytab:
   178    java.security.principal:

   179  encryption:
   180    maxEncryptSize: 1024
   181    type: Off # Off is to turn off encryption, to enable encryption, please select AES or RSA
```



- （5）数据源配置：Davinci 理论上支持所有有 JDBC 的数据源，我们默认支持的数据源有
  - Davinci 内部只提供了 MySql 的驱动包，也就是说，如果你要使用其他数据源，还需将对应驱动jar 包手动拷贝到`lib`目录并重新启动 Davinci 服务
  - ...

| 数据源名称    | 驱动类                                                |
| :------------ | :---------------------------------------------------- |
| mysql         | com.mysql.jdbc.Driver                                 |
| oracle        | oracle.jdbc.driver.OracleDriver                       |
| sqlserver     | com.microsoft.sqlserver.jdbc.SQLServerDriver          |
| h2            | org.h2.Driver                                         |
| phoenix       | org.apache.phoenix.jdbc.PhoenixDriver                 |
| mongodb       | mongodb.jdbc.MongoDriver                              |
| elasticSearch | –                                                     |
| presto        | com.facebook.presto.jdbc.PrestoDriver                 |
| moonbox       | moonbox.jdbc.MbDriver                                 |
| cassandra     | com.github.adejanovski.cassandra.jdbc.CassandraDriver |
| clickhouse    | ru.yandex.clickhouse.ClickHouseDriver                 |
| kylin         | org.apache.kylin.jdbc.Driver                          |
| vertica       | com.vertica.jdbc.Driver                               |
| hana          | com.sap.db.jdbc.Driver                                |
| impala        | com.cloudera.impala.jdbc41.Driver                     |





# 3 使用

- 启动：./bin/start-server.sh

- 注册与登录、激活

![注册与登录1](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/1.1.png)

- 创建项目
- 1-创建数据源

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815095435066.png)

- 2-创建数据视图：写sql
  - 编辑数据模型：维度与指标

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815095700085.png)



![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815095840858.png)



- 3-创建可视化组件
- 4-创建可视化应用 Dashboard

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815102934665.png)



## 用户与权限

- 通过项目（资源）/ 用户 / 角色构成基本的 [RBAC](https://en.wikipedia.org/wiki/Role-based_access_control) 模型来进行权限管理



![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815102414613.png)



![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815102457992.png)





![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220815102527117.png)



# 参考

- Github：https://github.com/edp963/davinci/releases

- 官方用户文档：https://edp963.github.io/davinci/docs/zh/1.1-deployment







