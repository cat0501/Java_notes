package datax;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
public class SongPlayList {
    public static void main(String[] args) {

        String a1 = "{\"user\":\"root\",\"password\":\"\",\"address\":\"jdbc:hive2://20.20.11.1:10000\",\"database\":\"bitnei_ods\",\"jdbcUrl\":\"jdbc:hive2://20.20.11.1:10000/bitnei_ods\",\"driverClassName\":\"org.apache.hive.jdbc.HiveDriver\",\"validationQuery\":\"select 1\",\"other\":\"principal=hive/cnbjfcysjztnn01@SJZT.COM;\",\"props\":{\"principal\":\"hive/cnbjfcysjztnn01@SJZT.COM\"},\"javaSecurityKrb5Conf\":\"\",\"defaultFS\":\"hdfs://20.20.11.2:8020\",\"hadoopConfig\":\"{\\\"dfs.nameservices\\\":\\\"nameservice1\\\",\\\"dfs.ha.namenodes.nameservice1\\\":\\\"cnbjfcysjztnn01,cnbjfcysjztnn02\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjfcysjztnn01\\\":\\\"cnbjfcysjztnn01:8020\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjfcysjztnn02\\\":\\\"cnbjfcysjztnn02:8020\\\",\\\"dfs.client.failover.proxy.provider.nameservice1\\\":\\\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\\\"}\",\"defineType\":1}";
        String a2 = "{\"user\":\"root\",\"password\":\"\",\"address\":\"jdbc:hive2://20.20.11.1:10000\",\"database\":\"bitnei_ods\",\"jdbcUrl\":\"jdbc:hive2://20.20.11.1:10000/bitnei_ods\",\"driverClassName\":\"org.apache.hive.jdbc.HiveDriver\",\"validationQuery\":\"select 1\",\"other\":\"principal=hive/cnbjfcysjztnn01@SJZT.COM;\",\"props\":{\"principal\":\"hive/cnbjfcysjztnn01@SJZT.COM\"},\"javaSecurityKrb5Conf\":\"\",\"defaultFS\":\"hdfs://20.20.11.2:8020\",\"hadoopConfig\":\"{\\\"dfs.nameservices\\\":\\\"nameservice1\\\",\\\"dfs.ha.namenodes.nameservice1\\\":\\\"cnbjfcysjztnn01,cnbjfcysjztnn02\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjfcysjztnn01\\\":\\\"cnbjfcysjztnn01:8020\\\",\\\"dfs.namenode.rpc-address.nameservice1.cnbjfcysjztnn02\\\":\\\"cnbjfcysjztnn02:8020\\\",\\\"dfs.client.failover.proxy.provider.nameservice1\\\":\\\"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\\\"}\",\"defineType\":1}";


        JSONObject parsedObject1 = JSONObject.parseObject(a1);
        JSONObject parsedObject2 = JSONObject.parseObject(a2);
        System.out.println(parsedObject1.toJSONString() + parsedObject2.toJSONString());


        JSONArray dataArray = new JSONArray();

        dataArray.add(JSONObject.parseObject(a1));
        dataArray.add(JSONObject.parseObject(a2));

        JSONObject jsonObject = new JSONObject();
        String dataArrayJSONString = dataArray.toJSONString();
        jsonObject.put("data", dataArrayJSONString.substring(1, dataArrayJSONString.length() - 1));
        System.out.println(dataArrayJSONString);

    }
    public static List<Integer> getRealSongOrder(int[] playlist) {
        List<Integer> result = new ArrayList<>();
        Deque<Integer> queue = new LinkedList<>();
        for (int id : playlist) {
            queue.offer(id);
        }
        while (!queue.isEmpty()) {
            int song = queue.poll();
            result.add(song);
            if (!queue.isEmpty()) {
                int nextSong = queue.poll();
                queue.offer(nextSong);
            }
        }
        return result;
    }
}
