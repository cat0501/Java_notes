package com.exam;

import java.util.Arrays;
import java.util.Scanner;

                public class A3 {
                    public static void main(String[] args) {
                        Scanner scanner = new Scanner(System.in);
                        int n = scanner.nextInt(); // 团队人数
                        int m = scanner.nextInt(); // 项目难度

                        int[] abilities = new int[n];
                        for (int i = 0; i < n; i++) {
                            abilities[i] = scanner.nextInt();
                        }

                        Arrays.sort(abilities); // 按照能力值从小到大排序

                        int left = 1; // 最小的小组成员数
                        int right = abilities[n - 1]; // 最大的小组成员能力值

                        int ans = 0;

                        while (left <= right) {
                            int mid = (left + right) / 2; // 当前小组成员能力值的中间值
                            if (check(abilities, mid, m)) { // 如果当前成员数的小组能处理大于等于 M 难度的项目
                                ans = mid; // 更新 ans
                                left = mid + 1; // 向右搜索，以找到更多的成员数的小组
                            } else {
                                right = mid - 1; // 向左搜索，以找到更少的成员数的小组
                            }
                        }

                        System.out.println(ans);
                    }

                    // 检查一个小组能否处理大于等于 M 难度的项目
                    private static boolean check(int[] abilities, int groupAbility, int m) {
                        int count = 0; // 当前小组的人数统计
                        int maxAbility = 0; // 当前小组的最大能力值

                        for (int i = abilities.length - 1; i >= 0; i--) {
                            if (count == 0 || maxAbility * count >= m) { // 当前小组人数达到指定值 或者 小组能处理大于等于 M 难度的项目
                                count = 0;
                                maxAbility = 0;
                            }

                            count++;
                            maxAbility = Math.max(maxAbility, abilities[i]);

                            if (maxAbility * count >= m && count > 1) { // 可以处理大于等于 M 难度的项目 并且 小组成员数量大于1
                                return true;
                            }
                        }

                        return false;
                    }
                }
