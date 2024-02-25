package last;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

public class TaskParamsHandle {
    public static void main(String[] args) {
        List<String> aaaaaa = new ArrayList<>();
        aaaaaa.add("last_run_time,last_use_time,pdate,vin");

        List<String> resultList = new ArrayList<>();
        for (String str : aaaaaa) {
            String[] splitValues = str.split(",");
            resultList.addAll(Arrays.asList(splitValues));
        }
        System.out.println(resultList);
        System.out.println(resultList.size());

        System.out.println(aaaaaa.contains("vin"));

        System.out.println("--------------------------------");
        List<String> columnList2 = new ArrayList<String>();
        columnList2.add("a,a2");
        columnList2.add("a1");
        System.out.println(columnList2.contains("a2"));
        System.out.println("--------------------------------");



        Set<String> fieldNames = new HashSet<>();
        fieldNames.add("fieldName".toLowerCase());
        // fieldNames.add("fieldNamE".toLowerCase());
        System.out.println(fieldNames.contains("fieldNamE".toLowerCase().toLowerCase()));

        List<String> permissionStrList = new ArrayList<>();
        permissionStrList.add("write");
        permissionStrList.add("select");

        List<String> distinctSortedPermissionStrList = permissionStrList.stream()
                .distinct()
                .sorted((s1, s2) -> {
                    if (s1.equals("select") && s2.equals("write")) {
                        return -1; // 先 select 后 write
                    } else if (s1.equals("write") && s2.equals("select")) {
                        return 1; // 先 select 后 write
                    } else {
                        return s1.compareTo(s2); // 其他情况按字符串正序排序
                    }
                })
                .collect(Collectors.toList());

        System.out.println(distinctSortedPermissionStrList);



        String aaa = "area_dept,city_dept,drive_mode,entry_date,first_reg,fule_consume_quantity,license_plate,power,power_amount,province_dept,un_name,unit_name,unloadad_weight,update_time,veh_category,veh_category_2,veh_level,veh_model_name";
        List<String> list = new ArrayList<>();
        list.add(aaa);
        String field = "city_dept";
        System.out.println(list.get(0).contains(field));



        List<String> columnList = new ArrayList<>();
        if (columnList.isEmpty()){
            System.out.println("1111111111");
        }



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        System.out.println(formattedDate);
        System.out.println(new Date());

        List<Permission> permissionList = new ArrayList<>();
        Permission p1 = new Permission();
        Permission p2 = new Permission();
        Permission p3 = new Permission();
        Permission p4 = new Permission();
        // p1.setResourceId(21);
        // p2.setResourceId(3);
        // permissionList.add(p1);
        // permissionList.add(p2);
        // permissionList.add(p3);
        // permissionList.add(p4);

        List<Integer> resourceIdCount = permissionList.stream()
                .map(Permission::getResourceId)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .collect(Collectors.toList());
        System.out.println(resourceIdCount);


        String taskParams = "{\"postStatements\":[],\"connParams\":\"\",\"segmentSeparator\":\"\",\"rawScript\":\"\",\"udfs\":\"\",\"type\":\"HIVE\",\"sql\":\"\",\"preStatements\":[],\"sqlType\":\"1\",\"customConfig\":1,\"displayRows\":10,\"json\":\"{\\\"job\\\":{\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":3}},\\\"content\\\":[{\\\"reader\\\":{\\\"name\\\":\\\"rdbmsreader\\\",\\\"parameter\\\":{\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"\\\",\\\"connection\\\":[{\\\"querySql\\\":[\\\"SELECT  `veh_category_2`  FROM bitnei_ods.testa1 WHERE pmonth =  '$[yyyyMMdd-3]' \\\"],\\\"jdbcUrl\\\":[\\\"jdbc:hive2://cnbjsjztpdn01:2181,cnbjsjztpnn01:2181,cnbjsjztpnn02:2181/default;password=root;principal=hive/_HOST@SJZT.COM;serviceDiscoveryMode=zooKeeper;user=root;zooKeeperNamespace=hiveserver2\\\"],\\\"table\\\":[\\\"testa1\\\"]}],\\\"fieldDelimiter\\\":\\\"\\\\u0001\\\",\\\"haveKerberos\\\":true,\\\"kerberosKeytabFilePath\\\":\\\"/opt/keytab/datacenter.keytab\\\",\\\"kerberosPrincipal\\\":\\\"datacenter@SJZT.COM\\\",\\\"dbType\\\":\\\"2\\\",\\\"dbName\\\":\\\"bitnei_ods\\\",\\\"oriTableName\\\":\\\"testa1\\\",\\\"partitionInfo\\\":[{\\\"partitionName\\\":\\\"pmonth\\\",\\\"partitionValue\\\":\\\"$[yyyyMMdd-3]\\\"}],\\\"columnBackUp\\\":[{\\\"index\\\":2,\\\"name\\\":\\\"veh_category_2\\\",\\\"type\\\":\\\"string\\\"}],\\\"column\\\":[\\\"veh_category_2\\\"]}},\\\"writer\\\":{\\\"name\\\":\\\"mysqlwriter\\\",\\\"parameter\\\":{\\\"writeMode\\\":\\\"truncate\\\",\\\"username\\\":\\\"root\\\",\\\"password\\\":\\\"smc@z9w6\\\",\\\"column\\\":[{\\\"name\\\":\\\"bbb\\\",\\\"type\\\":\\\"varchar(255)\\\"}],\\\"session\\\":[\\\"set session sql_mode='ANSI'\\\"],\\\"connection\\\":[{\\\"jdbcUrl\\\":\\\"jdbc:mysql://10.11.14.10:3306/carbon?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\\\",\\\"table\\\":[\\\"acv\\\"]}],\\\"columnBackUp\\\":[{\\\"name\\\":\\\"bbb\\\",\\\"type\\\":\\\"varchar(255)\\\"}],\\\"writeModeTmp\\\":\\\"truncate\\\",\\\"preSql\\\":[\\\"TRUNCATE TABLE carbon.acv\\\"],\\\"dbType\\\":0,\\\"dbName\\\":\\\"碳资产mysql\\\",\\\"targetTableName\\\":\\\"acv\\\"}}}]}}\",\"dependence\":{\"varPoolMap\":{},\"inputLocalParametersMap\":{},\"localParametersMap\":{},\"resources\":{\"resourceMap\":{}},\"resourceFilesList\":[]},\"localParams\":[{\"prop\":\"pmonth\",\"direct\":\"IN\",\"type\":\"VARCHAR\",\"value\":\"$[yyyyMMdd-1]\"}],\"partitionInfo\":\"[{\\\"partitionName\\\":\\\"pmonth\\\",\\\"partitionValue\\\":\\\"$[yyyyMMdd-3]\\\"}]\",\"resourceList\":[]}";
        System.out.println(taskParams);
        JSONObject jsonObject = JSONObject.parseObject(taskParams);

        String frontStr = JSONObject.parseObject(taskParams).getString("partitionInfo");
        System.out.println(frontStr);

        if (StringUtils.isNotBlank(frontStr)){
            // 序列化为list集合
            List<PartitionInfoDto> partitionInfoList = JSON.parseArray(frontStr, PartitionInfoDto.class);
            System.out.println("partitionInfoList: " + partitionInfoList);

            JSONArray jsonArray = new JSONArray();
            for (PartitionInfoDto partitionInfoDto : partitionInfoList) {
                JSONObject jsonObjectTmp = new JSONObject();
                jsonObjectTmp.put("prop", partitionInfoDto.getPartitionName());
                jsonObjectTmp.put("direct", "IN");
                jsonObjectTmp.put("type", "VARCHAR");
                jsonObjectTmp.put("value", partitionInfoDto.getPartitionValue());
                jsonArray.add(jsonObjectTmp);
            }
            System.out.println("localParams: " + jsonArray);

            // 放置到localParams字段
            JSONObject updatedJsonResult;
            updatedJsonResult = jsonObject.fluentPut("localParams", jsonArray);
            System.out.println(updatedJsonResult);
        }

    }
}
