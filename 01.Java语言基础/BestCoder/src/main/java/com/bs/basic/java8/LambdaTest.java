package com.bs.basic.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author cat
 * @description
 * @date 2022/6/4 下午5:02
 */
public class LambdaTest
{
    public static void main(String[] args)
    {

        List<Object> list = new ArrayList<>();
        //list.set();

        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune" };
        //System.out.println(Arrays.toString(planets));

        System.out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));

        System.out.println("Sorted by length:");
        //Arrays.sort(planets, (first, second) -> first.length() - second.length());
        //System.out.println(Arrays.toString(planets));



        Arrays.sort(planets, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        System.out.println(Arrays.toString(planets));

        //new Comparator<String>() {
        //    @Override
        //    public int compare(String o1, String o2) {
        //        return 0;
        //    }
        //};


        /**
         * 一个动作监听器
         */
        //Timer timer = new Timer(1000, event ->
        //        System.out.println("The time is " + new Date()));
        //timer.start();
        //
        //// keep program running until user selects "OK"
        //JOptionPane.showMessageDialog(null, "Quit program?");
        //System.exit(0);
    }
}
