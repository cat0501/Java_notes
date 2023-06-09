package basic.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.CharUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/1 13:34
 */
public class StringTest01 {
    public static void main(String[] args) {


        String testJson = "{\n" +
                "\t\"address\": [],\n" +
                "\t\"column\": [\n" +
                "\t\t\"audit_table_name\",\n" +
                "\t\t\"audit_date\"\n" +
                "\t],\n" +
                "\t\"hadoopConfig\": {\n" +
                "\t\t\"dfs.nameservices\": \"nameservice1\",\n" +
                "\t\t\"dfs.ha.namenodes.nameservice1\": \"cnbjsjztpnn01,cnbjsjztpnn02\",\n" +
                "\t\t\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\": \"cnbjsjztpnn01:8020\",\n" +
                "\t\t\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\": \"cnbjsjztpnn02:8020\",\n" +
                "\t\t\"dfs.client.failover.proxy.provider.nameservice1\": \"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\"\n" +
                "\t},\n" +
                "\t\"connection\": [{\n" +
                "\t\t\"jdbcUrl\": [\n" +
                "\t\t\t\"jdbc:mysql://10.11.14.10:3306/carbon\"\n" +
                "\t\t],\n" +
                "\t\t\"table\": [\n" +
                "\t\t\t\"audit_result\"\n" +
                "\t\t]\n" +
                "\t}],\n" +
                "\t\"username\": \"root\"\n" +
                "}";

        System.out.println(testJson);


        System.out.println("chatGpt 给出的方法如下--------------------------");
        // com.fasterxml.jackson.databind
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(testJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 获取 connection 数组节点
        ArrayNode connectionNode = (ArrayNode) jsonNode.get("connection");

        // 获取 connection 数组中的第一个元素
        ObjectNode connectionElementNode = (ObjectNode) connectionNode.get(0);

        // 获取 table 数组节点
        ArrayNode tableNode = (ArrayNode) connectionElementNode.get("table");

        // 将 table 数组中的值替换为 "zjl"
        tableNode.removeAll();
        tableNode.add("zjl");

        // 将修改后的 JSON 输出
        String modifiedJsonStr = null;
        try {
            //modifiedJsonStr = objectMapper.writeValueAsString(jsonNode);
            modifiedJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(modifiedJsonStr);

        System.out.println("--------------------------------------------------------------------");
        JSONObject jsonObject = JSONObject.parseObject(testJson);

        System.out.println("获取三种不同的值：getString、getJSONObject、getJSONArray");
        // string
        String username = jsonObject.getString("username");
        // object
        String hadoopConfig1 = jsonObject.getJSONObject("hadoopConfig").getString("dfs.ha.namenodes.nameservice1");
        System.out.println(hadoopConfig1);
        // array
        String jdbcUrl = jsonObject.getJSONArray("connection").getJSONObject(0).getString("jdbcUrl");
        System.out.println(jdbcUrl);

        System.out.println("尝试替换其中的值");
        System.out.println("如替换table的值为zjl");

        JSONObject object = JSONObject.parseObject(testJson).getJSONArray("connection")
                .getJSONObject(0)
                .fluentPut("table", "zjl");
        System.out.println(object.toJSONString());


        // 2023-06-09

        String addressSub = "hdfs:" + "jdbc:hive2://10.11.14.30:10000".substring(11);
        addressSub = addressSub.substring(0, addressSub.length() - 5) +"8020";
        System.out.println(addressSub);


        String lyk = "\u0001";
        String  lyk2 = "\\u0001";
        Character character = '\u0001';

        System.out.println(lyk.length());
        System.out.println(lyk2.length());
        System.out.println(String.valueOf(CharUtils.toChar(character)));
        String strToUnicode = strToUnicode("\u0001", false);
        System.out.println(CharUtils.toChar(strToUnicode));
        System.out.println(CharUtils.toChar(lyk));
//        System.out.println(CharUtils.toChar(lyk2));


        //System.out.println(CharUtils.toChar("\\u0001"));
        System.out.println(CharUtils.toChar("\u0001"));
        String str = "\u0001";
        System.out.println("aaaa,bbb,300".replaceAll(",", String.valueOf(CharUtils.toChar("\u0001"))));


        System.out.println(System.currentTimeMillis());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        String formattedDate = currentDate.format(formatter);

        System.out.println(formattedDate);

        String input = "/warehouse/tablespace/managed/hive/datacenter.db/ods_data_test0251/day=20220601/year=2023/month=6";

        String[] parts = input.split("/");
        String dayValue = null;
        String yearValue = null;
        String monthValue = null;

        for (String part : parts) {
            if (part.startsWith("day=")) {
                dayValue = part.substring(4);
            } else if (part.startsWith("year=")) {
                yearValue = part.substring(5);
            } else if (part.startsWith("month=")) {
                monthValue = part.substring(6);
            }
        }

        System.out.println("day value: " + dayValue);
        System.out.println("year value: " + yearValue);
        System.out.println("month value: " + monthValue);


        List<String> myList = new ArrayList<>();
        myList.add("value1");
        myList.add("value2");
        myList.add("value3");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);

        try {
            String jsonString = mapper.writeValueAsString(myList);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


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
