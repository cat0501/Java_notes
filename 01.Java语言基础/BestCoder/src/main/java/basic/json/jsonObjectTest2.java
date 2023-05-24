package basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.LinkedHashMap;

/**
 * @description
 * @updateTime 2023/5/23 11:05
 */
public class jsonObjectTest2 {
    public static void main(String[] args) {
        String taskDefinitionJson = "{\n" +
                "\t\"rawScript\": \"#!/bin/bash\\n    #********************************************************************#\",\n" +
                "\t\"localParams\": [],\n" +
                "\t\"resourceList\": [{\n" +
                "\t\t\"id\": 72\n" +
                "\t}]\n" +
                "}";
        LinkedHashMap<String, Object> json2 = JSON.parseObject(taskDefinitionJson, LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject2 = new JSONObject(true);
        jsonObject2.putAll(json2);

        String rawScript = jsonObject2.getString("rawScript");
        System.out.println(rawScript);

        //String resourceList1 = jsonObject2.getJSONArray("resourceList").getString(0);
        String resourceList = jsonObject2.getString("resourceList");
        JSONArray json = JSONArray.parseArray(resourceList);
        System.out.println("----------------------------");


        System.out.println(json);
    }
}
