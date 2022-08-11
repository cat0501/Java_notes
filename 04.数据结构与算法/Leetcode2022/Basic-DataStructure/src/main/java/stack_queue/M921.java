package stack_queue;

/**
 * @author cat
 * @description
 * @date 2022/5/30 下午7:46
 */

/**
只有满足下面几点之一，括号字符串才是有效的：
 - 它是一个空字符串，或者
 - 它可以被写成 AB （A 与 B 连接）, 其中 A 和 B 都是有效字符串，或者
 - 它可以被写作 (A)，其中 A 是有效字符串。

 给定一个括号字符串 s ，移动N次，你就可以在字符串的任何位置插入一个括号。
 - 例如，如果 s = "()))" ，你可以插入一个开始括号为 "(()))" 或结束括号为 "())))" 。
 返回 为使结果字符串 s 有效而必须添加的最少括号数。

 示例 1：
 输入：s = "())"
 输出：1

 示例 2：
 输入：s = "((("
 输出：3

 链接：https://leetcode.cn/problems/minimum-add-to-make-parentheses-valid
 */
public class M921 {

    public static void main(String[] args) {
        String s = "())";
        String s2 = "(((";

        System.out.println(new Solution().minAddToMakeValid(s));
        System.out.println(new Solution().minAddToMakeValid(s2));
    }

    static
    class Solution {
        public int minAddToMakeValid(String s) {
            // 记录插入次数
            int count = 0;
            // need 变量记录右括号的需求量
            int need = 0;

            for (int i = 0; i < s.length(); i++) {
                // 当前是左括号
                if (s.charAt(i) == '('){
                    // 对右括号的需求 + 1
                    need ++;
                }

                // 当前是右括号
                if (s.charAt(i) == ')'){
                    // 对右括号的需求 - 1
                    need --;

                    if (need == -1){
                        need = 0;
                        count ++;
                    }
                }
            }


            return count + need;
        }
    }

}
