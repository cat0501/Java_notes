import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/12 下午11:29
 */

//作为一道入门级别的动态规划题目，互联网大厂都喜欢考。
//并且根据大家的统计来看，属于必须掌握的十道算法题之一。

// 周四晚7点：字节面试
// 项目
// 算法
// 基础（对照简历复习复习）

public class E53 {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(new Solution53().maxSubArray(nums));
    }

}

//输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出：6
//解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。

class Solution53 {
    public int maxSubArray(int[] nums) {
        //（1）定义dp数组：dp[i]表示以nums[i]结尾的最大和连续子数组
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MIN_VALUE);

        // base case
        dp[0] = nums[0];
        //（3）状态转移
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
        }

        //（2）返回
        //System.out.println("dp[]: " + Arrays.toString(dp));
        Arrays.sort(dp);
        return dp[dp.length - 1];
    }
}