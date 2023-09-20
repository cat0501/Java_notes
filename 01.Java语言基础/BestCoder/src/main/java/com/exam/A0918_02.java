package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/18 18:59
 */

import java.util.Scanner;

public class A0918_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int M = scanner.nextInt();
        int n = scanner.nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = scanner.nextInt();
        }

        int[][] dp = new int[n + 1][M + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= a[i]) {
                    dp[i][j] += dp[i - 1][j - a[i]];
                }
            }
        }

        System.out.println(dp[n][M]);
    }
}






