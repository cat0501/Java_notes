package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/18 21:05
 */
import java.util.Scanner;

public class A2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        int n = scanner.nextInt();
        int[] c = new int[n];
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = scanner.nextInt();
            v[i] = scanner.nextInt();
        }
        int[][] dp = new int[s + 1][n + 1];
        for (int i = 1; i <= s; i++) {
            for (int j = 1; j <= n; j++) {
                if (c[j-1] > i) {
                    dp[i][j] = dp[i][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-c[j-1]][j-1] + v[j-1]);
                }
            }
        }
        System.out.println(dp[s][n]);
    }
}
