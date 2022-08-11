
/**
 * @author cat
 * @description
LeetCode 53. Maximum Subarray 最大子序和
作为一道入门级别的动态规划题目，互联网大厂都喜欢考。
并且根据大家的统计来看，属于必须掌握的十道算法题之一。

给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
子数组 是数组中的一个连续部分。

示例 1：
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。

示例 2：
输入：nums = [1]
输出：1

示例 3：
输入：nums = [5,4,-1,7,8]
输出：23

进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
链接：https://leetcode-cn.com/problems/maximum-subarray
 * @date 2022/4/29 上午9:46
 */
public class Lc53 {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray2(nums));
        System.out.println(maxSubArray3(nums));
    }

    //使用了三重循环，其中两层循环用于穷举所有可能的子数组，一层循环用于计算某个子数组的和。
    // 因此，算法的时间复杂度是O(N3) 。
    //public static int maxSubArray(int[] nums) {
    //    int length = nums.length;
    //    int res = Integer.MIN_VALUE;
    //
    //    for (int i = 0; i < length; i++) {
    //        for (int j = i; j < length; j++) {
    //            int sum = 0;
    //            for (int k = i; k <= j; k++) {
    //                sum += nums[k];
    //            }
    //
    //            res = Math.max(sum, res);
    //        }
    //
    //    }
    //
    //    return res;
    //}


    public static int maxSubArray2(int[] nums) {
        int length = nums.length;
        int res = Integer.MIN_VALUE;

        for (int i = 0; i < length; i++) {
            int sum = 0;

            for (int j = i; j < length; j++) {
                sum += nums[j];
                res = Math.max(sum, res);

                //for (int k = i; k <= j; k++) {
                //    sum += nums[k];
                //}
                //
                //res = Math.max(sum, res);
            }

        }

        return res;
    }

    /**
     * 解法3
     * @param nums
     * @return
     */
    public static int maxSubArray3(int[] nums) {
        int res = Integer.MIN_VALUE;

        int sum = 0;
        for (int num : nums) {
            sum += num;
            if (sum <= 0){
                sum = 0;
            }

            res = Math.max(res, sum);
        }
        return res;
    }

}
