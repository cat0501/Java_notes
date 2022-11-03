package array_link;

/**
 * @description
 * @date 2022/5/29 上午11:05
 */


/**
计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
<p>
示例 1：
输入：
["NumArray", "sumRange", "sumRange", "sumRange"]
[[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
输出：
[null, 1, -1, -3]
<p>
解释：
NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
 */
public class E303 {
    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        NumArray2 obj = new NumArray2(nums);

        int result = obj.sumRange(2, 5);
        System.out.println(result);
    }
}


/**
 解法1：普通的累加
 这样，可以达到效果，但是效率很差，因为 sumRange ⽅法会被频繁调⽤，⽽它的时间复杂度是 O(N)，其 中 N 代表 nums 数组的⻓度。
 这道题的最优解法是使用前缀和技巧，将 sumRange 函数的时间复杂度降为 O(1)，说白了就是不要在 sumRange ⾥⾯⽤ for 循环。
 */
class NumArray {
    private final int[] nums;

    // 构造方法
    public NumArray(int[] nums) {
        this.nums = nums;
    }

    // 求索引范围内的和
    public int sumRange(int left, int right) {
        int result = 0;
        for (int i = left; i <= right; i++){
            result += nums[i];
        }
        return result;
    }
}

/**
 解法2：构造一个前缀和数组
 */
class NumArray2 {
    private final int[] sums;

    // 构造方法
    public NumArray2(int[] nums) {
        sums = new int[nums.length + 1];
        for (int i = 0; i < sums.length - 1; i++){
            sums[i + 1] = sums[i] + nums[i];
        }
    }

    // 求索引范围内的和
    public int sumRange(int left, int right) {
        return sums[right + 1] - sums[left];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */