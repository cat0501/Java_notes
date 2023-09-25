package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/23 10:01
 */

import java.util.Scanner;

public class MeiTuan03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int sum = 0;
        int currentNumber = 1;

        for (int i = 1; i <= n; i++) {
            sum += currentNumber; // 将当前数字加到和变量中
            if (i % 3 == 0) {
                currentNumber--; // 每隔三个数字，当前数字减1
            } else {
                currentNumber++; // 其他情况下，当前数字加1
            }
        }

        System.out.println(sum);
    }
}
