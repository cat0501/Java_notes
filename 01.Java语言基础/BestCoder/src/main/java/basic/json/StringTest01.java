package basic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.CharUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/1 13:34
 */
public class StringTest01 {
    public static void main(String[] args) {


        String lyk = "\u0001";
        String  lyk2 = "\\u0001";
        Character character = '\u0001';

        System.out.println(lyk.length());
        System.out.println(lyk2.length());
        System.out.println(String.valueOf(CharUtils.toChar(character)));
        String strToUnicode = strToUnicode("\u0001", false);
        System.out.println(CharUtils.toChar(strToUnicode));
        System.out.println(CharUtils.toChar(lyk));
//        System.out.println(CharUtils.toChar(lyk2));


        //System.out.println(CharUtils.toChar("\\u0001"));
        System.out.println(CharUtils.toChar("\u0001"));
        String str = "\u0001";
        System.out.println("aaaa,bbb,300".replaceAll(",", String.valueOf(CharUtils.toChar("\u0001"))));


        System.out.println(System.currentTimeMillis());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        String formattedDate = currentDate.format(formatter);

        System.out.println(formattedDate);

        String input = "/warehouse/tablespace/managed/hive/datacenter.db/ods_data_test0251/day=20220601/year=2023/month=6";

        String[] parts = input.split("/");
        String dayValue = null;
        String yearValue = null;
        String monthValue = null;

        for (String part : parts) {
            if (part.startsWith("day=")) {
                dayValue = part.substring(4);
            } else if (part.startsWith("year=")) {
                yearValue = part.substring(5);
            } else if (part.startsWith("month=")) {
                monthValue = part.substring(6);
            }
        }

        System.out.println("day value: " + dayValue);
        System.out.println("year value: " + yearValue);
        System.out.println("month value: " + monthValue);


        List<String> myList = new ArrayList<>();
        myList.add("value1");
        myList.add("value2");
        myList.add("value3");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);

        try {
            String jsonString = mapper.writeValueAsString(myList);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    /**
     * 字符串转 Unicode 编码
     *
     * @param string   原字符串
     * @param halfWith 是否转换半角字符
     * @return 编码后的字符串
     */
    public static String strToUnicode(String string, boolean halfWith) {
        if (string == null || string.isEmpty()) {
            // 传入字符串为空返回原内容
            return string;
        }

        StringBuilder value = new StringBuilder(string.length() << 3);
        String prefix = "\\u", zerofix = "0", unicode;
        char c;
        for (int i = 0, j; i < string.length(); i++) {
            c = string.charAt(i);
            if (!halfWith && c > 31 && c < 127) {
                // 不转换半角字符
                value.append(c);
                continue;
            }
            value.append(prefix);

            // 高 8 位
            j = c >>> 8;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);

            // 低 8 位
            j = c & 0xFF;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);
        }

        return value.toString();
    }


}
