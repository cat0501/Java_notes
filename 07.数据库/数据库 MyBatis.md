







[`yudao-spring-boot-starter-mybatis`](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/)技术组件，基于 MyBatis Plus 实现数据库的操作。如果你没有学习过 MyBatis Plus，建议先阅读 [《芋道 Spring Boot MyBatis 入门 》](https://www.iocoder.cn/Spring-Boot/MyBatis/?yudao)文章。



> 友情提示
>
> MyBatis 是最容易读懂的 Java 框架之一，感兴趣的话，可以看看 [《芋道 MyBatis 源码解析》](https://www.iocoder.cn/MyBatis/good-collection/?yudao)系列，已经有 18000 人学习过！



<br>

## 1. 实体类

BaseDO是所有数据库实体的**父类**，代码如下：

```java
@Data
public abstract class BaseDO implements Serializable {

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建者，目前使用 AdminUserDO / MemberUserDO 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    /**
     * 更新者，目前使用 AdminUserDO / MemberUserDO 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

}

```





- `reateTime` + `creator` 字段，创建人相关信息。
- `updater` + `updateTime` 字段，创建人相关信息。
- `deleted` 字段，逻辑删除。

对应的 SQL 字段如下：

```sql
`creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

```



### 1.1 主键编号

`id` 主键编号，推荐使用 Long 型自增，原因是：

- 自增，保证数据库是按顺序写入，性能更加优秀。
- Long 型，避免未来业务增长，超过 Int 范围。

<br>

对应的 SQL 字段如下：

```sql
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
```



项目的 `id` **默认**采用数据库自增的策略，如果希望使用 Snowflake 雪花算法，可以修改 `application.yaml` 配置文件，将配置项 `mybatis-plus.global-config.db-config.id-type` 修改为 `ASSIGN_ID`。如下图所示：



![配置 Snowflake 雪花算法](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/01.png)





### 1.2 逻辑删除

所有表通过 `deleted` 字段来实现逻辑删除，值为 0 表示未删除，值为 1 表示已删除，可见 `application.yaml` 配置文件的 `logic-delete-value` 和 `logic-not-delete-value` 配置项。如下图所示：

![逻辑删除的配置](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/03.png)



① 所有 SELECT 查询，都会自动拼接 `WHERE deleted = 0` 查询条件，过滤已经删除的记录。如果被删除的记录，只能通过在 XML 或者 `@SELECT` 来手写 SQL 语句。例如说：



② 建立唯一索引时，需要额外增加 `delete_time` 字段，添加到唯一索引字段中，避免唯一索引冲突。例如说，`system_user` 使用 `username` 作为唯一索引：

- 未添加前：先逻辑删除了一条 `username = yudao` 的记录，然后又插入了一条 `username = yudao` 的记录时，会报索引冲突的异常。
- 已添加后：先逻辑删除了一条 `username = yudao` 的记录并更新 `delete_time` 为当前时间，然后又插入一条 `username = yudao` 并且 `delete_time` 为 0 的记录，不会导致唯一索引冲突。





### 1.3 自动填充

[DefaultDBFieldHandler](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/handler/DefaultDBFieldHandler.java)基于 MyBatis 自动填充机制，实现 BaseDO 通用字段的自动设置。代码如下如：

![DefaultDBFieldHandler 自动填充](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/02.png)







### 1.4 “复杂”字段类型

MyBatis Plus 提供 TypeHandler 字段类型处理器，用于 JavaType 与 JdbcType 之间的转换。示例如下：

![字段处理器的示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/13.png)



常用的字段类型处理器有：

- [JacksonTypeHandler](https://github.com/baomidou/mybatis-plus/blob/a3e121c27cd26cb7c546dfb88190f3b1f574dc38/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/handlers/JacksonTypeHandler.java)：通用的 Jackson 实现 JSON 字段类型处理器。
- [JsonLongSetTypeHandler](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/type/JsonLongSetTypeHandler.java)：针对 `Set<Long>` 的 Jackson 实现 JSON 字段类型处理器。

另外，如果你后续要拓展自定义的 TypeHandler 实现，可以添加到 [`cn.iocoder.yudao.framework.mybatis.core.type`](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/type/JsonLongSetTypeHandler.java)包下。







## 2. 编码规范



① 数据库实体类放在 `dal.dataobject` 包下，以 DO 结尾；数据库访问类放在 `dal.mysql` 包下，以 Mapper 结尾。如下图所示：

![包规范](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/17.png)



② 数据库实体类的注释要完整，特别是哪些字段是关联（外键）、枚举、冗余等等。例如说：

![包规范](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/18.png)



③ 禁止在 Controller、Service 中，**直接**进行 MyBatis Plus 操作。原因是：大量 MyBatis 操作散落在 Service 中，会导致 Service 的代码越来乱，无法聚焦业务逻辑。

![image-20220410095055620](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410095055620.png)



并且，通过只允许将 MyBatis Plus 操作编写 Mapper 层，更好的实现 SELECT 查询的复用，而不是 Service 会存在很多相同且重复的 SELECT 查询的逻辑。

<br>



④ Mapper 的 SELECT 查询方法的命名，采用 Spring Data 的 ["Query methods"](https://docs.spring.io/spring-data/jpa/docs/2.2.0.RELEASE/reference/html/#jpa.query-methods.query-creation)策略，方法名使用 `selectBy查询条件` 规则。例如说：

![SELECT 命名示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/07.png)

<br>

⑤ 优先使用 LambdaQueryWrapper 条件构造器，使用方法获得字段名，避免手写 `"字段"` 可能写错的情况。例如说：

![LambdaQueryWrapper 条件构造器](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/08.png)

<br>

⑥ 简单的单表查询，优先在 Mapper 中通过 `default` 方法实现。例如说：

![单表查询](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/11.png)



## 3. CRUD 接口

[BaseMapperX](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/mapper/BaseMapperX.java)接口，继承 MyBatis Plus 的 BaseMapper 接口，提供更强的 CRUD 操作能力。





### 3.1 selectOne

[`#selectOne(...)`](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/mapper/BaseMapperX.java#L30-L44)方法，使用指定条件，查询单条记录。示例如下：

![selectOne 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/09.png)



### 3.2 selectCount

[`#selectList(...)`](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/mapper/BaseMapperX.java#L58-L76)方法，使用指定条件，查询多条记录。示例如下：

![selectCount 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/10.png)



### 3.3 selectList

[`#selectList(...)`](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/mapper/BaseMapperX.java#L58-L76)方法，使用指定条件，查询多条记录。示例如下：

![selectList 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/11-20220410095728066.png)



### 3.4 selectPage

针对 MyBatis Plus 分页查询的二次分装，在 [BaseMapperX ](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/mapper/BaseMapperX.java)中实现，目的是使用项目自己的分页封装：

- 【入参】查询前，将项目的分页参数 [PageParam](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-common/src/main/java/cn/iocoder/yudao/framework/common/pojo/PageParam.java)，转换成 MyBatis Plus 的 IPage 对象。
- 【出参】查询后，将 MyBatis Plus 的分页结果 IPage，转换成项目的分页结果 [PageResult](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-common/src/main/java/cn/iocoder/yudao/framework/common/pojo/PageResult.java)。代码如下图：

![BaseMapperX 实现](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/01-20220410095927878.png)

<br>

具体的使用示例，可见 [TenantMapper](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-module-system/yudao-module-system-impl/src/main/java/cn/iocoder/yudao/module/system/dal/mysql/tenant/TenantMapper.java)类中，定义 selectPage 查询方法。代码如下：

```java
@Mapper
public interface TenantMapper extends BaseMapperX<TenantDO> {

    default PageResult<TenantDO> selectPage(TenantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TenantDO>()
                .likeIfPresent(TenantDO::getName, reqVO.getName()) // 如果 name 不为空，则进行 like 查询
                .likeIfPresent(TenantDO::getContactName, reqVO.getContactName())
                .likeIfPresent(TenantDO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(TenantDO::getStatus, reqVO.getStatus()) // 如果 status 不为空，则进行 = 查询
                .betweenIfPresent(TenantDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime()) // 如果 create 不为空，则进行 between 查询
                .orderByDesc(TenantDO::getId)); // 按照 id 倒序
    }
    
}

```



完整实战，可见 [《开发手册 —— 分页实现》](https://doc.iocoder.cn/page-feature) 文档。

## 4. 批量插入

绝大多数场景下，推荐使用 MyBatis Plus 提供的 IService 的 [`#saveBatch()`](https://github.com/baomidou/mybatis-plus/blob/34ebdf6ee6/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/service/IService.java#L66-L74)方法。示例 [PermissionServiceImpl](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-module-system/yudao-module-system-impl/src/main/java/cn/iocoder/yudao/module/system/service/permission/PermissionServiceImpl.java#L200-L230)如下：

![saveBatch 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/14.png)



## 5. 条件构造器



继承 MyBatis Plus 的条件构造器，拓展了 [LambdaQueryWrapperX](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/query/LambdaQueryWrapperX.java)和 [QueryWrapperX ](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-framework/yudao-spring-boot-starter-mybatis/src/main/java/cn/iocoder/yudao/framework/mybatis/core/query/QueryWrapperX.java)类，主要是增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。例如说：



![xxxIfPresent 方法](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/15.png)



具体的使用示例如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/16.png)





## 6. Mapper XML

默认配置下，MyBatis Mapper XML 需要写在各 `yudao-module-xxx-impl` 模块的 `resources/mapper` 目录下。示例 [TestDemoMapper.xml](https://github.com/YunaiV/ruoyi-vue-pro/blob/master/yudao-module-infra/yudao-module-infra-impl/src/main/resources/mapper/test/TestDemoMapper.xml)如下：



![TestDemoMapper.xml 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/19.png)



尽量避免数据库的连表（多表）查询，而是采用多次查询，Java 内存拼接的方式替代。例如说：

![UserController 示例](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/20.png)































