package com.exam;

/**
 * @author cat
 * @description
 * @date 2023/9/20 19:10
 */
import java.util.*;

    public class SF0920_01 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            int[] A = new int[n];
            int[] B = new int[n];
            for (int i = 0; i < n; i++) {
                A[i] = scanner.nextInt();
            }
            for (int i = 0; i < n; i++) {
                B[i] = scanner.nextInt();
            }
            int[] stack = new int[0]; // 初始栈C为空

            long maxProfit = calculateProfit(A, B, stack, 0, 0);

            System.out.println(maxProfit);
        }


        public static long calculateProfit(int[] A, int[] B, int[] stack, long profit, int index) {
            int n = A.length;
            if (index == 2 * n) {
                return profit;
            }

            long maxProfit = 0;

            // 操作1：将数组A中最小且尚未删除的数压入栈C中
            if (index < n && A[index] != -1) {
                int minValue = Integer.MAX_VALUE;
                int minIndex = -1;
                for (int i = 0; i < n; i++) {
                    if (A[i] != -1 && A[i] < minValue) {
                        minValue = A[i];
                        minIndex = i;
                    }
                }

                int[] newA = Arrays.copyOf(A, n);
                newA[minIndex] = -1;

                int[] newStack = Arrays.copyOf(stack, stack.length + 1);
                newStack[newStack.length - 1] = minValue;

                maxProfit = Math.max(maxProfit, calculateProfit(newA, B, newStack, profit, index + 1));
            }

            // 操作2：获得收益
            if (stack.length > 0) {
                int top = stack[stack.length - 1];
                int[] newStack = Arrays.copyOf(stack, stack.length - 1);

                long curProfit = (long) B[index % n] * top;

                maxProfit = Math.max(maxProfit, calculateProfit(A, B, newStack, profit + curProfit, index + 1));
            }

            // 不选择操作任何数
            maxProfit = Math.max(maxProfit, calculateProfit(A, B, stack, profit, index + 1));

            return maxProfit;
        }

    }



