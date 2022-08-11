/*
 * @lc app=leetcode.cn id=304 lang=java
 *
 * [304] 二维区域和检索 - 矩阵不可变
 */

// @lc code=start
class NumMatrix {

    // 维护一个矩阵：记录以(0,0)为起点的任意一个矩阵的内部元素的和
    private int[][] arr;

    public NumMatrix(int[][] matrix) {
        // 二维数组其实是一个一维数组，数组中每个元素是一个一维数组，故行列长度如下
        int rows = matrix.length;
        int cloumns = matrix[0].length;
        if(rows == 0 || cloumns == 0)    return;

        arr = new int[rows + 1][cloumns + 1];
        
        // 前缀和二维矩阵
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cloumns; j++) {
                // 对于每一个位置，我们来求元素和
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1] + matrix[i-1][j-1] - arr[i - 1][j - 1];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return arr[row2 + 1][col2 + 1] - arr[row1][col2+1] - arr[row2+1][col1] + arr[row1][col1];
    }
}
/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
// @lc code=end



// class NumMatrix {

//     // 维护一个矩阵：记录以(0,0)为起点的任意一个矩阵的内部元素的和
//     private int[][] arr;

//     public NumMatrix(int[][] matrix) {
//         // 二维数组其实是一个一维数组，数组中每个元素是一个一维数组，故行列长度如下
//         int rows = matrix.length;//5
//         int cloumns = matrix[0].length;//5
//         if(rows == 0 || cloumns == 0)    return;

//         arr = new int[rows + 1][cloumns + 1];
        
//         // 前缀和
//         for (int i = 1; i <= rows; i++) {
//             for (int j = 1; j <= cloumns; j++) {
//                 // 对于每一个位置，我们来进行求值
//                 arr[i][j] = arr[i - 1][j] + arr[i][j - 1] + matrix[i-1][j-1] - arr[i - 1][j - 1];
//             }
//         }
//     }
    
//     public int sumRegion(int row1, int col1, int row2, int col2) {
//         return arr[row2 + 1][col2 + 1] - arr[row1][col2+1] - arr[row2+1][col1] + arr[row1][col1];
//     }
// }
