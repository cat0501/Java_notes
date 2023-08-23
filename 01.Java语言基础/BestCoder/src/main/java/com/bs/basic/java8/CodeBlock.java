package com.bs.basic.java8;

/**
 * @author cat
 * @description
 * @date 2023/7/23 16:36
 */
public class CodeBlock {
    public static void main(String[] args) {

        System.out.println("普通代码块前的一般语句");

        {
            int x = 11;
            System.out.println("普通代码块中的变量X=" + x);
        }

        {
            int y = 13;
            System.out.println("普通代码块中的变量y=" + y);
        }

        int x = 12;
        System.out.println("主方法中的变量x=" + x);
    }
}
