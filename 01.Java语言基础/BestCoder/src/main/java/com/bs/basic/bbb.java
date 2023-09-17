package com.bs.basic;

import java.util.Arrays;
import java.util.Scanner;

public class bbb {
    public static void main(String[] args) {
        String rtm = "asd";
        long b = Long.parseLong(rtm);

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 安装照明灯的位置数
        int k = scanner.nextInt(); // 照明灯数量
        int[] lightPositions = new int[n];
        for (int i = 0; i < n; i++) {
            lightPositions[i] = scanner.nextInt();
        }
        Arrays.sort(lightPositions); // 按照升序排序
        //不对吗？？？？？  给了输入优化哈
        int[] distances = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            distances[i] = lightPositions[i + 1] - lightPositions[i];
        }
        Arrays.sort(distances); // 按照升序排序

        int totalDistance = lightPositions[n - 1] - lightPositions[0]; // 街道的长度
        for (int i = 0; i < k - 1; i++) {
            totalDistance -= distances[n - 2 - i]; // 选取前k-1个最小的距离
        }


        System.out.println(totalDistance);
    }
}








