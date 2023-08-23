package com.bs.algorithm;

import java.util.Arrays;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/17 15:14
 */
public class
M128 {
    public static void main(String[] args) {
        //int[] nums = {9,1,4,7,3,-1,0,5,8,-1,6};
        int[] nums = {1,2,3,4,100,200};
        System.out.println(new Solution128().longestConsecutive(nums));
    }
}

/**
 输入：nums = [100,4,200,1,3,2]
 输出：4
 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 */


// 思路：
class Solution128 {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);
        int count = 1;
        int resultCount = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] - nums[i] == 1){
                count += 1;
                if (resultCount < count){
                    resultCount = count;
                }
            } else {
                count = 1;
            }
        }
        return resultCount;
    }
}
