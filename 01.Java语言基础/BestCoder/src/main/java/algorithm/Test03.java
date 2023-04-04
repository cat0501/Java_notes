package algorithm;

import java.util.*;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/20 14:21
 */
public class Test03 {
    public static void main(String[] args) {

        Map<String, String> field = new HashMap<>();
        field.put("fieldType", "varchar");
        //field.put("fieldLength", "");
        field.put("fieldLength", "(20)");

        String fieldType = field.get("fieldType");
        String fieldLength = field.get("fieldLength");

        String typeWithLength = Objects.equals(fieldLength, "null") ? fieldType : fieldType + fieldLength;
        System.out.println(typeWithLength);

        //String str = "CREATE EXTERNAL TABLE 1()";
        //String[] tables = str.split("TABLE");
        //
        //String originalString = "CREATE EXTERNAL TABLE 1()";
        //String delimiter = "TABLE";
        //int delimiterIndex = originalString.indexOf(delimiter); // 获取分隔符的索引位置
        //String resultString = originalString.substring(0, delimiterIndex); // 使用索引位置截取字符串
        //System.out.println(resultString); // 输出 "Hello World"
        //if (resultString.contains("external")){
        //    System.out.println("yes");
        //}

        //String str = "varchar(100)";
        //String str2 = "dec(100,2)";
        //String str3 = "dec<100,2>";
        //
        //int index1 = str.indexOf("(");
        //int index2 = str.indexOf("<");

        //if (index1 != -1) {
        //    String part1 = str.substring(0, index1);
        //    String part2 = str.substring(index1);
        //
        //    System.out.println("Part 1: " + part1);  // 输出 "Hello "
        //    System.out.println("Part 2: " + part2);  // 输出 "(world)"
        //}
        //if (index2 != -1) {
        //    String part1 = str.substring(0, index1);
        //    String part2 = str.substring(index1);
        //
        //    System.out.println("Part 1: " + part1);  // 输出 "Hello "
        //    System.out.println("Part 2: " + part2);  // 输出 "(world)"
        //}


        //ArrayList<String> list = new ArrayList<>();
        //list.add("id");
        //list.add("vin");
        //
        //if (list.contains("id")){
        //    System.out.println("yes");
        //}
        //
        //String[] str = {"2","2","200","xian2","huizhou2"};
        //System.out.println(Arrays.toString(str));
        //System.out.println(Arrays.toString(str).replace(","," "));
        //System.out.println(Arrays.toString(str).replace("[","").replace("]",""));

        //// System.lineSeparator();
        ////
        //String sb = "aaa" +
        //        System.lineSeparator() +
        //        "bbb" +
        //        System.lineSeparator() +
        //        "ccc";
        ////System.out.println(sb + ";");
        //
        //// Common
        ///**
        // When things are uncertain, you can ask the spring breeze.
        //下班打印英语真题
        //
        // */
        //
        //// String API
        //String a = new String("adsadsasw");
        //a = a.substring(0, a.length() - 1);
        //System.out.println(a);
        ///**
        // aaa
        // bbb
        // ccc
        // */














    }

}
