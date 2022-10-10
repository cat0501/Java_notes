import java.util.LinkedList;
import java.util.List;

/**
 * @author Lemonade
 * @description 螺旋矩阵（顺时针螺旋顺序，返回矩阵中所有元素）
 * @updateTime 2022/10/10 10:10
 */

public class M54 {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.println(new Solution54().spiralOrder(matrix));
    }
}
//输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]

// 解法1：直接遍历
class Solution54 {
    public List<Integer> spiralOrder(int[][] matrix) {
        LinkedList<Integer> result = new LinkedList<>();
        int left = 0;
        int right = matrix[0].length - 1;//3
        int top = 0;
        int bottom = matrix.length - 1;//2

        int total = matrix.length * matrix[0].length;
        while (total > 0){
            // 从左往右
            for (int i = left; i <= right; i++){
                result.add(matrix[top][i]);
                total--;
            }
            top++;

            // 从上往下
            for (int i = top; i <= bottom; i++){
                result.add(matrix[i][right]);
                total--;
            }
            right--;

            // 从右往左
            for (int i = right; i >= left && total > 0; i--){
                result.add(matrix[bottom][i]);
                total--;
            }
            bottom--;

            // 从下往上
            for (int i = bottom; i >= top; i--){
                result.add(matrix[i][left]);
                total--;
            }
            left++;
        }
        return result;
    }
}