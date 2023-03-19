package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/17 9:56
 */
public class M54 {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.println(new Solution().spiralOrder(matrix));

    }
}


/**
 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 输出：[1,2,3,4,8,12,11,10,9,5,6,7]

 1  2  3  4
 5  6  7  8
 9 10 11 12
 */


class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<>();

        int rows = matrix.length, columns = matrix[0].length;
        int total = rows * columns;

        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        int row = 0, colum = 0;
        int nextRow = 0, nextColum = 0;
        int directionIndex = 0;
        boolean[][] visited = new boolean[rows][columns];

        for(int i = 0; i < total; i++){
            order.add(matrix[row][colum]);
            visited[row][colum] = true;
            nextRow = row + directions[directionIndex][0];
            nextColum = colum + directions[directionIndex][1];

            if(nextRow >= rows || nextRow < 0 || nextColum >= columns || nextColum < 0 || visited[nextRow][nextColum]){
                directionIndex = (directionIndex + 1) % 4;
            }
            row += directions[directionIndex][0];
            colum += directions[directionIndex][1];
        }
        return order;
    }
}
