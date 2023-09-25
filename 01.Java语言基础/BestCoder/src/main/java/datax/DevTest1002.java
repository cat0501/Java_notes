package datax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class DevTest1002 {
    public static void main(String[] args) throws JsonProcessingException {



        // String commandParam = "{\"complementStartDate\":\"2023-09-07 15:50:00\",\"complementEndDate\":\"2023-09-07 15:50:00\",\"schedule_timezone\":\"Asia/Shanghai\"}";
        // String complementStartDate = JSONObject.parseObject(commandParam).getString("complementStartDate");
        // System.out.println(complementStartDate);


        String jsonString = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"1\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"mongodbreader\\\",\\\"parameter\\\":{\\\"address\\\":[\\\"10.10.6.59:27017\\\"],\\\"userName\\\":\\\"\\\",\\\"userPassword\\\":\\\"\\\",\\\"dbName\\\":\\\"mongoDBCell\\\",\\\"collectionName\\\":\\\"STA_SELL\\\",\\\"column\\\":[{\\\"name\\\":\\\"_id\\\",\\\"type\\\":\\\"String\\\"},{\\\"name\\\":\\\"vehNum\\\",\\\"type\\\":\\\"Long\\\"}],\\\"dbType\\\":\\\"12\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:mongodb://10.10.6.59:27017/nationprod\\\"],\\\"table\\\":[\\\"STA_SELL\\\"]}],\\\"dataSourceName\\\":\\\"mongoDBCell\\\",\\\"schema\\\":\\\"\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"hdfswriter\\\",\\\"parameter\\\":{\\\"defaultFS\\\":\\\"hdfs://20.20.11.1:8020\\\",\\\"fileType\\\":\\\"parquet\\\",\\\"path\\\":\\\"/warehouse/tablespace/external/hive/zjl.db/llop01\\\",\\\"fileName\\\":\\\"llop01\\\",\\\"column\\\":[{\\\"name\\\":\\\"unit_name\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"veh_category\\\",\\\"type\\\":\\\"string\\\"}],\\\"writeMode\\\":\\\"truncate\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbType\\\":\\\"2\\\",\\\"dbName\\\":\\\"zjl\\\",\\\"targetTableName\\\":\\\"llop01\\\",\\\"haveKerberos\\\":true,\\\"kerberosKeytabFilePath\\\":\\\"/opt/keytab/datacenter.keytab\\\",\\\"kerberosPrincipal\\\":\\\"datacenter@SJZT.COM\\\"}}}]}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"partitionInfo\":\"[]\",\"resourceList\":[]}";

        String jsonStr = JSONObject.parseObject(jsonString).getString("json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode at = jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("column");
        System.out.println(at);

        List<String> propertyNames = new ArrayList<>();
        for (JsonNode objectNode : at) {
            JsonNode nameNode = objectNode.get("name");
            if (nameNode != null && nameNode.isTextual()) {
                propertyNames.add(nameNode.toString());
            }
        }

        System.out.println(propertyNames);

        // // 将获取的propertyNames替换jsonString中的column值
        ArrayNode columArrayNode = objectMapper.valueToTree(propertyNames);
        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).put("column", columArrayNode);

        String hadoopValue = "{\"dfs.nameservices\":\"nameservice1\",\"dfs.ha.namenodes.nameservice1\":\"cnbjsjztpnn01,cnbjsjztpnn02\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\":\"cnbjsjztpnn01:8020\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\":\"cnbjsjztpnn02:8020\",\"dfs.client.failover.proxy.provider.nameservice1\":\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\"}";
        JSONObject jsonObject = JSONObject.parseObject(hadoopValue);
        ObjectNode hadoopConfigNode = (ObjectNode) objectMapper.readTree(hadoopValue);
        ObjectNode writerParameterNode = (ObjectNode) jsonNode.get("job").get("content").get(0).get("writer").get("parameter");
        writerParameterNode.set("hadoopConfig", hadoopConfigNode);


        System.out.println(jsonNode);
        System.out.println("______________________________________________________________");
        String updatedJsonString = objectMapper.writeValueAsString(jsonNode);
        System.out.println(updatedJsonString);

        System.out.println("______________________________________________________________");

        String oriAddress = "10.10.6.59:27017";
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(oriAddress);

        System.out.println(arrayNode);

        String databaseNameToRefresh = "a";
        String targetTableName = "b";

        String repairSql = "MSCK REPAIR TABLE " + databaseNameToRefresh + "." + targetTableName;
        String analyzeSql = "ANALYZE TABLE " +  databaseNameToRefresh + "." + targetTableName  + " COMPUTE STATISTICS";
        System.out.println(repairSql);
        System.out.println(analyzeSql);
        System.out.println(repairSql + ";" + analyzeSql);




        String taskDefinitionJson = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"1\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"mysqlreader\\\",\\\"parameter\\\":{\\\"username\\\":\\\"assets\\\",\\\"password\\\":\\\"smc@z9w6\\\",\\\"column\\\":[\\\"user_name\\\",\\\"nick_name\\\"],\\\"splitPk\\\":\\\"\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:mysql://20.20.11.48:3306/assets\\\"],\\\"table\\\":[\\\"sys_user\\\"]}],\\\"dbType\\\":\\\"0\\\",\\\"dbName\\\":\\\"assets\\\",\\\"schema\\\":\\\"\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"hdfswriter\\\",\\\"parameter\\\":{\\\"defaultFS\\\":\\\"hdfs://20.20.11.1:8020\\\",\\\"fileType\\\":\\\"parquet\\\",\\\"path\\\":\\\"/warehouse/tablespace/managed/hive/zjl.db/test_10001\\\",\\\"fileName\\\":\\\"test_10001\\\",\\\"column\\\":[{\\\"name\\\":\\\"num\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"id\\\",\\\"type\\\":\\\"string\\\"}],\\\"writeMode\\\":\\\"truncate\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbType\\\":\\\"2\\\",\\\"dbName\\\":\\\"zjl\\\",\\\"targetTableName\\\":\\\"test_10001\\\",\\\"haveKerberos\\\":true,\\\"kerberosKeytabFilePath\\\":\\\"/opt/keytab/datacenter.keytab\\\",\\\"kerberosPrincipal\\\":\\\"datacenter@SJZT.COM\\\"}}}]}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"partitionInfo\":\"[]\",\"resourceList\":[]}";

        // JSONObject jsonObject = JSON.parseObject(taskDefinitionJson);
        // String dbType1 = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content")
        //         .getJSONObject(0).getJSONObject("reader").getJSONObject("parameter")
        //         .getString("dbType");
        //
        // String dbType2 = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content")
        //         .getJSONObject(0).getJSONObject("writer").getJSONObject("parameter")
        //         .getString("dbType");
        //
        // String oriTableName = (String) jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content")
        //         .getJSONObject(0).getJSONObject("reader").getJSONObject("parameter").getJSONArray("connection")
        //         .getJSONObject(0)
        //         .getJSONArray("table")
        //         .get(0);
        //
        // System.out.println(dbType1);
        // System.out.println(dbType2);
        // System.out.println(oriTableName);


        String frontStr = JSONObject.parseObject(taskDefinitionJson).getString("json");
        ObjectMapper frontObjectMapper = new ObjectMapper();
        JsonNode frontJsonNode;
        try {
            frontJsonNode = frontObjectMapper.readTree(frontStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String oriDbType = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("dbType").asText();
        String oriDbName = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("dbName").asText();
        String text = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection").get(0).get("table").get(0).asText();

        String table = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection").get(0).get("table").asText();

        // System.out.println(oriDbType);
        // System.out.println(oriDbName);
        // System.out.println(text);
        System.out.println(table);
    }
}
