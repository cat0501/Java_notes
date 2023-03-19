package algorithm;

import java.util.Arrays;

/**
 * @author cat
 * @description 最短无序连续子数组 shortest-unsorted-continuous-subarray
 * @date 2023/3/19 13:58
 */
public class M581 {
    public static void main(String[] args) {
        int[] nums = {2,6,4,8,10,9,15};
        int[] nums2 = {1,2,3,4};
        //System.out.println(new Solution581().findUnsortedSubarray(nums));
        System.out.println(new Solution581().findUnsortedSubarray(nums2));
    }
}

/**
 输入：nums = [2,6,4,8,10,9,15]
 输出：5
 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
 */

/**
 思路：
 具体地，我们创建数组 nums 的拷贝，记作数组 tmp。
 并对该数组进行排序，然后我们从左向右找到第一个两数组不同的位置，即为左边界。同理也可以找到右边界。
 最后输出左右边界间数组的长度即可。

 特别地，当原数组有序时，长度为0，我们可以直接返回结果。
 */
class Solution581 {
    public int findUnsortedSubarray(int[] nums) {
        int size = nums.length;
        int[] tmp = Arrays.copyOf(nums, size);
        Arrays.sort(nums);

        int left = 0, right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == tmp[i]) {
                left += 1;
            } else {
                break;
            }
        }
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] == tmp[i]){
                right += 1;
            } else {
                break;
            }
        }

        if (left == size || right == size){
            return 0;
        }
        return nums.length - left - right;
    }
}