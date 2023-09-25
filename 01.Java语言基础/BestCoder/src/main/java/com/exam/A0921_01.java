package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/21 18:57
 */

import java.util.Scanner;

public class A0921_01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            int num = a[i];
            if (num == n) {
                b[i] = 1;
            } else {
                b[i] = num + 1;
            }
            for (int j = 0; j < i; j++) {
                if (b[i] == b[j]) {
                    b[i]++;
                    if (b[i] > n) {
                        b[i] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.print(b[i] + " ");
        }
    }
}
