package basic.standard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class JsonModificationExample {
    public static void main(String[] args) throws Exception {
        // Original JSON string
        String jsonString = "{\"job\":{\"content\":[{\"reader\":{\"parameter\":{\"schema\":\"\",\"userPassword\":\"123456\",\"password\":\"ev\",\"address\":[\"10.11.9.7:27019\"],\"dbName\":\"nation-screen\",\"column\":[{\"name\":\"cityCode\",\"type\":\"String\"},{\"name\":\"authId\",\"type\":\"String\"}],\"dbType\":\"12\",\"splitPk\":\"\",\"userName\":\"screen\",\"splitter\":\",\",\"collectionName\":\"SYS_FACTORY\",\"username\":\"ev\"},\"name\":\"mongodbreader\"},\"writer\":{\"parameter\":{\"fileName\":\"ods_data_test0601_2023_06_13\",\"compress\":\"snappy\",\"dbName\":\"test_zjl123456\",\"column\":[{\"name\":\"vehiclescore\",\"type\":\"string\"},{\"name\":\"vin\",\"type\":\"string\"}],\"dbType\":\"2\",\"writeMode\":\"truncate\",\"fieldDelimiter\":\"\\u0001\",\"splitter\":\",\",\"path\":\"/warehouse/tablespace/external/hive/zjl.db/ods_data_test0601\",\"hadoopConfig\":{\"dfs.ha.namenodes.nameservice1\":\"cnbjsjztpnn01,cnbjsjztpnn02\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\":\"cnbjsjztpnn02:8020\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\":\"cnbjsjztpnn01:8020\",\"dfs.nameservices\":\"nameservice1\",\"dfs.client.failover.proxy.provider.nameservice1\":\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\"},\"defaultFS\":\"hdfs://10.11.14.30:8020\",\"targetTableName\":\"ods_data_test0601\",\"fileType\":\"parquet\"},\"name\":\"hdfswriter\"}}],\"setting\":{\"errorLimit\":{\"record\":0,\"percentage\":0.02},\"speed\":{\"channel\":3}}}}";

        // Parse the JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        //JsonNode node = ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).get("databaseName");
        //String databaseName = node.asText();
        //System.out.println(databaseName);
        // Get the "dbName" field and update its value
        ((ObjectNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter")).put("dbName", "aaa");

        // Get the "column" array and update its values
        ArrayNode columnArrayNode = (ArrayNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("column");

        // Convert the ArrayNode into a JSON array of names
        List<String> field = new ArrayList<>();
        for (JsonNode node : columnArrayNode) {
            String name = node.get("name").asText();
            field.add(name);
        }
        columnArrayNode.removeAll();

        columnArrayNode.removeAll();
        for (String f : field) {
            columnArrayNode.add(f);
        }

        // Convert the modified JSON back to string
        String modifiedJsonString = objectMapper.writeValueAsString(jsonNode);
        System.out.println(modifiedJsonString);
    }
}

