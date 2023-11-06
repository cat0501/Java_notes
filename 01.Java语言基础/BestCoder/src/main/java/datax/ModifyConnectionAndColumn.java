package datax;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Administrator
 */
public class ModifyConnectionAndColumn {
    public static void main(String[] args) {
        /**
         fastjson
         jackson
         */

        String taskDefinitionJson = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"1\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"rdbmsreader\\\",\\\"parameter\\\":{\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"\\\",\\\"connection\\\":[{\\\"querySql\\\":[\\\"SELECT realname FROM bitnei_ods.sys_user\\\"],\\\"jdbcUrl\\\":[\\\"jdbc:hive2://cnbjsjztpdn01:2181,cnbjsjztpnn01:2181,cnbjsjztpnn02:2181/default;password=root;principal=hive/_HOST@SJZT.COM;serviceDiscoveryMode=zooKeeper;user=root;zooKeeperNamespace=hiveserver2\\\"],\\\"table\\\":[\\\"sys_user\\\"]}],\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"haveKerberos\\\":true,\\\"kerberosKeytabFilePath\\\":\\\"/opt/keytab/datacenter.keytab\\\",\\\"kerberosPrincipal\\\":\\\"datacenter@SJZT.COM\\\",\\\"dbType\\\":\\\"2\\\",\\\"dbName\\\":\\\"bitnei_ods\\\",\\\"oriTableName\\\":\\\"sys_user\\\",\\\"partitionInfo\\\":[],\\\"columnBackUp\\\":[{\\\"index\\\":2,\\\"name\\\":\\\"realname\\\",\\\"type\\\":\\\"string\\\"}],\\\"column\\\":[{\\\"index\\\":2,\\\"name\\\":\\\"realname\\\",\\\"type\\\":\\\"string\\\"}],\\\"schema\\\":\\\"\\\"}},\\\"writer\\\":{\\\"name\\\":\\\"mysqlwriter\\\",\\\"parameter\\\":{\\\"writeMode\\\":\\\"append\\\",\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"smc@z9w6\\\",\\\"column\\\":[{\\\"name\\\":\\\"bbb\\\",\\\"type\\\":\\\"varchar(255)\\\"}],\\\"session\\\":[\\\"set session sql_mode='ANSI'\\\"],\\\"connection\\\":[{\\\"jdbcUrl\\\":\\\"jdbc:mysql://10.11.14.10:3306/carbon?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\\\",\\\"table\\\":[\\\"acv\\\"]}],\\\"columnBackUp\\\":[{\\\"name\\\":\\\"bbb\\\",\\\"type\\\":\\\"varchar(255)\\\"}],\\\"dbType\\\":\\\"0\\\",\\\"dbName\\\":\\\"碳资产mysql\\\",\\\"targetTableName\\\":\\\"acv\\\"}}}]}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"partitionInfo\":\"[]\",\"resourceList\":[]}";
        // com.alibaba.fastjson
        JSONObject jsonObject = JSONObject.parseObject(taskDefinitionJson);
        String frontStr = JSONObject.parseObject(taskDefinitionJson).getString("json");

        ObjectMapper frontObjectMapper = new ObjectMapper();
        JsonNode frontJsonNode;
        try {
            frontJsonNode = frontObjectMapper.readTree(frontStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String oriDbType = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("dbType").asText();
        JsonNode jsonNode = null;
        if (Integer.parseInt(oriDbType) == 2){
            jsonNode = frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection").get(0);
            JsonNode querySql = jsonNode.get("querySql");
            System.out.println(querySql);
            JsonNode jdbcUrl = jsonNode.get("jdbcUrl");

            ObjectNode objectNode = frontObjectMapper.createObjectNode();
            ArrayNode arrayNode = frontObjectMapper.createArrayNode();
            objectNode.set("querySql", querySql);
            objectNode.put("jdbcUrl", jdbcUrl);
            // arrayNode = frontObjectMapper.createArrayNode();
            arrayNode.add(objectNode);

            ((ObjectNode) frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter")).replace("connection", arrayNode);


            ArrayNode destColumnArrayNode = (ArrayNode) frontJsonNode.get("job").get("content").get(0).get("writer").get("parameter").get("column");
            ObjectMapper destColumnObjectMapper = new ObjectMapper();
            ArrayNode newArrayNode = destColumnObjectMapper.createArrayNode();
            for (JsonNode node : destColumnArrayNode) {
                newArrayNode.add(node.get("name").asText());
            }
            ((ObjectNode) frontJsonNode.get("job").get("content").get(0).get("writer").get("parameter")).replace("column", newArrayNode);

        }

        String updatedTaskParams = "";
        try {
            updatedTaskParams = frontObjectMapper.writeValueAsString(frontJsonNode);
        } catch (JsonProcessingException e) {
        }
        JSONObject updatedJsonResult = new JSONObject();

        // map.put(key, value);
        updatedJsonResult = jsonObject.fluentPut("json", updatedTaskParams);
        System.out.println(updatedJsonResult.toJSONString());

    }
}
