package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/21 19:54
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class A0921_04 {

    char c = '\r';
    float f = 11.1F;
    double d = 5.3E12;
    //byte bb = 433;
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            String row = scanner.next();
            for (int j = 0; j < m; j++) {
                matrix[i][j] = row.charAt(j);
            }
        }
        int[][] dp = new int[n][m];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = i; k >= 0; k--) {
                    Set<Character> set = new HashSet<>();
                    for (int l = j; l >= 0; l--) {
                        if (set.contains(matrix[k][l])) {
                            break;
                        }
                        set.add(matrix[k][l]);
                        dp[i][j]++;
                        count += dp[i][j];
                    }
                }
            }
        }
        System.out.println(count);
    }
}
