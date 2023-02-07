# 项目部署方法

## 前后端代码部署方法

首先将仓库下载

```bash
git clone https://gitee.com/confucianzuoyuan/yuntai.git
```

然后启动后端项目，使用`Intellij Idea`将`yuntai-backend`作为`Maven`项目打开，也就是打开`pom.xml`文件。

然后运行`YuntaiApplication.java`中的`main`函数。

然后启动前端项目，记得要安装`Node.js`最新版。如果安装的是`Node.js` 18以上，那么需要在终端输入以下命令：

Windows:

```bash
set NODE_OPTIONS=--openssl-legacy-provider
```

Linux & Mac OS

```bash
export NODE_OPTIONS=--openssl-legacy-provider
```

然后执行以下命令：

```bash
cd yuntai-frontend
npm install
npm run dev
```

这样就启动好前后端项目了。可以在`localhost:9528`访问我们的中台项目。

## 数据库部署方法

我们这个中台项目依赖以下数据库：

- MySQL
- ClickHouse
- Neo4j

### 用户权限模块数据库部署

在`MySQL`中创建数据库`yuntai_admin`

```sql
create database yuntai_admin;

use yuntai_admin;
```

创建`用户表`，然后插入几条测试数据：

```sql
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uname` (`username`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `user` VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112');
INSERT INTO `user` VALUES (2, 'zhangsan', '96e79218965eb72c92a549dd5a330112');
INSERT INTO `user` VALUES (3, 'lisi', '96e79218965eb72c92a549dd5a330112');
```

创建`角色表`，然后插入几条测试数据：

```sql
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='角色';

INSERT INTO `role` VALUES (1, '系统管理员');
INSERT INTO `role` VALUES (2, '普通用户');
```

创建`权限表`，然后插入几条测试数据：

```sql
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属上级',
  `permission_name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '名称code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='权限';

INSERT INTO `permission` VALUES (1, 0, '全部权限', 'ALL');

INSERT INTO `permission` VALUES (2, 1, '权限管理', 'ADMIN');
INSERT INTO `permission` VALUES (3, 1, '数据统计', 'STATISTICS');
INSERT INTO `permission` VALUES (4, 1, '报表管理', 'REPORT');
INSERT INTO `permission` VALUES (5, 1, '任务调度', 'SCHEDULER');
INSERT INTO `permission` VALUES (6, 1, '数据治理', 'GOVERNMENT');

INSERT INTO `permission` VALUES (7, 2, '用户管理', 'ADMIN-USER');
INSERT INTO `permission` VALUES (8, 2, '角色管理', 'ADMIN-ROLE');

INSERT INTO `permission` VALUES (9, 3, '实时统计', 'STATISTICS-REALTIME');
INSERT INTO `permission` VALUES (10, 3, '访问流量统计', 'STATISTICS-VISIT');

INSERT INTO `permission` VALUES (12, 4, 'MySQL报表管理', 'REPORT-MYSQL');
INSERT INTO `permission` VALUES (13, 4, 'ClickHouse报表管理', 'REPORT-CLICKHOUSE');

INSERT INTO `permission` VALUES (14, 5, '调度详情', 'SCHEDULER-DETAIL');

INSERT INTO `permission` VALUES (15, 6, 'Hive表元数据质量', 'GOVERNMENT-HIVE-META');
INSERT INTO `permission` VALUES (16, 6, 'Hive表血缘关系', 'GOVERNMENT-HIVE-LINEAGE');
INSERT INTO `permission` VALUES (17, 6, 'MySQL表数据质量监控', 'GOVERNMENT-MYSQL');
```

创建`用户-角色表`，然后插入一些测试数据：

```sql
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='用户角色关系表';

INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 2);
```

创建`角色-权限表`，然后插入一些测试数据：

```sql
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='角色权限关系表';

INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);
INSERT INTO `role_permission` VALUES (6, 1, 6);
INSERT INTO `role_permission` VALUES (7, 1, 7);
INSERT INTO `role_permission` VALUES (8, 1, 8);
INSERT INTO `role_permission` VALUES (9, 1, 9);
INSERT INTO `role_permission` VALUES (10, 1, 10);
INSERT INTO `role_permission` VALUES (12, 1, 12);
INSERT INTO `role_permission` VALUES (13, 1, 13);
INSERT INTO `role_permission` VALUES (14, 1, 14);
INSERT INTO `role_permission` VALUES (15, 1, 15);
INSERT INTO `role_permission` VALUES (16, 1, 16);
INSERT INTO `role_permission` VALUES (17, 1, 17);

INSERT INTO `role_permission` VALUES (18, 2, 1);
INSERT INTO `role_permission` VALUES (19, 2, 3);
INSERT INTO `role_permission` VALUES (20, 2, 9);
INSERT INTO `role_permission` VALUES (21, 2, 10);
```

### 数据统计模块数据库部署

在`MySQL`中创建数据库`gmall_report`。

```sql
CREATE DATABASE gmall_report;

use gmall_report;
```

创建表`ads_traffic_stats_by_channel`，然后插入一些测试数据：

```sql
CREATE TABLE `ads_traffic_stats_by_channel` (
  `dt` date NOT NULL COMMENT '统计日期',
  `recent_days` bigint(20) NOT NULL COMMENT '最近天数,1:最近1天,7:最近7天,30:最近30天',
  `channel` varchar(16) NOT NULL COMMENT '渠道',
  `uv_count` bigint(20) DEFAULT NULL COMMENT '访客人数',
  `avg_duration_sec` bigint(20) DEFAULT NULL COMMENT '会话平均停留时长，单位为秒',
  `avg_page_count` bigint(20) DEFAULT NULL COMMENT '会话平均浏览页面数',
  `sv_count` bigint(20) DEFAULT NULL COMMENT '会话数',
  `bounce_rate` decimal(16,2) DEFAULT NULL COMMENT '跳出率',
  PRIMARY KEY (`dt`,`recent_days`,`channel`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='各渠道流量统计';

INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, '360', 8, 53, 5, 8, 0.25);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'Appstore', 77, 66, 6, 77, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'huawei', 5, 55, 5, 5, 0.20);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'oppo', 32, 69, 6, 32, 0.06);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'vivo', 12, 74, 7, 12, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'wandoujia', 22, 76, 6, 22, 0.05);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'web', 11, 84, 8, 11, 0.00);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 1, 'xiaomi', 33, 61, 6, 33, 0.03);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, '360', 8, 53, 5, 8, 0.25);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'Appstore', 77, 66, 6, 77, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'huawei', 5, 55, 5, 5, 0.20);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'oppo', 32, 69, 6, 32, 0.06);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'vivo', 12, 74, 7, 12, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'wandoujia', 22, 76, 6, 22, 0.05);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'web', 11, 84, 8, 11, 0.00);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 7, 'xiaomi', 33, 61, 6, 33, 0.03);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, '360', 8, 53, 5, 8, 0.25);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'Appstore', 77, 66, 6, 77, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'huawei', 5, 55, 5, 5, 0.20);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'oppo', 32, 69, 6, 32, 0.06);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'vivo', 12, 74, 7, 12, 0.08);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'wandoujia', 22, 76, 6, 22, 0.05);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'web', 11, 84, 8, 11, 0.00);
INSERT INTO `ads_traffic_stats_by_channel` VALUES ('2022-06-29', 30, 'xiaomi', 33, 61, 6, 33, 0.03);
```

创建表`ads_page_path`，并插入一些测试数据：

```sql
CREATE TABLE `ads_page_path` (
  `dt` date NOT NULL COMMENT '统计日期',
  `recent_days` bigint(20) NOT NULL COMMENT '最近天数,1:最近1天,7:最近7天,30:最近30天',
  `source` varchar(64) NOT NULL COMMENT '跳转起始页面ID',
  `target` varchar(64) NOT NULL COMMENT '跳转终到页面ID',
  `path_count` bigint(20) DEFAULT NULL COMMENT '跳转次数',
  PRIMARY KEY (`dt`,`recent_days`,`source`,`target`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='页面浏览路径分析';

INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-10:payment', 'null', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-1:home', 'null', 14);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-1:home', 'step-2:good_detail', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-1:home', 'step-2:good_list', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-1:home', 'step-2:mine', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-1:home', 'step-2:search', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-2:good_detail', 'null', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-2:good_list', 'step-3:good_detail', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-2:mine', 'step-3:orders_unpaid', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-2:search', 'step-3:good_list', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-3:good_detail', 'step-4:cart', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-3:good_list', 'step-4:good_detail', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-3:orders_unpaid', 'step-4:good_detail', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-3:orders_unpaid', 'step-4:trade', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-4:cart', 'step-5:trade', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-4:good_detail', 'step-5:good_spec', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-4:good_detail', 'step-5:login', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-4:trade', 'step-5:payment', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-5:good_spec', 'step-6:comment', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-5:login', 'step-6:good_detail', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-5:login', 'step-6:register', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-5:payment', 'null', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-5:trade', 'step-6:payment', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-6:comment', 'step-7:home', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-6:comment', 'step-7:trade', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-6:good_detail', 'step-7:cart', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-6:payment', 'null', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-6:register', 'step-7:good_detail', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-7:cart', 'step-8:trade', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-7:good_detail', 'step-8:cart', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-7:home', 'null', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-7:trade', 'step-8:payment', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-8:cart', 'step-9:trade', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-8:payment', 'null', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-8:trade', 'step-9:payment', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-9:payment', 'null', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 1, 'step-9:trade', 'step-10:payment', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-10:payment', 'null', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-1:home', 'null', 14);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-1:home', 'step-2:good_detail', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-1:home', 'step-2:good_list', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-1:home', 'step-2:mine', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-1:home', 'step-2:search', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-2:good_detail', 'null', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-2:good_list', 'step-3:good_detail', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-2:mine', 'step-3:orders_unpaid', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-2:search', 'step-3:good_list', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-3:good_detail', 'step-4:cart', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-3:good_list', 'step-4:good_detail', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-3:orders_unpaid', 'step-4:good_detail', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-3:orders_unpaid', 'step-4:trade', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-4:cart', 'step-5:trade', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-4:good_detail', 'step-5:good_spec', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-4:good_detail', 'step-5:login', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-4:trade', 'step-5:payment', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-5:good_spec', 'step-6:comment', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-5:login', 'step-6:good_detail', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-5:login', 'step-6:register', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-5:payment', 'null', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-5:trade', 'step-6:payment', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-6:comment', 'step-7:home', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-6:comment', 'step-7:trade', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-6:good_detail', 'step-7:cart', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-6:payment', 'null', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-6:register', 'step-7:good_detail', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-7:cart', 'step-8:trade', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-7:good_detail', 'step-8:cart', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-7:home', 'null', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-7:trade', 'step-8:payment', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-8:cart', 'step-9:trade', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-8:payment', 'null', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-8:trade', 'step-9:payment', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-9:payment', 'null', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 7, 'step-9:trade', 'step-10:payment', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-10:payment', 'null', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-1:home', 'null', 14);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-1:home', 'step-2:good_detail', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-1:home', 'step-2:good_list', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-1:home', 'step-2:mine', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-1:home', 'step-2:search', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-2:good_detail', 'null', 27);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-2:good_list', 'step-3:good_detail', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-2:mine', 'step-3:orders_unpaid', 44);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-2:search', 'step-3:good_list', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-3:good_detail', 'step-4:cart', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-3:good_list', 'step-4:good_detail', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-3:orders_unpaid', 'step-4:good_detail', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-3:orders_unpaid', 'step-4:trade', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-4:cart', 'step-5:trade', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-4:good_detail', 'step-5:good_spec', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-4:good_detail', 'step-5:login', 77);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-4:trade', 'step-5:payment', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-5:good_spec', 'step-6:comment', 25);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-5:login', 'step-6:good_detail', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-5:login', 'step-6:register', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-5:payment', 'null', 19);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-5:trade', 'step-6:payment', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-6:comment', 'step-7:home', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-6:comment', 'step-7:trade', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-6:good_detail', 'step-7:cart', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-6:payment', 'null', 38);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-6:register', 'step-7:good_detail', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-7:cart', 'step-8:trade', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-7:good_detail', 'step-8:cart', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-7:home', 'null', 15);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-7:trade', 'step-8:payment', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-8:cart', 'step-9:trade', 32);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-8:payment', 'null', 10);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-8:trade', 'step-9:payment', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-9:payment', 'null', 45);
INSERT INTO `ads_page_path` VALUES ('2022-06-29', 30, 'step-9:trade', 'step-10:payment', 32);
```

然后我们在`ClickHouse`中造一些数据，我们将表创建在默认数据库`default`中。

可以在`localhost:8123/play`网址操作`ClickHouse`数据库。

然后在`ClickHouse`中创建表`keyword_statistics`并插入测试数据：

```sql
CREATE TABLE keyword_statistics (
    start_time DateTime,
    end_time DateTime,
    keyword String,
    source String,
    keyword_count UInt64,
    ts UInt64
) ENGINE=MergeTree ORDER BY start_time;

INSERT INTO keyword_statistics VALUES
('2022-12-19 15:24:30','2022-12-19 15:24:40','小米','SEARCH',3,1646465083000),
('2022-12-19 15:24:40','2022-12-19 15:24:50','iphone11','SEARCH',2,1646465094000),
('2022-12-19 15:24:40','2022-12-19 15:24:50','口红','SEARCH',2,1646465094000),
('2022-12-19 15:24:40','2022-12-19 15:24:50','手机','SEARCH',1,1646465094000),
('2022-12-19 15:24:40','2022-12-19 15:24:50','苹果','SEARCH',1,1646465094000),
('2022-12-19 15:24:50','2022-12-19 15:25:00','ps5','SEARCH',2,1646465103000),
('2022-12-19 15:24:50','2022-12-19 15:25:00','图书','SEARCH',1,1646465103000),
('2022-12-19 15:24:50','2022-12-19 15:25:00','小米','SEARCH',2,1646465103000),
('2022-12-19 15:24:50','2022-12-19 15:25:00','电视','SEARCH',1,1646465103000),
('2022-12-19 15:25:00','2022-12-19 15:25:10','ps5','SEARCH',1,1646465114000),
('2022-12-19 15:25:00','2022-12-19 15:25:10','口红','SEARCH',1,1646465114000),
('2022-12-19 15:25:00','2022-12-19 15:25:10','图书','SEARCH',1,1646465114000),
('2022-12-19 15:25:00','2022-12-19 15:25:10','小米','SEARCH',1,1646465114000),
('2022-12-19 15:25:00','2022-12-19 15:25:10','电视','SEARCH',2,1646465114000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','口红','SEARCH',3,1646465123000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','小米','SEARCH',2,1646465123000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','手机','SEARCH',1,1646465123000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','电视','SEARCH',1,1646465123000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','盒子','SEARCH',2,1646465123000),
('2022-12-19 15:25:10','2022-12-19 15:25:20','苹果','SEARCH',1,1646465123000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','图书','SEARCH',1,1646465134000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','小米','SEARCH',2,1646465134000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','手机','SEARCH',2,1646465134000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','电视','SEARCH',1,1646465134000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','盒子','SEARCH',1,1646465134000),
('2022-12-19 15:25:20','2022-12-19 15:25:30','苹果','SEARCH',2,1646465134000),
('2022-12-19 15:25:30','2022-12-19 15:25:40','小米','SEARCH',2,1646465143000),
('2022-12-19 15:25:30','2022-12-19 15:25:40','手机','SEARCH',2,1646465143000),
('2022-12-19 15:25:30','2022-12-19 15:25:40','盒子','SEARCH',2,1646465143000),
('2022-12-19 15:25:30','2022-12-19 15:25:40','苹果','SEARCH',2,1646465143000);
```

在`ClickHouse`中创建表`province_statistics`，然后插入测试数据：

```sql
CREATE TABLE province_statistics (
    start_time DateTime,
    end_time DateTime,
    province_id UInt64,
    province_name String,
    area_code String,
    iso_code String,
    iso_3166_2 String,
    order_amount Decimal(16,2),
    order_count UInt64,
    ts UInt64
) ENGINE=MergeTree ORDER BY start_time;

INSERT INTO province_statistics VALUES
('2022-12-19 10:48:50','2022-12-19 10:49:00',11,'江西','360000','CN-36','CN-JX',21195.00,1,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',12,'山东','370000','CN-37','CN-SD',5757.00,1,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',17,'辽宁','210000','CN-21','CN-LN',6006.00,2,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',18,'陕西','610000','CN-61','CN-SN',28062.00,2,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',20,'青海','630000','CN-63','CN-QH',12098.00,1,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',24,'湖北','420000','CN-42','CN-HB',33.00,1,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',30,'澳门','820000','CN-92','CN-MO',24613.00,1,1646448623000),
('2022-12-19 10:48:50','2022-12-19 10:49:00',32,'贵州','520000','CN-52','CN-GZ',40.00,1,1646448623000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',4,'内蒙古','150000','CN-15','CN-NM',4188.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',6,'上海','310000','CN-31','CN-SH',2598.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',10,'福建','350000','CN-35','CN-FJ',8197.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',11,'江西','360000','CN-36','CN-JX',16394.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',18,'陕西','610000','CN-61','CN-SN',4188.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',20,'青海','630000','CN-63','CN-QH',16497.00,1,1646448706000),
('2022-12-19 10:50:20','2022-12-19 10:50:30',33,'云南','530000','CN-53','CN-YN',6068.00,1,1646448706000);
```

在`ClickHouse`中创建`product_statistics`表，然后插入一些测试数据：

```sql
CREATE TABLE product_statistics (
    start_time DateTime,
    end_time DateTime,
    sku_id UInt64,
    sku_name String,
    sku_price Decimal(16,2),
    spu_id UInt64,
    spu_name String,
    tm_id UInt64,
    tm_name String,
    category3_id UInt64,
    category3_name String,
    display_count UInt64,
    click_count UInt64,
    favor_count UInt64,
    cart_count UInt64,
    order_sku_num UInt64,
    order_amount Decimal(16,2),
    order_count UInt64,
    payment_amount Decimal(16,2),
    paid_order_count UInt64,
    refund_order_count UInt64,
    refund_amount Decimal(16,2),
    comment_count UInt64,
    good_comment_count UInt64,
    ts UInt64
) ENGINE=MergeTree ORDER BY sku_id;

INSERT INTO product_statistics VALUES
('2022-12-19 11:38:10','2022-12-19 11:38:20',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,1,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',13,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB亮黑色全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',15,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB冰霜银全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,2,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,2,9,0,0.00,0,0.00,0,0,0.00,1,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,2,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,2,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',27,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z02少女红 活力青春 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,2,0,0.00,0,0.00,0,0,0.00,1,0,1646624329928),
('2022-12-19 11:38:10','2022-12-19 11:38:20',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,6,0,0.00,0,0.00,0,1,69.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624329929),
('2022-12-19 11:38:10','2022-12-19 11:38:20',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:10','2022-12-19 11:38:20',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624329930),
('2022-12-19 11:38:40','2022-12-19 11:38:50',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,1,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',0,0,1,4,1,6999.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,5,1,999.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,8,3,24591.00,1,0.00,0,0,0.00,1,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,8,1,8197.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,13,3,24591.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,6,5,45985.00,2,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',13,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB亮黑色全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',15,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB冰霜银全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,7,1,4488.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,5,3,20097.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',18,'TCL 75Q10 75英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',9199.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,4,2,5798.00,1,0.00,0,0,0.00,1,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,9,1,3299.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,1,4,1,40.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,1,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,8,2,46.00,1,0.00,0,0,0.00,1,1,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',27,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z02少女红 活力青春 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,3,8,1,129.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,6,3,387.00,1,0.00,0,0,0.00,1,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,11,6,414.00,2,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,9,1,3927.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:38:40','2022-12-19 11:38:50',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,9,3,16497.00,1,0.00,0,0,0.00,0,0,1646624341225),
('2022-12-19 11:39:00','2022-12-19 11:39:10',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,0,0,0.00,0,5798.00,0,0,0.00,0,0,1646624377368),
('2022-12-19 10:48:50','2022-12-19 10:49:00',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,1,11,2,11498.00,1,0.00,0,0,0.00,1,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,11,1,5999.00,1,0.00,0,0,0.00,0,0,1646624097715),
('2022-12-19 10:48:50','2022-12-19 10:49:00',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,3,1,999.00,1,0.00,0,0,0.00,1,1,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097709),
('2022-12-19 10:48:50','2022-12-19 10:49:00',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,7,3,24591.00,1,0.00,0,0,0.00,0,0,1646624097715),
('2022-12-19 10:48:50','2022-12-19 10:49:00',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,10,1,8197.00,1,0.00,0,0,0.00,1,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,2,10,3,24591.00,1,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:48:50','2022-12-19 10:49:00',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,2,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',13,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB亮黑色全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097705),
('2022-12-19 10:48:50','2022-12-19 10:49:00',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',15,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB冰霜银全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097765),
('2022-12-19 10:48:50','2022-12-19 10:49:00',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,11,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',18,'TCL 75Q10 75英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',9199.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,9,5,45995.00,2,0.00,0,0,0.00,1,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,6,1,11999.00,1,0.00,0,0,0.00,1,0,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,7,1,2899.00,1,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,1,10,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:48:50','2022-12-19 10:49:00',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,7,4,160.00,2,0.00,0,1,120.00,0,0,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,8,8,88.00,3,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097765),
('2022-12-19 10:48:50','2022-12-19 10:49:00',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,1,13,7,903.00,3,0.00,0,0,0.00,2,1,1646624097715),
('2022-12-19 10:48:50','2022-12-19 10:49:00',27,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z02少女红 活力青春 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,2,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,10,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,9,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,7,3,207.00,1,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:48:50','2022-12-19 10:49:00',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,2,7,2,600.00,1,0.00,0,0,0.00,1,0,1646624097769),
('2022-12-19 10:48:50','2022-12-19 10:49:00',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097715),
('2022-12-19 10:48:50','2022-12-19 10:49:00',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,2,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:48:50','2022-12-19 10:49:00',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,6,2,10998.00,2,0.00,0,0,0.00,0,0,1646624097716),
('2022-12-19 10:49:10','2022-12-19 10:49:20',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,0,0,0.00,0,11498.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:49:10','2022-12-19 10:49:20',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,0,0,0.00,0,999.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:49:10','2022-12-19 10:49:20',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,0,0,0.00,0,8197.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:49:10','2022-12-19 10:49:20',18,'TCL 75Q10 75英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',9199.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,0,0,0.00,0,27597.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:49:10','2022-12-19 10:49:20',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,0,0,0.00,0,11999.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:49:10','2022-12-19 10:49:20',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,0,0,0.00,0,120.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:49:10','2022-12-19 10:49:20',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,0,0,0.00,0,645.00,2,0,0.00,0,0,1646624097718),
('2022-12-19 10:49:10','2022-12-19 10:49:20',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,0,0,0.00,0,600.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:49:10','2022-12-19 10:49:20',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,0,0,0.00,0,5499.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:50:20','2022-12-19 10:50:30',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,11,1,5999.00,1,0.00,0,1,5999.00,0,0,1646624097770),
('2022-12-19 10:50:20','2022-12-19 10:50:30',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:50:20','2022-12-19 10:50:30',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,9,0,0.00,0,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:20','2022-12-19 10:50:30',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:50:20','2022-12-19 10:50:30',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,1,0,0.00,0,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,7,4,5196.00,2,0.00,0,0,0.00,1,0,1646624097717),
('2022-12-19 10:50:20','2022-12-19 10:50:30',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:50:20','2022-12-19 10:50:30',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,10,1,8197.00,1,0.00,0,0,0.00,1,1,1646624097770),
('2022-12-19 10:50:20','2022-12-19 10:50:30',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,4,2,16394.00,1,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 10:50:20','2022-12-19 10:50:30',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:50:20','2022-12-19 10:50:30',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,13,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:50:20','2022-12-19 10:50:30',13,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB亮黑色全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,2,9,1,4188.00,1,0.00,0,0,0.00,0,0,1646624097717),
('2022-12-19 10:50:20','2022-12-19 10:50:30',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,2,5,1,4188.00,1,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',15,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB冰霜银全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,2,0,0.00,0,0.00,0,0,0.00,0,0,1646624097772),
('2022-12-19 10:50:20','2022-12-19 10:50:30',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,7,2,8976.00,1,0.00,0,0,0.00,1,0,1646624097720),
('2022-12-19 10:50:20','2022-12-19 10:50:30',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,7,3,20097.00,1,0.00,0,0,0.00,1,1,1646624097771),
('2022-12-19 10:50:20','2022-12-19 10:50:30',18,'TCL 75Q10 75英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',9199.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,2,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:50:20','2022-12-19 10:50:30',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:50:20','2022-12-19 10:50:30',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,1,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097772),
('2022-12-19 10:50:20','2022-12-19 10:50:30',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,1,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 10:50:20','2022-12-19 10:50:30',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:50:20','2022-12-19 10:50:30',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,1,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:20','2022-12-19 10:50:30',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,6,1,11.00,1,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097769),
('2022-12-19 10:50:20','2022-12-19 10:50:30',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,1,3,3,317.00,1,0.00,0,1,387.00,0,0,1646624097719),
('2022-12-19 10:50:20','2022-12-19 10:50:30',27,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z02少女红 活力青春 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,10,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:50:20','2022-12-19 10:50:30',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,2,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,2,1,69.00,1,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:20','2022-12-19 10:50:30',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,1,8,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:50:20','2022-12-19 10:50:30',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,1,7,4,1952.00,2,0.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:20','2022-12-19 10:50:30',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,9,0,0.00,0,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:20','2022-12-19 10:50:30',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,5,3,16497.00,1,0.00,0,0,0.00,1,1,1646624097720),
('2022-12-19 10:50:40','2022-12-19 10:50:50',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,0,0,0.00,0,5999.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:50:40','2022-12-19 10:50:50',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,0,0,0.00,0,2598.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:40','2022-12-19 10:50:50',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,0,0,0.00,0,8197.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:50:40','2022-12-19 10:50:50',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,0,0,0.00,0,8976.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:50:40','2022-12-19 10:50:50',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,0,0,0.00,0,20097.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:50:40','2022-12-19 10:50:50',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,0,0,0.00,0,11.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:40','2022-12-19 10:50:50',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,0,0,0.00,0,317.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:50:40','2022-12-19 10:50:50',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,0,0,0.00,0,69.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:50:40','2022-12-19 10:50:50',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,0,0,0.00,0,488.00,0,0,0.00,0,0,1646624097718),
('2022-12-19 10:50:40','2022-12-19 10:50:50',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,0,0,0.00,0,16497.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,1,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,8,2,1998.00,1,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',0,0,1,8,3,3897.00,1,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,1,5,2,16394.00,1,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 10:51:40','2022-12-19 10:51:50',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,2,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:51:40','2022-12-19 10:51:50',13,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB亮黑色全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,9,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,7,1,4188.00,1,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:51:40','2022-12-19 10:51:50',15,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB冰霜银全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',17,'TCL 65Q10 65英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',6699.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,4,3,20097.00,1,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',18,'TCL 75Q10 75英寸 QLED原色量子点电视 安桥音响 AI声控智慧屏 超薄全面屏 MEMC防抖 3+32GB 平板电视',9199.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,2,9,2,18398.00,2,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 10:51:40','2022-12-19 10:51:50',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,1,11,6,71994.00,2,0.00,0,0,0.00,1,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,1,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,2,10,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,7,1,39.00,1,0.00,0,0,0.00,1,1,1646624097770),
('2022-12-19 10:51:40','2022-12-19 10:51:50',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,2,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:51:40','2022-12-19 10:51:50',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:51:40','2022-12-19 10:51:50',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,6,2,188.00,1,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',27,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z02少女红 活力青春 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 10:51:40','2022-12-19 10:51:50',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,4,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,2,4,5,275.00,2,0.00,0,0,0.00,1,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,4,2,138.00,1,0.00,0,0,0.00,0,0,1646624097719),
('2022-12-19 10:51:40','2022-12-19 10:51:50',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,6,3,207.00,1,0.00,0,0,0.00,1,0,1646624097721),
('2022-12-19 10:51:40','2022-12-19 10:51:50',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,11,0,0.00,0,0.00,0,0,0.00,0,0,1646624097770),
('2022-12-19 10:51:40','2022-12-19 10:51:50',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,1,6,0,0.00,0,0.00,0,0,0.00,0,0,1646624097720),
('2022-12-19 10:51:40','2022-12-19 10:51:50',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,0,7,3,11781.00,1,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:51:40','2022-12-19 10:51:50',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,2,5,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 10:52:00','2022-12-19 10:52:10',19,'TCL 85Q6 85英寸 巨幕私人影院电视 4K超高清 AI智慧屏 全景全面屏 MEMC运动防抖 2+16GB 液晶平板电视机',11999.00,5,'TCL巨幕私人影院电视 4K超高清 AI智慧屏  液晶平板电视机',4,'TCL',86,'平板电视',0,0,0,0,0,0.00,0,35997.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 10:52:00','2022-12-19 10:52:10',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,0,0,0.00,0,39.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 10:52:00','2022-12-19 10:52:10',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,0,0,0.00,0,137.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 10:52:00','2022-12-19 10:52:10',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,0,0,0,0.00,0,207.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:24:30','2022-12-19 15:24:40',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',15,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:24:30','2022-12-19 15:24:40',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',19,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:30','2022-12-19 15:24:40',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',22,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:30','2022-12-19 15:24:40',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',22,5,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:30','2022-12-19 15:24:40',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',20,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:30','2022-12-19 15:24:40',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',20,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:30','2022-12-19 15:24:40',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',33,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:30','2022-12-19 15:24:40',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',12,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:30','2022-12-19 15:24:40',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',21,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 15:24:30','2022-12-19 15:24:40',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',26,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:24:40','2022-12-19 15:24:50',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',33,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:24:40','2022-12-19 15:24:50',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',22,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:24:40','2022-12-19 15:24:50',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',35,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:40','2022-12-19 15:24:50',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',33,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:40','2022-12-19 15:24:50',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',26,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:40','2022-12-19 15:24:50',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',35,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:40','2022-12-19 15:24:50',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',27,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:24:40','2022-12-19 15:24:50',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',26,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:40','2022-12-19 15:24:50',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',29,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 15:24:40','2022-12-19 15:24:50',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',29,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:24:50','2022-12-19 15:25:00',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',32,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:24:50','2022-12-19 15:25:00',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',26,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:24:50','2022-12-19 15:25:00',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',35,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:24:50','2022-12-19 15:25:00',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',29,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:24:50','2022-12-19 15:25:00',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',30,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:24:50','2022-12-19 15:25:00',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',34,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:24:50','2022-12-19 15:25:00',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',20,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:24:50','2022-12-19 15:25:00',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',33,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097721),
('2022-12-19 15:24:50','2022-12-19 15:25:00',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',34,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097771),
('2022-12-19 15:24:50','2022-12-19 15:25:00',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',26,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:25:00','2022-12-19 15:25:10',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',39,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:25:00','2022-12-19 15:25:10',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',32,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:00','2022-12-19 15:25:10',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',30,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:25:00','2022-12-19 15:25:10',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',30,5,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:25:00','2022-12-19 15:25:10',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',29,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:25:00','2022-12-19 15:25:10',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',28,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:25:00','2022-12-19 15:25:10',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',36,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097724),
('2022-12-19 15:25:00','2022-12-19 15:25:10',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',37,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:25:00','2022-12-19 15:25:10',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',24,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097772),
('2022-12-19 15:25:00','2022-12-19 15:25:10',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',41,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097774),
('2022-12-19 15:25:10','2022-12-19 15:25:20',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',37,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:10','2022-12-19 15:25:20',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',30,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:10','2022-12-19 15:25:20',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',32,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:10','2022-12-19 15:25:20',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',35,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:25:10','2022-12-19 15:25:20',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',31,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:10','2022-12-19 15:25:20',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',32,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:10','2022-12-19 15:25:20',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',29,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:10','2022-12-19 15:25:20',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',31,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097722),
('2022-12-19 15:25:10','2022-12-19 15:25:20',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',21,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097772),
('2022-12-19 15:25:10','2022-12-19 15:25:20',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',23,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:20','2022-12-19 15:25:30',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',34,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:20','2022-12-19 15:25:30',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',35,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:20','2022-12-19 15:25:30',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',27,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:20','2022-12-19 15:25:30',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',31,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:20','2022-12-19 15:25:30',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',43,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:20','2022-12-19 15:25:30',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',32,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:20','2022-12-19 15:25:30',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',30,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097725),
('2022-12-19 15:25:20','2022-12-19 15:25:30',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',39,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:20','2022-12-19 15:25:30',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',35,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097772),
('2022-12-19 15:25:20','2022-12-19 15:25:30',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',26,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:30','2022-12-19 15:25:40',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',34,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:30','2022-12-19 15:25:40',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',30,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097726),
('2022-12-19 15:25:30','2022-12-19 15:25:40',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',32,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097726),
('2022-12-19 15:25:30','2022-12-19 15:25:40',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',35,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:30','2022-12-19 15:25:40',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',31,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097726),
('2022-12-19 15:25:30','2022-12-19 15:25:40',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',30,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:30','2022-12-19 15:25:40',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',38,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097726),
('2022-12-19 15:25:30','2022-12-19 15:25:40',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',31,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097723),
('2022-12-19 15:25:30','2022-12-19 15:25:40',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',35,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097773),
('2022-12-19 15:25:30','2022-12-19 15:25:40',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',30,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624097775),
('2022-12-19 15:25:40','2022-12-19 15:25:50',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',28,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295291),
('2022-12-19 15:25:40','2022-12-19 15:25:50',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',25,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295292),
('2022-12-19 15:25:40','2022-12-19 15:25:50',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',33,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295292),
('2022-12-19 15:25:40','2022-12-19 15:25:50',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',25,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295288),
('2022-12-19 15:25:40','2022-12-19 15:25:50',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',24,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295292),
('2022-12-19 15:25:40','2022-12-19 15:25:50',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',22,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295288),
('2022-12-19 15:25:40','2022-12-19 15:25:50',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',26,3,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295292),
('2022-12-19 15:25:40','2022-12-19 15:25:50',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',32,4,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295288),
('2022-12-19 15:25:40','2022-12-19 15:25:50',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',21,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295288),
('2022-12-19 15:25:40','2022-12-19 15:25:50',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',25,2,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646624295292),
('2022-12-19 18:46:10','2022-12-19 18:46:20',14,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 6GB+128GB冰霜银全网通5G手机',4188.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,1,8,2,8376.00,2,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',24,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',11.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,9,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',28,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Z03女王红 性感冷艳 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,0,11,2,219.16,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',29,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M01醉蔷薇',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,7,3,175.84,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',30,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M02干玫瑰',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,9,1,69.00,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:00','2022-12-19 18:46:10',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',2,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775219),
('2022-12-19 18:46:00','2022-12-19 18:46:10',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',1,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775232),
('2022-12-19 18:46:00','2022-12-19 18:46:10',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',1,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775232),
('2022-12-19 18:46:00','2022-12-19 18:46:10',4,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 冰雾白 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',3,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775232),
('2022-12-19 18:46:00','2022-12-19 18:46:10',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',2,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775219),
('2022-12-19 18:46:00','2022-12-19 18:46:10',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',3,1,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775219),
('2022-12-19 18:46:00','2022-12-19 18:46:10',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',3,0,0,0,0,0.00,0,0.00,0,0,0.00,0,0,1646390775232),
('2022-12-19 18:46:10','2022-12-19 18:46:20',1,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 陶瓷黑 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',18,1,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',2,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 12GB+256GB 陶瓷黑 游戏手机',6999.00,1,'小米10',5,'小米',61,'手机',24,2,1,5,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',3,'小米10 至尊纪念版 双模5G 骁龙865 120HZ高刷新率 120倍长焦镜头 120W快充 8GB+128GB 透明版 游戏手机',5999.00,1,'小米10',5,'小米',61,'手机',18,1,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646390778081),
('2022-12-19 18:46:10','2022-12-19 18:46:20',5,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 4GB+128GB 明月灰 游戏智能手机 小米 红米',999.00,2,'Redmi 10X',1,'Redmi',61,'手机',25,1,0,9,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',6,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 冰雾白 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',30,0,0,11,3,3897.00,2,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',7,'Redmi 10X 4G Helio G85游戏芯 4800万超清四摄 5020mAh大电量 小孔全面屏 128GB大存储 8GB+128GB 明月灰 游戏智能手机 小米 红米',1299.00,2,'Redmi 10X',1,'Redmi',61,'手机',23,1,0,7,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',8,'Apple iPhone 12 (A2404) 64GB 黑色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',14,3,0,7,6,49182.00,2,0.00,0,0,0.00,1,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',9,'Apple iPhone 12 (A2404) 64GB 红色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',23,2,1,9,1,8197.00,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',10,'Apple iPhone 12 (A2404) 64GB 蓝色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',10,1,1,8,1,8197.00,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',11,'Apple iPhone 12 (A2404) 64GB 白色 支持移动联通电信5G 双卡双待手机',8197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,3,7,0,0.00,0,0.00,0,0,0.00,0,0,1646390778081),
('2022-12-19 18:46:10','2022-12-19 18:46:20',12,'Apple iPhone 12 (A2404) 128GB 黑色 支持移动联通电信5G 双卡双待手机',9197.00,3,'Apple iPhone 12',2,'苹果',61,'手机',0,0,2,6,1,9197.00,1,0.00,0,0,0.00,0,0,1646390778082),
('2022-12-19 18:46:10','2022-12-19 18:46:20',16,'华为 HUAWEI P40 麒麟990 5G SoC芯片 5000万超感知徕卡三摄 30倍数字变焦 8GB+128GB亮黑色全网通5G手机',4488.00,4,'HUAWEI P40',3,'华为',61,'手机',0,0,0,10,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',20,'小米电视E65X 65英寸 全面屏 4K超高清HDR 蓝牙遥控内置小爱 2+8GB AI人工智能液晶网络平板电视 L65M5-EA',2899.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,6,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',21,'小米电视4A 70英寸 4K超高清 HDR 二级能效 2GB+16GB L70M5-4A 内置小爱 智能网络液晶平板教育电视',3299.00,6,'小米电视 内置小爱 智能网络液晶平板教育电视',5,'小米',86,'平板电视',0,0,0,5,2,6598.00,1,0.00,0,0,0.00,1,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',22,'十月稻田 长粒香大米 东北大米 东北香米 5kg',39.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,6,2,78.00,1,0.00,0,0,0.00,1,0,1646390778082),
('2022-12-19 18:46:10','2022-12-19 18:46:20',23,'十月稻田 辽河长粒香 东北大米 5kg',40.00,7,'十月稻田 长粒香大米 东北大米 东北香米 5kg',6,'长粒香',803,'米面杂粮',0,0,0,4,3,120.00,1,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',25,'金沙河面条 银丝挂面900g*3包 爽滑 细面条 龙须面 速食面',23.00,8,'金沙河面条 原味银丝挂面 龙须面 方便速食拉面 清汤面 900g',7,'金沙河',803,'米面杂粮',0,0,0,8,0,0.00,0,0.00,0,0,0.00,0,0,1646390778082),
('2022-12-19 18:46:10','2022-12-19 18:46:20',26,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 Y01复古红 百搭气质 璀璨金钻哑光唇膏 ',129.00,9,'索芙特i-Softto 口红不掉色唇膏保湿滋润 璀璨金钻哑光唇膏 ',8,'索芙特',477,'唇部',0,0,1,7,1,99.00,1,0.00,0,0,0.00,1,1,1646390778081),
('2022-12-19 18:46:10','2022-12-19 18:46:20',31,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏 M03赤茶',69.00,10,'CAREMiLLE珂曼奶油小方口红 雾面滋润保湿持久丝缎唇膏',9,'CAREMiLLE',477,'唇部',0,0,1,5,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',32,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 5号淡香水35ml',300.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,7,5,1500.00,4,0.00,0,0,0.00,1,1,1646390778082),
('2022-12-19 18:46:10','2022-12-19 18:46:20',33,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT 粉邂逅淡香水35ml',488.00,11,'香奈儿（Chanel）女士香水5号香水 粉邂逅柔情淡香水EDT ',11,'香奈儿',473,'香水',0,0,0,3,0,0.00,0,0.00,0,0,0.00,0,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',34,'华为智慧屏V55i-J 55英寸 HEGE-550B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 银钻灰 京品家电',3927.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,10,10,39270.00,5,0.00,0,0,0.00,1,0,1646390778080),
('2022-12-19 18:46:10','2022-12-19 18:46:20',35,'华为智慧屏V65i 65英寸 HEGE-560B 4K全面屏智能电视机 多方视频通话 AI升降摄像头 4GB+32GB 星际黑',5499.00,12,'华为智慧屏 4K全面屏智能电视机',3,'华为',86,'平板电视',0,0,1,10,4,21996.00,2,0.00,0,0,0.00,1,0,1646390778080);
```

### 报表管理模块数据库部署

这部分不用部署，我们的报表管理模块直接在`ClickHouse`中的`default`数据库和`MySQL`中的`gmall_report`数据库中创建新的表。

### 任务调度模块数据库部署

在`MySQL`中创建`yuntai_schedule`数据库。

```sql
CREATE DATABASE yuntai_schedule;

use yuntai_schedule;
```

创建表`scheduler_job_info`：

```sql
create table scheduler_job_info (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    jobType varchar(100),
    jobName varchar(100),
    jobGroup varchar(100),
    jobStatus varchar(100),
    jobClass varchar(100),
    cronExpression varchar(100),
    repeatTime bigint(20),
    cronJob boolean,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### 数据治理模块数据库部署

创建数据库`yuntai_government`

```sql
CREATE DATABASE yuntai_government;

use yuntai_government;
```

然后创建表`mysql_data_monitor`，用来存放监控`MySQL`中表的字段的结果数据

```sql
create table mysql_data_monitor (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    database_name varchar(100),
    table_name varchar(100),
    field_name varchar(100),
    field_null_rate double(40,2),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

创建数据库`gmall`用来做`MySQL`监控的测试数据库。

创建一张简单的表`yuntai_zuoyuan`，用来监控

```sql
create table yuntai_zuoyuan (age varchar(10), height varchar(10));

insert into yuntai_zuoyuan (age) values ('10');
insert into yuntai_zuoyuan (height) values ('10');
```

然后创建表`hive_metadata_monitor`，用来存放`Hive`元数据质量监控的结果数据

```sql
create table hive_metadata_monitor (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    database_name varchar(100),
    table_name varchar(100),
    fields_number bigint(20),
    missing_comment_fields_number bigint(20),
    has_table_comment tinyint(1),
    has_technical_owner tinyint(1),
    has_business_owner tinyint(1),
    has_output_last_seven_days tinyint(1),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

#### neo4j安装

在网址`https://neo4j.com/download-center/#community`下载对应操作系统的`neo4j`社区版。然后安装。

下载完毕以后，解压缩，然后进入到文件夹中。

```bash
cd 解压缩后的neo4j文件夹

./bin/neo4j start
```

就可以启动`neo4j`数据库了。可以在网页`localhost:7474`来操作`neo4j`数据库。操作之前需要修改密码。

neo4j的初始用户名和密码都是`neo4j`。

代码中我的neo4j的用户名是：`neo4j`，密码是：`00000000`。

> 注意：系统的JDK版本必须是`Java 17`以上的版本。

`neo4j`快速入门教程：`https://juejin.cn/post/7146016720388358181`