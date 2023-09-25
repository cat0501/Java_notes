package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/21 18:57
 */

import java.util.Scanner;

public class A0921_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q = 1;  // 修改为固定的3次查询

        String[] sList = new String[q];
        String[] tList = new String[q];
        boolean[] result = new boolean[q];

        //scanner.nextLine();
        // 循环读取输入并处理每个查询
        for (int i = 0; i < q; i++) {
            sList[i] = scanner.nextLine();

            tList[i] = scanner.nextLine();

            result[i] = canTransform(sList[i], tList[i]);
            System.out.println("-------------------");
            System.out.println(result[i]);
        }


        // 打印结果
        for (int i = 0; i < q; i++) {
            if (result[i]) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

    private static boolean canTransform(String s, String t) {
        // 和之前的代码保持不变
        int[] countS = new int[26];
        int[] countT = new int[26];

        for (int i = 0; i < s.length(); i++) {
            countS[s.charAt(i) - 'a']++;
            countT[t.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (countS[i] != countT[i]) {
                return false;
            }
        }

        return true;
    }
}
