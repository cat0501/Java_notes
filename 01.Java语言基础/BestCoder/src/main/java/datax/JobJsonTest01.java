package datax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JobJsonTest01 {
    public static void main(String[] args) {
        String jobJson = "{\"job\":{\"setting\":{\"speed\":{\"channel\":3}},\"content\":[{\"reader\":{\"name\":\"rdbmsreader\",\"parameter\":{\"username\":\"default\",\"password\":\"default\",\"connection\":[{\"querySql\":[\"SELECT id,vin from bitnei_ods.ods_dcsy_data_analysis_forecast_new_result limit 10\"],\"jdbcUrl\":[\"jdbc:hive2://cnbjfcysjztdn07:10000/zjl;AuthMech=1;KrbRealm=SJZT.COM;KrbHostFQDN=_HOST;KrbServiceName=hive\"]}],\"fieldDelimiter\":\"\\u0001\",\"haveKerberos\":true,\"kerberosKeytabFilePath\":\"/opt/keytab/datacenter.keytab\",\"kerberosPrincipal\":\"datacenter@SJZT.COM\"}},\"writer\":{\"name\":\"mysqlwriter\",\"parameter\":{\"writeMode\":\"insert\",\"username\":\"assets\",\"password\":\"\",\"column\":[\"menu_name\",\"query\"],\"session\":[\"set session sql_mode='ANSI'\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://20.20.11.48:3306/assets?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\",\"table\":[\"test102301\"]}]}}}]}}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jobJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串解析异常");
        }

        String partitionInfo = "[{\"partitionName\":\"day\",\"partitionValue\":\"20241026\"},{\"partitionName\":\"day1\",\"partitionValue\":\"202410261\"}]";


        ArrayNode arrayNode = objectMapper.createArrayNode();
        ArrayNode jsonNodes = null;
        try {
            jsonNodes = objectMapper.readValue(partitionInfo, ArrayNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (JsonNode node : jsonNodes) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            String partitionName = node.get("partitionName").asText();
            String partitionValue = node.get("partitionValue").asText();

            objectNode.put("partitionName", partitionName);
            objectNode.put("partitionValue", partitionValue);
            arrayNode.add(objectNode);
        }


        // ObjectNode objectNode = objectMapper.createObjectNode();
        // objectNode.put("partitionName", "day");
        // objectNode.put("partitionValue", "1026");
        //
        // ObjectNode objectNode2 = objectMapper.createObjectNode();
        // objectNode2.put("partitionName", "day1");
        // objectNode2.put("partitionValue", "10261");

        // arrayNode.add(objectNode);
        // arrayNode.add(objectNode2);
        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).put("partitionInfo", arrayNode);

        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).remove("haveKerberos");
        // System.out.println(jsonNode);





    }
}
