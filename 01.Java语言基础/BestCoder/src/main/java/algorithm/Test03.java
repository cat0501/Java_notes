package algorithm;

import java.util.*;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/20 14:21
 */
public class Test03 {
    public static void main(String[] args) {
        // -------------------------------------------------------------test varchar(20) or varchar
        Map<String, String> field = new HashMap<>();
        field.put("fieldType", "varchar");
        //field.put("fieldLength", "");
        field.put("fieldLength", "(20)");

        String fieldType = field.get("fieldType");
        String fieldLength = field.get("fieldLength");

        String typeWithLength = Objects.equals(fieldLength, "null") ? fieldType : fieldType + fieldLength;
        System.out.println(typeWithLength);
        // 某种可能：不工作了。回家休息。五一回家。
        // 28、29（周六）、30、1、2、3。
        // 总的来说，神张角这个武将在同价位里确实不行，跟神马超神张飞相比确实不行，但不是说这个武将不强，
        // 只是说同价位以及比他低一个价位的武将里，神张角确实不怎么够看的，
        // 摸不到ak空有过牌用处也不是很大，三首的防御技除了克制一些特定武将方面有奇效，也比较克制摸牌白武将，但是面对大过牌武将就费劲了，
        // 一个20宝玉的武将的防御技能克制的范围如此之低属实不咋地，甚至一些摸牌白都可以屯牌蓄爆破三首。
        // 三技能肆军四技能天劫发动时机都比较晚，打二二局基本都是前几轮就已经决胜负了，基本很难拖到中后期，
        // 倒是在军八中有不错的发挥。还有就是打一些拖节奏的武将也是有些优势的。
        //
        //这个武将二二不太行，但是军八还是可以的，三首的防御能力在军八挺够用的，军八局也不是谁都能打到你，能打到你的不一定能凑齐三类型，
        // 而且军八节奏比二二慢，人也多，肆军和天劫肯定也是比二二局好发动。
        //
        //神张角这个武将，可以说是素将杀手，反正欺负素将确实有一手，但是一个价格为20宝玉的武将，到头来得了个这么个称号，确实不值，
        // 还有一点，这个武将真的很吃ak，有ak是爹，没ak就是个有过牌没输出的武将，甚至还过不了多少牌。

        // -------------------------------------------------------------if EXTERNAL table or not
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

        // -------------------------------------------------------------String spit by ( or <
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

        // -------------------------------------------------------------String replace
        //String[] str = {"2","2","200","xian2","huizhou2"};
        //System.out.println(Arrays.toString(str));
        //System.out.println(Arrays.toString(str).replace(","," "));
        //System.out.println(Arrays.toString(str).replace("[","").replace("]",""));

        // -------------------------------------------------------------lineSeparator
        //String sb = "aaa" +
        //        System.lineSeparator() +
        //        "bbb" +
        //        System.lineSeparator() +
        //        "ccc";
        ////System.out.println(sb + ";");

        /// -------------------------------------------------------------String substring
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
