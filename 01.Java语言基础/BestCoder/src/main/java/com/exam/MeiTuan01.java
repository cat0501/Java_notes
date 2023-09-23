package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/23 10:01
 */

import java.util.Scanner;

public class MeiTuan01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = scanner.nextInt();
        }

        int maxScore = scores[0];
        int minScore = scores[0];
        int count = 0;

        for (int i = 1; i < n; i++) {
            if (scores[i] > maxScore || scores[i] < minScore) {
                count++;
            }

            maxScore = Math.max(maxScore, scores[i]);
            minScore = Math.min(minScore, scores[i]);
        }

        System.out.println(count);
    }
}
