package algorithm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/20 14:21
 */
public class Test03 {
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add("id");
        list.add("vin");

        if (list.contains("id")){
            System.out.println("yes");
        }

        String[] str = {"2","2","200","xian2","huizhou2"};
        System.out.println(Arrays.toString(str));
        System.out.println(Arrays.toString(str).replace(","," "));
        System.out.println(Arrays.toString(str).replace("[","").replace("]",""));

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
