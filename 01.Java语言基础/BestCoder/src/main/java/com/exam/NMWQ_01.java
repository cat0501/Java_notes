package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 20:26
 */
import java.util.Scanner;

    public class NMWQ_01 {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            int s = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }

            int[][] dp = new int[n+1][s+1];
            for (int i = 0; i <= n; i++) {
                dp[i][0] = 1;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= s; j++) {
                    dp[i][j] = dp[i-1][j];
                    if (j >= nums[i-1]) {
                        dp[i][j] += dp[i-1][j-nums[i-1]];
                    }
                }
            }

            System.out.println(dp[n][s]);
        }
    }
