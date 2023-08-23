package com.bs.niuke;

/**
 * @author cat
 * @description
 * @date 2022/4/13 下午8:15
 */

import java.util.Scanner;

/**
 * 描述
 * 有一个长度为7的无序数组，按照从小到大的顺序排序后输出。
 * <p>
 * 输入：
 * 13 11 9 7 5 3 1
 * 输出：
 * 1 3 5 7 9 11 13
 */

public class Java51 {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[7];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }
        scanner.close();

        //write your code here......
        // 实现冒泡排序的逻辑
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] ^= arr[j + 1];
                    arr[j + 1] ^= arr[j];
                    arr[j] ^= arr[j + 1];
                }
            }
            // while (arr[i] > arr[i + 1]) {
            //    int tmp = arr[i];
            //    arr[i] = arr[i + 1];
            //    arr[i + 1] = tmp;
            //    i++;
            //    if (i >= arr.length -1){
            //        break;
            //    }
        }


        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
    }

}
