package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/28 19:58
 */

import java.util.Scanner;

public class JSY_T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        int[][] cheese = new int[N][N];
        int[][] dp = new int[N][N];

        // 输入奶酪数量
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                cheese[i][j] = scanner.nextInt();
            }
        }

        // 动态规划
        dp[0][0] = cheese[0][0];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + cheese[i][j];
                } else if (j == i) {
                    dp[i][j] = dp[i - 1][j - 1] + cheese[i][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + cheese[i][j];
                }
            }
        }

        // 找出能获取最多奶酪的路径
        int maxCheese = 0;
        for (int j = 0; j < N; j++) {
            if (dp[N - 1][j] > maxCheese) {
                maxCheese = dp[N - 1][j];
            }
        }

        System.out.println(maxCheese);
    }
}
