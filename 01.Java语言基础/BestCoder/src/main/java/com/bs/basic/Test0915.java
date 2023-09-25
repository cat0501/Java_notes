package com.bs.basic;

/**
 * @author cat
 * @description
 * @date 2023/9/15 18:28
 */
// 你看我输入的测试，没问题，你看对不？？？？？？？？？？
import java.util.Scanner;
    //有一个n*m的网格，起初你在（1,1）处，现在你想走到（n,m）处，且经过的黑色网格尽可能少。请输出最少需要经过多少个黑色网格。网格图是四联通的，
    // 也就是每次只能向上下左右四个相邻的格子移动，且不能走出边界。输入描述：第一行两个正整数n和m，含义如上描述。接下来n行，每行m个数，此数1表示黑色格子，0表示白色格子。
    // ACM模式java解答

    //有啥问题？？？？？？？？才45% 通

    public class Test0915 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int[][] grid = new int[n][m];

            // Read the grid
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }

            int[][] dp = new int[n][m];
            dp[0][0] = grid[0][0];

            // Fill in the first column
            for (int i = 1; i < n; i++) {
                dp[i][0] = dp[i-1][0] + grid[i][0];
            }

            // Fill in the first row
            for (int j = 1; j < m; j++) {
                dp[0][j] = dp[0][j-1] + grid[0][j];
            }

            // Calculate the minimum number of black grids to reach each cell
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
                }
            }

            int minBlackGrids = dp[n-1][m-1];
            System.out.println(minBlackGrids);

            scanner.close();
        }
    }
