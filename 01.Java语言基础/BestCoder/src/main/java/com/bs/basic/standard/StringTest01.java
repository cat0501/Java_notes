package com.bs.basic.standard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/1 13:34
 */
public class StringTest01 {
    public static void main(String[] args) {


        String testJson = "{\"job\":{\"content\":[{\"reader\":{\"parameter\":{\"schema\":\"\",\"userPassword\":\"123456\",\"password\":\"ev\",\"address\":[\"10.11.9.7:27019\"],\"dbName\":\"nation-screen\",\"column\":[{\"name\":\"cityCode\",\"type\":\"String\"},{\"name\":\"authId\",\"type\":\"String\"}],\"dbType\":\"12\",\"splitPk\":\"\",\"userName\":\"screen\",\"splitter\":\",\",\"collectionName\":\"SYS_FACTORY\",\"username\":\"ev\"},\"name\":\"mongodbreader\"},\"writer\":{\"parameter\":{\"fileName\":\"ods_data_test0601_2023_06_13\",\"compress\":\"snappy\",\"dbName\":\"test_zjl123456\",\"column\":[{\"name\":\"vehiclescore\",\"type\":\"string\"},{\"name\":\"vin\",\"type\":\"string\"}],\"dbType\":\"2\",\"writeMode\":\"truncate\",\"fieldDelimiter\":\"\\u0001\",\"splitter\":\",\",\"path\":\"/warehouse/tablespace/external/hive/zjl.db/ods_data_test0601\",\"hadoopConfig\":{\"dfs.ha.namenodes.nameservice1\":\"cnbjsjztpnn01,cnbjsjztpnn02\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\":\"cnbjsjztpnn02:8020\",\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\":\"cnbjsjztpnn01:8020\",\"dfs.nameservices\":\"nameservice1\",\"dfs.client.failover.proxy.provider.nameservice1\":\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\"},\"defaultFS\":\"hdfs://10.11.14.30:8020\",\"targetTableName\":\"ods_data_test0601\",\"fileType\":\"parquet\"},\"name\":\"hdfswriter\"}}],\"setting\":{\"errorLimit\":{\"record\":0,\"percentage\":0.02},\"speed\":{\"channel\":3}}}}";

        System.out.println(testJson);

        // MONGODB 差异化字段处理
        // "dbName": "mongodbTable",
        // "column": ["cityCode", "authId"],


        System.out.println("chatGpt 给出的方法如下--------------------------");
        // com.fasterxml.jackson.databind
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(testJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Modify dbName
        JsonNode dbNameNode = jsonNode.at("/job/content/0/reader/parameter/dbName");
        ((ObjectNode) dbNameNode).put("dbName", "new_db_name");


        //// 获取 connection 数组节点
        //ArrayNode connectionNode = (ArrayNode) jsonNode.get("connection");
        //
        //// 获取 connection 数组中的第一个元素
        //ObjectNode connectionElementNode = (ObjectNode) connectionNode.get(0);
        //
        //// 获取 table 数组节点
        //ArrayNode tableNode = (ArrayNode) connectionElementNode.get("table");
        //
        //// 将 table 数组中的值替换为 "zjl"
        //tableNode.removeAll();
        //tableNode.add("zjl");
        //
        //// 将 username的值替换为 "zjl"
        //ObjectNode objectNode = (ObjectNode) jsonNode;
        //objectNode.put("name", "zjl123456");


        // 将修改后的 JSON 输出
        String modifiedJsonStr = null;
        try {
            //modifiedJsonStr = objectMapper.writeValueAsString(jsonNode);
            modifiedJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(modifiedJsonStr);





        //System.out.println("--------------------------------------------------------------------");
        //JSONObject jsonObject = JSONObject.parseObject(testJson);
        //
        //System.out.println("获取三种不同的值：getString、getJSONObject、getJSONArray");
        //// string
        //String username = jsonObject.getString("username");
        //// object
        //String hadoopConfig1 = jsonObject.getJSONObject("hadoopConfig").getString("dfs.ha.namenodes.nameservice1");
        //System.out.println(hadoopConfig1);
        //// com.bs.array
        //String jdbcUrl = jsonObject.getJSONArray("connection").getJSONObject(0).getString("jdbcUrl");
        //System.out.println(jdbcUrl);
        //
        //System.out.println("尝试替换其中的值");
        //System.out.println("如替换table的值为zjl");
        //
        //JSONObject object = JSONObject.parseObject(testJson).getJSONArray("connection")
        //        .getJSONObject(0)
        //        .fluentPut("table", "zjl");
        //System.out.println(object.toJSONString());


//        // 2023-06-09
//
//        String addressSub = "hdfs:" + "jdbc:hive2://10.11.14.30:10000".substring(11);
//        addressSub = addressSub.substring(0, addressSub.length() - 5) +"8020";
//        System.out.println(addressSub);
//
//
//        String lyk = "\u0001";
//        String  lyk2 = "\\u0001";
//        Character character = '\u0001';
//
//        System.out.println(lyk.length());
//        System.out.println(lyk2.length());
//        System.out.println(String.valueOf(CharUtils.toChar(character)));
//        String strToUnicode = strToUnicode("\u0001", false);
//        System.out.println(CharUtils.toChar(strToUnicode));
//        System.out.println(CharUtils.toChar(lyk));
////        System.out.println(CharUtils.toChar(lyk2));
//
//
//        //System.out.println(CharUtils.toChar("\\u0001"));
//        System.out.println(CharUtils.toChar("\u0001"));
//        String str = "\u0001";
//        System.out.println("aaaa,bbb,300".replaceAll(",", String.valueOf(CharUtils.toChar("\u0001"))));
//
//
//        System.out.println(System.currentTimeMillis());
//
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
//        String formattedDate = currentDate.format(formatter);
//
//        System.out.println(formattedDate);
//
//        String input = "/warehouse/tablespace/managed/hive/datacenter.db/ods_data_test0251/day=20220601/year=2023/month=6";
//
//        String[] parts = input.split("/");
//        String dayValue = null;
//        String yearValue = null;
//        String monthValue = null;
//
//        for (String part : parts) {
//            if (part.startsWith("day=")) {
//                dayValue = part.substring(4);
//            } else if (part.startsWith("year=")) {
//                yearValue = part.substring(5);
//            } else if (part.startsWith("month=")) {
//                monthValue = part.substring(6);
//            }
//        }
//
//        System.out.println("day value: " + dayValue);
//        System.out.println("year value: " + yearValue);
//        System.out.println("month value: " + monthValue);
//
//
//        List<String> myList = new ArrayList<>();
//        myList.add("value1");
//        myList.add("value2");
//        myList.add("value3");
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
//
//        try {
//            String jsonString = mapper.writeValueAsString(myList);
//            System.out.println(jsonString);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }


    }


    /**
     * 字符串转 Unicode 编码
     *
     * @param string   原字符串
     * @param halfWith 是否转换半角字符
     * @return 编码后的字符串
     */
    public static String strToUnicode(String string, boolean halfWith) {
        if (string == null || string.isEmpty()) {
            // 传入字符串为空返回原内容
            return string;
        }

        StringBuilder value = new StringBuilder(string.length() << 3);
        String prefix = "\\u", zerofix = "0", unicode;
        char c;
        for (int i = 0, j; i < string.length(); i++) {
            c = string.charAt(i);
            if (!halfWith && c > 31 && c < 127) {
                // 不转换半角字符
                value.append(c);
                continue;
            }
            value.append(prefix);

            // 高 8 位
            j = c >>> 8;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);

            // 低 8 位
            j = c & 0xFF;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);
        }

        return value.toString();
    }


}
