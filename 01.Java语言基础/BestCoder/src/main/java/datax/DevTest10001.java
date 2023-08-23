package datax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DevTest10001 {

    public static String formatSize(long size) {
        String[] units = { "B", "KB", "MB", "GB", "TB" };
        int unitIndex = 0;

        while (size > 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        return String.format("%d %s", size, units[unitIndex]);
    }



    public static String MAIL_HTML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>数据任务出错报警</title>\n" +
            "    <style>\n" +
            "        .alert {\n" +
            "            color: red;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        .bottom {\n" +
            "            color: red;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1 class=\"alert\">数据任务出错报警</h1>\n" +
            "<p><strong>{{0}}（责任人名称）</strong>，您的线上周期任务在运行时发生错误，以下是出错信息：</p>\n" +
            "<table>\n" +
            "    <tr>\n" +
            "        <td>任务名称：</td>\n" +
            "        <td>{{1}}[taskName]</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>运行类型：</td>\n" +
            "        <td>调度执行</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>节点类型：</td>\n" +
            "        <td>{{2}}[taskType]</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>运行状态：</td>\n" +
            "        <td>失败</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>责任人：</td>\n" +
            "        <td>{{3}}[username]</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>调度时间：</td>\n" +
            "        <td>{{4}}[2023-08-16 00:00:00]</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>开始时间：</td>\n" +
            "        <td>{{5}}[2023-08-16 00:00:00]</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>结束时间：</td>\n" +
            "        <td>{{6}}[2023-08-16 00:00:00]</td>\n" +
            "    </tr>\n" +
            "</table>\n" +
            "<p class=\"bottom\">请及时处理出错任务，避免影响您和他人的数据作业。</p>\n" +
            "<p><strong><a href=\"http://10.11.14.15:8080/operations/real-time-task\">点击此处，</a>跳转到运维中心查看更多详情信息</strong>。</p>\n" +
            "</body>\n" +
            "</html>";

    public static void main(String[] args) {

        // String template = "<p><strong>\"{0} + '（责任人名称）'\">[责任人名称]，您的线上周期任务在运行时发生错误，以下是出错信息：</strong></p>";
        // HashMap<Integer, Object> values = new HashMap<>();
        // values.put(0, "Alice");
        //
        // String message = MessageFormat.format(template, values.get(0));
        // System.out.println(message);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);

        System.out.println(dateString);


        String emailContentReplaced = MAIL_HTML
                .replace("{{0}}", "111")
                .replace("{{1}}", "taskName")
                .replace("{{2}}", "taskType")
                .replace("{{3}}", "startTime")
                .replace("{{4}}", "endTime")
                .replace("{{5}}", "subTime");

        System.out.println(emailContentReplaced);












        // String jsonString = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"select * from bitnei_ods.dws_vin_veh_level limit 2;\\r\\nselect * from bitnei_ods.dws_vin_veh_level limit 3;\",\"preStatements\":[],\"sqlType\":\"0\",\"customConfig\":0,\"datasource\":39,\"displayRows\":10,\"json\":\"\",\"dependence\":{\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resourceFilesList\":[],\"resources\":{\"resourceMap\":{}},\"varPoolMap\":{}},\"localParams\":[],\"resourceList\":[]}";
        //
        // System.out.println(jsonString);
        //
        // jsonString = jsonString.replaceAll("\\\\r\\\\n|[\\r\\n]", "\n");
        //
        // System.out.println(jsonString);
        //
        //
        //
        // long sizeInBytes = 11949622;
        // String formattedSize = formatSize(sizeInBytes);
        // System.out.println(formattedSize);
        //
        //
        //
        //
        // Map<String, Object> map = new HashMap<>();
        // map.put("1", 123456);
        // log.info(map.toString());
        //
        // String frontStr = "{\"job\":{\"content\":[{\"reader\":{\"parameter\":{\"schema\":\"\",\"userPassword\":\"\",\"address\":[],\"dbName\":\"碳资产mysql\",\"column\":[\"id\",\"audit_table_name\",\"audit_date\",\"rule_description\"],\"dbType\":\"0\",\"userName\":\"\",\"splitter\":\",\",\"collectionName\":\"\",\"password\":\"smc@z9w6\",\"connection\":[{\"jdbcUrl\":[\"jdbc:mysql://10.11.14.10:3306/carbon\"],\"table\":[\"audit_result\"]}],\"splitPk\":\"\",\"username\":\"root\"},\"name\":\"mysqlreader\"},\"writer\":{\"parameter\":{\"path\":\"/warehouse/tablespace/managed/hive/bitnei_ods.db/fpk1\",\"fileName\":\"fpk1_2023_08_17\",\"compress\":\"snappy\",\"dbName\":\"数据中台hive中bitnei_ods库\",\"column\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"yearmonth\",\"type\":\"string\"},{\"name\":\"descitycode\",\"type\":\"double\"},{\"name\":\"citycode\",\"type\":\"double\"}],\"defaultFS\":\"hdfs://10.11.14.30:8020\",\"dbType\":\"2\",\"targetTableName\":\"fpk1\",\"writeMode\":\"truncate\",\"fieldDelimiter\":\"\\u0001\",\"splitter\":\",\",\"fileType\":\"parquet\"},\"name\":\"hdfswriter\"}}],\"setting\":{\"errorLimit\":{\"record\":0,\"percentage\":0.02},\"speed\":{\"channel\":3}}}}";
        // log.info("frontStr: {}", frontStr);
        //
        // ObjectMapper objectMapper = new ObjectMapper();
        // JsonNode jsonNode;
        // try {
        //     jsonNode = objectMapper.readTree(frontStr);
        // } catch (JsonProcessingException e) {
        //     throw new RuntimeException(e);
        // }
        //
        // String connection = jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection").get(0).toString();
        // log.info("");
        // log.info("connection: {}", connection);
        // ArrayNode connectionArrayNode = (ArrayNode) jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("connection");
        // log.info(connectionArrayNode.toString());
        //
        // ObjectNode connectionObjectNode = (ObjectNode) (connectionArrayNode.get(0));
        // String bbb = "jdbc:mysql://10.11.14.10:3306/carbon123456";
        // ArrayNode jdbcUrlArrayNode = connectionObjectNode.putArray("jdbcUrl");
        // jdbcUrlArrayNode.add(bbb);
        // log.info(connectionArrayNode.toString());
        //
        // String username = jsonNode.get("job").get("content").get(0).get("reader").get("parameter").get("username").asText();
        // log.info("username: {}", username);
    }
}
