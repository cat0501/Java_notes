package basic.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.MapperFeature.REQUIRE_SETTERS_FOR_GETTERS;

/**
 * @description
 * @updateTime 2023/5/23 11:05
 */
public class jsonObjectTest2 {
    public static void main(String[] args) {

        String scheduleTime = "[{\"complementStartDate\":\"2023-05-22 00:00:00\",\"complementEndDate\":\"2023-05-25 00:00:00\"}]";

        ObjectMapper mapper = new ObjectMapper();
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        try {
            scheduleDtoList = mapper.readValue(scheduleTime, new TypeReference<List<ScheduleDto>>(){});
            scheduleDtoList.forEach(System.out::println);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        scheduleDtoList.forEach(scheduleDto -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String scheduleDtoString = "";
            try {
                scheduleDtoString = objectMapper.writeValueAsString(scheduleDto);
                System.out.println(scheduleDtoString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });


        //String taskDefinitionJson = "{\n" +
        //        "\t\"rawScript\": \"#!/bin/bash\\n    #********************************************************************#\",\n" +
        //        "\t\"localParams\": [],\n" +
        //        "\t\"resourceList\": [{\n" +
        //        "\t\t\"id\": 72\n" +
        //        "\t}]\n" +
        //        "}";
        //LinkedHashMap<String, Object> json2 = JSON.parseObject(taskDefinitionJson, LinkedHashMap.class, Feature.OrderedField);
        //JSONObject jsonObject2 = new JSONObject(true);
        //jsonObject2.putAll(json2);
        //
        //String rawScript = jsonObject2.getString("rawScript");
        //System.out.println(rawScript);
        //
        ////String resourceList1 = jsonObject2.getJSONArray("resourceList").getString(0);
        //String resourceList = jsonObject2.getString("resourceList");
        //JSONArray json = JSONArray.parseArray(resourceList);
        //System.out.println("----------------------------");
        //
        //
        //System.out.println(json);
    }
}



class ScheduleDto {
    private String complementStartDate;
    private String complementEndDate;

    public String getComplementStartDate() {
        return complementStartDate;
    }

    public void setComplementStartDate(String complementStartDate) {
        this.complementStartDate = complementStartDate;
    }

    public String getComplementEndDate() {
        return complementEndDate;
    }

    public void setComplementEndDate(String complementEndDate) {
        this.complementEndDate = complementEndDate;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "complementStartDate='" + complementStartDate + '\'' +
                ", complementEndDate='" + complementEndDate + '\'' +
                '}';
    }
}
