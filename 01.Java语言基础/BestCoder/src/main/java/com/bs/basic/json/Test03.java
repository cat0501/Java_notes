package com.bs.basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Test03 {
    public static void main(String[] args) throws JsonProcessingException {
        JSONObject jsonResult = new JSONObject();
        String taskDefinitionJson = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"0\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"schema\\\":\\\"\\\",\\\"userPassword\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"mysql_Test\\\",\\\"column\\\":[\\\"aaa\\\",\\\"id\\\"],\\\"dbType\\\":\\\"13\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"aaa\\\"]}],\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"},\\\"name\\\":\\\"oraclereader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"path\\\":\\\"/warehouse/tablespace/managed/hive/datacenter.db/ods_t_veh_product_info_statistic_all_sy\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"123\\\",\\\"column\\\":[{\\\"name\\\":\\\"vin\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"id\\\"}],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":\\\"2\\\",\\\"targetTableName\\\":\\\"ods_t_veh_product_info_statistic_all_sy\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"parquet\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"errorLimit\\\":{\\\"record\\\":0,\\\"percentage\\\":0.02},\\\"speed\\\":{\\\"channel\\\":3}}}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"resourceList\":[]}";

        //(1)
        JSONObject jsonObject = JSONObject.parseObject(taskDefinitionJson);
        String jsons = JSONObject.parseObject(taskDefinitionJson).getString("json");
        String jsonsGet = JSON.parseObject(jsons).toString();

        String jobs = JSONObject.parseObject(jsonsGet).getString("job");
        String jobsGet = JSON.parseObject(jobs).toString();

        String contents = JSONObject.parseObject(jobsGet).getString("content");
        String contentsGet = JSON.parseArray(contents).get(0).toString();

        String writers = JSONObject.parseObject(contentsGet).getString("writer");
        String parameters = JSONObject.parseObject(writers).getString("parameter");


        JSONObject newParameters = JSONObject.parseObject(parameters).fluentPut("path", "/1111111111");

        //(2)
        String readers = JSONObject.parseObject(contentsGet).getString("reader");
        String readersParameters = JSONObject.parseObject(readers).getString("parameter");
        String connections = JSONObject.parseObject(readersParameters).getString("connection");


        String jdbcUrlInfo = "jdbc://101.10";
        List<String> stringList = new ArrayList<>();
        stringList.add(jdbcUrlInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stringList);
        JSONArray json2 = JSONArray.parseArray(json);

        String contentsGets = JSON.parseArray(connections).get(0).toString();
        JSONObject jdbcUrls = JSONObject.parseObject(contentsGets).fluentPut("jdbcUrl", json2);

        System.out.println("拼装结果............");




        JSONObject connectionJson = JSONObject.parseObject(readersParameters)
                .fluentPut("connection", JSON.parseArray("[" + JSONObject.toJSONString(jdbcUrls) + "]"))
                .fluentPut("username", "root")
                .fluentPut("password", "1234456");
        JSONObject readersJson = JSONObject.parseObject(readers)
                .fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(connectionJson)))
                .fluentPut("name", "mysqlreader");

        JSONObject parameterJson = JSONObject.parseObject(writers).fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(newParameters)));
        JSONObject writerJson = JSONObject.parseObject(contentsGet)
                .fluentPut("writer", JSON.parseObject(JSONObject.toJSONString(parameterJson)))
                .fluentPut("reader", JSON.parseObject(JSONObject.toJSONString(readersJson)));
        JSONObject contentJson = JSONObject.parseObject(jobsGet).fluentPut("content", JSON.parseArray("[" + JSONObject.toJSONString(writerJson) + "]"));
        JSONObject jobJson = JSONObject.parseObject(jsonsGet).fluentPut("job", JSON.parseObject(JSONObject.toJSONString(contentJson)));


        System.out.println("jobJson----------->" + jobJson.toString());

        //jsonResult = jsonObject.fluentPut("json", jobJson.toString());
        //System.out.println("jsonResult----------->" + jsonResult);















        //String sql = "111111;";
        //sql = sql.substring(0, sql.length() - 1);
        //System.out.println(sql);

        //JSONObject jsonObject = JSONObject.parseObject(taskDefinitionJson);
        //String path = "/111111111111111111111111";
        //JSONObject json = jsonObject.getJSONObject("json");
        //if (json != null){
        //    JSONObject job = json.getJSONObject("job");
        //    job.getJSONObject("content").getJSONObject("writer").getJSONObject("parameter").put("path", path);
        //}
        //
        //System.out.println("new string------->" + jsonObject.toString());

        //String taskDefinitionJson = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"0\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3},\\\"errorLimit\\\":{\\\"record\\\":0,\\\"percentage\\\":0.02}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"oraclereader\\\",\\\"parameter\\\":{\\\"userPassword\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"oracle\\\",\\\"column\\\":[\\\"ID\\\",\\\"PARENT_ID\\\"],\\\"dbType\\\":\\\"5\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"BASE_AREA\\\"]}],\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"hdfswriter\\\",\\\"parameter\\\":{\\\"path\\\":\\\"/datacenter/default/default_table\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"数据中台hive\\\",\\\"column\\\":[{\\\"name\\\":\\\"vin\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"veh_type\\\",\\\"type\\\":\\\"INT\\\"}],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":\\\"2\\\",\\\"targetTableName\\\":\\\"dim_data_analysis_forecast_new_sy\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"orc\\\"}}}]}}\",\"localParams\":[],\"resourceList\":[]}";

        //JSONObject jsonObjects = JSON.parseObject(taskDefinitionJson);
        //JSONObject json = jsonObjects.getJSONObject("json");
        //JSONObject job = json.getJSONObject("job");
        //job.getJSONArray("content").getJSONObject(0).getJSONObject("writer").getJSONObject("parameter").put("path", "/11111");
        //
        //System.out.println("result------->" + jsonObjects.fluentPut("json", json.toString()).toString());


        //.getJSONObject("parameter").put("path", "/1111111111111");








        //System.out.println("-------------------------------------------->");
        //
        //JSONObject jsonObject = JSONObject.parseObject(str);
        //String jsons = JSONObject.parseObject(str).getString("json");
        //System.out.println("jsons------>" + jsons);
        //String jsonsGet = JSON.parseObject(jsons).toString();
        //System.out.println("jsonsGet------>" + jsonsGet);
        //
        //String jobs = JSONObject.parseObject(jsonsGet).getString("job");
        //String jobsGet = JSON.parseObject(jobs).toString();
        //
        //String contents = JSONObject.parseObject(jobsGet).getString("content");
        ////String contentsGet = JSON.parseArray(contents).get(0).toString();
        //
        //String writers = JSONObject.parseObject(contentsGet).getString("writer").toString();
        //String parameters = JSONObject.parseObject(writers).getString("parameter").toString();
        //String paths = JSONObject.parseObject(parameters).getString("path").toString();
        //
        //System.out.println(paths);
        //JSONObject newData = JSONObject.parseObject(parameters).fluentPut("path", "111111");
        //System.out.println(newData.toJSONString());
        //
        //System.out.println("拼装结果............");
        //JSONObject newRanks = JSONObject.parseObject(writers).fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(newData)));//这个[]括号，是因为当时拿的数据结构是数组，所以需要这种方式转换成数组，暂时没想到更好的方式
        //System.out.println("parameter--------------------->" + newRanks.toJSONString());
        //
        //JSONObject newPages = JSONObject.parseObject(contentsGet).fluentPut("writer", JSON.parseObject(JSONObject.toJSONString(newRanks)));
        //System.out.println("writer--------------------->" + newPages.toJSONString());
        //
        //JSONObject newPages2 = JSONObject.parseObject(jobsGet).fluentPut("content", JSON.parseArray("[" + JSONObject.toJSONString(newPages) + "]"));
        //System.out.println("content--------------------->" + newPages2.toJSONString());
        //
        //JSONObject newPages3 = JSONObject.parseObject(jsonsGet).fluentPut("job", JSON.parseObject(JSONObject.toJSONString(newPages2)));
        //System.out.println("job--------------------->" + newPages3.toJSONString());
        //
        //JSONObject newPages4 = jsonObject.fluentPut("json", newPages3.toString());
        //
        //System.out.println("task_params--------------------->" + newPages4.toString());













        // 今年五一期间，十大热门到达城市，分别为：北京、广州、上海、成都、武汉、杭州、西安、南京、长沙、重庆。
        //
        //String lo = "STRINGddd";
        //System.out.println(lo.toLowerCase());

        //String pathTmp = "hdfs://nameservice1/datacenter/bitnei_dim/dim_veh_model_zn";
        //int index1 = pathTmp.indexOf("1");
        //if (index1 != -1) {
        //    String part1 = pathTmp.substring(0, index1);
        //    String part2 = pathTmp.substring(index1 + 1);
        //
        //    System.out.println("Part 1: " + part1);  // 输出 "Hello "
        //    System.out.println("Part 2: " + part2);  // 输出 "(world)"
        //}
        //
        //System.out.println("--------------------------------------");


        //System.out.println(jsonObject.toString());

        //jsonObject.getJSONObject("json")
        //        .getJSONObject("job")
        //        .getJSONObject("content")
        //        .getJSONObject("writer")
        //        .getJSONObject("parameter")
        //        .getJSONObject("path")
        //        .fluentPut("path", "1");


        //String str = "varchar(20)";
        //byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        //for (int i = 0; i < bytes.length; i++) {
        //    System.out.println(bytes[i]);
        //    //System.out.println((char) bytes[i]);
        //    System.out.println("-------------------------");
        //}
        //



        // -------------------------------------------------------------test varchar(20) or varchar
        //Map<String, String> field = new HashMap<>();
        //field.put("fieldType", "varchar");
        ////field.put("fieldLength", "");
        ///**
        //
        // */
        //
        //field.put("fieldLength", "(20)");
        //
        //String fieldType = field.get("fieldType");
        //String fieldLength = field.get("fieldLength");
        //
        //if (fieldLength  == null){
        //    System.out.println("error...");
        //}
        //String typeWithLength = Objects.equals(fieldLength, "null") ? fieldType : fieldType + fieldLength;
        //System.out.println(typeWithLength);
        // -------------------------------------------------------------if EXTERNAL table or not
        //String str = "CREATE EXTERNAL TABLE 1()";
        //String[] tables = str.split("TABLE");
        //
        //String originalString = "CREATE EXTERNAL TABLE 1()";
        //String delimiter = "TABLE";
        //int delimiterIndex = originalString.indexOf(delimiter); // 获取分隔符的索引位置
        //String resultString = originalString.substring(0, delimiterIndex); // 使用索引位置截取字符串
        //System.out.println(resultString); // 输出 "Hello World"
        //if (resultString.contains("external")){
        //    System.out.println("yes");
        //}

        // -------------------------------------------------------------String spit by ( or <
        //String str = "varchar(100)";
        //String str2 = "dec(100,2)";
        //String str3 = "dec<100,2>";
        //
        //int index1 = str.indexOf("(");
        //int index2 = str.indexOf("<");

        //if (index1 != -1) {
        //    String part1 = str.substring(0, index1);
        //    String part2 = str.substring(index1);
        //
        //    System.out.println("Part 1: " + part1);  // 输出 "Hello "
        //    System.out.println("Part 2: " + part2);  // 输出 "(world)"
        //}
        //if (index2 != -1) {
        //    String part1 = str.substring(0, index1);
        //    String part2 = str.substring(index1);
        //
        //    System.out.println("Part 1: " + part1);  // 输出 "Hello "
        //    System.out.println("Part 2: " + part2);  // 输出 "(world)"
        //}

        // -------------------------------------------------------------String replace
        //String[] str = {"2","2","200","xian2","huizhou2"};
        //System.out.println(Arrays.toString(str));
        //System.out.println(Arrays.toString(str).replace(","," "));
        //System.out.println(Arrays.toString(str).replace("[","").replace("]",""));

        // -------------------------------------------------------------lineSeparator
        //String sb = "aaa" +
        //        System.lineSeparator() +
        //        "bbb" +
        //        System.lineSeparator() +
        //        "ccc";
        ////System.out.println(sb + ";");

        /// -------------------------------------------------------------String substring
        //String a = new String("adsadsasw");
        //a = a.substring(0, a.length() - 1);
        //System.out.println(a);
        ///**
        // aaa
        // bbb
        // ccc
        // */
    }

}
