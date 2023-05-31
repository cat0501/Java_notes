package basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/5/18 14:59
 */
public class jsonObjectTest {
    public static void main(String[] args) {
        String taskDefinitionJson = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"0\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"schema\\\":\\\"\\\",\\\"userPassword\\\":\\\"\\\",\\\"password\\\":\\\"ev\\\",\\\"address\\\":[],\\\"dbName\\\":\\\"mysql_Test\\\",\\\"column\\\":[\\\"aaa\\\",\\\"id\\\"],\\\"dbType\\\":\\\"13\\\",\\\"connection\\\":[{\\\"jdbcUrl\\\":[\\\"jdbc:oracle:thin:@//10.11.3.33:1521/evmsc\\\"],\\\"table\\\":[\\\"aaa\\\"]}],\\\"userName\\\":\\\"\\\",\\\"splitter\\\":\\\",\\\",\\\"collectionName\\\":\\\"\\\",\\\"username\\\":\\\"ev\\\"},\\\"name\\\":\\\"oraclereader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"path\\\":\\\"/warehouse/tablespace/managed/hive/datacenter.db/ods_t_veh_product_info_statistic_all_sy\\\",\\\"fileName\\\":\\\"ppp\\\",\\\"compress\\\":\\\"snappy\\\",\\\"dbName\\\":\\\"123\\\",\\\"column\\\":[{\\\"name\\\":\\\"vin\\\",\\\"type\\\":\\\"string\\\"},{\\\"name\\\":\\\"id\\\"}],\\\"defaultFS\\\":\\\"hdfs://10.11.14.30:8020\\\",\\\"dbType\\\":\\\"2\\\",\\\"targetTableName\\\":\\\"ods_t_veh_product_info_statistic_all_sy\\\",\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"splitter\\\":\\\",\\\",\\\"fileType\\\":\\\"parquet\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"errorLimit\\\":{\\\"record\\\":0,\\\"percentage\\\":0.02},\\\"speed\\\":{\\\"channel\\\":3}}}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[],\"resourceList\":[]}";


        System.out.println(taskDefinitionJson);
        // com.alibaba.fastjson.JSONObject
        // com.alibaba.fastjson.JSON

        //JSONObject jsonObject = JSONObject.parseObject(taskDefinitionJson);
        String jsons = JSONObject.parseObject(taskDefinitionJson).getString("json");
        String jsonsGet = JSON.parseObject(jsons).toString();
        //System.out.println("jsons-------->" + jsons);
        //System.out.println("jsonsGet-------->" + jsonsGet);

        String jobs = JSONObject.parseObject(jsonsGet).getString("job");
        String jobsGet = JSON.parseObject(jobs).toString();
        //System.out.println("jobs-------->" + jobs);
        //System.out.println("jobsGet-------->" + jobsGet);

        String contents = JSONObject.parseObject(jobsGet).getString("content");
        String contentsGet = JSON.parseArray(contents).get(0).toString();

        String writers = JSONObject.parseObject(contentsGet).getString("writer");
        String parameters = JSONObject.parseObject(writers).getString("parameter");
        String dbName = JSONObject.parseObject(parameters).getString("dbName");
        String dbType = JSONObject.parseObject(parameters).getString("dbType");

        String readers = JSONObject.parseObject(contentsGet).getString("reader");
        String readersParameters = JSONObject.parseObject(readers).getString("parameter");
        String originalDbName = JSONObject.parseObject(readersParameters).getString("dbName");
        String originalDbType = JSONObject.parseObject(readersParameters).getString("dbType");



        //-----------------------------------------------------------
        JSONObject jsonObject = JSONObject.parseObject(taskDefinitionJson);
        String contentStr = jsonObject.getJSONObject("json").getJSONObject("job").getJSONArray("content").getString(0);

        JSONObject contentJsonObject = JSONObject.parseObject(contentStr);
        String originalType = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getString("dbType");
        String originalName = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getString("dbName");
        String schema = contentJsonObject.getJSONObject("reader").getJSONObject("parameter").getString("schema");

        String targetType = contentJsonObject.getJSONObject("writer").getJSONObject("parameter").getString("dbType");
        String targetName = contentJsonObject.getJSONObject("writer").getJSONObject("parameter").getString("dbName");

        System.out.println(originalName + ":" + originalType);
        System.out.println(targetName + ":" + targetType);
        System.out.println("-----------------------------------------------");
        System.out.println("dbName-------->" + dbName);
        System.out.println("dbType-------->" + dbType);
        System.out.println("originalDbName-------->" + originalDbName);
        System.out.println("originalDbType-------->" + originalDbType);
        System.out.println(taskDefinitionJson);
    }
}
