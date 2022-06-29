
import java.util.Arrays;

/**
 * @author cat
 * @description
 * @date 2022/6/12 下午12:58
 */
public class M322 {
    public static void main(String[] args) {

        //int[] dp = new int[9];
        //Arrays.fill(dp, 9);
        //System.out.println(Arrays.toString(dp));

        //int[] coins = {1, 2, 5};
        //int amount = 11;
        //int i = new Solution322().coinChange(coins, amount);
        //System.out.println(i);
    }
}



// 这个问题其实解决了，只不过需要消除⼀下重叠⼦问题
class Solution322 {
    public int coinChange(int[] coins, int amount) {
        // 题⽬要求的最终结果是 dp(amount)
        return dp(coins, amount);
    }

    int dp(int[] coins, int amount){
        // (1)base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        int res = Integer.MAX_VALUE;

        for (int coin : coins) {
            // (2)计算子问题的结果
            int subProblem = dp(coins, amount - coin);

            // 子问题无解则跳过
            if (subProblem == -1) continue;

            // (3)子问题中选择最优解。然后加1
            res = Math.min(res, subProblem + 1);
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }
}

// 解法2：带备忘录的迭代算法
class Solution322322 {

    int[] memo;

    public int coinChange(int[] coins, int amount) {
        memo = new int[amount + 1];
        //Arrays.fill(memo, -1);
        Arrays.fill(memo, -2);
        return dp(coins, amount);
    }

    private int dp(int[] coins, int amount) {

        // base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        // 查备忘录，防止重复计算
        if (memo[amount] != -2) return memo[amount];

        //int res = amount + 1;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 求解子问题
            int subProblem = dp(coins, amount - coin);
            if (subProblem == -1) continue;
            // 子问题中选择最优解，然后加1
            res = Math.min(res, subProblem + 1);
        }
        // 保存到备忘录
        // 子问题无解，需要返回-1
        //memo[amount] = res;
        memo[amount] = res == Integer.MAX_VALUE ? -1 : res;

        return memo[amount];
    }
}

// 解法3：dp数组的迭代解法
class Solution32 {
    public int coinChange(int[] coins, int amount) {
        // dp 数组的定义：当⽬标⾦额为 i 时，⾄少需要 dp[i] 枚硬币凑出。
        // 数组⼤⼩为 amount + 1，初始值也为 amount + 1
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        // base case
        dp[0] = 0;
        // 遍历所有状态的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 求所有选择的最⼩值
            for (int coin : coins) {
                // 子问题无解，跳过
                if (i - coin < 0){
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}




