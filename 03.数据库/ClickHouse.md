

Clickhouse 是一个面向联机分析处理(OLAP)的开源的面向列式存储的 DBMS，简称 CK, 与 Hadoop, Spark 相比，ClickHouse 很轻量级,由俄罗斯第一大搜索引擎 **`Yandex`** 于2016年6月发布, 开发语言为 **`C++`** 。主要用于在线分析处理查询（OLAP），能够使用 SQL 查询实时生成分析数据报告。



关于时间检索：`toDate(data_time)` 字符串转日期 data , `formatDateTime(toDate(data_time),'%Y-%m-%d')` 时间格式转换，然后比较。

```xml
<if test="startTime != null and startTime != ''"><!-- 开始时间检索 -->
    AND formatDateTime(toDate(data_time),'%Y-%m-%d') &gt;= formatDateTime(toDate(#{startTime}),'%Y-%m-%d')
</if>
<if test="endTime != null and endTime != ''"><!-- 结束时间检索 -->
    AND formatDateTime(toDate(data_time),'%Y-%m-%d') &lt;= formatDateTime(toDate(#{endTime}),'%Y-%m-%d')
</if>
```





```xml
<select id="getListByConditions" resultType="com.ewsmp.module.carbonasset.entity.SingleBusDetail">
    select vin, data_time, carbon_emission_day, carbon_reduction_day, carbon_emission_online, carbon_reduction_online,
           oem_name, bus_team, car_no, branch_company
    from carbonasset_bus_single_emission
    <where>
        <if test="vin!=null and vin!=''"> and vin = #{vin}</if>
        <if test="branchCompany!=null and branchCompany!=''"> and branch_company like concat('%', #{branchCompany}, '%')</if>
        <if test="busTeam != null and busTeam !=''"> AND bus_team = #{busTeam}</if>
        <if test="oemName!=null and oemName!=''"> and oem_name like concat('%', #{oemName}, '%')</if>
        <if test="startTime != null and startTime != ''"><!-- 开始时间检索 -->
            AND formatDateTime(toDate(data_time),'%Y-%m-%d') &gt;= formatDateTime(toDate(#{startTime}),'%Y-%m-%d')
        </if>
        <if test="endTime != null and endTime != ''"><!-- 结束时间检索 -->
            AND formatDateTime(toDate(data_time),'%Y-%m-%d') &lt;= formatDateTime(toDate(#{endTime}),'%Y-%m-%d')
        </if>
    </where>
    ORDER BY data_time DESC
</select>
```

















- 参考

[Clickhouse时间日期函数一文详解+代码展示](https://blog.csdn.net/master_hunter/article/details/125762575)

[clickhouse函数](https://blog.csdn.net/qyj19920704/article/details/126614453)