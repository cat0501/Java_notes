package com.bs.basic;

import java.util.Arrays;

    public class A01 {
        public static int[] findPromotionScore(int[] scores, int m, int n) {
            Arrays.sort(scores);
            System.out.println(scores);
            int minScore = scores[scores.length - n]; // 最低晋级分数线的初值
            int maxScore = scores[scores.length - m]; // 最高晋级分数线的初值

            System.out.println(minScore);
            System.out.println(maxScore);

            while (minScore <= maxScore) {
                int midScore = minScore + (maxScore - minScore) / 2;

                // 计算大于等于分数线的项目组数量
                int promotionCount = 0;
                int eliminationCount = 0;
                for (int score : scores) {
                    if (score >= midScore) {
                        promotionCount++;
                    } else {
                        eliminationCount++;
                    }
                }

                if (promotionCount == n && eliminationCount == scores.length - n) {
                    // 如果晋级和淘汰数量都满足条件，则midScore是满足条件的最小分数线
                    return new int[]{midScore};
                } else if (promotionCount > n) {
                    // 晋级数量大于n，继续往下搜索
                    minScore = midScore + 1;
                } else {
                    // 晋级数量小于n，调整初级晋级分数线的上限
                    maxScore = midScore - 1;
                }
            }

            return new int[]{-1}; // 若不存在分数线则返回-1
        }

        public static void main(String[] args) {
            int[] scores = {1, 2, 3, 4, 5, 6, 7, 8};
            int m = 2;
            int n = 3;

            int[] result = findPromotionScore(scores, m, n);

            if (result[0] == -1) {
                System.out.println("不存在分数线");
            } else {
                System.out.println("满足条件的最小分数线为：" + result[0]);
            }
        }
    }
