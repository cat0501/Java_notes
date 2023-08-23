package com.bs.basic.standard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/13 9:30
 */
public class JsonTest01 {
    public static void main(String[] args) {
        // summary
        /**
         taskParams.replaceAll("\\s+", "");

         com.alibaba.fastjson.JSONObject


         JSONObject.parseObject(jobJsonObject.toString()) 可以去除转义
         */

        String taskParams = "{\n" +
                "\t\"postStatements\": [],\n" +
                "\t\"connParams\": \"\",\n" +
                "\t\"segmentSeparator\": \"\",\n" +
                "\t\"rawScript\": \"\",\n" +
                "\t\"udfs\": \"\",\n" +
                "\t\"type\": \"HIVE\",\n" +
                "\t\"sql\": \"\",\n" +
                "\t\"preStatements\": [],\n" +
                "\t\"sqlType\": \"0\",\n" +
                "\t\"customConfig\": 1,\n" +
                "\t\"displayRows\": 10,\n" +
                "\t\"json\": \"{\\\"job\\\":{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"schema\\\":\\\"\\\",\\\"userPassword\\\":\\\"\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"生产mysql\\\",\\\"column\\\":[\\\"audit_table_name\\\",\\\"audit_date\\\"],\\\"dbType\\\":\\\"0\\\",\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"password\\\":\\\"smc@z9w6\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:mysql://10.11.14.10:3306/carbon\\\"],\\\"table\\\":[\\\"audit_result\\\"]}],\\\"splitPk\\\":\\\"\\\",\\\"username\\\":\\\"root\\\"},\\\"name\\\":\\\"mysqlreader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"fileName\\\":\\\"ods_data_test0601_2023_06_12\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"test_zjl123456\\\",\\\"column\\\":[{\\\"name\\\":\\\"vehiclescore\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"day\\\",\\\"type\\\":\\\"string\\\"}],\\\"dbType\\\":\\\"2\\\",\\\"writeMode\\\":\\\"truncate\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"path\\\":\\\"/warehouse/tablespace/external/hive/zjl.db/ods_data_test0601\\\",\\\"hadoopConfig\\\":{\\\"dfs.ha.namenodes.nameservice1\\\":\\\"cnbjsjztpnn01,cnbjsjztpnn02\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\\\":\\\"cnbjsjztpnn02:8020\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\\\":\\\"cnbjsjztpnn01:8020\\\",\\\"dfs.nameservices\\\":\\\"nameservice1\\\",\\\"dfs.client.failover.proxy.provider.nameservice1\\\":\\\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\\\"},\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"targetTableName\\\":\\\"ods_data_test0601\\\",\\\"fileType\\\":\\\"parquet\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"errorLimit\\\":{\\\"record\\\":0,\\\"percentage\\\":0.02},\\\"speed\\\":{\\\"channel\\\":3}}}}\",\n" +
                "\t\"dependence\": {\n" +
                "\t\t\"varPoolMap\": {},\n" +
                "\t\t\"inputLocalParametersMap\": {},\n" +
                "\t\t\"localParametersMap\": {},\n" +
                "\t\t\"resources\": {\n" +
                "\t\t\t\"resourceMap\": {}\n" +
                "\t\t},\n" +
                "\t\t\"resourceFilesList\": []\n" +
                "\t},\n" +
                "\t\"localParams\": [],\n" +
                "\t\"partitionInfo\": \"[]\",\n" +
                "\t\"resourceList\": []\n" +
                "}";

        System.out.println(taskParams);

        // 去除换行符、制表符和空格
        String output = taskParams.replaceAll("\\s+", "");
        System.out.println(output);

        // JSONObject、JSON 处理 （多此一举）
        //String jsonString = JSONObject.parseObject(taskParams).getString("json");
        //String jsonsGet = JSON.parseObject(jsonString).toString();
        //System.out.println("jsonString-------->" + jsonString);
        //System.out.println("jsonsGet-------->" + jsonsGet);

        // 获取字段值 ----------------------------------------------------------------------------------------
        JSONObject jsonObject = JSONObject.parseObject(taskParams);

        String contentStr = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content").getString(0);

        JSONObject contentJsonObject = JSONObject.parseObject(contentStr);
        String originalType = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getString("dbType");
        String originalName = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getString("dbName");

        String targetType = contentJsonObject.getJSONObject("writer").getJSONObject("parameter").getString("dbType");
        String targetName = contentJsonObject.getJSONObject("writer").getJSONObject("parameter").getString("dbName");

        System.out.println("数据来源" + originalName + ":" + originalType);
        System.out.println("数据去向" + targetName + ":" + targetType);
        // 修改字段值并返回 (修改content-reader-parameter-connection-table的值为 table_name123)-----------------------------

        String tableName = "table_name123";
        //String tableName = "table_name123,456";
        String[] splitTableName = tableName.split(",");

        JSONObject connectionJsonObject = jsonObject.getJSONObject("json").getJSONObject("job")
                .getJSONArray("content")
                .getJSONObject(0)
                .getJSONObject("reader").getJSONObject("parameter")
                .getJSONArray("connection")
                .getJSONObject(0)
                .fluentPut("table", JSONObject.parseArray(JSON.toJSONString(splitTableName)));
        System.out.println(connectionJsonObject.toString());


        JSONArray jsonArray = new JSONArray();
        jsonArray.add(connectionJsonObject);
        JSONObject parameterJsonObject = jsonObject.getJSONObject("json").getJSONObject("job")
                .getJSONArray("content")
                .getJSONObject(0)
                .getJSONObject("reader").getJSONObject("parameter")
                .fluentPut("connection", JSONObject.parseArray(JSONObject.toJSONString(jsonArray)));
        //JSONObject.parseArray("") 该方法期望的是一个合法的 JSON 数组字符串作为输入参数，而不仅仅是一个普通的字符串
        System.out.println(parameterJsonObject.toString());

        JSONObject readerJsonObject = jsonObject.getJSONObject("json").getJSONObject("job")
                .getJSONArray("content")
                .getJSONObject(0)
                .fluentPut("reader", JSONObject.parseObject(parameterJsonObject.toString()));
        System.out.println("------------------------------------>");
        System.out.println(readerJsonObject.toString());

        JSONArray readerJsonArray = new JSONArray();
        readerJsonArray.add(readerJsonObject);
        JSONObject contentJsonObject2 = jsonObject.getJSONObject("json").getJSONObject("job").fluentPut("content",
                JSONObject.parseArray(JSONObject.toJSONString(readerJsonArray)));
        System.out.println(contentJsonObject2.toString());

        JSONObject jobJsonObject = jsonObject.getJSONObject("json").fluentPut("job", JSONObject.parseObject(contentJsonObject2.toString()));
        System.out.println(jobJsonObject.toString());
        // 返回最后结果
        JSONObject jsonResult = new JSONObject();
        jsonResult = jsonObject.fluentPut("json", JSONObject.parseObject(jobJsonObject.toString()));
        System.out.println("替换后的值为------------------------------>");
        System.out.println(jsonResult.toString());

        String a = "10.11.9.7:27019";
        JSONArray readerAddressJsonArray = new JSONArray();
        readerAddressJsonArray.add(a);

        System.out.println(JSONObject.parseArray(JSONObject.toJSONString(readerAddressJsonArray)));


        JSONArray columnJsonArray = new JSONArray();
        String columnOne = "{\n" +
                "\t\t\t\t\t\t\t\"name\": \"authId\",\n" +
                "\t\t\t\t\t\t\t\"type\": \"string\"\n" +
                "\t\t\t\t\t\t}";

        Map<String, String> map = new HashMap<>();
        map.put("name", "authId");
        map.put("type", "string");
        String jsonString = JSON.toJSONString(map);


        JSONObject object = JSON.parseObject(jsonString);
        columnJsonArray.add(object);
        System.out.println(JSONObject.parseArray(JSONObject.toJSONString(columnJsonArray)));

        String co = "[\"audit_table_name\", \"audit_date\"]";
        System.out.println(co);
        List<String> array = JSON.parseArray(co, String.class);
        //String[] splitColumn = co.split(",");
        for (String sc : array) {
            System.out.println(sc);
        }
    }
}
