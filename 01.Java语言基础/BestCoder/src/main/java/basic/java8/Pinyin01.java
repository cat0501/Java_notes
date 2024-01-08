package basic.java8;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class Pinyin01 {
    public static void main(String[] args) {
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
