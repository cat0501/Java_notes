/*
 * @lc app=leetcode.cn id=303 lang=java
 *
 * [303] 区域和检索 - 数组不可变
 */

// @lc code=start
class NumArray {
    // 前缀和数组
    private int[] preSums;

    // 输入一个数组，构造前缀和
    public NumArray(int[] nums) {
        preSums = new int[nums.length + 1];
        for(int i = 1; i <= nums.length; i++){
            preSums[i] = preSums[i-1] + nums[i-1];
        }
    }

    // 查询闭区间的累加和
    public int sumRange(int left, int right) {
        return preSums[right + 1] - preSums[left];
    }
}
/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
// @lc code=end

/** 
解法1：
15 / 15 个通过测试用例
状态：通过
执行用时: 69 ms
内存消耗: 41.3 MB

    class NumArray {
    private int[] nums;

    public NumArray(int[] nums) {
        this.nums = nums;
    }
    
    public int sumRange(int left, int right) {
        int res = 0;
        for (int i = left; i <= right; i++) {
            res += nums[i];
        }
        return res;
    }
}
*/



/** 
解法2：
15/15 cases passed (8 ms)
Your runtime beats 67.43 % of java submissions
Your memory usage beats 92.22 % of java submissions (40.9 MB) 

class NumArray {
    // 前缀和数组
    private int[] preSums;

    public NumArray(int[] nums) {
        preSums = new int[nums.length + 1];
        for(int i = 1; i <= nums.length; i++){
            preSums[i] = preSums[i-1] + nums[i-1];
        }
    }
    // 查询闭区间的累加和
    public int sumRange(int left, int right) {
        return preSums[right + 1] - preSums[left];
    }
}
*/

/** 
错误：
class NumArray {
    // [-2,0,3,-5,2,-1]
    private int[] nums;
    public NumArray(int[] nums) {
        this.nums = nums;
    }
    
    // 存放前缀和：0 3 8
    private int[] preSums = new int[nums.length + 1];
    
    private void setPreSums(int[] nums){ 
        for(int i = 1; i <= nums.length; i++){
            preSums[0] = 0;
            preSums[i] = preSums[i-1] + nums[i-1];
        }
    }

    public int sumRange(int left, int right) {
        setPreSums(nums);
        return preSums[right + 1] - preSums[left];
    }

}
*/