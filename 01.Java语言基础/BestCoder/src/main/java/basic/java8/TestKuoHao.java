package basic.java8;

import com.alibaba.fastjson.JSONArray;

import java.util.*;
import java.util.stream.Collectors;

public class TestKuoHao {

    public static void main(String[] args) {
        List<String> columnList = Arrays.asList("id", "vin", "uu", "oo", "ll");
        String input = "vin,id,oo";
        String result = getOrderedList(columnList, input);
        System.out.println(result);
    }

    public static String getOrderedList(List<String> orderedList, String input) {
        List<String> inputList = Arrays.stream(input.split(","))
                .map(String::trim)
                .distinct()
                .filter(orderedList::contains)
                .sorted(Comparator.comparingInt(orderedList::indexOf))
                .collect(Collectors.toList());
        return String.join(",", inputList);
    }

    private static String getColumnJsonString(List<String> fieldList) {
        String[] actions = fieldList.toString().substring(1, fieldList.toString().length() - 1).replaceAll("\\s", "").split(",");

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(Arrays.asList(actions));
        return jsonArray.toString();
    }

    private static String getJsonColumn(String columnJsonString) {
        JSONArray jsonArray = JSONArray.parseArray(columnJsonString);
        JSONArray outputJsonArray = new JSONArray();
        for (Object item : jsonArray) {
            JSONArray nestedArray = new JSONArray();
            nestedArray.add(item);
            outputJsonArray.add(nestedArray);
        }
        return outputJsonArray.toJSONString();
    }

}
