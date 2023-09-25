package com.controller;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OracleTest02 {
    public static void main(String[] args) {

        String ddl = "CREATE TABLE `test_upload02`(\n" +
                "  `id` string COMMENT '主键', \n" +
                "  `vin` string COMMENT '车辆vin', \n" +
                "  `veh_brand` string COMMENT '车辆品牌', \n" +
                "  `veh_type` int COMMENT '车辆类型(乘用车及客车-1\\;专用汽车-2\\;半挂牵引车-3\\;货车-4\\;)', \n" +
                "  `veh_name` string COMMENT '车辆名称(纯电动轿车等)', \n" +
                "  `notice_model` string COMMENT '车辆公告型号', \n" +
                "  `config_num` string COMMENT '车辆配置号', \n" +
                "  `production_year` int COMMENT '下线时间-年', \n" +
                "  `production_year_month` int COMMENT '下线时间-年-月', \n" +
                "  `production_time` timestamp COMMENT '车辆下线时间', \n" +
                "  `pack_size` int COMMENT '电池包数', \n" +
                "  `module_size` int COMMENT '电池模块数', \n" +
                "  `cell_size` int COMMENT '电池单体数', \n" +
                "  `province_code` int COMMENT '厂商所属省份编码', \n" +
                "  `city_code` int COMMENT '厂商所属城市编码', \n" +
                "  `auth_name` string COMMENT '所属厂商', \n" +
                "  `auth_code` string COMMENT '所属厂商编码', \n" +
                "  `auth_id` string COMMENT '厂商id', \n" +
                "  `create_time` timestamp COMMENT '车辆上报到国家平台的时间', \n" +
                "  `auth_type` int COMMENT '厂商类型：0 国产汽车企业 ，1 进口汽车企业', \n" +
                "  `battery_type_sign` string COMMENT '电池包类型及公告型号对应电池类型:镍氢电池:A\\;磷酸铁锂电池:B\\;锰酸锂电池:C\\;钴酸锂电池:D\\;三元材料电池:E\\;钛酸锂电池:G\\;超级电容器:F\\;其他:Z', \n" +
                "  `cell_enterprise` string COMMENT '单体生产企业', \n" +
                "  `cell_enterprise_abb` string COMMENT '单体生产企业母公司-简称', \n" +
                "  `cell_enterprise_sign` string COMMENT '单体生产企业母公司', \n" +
                "  `cell_manufacturer` string COMMENT '单体厂商', \n" +
                "  `module_manufacturer` string COMMENT '模块厂商', \n" +
                "  `pack_manufacturer` string COMMENT '电池包厂商', \n" +
                "  `eginee_type` string COMMENT '动力类型：插电混动,纯电动，混合动力，燃料电池', \n" +
                "  `battery_type` string COMMENT '电池类型：镍氢电池:A\\;磷酸铁锂电池:B\\;锰酸锂电池:C\\;钴酸锂电池:D\\;三元材料电池:E\\;钛酸锂电池:G\\;超级电容器:F\\;其他:Z', \n" +
                "  `veh_type_max` string COMMENT '车辆类型 大分类 CY :乘用车KC:客车ZY：专用车', \n" +
                "  `syn_time` timestamp COMMENT '同步时间', \n" +
                "  `batterypass` double COMMENT '质量', \n" +
                "  `mileage` double COMMENT '里程', \n" +
                "  `power_amount` double COMMENT '电池包总电量（度），从车型表来', \n" +
                "  `rating_volume` double COMMENT '电池包总容量（安时），从车型表来', \n" +
                "  `battery_production_time` timestamp COMMENT '电池生产时间', \n" +
                "  `sale_time` date COMMENT '销售时间', \n" +
                "  `sale_year` int COMMENT '销售时间-年', \n" +
                "  `sale_year_month` int COMMENT '销售时间-年-月', \n" +
                "  `sale_area` string COMMENT '销售地区(原文本)', \n" +
                "  `sell_province_code` int COMMENT '销售省份编码', \n" +
                "  `sell_city_code` int COMMENT '销售城市编码', \n" +
                "  `license_plate` string COMMENT '车牌', \n" +
                "  `veh_purpose` string COMMENT '车辆用途', \n" +
                "  `battery_voltage` double COMMENT '储能装置总成标称电压（V）', \n" +
                "  `battery_capacity` double COMMENT '总成标称容量(Ah)', \n" +
                "  `endtime` string COMMENT '最后一次注册日期', \n" +
                "  `retire_date` string COMMENT '退役日期', \n" +
                "  `retire_date_new` string COMMENT '退役日期_新_对比用', \n" +
                "  `retire_date_extra` string COMMENT '退役日期_对比用', \n" +
                "  `retire_date_other` string, \n" +
                "  `cell_shape` string COMMENT '1:方形  2:圆柱形 3:软包 4:其它', \n" +
                "  `cell_shape_sign` int COMMENT '1:方形  2:圆柱形 3:软包 4:其它', \n" +
                "  `last_upload_dynamic_time` string COMMENT '最后一次更新天数（天）', \n" +
                "  `sale_create_time` timestamp COMMENT '销售日期创建时间')\n" +
                "COMMENT '碳核算车辆公告号信息表维护表'\n" +
                "ROW FORMAT SERDE \n" +
                "  'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe' \n" +
                "STORED AS INPUTFORMAT \n" +
                "  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat' \n" +
                "OUTPUTFORMAT \n" +
                "  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'\n" +
                "LOCATION\n" +
                "  'hdfs://nameservice1/warehouse/tablespace/managed/hive/zjl.db/test_upload02'\n" +
                "TBLPROPERTIES (\n" +
                "  'bucketing_version'='2', \n" +
                "  'transactional'='true', \n" +
                "  'transactional_properties'='insert_only', \n" +
                "  'transient_lastDdlTime'='1695632572')\n" +
                ";";

        System.out.println(ddl);
        System.out.println("----------------------------------------------");
        int startIndex = ddl.indexOf("(");
        String rowFormat = "ROW FORMAT";
        int endIndex;

        endIndex = ddl.indexOf(rowFormat);

        if (endIndex != -1) {
            String substring = ddl.substring(startIndex, endIndex);
            System.out.println(substring);
        } else {
            System.out.println("未找到匹配的内容");
        }

    }
}
