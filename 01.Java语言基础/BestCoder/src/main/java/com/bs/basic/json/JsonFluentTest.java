package com.bs.basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cat
 * @description
 * @date 2023/5/31 21:59
 */
public class JsonFluentTest {
    public static void main(String[] args) {
        JSONObject jsonResult = new JSONObject();

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
                "\t\"json\": \"{\\\"job\\\": {\\\"setting\\\": {\\\"speed\\\": {\\\"channel\\\": 3},\\\"errorLimit\\\": {\\\"record\\\": 0,\\\"percentage\\\": 0.02}},\\\"content\\\": [{\\\"reader\\\":{\\\"name\\\":\\\"oraclereader\\\",\\\"parameter\\\":{\\\"schema\\\":\\\"EV\\\",\\\"userPassword\\\":\\\"\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"default_oracle\\\",\\\"column\\\":[],\\\"dbType\\\":5,\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"DEFAULT_TABLE\\\"]}],\\\"splitPk\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"hdfswriter\\\",\\\"parameter\\\":{\\\"path\\\":\\\"/datacenter/default/default_table\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"default_hive\\\",\\\"column\\\":[],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":2,\\\"targetTableName\\\":\\\"default_table\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"parquet\\\"}}}]}}\",\n" +
                "\t\"dependence\": {\n" +
                "\t\t\"inputLocalParametersMap\": {},\n" +
                "\t\t\"localParametersMap\": {},\n" +
                "\t\t\"resourceFilesList\": [],\n" +
                "\t\t\"resources\": {\n" +
                "\t\t\t\"resourceMap\": {}\n" +
                "\t\t},\n" +
                "\t\t\"varPoolMap\": {}\n" +
                "\t},\n" +
                "\t\"localParams\": [],\n" +
                "\t\"resourceList\": []\n" +
                "}";

        System.out.println(taskParams);


        JSONObject jsonObject = JSONObject.parseObject(taskParams);

        String contentJson = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content").getString(0);
        JSONObject contentJsonObject = JSONObject.parseObject(contentJson);

        String connectionJson = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getJSONArray("connection").getString(0);
        JSONObject connectionJsonObject = JSONObject.parseObject(connectionJson);

        String table = connectionJsonObject.getString("table");
        String tableInfo = table.replaceAll("[\\[\\]\"]", "");
        tableInfo = "aaa.bbb";

        String tableSub = tableInfo;
        if (tableInfo.indexOf('.') != -1) {
            tableSub = tableInfo.substring(tableInfo.indexOf('.') + 1);
        }
        //tableSub = "DEFAULT_TABLE";
        tableSub = getStringJson(tableSub);
        System.out.println(tableSub);
        // 返回json串

        String jsons = JSONObject.parseObject(taskParams).getString("json");
        String jsonsGet = JSON.parseObject(jsons).toString();
        String jobs = JSONObject.parseObject(jsonsGet).getString("job");
        String jobsGet = JSON.parseObject(jobs).toString();
        String contents = JSONObject.parseObject(jobsGet).getString("content");
        String contentsGet = JSON.parseArray(contents).get(0).toString();


        JSONObject readersJson = new JSONObject();


        String readers = JSONObject.parseObject(contentsGet).getString("reader");
        String readersParameters = JSONObject.parseObject(readers).getString("parameter");
        String connections = JSONObject.parseObject(readersParameters).getString("connection");
        String contentsGets = JSON.parseArray(connections).get(0).toString();
        //------------------
        System.out.println(tableSub);
        JSONArray tableSubJson = JSONArray.parseArray(tableSub);
        JSONObject jdbcUrls = JSONObject.parseObject(contentsGets)
                .fluentPut("table", tableSubJson);

        JSONObject connectionJson2 = JSONObject.parseObject(readersParameters)
                .fluentPut("connection", JSON.parseArray("[" + JSONObject.toJSONString(jdbcUrls) + "]"));
        readersJson = JSONObject.parseObject(readers)
                .fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(connectionJson2)));

        JSONObject writerJson = JSONObject.parseObject(contentsGet)
                .fluentPut("reader", JSON.parseObject(JSONObject.toJSONString(readersJson)));
        JSONObject contentJsonObj = JSONObject.parseObject(jobsGet).fluentPut("content", JSON.parseArray("[" + JSONObject.toJSONString(writerJson) + "]"));
        JSONObject jobJson = JSONObject.parseObject(jsonsGet).fluentPut("job", JSON.parseObject(JSONObject.toJSONString(contentJsonObj)));

        // 新增字段返回分区信息
        jsonObject.put("newKey", "newValue");
        jsonResult = jsonObject.fluentPut("json", jobJson.toString());

        System.out.println("---------------->");
        System.out.println(jsonResult.toString());
    }

    @Nullable
    private static String getStringJson(String str) {
        List<String> stringList = new ArrayList<>();
        stringList.add(str);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return json;
    }
}
//package com.bs.basic.json;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
////import com.sun.istack.internal.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author cat
// * @description
// * @date 2023/5/31 21:59
// */
//public class JsonFluentTest {
//    public static void main(String[] args) {
//        JSONObject jsonResult = new JSONObject();
//
//        String taskParams = "{\n" +
//                "\t\"postStatements\": [],\n" +
//                "\t\"connParams\": \"\",\n" +
//                "\t\"segmentSeparator\": \"\",\n" +
//                "\t\"rawScript\": \"\",\n" +
//                "\t\"udfs\": \"\",\n" +
//                "\t\"type\": \"HIVE\",\n" +
//                "\t\"sql\": \"\",\n" +
//                "\t\"preStatements\": [],\n" +
//                "\t\"sqlType\": \"0\",\n" +
//                "\t\"customConfig\": 1,\n" +
//                "\t\"displayRows\": 10,\n" +
//                "\t\"json\": \"{\\\"job\\\": {\\\"setting\\\": {\\\"speed\\\": {\\\"channel\\\": 3},\\\"errorLimit\\\": {\\\"record\\\": 0,\\\"percentage\\\": 0.02}},\\\"content\\\": [{\\\"reader\\\":{\\\"name\\\":\\\"oraclereader\\\",\\\"parameter\\\":{\\\"schema\\\":\\\"EV\\\",\\\"userPassword\\\":\\\"\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"default_oracle\\\",\\\"column\\\":[],\\\"dbType\\\":5,\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"DEFAULT_TABLE\\\"]}],\\\"splitPk\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"hdfswriter\\\",\\\"parameter\\\":{\\\"path\\\":\\\"/datacenter/default/default_table\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"default_hive\\\",\\\"column\\\":[],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":2,\\\"targetTableName\\\":\\\"default_table\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"parquet\\\"}}}]}}\",\n" +
//                "\t\"dependence\": {\n" +
//                "\t\t\"inputLocalParametersMap\": {},\n" +
//                "\t\t\"localParametersMap\": {},\n" +
//                "\t\t\"resourceFilesList\": [],\n" +
//                "\t\t\"resources\": {\n" +
//                "\t\t\t\"resourceMap\": {}\n" +
//                "\t\t},\n" +
//                "\t\t\"varPoolMap\": {}\n" +
//                "\t},\n" +
//                "\t\"localParams\": [],\n" +
//                "\t\"resourceList\": []\n" +
//                "}";
//        JSONObject jsonObject = JSONObject.parseObject(taskParams);
//
//        String contentJson = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content").getString(0);
//        JSONObject contentJsonObject = JSONObject.parseObject(contentJson);
//
//        String connectionJson = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getJSONArray("connection").getString(0);
//        JSONObject connectionJsonObject = JSONObject.parseObject(connectionJson);
//
//        String table = connectionJsonObject.getString("table");
//        String tableInfo = table.replaceAll("[\\[\\]\"]", "");
//        tableInfo = "aaa.bbb";
//
//        String tableSub = tableInfo;
//        if (tableInfo.indexOf('.') != -1) {
//            tableSub = tableInfo.substring(tableInfo.indexOf('.') + 1);
//        }
//        //tableSub = "DEFAULT_TABLE";
//        tableSub = getStringJson(tableSub);
//        System.out.println(tableSub);
//        // 返回json串
//
//        String jsons = JSONObject.parseObject(taskParams).getString("json");
//        String jsonsGet = JSON.parseObject(jsons).toString();
//        String jobs = JSONObject.parseObject(jsonsGet).getString("job");
//        String jobsGet = JSON.parseObject(jobs).toString();
//        String contents = JSONObject.parseObject(jobsGet).getString("content");
//        String contentsGet = JSON.parseArray(contents).get(0).toString();
//
//
//        JSONObject readersJson = new JSONObject();
//
//
//        String readers = JSONObject.parseObject(contentsGet).getString("reader");
//        String readersParameters = JSONObject.parseObject(readers).getString("parameter");
//        String connections = JSONObject.parseObject(readersParameters).getString("connection");
//        String contentsGets = JSON.parseArray(connections).get(0).toString();
//        //------------------
//        System.out.println(tableSub);
//        JSONArray tableSubJson = JSONArray.parseArray(tableSub);
//        JSONObject jdbcUrls = JSONObject.parseObject(contentsGets)
//                .fluentPut("table", tableSubJson);
//
//        JSONObject connectionJson2 = JSONObject.parseObject(readersParameters)
//                .fluentPut("connection", JSON.parseArray("[" + JSONObject.toJSONString(jdbcUrls) + "]"));
//        readersJson = JSONObject.parseObject(readers)
//                .fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(connectionJson2)));
//
//        JSONObject writerJson = JSONObject.parseObject(contentsGet)
//                .fluentPut("reader", JSON.parseObject(JSONObject.toJSONString(readersJson)));
//        JSONObject contentJsonObj = JSONObject.parseObject(jobsGet).fluentPut("content", JSON.parseArray("[" + JSONObject.toJSONString(writerJson) + "]"));
//        JSONObject jobJson = JSONObject.parseObject(jsonsGet).fluentPut("job", JSON.parseObject(JSONObject.toJSONString(contentJsonObj)));
//
//        // 新增字段返回分区信息
//        jsonObject.put("newKey", "newValue");
//        jsonResult = jsonObject.fluentPut("json", jobJson.toString());
//
//        System.out.println("---------------->");
//        System.out.println(jsonResult.toString());
//    }
//    //
//    //@Nullable
//    //private static String getStringJson(String str) {
//    //    List<String> stringList = new ArrayList<>();
//    //    stringList.add(str);
//    //    ObjectMapper objectMapper = new ObjectMapper();
//    //    String json = null;
//    //    try {
//    //        json = objectMapper.writeValueAsString(stringList);
//    //    } catch (JsonProcessingException e) {
//    //        System.out.println(e);
//    //    }
//    //    return json;
//    //}
//}
