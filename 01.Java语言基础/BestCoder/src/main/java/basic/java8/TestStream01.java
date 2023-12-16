package basic.java8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestStream01 {
    public static void main(String[] args) {
        List<Map<String, String>> fieldDtoList = new ArrayList<>();
        Map m1 = new HashMap<>();
        m1.put("fieldName", "state1");
        m1.put("fieldDesc", "1");
        fieldDtoList.add(m1);

        Map m12 = new HashMap<>();
        m12.put("fieldName", "state");
        m12.put("fieldDesc", "1");
        fieldDtoList.add(m12);

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("state");


        Map<Boolean, List<Map<String, String>>> groupFieldMap = fieldDtoList.stream()
                .collect(Collectors.partitioningBy(field -> fieldNameList.contains(field.get("fieldName"))));
        List<Map<String, String>> maps = groupFieldMap.get(true);
        List<Map<String, String>> falseMaps = groupFieldMap.get(false);
        System.out.println(falseMaps);
        System.out.println(maps);

    }
}
