package basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cat
 * @description
 * @date 2023/5/31 21:59
 */
public class JsonFluentTest {
    public static void main(String[] args) {
        JSONObject jsonResult = new JSONObject();

        String taskParams = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\"  ,\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"0\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"schema\\\":\\\"\\\",\\\"userPassword\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"mysql_Test\\\",\\\"column\\\":[\\\"aaa\\\",\\\"id\\\"],\\\"dbType\\\":\\\"13\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"aaa\\\"]}],\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"},\\\"name\\\":\\\"oraclereader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"path\\\":\\\"/warehouse/tablespace/managed/hive/datacenter.db/ods_t_veh_product_info_statistic_all_sy\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"123\\\",\\\"column\\\":[{\\\"name\\\":\\\"vin\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"id\\\"}],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":\\\"2\\\",\\\"targetTableName\\\":\\\"ods_t_veh_product_info_statistic_all_sy\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"parquet\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"errorLimit\\\":{\\\"record\\\":0,\\\"percentage\\\":0.02},\\\"speed\\\":{\\\"channel\\\":3}}}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"resourceList\":[]}";
        JSONObject jsonObject = JSONObject.parseObject(taskParams);

        String contentJson = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content").getString(0);
        JSONObject contentJsonObject = JSONObject.parseObject(contentJson);

        String connectionJson = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getJSONArray("connection").getString(0);
        JSONObject connectionJsonObject = JSONObject.parseObject(connectionJson);

        String table = connectionJsonObject.getString("table");
        String tableInfo = table.replaceAll("[\\[\\]\"]", "");
        tableInfo = "aaa.bbb";

        String tableSub = tableInfo;
        if (tableInfo.indexOf('.') != -1) {
            tableSub = tableInfo.substring(tableInfo.indexOf('.') + 1);
            tableSub = getStringJson(tableSub);
        }
        System.out.println(tableSub);
        // 返回json串

        String jsons = JSONObject.parseObject(taskParams).getString("json");
        String jsonsGet = JSON.parseObject(jsons).toString();
        String jobs = JSONObject.parseObject(jsonsGet).getString("job");
        String jobsGet = JSON.parseObject(jobs).toString();
        String contents = JSONObject.parseObject(jobsGet).getString("content");
        String contentsGet = JSON.parseArray(contents).get(0).toString();


        JSONObject readersJson = new JSONObject();


        String readers = JSONObject.parseObject(contentsGet).getString("reader");
        String readersParameters = JSONObject.parseObject(readers).getString("parameter");
        String connections = JSONObject.parseObject(readersParameters).getString("connection");
        String contentsGets = JSON.parseArray(connections).get(0).toString();
        //------------------
        JSONArray tableSubJson = JSONArray.parseArray(tableSub);
        JSONObject jdbcUrls = JSONObject.parseObject(contentsGets)
                .fluentPut("table", tableSubJson);

        JSONObject connectionJson2 = JSONObject.parseObject(readersParameters)
                .fluentPut("connection", JSON.parseArray("[" + JSONObject.toJSONString(jdbcUrls) + "]"));
        readersJson = JSONObject.parseObject(readers)
                .fluentPut("parameter", JSON.parseObject(JSONObject.toJSONString(connectionJson2)));

        JSONObject writerJson = JSONObject.parseObject(contentsGet)
                .fluentPut("reader", JSON.parseObject(JSONObject.toJSONString(readersJson)));
        JSONObject contentJsonObj = JSONObject.parseObject(jobsGet).fluentPut("content", JSON.parseArray("[" + JSONObject.toJSONString(writerJson) + "]"));
        JSONObject jobJson = JSONObject.parseObject(jsonsGet).fluentPut("job", JSON.parseObject(JSONObject.toJSONString(contentJsonObj)));

        jsonResult = jsonObject.fluentPut("json", jobJson.toString());

        System.out.println("---------------->");
        System.out.println(jsonResult.toString());
    }

    @Nullable
    private static String getStringJson(String str) {
        List<String> stringList = new ArrayList<>();
        stringList.add(str);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return json;
    }
}
