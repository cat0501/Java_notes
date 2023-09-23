package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 20:26
 */
import java.util.Scanner;

    public class NMWQ_02 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int testCaseCount = scanner.nextInt();
            scanner.nextLine(); // 读取换行符

            for (int i = 0; i < testCaseCount; i++) {
                String str = scanner.next();
                String pattern = scanner.next();
                boolean result = isMatch(str, pattern);
                System.out.println(result ? "true" : "false");
            }
        }

        public static boolean isMatch(String str, String pattern) {
            int m = str.length();
            int n = pattern.length();

            boolean[][] dp = new boolean[m+1][n+1];
            dp[0][0] = true;

            for (int j = 1; j <= n; j++) {
                if (pattern.charAt(j-1) == '*') {
                    dp[0][j] = dp[0][j-2];
                }
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (str.charAt(i-1) == pattern.charAt(j-1) || pattern.charAt(j-1) == ',') {
                        dp[i][j] = dp[i-1][j-1];
                    } else if (pattern.charAt(j-1) == '*') {
                        dp[i][j] = dp[i][j-2];
                        if (pattern.charAt(j-2) == str.charAt(i-1) || pattern.charAt(j-2) == ',') {
                            dp[i][j] = dp[i][j] || dp[i-1][j];
                        }
                    } else if (pattern.charAt(j-1) == '?') {
                        dp[i][j] = dp[i-1][j-1];
                    }
                }
            }

            return dp[m][n];
        }
    }
