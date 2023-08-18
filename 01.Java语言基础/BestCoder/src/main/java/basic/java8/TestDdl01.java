package basic.java8;

import org.bson.io.BsonOutput;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/8/8 9:46
 */
public class TestDdl01 {
    public static void main(String[] args) {
        String tri = "SELECT a.vin,a.veh_model_name,\\r\\nCASE WHEN a.rating_volume_str IS NULL AND a.power_amount_str IS NULL THEN CONCAT_WS('_',SUBSTR(a.veh_model_name,1,4),'NULL','NULL')\\r\\n    WHEN a.rating_volume_str IS NULL AND a.power_amount_str IS NOT NULL THEN CONCAT_WS('_',SUBSTR(a.veh_model_name,1,4),'NULL',CAST(a.power_amount_str AS string))\\r\\n    WHEN a.rating_volume_str IS NOT NULL AND a.power_amount_str IS NULL THEN CONCAT_WS('_',SUBSTR(a.veh_model_name,1,4),CAST(a.rating_volume_str AS string),'NULL')\\r\\n    WHEN a.rating_volume_str IS NOT NULL AND a.power_amount_str IS NOT NULL THEN CONCAT_WS('_',SUBSTR(a.veh_model_name,1,4),CAST(a.rating_volume_str AS string),CAST(a.power_amount_str AS string))\\r\\n    END AS veh_level,\\r\\na.rating_volume,a.power_amount,a.battery_type FROM (SELECT vin,veh_model_name,\\r\\nrating_volume AS rating_volume_str, power_amount AS power_amount_str,rating_volume,power_amount,battery_type FROM bitnei_ods.bitnei_rtm_dim_reg_cars_base\\r\\n) a\\r\\nlimit 5\n";

        System.out.println(tri);
        System.out.println(tri.replaceAll("\\\\r\\\\n", " "));

        String ddl = "CREATE EXTERNAL TABLE bitnei_ods.dws_last_used_action (\n" +
                "  vin STRING COMMENT 'vin码',\n" +
                "  last_cha_time STRING COMMENT '最后充电时间',\n" +
                "  last_run_time STRING COMMENT '最后行驶时间',\n" +
                "  last_use_time STRING COMMENT '最后上线时间'\n" +
                ")\n" +
                "PARTITIONED BY (\n" +
                "  pdate STRING COMMENT '分区：yyyy-MM-dd'\n" +
                ")\n" +
                "WITH SERDEPROPERTIES ('serialization.format'='1')\n" +
                "STORED AS TEXTFILE\n" +
                "LOCATION 'hdfs://nameservice1/warehouse/tablespace/external/hive/bitnei_ods.db/dws_last_used_action'\n" +
                "TBLPROPERTIES ('bucketing_version'='2')";

        int startIndex = ddl.indexOf("(");
        int endIndex = ddl.indexOf(")", startIndex);
        String fieldStr = ddl.substring(startIndex, endIndex + 1);
        System.out.println(ddl);

        System.out.println(fieldStr);

        // 如果存在PARTITIONED BY，则提取PARTITIONED BY后面的括号内容
        int partitionedByIndex = ddl.indexOf("PARTITIONED BY");
        if (partitionedByIndex != -1) {
            int secondBracketStartIndex = ddl.indexOf("(", partitionedByIndex);
            int secondBracketEndIndex = ddl.indexOf(")", secondBracketStartIndex);
            String secondContent = ddl.substring(secondBracketStartIndex + 1, secondBracketEndIndex).trim();

            // 拼接两个内容
            fieldStr = fieldStr.substring(0, fieldStr.length() - 1) + ", " + secondContent + ")";
        }
        System.out.println("--------------------");
        System.out.println(fieldStr);
    }
}
