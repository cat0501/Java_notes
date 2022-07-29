package others;

import java.util.ArrayList;
import java.util.List;

// 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
// 输出：[1,2,3,6,9,8,7,4,5]

// https://leetcode.cn/problems/spiral-matrix/solution/luo-xuan-ju-zhen-by-leetcode-solution/
public class M54 {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        List<Integer> integerList = new Solution().spiralOrder(matrix);
        System.out.println(integerList);
    }
}

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }

        int rows = matrix.length, columns = matrix[0].length;
        // 判断路径是否进入之前访问过的位置。每个元素表示该位置是否被访问过。
        boolean[][] visited = new boolean[rows][columns];
        // 如何判断路径是否结束？
        // 由于矩阵中的每个元素都被访问一次，因此路径的长度即为矩阵中的元素数量，
        int total = rows * columns;
        int row = 0, column = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;

        for (int i = 0; i < total; i++) {
            order.add(matrix[row][column]);
            visited[row][column] = true;
            // 新的行和列
            int nextRow = row + directions[directionIndex][0];
            int nextColumn = column + directions[directionIndex][1];

            //？
            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
                directionIndex = (directionIndex + 1) % 4;
            }
            // update new row and column
            row += directions[directionIndex][0];
            column += directions[directionIndex][1];
        }
        // return
        return order;
    }
}


