package algorithm;

import java.util.*;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/3/17 14:16
 */
public class M15 {
}


/**
 输入：nums = [-1,0,1,2,-1,-4]
 输出：[[-1,-1,2],[-1,0,1]]
 解释：
 nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 注意，输出的顺序和三元组的顺序并不重要。
 */

class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> millionYuanList = new ArrayList<>();
        // 人都不够，还三胎
        if (nums.length < 3){
            return millionYuanList;
        }
        // 孩子们排好队
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            // 如果老大都大于0 后面的兄弟肯定都大于0 直接返回就好
            if (nums[i] > 0){
                break;
            }
            // 老大出列
            Integer first = nums[i];
            // 老大想多领奖
            // continue 语句会终止本次循环，循环体中 continue 之后的语句将不再执行，接着进行下次循环
            //if (i < nums.length - 1 && nums[i] == nums[i + 1]) continue;
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            // 画个圈 ，让各自老二在里面呆着
            Set<Integer> set = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                // 老三出列，一会和老大一起去圈里找老二
                Integer third = nums[j];
                // 根据目标 老大+老二+老三=0，老二 = -（老大+老二）
                Integer second = -(first + third);
                // 找到老二，记到中奖名单
                if (set.contains(second)){
                    millionYuanList.add(new ArrayList<>(Arrays.asList(first, third,-(first + third))));
                    // 老三想多领奖
                    while (j < nums.length - 1 && nums[j] == nums[j + 1] ) j++;
                }
                set.add(third);
            }
        }

        return millionYuanList;
    }
}