package array_link;

/**
 * @description
 * @date 2022/5/29 上午11:53
 */

import java.util.Arrays;

/**
计算其子矩形范围内元素的总和，该子矩阵的 左上角 为 (row1, col1) ，右下角 为 (row2, col2) 。
<p>
示例 1：
输入:
["NumMatrix","sumRegion","sumRegion","sumRegion"]
[[[[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]],[2,1,4,3],[1,1,2,2],[1,2,2,4]]
输出:
[null, 8, 11, 12]
<p>
解释:
NumMatrix numMatrix = new NumMatrix([[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]);
numMatrix.sumRegion(2, 1, 4, 3); // return 8 (红色矩形框的元素总和)
numMatrix.sumRegion(1, 1, 2, 2); // return 11 (绿色矩形框的元素总和)
numMatrix.sumRegion(1, 2, 2, 4); // return 12 (蓝色矩形框的元素总和)

 */
public class M304 {
    public static void main(String[] args) {
        // 本质上是以数组作为数组元素的数组，即数组的数组。
        int[][] arr2 = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};

        NumMatrix numMatrix = new NumMatrix(arr2);
        System.out.println(numMatrix.sumRegion(2, 1, 4, 3));
        System.out.println(numMatrix.sumRegion(1, 1, 2, 2));
        System.out.println(numMatrix.sumRegion(1, 2, 2, 4));
    }
}

/**
 * 解法1：分别是对每一行计算一维前缀和
 */
class NumMatrix {

    int[][] sums;
    // 矩阵初始化
    public NumMatrix(int[][] matrix) {
        // 行数
        int length1 = matrix.length;

        if (length1 > 0){
            // 列数
            int length2 = matrix[0].length;
            sums = new int[length1][length2 + 1];

            int rows = 0;
            for (int[] ints : matrix) {
                //System.out.println(rows);
                //System.out.println(Arrays.toString(ints));

                // 一维数组处理
                for (int i = 0; i < length2 + 1; i++) {
                    if (i != 0){
                        sums[rows][i] = sums[rows][i - 1] + ints[i -1 ];
                        //System.out.println("now: " + ints[i - 1]);
                    }
                }

                rows++;
            }
            System.out.println(Arrays.deepToString(sums));
        }
    }

    // 计算子矩阵元素和
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int result = 0;

        for (int i = row1; i <= row2; i++) {
            //System.out.println("now" + (sums[i][col2 + 1] - sums[i][col1]));
            result += (sums[i][col2 + 1] - sums[i][col1]);
        }

        return result;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
