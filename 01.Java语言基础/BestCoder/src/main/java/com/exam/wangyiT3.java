package com.exam;

import java.util.Scanner;

public class wangyiT3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        int[] sout = new int[t];
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int x = scanner.nextInt();
            int[] abilities = new int[n];
            for (int j = 0; j < n; j++) {
                abilities[j] = scanner.nextInt();
            }
            int count = countProjectPlans(n, x, abilities);
            sout[i] = count;
        }
        for (int i : sout) {
            System.out.println(i);
        }
        scanner.close();
    }

    private static int countProjectPlans(int n, int x, int[] abilities) {
        int half = n / 2;
        int count = 0;
        for (int i = 0; i < (1 << n); i++) {
            if (Integer.bitCount(i) == half) {
                int total = 0;
                for (int j = 0; j < n; j++) {
                    if ((i & (1 << j)) != 0) {
                        total += abilities[j];
                    }
                }
                if (total >= x) {
                    count++;
                }
            }
        }
        return count;
    }
}
