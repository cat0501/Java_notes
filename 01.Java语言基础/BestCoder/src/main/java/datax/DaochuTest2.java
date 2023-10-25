package datax;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringEscapeUtils;

public class DaochuTest2 {
    public static void main(String[] args) {
        String taskParams = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"1\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"rdbmsreader\\\",\\\"parameter\\\":{\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"\\\",\\\"connection\\\":[{\\\"querySql\\\":[\\\"SELECT num,id FROM bitnei_ods.path04 WHERE day = 1\\\"],\\\"jdbcUrl\\\":[\\\"jdbc:hive2://cnbjsjztpdn01:2181,cnbjsjztpnn01:2181,cnbjsjztpnn02:2181/default;password=root;principal=hive/_HOST@SJZT.COM;serviceDiscoveryMode=zooKeeper;user=root;zooKeeperNamespace=hiveserver2\\\"],\\\"table\\\":[\\\"path04\\\"]}],\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"haveKerberos\\\":true,\\\"kerberosKeytabFilePath\\\":\\\"/opt/keytab/datacenter.keytab\\\",\\\"kerberosPrincipal\\\":\\\"datacenter@SJZT.COM\\\",\\\"dbType\\\":\\\"2\\\",\\\"dbName\\\":\\\"bitnei_ods\\\",\\\"oriTableName\\\":\\\"path04\\\",\\\"schema\\\":\\\"\\\",\\\"column\\\":[{\\\"index\\\":0,\\\"name\\\":\\\"unit_id\\\",\\\"type\\\":\\\"string\\\"}]}},\\\"writer\\\":{\\\"name\\\":\\\"mysqlwriter\\\",\\\"parameter\\\":{\\\"writeMode\\\":\\\"insert\\\",\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"smc@z9w6\\\",\\\"column\\\":[{\\\"name\\\":\\\"ccc\\\",\\\"type\\\":\\\"varchar(255)\\\"}],\\\"session\\\":[\\\"set session sql_mode='ANSI'\\\"],\\\"connection\\\":[{\\\"jdbcUrl\\\":\\\"jdbc:mysql://10.11.14.10:3306/carbon?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\\\",\\\"table\\\":[\\\"acv\\\"]}],\\\"dbType\\\":\\\"0\\\",\\\"dbName\\\":\\\"碳资产mysql\\\",\\\"targetTableName\\\":\\\"acv\\\"}}}]}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"partitionInfo\":\"[]\",\"resourceList\":[]}";
        JSONObject jsonObject = JSONObject.parseObject(taskParams);
        JSONObject jsonResult = new JSONObject();

        String jsons = JSONObject.parseObject(taskParams).getString("json");
        // System.out.println(jsons);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsons);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串解析异常");
        }

        String oriTableName = jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("oriTableName").asText();

        // 写入模式字段处理
        String writeMode = jsonNode.get("job").get("content").get(0).get("writer").get("parameter").get("writeMode").asText();
        if ("replace".equals(writeMode)){
            writeMode = "truncate";
        } else {
            writeMode = "append";
        }
        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("writer").get("parameter")).put("writeMode", writeMode);

        System.out.println(oriTableName);
        ArrayNode connectionArrayNode = (ArrayNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection");

        ObjectNode existingObjectNode = (ObjectNode) connectionArrayNode.get(0);
        existingObjectNode.putArray("table").add(oriTableName);


        // TODO 分区信息处理
        String pt = "[{\"partitionName\":\"day\",\"partitionValue\":\"1\"}]";
        // ObjectMapper objectMapper2 = new ObjectMapper();
        // JsonNode partitionInfoNode = null;
        // try {
        //     partitionInfoNode = objectMapper2.readTree(pt);
        // } catch (JsonProcessingException e) {
        //     throw new RuntimeException(e);
        // }
        // System.out.println(partitionInfoNode);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add(pt);

        System.out.println(arrayNode);
        jsonObject.put("partitionInfo", arrayNode);
        jsonResult = jsonObject.fluentPut("json", jsonNode.toString());
        System.out.println(jsonResult);


    }
}