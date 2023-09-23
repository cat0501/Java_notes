package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 20:26
 */

import java.util.Scanner;

public class NMWQ_04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] h = new int[n];
        for (int i = 0; i < n; i++) {
            h[i] = scanner.nextInt();
        }
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            int nextJump = -1;
            for (int j = i + 1; j < n; j++) {
                if (h[j] > h[i]) {
                    if (nextJump == -1 || h[j] < h[nextJump]) {
                        nextJump = j;
                    }
                }
            }
            d[i] = nextJump == -1 ? -1 : h[nextJump];
        }
        for (int i = 0; i < n; i++) {
            System.out.print(d[i] + " ");
        }
    }
}
