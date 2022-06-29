import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/12 下午11:06
 */
public class M300 {
    public static void main(String[] args) {
        int[] arr = {10,9,2,5,3,7,101,18};

        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[arr.length - 1]);
    }
}
//最长递增子序列
//https://leetcode.cn/problems/longest-increasing-subsequence/
class Solution300 {

    public int lengthOfLIS(int[] nums) {
            // (1)定义dp数组：dp[i] 表示以 nums[i] 这个数结尾的最⻓递增⼦序列的⻓度。
        int length = nums.length;
        int[] dp = new int[length];
        Arrays.fill(dp, 1);

        // (3)数学归纳法
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // (2)最后返回的是
        //int count = 1;

        //for (int i : dp) {
        //    if (i > count){
        //        count = i;
        //    }
        //}
        Arrays.sort(dp);
        return dp[dp.length -1];
    }
}



class Solution3001 {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        Arrays.sort(dp);
        return dp[dp.length - 1];
    }
}

