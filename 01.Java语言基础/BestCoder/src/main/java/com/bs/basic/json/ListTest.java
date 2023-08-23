package com.bs.basic.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/5/18 16:41
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> tableName = new ArrayList<>();
        tableName.add("originalTableName");
        System.out.println(tableName);

        List<String> originalTableName = Arrays.asList("originalTableName");

        System.out.println(Arrays.asList("originalTableName"));
    }
}
