package datax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DaochuTest {
    public static void main(String[] args) {
        // ObjectMapper objectMapper = new ObjectMapper();
        // String jdbcUrl = "jdbc:mysql://10.10.11.64:4000/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        // JsonNode tableNode = objectMapper.createArrayNode().add("data_center_analysis_forecast_new");
        //
        // JsonNode jsonArray = objectMapper.createObjectNode()
        //         .put("jdbcUrl", jdbcUrl)
        //         .set("table", tableNode);
        // JsonNode jsonNode = objectMapper.createArrayNode().add(jsonArray);
        //
        // System.out.println(jsonNode.toString());





        String frontStr = "{\"job\":{\"setting\":{\"speed\":{\"channel\":3}},\"content\":[{\"reader\":{\"name\":\"rdbmsreader\",\"parameter\":{\"username\":\"default\",\"password\":\"default\",\"connection\":[{\"querySql\":[\"SELECT id,vin from bitnei_ods.ods_dcsy_data_analysis_forecast_new_result limit 10\"],\"jdbcUrl\":[\"jdbc:hive2://cnbjfcysjztdn07:10000/zjl;AuthMech=1;KrbRealm=SJZT.COM;KrbHostFQDN=_HOST;KrbServiceName=hive\"]}],\"fieldDelimiter\":\"\\u0001\",\"haveKerberos\":true,\"kerberosKeytabFilePath\":\"/opt/keytab/datacenter.keytab\",\"kerberosPrincipal\":\"datacenter@SJZT.COM\"}},\"writer\":{\"name\":\"mysqlwriter\",\"parameter\":{\"writeMode\":\"insert\",\"username\":\"assets\",\"password\":\"\",\"column\":[\"menu_name\",\"query\"],\"session\":[\"set session sql_mode='ANSI'\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://20.20.11.48:3306/assets?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\",\"table\":[\"test102301\"]}]}}}]}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(frontStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON字符串解析异常");
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        String querySql = "1123";
        String jdbcUrl = "john.doe@example.com";
        String[] querySqlArray = {querySql};
        String[] jdbcUrlArray = {jdbcUrl};

        arrayNode.add(querySqlArray[0]);
        objectNode.put("querySql", arrayNode);

        arrayNode = objectMapper.createArrayNode();
        arrayNode.add(jdbcUrlArray[0]);
        objectNode.put("jdbcUrl", arrayNode);

        arrayNode = objectMapper.createArrayNode();
        arrayNode.add(objectNode);
        System.out.println(arrayNode);

        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).replace("connection", arrayNode);

        System.out.println(jsonNode);



        // ArrayNode columnArrayNode = (ArrayNode) frontJsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("column");
        // System.out.println(columnArrayNode.toString());
        //
        //
        // StringBuilder querySql = new StringBuilder("SELECT ");
        // int length = columnArrayNode.size();
        // System.out.println(length);
        // for (int i = 0; i < length; i++) {
        //     JsonNode jsonNode = columnArrayNode.get(i);
        //     String name = jsonNode.get("name").asText();
        //
        //     System.out.println(name);
        //     querySql.append(name);
        //     if (i < length - 1) {
        //         querySql.append(",");
        //     }
        // }
        // System.out.println(querySql);




        // ArrayNode destColumnArrayNode = (ArrayNode) frontJsonNode.get("job").get("content").get(0).get("writer").get("parameter").get("column");
        // ObjectMapper objectMapper = new ObjectMapper();
        // ArrayNode newArrayNode = objectMapper.createArrayNode();
        // for (JsonNode node : destColumnArrayNode) {
        //     newArrayNode.add(node.get("name").asText());
        // }
        //
        // System.out.println(newArrayNode.toString());
    }
}