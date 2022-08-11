import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/12 下午10:04
 */
public class M931 {
    public static void main(String[] args) {

    }



    static class Solution {
        public int minFallingPathSum(int[][] matrix) {
            int n = matrix.length;
            int res = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                res = Math.min(res, dp(matrix, n - 1, i));
            }
            return res;
        }

        int dp(int[][] matrix, int i, int j) {
            // 非法索引检查
            if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length) {
                return 999;
            }

            // base case
            if (i == 0){
                return matrix[i][j];
            }
            // 状态转移
            return matrix[i][j] + min(
              dp(matrix, i - 1, j - 1),
              dp(matrix, i - 1, j),
              dp(matrix, i - 1, j + 1)
            );

        }

        private int min(int dp, int dp1, int dp2) {
            return Math.min(dp, Math.min(dp1, dp2));
        }
    }
}




class Solution931 {

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int res = Integer.MAX_VALUE;

        // (1)添加备忘录
        memo = new int[n][n];
        for (int[] ints : memo) {
            Arrays.fill(ints, 66666);
        }

        // 终点可能在任何一列
        for (int i = 0; i < n; i++) {
            res = Math.min(res, dp(matrix, n - 1, i));
        }
        return res;
    }
    // 备忘录
    int[][] memo;

    int dp(int[][] matrix, int i, int j) {
        // 非法索引检查
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length) {
            return 99999;
        }

        // base case
        if (i == 0){
            return matrix[0][j];
        }

        // (2)查询备忘录
        if (memo[i][j] != 66666) {
            return memo[i][j];
        }

        // 状态转移
        // (3)记录到被备忘录
        memo[i][j] =  matrix[i][j] + min(
                dp(matrix, i - 1, j),
                dp(matrix, i - 1, j - 1),
                dp(matrix, i - 1, j + 1)
        );
        return memo[i][j];
    }

    private int min(int dp, int dp1, int dp2) {
        return Math.min(dp, Math.min(dp1, dp2));
    }
}