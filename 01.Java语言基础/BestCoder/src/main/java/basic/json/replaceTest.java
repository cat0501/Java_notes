package basic.json;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/12 9:23
 */
public class replaceTest {
        public static void main(String[] args) {
            String input = "{\n\t\"dfs.nameservices\": \"nameservice1\",\n\t\"dfs.ha.namenodes.nameservice1\": \"cnbjsjztpnn01,cnbjsjztpnn02\",\n\t\"dfs.namenode.rpc-address.nameservice1.cnbjsjztpnn01\": \"cnbjsjztpnn01:8020\",\n\t\"dfs.namenode.rpc-address.nameservice2.cnbjsjztpnn02\": \"cnbjsjztpnn02:8020\",\n\t\"dfs.client.failover.proxy.provider.nameservice1\": \"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider\"\n}";

            // 去除换行符、制表符和空格
            String output = input.replaceAll("\\s+", "");

            System.out.println("Input: " + input);
            System.out.println("Output: " + output);
        }

}
