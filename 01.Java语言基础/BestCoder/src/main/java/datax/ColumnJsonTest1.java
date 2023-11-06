package datax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ColumnJsonTest1 {
    public static void main(String[] args) {
        String jobJson = "{\"job\":{\"setting\":{\"speed\":{\"channel\":3}},\"content\":[{\"reader\":{\"name\":\"rdbmsreader\",\"parameter\":{\"username\":\"root\",\"password\":\"\",\"connection\":[{\"querySql\":[\"SELECT unit_id,state FROM bitnei_ods.path04 WHERE day = 23456\"],\"jdbcUrl\":[\"jdbc:hive2://cnbjsjztpdn01:2181,cnbjsjztpnn01:2181,cnbjsjztpnn02:2181/default;password=root;principal=hive/_HOST@SJZT.COM;serviceDiscoveryMode=zooKeeper;user=root;zooKeeperNamespace=hiveserver2\"],\"table\":[\"path04\"]}],\"fieldDelimiter\":\"\\u0001\",\"haveKerberos\":true,\"kerberosKeytabFilePath\":\"/opt/keytab/datacenter.keytab\",\"kerberosPrincipal\":\"datacenter@SJZT.COM\",\"dbType\":\"2\",\"dbName\":\"bitnei_ods\",\"oriTableName\":\"path04\",\"partitionInfo\":[{\"partitionName\":\"day\",\"partitionValue\":\"23456\"}],\"columnBackUp\":[{\"index\":0,\"name\":\"unit_id\",\"type\":\"string\"},{\"index\":1,\"name\":\"state\",\"type\":\"string\"}],\"column\":[{\"index\":0,\"name\":\"unit_id\",\"type\":\"string\"},{\"index\":1,\"name\":\"state\",\"type\":\"string\"}]}},\"writer\":{\"name\":\"mysqlwriter\",\"parameter\":{\"writeMode\":\"truncate\",\"username\":\"root\",\"password\":\"smc@z9w6\",\"column\":[{\"name\":\"bbb\",\"type\":\"varchar(255)\"},{\"name\":\"ccc\",\"type\":\"varchar(255)\"}],\"session\":[\"set session sql_mode='ANSI'\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://10.11.14.10:3306/carbon?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\",\"table\":[\"acv\"]}],\"columnBackUp\":[{\"name\":\"bbb\",\"type\":\"varchar(255)\"},{\"name\":\"ccc\",\"type\":\"varchar(255)\"}],\"preSql\":[\"TRUNCATE TABLE carbon.acv\"],\"dbType\":0,\"dbName\":\"碳资产mysql\",\"targetTableName\":\"acv\"}}}]}}";

        //

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jobJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串解析异常");
        }
        System.out.println(jsonNode);

        ArrayNode oriColumnArrayNode = (ArrayNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("columnBackUp");
        System.out.println(oriColumnArrayNode);

        ArrayNode columnArrayNode = objectMapper.createArrayNode();
        for (JsonNode node : oriColumnArrayNode) {
            String name = node.get("name").asText();
            columnArrayNode.add(name);
        }
        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).put("column", columnArrayNode);
        System.out.println(jsonNode);
    }
}
