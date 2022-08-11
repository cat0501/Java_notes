package array_link;

/**
 * @author cat
 * @description
 * @date 2022/5/29 下午3:38
 */

import java.util.Arrays;
import java.util.HashMap;

/**
给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
<p>
示例 1：
输入：nums = [1,1,1], k = 2
输出：2

示例 2：
输入：nums = [1,2,3], k = 3
输出：2
 */
public class M560 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int[] nums2 = {1, 1, 1};
        int[] nums3 = {3, 5, 2, -2, 4, 1};

        int count = new Solution2().subarraySum(nums, 3);
        int count2 = new Solution2().subarraySum(nums2, 2);
        int count3 = new Solution2().subarraySum(nums3, 2);
        System.out.println(count);
        System.out.println(count2);
        System.out.println(count3);
    }

    /**
     * 解法1：前缀和
     *
     * 时间复杂度n^2,空间复杂度n，并不是最优的解法。
     * 不过通过这个解法理解了前缀和数组的工作原理后，可以使用一些巧妙的办法降低时间复杂度。
     */
    static class Solution {
        public int subarraySum(int[] nums, int k) {
            // 构造前缀和
            int[] sums = new int[nums.length + 1];
            for (int i = 0; i < sums.length; i++) {
                if (i != 0) {
                    sums[i] = sums[i - 1] + nums[i - 1];
                }
            }
            System.out.println(Arrays.toString(sums));

            // 穷举所有子数组
            int count = 0;
            for (int i = 1; i < sums.length; i++) {
                for (int j = 0; j < i; j++) {
                    //
                    if (sums[i] - sums[j] == k) {
                        count++;
                    }
                }

            }
            return count;
        }
    }

    /**
     * 解法2：用hash表
     *
     * 时间复杂度n,最优解法。
     */
    static class Solution2 {
        public int subarraySum(int[] nums, int k) {
            //int n = nums.length;

            // 哈希表 preSum: 前缀和->该前缀和出现的次数
            HashMap<Integer, Integer> preSum = new HashMap<>();
            preSum.put(0, 1);

            int sum = 0, count = 0;

            for (int num : nums) {
                sum += num;
                // 期待的前缀和
                int target = sum - k;
                if (preSum.containsKey(target)) {
                    // 如果有这个前缀和，直接更新答案
                    count += preSum.get(target);
                }
                // 前缀和加入并记录出现次数
                preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
            }

            System.out.println(preSum);
            return count;
        }
    }

}
