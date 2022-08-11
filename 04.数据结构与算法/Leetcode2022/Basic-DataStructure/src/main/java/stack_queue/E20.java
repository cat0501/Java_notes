package stack_queue;

/**
 * @author cat
 * @description
 * @date 2022/5/30 下午6:55
 */

import java.util.Stack;

/**
 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

 有效字符串需满足：
 - 左括号必须用相同类型的右括号闭合。
 - 左括号必须以正确的顺序闭合。

 链接：https://leetcode.cn/problems/valid-parentheses

 示例 1：
 输入：s = "()"
 输出：true

 示例 2：
 输入：s = "()[]{}"
 输出：true

 示例 3：
 输入：s = "(]"
 输出：false

 示例 4：
 输入：s = "([)]"
 输出：false

 示例 5：
 输入：s = "{[]}"
 输出：true
 */
public class E20 {

    public static void main(String[] args) {
        String s1 = "()";
        String s4 = "()[]{}";
        String s2 = "(]";
        String s5 = "([)]";
        String s = "{[]}";
        String s6 = "]";


        //System.out.println(new Solution().isValid(s1));
        //System.out.println(new Solution().isValid(s4));
        //System.out.println(new Solution().isValid(s2));
        //System.out.println(new Solution().isValid(s5));
        //System.out.println(new Solution().isValid(s));
        System.out.println(new Solution().isValid(s6));

    }


    static
    class Solution {
        public boolean isValid(String s) {

            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                //System.out.println("当前字符是：" + c);

                // 当前是右括号
                if (c == ')' || c == '}' || c == ']') {
                    switch (c) {
                        case ')':
                            if (stack.size() > 0 && stack.pop() == '(') {
                            } else {
                                return false;
                            }
                            break;

                        case '}':
                            if (stack.size() > 0 && stack.pop() == '{') {
                            } else {
                                return false;
                            }
                            break;

                        case ']':
                            if (stack.size() > 0 && stack.pop() == '[') {
                            } else {
                                return false;
                            }
                    }

                }
                // 当前是左括号
                else {
                    stack.push(c);
                    //System.out.println("当前的栈为------------------>" + stack.peek());

                }
            }
            //System.out.println("当前的栈size为------------------>" + stack.size());

            return stack.isEmpty();
        }
    }
}
