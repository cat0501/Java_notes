package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 19:10
 */

import java.util.Scanner;

     public class SF0920_02 {
         public static void main(String[] args) {
             Scanner scanner = new Scanner(System.in);

             int n = scanner.nextInt();
             int[] a = new int[n];
             int[] b = new int[n];

             for (int i = 0; i < n; i++) {
                 a[i] = scanner.nextInt();
             }

             for (int i = 0; i < n; i++) {
                 b[i] = scanner.nextInt();
             }

             int[][] dp = new int[2*n+1][n+1];

             for (int i = 1; i <= 2*n; i++) {
                 for (int j = 0; j <= n; j++) {
                     if (i % 2 == 1 && j > 0) {
                         dp[i][j] = dp[i-1][j-1] * b[j];
                     } else if (i % 2 == 0 && j < n) {
                         dp[i][j] = Math.max(dp[i-1][j] * b[j+1], dp[i-1][j+1] * b[j+1]);
                     }
                 }
             }

             int ans = 0;
             for (int j = 0; j <= n; j++) {
                 ans += dp[2*n][j];
             }

             System.out.println(ans);
         }
     }
