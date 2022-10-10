/**
 * @author Lemonade
 * @description 跳跃游戏（贪心）
 * @updateTime 2022/10/10 13:33
 */
public class M55 {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        int[] nums2 = {3,2,1,0,4};
        int[] nums3 = {3,0,8,2,0,0,1};
        System.out.println(new Solution55().canJump(nums3));
    }

}
//给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
//数组中的每个元素代表你在该位置可以跳跃的最大长度。
//判断你是否能够到达最后一个下标。

//输入：nums = [2,3,1,1,4]
//输出：true1
//解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。

//输入：nums = [3,2,1,0,4]
//输出：false
//解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。

//2,0,0
//3,0,8,2,0,0,1

// 贪心
class Solution55 {
    public boolean canJump(int[] nums) {
        int length = nums.length;
        //维护 最远可以到达的位置
        int most = 0;

        for (int i = 0; i < length; i++) {
            if (i <= most){
                //most = Math.max(nums[i], i + nums[i]);
                most = Math.max(most, i + nums[i]);

                //if (most >= length - 1 || length == 1){
                if (most >= length - 1){
                    return true;
                }
            }
        }

        return false;
    }
}