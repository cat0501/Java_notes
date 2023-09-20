package com.exam;

/**
 * @author cat 掌阅
 * @description
 * @date 2023/9/18 20:04
 */
import java.util.Scanner;

public class A0918_zy_01 {
    public static void main(String[] args) {
        String a = "1";


        int b;
        b=Integer.parseInt(a);
        System.out.println(b);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int result = minSubArrayLen(input);
        System.out.println(result);
    }

    public static int minSubArrayLen(String input) {
        String[] numsStr = input.split(",");
        int[] nums = new int[numsStr.length - 1];
        for (int i = 0; i < numsStr.length - 1; i++) {
            nums[i] = Integer.parseInt(numsStr[i]);
        }

        int lo = 0, hi = 0, sum = 0, min = Integer.MAX_VALUE;
        int s = Integer.parseInt(numsStr[numsStr.length - 1]);
        while (hi < nums.length) {
            sum += nums[hi++];
            while (sum >= s) {
                min = Math.min(min, hi - lo);
                sum -= nums[lo++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
