package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/23 13:54
 */

import java.util.Scanner;

public class wangyiT1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            for (int num : arr) {
                System.out.println(num);
            }


            boolean canBeSorted = true;
            for (int i = 1; i < n; i++) {
                if (arr[i] >= arr[i-1]) {
                    System.out.println("1111111111");
                    continue;
                }
                if (arr[(i+2) % n] <= arr[i-1]) {
                    System.out.println("2222222222");
                    continue;
                }
                if (arr[i] <= arr[(i+2) % n]) {
                    System.out.println("33333333333");
                    continue;
                }
                System.out.println("444444444444");
                canBeSorted = false;
                break;
            }







            if (canBeSorted) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        sc.close();
    }
}
