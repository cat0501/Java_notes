package algorithm;

/**
 * @author cat
 * @description 寻找重复数 find-the-duplicate-number
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 * @date 2023/3/19 20:48
 */
public class M287 {
    public static void main(String[] args) {
        int[] nums = {1,3,4,2,2};
        System.out.println(new Solution287().findDuplicate(nums));
    }
}

/**
 输入：nums = [1,3,4,2,2]
 输出：2
 */

/**
 思路：二分查找
 */
class Solution287 {
    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int l = 1, r = n - 1, ans = -1;

        while(l <= r){
            int middleNumber = (l + r) >> 1;
            int count = 0;
            for (int num : nums) {
                if (num <= middleNumber) {
                    count++;
                }
            }
            if (count <= middleNumber){
                l = middleNumber + 1;
            } else {
                r = middleNumber - 1;
                ans = middleNumber;
            }
        }

        return ans;
    }
}
