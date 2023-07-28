package basic.java8;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlusOneDay {
    public static void main(String[] args) {

        List<ArrayList<String>> bbb = new ArrayList<>();


        List<String> aaa = new ArrayList<>();
        aaa.add("[2,4]");
        aaa.add("[2,4,100]");
        aaa.add("[2,4,101]");
        aaa.add("[2,4,101,10009]");
        aaa.add("[2,4,101,10009,19099]");
        aaa.add("[2,5]");

        System.out.println(aaa);


        String fieldName = "_id";
        String fieldName2 = "1_id";
        if (fieldName.startsWith("_")){
            fieldName = "`" + fieldName + "`";
        }
        System.out.println(fieldName);
        System.out.println(fieldName2);


        String strDate = "{\"complementStartDate\":\"2023-05-01 00:00:00\",\"complementEndDate\":\"2023-05-03 00:00:00\"}";

        JSONObject objDate = JSONObject.parseObject(strDate);
        String complementEndDateStr = objDate.getString("complementEndDate");
        String complementStartDateStr = objDate.getString("complementStartDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime complementStartDate = LocalDateTime.parse(complementStartDateStr, formatter);
        LocalDate complementEndDate = LocalDate.parse(complementEndDateStr.substring(0, 10));

        complementEndDate = complementEndDate.plusDays(1); // 加上一天
        ZoneId zoneId = ZoneId.systemDefault();
        Date nextDayEndDate = Date.from(complementEndDate.atStartOfDay(zoneId).toInstant()); // 加上一天后再转成Date对象

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nextDayEndDateStr = sdf.format(nextDayEndDate);

        JSONObject result = new JSONObject();

        result.put("complementEndDate", nextDayEndDateStr);
        result.put("complementStartDate", complementStartDateStr);

        String jsonString = JSONObject.toJSONString(result,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue);
        System.out.println(jsonString);

    }
}