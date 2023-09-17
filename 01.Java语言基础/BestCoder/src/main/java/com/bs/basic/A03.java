package com.bs.basic;

import java.util.Arrays;

        public class A03 {
            public static int findQualificationScore(int[] scores, int m, int n) {
                // 将分数按照从高到低排序
                Arrays.sort(scores);

                int count = scores.length;
                int[] result = new int[2]; // 存储满足条件的最小值和最大值

                if (count < m) {
                    result[0] = -1; // 分数数量小于最小晋级数量，无法晋级
                    result[1] = -1;
                } else {
                    result[0] = scores[count - n - 1]; // 淘汰的最低分数线
                    result[1] = scores[count - m]; // 晋级的最高分数线
                }

                return result[0];
            }

            public static void main(String[] args) {
                int[] scores = { 1, 2, 3, 4, 5, 6,7,8 };
                int m = 2;
                int n = 3;

                //int[] result = findQualificationScore(scores, m, n);
                //System.out.println("最小晋级分数线：" + result[0]);
                //System.out.println("最大晋级分数线：" + result[1]);
            }
        }

