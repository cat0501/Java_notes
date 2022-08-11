///*
// * @lc app=leetcode.cn id=560 lang=java
// *
// * [560] 和为 K 的子数组
// */
//
//import java.util.HashMap;
//
//// @lc code=start
//class Solution {
//    public int subarraySum(int[] nums, int k) {
//        int n = nums.length;
//
//        HashMap<Integer, Integer> preSum = new HashMap<>();
//        preSum.put(0,1);
//
//        int count = 0,sum = 0;
//        for(int i = 0; i < n; i++){
//            sum += nums[i];
//            int sum_j = sum - k;
//            if (preSum.containsKey(sum_j)){
//                count += preSum.get(sum_j);
//            }
//            preSum.put(sum, preSum.getOrDefault(sum,0) + 1);
//        }
//        return count;
//    }
//}
//
//// @lc code=end
//
///**
//class Solution {
//
//    public int subarraySum(int[] nums, int k) {
//        // 1 1 1
//        int n = nums.length;
//        // 前缀和数组
//        int[] preSum = new int[n + 1];
//        preSum[0] = 0;
//        // 0 1 2 3
//        for(int i = 0; i < n; n++){
//            preSum[i + 1] = preSum[i] + nums[i];
//        }
//
//        int count = 0;
//        for(int i = 1; i <= n; i++){
//            for(int j = 0; j < i; j++){
//                if(preSum[i] - preSum[j] == k){
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//}
//*/
//
//
///**
//class Solution {
//    public int subarraySum(int[] nums, int k) {
//        int n = nums.length;
//
//        HashMap<Integer, Integer> preSum = new HashMap<>();
//        preSum.put(0,1);
//
//        int count = 0,sum = 0;
//        for(int i = 0; i < n; i++){
//            sum += nums[i];
//            int sum_j = sum - k;
//            if (preSum.containsKey(sum_j)){
//                count += preSum.get(sum_j);
//            }
//            preSum.put(sum, preSum.getOrDefault(sum,0) + 1);
//        }
//        return count;
//
//    }
//}
//
//
//class Solution {
//    // 3 4 7 2 -3 1 4 2
//    // 1 2 3    3    2
//    public int subarraySum(int[] nums, int k) {
//        int count = 0;
//        for (int start = 0; start < nums.length; ++start) {
//            int sum = 0;
//            for (int end = start; end >= 0; --end) {
//                sum += nums[end];
//                if (sum == k) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//}
//*/