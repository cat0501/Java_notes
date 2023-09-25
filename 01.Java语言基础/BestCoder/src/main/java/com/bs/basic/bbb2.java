package com.bs.basic;

import java.util.Arrays;
import java.util.Scanner;

// 你打个输入  这里我测试哈
//就是图片上的
// 你试下这个新的呀   你弄个输入5

public class bbb2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 安装照明灯的位置数
        int k = scanner.nextInt(); // 照明灯数量
        int[] lightPositions = new int[n];
        for (int i = 0; i < n; i++) {
            lightPositions[i] = scanner.nextInt();
        }
        Arrays.sort(lightPositions); // 按照升序排序

        int[] distances = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            distances[i] = lightPositions[i + 1] - lightPositions[i] - 1;
        }
        Arrays.sort(distances); // 按照升序排序

        int totalDistance = lightPositions[0] - 0 + 1; // 街道起点到第一个路灯的距离
        totalDistance += lightPositions[n - 1] - lightPositions[n - 2] + 1; // 街道最后一个路灯到终点的距离
        for (int i = n - 3; i >= n - k; i--) {
            totalDistance += distances[i];
        }

        System.out.println(totalDistance);
    }
}
