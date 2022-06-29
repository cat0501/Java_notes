import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/15 上午11:22
 */
public class M64 {
}

// 典型的动态规划问题

//时间复杂度 O(M×N) ： 遍历整个 grid 矩阵元素。
//空间复杂度 O(1)O(1) ： 直接修改原矩阵，不使用额外空间。
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0){
                    continue;
                } else if (i == 0){
                    grid[i][j] = grid[i][j - 1] + grid[i][j];
                } else if (j == 0){
                    grid[i][j] = grid[i - 1][j] + grid[i][j];
                } else {
                    grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
                }
            }
        }
        return grid[m - 1][n - 1];
    }




    // 解法2如下
    int[][] memo;

    public int minPathSum2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dp(grid, m - 1, n - 1);
    }

    public int dp(int[][] grid, int i, int j) {
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        memo[i][j] = Math.min(dp(grid, i - 1, j), dp(grid, i, j - 1)) + grid[i][j];
        return memo[i][j];
    }
}