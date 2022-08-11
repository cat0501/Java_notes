/**
 * @author cat
 * @description
 * @date 2022/6/15 下午10:26
 */


/**
给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。

示例 1：
输入：nums = [1,1,1], k = 2
输出：2

示例 2：
输入：nums = [1,2,3], k = 3
输出：2
 */

public class M560 {
}

//时间复杂度：O(N^2)，这里 N 是数组的长度；
//空间复杂度：O(1)。
class Solution560 {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                    continue;
                }
            }
        }
        return count;
    }
}