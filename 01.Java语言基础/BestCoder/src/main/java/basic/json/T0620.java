package basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/20 11:38
 */
public class T0620 {
    public static void main(String[] args) {
        String connectionInfo2 = "{\"user\":\"root\",\"password\":\"root\",\"address\":\"jdbc:hive2://10.11.14.30:10000\",\"database\":\"bitnei_ods\",\"jdbcUrl\":\"jdbc:hive2://10.11.14.30:10000/bitnei_ods\",\"driverClassName\":\"org.apache.hive.jdbc.HiveDriver\",\"validationQuery\":\"select 1\",\"hadoopConfig\":\"{\\n        \\\"dfs.nameservices\\\": \\\"nameservice1\\\",\\n        \\\"dfs.ha.namenodes.nameservice1\\\": \\\"cnbjsjztpnn01,cnbjsjztpnn02\\\",\\n        \\\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\\\": \\\"cnbjsjztpnn01:8020\\\",\\n        \\\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn02\\\": \\\"cnbjsjztpnn02:8020\\\",\\n        \\\"dfs.client.failover.proxy.provider.nameservice1\\\": \\\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\\\"\\n}\"}";
        //String connectionInfo2 = "{\"user\":\"root\",\"password\":\"\",\"address\":\"jdbc:hive2://10.11.14.30:10000\",\"database\":\"lyk\",\"jdbcUrl\":\"jdbc:hive2://10.11.14.30:10000/lyk\",\"driverClassName\":\"org.apache.hive.jdbc.HiveDriver\",\"validationQuery\":\"select 1\"}";
        LinkedHashMap<String, Object> json2 = JSON.parseObject(connectionInfo2, LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject2 = new JSONObject(true);
        jsonObject2.putAll(json2);

        String defaultFS = jsonObject2.getString("defaultFS");
        String hadoopConfig = "";
        System.out.println(jsonObject2.toString());
        hadoopConfig = jsonObject2.getString("hadoopConfig");
        System.out.println(hadoopConfig);
        String address = jsonObject2.getString("address");

        JSONObject hadoopConfigJsonObject = new JSONObject();
        if(StringUtils.isNotBlank(hadoopConfig)){
            String replaceHadoopConfig = hadoopConfig.replaceAll("\\s+", "");
            hadoopConfigJsonObject = JSON.parseObject(replaceHadoopConfig);
        }
        System.out.println(hadoopConfigJsonObject);
    }
}
