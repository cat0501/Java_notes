//import static com.sun.tools.doclint.Entity.sum;

/**
 * @author cat
 * @description
 * @date 2022/6/12 上午10:41
 */
public class E509 {

    public static void main(String[] args) {

    }
}

//斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
//F(0) = 0，F(1) = 1
//F(n) = F(n - 1) + F(n - 2)，其中 n > 1
//给定 n ，请计算 F(n) 。

/**
 输入：n = 2
 输出：1
 解释：F(2) = F(1) + F(0) = 1 + 0 = 1

 输入：n = 3
 输出：2
 解释：F(3) = F(2) + F(1) = 1 + 1 = 2

 输入：n = 4
 输出：3
 解释：F(4) = F(3) + F(2) = 2 + 1 = 3
 */


//解法1：递归解法
//class Solution2 {
//    public int fib(int n) {
//
//        if (n == 0)return 0;
//        if (n == 1)return 1;
//
//
//        return fib(n - 1) + fib(n - 2);
//    }
//}


//解法2：带备忘录的递归解法
class Solution {
    public int fib(int n) {
        int[] memo = new int[n + 1];

        return help(memo, n);
    }

    int help(int[] memo, int n) {
        if (n == 0 || n == 1) return n;
        //思考🤔
        // 已经计算过，不用再计算了
        if (memo[n] != 0) return memo[n];

        memo[n] = help(memo, n - 1) + help(memo, n - 2);
        return memo[n];
    }

}

// dp
class Solution3 {
    public int fib(int n) {
        if (n == 0) return 0;
        int[] dp = new int[n + 1];

        dp[0] = 0; dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}

class Solution4 {
    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int pre = 1; int cur = 1;
        for (int i = 3; i <= n; i++) {
            int sum = pre + cur;
            pre = cur;
            cur = sum;
        }

        return cur;
    }
}







