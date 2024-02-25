package basic.java8;

import com.alibaba.fastjson.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;


public class Pinyin01 {
    public static void main(String[] args) {
        String policyName = "zjl.hdfs_test1123.b9335e673a3c47f8a9e84f923693ff11";
        int firstDotIndex = policyName.indexOf(".");
        int secondDotIndex = policyName.indexOf(".", firstDotIndex + 1);

        String resultTableName;
        if (secondDotIndex != -1) {
            resultTableName = policyName.substring(firstDotIndex + 1, secondDotIndex);
        } else {
            resultTableName = policyName.substring(firstDotIndex + 1);
        }

        String databasesName = policyName.substring(0, firstDotIndex);
        System.out.println(databasesName);
        System.out.println(resultTableName);







        String aa = "select,write";
        List<String> al = new ArrayList<>();
        al.add("select");
        al.add("write");

        System.out.println(al.toString());

        // 汉字字符串
        String hanzi = "张是";

        // 定义拼音输出格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        // 将汉字转换为拼音字符串
        try {
            String pinyin = PinyinHelper.toHanYuPinyinString(hanzi, format, "", true);
            System.out.println(pinyin);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

    }
}
