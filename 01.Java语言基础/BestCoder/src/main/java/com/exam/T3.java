package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 21:29
 */

import java.util.Scanner;

public class T3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();

        // 读取输入并构建输入字符串
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                break;
            }
            inputBuilder.append(line);
            inputBuilder.append("\n");
        }

        String input = inputBuilder.toString();
        String[] inputs = input.split("\n");

        int testCaseCount = Integer.parseInt(inputs[0]);
        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 1; i <= 2 * testCaseCount; i += 2) {
            String str = inputs[i];
            String pattern = inputs[i + 1];
            boolean result = isMatch(str, pattern);
            outputBuilder.append(result ? "true" : "false");
            outputBuilder.append("\n");
        }

        String output = outputBuilder.toString();
        System.out.println(output);
    }

    public static boolean isMatch(String str, String pattern) {
        int m = str.length();
        int n = pattern.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == 'a') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2];
                    if (pattern.charAt(j - 2) == str.charAt(i - 1) || pattern.charAt(j - 2) == 'a') {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pattern.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }
}
